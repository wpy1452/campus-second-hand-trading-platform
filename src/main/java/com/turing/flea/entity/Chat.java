package com.turing.flea.entity;

import java.util.Date;

/**
 * 聊天消息 (对应 chat 表)
 *
 * 数据的设计:
 *   id         消息id, 主键, 数据库自增
 *   sendId     发送者 -> user.id
 *   recvId     接收者 -> user.id
 *   content    消息内容
 *   read      是否已读: false 未读 / true 已读
 *              聊天窗口每3秒轮询(recvId = 我 且 read = false)的消息, 显示完调"标记已读"改成 true
 *   createTime 发送时间, 聊天记录区排序用它
 *
 * 下面的 otherNickname 不是数据库字段:
 *   聊天窗口标题要显示"和谁聊", 由 ChatService 填上
 *
 * 负责人: 待分配
 */
public class Chat {

    /** 消息id, 数据库自增 */
    private int id;
    /** 发送者id -> user.id */
    private int sendId;
    /** 接收者id -> user.id */
    private int recvId;
    /** 消息内容 */
    private String content;
    /** 是否已读: false 未读, true 已读 (对应数据库 is_read 的 0/1) */
    private boolean read;
    /** 发送时间 */
    private Date createTime;

    /* -------------------- 以下是"非数据库字段", 只是展示用 -------------------- */
    /** 聊天对象的昵称, 连表查询时填进来 */
    private String otherNickname;

    /* ------------------- getter / setter (已写好, 直接调用) ------------------- */

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getSendId() { return sendId; }

    public void setSendId(int sendId) { this.sendId = sendId; }

    public int getRecvId() { return recvId; }

    public void setRecvId(int recvId) { this.recvId = recvId; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

    public boolean isRead() { return read; }

    public void setRead(boolean read) { this.read = read; }

    public Date getCreateTime() { return createTime; }

    public void setCreateTime(Date createTime) { this.createTime = createTime; }

    public String getOtherNickname() { return otherNickname; }

    public void setOtherNickname(String otherNickname) { this.otherNickname = otherNickname; }
}