package com.turing.flea.entity;

import com.turing.flea.common.GoodsStatus;

import java.util.Date;

/**
 * 商品 (对应 goods 表)
 *
 * 数据的设计:
 *   id          商品id, 主键, 数据库自增
 *   title       标题, 搜索时按它模糊匹配
 *   description 描述
 *   price       价格, 单位是【分】: 9.90元 存 990。
 *               显示: price / 100.0 保留两位小数; 输入: 元 * 100 取整。
 *               不要用 float/double 存金额, 会有精度误差
 *   imagePath   图片路径, 界面上用 new ImageIcon(imagePath) 显示空则不显示图片
 *   sellerId    卖家id -> user.id, 发布时填 Session.currentUserId()
 *   status      商品状态, 用 GoodsStatus 枚举, 不要到处写 1 2 3
 *   createTime  发布时间
 *   updateTime  最后修改时间
 *
 * 下面的 sellerNickname 不是数据库字段, 是"连表查出来的"额外数据:
 *   商品详情页/我的商品管理要显示卖家昵称, 由 SQL 的 join 或 GoodsService 填上, 没有值时为 null
 *
 * 负责人: 待分配
 */
public class Goods {

    /** 商品id, 数据库自增 */
    private int id;
    /** 标题 */
    private String title;
    /** 描述 */
    private String description;
    /** 价格, 单位: 分 (990 表示 9.90 元) */
    private int price;
    /** 图片路径, 界面用 new ImageIcon(imagePath) 显示 */
    private String imagePath;
    /** 卖家id -> user.id */
    private int sellerId;
    /** 商品状态: 在售 / 已售出 / 已下架 / 待处理 */
    private GoodsStatus status;
    /** 发布时间 */
    private Date createTime;
    /** 最后修改时间 */
    private Date updateTime;

    /* -------------------- 以下是"非数据库字段", 只是展示用 -------------------- */
    /** 卖家昵称, 连表查询时填进来, 数据库里没有这一列 */
    private String sellerNickname;

    /* ------------------- getter / setter (已写好, 直接调用) ------------------- */

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public int getPrice() { return price; }

    public void setPrice(int price) { this.price = price; }

    public String getImagePath() { return imagePath; }

    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public int getSellerId() { return sellerId; }

    public void setSellerId(int sellerId) { this.sellerId = sellerId; }

    public GoodsStatus getStatus() { return status; }

    public void setStatus(GoodsStatus status) { this.status = status; }

    public Date getCreateTime() { return createTime; }

    public void setCreateTime(Date createTime) { this.createTime = createTime; }

    public Date getUpdateTime() { return updateTime; }

    public void setUpdateTime(Date updateTime) { this.updateTime = updateTime; }

    public String getSellerNickname() { return sellerNickname; }

    public void setSellerNickname(String sellerNickname) { this.sellerNickname = sellerNickname; }
}