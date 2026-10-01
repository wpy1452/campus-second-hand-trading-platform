package com.turing.flea.view;

import com.turing.flea.common.GoodsStatus;
import com.turing.flea.common.Session;
import com.turing.flea.entity.Goods;
import com.turing.flea.entity.GoodsMessage;
import com.turing.flea.service.GoodsMessageService;
import com.turing.flea.service.GoodsService;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * 商品详情界面：展示商品标题/描述/价格/图片/卖家/状态，并提供留言区、留言发送、联系卖家、立即购买入口。
 * 本类只做界面展示与输入格式校验，不写 SQL、不调用 DAO。
 */
public class GoodsDetailView extends JFrame {

    private int goodsId;
    private JLabel titleLabel;
    private JLabel priceLabel;
    private JLabel statusLabel;
    private JLabel sellerLabel;
    private JLabel imageLabel;
    private JTextArea descriptionArea;
    private JList<String> messageList;
    private DefaultListModel<String> messageListModel;
    private JTextField messageField;
    private JButton messageButton;
    private JButton contactButton;
    private JButton buyButton;

    private GoodsService goodsService;
    private GoodsMessageService goodsMessageService;
    private Goods currentGoods;

    public GoodsDetailView(int goodsId) {
        super("商品详情");
        this.goodsId = goodsId;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        initView();
    }

    public void initView() {
        goodsService = new GoodsService();
        goodsMessageService = new GoodsMessageService();

        // 标题栏
        titleLabel = new JLabel("加载中...");
        titleLabel.setFont(new Font("宋体", Font.BOLD, 20));

        statusLabel = new JLabel("-");
        statusLabel.setFont(new Font("宋体", Font.PLAIN, 14));
        statusLabel.setForeground(new Color(90, 90, 90));

        JPanel headPanel = new JPanel(new BorderLayout(10, 0));
        headPanel.add(titleLabel, BorderLayout.CENTER);
        headPanel.add(statusLabel, BorderLayout.EAST);

        // 商品信息区
        imageLabel = new JLabel("暂无图片", JLabel.CENTER);
        imageLabel.setPreferredSize(new Dimension(280, 210));
        imageLabel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        priceLabel = new JLabel("￥0.00");
        priceLabel.setFont(new Font("宋体", Font.BOLD, 18));
        priceLabel.setForeground(new Color(210, 60, 60));

        sellerLabel = new JLabel("-");

        JPanel infoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 4, 6, 4);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        infoPanel.add(new JLabel("价格："), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        infoPanel.add(priceLabel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        infoPanel.add(new JLabel("卖家："), gbc);
        gbc.gridx = 1;
        infoPanel.add(sellerLabel, gbc);

        descriptionArea = new JTextArea();
        descriptionArea.setEditable(false);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setFont(new Font("宋体", Font.PLAIN, 14));
        JScrollPane descriptionScroll = new JScrollPane(descriptionArea);
        descriptionScroll.setBorder(BorderFactory.createTitledBorder("商品描述"));

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.add(imageLabel);
        leftPanel.add(infoPanel);
        leftPanel.add(descriptionScroll);

        // 留言区
        messageListModel = new DefaultListModel<String>();
        messageList = new JList<String>(messageListModel);
        messageList.setFont(new Font("宋体", Font.PLAIN, 14));
        JScrollPane messageScroll = new JScrollPane(messageList);
        messageScroll.setBorder(BorderFactory.createTitledBorder("商品留言"));

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(messageScroll, BorderLayout.CENTER);

        JPanel centerPanel = new JPanel(new BorderLayout(10, 0));
        centerPanel.add(leftPanel, BorderLayout.CENTER);
        centerPanel.add(rightPanel, BorderLayout.EAST);
        rightPanel.setPreferredSize(new Dimension(330, 0));

        // 底部留言输入
        messageField = new JTextField(22);
        messageButton = new JButton("留言");
        contactButton = new JButton("联系卖家");
        buyButton = new JButton("立即购买");

        messageField.addActionListener(e -> sendMessageButtonAction());
        messageButton.addActionListener(e -> sendMessageButtonAction());
        contactButton.addActionListener(e -> onContactSeller());
        buyButton.addActionListener(e -> onBuyClick());

        contactButton.setToolTipText("聊天功能暂缓，请在留言区与卖家沟通");

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        bottomPanel.add(new JLabel("留言："));
        bottomPanel.add(messageField);
        bottomPanel.add(messageButton);
        bottomPanel.add(contactButton);
        bottomPanel.add(buyButton);

        // 组装
        JPanel rootPanel = new JPanel(new BorderLayout(10, 10));
        rootPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        rootPanel.add(headPanel, BorderLayout.NORTH);
        rootPanel.add(centerPanel, BorderLayout.CENTER);
        rootPanel.add(bottomPanel, BorderLayout.SOUTH);
        setContentPane(rootPanel);

        loadGoodsDetail();
        loadMessages();
    }

    // 加载商品详情并填充界面
    public void loadGoodsDetail() {
        Goods goods;
        try {
            goods = goodsService.detail(goodsId);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "加载商品详情失败：" + e.getMessage(),
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

        titleLabel.setText(goods.getTitle() == null ? "" : goods.getTitle());

        // 数据库价格单位为分，显示时换算成元
        priceLabel.setText("￥" + String.format("%.2f", goods.getPrice() / 100.0));

        GoodsStatus status = goods.getStatus();
        statusLabel.setText("状态：" + (status == null ? "未知" : status.getDesc()));

        String nickname = goods.getSellerNickname();
        if (nickname == null || nickname.trim().isEmpty()) {
            nickname = "用户#" + goods.getSellerId();
        }
        sellerLabel.setText(nickname);

        descriptionArea.setText(goods.getDescription() == null ? "" : goods.getDescription());
        descriptionArea.setCaretPosition(0);

        // 图片路径为空则不显示；否则缩放到 280x210
        String imagePath = goods.getImagePath();
        if (imagePath == null || imagePath.trim().isEmpty()) {
            imageLabel.setIcon(null);
            imageLabel.setText("暂无图片");
        } else {
            imagePath = imagePath.trim();
            ImageIcon rawIcon = null;
            try {
                if (imagePath.startsWith("http://") || imagePath.startsWith("https://")) {
                    URLConnection conn = new URL(imagePath).openConnection();
                    conn.setConnectTimeout(3000);
                    conn.setReadTimeout(5000);
                    try (InputStream in = conn.getInputStream()) {
                        BufferedImage img = ImageIO.read(in);
                        if (img != null) {
                            rawIcon = new ImageIcon(img);
                        }
                    }
                } else {
                    rawIcon = new ImageIcon(imagePath);   // 本地文件照旧
                }
            } catch (Exception ex) {
                rawIcon = null;                            // 网络失败/坏链接
            }
            if (rawIcon != null && rawIcon.getIconWidth() > 0) {
                Image scaled = rawIcon.getImage().getScaledInstance(280, 210, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(scaled));
                imageLabel.setText("");
            } else {
                imageLabel.setIcon(null);
                imageLabel.setText("图片加载失败");
            }
        }

        // 仅在售且非本人发布的商品才可购买
        boolean isMyGoods = goods.getSellerId() == Session.currentUserId();
        boolean onSale = status == GoodsStatus.SALE;
        buyButton.setEnabled(!isMyGoods && onSale);
        if (isMyGoods) {
            buyButton.setToolTipText("这是你自己发布的商品");
        } else if (!onSale) {
            buyButton.setToolTipText("该商品当前不在售，无法购买");
        }
    }

    // 加载留言列表（首次进入时调用）
    public void loadMessages() {
        refreshMessages();
    }

    // 刷新留言区
    public void refreshMessages() {
        if (messageListModel == null) {
            return;
        }
        messageListModel.clear();

        List<GoodsMessage> list;
        try {
            list = goodsMessageService.listByGoods(goodsId);
        } catch (RuntimeException e) {
            messageListModel.addElement("留言加载失败：" + e.getMessage());
            return;
        }
        if (list == null || list.isEmpty()) {
            messageListModel.addElement("暂无留言，来说两句吧~");
            return;
        }
        for (GoodsMessage m : list) {
            String nickname = m.getUserNickname();
            if (nickname == null || nickname.trim().isEmpty()) {
                nickname = "用户#" + m.getUserId();
            }
            String content = m.getContent() == null ? "" : m.getContent();
            messageListModel.addElement(nickname + "：" + content);
        }
    }

    // 点击「留言」：校验登录与内容后调用 Service 发送
    public void sendMessageButtonAction() {
        int userId = Session.currentUserId();
        if (userId <= 0) {
            JOptionPane.showMessageDialog(this, "请先登录", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String content = messageField.getText();
        if (content == null || content.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "留言内容不能为空", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        boolean ok;
        try {
            ok = goodsMessageService.add(goodsId, userId, content.trim());
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "留言失败：" + e.getMessage(),
                    "提示", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (ok) {
            messageField.setText("");
            refreshMessages();
        } else {
            JOptionPane.showMessageDialog(this, "留言失败，请稍后重试", "提示", JOptionPane.ERROR_MESSAGE);
        }
    }

    // 点击「联系卖家」：聊天功能暂缓，引导用户使用留言区
    public void onContactSeller() {
        JOptionPane.showMessageDialog(this, "聊天功能暂缓，改为商品留言模块，请在留言区给卖家留言",
                "提示", JOptionPane.INFORMATION_MESSAGE);
    }

    // 点击「立即购买」：确认后打开交易界面
    public void onBuyClick() {
        int choice = JOptionPane.showConfirmDialog(this, "确认购买该商品吗?",
                "确认购买", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            new TradeView(goodsId).setVisible(true);
        }
    }

    // 以下为骨架旧方法名的兼容别名
    public void loadGoods() {
        loadGoodsDetail();
    }

    public void onMessageSend() {
        sendMessageButtonAction();
    }
}
