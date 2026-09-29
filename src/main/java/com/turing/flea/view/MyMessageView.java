package com.turing.flea.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import com.turing.flea.common.Session;
import com.turing.flea.service.GoodsMessageService;
import com.turing.flea.entity.GoodsMessage;


/**
 * 我的留言管理界面 (拓展功能)
 *
 * 对应需求: 留言模块 -> 我的留言列表: 展示当前用户所有留言, 可删除
 *
 * 负责人: 云
 */
public class MyMessageView extends JFrame {

    /* ------------------------------ 数据的设计 ------------------------------ */
    /** 我的留言表格 */
    private JTable messageTable;
    /** 表格模型, 表头: 留言id / 商品标题 / 留言内容 / 留言时间 */
    private DefaultTableModel tableModel;
    /** 删除按钮 */
    private JButton deleteButton;
    /** 刷新按钮 */
    private JButton refreshButton;

    /**
     * 负责人: 待分配
     * 功能: 创建我的留言窗口, 初始化界面并加载留言
     * 参数: 无
     * 返回值: 无
     */
    public MyMessageView() {
        super("我的留言");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 450);
        setLocationRelativeTo(null);
        initView();
    }

    /**
     * 负责人: 待分配
     * 功能: 初始化界面, 并调用 loadMyMessages()
     * 参数: 无
     * 返回值: 无
     */
    public void initView() {
        String[] columns = {"留言ID", "商品标题", "留言内容", "留言时间"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        messageTable = new JTable(tableModel);

        deleteButton = new JButton("删除选中留言");
        refreshButton = new JButton("刷新");

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onDeleteClick();
            }
        });
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadMyMessages();
            }
        });

        JScrollPane scrollPane = new JScrollPane(messageTable);
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 8));
        bottom.add(refreshButton);
        bottom.add(deleteButton);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        loadMyMessages();

        // TODO 待实现 (负责人: 云)
    }

    /**
     * 负责人: 待分配
     * 功能: 加载我的全部留言
     *       1. 调用 GoodsMessageService.myMessages(Session.currentUserId())
     *       2. 清空表格后逐行 addRow: 留言id / 商品标题 / 内容 / 留言时间
     * 参数: 无
     * 返回值: 无
     */
    public void loadMyMessages() {
        GoodsMessageService service = new GoodsMessageService();
        List<GoodsMessage> list = service.myMessages(Session.currentUserId());

        if (list == null){
            System.out.println("loadMyMessages:返回留言列表为空");
            return;
        }

        tableModel.setRowCount(0);

        for (int i = 0; i < list.size(); i++) {
            GoodsMessage m = list.get(i);
            tableModel.addRow(new Object[]{
                    m.getId(),
                    m.getGoodsTitle(),
                    m.getContent(),
                    m.getCreateTime()
            });
        }
    }

    /**
     * 负责人: 待分配
     * 功能: 点击【删除】: 取选中行的留言id -> 弹框确认 -> GoodsMessageService.delete(id) -> 刷新表格
     * 参数: 无
     * 返回值: 无
     */
    public void onDeleteClick() {
        int row = messageTable.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "请先选择一条留言");
            return;
        }
        int messageId = Integer.parseInt(messageTable.getValueAt(row, 0).toString());
        int confirm = JOptionPane.showConfirmDialog(
                this, "确定删除这条留言吗？", "删除确认", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }
        GoodsMessageService service = new GoodsMessageService();
        boolean success = service.delete(messageId);
        if (success) {
            JOptionPane.showMessageDialog(this, "删除成功");
            loadMyMessages();
        } else {
            JOptionPane.showMessageDialog(this, "删除失败，请稍后重试");
        }
    }

}