package com.turing.flea.view;

import com.turing.flea.common.Session;
import com.turing.flea.entity.User;
import com.turing.flea.service.UserService;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * 登录界面
 *
 * 对应需求: 账号输入框 / 密码输入框 / 登录按钮 / 注册账号按钮
 *   登录: 录入账号密码 -> 与 user 表比对 -> 成功进主界面, 失败提示"账号或密码错误"
 *   注册账号: 点击进入注册界面
 *
 * 负责人: 待分配
 *
 * 分层说明: 这个类只负责"展示 + 收用户输入 + 弹提示";
 *          账号密码正确与否由 UserService.login 判断, 这里不要写 SQL
 */
public class LoginView extends JFrame {

    /* ------------------------------ 数据的设计 ------------------------------ */
    /** 账号输入框 */
    private JTextField accountField;
    /** 密码输入框 (JPasswordField 输入的内容显示成圆点) */
    private JPasswordField passwordField;
    /** 登录按钮 */
    private JButton loginButton;
    /** 注册账号按钮: 点击跳到注册界面 */
    private JButton registerButton;

    /**
     * 负责人: 待分配
     * 功能: 创建登录窗口并初始化界面
     * 参数: 无
     * 返回值: 无
     */
    public LoginView() {
        super("校园二手交易平台 - 登录");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 260);
        setLocationRelativeTo(null);
        initView();
    }

    /**
     * 负责人: 待分配
     * 功能: 初始化界面: 摆好"账号输入框/密码输入框/登录按钮/注册账号按钮", 给按钮绑定点击事件
     * 参数: 无
     * 返回值: 无
     */
    public void initView() {
        //使用绝对布局
        setLayout(null);

        // 账号密码文本
        JLabel accountLabel = new JLabel("账号:");
        accountLabel.setBounds(50, 40, 60, 30);
        add(accountLabel);

        JLabel passwordLabel = new JLabel("密码:");
        passwordLabel.setBounds(50, 85, 60, 30);
        add(passwordLabel);

        // 账号密码输入框
        accountField = new JTextField();
        accountField.setBounds(120, 40, 200, 30);
        add(accountField);

        passwordField = new JPasswordField();
        passwordField.setBounds(120, 85, 200, 30);
        add(passwordField);

        // 登录按钮
        loginButton = new JButton("登录");
        loginButton.setBounds(80, 150, 100, 35);
        add(loginButton);

        // 注册按钮
        registerButton = new JButton("注册账号");
        registerButton.setBounds(220, 150, 100, 35);
        add(registerButton);

        // 添加按钮监听
        loginButton.addActionListener(e -> onLoginClick());

        registerButton.addActionListener(e -> onRegisterClick());
    }

    /**
     * 负责人: 待分配
     * 功能: 点击【登录】按钮
     *       1. 从输入框读账号、密码 (view 只做非空校验: 为空就提示"账号和密码不能为空")
     *       2. 调用 UserService.login(account, password)
     *       3. 返回 null -> 弹框提示"账号或密码错误"
     *       4. 返回了用户 -> Session.setCurrentUser(user); dispose() 关掉本窗口; new MainView().setVisible(true)
     * 参数: 无
     * 返回值: 无
     */
    public void onLoginClick() {

        //获取账号密码字符串
        String account = accountField.getText();
        String password = new String(passwordField.getPassword());

        //账号密码判空
        if(account ==null || password == null || account.isBlank() || password.isBlank()){
            JOptionPane.showMessageDialog(this, "账号和密码不能为空");
            return;
        }

        //调用业务层
        UserService userService = new UserService();
        User user = userService.login(account,password);

        //登录失败
        if(user == null) {
            JOptionPane.showMessageDialog(this, "账号或密码错误");
            return;
        }

        // 登录成功保存用户
        Session.setCurrentUser(user);

        // 关闭登录窗口
        dispose();

        // 打开主界面
        new MainView().setVisible(true);
    }

    /**
     * 负责人: 待分配
     * 功能: 点击【注册账号】按钮: 打开注册界面, 关掉自己
     *       1. new RegisterView().setVisible(true); dispose();
     * 参数: 无
     * 返回值: 无
     */
    public void onRegisterClick() {

        // 打开注册页面
        new RegisterView().setVisible(true);

        // 关闭当前窗口
        dispose();
    }
}