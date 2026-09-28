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

import java.util.List;

/**
 * 交易业务层: 创建 / 取消 / 完成 / 退货
 * 负责人: 亦妄辰
 *
 * 交易状态和商品状态要一起变:
 *   创建交易 -> 交易未支付 + 商品已售出
 *   取消交易 -> 交易已取消 + 商品变回在售
 *
 * 事务还没做: GoodsDao.updateStatus 没有"传入 Connection"的重载, 它自己拿连接,
 * 两句 SQL 不在同一个事务里, 所以先一步步调, 等补上重载再改成事务。
 */
public class TradeService {

    /** 交易数据访问对象 */
    private TradeDao tradeDao = new TradeDaoImpl();
    /** 商品数据访问对象: 交易要连带改商品状态 */
    private GoodsDao goodsDao = new GoodsDaoImpl();

    /**
     * 负责人: 亦妄辰
     * 功能: 创建交易 (商品详情界面 -> 立即购买)
     * 参数: goodsId 商品id; buyerId 买家id
     * 返回值: 创建成功的交易, 失败返回 null
     */
    public Trade create(int goodsId, int buyerId) {
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

        // 新建交易, 金额和卖家都从商品那边抄过来
        Trade trade = new Trade();
        trade.setGoodsId(goodsId);
        trade.setBuyerId(buyerId);
        trade.setSellerId(goods.getSellerId());
        trade.setAmount(goods.getPrice());
        trade.setStatus(TradeStatus.UNPAID);

        if (tradeDao.insert(trade) == -1) {
            return null;
        }

        // 商品改成已售出
        goodsDao.updateStatus(goodsId, GoodsStatus.SOLD);

        return trade;
    }

    /**
     * 负责人: 亦妄辰
     * 功能: 取消交易 (交易改已取消, 商品变回在售)
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

        if (!tradeDao.updateStatus(tradeId, TradeStatus.CANCELED)) {
            return false;
        }

        // 商品变回在售。只有当前是"已售出"才改, 别把卖家自己下架的商品又改成在售
        Goods goods = goodsDao.findById(trade.getGoodsId());
        if (goods != null && goods.getStatus() == GoodsStatus.SOLD) {
            goodsDao.updateStatus(trade.getGoodsId(), GoodsStatus.SALE);
        }
        return true;
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

        if (!tradeDao.updateStatus(tradeId, TradeStatus.REFUNDING)) {
            return false;
        }

        Goods goods = goodsDao.findById(trade.getGoodsId());
        if (goods != null && goods.getStatus() == GoodsStatus.SOLD) {
            goodsDao.updateStatus(trade.getGoodsId(), GoodsStatus.SALE);
        }
        return true;
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
}
