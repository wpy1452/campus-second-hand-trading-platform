package com.turing.flea.view;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPasswordField;

/**
 * 修改密码弹窗
 *
 * 对应需求: 个人中心 -> 密码修改: 校验原密码后更新 (更新 user 表的 password)
 *
 * 负责人: 待分配
 */
public class ChangePasswordDialog extends JDialog {

    /* ------------------------------ 数据的设计 ------------------------------ */
    /** 原密码输入框 */
    private JPasswordField oldPasswordField;
    /** 新密码输入框 */
    private JPasswordField newPasswordField;
    /** 确认新密码输入框 */
    private JPasswordField confirmPasswordField;
    /** 确定按钮 */
    private JButton okButton;
    /** 取消按钮 */
    private JButton cancelButton;

    /**
     * 负责人: 待分配
     * 功能: 创建修改密码弹窗(模态)
     * 参数: owner 父窗口
     * 返回值: 无
     */
    public ChangePasswordDialog(JFrame owner) {
        super(owner, "修改密码", true);
        setSize(380, 260);
        setLocationRelativeTo(owner);
        initView();
    }

    /**
     * 负责人: 待分配
     * 功能: 初始化界面: 摆好三个密码框和按钮
     * 参数: 无
     * 返回值: 无
     */
    public void initView() {
        // TODO 待实现 (负责人: 待分配)
    }

    /**
     * 负责人: 待分配
     * 功能: 点击【确定】
     *       1. 校验: 三个框都不为空; 两次新密码输入一致(不一致弹提示"两次输入的密码不一致")
     *       2. 调用 UserService.changePassword(Session.currentUserId(), 原密码, 新密码)
     *       3. true -> 弹提示"密码修改成功", dispose(); false -> 弹提示"原密码错误"
     * 参数: 无
     * 返回值: 无
     */
    public void onOkClick() {
        throw new UnsupportedOperationException("待实现: ChangePasswordDialog.onOkClick 负责人: 待分配");
    }
}