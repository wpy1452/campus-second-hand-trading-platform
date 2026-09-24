package com.turing.flea.view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * 修改商品界面 (个人中心 -> 我的商品管理 -> 修改)
 *
 * 对应需求: 修改: 进入修改页面, 更新商品信息
 *
 * 负责人: 待分配
 *
 * 说明: 打开时先把原来的商品信息显示出来(loadGoods), 用户改完点保存 -> GoodsService.modify
 */
public class EditGoodsView extends JFrame {

    /* ------------------------------ 数据的设计 ------------------------------ */
    /** 要修改的商品id, 由构造方法传进来 */
    private int goodsId;
    /** 标题输入框, 打开时填原来的标题 */
    private JTextField titleField;
    /** 描述输入框(多行) */
    private JTextArea descriptionArea;
    /** 价格输入框, 单位: 元 (显示时要先除以100, 保存时再乘100) */
    private JTextField priceField;
    /** 图片路径输入框 */
    private JTextField imagePathField;
    /** 选择图片按钮 */
    private JButton chooseImageButton;
    /** 保存按钮 */
    private JButton saveButton;
    /** 返回按钮 */
    private JButton backButton;

    /**
     * 负责人: 待分配
     * 功能: 创建修改商品窗口, 初始化界面并回显原来的商品信息
     * 参数: goodsId 要修改的商品id
     * 返回值: 无
     */
    public EditGoodsView(int goodsId) {
        super("修改商品");
        this.goodsId = goodsId;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 480);
        setLocationRelativeTo(null);
        initView();
    }

    /**
     * 负责人: 待分配
     * 功能: 初始化界面, 并调用 loadGoods() 回显数据
     * 参数: 无
     * 返回值: 无
     */
    public void initView() {
        // TODO 待实现 (负责人: 待分配)
    }

    /**
     * 负责人: 待分配
     * 功能: 回显商品原来信息
     *       1. 调用 GoodsService.detail(goodsId)
     *       2. 标题/描述/图片路径直接填; 价格要把"分"换成"元"再填 (price/100.0)
     * 参数: 无
     * 返回值: 无
     */
    public void loadGoods() {
        throw new UnsupportedOperationException("待实现: EditGoodsView.loadGoods 负责人: 待分配");
    }

    /**
     * 负责人: 待分配
     * 功能: 点击【选择图片】按钮: 同发布界面, 用 JFileChooser 选图片并把路径填进输入框
     * 参数: 无
     * 返回值: 无
     */
    public void onChooseImageClick() {
        throw new UnsupportedOperationException("待实现: EditGoodsView.onChooseImageClick 负责人: 待分配");
    }

    /**
     * 负责人: 待分配
     * 功能: 点击【保存】按钮
     *       1. 校验标题/价格格式
     *       2. 封装 Goods(id 用本类的 goodsId, price 换算成分), 调用 GoodsService.modify(goods)
     *       3. 成功 -> 弹提示"修改成功", dispose()
     * 参数: 无
     * 返回值: 无
     */
    public void onSaveClick() {
        throw new UnsupportedOperationException("待实现: EditGoodsView.onSaveClick 负责人: 待分配");
    }

    /**
     * 负责人: 待分配
     * 功能: 点击【返回】按钮: dispose()
     * 参数: 无
     * 返回值: 无
     */
    public void onBackClick() {
        throw new UnsupportedOperationException("待实现: EditGoodsView.onBackClick 负责人: 待分配");
    }
}