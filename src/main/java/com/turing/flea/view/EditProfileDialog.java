package com.turing.flea.view;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextField;

/**
 * 修改个人信息弹窗
 *
 * 对应需求: 个人中心 -> 个人信息修改: 上传服务端修改信息并保存 (更新 user 表的 nickname / contact)
 *
 * 负责人: 待分配
 */
public class EditProfileDialog extends JDialog {

    /* ------------------------------ 数据的设计 ------------------------------ */
    /** 昵称输入框, 打开时填当前昵称 */
    private JTextField nicknameField;
    /** 联系信息输入框, 打开时填当前联系信息 */
    private JTextField contactField;
    /** 保存按钮 */
    private JButton saveButton;
    /** 取消按钮 */
    private JButton cancelButton;

    /**
     * 负责人: 待分配
     * 功能: 创建修改个人信息弹窗(模态窗口: 不关掉它就不能点主窗口)
     * 参数: owner 父窗口
     * 返回值: 无
     */
    public EditProfileDialog(JFrame owner) {
        super(owner, "修改个人信息", true);
        setSize(380, 240);
        setLocationRelativeTo(owner);
        initView();
    }

    /**
     * 负责人: 待分配
     * 功能: 初始化界面: 摆好两个输入框和按钮, 把 Session.getCurrentUser() 的昵称/联系方式填进去
     * 参数: 无
     * 返回值: 无
     */
    public void initView() {
        // TODO 待实现 (负责人: 待分配)
    }

    /**
     * 负责人: 待分配
     * 功能: 点击【保存】
     *       1. 校验昵称不为空
     *       2. 把当前用户(User 对象)的 nickname / contact 改成输入框里的值
     *       3. 调用 UserService.updateInfo(user), 成功 -> 弹提示, dispose(); 失败 -> 弹提示"修改失败"
     * 参数: 无
     * 返回值: 无
     */
    public void onSaveClick() {
        throw new UnsupportedOperationException("待实现: EditProfileDialog.onSaveClick 负责人: 待分配");
    }
}