package edu.sustech.xiangqi;

import edu.sustech.xiangqi.model.ChessBoardModel;
import edu.sustech.xiangqi.ui.Style;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class NoticeFrame extends JFrame{
    private String user;
    private int newStyle;
    public Font defaultFont = new Font("微软雅黑", Font.BOLD, 18);



    //  public JFrame chessFrame;

    public NoticeFrame(String title, String user, int style, String content){
        super("警告");
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



//        JButton newGameButton = new JButton("新游戏");
//        newGameButton.setLocation(50, 100);
//        newGameButton.setSize(400, 50);
//        this.add(newGameButton);
//        newGameButton.addActionListener(e -> {
//            GameFrame chessFrame = new GameFrame(title, user, null, style);
//            this.setVisible(false);
//            chessFrame.setVisible(true);
//            createFolder();
//            //这里可能需要log相关的代码
////            newStyle = 0;
////            this.setVisible(false);
////            StyleFrame styleFrame = new StyleFrame("风格面板", originalFrame.user, originalFrame, originalFrame.getModel(), newStyle);
////            styleFrame.setVisible(true);
////            System.out.println(user + " tried style 1");
////            model.pauseButton(false);
//        });


//        JButton continueButton = new JButton("继续游戏");
//        continueButton.setLocation(50, 200);
//        continueButton.setSize(400, 50);
//        this.add(continueButton);
//        continueButton.addActionListener(e -> {
//            //这里可能需要log相关的代码
////            newStyle = 1;
////            this.setVisible(false);
////            StyleFrame styleFrame = new StyleFrame("风格面板", originalFrame.user, originalFrame, originalFrame.getModel(), newStyle);
////            styleFrame.setVisible(true);
////            System.out.println(user + " tried style 2");
////            model.pauseButton(true);
//        });


//        JButton aiChessButton = new JButton("AI对战");
//        aiChessButton.setLocation(50, 300);
//        aiChessButton.setSize(400, 50);
//        this.add(aiChessButton);
//        aiChessButton.addActionListener(e -> {
//
//        });
//
//        JButton lastChessButton = new JButton("上一局回放");
//        lastChessButton.setLocation(50, 400);
//        lastChessButton.setSize(400, 50);
//        this.add(lastChessButton);
//        lastChessButton.addActionListener(e -> {
//            ReplayFrame replayFrame = new ReplayFrame(title, user, null, style);
//            this.setVisible(false);
//            replayFrame.setVisible(true);
//
//        });

//        JButton styleButton = new JButton("切换皮肤");
//        styleButton.setLocation(50, 500);
//        styleButton.setSize(400, 50);
//        this.add(styleButton);
//        styleButton.addActionListener(e -> {
//            StyleFrame styleFrame = new StyleFrame(title, user, style);
//            this.setVisible(false);
//            styleFrame.setVisible(true);
//
//            System.out.println(user + " is now in the styleFrame");
//        });

        JButton exitButton = new JButton("确定");
        exitButton.setLocation(50, 180);
        exitButton.setSize(300, 50);
        this.add(exitButton);
        exitButton.addActionListener(e -> {
//            LoginFrame loginFrame = new LoginFrame("中国象棋 登录界面");
//            this.setVisible(false);
//            deleteFile();
//            loginFrame.setVisible(true);

            this.setVisible(false);
        });






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

//    private void createFolder(){
//        Path path = Paths.get("UserData/" + user);
//        try{
//            Files.createDirectories(path);
//            System.out.println(user + "'s log folder ready!");
//        }catch(IOException e){
//            System.err.println(e.getMessage());
//        }
//    }

//    private void deleteFile(){
//        try{
//            File fileToDelete = new File("UserData/游客6060/游客6060.txt");
//            if(fileToDelete.exists()){
//                if(fileToDelete.delete()){
//                    System.out.println("visitor log successfully deleted!");
//                }else{
//                    System.out.println("visitor log deleting failed!");
//                }
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    }


}


