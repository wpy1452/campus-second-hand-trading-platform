package com.turing.flea.common;

/**
 * 商品状态 (对应 goods 表的 status 字段)
 *
 * 数据的设计:
 *   code -> 存进数据库的值 (int)
 *   desc -> 显示在界面上的文字
 *   写数据库用 getCode(), 界面显示用 getDesc()
 *
 * 负责人: 久违
 */
public enum GoodsStatus {

    /** 1 在售: 主窗口商品列表只查这个状态的商品 */
    SALE(1, "在售"),
    /** 2 已售出: 创建交易时把商品改成这个状态 */
    SOLD(2, "已售出"),
    /** 3 已下架: 卖家在我的商品管理里下架 */
    OFF(3, "已下架"),
    /** 4 待处理/草稿: 拓展功能(草稿箱), 核心流程用不到 */
    DRAFT(4, "待处理");

    /** 存数据库用的值 */
    private final int code;
    /** 界面上显示的文字 */
    private final String desc;

    GoodsStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 负责人: 待分配
     * 功能: 取存数据库用的状态值
     * 参数: 无
     * 返回值: 状态值, 例如 1
     */
    public int getCode() {
        return code;
    }

    /**
     * 负责人: 待分配
     * 功能: 取界面上显示的中文
     * 参数: 无
     * 返回值: 例如 "在售"
     */
    public String getDesc() {
        return desc;
    }

    /**
     * 负责人: 待分配
     * 功能: 把数据库读出来的数字转成枚举, 界面/业务里统一用枚举判断, 不要到处写数字
     * 参数: code 数据库里的状态值
     * 返回值: 对应的枚举; 数值非法时返回 null
     */
    public static GoodsStatus of(int code) {
        for (GoodsStatus s : values()) {
            if (s.code == code) {
                return s;
            }
        }
        return null;
    }
}