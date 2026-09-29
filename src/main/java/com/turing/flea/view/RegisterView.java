package com.turing.flea.view;

import com.turing.flea.entity.User;
import com.turing.flea.service.UserService;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * 注册界面
 *
 * 对应需求: 账号 / 密码 / 昵称 / 联系信息 / 注册按钮 / 返回登录按钮
 *   注册: 校验账号重复 -> 重复提示"账号已存在"; 不重复则封装 User 存入 user 表, 成功跳登录
 *   返回登录: 点击返回登录界面
 *
 * 负责人: 待分配
 *
 * 分层说明: 格式校验(账号长度<=10、不能有特殊字符)在这里做;
 *          "账号是否已存在"调用 UserService.register / existsAccount 判断
 */
public class RegisterView extends JFrame {

    /* ------------------------------ 数据的设计 ------------------------------ */
    /** 账号输入框 */
    private JTextField accountField = new JTextField();
    /** 密码输入框 */
    private JPasswordField passwordField = new JPasswordField();
    /** 昵称输入框 */
    private JTextField nicknameField = new JTextField();
    /** 联系信息输入框 (手机号/微信/QQ) */
    private JTextField contactField = new JTextField();
    /** 注册按钮 */
    private JButton registerButton = new JButton("注册");
    /** 返回登录按钮 */
    private JButton backButton = new JButton("返回登录");

    /**
     * 负责人: 待分配
     * 功能: 创建注册窗口并初始化界面
     * 参数: 无
     * 返回值: 无
     */
    public RegisterView() {
        super("校园二手交易平台 - 注册");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(420, 320);
        setLocationRelativeTo(null);
        initView();
    }

    /**
     * 负责人: 待分配
     * 功能: 初始化界面: 摆好 5 个输入框和 2 个按钮, 给按钮绑定点击事件
     * 参数: 无
     * 返回值: 无
     */
    public void initView() {
        //使用绝对布局
        setLayout(null);

        //标签文本
        JLabel accountLabel = new JLabel("账号:");
        accountLabel.setBounds(55, 35, 65, 30);
        add(accountLabel);

        JLabel passwordLabel = new JLabel("密码:");
        passwordLabel.setBounds(55, 77, 650, 30);
        add(passwordLabel);

        JLabel nicknameLabel = new JLabel("昵称:");
        nicknameLabel.setBounds(55, 119, 65, 30);
        add(nicknameLabel);

        JLabel contactLabel = new JLabel("联系信息:");
        contactLabel.setBounds(55, 161, 65, 30);
        add(contactLabel);

        //输入框
        accountField.setBounds(130, 35, 205, 30);
        add(accountField);

        passwordField.setBounds(130, 77, 205, 30);
        add(passwordField);

        nicknameField.setBounds(130, 119, 205, 30);
        add(nicknameField);

        contactField.setBounds(130, 161, 205, 30);
        add(contactField);

        //注册按钮
        registerButton.setBounds(95, 220, 100, 35);
        add(registerButton);

        //返回登录按钮
        backButton.setBounds(225, 220, 100, 35);
        add(backButton);

        //添加按钮监听
        registerButton.addActionListener(e -> onRegisterClick());
        backButton.addActionListener(e -> onBackClick());
    }

    /**
     * 负责人: 待分配
     * 功能: 点击【注册】按钮
     *       1. 读 4 个输入框的内容, 做格式校验(不为空 / 账号长度<=10 / 不能有特殊字符), 不合格就弹框提示
     *       2. 封装 User 对象, 调用 UserService.register(user)
     *       3. 返回 false -> 弹框提示"账号已存在"
     *       4. 返回 true -> 弹框提示"注册成功", 然后 new LoginView().setVisible(true); dispose()
     * 参数: 无
     * 返回值: 无
     */
    public void onRegisterClick() {
        //获取各输入框字符串
        String account = accountField.getText();
        String password = new String(passwordField.getPassword());
        String nickname = nicknameField.getText();
        String contact = contactField.getText();

        //非空校验
        if (account == null || password == null || nickname == null || contact == null
                || account.isBlank() || password.isBlank() || nickname.isBlank() || contact.isBlank()) {
            JOptionPane.showMessageDialog(this, "所有输入项均不能为空");
            return;
        }

        //去除首尾空格后进行格式校验
        account = account.trim();
        password = password.trim();
        nickname = nickname.trim();
        contact = contact.trim();

        //账号长度校验 (长度 <= 10)
        if (account.length() > 10) {
            JOptionPane.showMessageDialog(this, "账号长度不能超过10位");
            return;
        }

        //账号不能含有特殊字符校验 (只允许字母和数字)
        if (!account.matches("^[a-zA-Z0-9]+$")) {
            JOptionPane.showMessageDialog(this, "账号不能包含特殊字符");
            return;
        }

        //封装 User 对象
        User user = new User();
        user.setAccount(account);
        user.setPassword(password);
        user.setNickname(nickname);
        user.setContact(contact);

        //调用业务层注册
        UserService userService = new UserService();
        boolean success = userService.register(user);

        //注册失败 (账号已存在)
        if (!success) {
            JOptionPane.showMessageDialog(this, "账号已存在");
            return;
        }

        //注册成功提示，跳回登录界面
        JOptionPane.showMessageDialog(this, "注册成功");

        //打开登录界面
        new LoginView().setVisible(true);

        //关闭当前窗口
        dispose();
    }

    /**
     * 负责人: 待分配
     * 功能: 点击【返回登录】按钮: new LoginView().setVisible(true); dispose()
     * 参数: 无
     * 返回值: 无
     */
    public void onBackClick() {
        //打开登录界面
        new LoginView().setVisible(true);

        //关闭当前窗口
        dispose();
    }
}