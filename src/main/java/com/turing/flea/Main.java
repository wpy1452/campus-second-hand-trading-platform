package com.turing.flea;

import com.turing.flea.view.LoginView;

import javax.swing.SwingUtilities;

/**
 * 程序入口
 *
 * 负责人: 技术官
 * 界面跳转的起点: Main -> 登录界面 LoginView -> (登录成功) 主窗口 MainView -> ...
 *
 * 怎么运行: 运行这个类的 main 方法; 打包后 java -jar campus-flea-market.jar
 */
public class Main {

    /**
     * 负责人: 技术官
     * 功能: 启动程序, 打开登录界面
     * 参数: args 命令行参数, 用不到
     * 返回值: 无
     */
    public static void main(String[] args) {
        // Swing 的界面要在事件分发线程里创建, 固定这么写
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginView().setVisible(true);
            }
        });
    }
}