package com.turing.flea.view;

import com.turing.flea.common.Session;
import com.turing.flea.entity.Goods;
import com.turing.flea.service.GoodsService;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;

/**
 * 修改商品界面（个人中心 -> 我的商品管理 -> 修改）：打开时回显原商品信息，修改后保存。
 * 本类只做界面回显与格式校验，业务判断交给 GoodsService。
 */
public class EditGoodsView extends JFrame {

    private int goodsId;
    private JTextField titleField;
    private JTextArea descriptionArea;
    private JTextField priceField;
    private JTextField imagePathField;
    private JButton chooseImageButton;
    private JButton saveButton;
    private JButton backButton;

    private GoodsService goodsService;
    private Goods currentGoods;

    public EditGoodsView(int goodsId) {
        super("修改商品");
        this.goodsId = goodsId;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 480);
        setLocationRelativeTo(null);
        initView();
    }

    public void initView() {
        goodsService = new GoodsService();

        // 表单区
        titleField = new JTextField(24);

        descriptionArea = new JTextArea(5, 24);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane descriptionScroll = new JScrollPane(descriptionArea);

        priceField = new JTextField(24);
        imagePathField = new JTextField(16);
        chooseImageButton = new JButton("选择图片");

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 6, 8, 6);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        formPanel.add(new JLabel("标题："), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        formPanel.add(titleField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        formPanel.add(new JLabel("描述："), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        formPanel.add(descriptionScroll, gbc);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        formPanel.add(new JLabel("价格(元)："), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        formPanel.add(priceField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.0;
        formPanel.add(new JLabel("图片路径："), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        imagePanel.add(imagePathField);
        imagePanel.add(chooseImageButton);
        formPanel.add(imagePanel, gbc);

        // 按钮区
        saveButton = new JButton("保存");
        backButton = new JButton("取消");

        chooseImageButton.addActionListener(e -> onChooseImageClick());
        saveButton.addActionListener(e -> saveButtonAction());
        backButton.addActionListener(e -> cancelButtonAction());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.add(saveButton);
        buttonPanel.add(backButton);

        // 组装
        JPanel rootPanel = new JPanel(new BorderLayout(10, 10));
        rootPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        rootPanel.add(formPanel, BorderLayout.CENTER);
        rootPanel.add(buttonPanel, BorderLayout.SOUTH);
        setContentPane(rootPanel);

        loadGoods();
    }

    // 回显商品原信息（数据库价格为分，界面显示为元）
    public void loadGoods() {
        Goods goods;
        try {
            goods = goodsService.detail(goodsId);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "加载商品信息失败：" + e.getMessage(),
                    "提示", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }
        if (goods == null) {
            JOptionPane.showMessageDialog(this, "商品不存在或已被删除", "提示", JOptionPane.WARNING_MESSAGE);
            dispose();
            return;
        }
        currentGoods = goods;

        titleField.setText(goods.getTitle() == null ? "" : goods.getTitle());
        descriptionArea.setText(goods.getDescription() == null ? "" : goods.getDescription());
        descriptionArea.setCaretPosition(0);
        priceField.setText(String.format("%.2f", goods.getPrice() / 100.0));
        imagePathField.setText(goods.getImagePath() == null ? "" : goods.getImagePath());
    }

    // 点击「选择图片」：用文件选择器选图片并填入路径
    public void onChooseImageClick() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("选择商品图片");
        chooser.setFileFilter(new FileNameExtensionFilter("图片文件", "jpg", "jpeg", "png", "gif", "bmp"));
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            imagePathField.setText(file.getAbsolutePath());
        }
    }

    // 点击「保存」：校验格式后封装 Goods 并交给 Service 保存
    public void saveButtonAction() {
        String title = titleField.getText();
        if (title == null || title.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "标题不能为空", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String priceText = priceField.getText();
        if (priceText == null || priceText.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "价格不能为空", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        double yuan;
        try {
            yuan = Double.parseDouble(priceText.trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "价格必须是数字", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (yuan <= 0) {
            JOptionPane.showMessageDialog(this, "价格必须大于 0", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        // 元换算成分（四舍五入）
        int price = (int) Math.round(yuan * 100);
        if (price <= 0) {
            JOptionPane.showMessageDialog(this, "价格必须大于 0", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Goods goods = new Goods();
        goods.setId(goodsId);
        goods.setTitle(title.trim());
        goods.setDescription(descriptionArea.getText() == null ? "" : descriptionArea.getText().trim());
        goods.setPrice(price);
        goods.setImagePath(imagePathField.getText() == null ? "" : imagePathField.getText().trim());
        goods.setSellerId(Session.currentUserId());

        boolean ok;
        try {
            ok = goodsService.modify(goods);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "修改失败：" + e.getMessage(),
                    "提示", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (ok) {
            JOptionPane.showMessageDialog(this, "修改成功", "提示", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "修改失败，只能修改自己发布的商品",
                    "提示", JOptionPane.ERROR_MESSAGE);
        }
    }

    // 点击「取消」：关闭窗口，不保存
    public void cancelButtonAction() {
        dispose();
    }

    // 以下为骨架旧方法名的兼容别名
    public void onSaveClick() {
        saveButtonAction();
    }

    public void onBackClick() {
        cancelButtonAction();
    }
}
