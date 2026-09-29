package com.turing.flea.view;

import com.turing.flea.common.Session;
import com.turing.flea.common.TradeStatus;
import com.turing.flea.entity.Trade;
import com.turing.flea.service.TradeService;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

/**
 * 交易界面
 *
 * 对应需求: 商品信息 / 交易金额 / 状态(未支付/已完成/已取消/退货) / 确认交易 / 取消交易
 * 创建交易: 点击立即购买 -> 创建 trade 记录, 商品状态改成已售出
 * 取消交易: 更新交易状态为已取消, 商品状态恢复在售
 * 交易状态推进: 买卖双方确认完成 -> 更新为已完成, 支持退货
 *
 * 负责人: 小天
 */
public class TradeView extends JFrame {

    /*
     * ------------------------------ 界面样式（统一字体/留白） ------------------------------
     */
    /** 界面统一字体: 中文环境下微软雅黑显示更清晰 */
    private static final Font BASE_FONT = new Font("微软雅黑", Font.PLAIN, 13);
    /** 需要强调的文字(状态行) */
    private static final Font BOLD_FONT = new Font("微软雅黑", Font.BOLD, 13);

    /* ------------------------------ 数据的设计 ------------------------------ */
    /** 商品id, 由构造方法传进来 */
    private int goodsId;
    /** 商品信息(标题 + 价格) */
    private JLabel goodsLabel;
    /** 交易金额, 显示成"￥150.00" */
    private JLabel amountLabel;
    /** 交易状态: 未支付 / 已完成 / 已取消 / 退货中 / 已退货 */
    private JLabel statusLabel;
    /** 支付按钮: 创建交易(下单支付) */
    private JButton payButton;
    /** 确认交易(完成)按钮 */
    private JButton confirmButton;
    /** 取消交易按钮 */
    private JButton cancelButton;
    /** 申请退货按钮 (拓展功能) */
    private JButton refundButton;

    /** 当前这笔交易(含交易id), 打开时由 loadTrade 查出来, 按钮操作都靠它; 还没支付时为 null */
    private Trade currentTrade;
    /** 交易业务层: 支付/取消/完成/退货只调它, 界面不写 SQL */
    private TradeService tradeService = new TradeService();

    /**
     * 负责人: 小天
     * 功能: 创建交易窗口, 初始化界面, 如果这个商品还没有交易就创建一条
     * 参数: goodsId 要交易的商品id
     * 返回值: 无
     */
    public TradeView(int goodsId) {
        super("交易");
        this.goodsId = goodsId;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(560, 420);
        setLocationRelativeTo(null);
        initView();
    }

    /**
     * 负责人: 小天
     * 功能: 初始化界面, 并调用 loadTrade()
     * 参数: 无
     * 返回值: 无
     */
    public void initView() {
        /* 中部: 交易信息区(商品/金额/状态) */
        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBorder(BorderFactory.createTitledBorder("交易信息"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(14, 18, 14, 18);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        goodsLabel = new JLabel("商品:");
        amountLabel = new JLabel("交易金额:");
        statusLabel = new JLabel("当前状态:");
        goodsLabel.setFont(BASE_FONT);
        amountLabel.setFont(BASE_FONT);
        statusLabel.setFont(BOLD_FONT);

        gbc.gridx = 0;
        gbc.gridy = 0;
        infoPanel.add(goodsLabel, gbc);
        gbc.gridy = 1;
        infoPanel.add(amountLabel, gbc);
        gbc.gridy = 2;
        infoPanel.add(statusLabel, gbc);

        /* 底部: 操作按钮 */
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 14));
        payButton = new JButton("支付");
        confirmButton = new JButton("确认交易");
        cancelButton = new JButton("取消交易");
        refundButton = new JButton("申请退货");
        Insets buttonMargin = new Insets(7, 16, 7, 16);
        payButton.setMargin(buttonMargin);
        confirmButton.setMargin(buttonMargin);
        cancelButton.setMargin(buttonMargin);
        refundButton.setMargin(buttonMargin);
        payButton.setFont(BASE_FONT);
        confirmButton.setFont(BASE_FONT);
        cancelButton.setFont(BASE_FONT);
        refundButton.setFont(BASE_FONT);
        buttonPanel.add(payButton);
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(refundButton);

        add(infoPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        payButton.addActionListener(e -> payButtonAction());
        confirmButton.addActionListener(e -> completeButtonAction());
        cancelButton.addActionListener(e -> cancelButtonAction());
        refundButton.addActionListener(e -> refundButtonAction());

        loadTrade();
    }

    /**
     * 负责人: 小天
     * 功能: 加载交易信息
     * 1. 调用 TradeService.getActiveByGoods(goodsId): 有没结束的交易就直接显示
     * 2. 没有 -> 说明是刚点"立即购买"进来的, 调用 TradeService.create(goodsId,
     * Session.currentUserId()) 创建
     * 3. 把商品标题/金额/状态显示出来, 并根据状态决定按钮能不能点:
     * 未支付 -> 可以取消/确认完成
     * 已完成 -> 可以申请退货(拓展)
     * 已取消 -> 按钮全部置灰
     * 参数: 无
     * 返回值: 无
     */
    public void loadTrade() {
        Trade trade = tradeService.getActiveByGoods(goodsId);
        currentTrade = trade;

        if (trade == null) {
            // 还没有交易: 等买家点【支付】创建(对应注释第 2 步的 create, 由 payButtonAction 执行)
            goodsLabel.setText("商品: 商品#" + goodsId);
            amountLabel.setText("交易金额: 支付后显示");
            statusLabel.setText("当前状态: 等待支付");
            switchButtons(true, false, false, false);
            return;
        }

        renderTrade();
    }

    /**
     * 负责人: 小天
     * 功能: 点击【支付】
     * 1. 弹框确认"确认购买该商品并支付?"
     * 2. 确认 -> 调用 TradeService.create(goodsId, Session.currentUserId()): 创建交易,
     * 商品变已售出
     * 3. 成功 -> 显示交易信息; 失败 -> 弹提示(商品不存在/已售出/已下架, 不能购买自己的商品)
     * 参数: 无
     * 返回值: 无
     */
    public void payButtonAction() {
        int choice = JOptionPane.showConfirmDialog(this,
                "确认购买该商品并支付?", "支付确认", JOptionPane.YES_NO_OPTION);
        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        Trade trade = tradeService.create(goodsId, Session.currentUserId());
        if (trade != null) {
            currentTrade = trade;
            renderTrade();
            JOptionPane.showMessageDialog(this, "下单并支付成功");
        } else {
            JOptionPane.showMessageDialog(this,
                    "下单失败：商品不存在、已售出或已下架，也不能购买自己的商品");
        }
    }

    /**
     * 负责人: 小天
     * 功能: 点击【确认交易】: 调用 TradeService.finish(tradeId), 成功则刷新状态
     * 参数: 无
     * 返回值: 无
     */
    public void completeButtonAction() {
        if (currentTrade == null) {
            return;
        }

        int choice = JOptionPane.showConfirmDialog(this,
                "确认完成这笔交易吗?", "完成交易", JOptionPane.YES_NO_OPTION);
        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        boolean success = tradeService.finish(currentTrade.getId());
        if (success) {
            currentTrade.setStatus(TradeStatus.FINISHED);
            renderTrade();
            JOptionPane.showMessageDialog(this, "交易已完成");
        } else {
            JOptionPane.showMessageDialog(this, "操作失败");
        }
    }

    /**
     * 负责人: 小天
     * 功能: 点击【取消交易】
     * 1. 弹框确认"确定取消这笔交易吗?"
     * 2. 确认 -> 调用 TradeService.cancel(tradeId): 交易变已取消, 商品恢复在售
     * 3. 成功 -> 刷新界面并弹提示
     * 参数: 无
     * 返回值: 无
     */
    public void cancelButtonAction() {
        if (currentTrade == null) {
            return;
        }

        int choice = JOptionPane.showConfirmDialog(this,
                "确定取消这笔交易吗?", "取消交易", JOptionPane.YES_NO_OPTION);
        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        boolean success = tradeService.cancel(currentTrade.getId());
        if (success) {
            currentTrade.setStatus(TradeStatus.CANCELED);
            renderTrade();
            JOptionPane.showMessageDialog(this, "交易已取消");
        } else {
            JOptionPane.showMessageDialog(this, "取消失败");
        }
    }

    /**
     * 负责人: 小天
     * 功能: 点击【申请退货】(拓展): 调用 TradeService.applyRefund(tradeId), 成功则刷新状态
     * 参数: 无
     * 返回值: 无
     */
    public void refundButtonAction() {
        if (currentTrade == null) {
            return;
        }

        int choice = JOptionPane.showConfirmDialog(this,
                "确定对该笔交易申请退货吗?", "申请退货", JOptionPane.YES_NO_OPTION);
        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        boolean success = tradeService.applyRefund(currentTrade.getId());
        if (success) {
            currentTrade.setStatus(TradeStatus.REFUNDING);
            renderTrade();
            JOptionPane.showMessageDialog(this, "退货申请已提交");
        } else {
            JOptionPane.showMessageDialog(this, "申请退货失败");
        }
    }

    /* ------------------------------ 新增的私有辅助函数 ------------------------------ */

    /**
     * 功能: 把 currentTrade 的商品标题/金额/状态显示到三个标签上, 并按状态切换按钮
     */
    private void renderTrade() {
        Trade trade = currentTrade;

        String title = trade.getGoodsTitle() != null
                ? trade.getGoodsTitle()
                : "商品#" + trade.getGoodsId();
        goodsLabel.setText("商品: " + title);
        amountLabel.setText("交易金额: " + formatAmount(trade.getAmount()));
        statusLabel.setText("当前状态: " + trade.getStatus().getDesc());

        boolean canPay = false;
        boolean canConfirm = false;
        boolean canCancel = false;
        boolean canRefund = false;
        if (trade.getStatus() == TradeStatus.UNPAID) {
            // 未支付 -> 可以取消/确认完成
            canCancel = true;
            canConfirm = true;
        } else if (trade.getStatus() == TradeStatus.FINISHED) {
            // 已完成 -> 可以申请退货(拓展)
            canRefund = true;
        }
        // 已取消/退货中/已退货 -> 按钮全部置灰
        switchButtons(canPay, canConfirm, canCancel, canRefund);
    }

    /**
     * 功能: 统一设置四个按钮能不能点
     * 参数: pay/confirm/cancel/refund 分别对应支付/确认交易/取消交易/申请退货按钮
     */
    private void switchButtons(boolean pay, boolean confirm, boolean cancel, boolean refund) {
        payButton.setEnabled(pay);
        confirmButton.setEnabled(confirm);
        cancelButton.setEnabled(cancel);
        refundButton.setEnabled(refund);
    }

    /**
     * 功能: 把"分"格式化成界面金额, 例如 15000 -> "￥150.00"
     */
    private String formatAmount(int amount) {
        return String.format("￥%.2f", amount / 100.0);
    }
}
