package com.turing.flea.service;

import com.turing.flea.common.GoodsStatus;
import com.turing.flea.common.TradeStatus;
import com.turing.flea.dao.GoodsDao;
import com.turing.flea.dao.TradeDao;
import com.turing.flea.dao.impl.GoodsDaoImpl;
import com.turing.flea.dao.impl.TradeDaoImpl;
import com.turing.flea.entity.Goods;
import com.turing.flea.entity.Trade;

import java.util.List;

/**
 * 交易业务层: 创建 / 取消 / 完成 / 退货
 *
 * 负责人: 待分配
 *
 * 最重要的一条: 交易和商品状态必须一起变, 所以要用【事务】, 写法:
 *   Connection conn = null;
 *   try {
 *       conn = DBUtil.getConnection();
 *       conn.setAutoCommit(false);        // 开事务
 *       ... 两句 SQL 都执行 ...
 *       conn.commit();                    // 一起成功
 *   } catch (SQLException e) {
 *       conn.rollback();                  // 一起失败
 *   } finally {
 *       DBUtil.close(null, null, conn);
 *   }
 */
public class TradeService {

    /** 交易数据访问对象 */
    private TradeDao tradeDao = new TradeDaoImpl();
    /** 商品数据访问对象: 交易要连带改商品状态 */
    private GoodsDao goodsDao = new GoodsDaoImpl();

    /**
     * 负责人: 待分配
     * 功能: 创建交易 (商品详情界面 -> 立即购买)
     *       1. 调用 goodsDao.findById(goodsId), 商品不存在或状态不是在售 -> 返回 null
     *          (自己不能买自己的商品, 也要在这里拦掉)
     *       2. 调用 tradeDao.findActiveByGoodsId(goodsId), 已经有没结束的交易 -> 返回 null
     *       3. 开事务: tradeDao.insert(新交易, 状态 UNPAID, 金额=商品价格)
     *                  goodsDao.updateStatus(goodsId, GoodsStatus.SOLD)
     *       4. 两句都成功 -> commit, 返回这条交易; 有异常 -> rollback, 返回 null
     * 参数: goodsId 商品id; buyerId 买家id
     * 返回值: 创建成功的交易, 失败返回 null
     */
    public Trade create(int goodsId, int buyerId) {
        throw new UnsupportedOperationException("待实现: TradeService.create 负责人: 待分配");
    }

    /**
     * 负责人: 待分配
     * 功能: 取消交易
     *       1. 判断是不是这笔交易的买家/卖家本人
     *       2. 开事务: tradeDao.updateStatus(tradeId, TradeStatus.CANCELED)
     *                  goodsDao.updateStatus(商品id, GoodsStatus.SALE)  <- 商品重新变成在售
     *       3. 成功 commit, 失败 rollback
     * 参数: tradeId 交易id
     * 返回值: 取消成功返回 true, 否则 false
     */
    public boolean cancel(int tradeId) {
        throw new UnsupportedOperationException("待实现: TradeService.cancel 负责人: 待分配");
    }

    /**
     * 负责人: 待分配
     * 功能: 确认交易完成 (买卖双方都确认后状态改成已完成)
     *       1. 调用 tradeDao.updateStatus(tradeId, TradeStatus.FINISHED)
     * 参数: tradeId 交易id
     * 返回值: 成功返回 true, 否则 false
     */
    public boolean finish(int tradeId) {
        throw new UnsupportedOperationException("待实现: TradeService.finish 负责人: 待分配");
    }

    /**
     * 负责人: 待分配
     * 功能: 申请退货 (拓展功能)
     *       1. 只有买家能申请, 且交易已经是已完成
     *       2. 开事务: tradeDao.updateStatus(tradeId, TradeStatus.REFUNDING)
     *                  goodsDao.updateStatus(商品id, GoodsStatus.SALE)  <- 商品回到在售
     * 参数: tradeId 交易id
     * 返回值: 申请成功返回 true, 否则 false
     */
    public boolean applyRefund(int tradeId) {
        throw new UnsupportedOperationException("待实现: TradeService.applyRefund 负责人: 待分配");
    }

    /**
     * 负责人: 待分配
     * 功能: 查某个商品当前还没结束的交易 (交易界面打开时用)
     *       1. 调用 tradeDao.findActiveByGoodsId(GoodsId)
     * 参数: goodsId 商品id
     * 返回值: 交易, 没有返回 null
     */
    public Trade getActiveByGoods(int goodsId) {
        throw new UnsupportedOperationException("待实现: TradeService.getActiveByGoods 负责人: 待分配");
    }

    /**
     * 负责人: 待分配
     * 功能: 查我参与的交易(我买的 + 我卖的)
     *       1. 调用 tradeDao.findByUserId(Session.currentUserId())
     * 参数: userId 用户id
     * 返回值: 交易列表, 没有返回空集合
     */
    public List<Trade> myTrades(int userId) {
        throw new UnsupportedOperationException("待实现: TradeService.myTrades 负责人: 待分配");
    }
}