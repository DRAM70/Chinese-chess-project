package edu.sustech.xiangqi;

import edu.sustech.xiangqi.model.ChessBoardModel;
import edu.sustech.xiangqi.ui.ChessBoardPanel;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

public class GameFrame extends JFrame{
    public String user;
    private int style = 0;
    private JLabel label;
    private ChessBoardModel modelIN;//当前全局可使用的棋盘？？？？？？？？


    public GameFrame(String title, String user, ChessBoardModel preModel, int style){
        super(title);
            //上面是login的界面，下面是象棋的界面
        this.user = user;
        if(preModel != null){
            modelIN = preModel;
        }
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        //关闭程序时，同时删除可能存在的游客6060
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                deleteFile();
                dispose();
                System.exit(0);
            }
        });
        //listener结束

        ChessBoardModel model = new ChessBoardModel(user);
        if(preModel != null){
            model = preModel;
        }else{
            modelIN = model;
        }
        //model.setUser(user);
        //这里的setUser在model初始化之后无效，为什么，因为在测试中使用logWriter时user还没有被赋值，已解决


        this.label = new JLabel(user);
        label.setLocation(600, 100);
        label.setSize(100, 50);
        this.add(label);//先添加的后绘制

        JButton button = new JButton("start");
        button.setLocation(600, 200);
        button.setSize(100, 50);
        this.add(button);//先添加的后绘制
        button.addActionListener(e -> {
            System.out.println("hhhhhh");
        });


        JButton reset = new JButton("重开棋局");
        reset.setLocation(600, 400);
        reset.setSize(100, 50);
        this.add(reset);
        reset.addActionListener(e -> {
            GameFrame newFrame = new GameFrame("中国象棋", user, null, style);
            this.setVisible(false);
            newFrame.setVisible(true);
            //这里可能还要添加存储log相关的代码
            System.out.println("A new GameFrame for " + user);
        });

        JButton retractPiece = new JButton("悔棋");
        retractPiece.setLocation(600, 500);
        retractPiece.setSize(100, 50);
        this.add(retractPiece);
        retractPiece.addActionListener(e -> {
            //这里可能需要log相关的代码
            System.out.println(user + " retracted a piece^^^^");


            modelIN.checkMove(12, 5, 0);
        });

        JButton changeStyle = new JButton("切换风格");
        changeStyle.setLocation(600, 600);
        changeStyle.setSize(100, 50);
        this.add(changeStyle);
//        int style = 0;
        //这里需要进一步细化
        changeStyle.addActionListener(e -> {
            //这里可能需要log相关的代码，不需要了，只要使用原有model，就不会改变
            //但是每个用户的style还没有储存
            StyleFrame styleFrame = new StyleFrame("风格面板", user, GameFrame.this, modelIN, style);
            this.setVisible(false);
            styleFrame.setVisible(true);

            System.out.println(user + " changed the style to ^^^^");
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
        boardPanel.label = label;
        this.add(boardPanel);
//            chessFrame.pack();//大小适于内容
        this.setSize(800, 700);
        this.setLocationRelativeTo(null);
        //chessFrame.setVisible(true);
    }


    public void setStyle(int style){
        this.style = style;
    }

    public ChessBoardModel getModel(){
        return modelIN;
    }

    private void deleteFile(){
        try{
            File fileToDelete = new File("UserData/游客6060/游客6060.txt");
            if(fileToDelete.exists()){
                if(fileToDelete.delete()){
                    System.out.println("visitor log successfully deleted!");
                }else{
                    System.out.println("visitor log deleting failed!");
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }




}


