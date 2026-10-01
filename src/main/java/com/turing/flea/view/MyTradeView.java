package com.turing.flea.view;

import com.turing.flea.common.Session;
import com.turing.flea.common.TradeStatus;
import com.turing.flea.entity.Trade;
import com.turing.flea.service.TradeService;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 我的交易界面：列出当前用户参与的交易(我买的 + 我卖的)。
 *
 * 说明: 商品一旦下单会变成"已售出"从首页消失, 卖家能在个人中心"我的商品"里看到,
 *       买家没有入口, 所以这里给买卖双方一个统一的交易列表。
 *
 * 本类只做界面展示, 数据一律通过 TradeService.myTrades 获取, 不写 SQL。
 */
public class MyTradeView extends JFrame {

    /* ------------------------------ 数据的设计 ------------------------------ */
    /** 交易表格: 展示交易id/商品标题/金额/状态/角色/时间 */
    private JTable tradeTable;
    /** 表格模型 */
    private DefaultTableModel tableModel;
    /** 交易业务层 */
    private final TradeService tradeService = new TradeService();
    /** 与表格行一一对应的交易对象, 双击时按行号取用 */
    private List<Trade> trades = new ArrayList<>();
    /** 创建时间格式化 */
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public MyTradeView() {
        super("我的交易");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(780, 440);
        setLocationRelativeTo(null);
        initView();
    }

    /**
     * 功能: 初始化界面并加载交易列表
     */
    public void initView() {
        String[] columns = {"交易ID", "商品标题", "金额(元)", "状态", "我的角色", "时间"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tradeTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tradeTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("我买入 / 卖出的交易"));

        JButton refreshButton = new JButton("刷新");
        JButton closeButton = new JButton("关闭");
        refreshButton.addActionListener(e -> loadTrades());
        closeButton.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 8));
        buttonPanel.add(refreshButton);
        buttonPanel.add(closeButton);

        setLayout(new BorderLayout(8, 8));
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        tradeTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    onTradeRowClick();
                }
            }
        });

        loadTrades();
    }

    /**
     * 功能: 加载当前用户参与的交易, 逐行填充表格
     *       我买的和卖的都查出来, 用"我的角色"列区分
     */
    public void loadTrades() {
        int me = Session.currentUserId();
        trades = tradeService.myTrades(me);

        tableModel.setRowCount(0);
        if (trades == null) {
            trades = new ArrayList<>();
            return;
        }
        for (Trade t : trades) {
            String title = t.getGoodsTitle() != null ? t.getGoodsTitle() : "商品#" + t.getGoodsId();
            String role = t.getBuyerId() == me ? "买家" : "卖家";
            tableModel.addRow(new Object[]{
                    t.getId(),
                    title,
                    String.format("%.2f", t.getAmount() / 100.0),
                    t.getStatus() == null ? "未知" : t.getStatus().getDesc(),
                    role,
                    t.getCreateTime() == null ? "" : dateFormat.format(t.getCreateTime())
            });
        }
    }

    /**
     * 功能: 双击一行交易
     *       未支付 / 退货中 -> 打开交易界面继续操作(取消/确认/退货)
     *       其它已结束的状态 -> 只提示, 不进交易界面
     *       (TradeView 打开时只认未结束的交易, 已完成的进去会误显示成"等待支付")
     */
    public void onTradeRowClick() {
        int row = tradeTable.getSelectedRow();
        if (row < 0 || row >= trades.size()) {
            return;
        }
        Trade trade = trades.get(row);
        TradeStatus status = trade.getStatus();
        if (status == TradeStatus.UNPAID || status == TradeStatus.REFUNDING) {
            new TradeView(trade.getGoodsId()).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this,
                    "该交易已" + (status == null ? "结束" : status.getDesc()) + "，无需继续操作",
                    "提示", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
