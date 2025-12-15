package edu.sustech.xiangqi;

import edu.sustech.xiangqi.model.ChessBoardModel;
import edu.sustech.xiangqi.ui.ChessBoardPanel;
import edu.sustech.xiangqi.ui.Style;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class GameFrame extends JFrame{
    public String user;
//    private int style = 0;
    public static JLabel label;
    public static JLabel label2;
    private ChessBoardModel modelIN;//当前全局可使用的棋盘,请使用这个

    private static GameFrame instance;
    private static String title;
    private static ChessBoardModel aiModel;
    private static ChessBoardModel timingModel;
    private static int style;


    public GameFrame(String title, String user, ChessBoardModel preModel, ChessBoardModel aiModel, ChessBoardModel timingModel, int style){
        super("红黑对战");
            //上面是login的界面，下面是象棋的界面
        this.user = user;
        if(preModel != null){
            modelIN = preModel;
        }
        Style[] styleList = Style.values();
        this.user = user;
        this.title = title;
        this.aiModel = aiModel;
        this.timingModel = timingModel;
        this.style = style;
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        //关闭程序时，同时删除可能存在的游客6060
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                if(ChoiceBox.choiceBox("退出确认", "要退出吗？（当前棋局会自动保存）")){
                    ToolBox.tempEnd(user, timingModel);
                    ToolBox.deleteVisitorFile();
                    dispose();
                    System.exit(0);
                }
            }
        });
        //listener结束

        ChessBoardModel model = new ChessBoardModel(user, true, 1);
        if(preModel != null){
            model = preModel;
        }else{
            modelIN = model;
        }
        //model.setUser(user);
        //这里的setUser在model初始化之后无效，为什么，因为在测试中使用logWriter时user还没有被赋值，已解决


        label = new JLabel("你好，" + user);
        label.setForeground(styleList[style].getLabelColor());
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setLocation(520, 50);
        label.setSize(300, 50);
        this.add(label);//先添加的后绘制

        label2 = new JLabel("你好，" + user);
        label2.setForeground(styleList[style].getLabelColor());
        label2.setHorizontalAlignment(SwingConstants.CENTER);
        label2.setLocation(520, 100);
        label2.setSize(300, 50);
        this.add(label2);//先添加的后绘制

        JButton retractPiece = new JButton("悔棋");
        retractPiece.setLocation(620, 300);
        retractPiece.setSize(120, 50);
        this.add(retractPiece);
        retractPiece.addActionListener(e -> {
//            modelIN = RetractBox.retractModel(user, 1);

            GameFrame newFrame = new GameFrame(title, user, RetractBox.retractModel(user, 1), aiModel, timingModel, style);
            this.setVisible(false);
            newFrame.setVisible(true);

            System.out.println(user + " retracted a piece");
        });

        JButton button = new JButton("认输");
        button.setLocation(620, 400);
        button.setSize(120, 50);
        this.add(button);//先添加的后绘制
        button.addActionListener(e -> {
            System.out.println("hhhhhh");

            //这里面还要添加认输确认，添加到log


            if(ChoiceBox.choiceBox("结束确认", "要认输吗？")){
                ToolBox.confirmToEnd(user, 1);
                MenuFrame menuFrame = new MenuFrame(title, user, style, null, aiModel, timingModel);
                this.setVisible(false);
                menuFrame.setVisible(true);
            }

        });


        JButton reset = new JButton("重开棋局");
        reset.setLocation(620, 500);
        reset.setSize(120, 50);
        this.add(reset);
        reset.addActionListener(e -> {
            if(ChoiceBox.choiceBox("重开确认", "确定要开始新棋局吗？（当前棋局会自动结束）")){
                ToolBox.confirmToEnd(user, 1);
                GameFrame newFrame = new GameFrame(title, user, null, aiModel, timingModel, style);
                this.setVisible(false);
                newFrame.setVisible(true);
                //这里可能还要添加存储log相关的代码



                System.out.println("A new GameFrame for " + user);
            }
        });


        JButton backButton = new JButton("返回菜单");
        backButton.setLocation(620, 600);
        backButton.setSize(120, 50);
        this.add(backButton);
//        int style = 0;
        //这里需要进一步细化
        backButton.addActionListener(e -> {
            MenuFrame menuFrame = new MenuFrame(title, user, style, modelIN, aiModel, timingModel);
            this.setVisible(false);
            menuFrame.setVisible(true);
            //这里可能需要log相关的代码，不需要了，只要使用原有model，就不会改变

        });

//        ChessBoardModel model = new ChessBoardModel();
//        model.setUser(user);
//        //这里的setUser在model初始化之后无效，为什么，因为在测试中使用logWriter时user还没有被赋值，已解决





//        //model上移了
//        //注意style相关
//        ChessBoardPanel boardPanel = new ChessBoardPanel(model, 0);
//        //
//
//        boardPanel.label = label;


        ChessBoardPanel boardPanel = new ChessBoardPanel(model, style);
        boardPanel.label = label2;
        this.add(boardPanel);
//            chessFrame.pack();//大小适于内容
        this.setSize(800, 700);
        this.setLocationRelativeTo(null);
        //chessFrame.setVisible(true);
    }


//    public void setStyle(int style){
//        this.style = style;
//    }

    public ChessBoardModel getModel(){
        return modelIN;
    }

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


//    public void sleepAction(int toNumber, int toRow, int toCol){
//        Timer timer = new Timer(5000, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                modelIN.checkMove(toNumber, toRow, toCol);
//            }
//        });
//        timer.setRepeats(false);
//        timer.start();
//    }

    public static GameFrame getInstance(){
        return instance;
    }


    public void end(String s){
        ToolBox.confirmToEnd(user, 3);
        MenuFrame menuFrame = new MenuFrame(title, user, style, null, aiModel, timingModel);
        this.setVisible(false);

        menuFrame.setVisible(true);
        NoticeBox noticeBox = new NoticeBox("提示", user, style, s);
        noticeBox.setVisible(true);
    }


}


