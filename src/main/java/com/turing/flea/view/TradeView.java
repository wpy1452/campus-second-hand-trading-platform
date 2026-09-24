package com.turing.flea.view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * 交易界面
 *
 * 对应需求: 商品信息 / 交易金额 / 状态(未支付/已完成/已取消/退货) / 确认交易 / 取消交易
 *   创建交易: 点击立即购买 -> 创建 trade 记录, 商品状态改成已售出
 *   取消交易: 更新交易状态为已取消, 商品状态恢复在售
 *   交易状态推进: 买卖双方确认完成 -> 更新为已完成, 支持退货
 *
 * 负责人: 待分配
 */
public class TradeView extends JFrame {

    /* ------------------------------ 数据的设计 ------------------------------ */
    /** 商品id, 由构造方法传进来 */
    private int goodsId;
    /** 商品信息(标题 + 价格) */
    private JLabel goodsLabel;
    /** 交易金额, 显示成"￥150.00" */
    private JLabel amountLabel;
    /** 交易状态: 未支付 / 已完成 / 已取消 / 退货中 / 已退货 */
    private JLabel statusLabel;
    /** 确认交易(完成)按钮 */
    private JButton confirmButton;
    /** 取消交易按钮 */
    private JButton cancelButton;
    /** 申请退货按钮 (拓展功能) */
    private JButton refundButton;

    /**
     * 负责人: 待分配
     * 功能: 创建交易窗口, 初始化界面, 如果这个商品还没有交易就创建一条
     * 参数: goodsId 要交易的商品id
     * 返回值: 无
     */
    public TradeView(int goodsId) {
        super("交易");
        this.goodsId = goodsId;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(520, 400);
        setLocationRelativeTo(null);
        initView();
    }

    /**
     * 负责人: 待分配
     * 功能: 初始化界面, 并调用 loadTrade()
     * 参数: 无
     * 返回值: 无
     */
    public void initView() {
        // TODO 待实现 (负责人: 待分配)
    }

    /**
     * 负责人: 待分配
     * 功能: 加载交易信息
     *       1. 调用 TradeService.getActiveByGoods(goodsId): 有没结束的交易就直接显示
     *       2. 没有 -> 说明是刚点"立即购买"进来的, 调用 TradeService.create(goodsId, Session.currentUserId()) 创建
     *       3. 把商品标题/金额/状态显示出来, 并根据状态决定按钮能不能点:
     *          未支付 -> 可以取消/确认完成
     *          已完成 -> 可以申请退货(拓展)
     *          已取消 -> 按钮全部置灰
     * 参数: 无
     * 返回值: 无
     */
    public void loadTrade() {
        throw new UnsupportedOperationException("待实现: TradeView.loadTrade 负责人: 待分配");
    }

    /**
     * 负责人: 待分配
     * 功能: 点击【确认交易】: 调用 TradeService.finish(tradeId), 成功则刷新状态
     * 参数: 无
     * 返回值: 无
     */
    public void onConfirmClick() {
        throw new UnsupportedOperationException("待实现: TradeView.onConfirmClick 负责人: 待分配");
    }

    /**
     * 负责人: 待分配
     * 功能: 点击【取消交易】
     *       1. 弹框确认"确定取消这笔交易吗?"
     *       2. 确认 -> 调用 TradeService.cancel(tradeId): 交易变已取消, 商品恢复在售
     *       3. 成功 -> 刷新界面并弹提示
     * 参数: 无
     * 返回值: 无
     */
    public void onCancelClick() {
        throw new UnsupportedOperationException("待实现: TradeView.onCancelClick 负责人: 待分配");
    }

    /**
     * 负责人: 待分配
     * 功能: 点击【申请退货】(拓展): 调用 TradeService.applyRefund(tradeId), 成功则刷新状态
     * 参数: 无
     * 返回值: 无
     */
    public void onRefundClick() {
        throw new UnsupportedOperationException("待实现: TradeView.onRefundClick 负责人: 待分配");
    }
}