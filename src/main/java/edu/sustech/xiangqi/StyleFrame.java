package edu.sustech.xiangqi;

import edu.sustech.xiangqi.model.ChessBoardModel;
import edu.sustech.xiangqi.ui.Style;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class StyleFrame extends JFrame{
    private String user;
    private int newStyle;


  //  public JFrame chessFrame;

    public StyleFrame(String title, String user, GameFrame originalFrame, ChessBoardModel model, int style){
        super(title);
            //上面是login的界面，下面是象棋的界面
        this.user = user;
        this.newStyle = style;
        this.setLayout(null);
        this.setSize(500, 500);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);


        //关闭程序时，默认确认当前皮肤，并返回棋盘
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                setVisible(false);
                GameFrame gameFrame = new GameFrame(originalFrame.getTitle(), originalFrame.user, originalFrame.getModel() ,newStyle);
                gameFrame.setVisible(true);


                System.out.println(user + " confirmed new style " + style);
            }
        });
        //listener结束



        JButton style1 = new JButton("风格1， 注意还要把风格添加到用户资料里");
        style1.setLocation(50, 50);
        style1.setSize(400, 50);
        this.add(style1);
        style1.addActionListener(e -> {
            //这里可能需要log相关的代码
            newStyle = 0;
            this.setVisible(false);
            StyleFrame styleFrame = new StyleFrame("风格面板", originalFrame.user, originalFrame, originalFrame.getModel(), newStyle);
            styleFrame.setVisible(true);
            System.out.println(user + " tried style 1");
        });


        JButton style2 = new JButton("风格2");
        style2.setLocation(150, 100);
        style2.setSize(100, 50);
        this.add(style2);
        style2.addActionListener(e -> {
            //这里可能需要log相关的代码
            newStyle = 1;
            this.setVisible(false);
            StyleFrame styleFrame = new StyleFrame("风格面板", originalFrame.user, originalFrame, originalFrame.getModel(), newStyle);
            styleFrame.setVisible(true);
            System.out.println(user + " tried style 2");
        });


        JButton confirm = new JButton("返回");
        confirm.setLocation(60, 400);
        confirm.setSize(100, 50);
        this.add(confirm);
        confirm.addActionListener(e -> {
//            StyleFrame newFrame = new StyleFrame("中国象棋", user);
            this.setVisible(false);
            GameFrame gameFrame = new GameFrame(originalFrame.getTitle(), originalFrame.user, originalFrame.getModel() ,newStyle);
            gameFrame.setVisible(true);


            System.out.println(user + " confirmed new style " + style);
        });



        Style[] styleList = Style.values();
        getContentPane().setBackground(styleList[style].getBackgroundColor());
        //这里可能还要修改来适应可能的图片
        //修改此处注意修改panel的相同方法

//        switch(style){
//            case 0:
//                setBackground(styleList[0].getBackgroundColor());
//                break;
//            case 1:
////            ImageIcon originalIcon = new ImageIcon(".\\src\\main\\resources\\loginBackground2.jpg");
////            Image scaledImage = originalIcon.getImage().getScaledInstance(500, 500, Image.SCALE_SMOOTH);
////            ImageIcon scaledIcon = new ImageIcon(scaledImage);
////            JLabel background = new JLabel(scaledIcon);
////            background.setSize(500, 500);
////            background.setLocation(0, 0);
////            this.add(background);
//                //对图片的尝试
//                setBackground();
//                break;
//        }
    }
}


