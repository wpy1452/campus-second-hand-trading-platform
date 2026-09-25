package com.turing.flea.common;

/**
 * 交易状态 (对应 trade 表的 status 字段)
 *
 * 数据的设计:
 *   code -> 存进数据库的值 (int)
 *   desc -> 交易界面上显示的状态文字
 *
 * 负责人: 久违
 */
public enum TradeStatus {

    /** 1 未支付: 点击"立即购买"创建交易后的默认状态 */
    UNPAID(1, "未支付"),
    /** 2 已完成: 买卖双方确认完成 */
    FINISHED(2, "已完成"),
    /** 3 已取消: 买家/卖家点"取消交易", 商品状态要恢复成在售 */
    CANCELED(3, "已取消"),
    /** 4 退货中: 拓展功能, 买家发起退货 */
    REFUNDING(4, "退货中"),
    /** 5 已退货: 拓展功能, 退货完成 */
    REFUNDED(5, "已退货");

    /** 存数据库用的值 */
    private final int code;
    /** 界面上显示的文字 */
    private final String desc;

    TradeStatus(int code, String desc) {
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
     * 返回值: 例如 "未支付"
     */
    public String getDesc() {
        return desc;
    }

    /**
     * 负责人: 待分配
     * 功能: 把数据库读出来的数字转成枚举
     * 参数: code 数据库里的状态值
     * 返回值: 对应的枚举; 数值非法时返回 null
     */
    public static TradeStatus of(int code) {
        for (TradeStatus s : values()) {
            if (s.code == code) {
                return s;
            }
        }
        return null;
    }
}