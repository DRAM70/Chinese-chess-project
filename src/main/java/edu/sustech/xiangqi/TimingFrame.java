package edu.sustech.xiangqi;

import edu.sustech.xiangqi.model.ChessBoardModel;
import edu.sustech.xiangqi.ui.ChessBoardPanel;
import edu.sustech.xiangqi.ui.Style;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TimingFrame extends JFrame{
    public static String user;
    //    private int style = 0;
    public static JLabel label;
    public static JLabel label2;
    public static JLabel redTimeLabel;
    public static JLabel blackTimeLabel;
    private ChessBoardModel modelIN;//当前全局可使用的棋盘,请使用这个
//    private TimeBox timeBox = new TimeBox(, );
    public static int redTime;
    public static int blackTime;


    private static TimingFrame instance;
    private static String title;
    private static ChessBoardModel preModel;
    private static ChessBoardModel aiModel;
    private static int style;


    public TimingFrame(String title, String user, ChessBoardModel preModel, ChessBoardModel aiModel, ChessBoardModel timingModel, int style){
        super("计时对战");
        //上面是login的界面，下面是象棋的界面
        this.user = user;
        this.title = title;
        this.preModel = preModel;
        this.aiModel = aiModel;
        this.style = style;


        ChessBoardModel model = new ChessBoardModel(user, true, 3);

        if(timingModel != null){
            model = timingModel;
            modelIN = model;
//            timeBox = new TimeBox(timingModel.redTime, timingModel.blackTime, timingModel);
        }else{
            redTime = ToolBox.readTime(true);
            blackTime = ToolBox.readTime(false);
            model.redTime = redTime;
            model.blackTime = blackTime;
            modelIN = model;
//            timeBox = new TimeBox(600, 600, modelIN);
        }
        TimeBox timeBox = new TimeBox(modelIN.redTime, modelIN.blackTime, modelIN);
        timeBox.isRedTurn = modelIN.isRedTurn();



//        if(timingModel != null){
//            modelIN = timingModel;
//
//        }
//        redTime = ToolBox.readTime(true);
//        blackTime = ToolBox.readTime(false);
        Style[] styleList = Style.values();
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        instance = this;
        ChessBoardPanel boardPanel = new ChessBoardPanel(model, style);

        //关闭程序时，同时删除可能存在的游客6060
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                if(!timeBox.isPause){
                    timeBox.isPause = !timeBox.isPause;
                    label2.setText("已暂停");
                    boardPanel.isClickable = false;
                }
                if(ChoiceBox.choiceBox("退出确认", "要退出吗？（当前棋局会自动保存）")){
                    ToolBox.tempEnd(user, timingModel);
                    ToolBox.deleteVisitorFile();
                    dispose();
                    System.exit(0);
                }
            }
        });
        //listener结束


        //model.setUser(user);
        //这里的setUser在model初始化之后无效，为什么，因为在测试中使用logWriter时user还没有被赋值，已解决


        label = new JLabel("你好，" + user);
        if(modelIN.isRedTurn()){
            label.setText("红方执子");
        }else{
            label.setText("黑方执子");
        }
        label.setForeground(styleList[style].getLabelColor());
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setLocation(520, 50);
        label.setSize(300, 50);
        this.add(label);//先添加的后绘制

        label2 = new JLabel("你好，" + user);
        if(timeBox.isPause){
            label2.setText("已暂停");
            boardPanel.isClickable = false;
        }
        label2.setForeground(styleList[style].getLabelColor());
        label2.setHorizontalAlignment(SwingConstants.CENTER);
        label2.setLocation(520, 100);
        label2.setSize(300, 50);
        this.add(label2);//先添加的后绘制

        JLabel blackLabel = new JLabel("黑方倒计时");
        blackLabel.setForeground(styleList[style].getLabelColor());
        blackLabel.setHorizontalAlignment(SwingConstants.CENTER);
        blackLabel.setLocation(520, 150);
        blackLabel.setSize(300, 50);
        this.add(blackLabel);//先添加的后绘制

        blackTimeLabel = new JLabel("--:--");
        blackTimeLabel.setText(TimeBox.formatTime(blackTime));
        blackTimeLabel.setForeground(styleList[style].getLabelColor());
        blackTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        blackTimeLabel.setLocation(520, 180);
        blackTimeLabel.setSize(300, 50);
        this.add(blackTimeLabel);//先添加的后绘制

        redTimeLabel = new JLabel("--:--");
        redTimeLabel.setText(TimeBox.formatTime(redTime));
        redTimeLabel.setForeground(Color.RED);
        //redLabel.setForeground(styleList[style].getLabelColor());
        redTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        redTimeLabel.setLocation(520, 210);
        redTimeLabel.setSize(300, 50);
        this.add(redTimeLabel);//先添加的后绘制

        JLabel redLabel = new JLabel("红方倒计时");
        redLabel.setForeground(Color.RED);
        //redLabel.setForeground(styleList[style].getLabelColor());
        redLabel.setHorizontalAlignment(SwingConstants.CENTER);
        redLabel.setLocation(520, 240);
        redLabel.setSize(300, 50);
        this.add(redLabel);//先添加的后绘制

        JButton pause = new JButton("暂停/继续");
        pause.setLocation(620, 300);
        pause.setSize(120, 50);
        this.add(pause);//先添加的后绘制
        pause.addActionListener(e -> {
//            System.out.println("hhhhhh");
            if(!timeBox.isPause){
                label2.setText("已暂停");
                boardPanel.isClickable = false;
            }else{
                label2.setText("已继续");
                boardPanel.isClickable = true;
            }
            timeBox.isPause = !timeBox.isPause;

        });


        JButton button = new JButton("认输");
        button.setLocation(620, 400);
        button.setSize(120, 50);
        this.add(button);//先添加的后绘制
        button.addActionListener(e -> {
            System.out.println("hhhhhh");

            //这里面还要添加认输确认，添加到log

            if(!timeBox.isPause){
                timeBox.isPause = !timeBox.isPause;
                label2.setText("已暂停");
                boardPanel.isClickable = false;
            }
            if(ChoiceBox.choiceBox("结束确认", "要认输吗？")){
                ToolBox.confirmToEnd(user, 3);
                MenuFrame menuFrame = new MenuFrame(title, user, style, preModel, aiModel, null);
                this.setVisible(false);
                menuFrame.setVisible(true);
            }

        });


        JButton reset = new JButton("重开棋局");
        reset.setLocation(620, 500);
        reset.setSize(120, 50);
        this.add(reset);
        reset.addActionListener(e -> {
            if(!timeBox.isPause){
                timeBox.isPause = !timeBox.isPause;
                label2.setText("已暂停");
                boardPanel.isClickable = false;
            }
            if(ChoiceBox.choiceBox("重开确认", "确定要开始新棋局吗？（当前棋局会自动结束）")){
                ToolBox.confirmToEnd(user, 3);
                //GameFrame newFrame = new GameFrame("中国象棋", user, null, aiModel, timingModel, style);
                TimingFrame newFrame = new TimingFrame(title, user, preModel, aiModel, null, style);
                this.setVisible(false);
                newFrame.setVisible(true);
                //这里可能还要添加存储log相关的代码



                System.out.println("A new TimingFrame for " + user);
            }
        });

//        JButton retractPiece = new JButton("悔棋");
//        retractPiece.setLocation(600, 500);
//        retractPiece.setSize(120, 50);
//        this.add(retractPiece);
//        retractPiece.addActionListener(e -> {
//            //这里可能需要log相关的代码
//            System.out.println(user + " retracted a piece^^^^");
//
//
////            modelIN.checkMove(12, 5, 0);
//        });

        JButton backButton = new JButton("返回菜单");
        backButton.setLocation(620, 600);
        backButton.setSize(120, 50);
        this.add(backButton);
//        int style = 0;
        //这里需要进一步细化
        backButton.addActionListener(e -> {
            if(!timeBox.isPause){
                timeBox.isPause = !timeBox.isPause;
                label2.setText("已暂停");
                boardPanel.isClickable = false;
            }
            MenuFrame menuFrame = new MenuFrame(title, user, style, preModel, aiModel, modelIN);
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



        boardPanel.label = label2;
        this.add(boardPanel);
//            chessFrame.pack();//大小适于内容
        this.setSize(800, 700);
        this.setLocationRelativeTo(null);
        //chessFrame.setVisible(true);
    }


    public ChessBoardModel getModel(){
        return modelIN;
    }


    public static TimingFrame getInstance(){
        return instance;
    }


    public void end(){
        ToolBox.confirmToEnd(user, 3);
        MenuFrame menuFrame = new MenuFrame(title, user, style, preModel, aiModel, null);
        this.setVisible(false);

        menuFrame.setVisible(true);
        NoticeBox noticeBox = new NoticeBox("提示", user, style, "计时结束！");
        noticeBox.setVisible(true);
    }





}


