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
import java.util.ArrayList;
import java.util.Scanner;

public class ReplayFrame extends JFrame{
    public String user;
    private int style;
    private static JLabel label;
    private ChessBoardModel modelIN;//当前全局可使用的棋盘？？？？？？？？//应该确定了
    private ArrayList<String> moveList = new ArrayList<>();
    private int stepIndex = 0;


    public ReplayFrame(String title, String user, ChessBoardModel preModel, int style){
        super("回放");
        //上面是login的界面，下面是象棋的界面
        this.user = user;
        this.style = style;
//        if(preModel != null){
//            modelIN = preModel;
//        }
        modelIN = new ChessBoardModel(user, false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        Style[] styleList = Style.values();


        //关闭程序时，同时删除可能存在的游客6060
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                MenuFrame menuFrame = new MenuFrame(title, user, style);
                setVisible(false);
                menuFrame.setVisible(true);

            }
        });
        //listener结束

        //ChessBoardModel model = new ChessBoardModel(user, false);
//        if(preModel != null){
//            model = preModel;
//        }else{
//            modelIN = model;
//        }
        //model.setUser(user);
        //这里的setUser在model初始化之后无效，为什么，因为在测试中使用logWriter时user还没有被赋值，已解决


        label = new JLabel(user);
        label.setForeground(styleList[style].getLabelColor());
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setLocation(520, 100);
        label.setSize(300, 50);
        this.add(label);//先添加的后绘制

        JButton lastStepButton = new JButton("上一步");
        lastStepButton.setLocation(600, 200);
        lastStepButton.setSize(120, 50);
        this.add(lastStepButton);//先添加的后绘制
        lastStepButton.addActionListener(e -> {
            System.out.println("ahhhhhhh   这里需要悔棋相关的代码");

            //需要判断可不可以上一步
//            if(stepIndex == 0){
//                label.setText("已经回到第一步啦！");
//            }else{
//                stepIndex--;
//                modelIN.checkMove(moveBreaker(stepIndex, 0),
//                        moveBreaker(stepIndex, 1),
//                        moveBreaker(stepIndex, 2));
//            }

        });


        JButton newtMoveButton = new JButton("下一步");
        newtMoveButton.setLocation(600, 400);
        newtMoveButton.setSize(120, 50);
        this.add(newtMoveButton);
        newtMoveButton.addActionListener(e -> {
            //需要判断可不可以下一步
            //有蓝色框框效果会更好一些
            if(stepIndex == moveList.size()){
                label.setText("已经是最后一步啦！");
            }else{
                modelIN.checkMove(moveBreaker(stepIndex, 0),
                                    moveBreaker(stepIndex, 1),
                                    moveBreaker(stepIndex, 2));
                stepIndex++;
            }


        });

        JButton retractPiece = new JButton("测试弹窗");
        retractPiece.setLocation(600, 500);
        retractPiece.setSize(120, 50);
        this.add(retractPiece);
        retractPiece.addActionListener(e -> {
            //这里可能需要log相关的代码
//            System.out.println(user + " retracted a piece^^^^");
//
//
//            modelIN.checkMove(12, 5, 0);

            NoticeFrame noticeFrame = new NoticeFrame(title, user, style, "您目前没有存档！");
            noticeFrame.setVisible(true);
        });

        JButton backButton = new JButton("返回菜单");
        backButton.setLocation(600, 600);
        backButton.setSize(120, 50);
        this.add(backButton);
//        int style = 0;
        //这里需要进一步细化
        backButton.addActionListener(e -> {
            MenuFrame menuFrame = new MenuFrame(title, user, style);
            this.setVisible(false);
            menuFrame.setVisible(true);

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


        ChessBoardPanel boardPanel = new ChessBoardPanel(modelIN, style);
        boardPanel.label = label;
        this.add(boardPanel);
//            chessFrame.pack();//大小适于内容
        this.setSize(800, 700);
        this.setLocationRelativeTo(null);
        readRecord();
        //chessFrame.setVisible(true);
    }


//    public ChessBoardModel getModel(){
//        return modelIN;
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

    private void readRecord(){
        //需要实现checklog来保证log不变




        checkLogLiteContent();
        File file = new File("UserData/" + user + "/" + user +".txt");
        try{
            Scanner sc = new Scanner(file);
            while(sc.hasNextLine()){
                String move = sc.nextLine();
                moveList.add(move);
            }
        }catch(FileNotFoundException e){
            //需要添加弹窗
            System.out.println(user + "'s log reading failed!");
        }
    }

    private void checkLogLiteContent(){
        File file = new File("UserData/" + user + "/" + user +".txt");
        //这里要判断hashcode的符合情况







    }

    private int moveBreaker(int index, int part){
        //part 0: the code for the chess
        //part 1: the column
        //part 2: the row
        String s = moveList.get(index);
        String chessIndex = "" + s.charAt(0) + s.charAt(1);
        String column = "" + s.charAt(3);
        String row = "" + s.charAt(5);
        if(part == 0){
            return Integer.parseInt(chessIndex);
        }
        if(part == 1){
            return Integer.parseInt(column);
        }
        if(part == 2){
            return Integer.parseInt(row);
        }
        return -1;
    }







}


