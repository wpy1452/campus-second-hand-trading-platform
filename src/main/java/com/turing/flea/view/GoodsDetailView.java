package com.turing.flea.view;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * 商品详情界面
 *
 * 对应需求: 标题 / 描述 / 价格 / 图片 / 卖家昵称 / 状态 / 留言区 / 留言输入框 / 联系卖家按钮 / 立即购买按钮
 *   查看留言: 加载该商品的留言列表      商品留言: 封装 GoodsMessage 插入 goods_message 表, 刷新留言区
 *   联系卖家: 进入聊天窗口            立即购买: 进入交易界面
 *
 * 负责人: 待分配
 */
public class GoodsDetailView extends JFrame {

    /* ------------------------------ 数据的设计 ------------------------------ */
    /** 当前查看的商品id, 由构造方法传进来, 后面的按钮都靠它干活 */
    private int goodsId;
    /** 商品标题 */
    private JLabel titleLabel;
    /** 价格, 显示成 "￥150.00" (数据库里存的是分) */
    private JLabel priceLabel;
    /** 商品状态: 在售/已售出/已下架 */
    private JLabel statusLabel;
    /** 卖家昵称 */
    private JLabel sellerLabel;
    /** 商品图片, 用 new ImageIcon(商品的imagePath) 显示; 路径为空就不显示 */
    private JLabel imageLabel;
    /** 商品描述 */
    private JTextArea descriptionArea;
    /** 留言区: 每条留言显示成"昵称: 内容" */
    private JList<String> messageList;
    /** 留言区的数据模型 */
    private DefaultListModel<String> messageListModel;
    /** 留言输入框 */
    private JTextField messageField;
    /** 留言按钮 */
    private JButton messageButton;
    /** 联系卖家按钮 */
    private JButton contactButton;
    /** 立即购买按钮 */
    private JButton buyButton;

    /**
     * 负责人: 待分配
     * 功能: 创建商品详情窗口, 初始化界面并加载这个商品的详情和留言
     * 参数: goodsId 要点开看的商品id
     * 返回值: 无
     */
    public GoodsDetailView(int goodsId) {
        super("商品详情");
        this.goodsId = goodsId;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        initView();
    }

    /**
     * 负责人: 待分配
     * 功能: 初始化界面, 并依次调用 loadGoods() 和 loadMessages()
     * 参数: 无
     * 返回值: 无
     */
    public void initView() {
        // TODO 待实现 (负责人: 待分配)
    }

    /**
     * 负责人: 待分配
     * 功能: 加载商品详情
     *       1. 调用 GoodsService.detail(goodsId)
     *       2. 把 标题/描述/价格/状态/卖家昵称 填到对应控件上; imagePath 不为空时用它 new ImageIcon 显示图片
     *       3. 商品不是"在售"时把【立即购买】按钮置灰 (setEnabled(false))
     * 参数: 无
     * 返回值: 无
     */
    public void loadGoods() {
        throw new UnsupportedOperationException("待实现: GoodsDetailView.loadGoods 负责人: 待分配");
    }

    /**
     * 负责人: 待分配
     * 功能: 加载留言区
     *       1. 调用 GoodsMessageService.listByGoods(goodsId)
     *       2. messageListModel.clear() 后逐条 addElement("昵称: 内容")
     * 参数: 无
     * 返回值: 无
     */
    public void loadMessages() {
        throw new UnsupportedOperationException("待实现: GoodsDetailView.loadMessages 负责人: 待分配");
    }

    /**
     * 负责人: 待分配
     * 功能: 点击【留言】按钮
     *       1. 读留言输入框, 空内容弹提示"留言内容不能为空"
     *       2. 调用 GoodsMessageService.add(goodsId, Session.currentUserId(), content)
     *       3. 成功 -> 清空输入框, 调 loadMessages() 刷新留言区
     * 参数: 无
     * 返回值: 无
     */
    public void onMessageSend() {
        throw new UnsupportedOperationException("待实现: GoodsDetailView.onMessageSend 负责人: 待分配");
    }

    /**
     * 负责人: 待分配
     * 功能: 点击【联系卖家】
     *       1. 卖家id = 商品的 sellerId
     *       2. 自己不能联系自己, 弹提示
     *       3. new ChatView(卖家的User).setVisible(true)
     * 参数: 无
     * 返回值: 无
     */
    public void onContactSeller() {
        throw new UnsupportedOperationException("待实现: GoodsDetailView.onContactSeller 负责人: 待分配");
    }

    /**
     * 负责人: 待分配
     * 功能: 点击【立即购买】
     *       1. 弹框确认"确认购买该商品吗?"(JOptionPane.showConfirmDialog)
     *       2. 确认 -> new TradeView(goodsId).setVisible(true)
     * 参数: 无
     * 返回值: 无
     */
    public void onBuyClick() {
        throw new UnsupportedOperationException("待实现: GoodsDetailView.onBuyClick 负责人: 待分配");
    }
}