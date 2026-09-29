package com.turing.flea.service;

import com.turing.flea.common.GoodsStatus;
import com.turing.flea.common.Session;
import com.turing.flea.common.TradeStatus;
import com.turing.flea.dao.GoodsDao;
import com.turing.flea.dao.TradeDao;
import com.turing.flea.dao.impl.GoodsDaoImpl;
import com.turing.flea.dao.impl.TradeDaoImpl;
import com.turing.flea.entity.Goods;
import com.turing.flea.entity.Trade;
import com.turing.flea.util.DBUtil;

import java.sql.Connection;
import java.util.List;

/**
 * 交易业务层: 创建 / 取消 / 完成 / 退货
 * 负责人: 亦妄辰
 *
 * 交易状态和商品状态要一起变:
 *   创建交易 -> 交易未支付 + 商品已售出
 *   取消交易 -> 交易已取消 + 商品变回在售
 *
 * 【事务说明】create / cancel / applyRefund 这三个方法里, "改 trade 表" 和 "改 goods 表"
 * 两句 SQL 必须跑在【同一条连接】上, 才算同一个事务。所以写法是:
 *   1. 本类自己 DBUtil.getConnection() 拿到连接, 然后 setAutoCommit(false)
 *   2. 调 DAO 的"带 Connection 的版本"(TradeDao 和 GoodsDao 都已提供), 这类方法
 *      不自己拿连接、也不关闭连接
 *   3. 本类负责 commit / rollback, 并在 finally 里把连接关掉
 *
 * 注意: 只写 setAutoCommit 是不够的 —— 如果 DAO 各拿各的连接, 事务是"假生效", 白写。
 *
 * 另外三个方法只有一句 SQL, 不需要事务: finish / getActiveByGoods / myTrades
 */
public class TradeService {

    /** 交易数据访问对象 */
    private TradeDao tradeDao = new TradeDaoImpl();
    /** 商品数据访问对象: 交易要连带改商品状态 */
    private GoodsDao goodsDao = new GoodsDaoImpl();

    /**
     * 负责人: 亦妄辰
     * 功能: 创建交易 (商品详情界面 -> 立即购买)
     *       1. 校验商品: 存在、在售、不是自己发的、还没有没结束的交易
     *       2. 【事务】新增交易(未支付) + 商品改成已售出, 两句一起成功或一起失败
     * 参数: goodsId 商品id; buyerId 买家id
     * 返回值: 创建成功的交易, 失败返回 null
     */
    public Trade create(int goodsId, int buyerId) {
        // ---------- 1. 校验(放在事务外, 避免无谓地开事务) ----------
        // 商品要存在, 而且还在卖
        Goods goods = goodsDao.findById(goodsId);
        if (goods == null || goods.getStatus() != GoodsStatus.SALE) {
            return null;
        }
        // 不能买自己发的商品
        if (goods.getSellerId() == buyerId) {
            return null;
        }
        // 这个商品已经有没结束的交易, 说明被别人占了
        if (tradeDao.findActiveByGoodsId(goodsId) != null) {
            return null;
        }

        // 组装交易, 金额和卖家都从商品那边抄过来
        Trade trade = new Trade();
        trade.setGoodsId(goodsId);
        trade.setBuyerId(buyerId);
        trade.setSellerId(goods.getSellerId());
        trade.setAmount(goods.getPrice());
        trade.setStatus(TradeStatus.UNPAID);

        // ---------- 2. 事务: 新增交易 + 商品改已售出 ----------
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);

            int newId = tradeDao.insert(trade, conn);
            if (newId == -1) {
                conn.rollback();
                return null;
            }
            // 商品没改成功也要整笔作废, 否则会留下"有交易但商品还在售"的脏数据
            if (!goodsDao.updateStatus(goodsId, GoodsStatus.SOLD, conn)) {
                conn.rollback();
                return null;
            }

            conn.commit();
            return trade;
        } catch (Exception e) {
            rollbackQuietly(conn);
            e.printStackTrace();
            return null;
        } finally {
            DBUtil.close(null, null, conn);
        }
    }

    /**
     * 负责人: 亦妄辰
     * 功能: 取消交易 (交易改已取消, 商品变回在售)
     *       1. 校验: 是买家/卖家本人, 且交易还没支付
     *       2. 【事务】交易改已取消 + 商品恢复在售, 两句一起成功或一起失败
     * 参数: tradeId 交易id
     * 返回值: 取消成功返回 true, 否则 false
     */
    public boolean cancel(int tradeId) {
        Trade trade = tradeDao.findById(tradeId);
        if (trade == null) {
            return false;
        }
        // 只有买家或卖家本人能取消
        int me = Session.currentUserId();
        if (trade.getBuyerId() != me && trade.getSellerId() != me) {
            return false;
        }
        // 只有还没支付的交易能取消
        if (trade.getStatus() != TradeStatus.UNPAID) {
            return false;
        }

        // 事务外先看商品当前状态: 只有确实"已售出"的才恢复成在售,
        // 别把卖家自己下架(已下架)的商品又改成在售
        Goods goods = goodsDao.findById(trade.getGoodsId());
        boolean needRestore = (goods != null && goods.getStatus() == GoodsStatus.SOLD);

        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);

            if (!tradeDao.updateStatus(tradeId, TradeStatus.CANCELED, conn)) {
                conn.rollback();
                return false;
            }
            if (needRestore && !goodsDao.updateStatus(trade.getGoodsId(), GoodsStatus.SALE, conn)) {
                conn.rollback();
                return false;
            }

            conn.commit();
            return true;
        } catch (Exception e) {
            rollbackQuietly(conn);
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(null, null, conn);
        }
    }

    /**
     * 负责人: 亦妄辰
     * 功能: 确认交易完成 (只有一句 SQL, 不用事务)
     * 参数: tradeId 交易id
     * 返回值: 成功返回 true, 否则 false
     */
    public boolean finish(int tradeId) {
        Trade trade = tradeDao.findById(tradeId);
        if (trade == null || trade.getStatus() != TradeStatus.UNPAID) {
            return false;
        }
        return tradeDao.updateStatus(tradeId, TradeStatus.FINISHED);
    }

    /**
     * 负责人: 亦妄辰
     * 功能: 申请退货 (拓展功能, 只有买家能申请, 商品回到在售)
     *       1. 校验: 是买家本人, 且交易已完成
     *       2. 【事务】交易改退货中 + 商品恢复在售, 两句一起成功或一起失败
     * 参数: tradeId 交易id
     * 返回值: 申请成功返回 true, 否则 false
     */
    public boolean applyRefund(int tradeId) {
        Trade trade = tradeDao.findById(tradeId);
        if (trade == null) {
            return false;
        }
        // 只有买家能申请, 而且交易得是已完成的
        if (trade.getBuyerId() != Session.currentUserId() || trade.getStatus() != TradeStatus.FINISHED) {
            return false;
        }

        // 事务外先看商品当前状态, 理由同 cancel
        Goods goods = goodsDao.findById(trade.getGoodsId());
        boolean needRestore = (goods != null && goods.getStatus() == GoodsStatus.SOLD);

        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);

            if (!tradeDao.updateStatus(tradeId, TradeStatus.REFUNDING, conn)) {
                conn.rollback();
                return false;
            }
            if (needRestore && !goodsDao.updateStatus(trade.getGoodsId(), GoodsStatus.SALE, conn)) {
                conn.rollback();
                return false;
            }

            conn.commit();
            return true;
        } catch (Exception e) {
            rollbackQuietly(conn);
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(null, null, conn);
        }
    }

    /**
     * 负责人: 亦妄辰
     * 功能: 查某个商品当前还没结束的交易 (交易界面打开时用)
     * 参数: goodsId 商品id
     * 返回值: 交易, 没有返回 null
     */
    public Trade getActiveByGoods(int goodsId) {
        return tradeDao.findActiveByGoodsId(goodsId);
    }

    /**
     * 负责人: 亦妄辰
     * 功能: 查我参与的交易(我买的 + 我卖的)
     * 参数: userId 用户id
     * 返回值: 交易列表, 没有返回空集合
     */
    public List<Trade> myTrades(int userId) {
        return tradeDao.findByUserId(userId);
    }

    /**
     * 负责人: 亦妄辰
     * 功能: 回滚事务的小工具。三个事务方法里都要写一遍 try/catch 回滚, 抽出来省事,
     *       也避免在 catch 里再套一层 try 把代码弄乱
     * 参数: conn 要回滚的连接, 可能是 null(还没拿到连接就出错了)
     * 返回值: 无
     */
    private void rollbackQuietly(Connection conn) {
        if (conn == null) {
            return;
        }
        try {
            conn.rollback();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
