package com.turing.flea.view;

import com.turing.flea.entity.Goods;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 * 主窗口 / 商品列表界面 (首页)
 *
 * 对应需求: 在售商品JTable / 搜索输入框 / 发布商品按钮 / 个人中心按钮 / 聊天入口
 *   浏览商品: 读取在售商品加载到表格, 点击进详情
 *   商品搜索: 关键词条件查询 goods 表, 展示匹配结果
 *   发布商品: 进入发布界面      个人中心: 进入个人中心      聊天入口: 进入聊天窗口
 *
 * 负责人: 待分配
 *
 * 分层说明: 数据全部通过 GoodsService 拿, 这里只负责把 List<Goods> 塞进 JTable
 */
public class MainView extends JFrame {

    /* ------------------------------ 数据的设计 ------------------------------ */
    /** 在售商品表格 */
    private JTable goodsTable;
    /** 表格的数据模型, 表头: 商品id / 标题 / 描述 / 价格 / 状态 (列顺序要和 fillTable 里一致) */
    private DefaultTableModel tableModel;
    /** 搜索输入框 */
    private JTextField searchField;
    /** 搜索按钮 */
    private JButton searchButton;
    /** 发布商品按钮 */
    private JButton publishButton;
    /** 个人中心按钮 */
    private JButton profileButton;
    /** 聊天入口按钮 */
    private JButton chatButton;
    /** 消息提示用的定时器: 每3秒查一次未读消息(拓展功能) */
    private javax.swing.Timer messageTimer;

    /**
     * 负责人: 待分配
     * 功能: 创建主窗口, 初始化界面并加载在售商品
     * 参数: 无
     * 返回值: 无
     */
    public MainView() {
        super("校园二手交易平台 - 首页");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        initView();
    }

    /**
     * 负责人: 待分配
     * 功能: 初始化界面: 摆好 表格/搜索框/4个按钮, 绑定按钮事件, 表格加鼠标监听(点击一行进详情),
     *       最后调用 loadOnSaleGoods() 第一次加载数据
     * 参数: 无
     * 返回值: 无
     */
    public void initView() {
        // TODO 待实现 (负责人: 待分配)
    }

    /**
     * 负责人: 待分配
     * 功能: 加载在售商品到表格
     *       1. 调用 GoodsService.listOnSale()
     *       2. 调用 fillTable(...) 显示
     * 参数: 无
     * 返回值: 无
     */
    public void loadOnSaleGoods() {
        throw new UnsupportedOperationException("待实现: MainView.loadOnSaleGoods 负责人: 待分配");
    }

    /**
     * 负责人: 待分配
     * 功能: 表格辅助方法: 先 tableModel.setRowCount(0) 清空, 再循环 addRow 把商品一行一行加进去
     *       每一行: id / 标题 / 描述 / 价格(price/100.0 保留两位小数) / 商品状态(getDesc())
     *       提示: 表格里不要把 id 显示得太显眼也不要隐藏, 点击行时要用它去查详情, 所以放在第 0 列
     * 参数: goodsList 要显示的商品列表
     * 返回值: 无
     */
    public void fillTable(List<Goods> goodsList) {
        throw new UnsupportedOperationException("待实现: MainView.fillTable 负责人: 待分配");
    }

    /**
     * 负责人: 待分配
     * 功能: 点击【搜索】按钮 (或在搜索框里回车)
     *       1. 取搜索框的文字, 调用 GoodsService.search(keyword)
     *       2. 调用 fillTable(...) 显示搜索结果
     *       3. 没搜到东西时弹框提示"没有找到相关商品"
     * 参数: 无
     * 返回值: 无
     */
    public void onSearchClick() {
        throw new UnsupportedOperationException("待实现: MainView.onSearchClick 负责人: 待分配");
    }

    /**
     * 负责人: 待分配
     * 功能: 点击表格里某一行商品: 打开商品详情界面
     *       1. 从表格第 0 列取出商品id
     *       2. new GoodsDetailView(goodsId).setVisible(true)
     * 参数: 无
     * 返回值: 无
     */
    public void onGoodsRowClick() {
        throw new UnsupportedOperationException("待实现: MainView.onGoodsRowClick 负责人: 待分配");
    }

    /**
     * 负责人: 待分配
     * 功能: 点击【发布商品】按钮: new PublishGoodsView().setVisible(true)
     * 参数: 无
     * 返回值: 无
     */
    public void onPublishClick() {
        throw new UnsupportedOperationException("待实现: MainView.onPublishClick 负责人: 待分配");
    }

    /**
     * 负责人: 待分配
     * 功能: 点击【个人中心】按钮: new ProfileView().setVisible(true)
     * 参数: 无
     * 返回值: 无
     */
    public void onProfileClick() {
        throw new UnsupportedOperationException("待实现: MainView.onProfileClick 负责人: 待分配");
    }

    /**
     * 负责人: 待分配
     * 功能: 点击【聊天】入口: 选择一个好友/卖家后 new ChatView(...).setVisible(true)
     *       最简单做法: 先弹出好友列表(或输入对方账号), 拿到对方的 User 再打开聊天窗口
     * 参数: 无
     * 返回值: 无
     */
    public void onChatClick() {
        throw new UnsupportedOperationException("待实现: MainView.onChatClick 负责人: 待分配");
    }

    /**
     * 负责人: 待分配
     * 功能: 启动消息提示定时器(拓展功能): new javax.swing.Timer(3000, ...) 每3秒调一次
     *       ChatService.unreadCount(Session.currentUserId()), 有未读就弹提示"你有新消息"
     * 参数: 无
     * 返回值: 无
     */
    public void startMessageTimer() {
        throw new UnsupportedOperationException("待实现: MainView.startMessageTimer 负责人: 待分配");
    }
}