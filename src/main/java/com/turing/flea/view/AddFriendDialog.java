package com.turing.flea.view;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextField;

/**
 * 添加好友弹窗 (拓展功能)
 *
 * 对应需求: 用户模块 -> 好友添加: 判断对方账号是否在列表, 不在则添加
 *
 * 负责人: 待分配
 */
public class AddFriendDialog extends JDialog {

    /* ------------------------------ 数据的设计 ------------------------------ */
    /** 对方账号输入框 */
    private JTextField accountField;
    /** 添加按钮 */
    private JButton addButton;
    /** 取消按钮 */
    private JButton cancelButton;

    /**
     * 负责人: 待分配
     * 功能: 创建添加好友弹窗(模态)
     * 参数: owner 父窗口
     * 返回值: 无
     */
    public AddFriendDialog(JFrame owner) {
        super(owner, "添加好友", true);
        setSize(360, 180);
        setLocationRelativeTo(owner);
        initView();
    }

    /**
     * 负责人: 待分配
     * 功能: 初始化界面: 摆好账号输入框和按钮
     * 参数: 无
     * 返回值: 无
     */
    public void initView() {
        // TODO 待实现 (负责人: 待分配)
    }

    /**
     * 负责人: 待分配
     * 功能: 点击【添加】
     *       1. 校验账号不为空
     *       2. 调用 FriendService.addFriend(Session.currentUserId(), account)
     *       3. true -> 弹提示"添加成功", dispose(); false -> 弹提示"对方账号不存在或已经是好友"
     * 参数: 无
     * 返回值: 无
     */
    public void onAddClick() {
        throw new UnsupportedOperationException("待实现: AddFriendDialog.onAddClick 负责人: 待分配");
    }
}