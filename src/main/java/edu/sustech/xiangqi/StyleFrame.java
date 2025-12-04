package edu.sustech.xiangqi;

import edu.sustech.xiangqi.model.ChessBoardModel;
import edu.sustech.xiangqi.ui.Style;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.nio.Buffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

public class StyleFrame extends JFrame{
    private String user;
    private int newStyle;


  //  public JFrame chessFrame;

    public StyleFrame(String title, String user, int style, ChessBoardModel preModel){
        super("风格面板");
            //上面是login的界面，下面是象棋的界面
        this.user = user;
        this.newStyle = style;
        this.setLayout(null);
        this.setSize(500, 500);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setLocationRelativeTo(null);


        //关闭程序时，默认确认当前皮肤，并返回棋盘
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                setVisible(false);
                MenuFrame menuFrame = new MenuFrame(title, user, newStyle, preModel);
                menuFrame.setVisible(true);


                System.out.println(user + " confirmed new style " + newStyle);
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
            StyleFrame styleFrame = new StyleFrame(title, user, newStyle, preModel);
            styleFrame.setVisible(true);
            lineRewriter(0);
            System.out.println(user + " tried style 1");
//            model.pauseButton(false);
        });


        JButton style2 = new JButton("风格2");
        style2.setLocation(150, 100);
        style2.setSize(100, 50);
        this.add(style2);
        style2.addActionListener(e -> {
            //这里可能需要log相关的代码
            newStyle = 1;
            this.setVisible(false);
            StyleFrame styleFrame = new StyleFrame(title, user, newStyle, preModel);
            styleFrame.setVisible(true);
            lineRewriter(1);
            System.out.println(user + " tried style 2");
//            model.pauseButton(true);
        });


        JButton confirm = new JButton("返回");
        confirm.setLocation(60, 400);
        confirm.setSize(100, 50);
        this.add(confirm);
        confirm.addActionListener(e -> {
            this.setVisible(false);
            MenuFrame menuFrame = new MenuFrame(title, user, newStyle, preModel);
            menuFrame.setVisible(true);




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


    private void lineRewriter(int a){
        File originalFile = new File(".\\UserInfo.txt");
        File tmpFile = new File(".\\UserInfoTemp.txt");


        if (!originalFile.setWritable(true)) {
            System.out.println("UserInfo can't be set readable!");
        }

        if (!tmpFile.setWritable(true)) {
            System.out.println("UserInfo can't be set readable!");
        }




        try(BufferedReader reader = new BufferedReader(new FileReader(originalFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tmpFile))){

            String currentLine;

            while((currentLine = reader.readLine()) != null){
                writer.write(currentLine);
                writer.newLine();
                if(user.equals(currentLine)){
                    writer.write(reader.readLine());
                    writer.newLine();

                    reader.readLine();
                    writer.write("" + a);
                    writer.newLine();

                    writer.write(reader.readLine());
                    writer.newLine();

                    writer.write(reader.readLine());
                    writer.newLine();

                    writer.write(reader.readLine());
                    writer.newLine();

                    writer.write(reader.readLine());
                    writer.newLine();
                }else{
                    writer.write(reader.readLine());
                    writer.newLine();
                    writer.write(reader.readLine());
                    writer.newLine();
                    writer.write(reader.readLine());
                    writer.newLine();
                    writer.write(reader.readLine());
                    writer.newLine();
                    writer.write(reader.readLine());
                    writer.newLine();
                    writer.write(reader.readLine());
                    writer.newLine();
                }
            }
        }catch(Exception e){
            System.out.println("LineRewriter failed!");
        }

        try{
            Path filePath = Paths.get(".\\UserInfo.txt");
            Files.newBufferedWriter(filePath, StandardOpenOption.TRUNCATE_EXISTING).close();
            System.out.println("UserInfo.txt is empty now!");
        }catch(IOException e){
            System.out.println("Fail to empty UserInfo.txt!");
        }




        try(BufferedReader reader = new BufferedReader(new FileReader(tmpFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(originalFile))){





            String currentLine;
//            int i= 1;

            while((currentLine = reader.readLine()) != null){
                writer.write(currentLine);
                writer.newLine();
            }
            writer.flush();
            writer.close();
            reader.close();

            if(!tmpFile.delete()){
                System.out.println("File deletion failed!");
                throw new IOException("File deletion failed!");
            }
        }catch(Exception e){
            System.out.println("LineRewriter failed!");
        }
    }
}


