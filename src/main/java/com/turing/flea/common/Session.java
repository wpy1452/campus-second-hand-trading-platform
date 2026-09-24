package com.turing.flea.common;

import com.turing.flea.entity.User;

/**
 * 全局登录状态 (就是架构里的"全局变量")
 *
 * 数据的设计:
 *   currentUser: 当前登录的用户。登录成功后 setCurrentUser(user), 退出登录时 logout()
 *                所有界面需要"当前是谁"的时候都从这里取, 不要在界面里自己存一份
 *
 * 注意: 整个程序只有这一处全局状态; service / dao 层不要自己再存一份当前用户
 *
 * 负责人: 技术官(公共类, 已实现, 直接调用)
 */
public class Session {

    /** 当前登录的用户, null 表示还没有人登录 */
    private static User currentUser;

    /** 工具类不允许 new */
    private Session() {
    }

    /**
     * 负责人: 技术官
     * 功能: 取当前登录的用户
     * 参数: 无
     * 返回值: 当前用户, 未登录时返回 null
     */
    public static User getCurrentUser() {
        return currentUser;
    }

    /**
     * 负责人: 技术官
     * 功能: 记录当前登录的用户 (登录成功后由 LoginView 调用)
     * 参数: user 登录成功的用户
     * 返回值: 无
     */
    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    /**
     * 负责人: 技术官
     * 功能: 取当前用户id, 省得每次判断 null
     * 参数: 无
     * 返回值: 当前用户id; 未登录返回 -1
     */
    public static int currentUserId() {
        return currentUser == null ? -1 : currentUser.getId();
    }

    /**
     * 负责人: 技术官
     * 功能: 退出登录, 把当前用户清空
     * 参数: 无
     * 返回值: 无
     */
    public static void logout() {
        currentUser = null;
    }
}