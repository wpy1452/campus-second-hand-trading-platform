package com.turing.flea.entity;

import java.util.Date;

/**
 * 商品留言 (对应 goods_message 表)
 *
 * 数据的设计:
 *   id         留言id, 主键, 数据库自增
 *   goodsId    留言属于哪个商品 -> goods.id
 *   userId     留言人 -> user.id
 *   content    留言内容
 *   createTime 留言时间
 *
 * 下面两个不是数据库字段, 是列表展示用的额外数据:
 *   userNickname  留言人昵称 (商品详情页的留言区要显示"谁留的言")
 *   goodsTitle    商品标题   (我的留言管理里显示"这条留言是留在哪个商品上的")
 *   由 SQL 的 join 或 GoodsMessageService 填上
 *
 * 负责人: 待分配
 */
public class GoodsMessage {

    /** 留言id, 数据库自增 */
    private int id;
    /** 商品id -> goods.id */
    private int goodsId;
    /** 留言人id -> user.id */
    private int userId;
    /** 留言内容 */
    private String content;
    /** 留言时间 */
    private Date createTime;

    /* -------------------- 以下是"非数据库字段", 只是展示用 -------------------- */
    /** 留言人昵称, 连表查询时填进来 */
    private String userNickname;
    /** 商品标题, 连表查询时填进来 */
    private String goodsTitle;

    /* ------------------- getter / setter (已写好, 直接调用) ------------------- */

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getGoodsId() { return goodsId; }

    public void setGoodsId(int goodsId) { this.goodsId = goodsId; }

    public int getUserId() { return userId; }

    public void setUserId(int userId) { this.userId = userId; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

    public Date getCreateTime() { return createTime; }

    public void setCreateTime(Date createTime) { this.createTime = createTime; }

    public String getUserNickname() { return userNickname; }

    public void setUserNickname(String userNickname) { this.userNickname = userNickname; }

    public String getGoodsTitle() { return goodsTitle; }

    public void setGoodsTitle(String goodsTitle) { this.goodsTitle = goodsTitle; }
}