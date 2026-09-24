package com.turing.flea.entity;

import java.util.Date;

/**
 * 用户 (对应 user 表)
 *
 * 数据的设计:
 *   id        用户id, 主键, 数据库自增
 *   account   账号, 唯一, 登录时用它; 要求长度<=10 且不能有特殊字符(注册/登录界面校验)
 *   password  密码, 教学项目明文存
 *   nickname  昵称, 商品详情页的"卖家昵称"、聊天窗口的"聊天对象"显示的是它
 *   contact   联系信息(手机号/微信/QQ), 点"联系卖家"时展示给买家
 *   createTime 注册时间
 *
 * 负责人: 待分配
 */
public class User {

    /** 用户id, 数据库自增, 新增时不用填 */
    private int id;
    /** 账号, 唯一, 长度<=10 */
    private String account;
    /** 密码 */
    private String password;
    /** 昵称 */
    private String nickname;
    /** 联系信息: 手机号/微信/QQ */
    private String contact;
    /** 注册时间 */
    private Date createTime;

    /* ------------------- getter / setter (已写好, 直接调用) ------------------- */

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getAccount() { return account; }

    public void setAccount(String account) { this.account = account; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getNickname() { return nickname; }

    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getContact() { return contact; }

    public void setContact(String contact) { this.contact = contact; }

    public Date getCreateTime() { return createTime; }

    public void setCreateTime(Date createTime) { this.createTime = createTime; }
}