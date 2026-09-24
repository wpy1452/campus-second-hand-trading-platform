package com.turing.flea.view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * 我的留言管理界面 (拓展功能)
 *
 * 对应需求: 留言模块 -> 我的留言列表: 展示当前用户所有留言, 可删除
 *
 * 负责人: 待分配
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
        // TODO 待实现 (负责人: 待分配)
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
        throw new UnsupportedOperationException("待实现: MyMessageView.loadMyMessages 负责人: 待分配");
    }

    /**
     * 负责人: 待分配
     * 功能: 点击【删除】: 取选中行的留言id -> 弹框确认 -> GoodsMessageService.delete(id) -> 刷新表格
     * 参数: 无
     * 返回值: 无
     */
    public void onDeleteClick() {
        throw new UnsupportedOperationException("待实现: MyMessageView.onDeleteClick 负责人: 待分配");
    }
}