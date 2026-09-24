package com.turing.flea.dao;

import com.turing.flea.common.TradeStatus;
import com.turing.flea.entity.Trade;

import java.util.List;

/**
 * 交易表 trade 的数据访问接口 (数据访问层 dao)
 *
 * 说明: 创建交易时要同时改商品状态, 两句 SQL 要放在同一个事务里, 见 TradeService.create 的注释。
 *
 * 负责人: 待分配
 */
public interface TradeDao {

    /**
     * 负责人: 待分配
     * 功能: 新增一条交易记录
     *       SQL: insert into trade(goods_id, buyer_id, seller_id, amount, status) values(?,?,?,?,?)
     * 参数: trade 要新增的交易(status 传 TradeStatus.UNPAID, amount 从商品价格抄过来)
     * 返回值: 新增交易的id; 失败返回 -1
     */
    int insert(Trade trade);

    /**
     * 负责人: 待分配
     * 功能: 修改交易状态 (取消/完成/退货都调它)
     *       SQL: update trade set status = ? where id = ?
     * 参数: tradeId 交易id; status 目标状态
     * 返回值: 修改成功返回 true, 否则 false
     */
    boolean updateStatus(int tradeId, TradeStatus status);

    /**
     * 负责人: 待分配
     * 功能: 按id查交易
     *       SQL: select t.*, g.title from trade t join goods g on t.goods_id = g.id where t.id = ?
     * 参数: tradeId 交易id
     * 返回值: 交易, 没有返回 null
     */
    Trade findById(int tradeId);

    /**
     * 负责人: 待分配
     * 功能: 查某个商品"当前还没结束"的那条交易 (判断商品有没有被别人买走)
     *       SQL: select t.*, g.title from trade t join goods g on t.goods_id = g.id
     *            where t.goods_id = ? and t.status in (1, 4) order by t.id desc limit 1
     * 参数: goodsId 商品id
     * 返回值: 交易, 没有返回 null
     */
    Trade findActiveByGoodsId(int goodsId);

    /**
     * 负责人: 待分配
     * 功能: 查"我参与的交易"(我买我的+我卖我的) —— 交易界面/我的交易列表
     *       SQL: select t.*, g.title from trade t join goods g on t.goods_id = g.id
     *            where t.buyer_id = ? or t.seller_id = ? order by t.create_time desc
     * 参数: userId 我的用户id
     * 返回值: 交易列表, 没有返回空集合
     */
    List<Trade> findByUserId(int userId);
}