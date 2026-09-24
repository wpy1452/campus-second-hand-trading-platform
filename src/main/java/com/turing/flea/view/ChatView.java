package com.turing.flea.view;

import com.turing.flea.entity.Chat;
import com.turing.flea.entity.User;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;

/**
 * 聊天窗口界面
 *
 * 对应需求: 聊天对象 / 聊天记录区 / 消息输入框 / 发送按钮
 *   发送消息: 存入 chat 表, 追加显示
 *   接收消息: Swing Timer 每3秒轮询未读记录, 追加并标记已读
 *   消息提示: 聊天框打开弹新消息, 未打开弹提示
 *
 * 负责人: 待分配
 *
 * 技术方案: 没有 Socket, 就是"每3秒去数据库查一次有没有人给我发消息"
 *   窗口打开 -> startTimer(); 窗口关闭 -> stopTimer() (在 windowClosing 里调)
 */
public class ChatView extends JFrame {

    /* ------------------------------ 数据的设计 ------------------------------ */
    /** 聊天对象(对方), 由构造方法传进来, 发送消息时要它的id */
    private User other;
    /** 聊天记录区: 每行显示成"我: 内容" / "对方昵称: 内容" */
    private JTextArea chatArea;
    /** 消息输入框 */
    private JTextField inputField;
    /** 发送按钮 */
    private JButton sendButton;
    /** 轮询定时器: 每3秒查一次未读消息 */
    private Timer timer;

    /**
     * 负责人: 待分配
     * 功能: 创建聊天窗口, 加载聊天记录并开始轮询
     * 参数: other 聊天对象
     * 返回值: 无
     */
    public ChatView(User other) {
        super("聊天");
        this.other = other;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 550);
        setLocationRelativeTo(null);
        initView();
    }

    /**
     * 负责人: 待分配
     * 功能: 初始化界面: 摆好记录区/输入框/发送按钮, 标题显示"和 对方昵称 聊天",
     *       调用 loadHistory(), 再 startTimer()
     * 参数: 无
     * 返回值: 无
     */
    public void initView() {
        // TODO 待实现 (负责人: 待分配)
    }

    /**
     * 负责人: 待分配
     * 功能: 加载历史聊天记录
     *       1. 调用 ChatService.history(Session.currentUserId(), other.getId())
     *       2. 逐条调用 appendChat(chat) 显示到记录区
     * 参数: 无
     * 返回值: 无
     */
    public void loadHistory() {
        throw new UnsupportedOperationException("待实现: ChatView.loadHistory 负责人: 待分配");
    }

    /**
     * 负责人: 待分配
     * 功能: 启动定时器: timer = new Timer(3000, ...), 每3秒:
     *       1. 调用 ChatService.pollUnread(Session.currentUserId())
     *       2. 有未读消息 -> 逐条 appendChat(...) 追加显示
     *       3. 追加完调用 ChatService.markRead(Session.currentUserId(), other.getId())
     *          (一定要标记已读, 否则下一次轮询又会把同样的消息查出来重复显示)
     * 参数: 无
     * 返回值: 无
     */
    public void startTimer() {
        throw new UnsupportedOperationException("待实现: ChatView.startTimer 负责人: 待分配");
    }

    /**
     * 负责人: 待分配
     * 功能: 停止定时器 (窗口关闭时调用, 否则窗口关了 Timer 还在跑)
     * 参数: 无
     * 返回值: 无
     */
    public void stopTimer() {
        throw new UnsupportedOperationException("待实现: ChatView.stopTimer 负责人: 待分配");
    }

    /**
     * 负责人: 待分配
     * 功能: 点击【发送】按钮
     *       1. 读输入框内容, 为空不处理
     *       2. 调用 ChatService.send(Session.currentUserId(), other.getId(), content)
     *       3. 成功 -> 追加显示成"我: 内容", 清空输入框
     * 参数: 无
     * 返回值: 无
     */
    public void onSendClick() {
        throw new UnsupportedOperationException("待实现: ChatView.onSendClick 负责人: 待分配");
    }

    /**
     * 负责人: 待分配
     * 功能: 把一条消息追加到聊天记录区
     *       1. chat.getSendId() == Session.currentUserId() -> 显示"我: 内容"
     *          否则显示"对方昵称: 内容"
     *       2. 追加后让记录区自动滚到最下面 (chatArea.setCaretPosition(chatArea.getDocument().getLength()))
     * 参数: chat 要显示的消息
     * 返回值: 无
     */
    public void appendChat(Chat chat) {
        throw new UnsupportedOperationException("待实现: ChatView.appendChat 负责人: 待分配");
    }
}