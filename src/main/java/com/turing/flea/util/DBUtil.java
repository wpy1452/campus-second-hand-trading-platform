package com.turing.flea.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * 数据库工具类 (JDBC)
 *
 * 负责人: 技术官(公共类, 已实现, 全组直接用, 不要各自改)
 * 说明: 数据库账号密码只改 src/main/resources/db.properties.local
 *
 * dao 层的写法(统一模板, 照着写就行):
 *   Connection conn = null; PreparedStatement ps = null; ResultSet rs = null;
 *   try {
 *       conn = DBUtil.getConnection();
 *       ps = conn.prepareStatement("select * from user where account = ?");
 *       ps.setString(1, account);
 *       rs = ps.executeQuery();
 *       ...
 *   } catch (SQLException e) {
 *       e.printStackTrace();
 *   } finally {
 *       DBUtil.close(rs, ps, conn);
 *   }
 */
public class DBUtil {

    /* ------------------------------ 数据的设计 ------------------------------ */
    /** 数据库连接地址, 从 db.properties 的 jdbc.url 读取 */
    private static String url;
    /** 数据库账号, 从 db.properties 的 jdbc.username 读取 */
    private static String username;
    /** 数据库密码, 从 db.properties 的 jdbc.password 读取 */
    private static String password;

    static {
        InputStream in1 = null;
        InputStream in2 = null;
        try {
            in1 = DBUtil.class.getClassLoader().getResourceAsStream("db.properties");
            if (in1 == null) {
                throw new IllegalStateException("找不到 db.properties, 确认它在 src/main/resources 下");
            }

            Properties props = new Properties();
            props.load(in1);

            // db.properties.local 可选: 存在就用它覆盖同名项, 不存在就跳过
            in2 = DBUtil.class.getClassLoader().getResourceAsStream("db.properties.local");
            if (in2 != null) {
                props.load(in2);
            }

            // 加载 MySQL 驱动
            Class.forName(props.getProperty("jdbc.driver"));
            url = props.getProperty("jdbc.url");
            username = props.getProperty("jdbc.username");
            password = props.getProperty("jdbc.password");
        } catch (Exception e) {
            throw new RuntimeException("初始化数据库配置失败: " + e.getMessage(), e);
        } finally {
            closeQuietly(in1);
            closeQuietly(in2);
        }
    }

    /** 工具类不允许 new */
    private DBUtil() {
    }

    /**
     * 负责人: 技术官
     * 功能: 获取一个数据库连接
     * 参数: 无
     * 返回值: Connection 连接对象, 用完必须用 close(...) 还回去
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    /**
     * 负责人: 技术官
     * 功能: 关闭数据库资源, 三个参数都可以传 null
     * 参数: rs 结果集 / st 语句对象(PreparedStatement 也是 Statement) / conn 连接
     * 返回值: 无
     */
    public static void close(ResultSet rs, Statement st, Connection conn) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 负责人: 技术官
     * 功能: 关闭一个可能为 null 的输入流, 关失败只打印不抛异常
     * 参数: in 输入流, 可以为 null
     * 返回值: 无
     */
    private static void closeQuietly(InputStream in) {
        if (in != null) {
            try {
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}