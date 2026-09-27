package com.turing.flea.view;

import com.turing.flea.common.Session;
import com.turing.flea.entity.User;
import com.turing.flea.service.UserService;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

/**
 * 修改个人信息弹窗
 *
 * 对应需求: 个人中心 -> 个人信息修改: 上传服务端修改信息并保存 (更新 user 表的 nickname / contact)
 *
 * 负责人: 小天
 */
public class EditProfileDialog extends JDialog {

    /*
     * ------------------------------ 界面样式（统一字体/留白） ------------------------------
     */
    /** 弹窗统一字体，与交易界面保持一致 */
    private static final Font BASE_FONT = new Font("微软雅黑", Font.PLAIN, 13);

    /* ------------------------------ 数据的设计 ------------------------------ */
    /** 昵称输入框, 打开时填当前昵称 */
    private JTextField nicknameField;
    /** 联系信息输入框, 打开时填当前联系信息 */
    private JTextField contactField;
    /** 保存按钮 */
    private JButton saveButton;
    /** 取消按钮 */
    private JButton cancelButton;

    /** 用户业务层: 修改信息由它处理, 界面不写 SQL */
    private UserService userService = new UserService();

    /**
     * 负责人: 小天
     * 功能: 创建修改个人信息弹窗(模态窗口: 不关掉它就不能点主窗口)
     * 参数: owner 父窗口
     * 返回值: 无
     */
    public EditProfileDialog(JFrame owner) {
        super(owner, "修改个人信息", true);
        setSize(430, 280);
        setLocationRelativeTo(owner);
        initView();
    }

    /**
     * 负责人: 小天
     * 功能: 初始化界面: 摆好两个输入框和按钮, 把 Session.getCurrentUser() 的昵称/联系方式填进去
     * 参数: 无
     * 返回值: 无
     */
    public void initView() {
        User currentUser = Session.getCurrentUser();
        String nickname = currentUser == null ? "" : currentUser.getNickname();
        String contact = currentUser == null ? "" : currentUser.getContact();

        nicknameField = new JTextField(nickname, 18);
        contactField = new JTextField(contact, 18);
        Insets fieldMargin = new Insets(5, 6, 5, 6);
        nicknameField.setMargin(fieldMargin);
        contactField.setMargin(fieldMargin);
        nicknameField.setFont(BASE_FONT);
        contactField.setFont(BASE_FONT);

        JLabel nicknameLabel = new JLabel("昵称:");
        JLabel contactLabel = new JLabel("联系信息:");
        nicknameLabel.setFont(BASE_FONT);
        contactLabel.setFont(BASE_FONT);

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        // 行间距和列间距加大，避免拥挤
        gbc.insets = new Insets(14, 12, 14, 12);

        // 标签右对齐，冒号在一条竖线上
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(nicknameLabel, gbc);
        gbc.gridy = 1;
        formPanel.add(contactLabel, gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1;
        gbc.gridy = 0;
        formPanel.add(nicknameField, gbc);
        gbc.gridy = 1;
        formPanel.add(contactField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 28, 8));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(2, 0, 14, 0));
        saveButton = new JButton("保存");
        cancelButton = new JButton("取消");
        saveButton.setFont(BASE_FONT);
        cancelButton.setFont(BASE_FONT);
        Insets buttonMargin = new Insets(7, 22, 7, 22);
        saveButton.setMargin(buttonMargin);
        cancelButton.setMargin(buttonMargin);
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        saveButton.addActionListener(e -> saveButtonAction());
        cancelButton.addActionListener(e -> dispose());
    }

    /**
     * 负责人: 小天
     * 功能: 点击【保存】
     * 1. 校验昵称不为空
     * 2. 把当前用户(User 对象)的 nickname / contact 改成输入框里的值
     * 3. 调用 UserService.updateInfo(user), 成功 -> 弹提示, dispose(); 失败 -> 弹提示"修改失败"
     * 参数: 无
     * 返回值: 无
     */

    public void onSaveClick() {
        throw new UnsupportedOperationException("待实现: EditProfileDialog.onSaveClick 负责人: 待分配");
    }
    
    public void saveButtonAction() {
        User user = Session.getCurrentUser();
        if (user == null) {
            JOptionPane.showMessageDialog(this, "登录已失效, 请重新登录");
            return;
        }

        String nickname = nicknameField.getText().trim();
        String contact = contactField.getText().trim();
        if (nickname.isEmpty()) {
            JOptionPane.showMessageDialog(this, "昵称不能为空");
            return;
        }

        user.setNickname(nickname);
        user.setContact(contact);

        boolean success = userService.updateInfo(user);
        if (success) {
            JOptionPane.showMessageDialog(this, "修改成功");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "修改失败");
        }
    }
}
