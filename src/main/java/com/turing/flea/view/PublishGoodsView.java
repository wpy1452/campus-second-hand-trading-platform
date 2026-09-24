package com.turing.flea.view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * 发布商品界面
 *
 * 对应需求: 标题 / 描述 / 价格 / 图片路径 / 发布按钮 / 返回按钮
 *   发布商品: 校验后封装 Goods 存入 goods 表, 状态为在售, 成功后刷新主窗口
 *   返回: 点击返回主窗口
 *
 * 负责人: 待分配
 *
 * 注意: 价格输入的是"元"(比如 150.50), 存库前要转成"分" (元 * 100 取整), 别直接存小数
 */
public class PublishGoodsView extends JFrame {

    /* ------------------------------ 数据的设计 ------------------------------ */
    /** 标题输入框 */
    private JTextField titleField;
    /** 描述输入框(多行) */
    private JTextArea descriptionArea;
    /** 价格输入框, 单位: 元 */
    private JTextField priceField;
    /** 图片路径输入框, 也可以点"选择图片"按钮从本地选 */
    private JTextField imagePathField;
    /** 选择图片按钮 (JFileChooser 选一张本地图片) */
    private JButton chooseImageButton;
    /** 发布按钮 */
    private JButton publishButton;
    /** 返回按钮 */
    private JButton backButton;

    /**
     * 负责人: 待分配
     * 功能: 创建发布商品窗口并初始化界面
     * 参数: 无
     * 返回值: 无
     */
    public PublishGoodsView() {
        super("发布商品");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 480);
        setLocationRelativeTo(null);
        initView();
    }

    /**
     * 负责人: 待分配
     * 功能: 初始化界面: 摆好输入框和按钮, 给按钮绑定点击事件
     * 参数: 无
     * 返回值: 无
     */
    public void initView() {
        // TODO 待实现 (负责人: 待分配)
    }

    /**
     * 负责人: 待分配
     * 功能: 点击【选择图片】按钮
     *       1. new JFileChooser() 弹出来选一张图片
     *       2. 把选中的文件绝对路径 setText 到图片路径输入框
     * 参数: 无
     * 返回值: 无
     */
    public void onChooseImageClick() {
        throw new UnsupportedOperationException("待实现: PublishGoodsView.onChooseImageClick 负责人: 待分配");
    }

    /**
     * 负责人: 待分配
     * 功能: 点击【发布】按钮
     *       1. 校验: 标题不能为空, 价格必须是数字且>0 (view 的格式校验)
     *       2. 封装 Goods: title/description/imagePath, price = 元*100, sellerId = Session.currentUserId()
     *       3. 调用 GoodsService.publish(goods), 返回 -1 表示失败 -> 弹提示
     *       4. 成功 -> 弹提示"发布成功", 然后 dispose()
     *          (主窗口刷新: 可以在 MainView 里重新 loadOnSaleGoods(), 或者发布成功时让主窗口重新查一次)
     * 参数: 无
     * 返回值: 无
     */
    public void onPublishClick() {
        throw new UnsupportedOperationException("待实现: PublishGoodsView.onPublishClick 负责人: 待分配");
    }

    /**
     * 负责人: 待分配
     * 功能: 点击【返回】按钮: dispose() 关掉本窗口
     * 参数: 无
     * 返回值: 无
     */
    public void onBackClick() {
        throw new UnsupportedOperationException("待实现: PublishGoodsView.onBackClick 负责人: 待分配");
    }
}