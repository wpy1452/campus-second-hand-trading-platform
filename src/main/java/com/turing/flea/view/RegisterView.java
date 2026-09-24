package com.turing.flea.view;

import javax.swing.JButton;
import javax.swing.JFrame;
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
    private JTextField accountField;
    /** 密码输入框 */
    private JPasswordField passwordField;
    /** 昵称输入框 */
    private JTextField nicknameField;
    /** 联系信息输入框 (手机号/微信/QQ) */
    private JTextField contactField;
    /** 注册按钮 */
    private JButton registerButton;
    /** 返回登录按钮 */
    private JButton backButton;

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
        // TODO 待实现 (负责人: 待分配)
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
        throw new UnsupportedOperationException("待实现: RegisterView.onRegisterClick 负责人: 待分配");
    }

    /**
     * 负责人: 待分配
     * 功能: 点击【返回登录】按钮: new LoginView().setVisible(true); dispose()
     * 参数: 无
     * 返回值: 无
     */
    public void onBackClick() {
        throw new UnsupportedOperationException("待实现: RegisterView.onBackClick 负责人: 待分配");
    }
}