package edu.sustech.xiangqi;

import edu.sustech.xiangqi.ui.Style;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class NoticeBox extends JFrame{
    private String user;
    private int newStyle;
    public Font defaultFont = new Font("微软雅黑", Font.BOLD, 18);



    //  public JFrame chessFrame;

    public NoticeBox(String title, String user, int style, String content){
        super(title);
        UIManager.put("Label.font", defaultFont);
        UIManager.put("Button.font", defaultFont);
        //上面是login的界面，下面是象棋的界面
        this.user = user;
        this.newStyle = style;
        this.setLayout(null);
        this.setSize(400, 300);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
//                deleteFile();
//                dispose();
//                System.exit(0);
                setVisible(false);
            }
        });
        Style[] styleList = Style.values();
        getContentPane().setBackground(styleList[style].getBackgroundColor());
        //这里可能还要修改来适应可能的图片
        //修改此处注意修改panel的相同方法

//        JPanel panel = new JPanel();
//        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
//        panel.setLocation(75, 50);

        JLabel noticeLabel = new JLabel();
        noticeLabel.setForeground(styleList[style].getLabelColor());
        noticeLabel.setLocation(0, 50);
        noticeLabel.setSize(400, 100);
        noticeLabel.setText(content);
        noticeLabel.setHorizontalAlignment(SwingConstants.CENTER);
//        noticeLabel.setVerticalAlignment(SwingConstants.CENTER);
//        panel.add(Box.createVerticalGlue());
//        panel.add(noticeLabel);
//        panel.add(Box.createVerticalGlue());
//        this.add(panel);
        this.add(noticeLabel);



        JButton exitButton = new JButton("确定");
        exitButton.setLocation(50, 180);
        exitButton.setSize(300, 50);
        this.add(exitButton);
        exitButton.addActionListener(e -> {

            this.setVisible(false);
        });
    }
}


