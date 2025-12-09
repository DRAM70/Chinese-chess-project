package edu.sustech.xiangqi;

import edu.sustech.xiangqi.model.ChessBoardModel;
import edu.sustech.xiangqi.ui.Style;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class MenuFrame extends JFrame{
    private String user;
    private int style;
    public Font defaultFont = new Font("微软雅黑", Font.BOLD, 18);



    //  public JFrame chessFrame;

    public MenuFrame(String title, String user, int style, ChessBoardModel preModel, ChessBoardModel aiModel, ChessBoardModel timingModel){
        super(title);
        UIManager.put("Label.font", defaultFont);
        UIManager.put("Button.font", defaultFont);
        //上面是login的界面，下面是象棋的界面
        this.user = user;
        this.style = style;
        this.setLayout(null);
        this.setSize(500, 1000);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                String a = "";
                if(preModel != null){
                    a = "（未完成的棋局会自动保存）";
                }
                if(ChoiceBox.choiceBox("退出确认", "要退出吗？" + a)){
                    ToolBox.tempEnd(user);
                    ToolBox.deleteVisitorFile();
                    dispose();
                    System.exit(0);
                }

            }
        });
        Style[] styleList = Style.values();
        getContentPane().setBackground(styleList[style].getBackgroundColor());
        //这里可能还要修改来适应可能的图片
        //修改此处注意修改panel的相同方法

//        JPanel panel = new JPanel();
//        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
//        panel.setLocation(75, 50);

        JLabel welcomeLabel = new JLabel();
        welcomeLabel.setForeground(styleList[style].getLabelColor());
        welcomeLabel.setLocation(150, 30);
        welcomeLabel.setSize(200, 50);
        welcomeLabel.setText("欢迎，" + user);
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setVerticalAlignment(SwingConstants.CENTER);
//        panel.add(Box.createVerticalGlue());
//        panel.add(welcomeLabel);
//        panel.add(Box.createVerticalGlue());
//        this.add(panel);
        this.add(welcomeLabel);



        JButton newGameButton = new JButton("新游戏");
        newGameButton.setLocation(50, 100);
        newGameButton.setSize(400, 50);
        this.add(newGameButton);
        newGameButton.addActionListener(e -> {
            String relativePath = "UserData/" + user + "/" + user +"Temp.txt";
            Path path = Paths.get(relativePath).toAbsolutePath().normalize();

            try{
                if(Files.exists(path)){
                    if(Files.size(path) != 0 || preModel != null){
                        if(ChoiceBox.choiceBox("新游戏确认", "存档上一盘并开始新游戏吗？")){
                            ToolBox.confirmToEnd(user, 1);
                            GameFrame chessFrame = new GameFrame(title, user, null, aiModel, timingModel, style);
                            this.setVisible(false);
                            chessFrame.setVisible(true);
                            createFolder();
                        }
                    }else{
                        GameFrame chessFrame = new GameFrame(title, user, null, aiModel, timingModel, style);
                        this.setVisible(false);
                        chessFrame.setVisible(true);
                        createFolder();
                    }
                }else{
                    GameFrame chessFrame = new GameFrame(title, user, null, aiModel, timingModel, style);
                    this.setVisible(false);
                    chessFrame.setVisible(true);
                    createFolder();
                }
            }catch (IOException ne){
                System.out.println("???");
            }
        });


        JButton continueButton = new JButton("继续游戏");
        continueButton.setLocation(50, 200);
        continueButton.setSize(400, 50);
        this.add(continueButton);
        continueButton.addActionListener(e -> {
            //这里可能需要log相关的代码
//            newStyle = 1;
//            this.setVisible(false);
//            StyleFrame styleFrame = new StyleFrame("风格面板", originalFrame.user, originalFrame, originalFrame.getModel(), newStyle);
//            styleFrame.setVisible(true);
//            System.out.println(user + " tried style 2");
//            model.pauseButton(true);
            if(preModel != null){//需要修改
                GameFrame gameFrame = new GameFrame(title, user, preModel, aiModel, timingModel, style);
                this.setVisible(false);
                gameFrame.setVisible(true);
            }else{
                NoticeBox noticeBox = new NoticeBox("提示", user, style, "您需要开始新棋局！");
                noticeBox.setVisible(true);
            }

        });


        JButton aiChessButton = new JButton("AI对战");
        aiChessButton.setLocation(50, 300);
        aiChessButton.setSize(400, 50);
        this.add(aiChessButton);
        aiChessButton.addActionListener(e -> {
            System.out.println("这个功能还没有完成");
            String relativePath = "UserData/" + user + "/" + user +"AITemp.txt";
            Path path = Paths.get(relativePath).toAbsolutePath().normalize();

            try{
                if(Files.exists(path)){
                    if(Files.size(path) != 0 || aiModel != null){
                        if(ChoiceBox.choiceBox("新游戏确认", "存档上一盘并开始新游戏吗？")){
                            ToolBox.confirmToEnd(user, 2);
                            AIFrame aiFrame = new AIFrame(title, user, preModel, null, timingModel, style);
                            this.setVisible(false);
                            aiFrame.setVisible(true);
                            createFolder();
                        }else{
                            AIFrame aiFrame = new AIFrame(title, user, preModel, aiModel, timingModel, style);
                            this.setVisible(false);
                            aiFrame.setVisible(true);
                        }
                    }else{
                        AIFrame aiFrame = new AIFrame(title, user, preModel, null, timingModel, style);
                        this.setVisible(false);
                        aiFrame.setVisible(true);
                        createFolder();
                    }
                }else{
                    AIFrame aiFrame = new AIFrame(title, user, preModel, null, timingModel, style);
                    this.setVisible(false);
                    aiFrame.setVisible(true);
                    createFolder();
                }
            }catch (IOException ne){
                System.out.println("???");
            }

        });

        JButton timingChessButton = new JButton("计时模式");
        timingChessButton.setLocation(50, 400);
        timingChessButton.setSize(400, 50);
        this.add(timingChessButton);
        timingChessButton.addActionListener(e -> {
            System.out.println("这个功能还没有完成");
            String relativePath = "UserData/" + user + "/" + user +"TimingTemp.txt";
            Path path = Paths.get(relativePath).toAbsolutePath().normalize();

            try{
                if(Files.exists(path)){
                    if(Files.size(path) != 0 || timingModel != null){
                        if(ChoiceBox.choiceBox("新游戏确认", "存档上一盘并开始新游戏吗？")){
                            ToolBox.confirmToEnd(user, 3);
                            TimingFrame timingFrame = new TimingFrame(title, user, preModel, aiModel, null, style);
                            this.setVisible(false);
                            timingFrame.setVisible(true);
                            createFolder();
                        }else{
                            TimingFrame timingFrame = new TimingFrame(title, user, preModel, aiModel, timingModel, style);
                            this.setVisible(false);
                            timingFrame.setVisible(true);
                        }
                    }else{
                        TimingFrame timingFrame = new TimingFrame(title, user, preModel, aiModel, null, style);
                        this.setVisible(false);
                        timingFrame.setVisible(true);
                        createFolder();
                    }
                }else{
                    TimingFrame timingFrame = new TimingFrame(title, user, preModel, aiModel, null, style);
                    this.setVisible(false);
                    timingFrame.setVisible(true);
                    createFolder();
                }
            }catch (IOException ne){
                System.out.println("???");
            }

        });

        JButton lastChessButton = new JButton("上一局回放");
        lastChessButton.setLocation(50, 500);
        lastChessButton.setSize(400, 50);
        this.add(lastChessButton);
        lastChessButton.addActionListener(e -> {
            if(checkLogLiteExistence()){
                if(decoder()){
                    ReplayFrame replayFrame = new ReplayFrame(title, user, preModel, aiModel, timingModel, style);
                    this.setVisible(false);
                    replayFrame.setVisible(true);
                }else{
                    deleteBrokenLog();
                    NoticeBox noticeBox = new NoticeBox("警告", user, style, "您的回放已损坏！已清理！");
                    noticeBox.setVisible(true);
                }
            }
        });

        JButton styleButton = new JButton("切换皮肤");
        styleButton.setLocation(50, 600);
        styleButton.setSize(400, 50);
        this.add(styleButton);
        styleButton.addActionListener(e -> {
            StyleFrame styleFrame = new StyleFrame(title, user, style, preModel, aiModel, timingModel);
            this.setVisible(false);
            styleFrame.setVisible(true);

            System.out.println(user + " is now in the styleFrame");
        });

        JButton exitButton = new JButton("退出");
        exitButton.setLocation(50, 700);
        exitButton.setSize(400, 50);
        this.add(exitButton);
        exitButton.addActionListener(e -> {
            String a = "";
            if(preModel != null){
                a = "（未完成的棋局会自动保存）";
            }
            if(ChoiceBox.choiceBox("退出确认", "要退出吗？" + a)){
                ToolBox.tempEnd(user);
                LoginFrame loginFrame = new LoginFrame("中国象棋 登录界面");
                this.setVisible(false);
                ToolBox.deleteVisitorFile();
                loginFrame.setVisible(true);
            }

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

    private void createFolder(){
        Path path = Paths.get("UserData/" + user);
        try{
            Files.createDirectories(path);
            System.out.println(user + "'s log folder ready!");
        }catch(IOException e){
            System.err.println(e.getMessage());
        }
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

    private boolean checkLogLiteExistence(){
        File file = new File("UserData/" + user + "/" + user +".txt");
        if(!file.exists()){
            NoticeBox noticeBox = new NoticeBox("提示", user, style, "您还没有存档，快去玩新游戏吧！");
            noticeBox.setVisible(true);
            return false;
        }
        return true;
    }

    private boolean decoder(){
        int code = 0;

        File moveFile = new File("UserData/" + user + "/" + user +".txt");


        if (!moveFile.setWritable(true)) {
            System.out.println(user + ".txt can't be set readable!");
        }
        ///
        try(BufferedReader reader = new BufferedReader(new FileReader(moveFile))){

            String currentLine;
            while((currentLine = reader.readLine()) != null){
                code += Integer.parseInt(currentLine);
            }
            reader.close();
        }catch(Exception e){
            System.out.println("encoder failed!");
        }

        int tempCode = (code-13*17)*31;
        int logCode = logCodeKey();

        if(tempCode == logCode){
            return true;
        }else{
            return false;
        }

    }

    private int logCodeKey(){
        File file = new File(".\\UserInfo.txt");
        Scanner in;
        try{
            in = new Scanner(file);
            while(in.hasNextLine()){
                String existingUsername = in.nextLine();
                if(user.equals(existingUsername)){
                    in.nextLine();
                    in.nextLine();
                    return Integer.parseInt(in.nextLine());
                }else{
                    in.nextLine();
                    in.nextLine();
                    in.nextLine();
                    in.nextLine();
                    in.nextLine();
                    in.nextLine();
                }
            }
            System.out.println(user + " doesn't exist as a username!");
            return -1;
        }catch(FileNotFoundException e){
            System.out.println("File UserInfo.txt not found!");
        }
        return -1;
    }

    private void deleteBrokenLog(){
        File file = new File("UserData/" + user + "/" + user +".txt");
        if(file.delete()){
            System.out.println(user + ".txt failed to delete!");
        }else{
            System.out.println(user + ".txt successfully deleted!");
        }
    }

}


