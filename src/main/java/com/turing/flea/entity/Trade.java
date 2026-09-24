package com.turing.flea.entity;

import com.turing.flea.common.TradeStatus;

import java.util.Date;

/**
 * 交易 (对应 trade 表)
 *
 * 数据的设计:
 *   id         交易id, 主键, 数据库自增
 *   goodsId    商品id -> goods.id
 *   buyerId    买家id -> user.id
 *   sellerId   卖家id -> user.id, 从商品的 sellerId 抄过来(方便按人查交易)
 *   amount     交易金额, 单位【分】, 创建交易时从 goods.price 抄过来
 *   status     交易状态, 用 TradeStatus 枚举
 *   createTime 创建时间
 *   updateTime 状态变更时间
 *
 * 下面的 goodsTitle 不是数据库字段, 交易界面/我的交易列表要显示买的是什么, 由查询时填上
 *
 * 交易和商品状态的联动 (核心业务规则, 写代码时别忘):
 *   创建交易 -> trade 新增一条(未支付), goods.status 改成 已售出
 *   取消交易 -> trade 状态改成 已取消, goods.status 恢复 在售
 *
 * 负责人: 待分配
 */
public class Trade {

    /** 交易id, 数据库自增 */
    private int id;
    /** 商品id -> goods.id */
    private int goodsId;
    /** 买家id -> user.id */
    private int buyerId;
    /** 卖家id -> user.id */
    private int sellerId;
    /** 交易金额, 单位: 分 */
    private int amount;
    /** 交易状态: 未支付 / 已完成 / 已取消 / 退货中 / 已退货 */
    private TradeStatus status;
    /** 创建时间 */
    private Date createTime;
    /** 状态变更时间 */
    private Date updateTime;

    /* -------------------- 以下是"非数据库字段", 只是展示用 -------------------- */
    /** 商品标题, 连表查询时填进来 */
    private String goodsTitle;

    /* ------------------- getter / setter (已写好, 直接调用) ------------------- */

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getGoodsId() { return goodsId; }

    public void setGoodsId(int goodsId) { this.goodsId = goodsId; }

    public int getBuyerId() { return buyerId; }

    public void setBuyerId(int buyerId) { this.buyerId = buyerId; }

    public int getSellerId() { return sellerId; }

    public void setSellerId(int sellerId) { this.sellerId = sellerId; }

    public int getAmount() { return amount; }

    public void setAmount(int amount) { this.amount = amount; }

    public TradeStatus getStatus() { return status; }

    public void setStatus(TradeStatus status) { this.status = status; }

    public Date getCreateTime() { return createTime; }

    public void setCreateTime(Date createTime) { this.createTime = createTime; }

    public Date getUpdateTime() { return updateTime; }

    public void setUpdateTime(Date updateTime) { this.updateTime = updateTime; }

    public String getGoodsTitle() { return goodsTitle; }

    public void setGoodsTitle(String goodsTitle) { this.goodsTitle = goodsTitle; }
}