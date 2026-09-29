package com.turing.flea.view;

import com.turing.flea.common.Session;
import com.turing.flea.entity.Goods;
import com.turing.flea.service.GoodsService;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;

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
     * 负责人: 1111
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
     * 负责人: 1111
     * 功能: 初始化界面: 摆好输入框和按钮, 给按钮绑定点击事件
     * 参数: 无
     * 返回值: 无
     */
    public void initView() {
        titleField = new JTextField(25);
        descriptionArea = new JTextArea(5, 25);
        descriptionArea.setLineWrap(true);
        priceField = new JTextField(10);
        imagePathField = new JTextField(25);
        chooseImageButton = new JButton("选择图片");
        publishButton = new JButton("发布商品");
        backButton = new JButton("返回");

        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("标题:"), gbc);
        gbc.gridx = 1;
        formPanel.add(titleField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("描述:"), gbc);
        gbc.gridx = 1;
        formPanel.add(new JScrollPane(descriptionArea), gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("价格:"), gbc);
        gbc.gridx = 1;
        JPanel pricePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        pricePanel.add(priceField);
        pricePanel.add(new JLabel(" 元"));
        formPanel.add(pricePanel, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("图片:"), gbc);
        gbc.gridx = 1;
        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        imagePanel.add(imagePathField);
        imagePanel.add(chooseImageButton);
        formPanel.add(imagePanel, gbc);

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.add(publishButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        chooseImageButton.addActionListener(e -> onChooseImageClick());
        publishButton.addActionListener(e -> onPublishClick());
        backButton.addActionListener(e -> onBackClick());
    }

    /**
     * 负责人: 1111
     * 功能: 点击【选择图片】按钮
     *       1. new JFileChooser() 弹出来选一张图片
     *       2. 把选中的文件绝对路径 setText 到图片路径输入框
     * 参数: 无
     * 返回值: 无
     */
    public void onChooseImageClick() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("选择商品图片");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            imagePathField.setText(selectedFile.getAbsolutePath());
        }
    }

    /**
     * 负责人: 1111
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
        String title = titleField.getText().trim();
        if (title.isEmpty()) {
            JOptionPane.showMessageDialog(this, "标题不能为空");
            return;
        }

        String priceText = priceField.getText().trim();
        if (priceText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "价格不能为空");
            return;
        }

        double priceYuan;
        try {
            priceYuan = Double.parseDouble(priceText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "价格必须是数字");
            return;
        }
        if (priceYuan <= 0) {
            JOptionPane.showMessageDialog(this, "价格必须大于0");
            return;
        }

        Goods goods = new Goods();
        goods.setTitle(title);
        goods.setDescription(descriptionArea.getText().trim());
        goods.setPrice((int) (priceYuan * 100));
        goods.setImagePath(imagePathField.getText().trim());
        goods.setSellerId(Session.currentUserId());

        GoodsService goodsService = new GoodsService();
        int goodsId = goodsService.publish(goods);
        if (goodsId == -1) {
            JOptionPane.showMessageDialog(this, "发布失败, 请稍后重试");
        } else {
            JOptionPane.showMessageDialog(this, "发布成功");
            dispose();
        }
    }

    /**
     * 负责人: 1111
     * 功能: 点击【返回】按钮: dispose() 关掉本窗口
     * 参数: 无
     * 返回值: 无
     */
    public void onBackClick() {
        dispose();
    }
}