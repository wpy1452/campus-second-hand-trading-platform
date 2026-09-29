package com.turing.flea.view;

import com.turing.flea.common.Session;
import com.turing.flea.service.UserService;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

/**
 * 修改密码弹窗
 *
 * 对应需求: 个人中心 -> 密码修改: 校验原密码后更新 (更新 user 表的 password)
 *
 * 负责人: 小天
 */
public class ChangePasswordDialog extends JDialog {

    /*
     * ------------------------------ 界面样式（统一字体/留白） ------------------------------
     */
    /** 弹窗统一字体，与交易界面保持一致 */
    private static final Font BASE_FONT = new Font("微软雅黑", Font.PLAIN, 13);

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

    /** 用户业务层: 密码是否正确、更新密码都由它处理, 界面不写 SQL */
    private UserService userService = new UserService();

    /**
     * 负责人: 小天
     * 功能: 创建修改密码弹窗(模态)
     * 参数: owner 父窗口
     * 返回值: 无
     */
    public ChangePasswordDialog(JFrame owner) {
        super(owner, "修改密码", true);
        setSize(430, 300);
        setLocationRelativeTo(owner);
        initView();
    }

    /**
     * 负责人: 小天
     * 功能: 初始化界面: 摆好三个密码框和按钮
     * 参数: 无
     * 返回值: 无
     */
    public void initView() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        // 行间距和列间距加大，避免拥挤
        gbc.insets = new Insets(12, 12, 12, 12);

        oldPasswordField = new JPasswordField(18);
        newPasswordField = new JPasswordField(18);
        confirmPasswordField = new JPasswordField(18);
        Insets fieldMargin = new Insets(5, 6, 5, 6);
        oldPasswordField.setMargin(fieldMargin);
        newPasswordField.setMargin(fieldMargin);
        confirmPasswordField.setMargin(fieldMargin);
        oldPasswordField.setFont(BASE_FONT);
        newPasswordField.setFont(BASE_FONT);
        confirmPasswordField.setFont(BASE_FONT);

        JLabel oldLabel = new JLabel("原密码:");
        JLabel newLabel = new JLabel("新密码:");
        JLabel confirmLabel = new JLabel("确认密码:");
        oldLabel.setFont(BASE_FONT);
        newLabel.setFont(BASE_FONT);
        confirmLabel.setFont(BASE_FONT);

        // 标签右对齐，冒号在一条竖线上
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(oldLabel, gbc);
        gbc.gridy = 1;
        formPanel.add(newLabel, gbc);
        gbc.gridy = 2;
        formPanel.add(confirmLabel, gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1;
        gbc.gridy = 0;
        formPanel.add(oldPasswordField, gbc);
        gbc.gridy = 1;
        formPanel.add(newPasswordField, gbc);
        gbc.gridy = 2;
        formPanel.add(confirmPasswordField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 28, 8));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(2, 0, 14, 0));
        okButton = new JButton("确定");
        cancelButton = new JButton("取消");
        okButton.setFont(BASE_FONT);
        cancelButton.setFont(BASE_FONT);
        Insets buttonMargin = new Insets(7, 22, 7, 22);
        okButton.setMargin(buttonMargin);
        cancelButton.setMargin(buttonMargin);
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        okButton.addActionListener(e -> confirmButtonAction());
        cancelButton.addActionListener(e -> dispose());
    }

    /**
     * 负责人: 小天
     * 功能: 点击【确定】
     * 1. 校验: 三个框都不为空; 两次新密码输入一致(不一致弹提示"两次输入的密码不一致")
     * 2. 调用 UserService.changePassword(Session.currentUserId(), 原密码, 新密码)
     * 3. true -> 弹提示"密码修改成功", dispose(); false -> 弹提示"原密码错误"
     * 参数: 无
     * 返回值: 无
     */
    public void confirmButtonAction() {
        String oldPassword = new String(oldPasswordField.getPassword());
        String newPassword = new String(newPasswordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请填写完整信息");
            return;
        }
        if (!newPassword.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "两次输入的密码不一致");
            return;
        }

        boolean success = userService.changePassword(
                Session.currentUserId(), oldPassword, newPassword);
        if (success) {
            JOptionPane.showMessageDialog(this, "密码修改成功");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "原密码错误");
        }
    }
}
