package edu.sustech.xiangqi;

import edu.sustech.xiangqi.model.ChessBoardModel;
import edu.sustech.xiangqi.ui.ChessBoardPanel;

import java.io.*;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class XiangqiApplication {
    public static String user;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame loginFrame = new JFrame("中国象棋登陆界面");
            loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            loginFrame.setLayout(null);
            loginFrame.setSize(500, 500);

            JTextField username = new JTextField();
            username.setLocation(150, 100);
            username.setSize(200, 50);
            loginFrame.add(username);

            JLabel usernameLabel = new JLabel();
            usernameLabel.setLocation(75, 100);
            usernameLabel.setSize(200, 50);
            usernameLabel.setText("Username");
            loginFrame.add(usernameLabel);


            JTextField passcode = new JTextField();
            passcode.setLocation(150, 175);
            passcode.setSize(200, 50);
            loginFrame.add(passcode);

            JLabel passcodeLabel = new JLabel();
            passcodeLabel.setLocation(75, 175);
            passcodeLabel.setSize(200, 50);
            passcodeLabel.setText("Passcode");
            loginFrame.add(passcodeLabel);


            JLabel loginStatusLabel = new JLabel();
            loginStatusLabel.setLocation(155, 50);
            loginStatusLabel.setSize(200, 50);
            loginStatusLabel.setText("???");
            loginFrame.add(loginStatusLabel);


            JButton loginIn = new JButton("login in");
            loginIn.setSize(100, 30);
            loginIn.setLocation(200, 250);
            loginFrame.add(loginIn);

            JButton register = new JButton("register");
            register.setSize(100, 30);
            register.setLocation(200, 300);
            loginFrame.add(register);

            loginFrame.setVisible(true);


            //上面是login的界面，下面是象棋的界面
            JFrame chessFrame = new JFrame("中国象棋");
            chessFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


            JLabel label = new JLabel("开始");
            label.setLocation(600, 100);
            label.setSize(100, 50);
            chessFrame.add(label);//先添加的后绘制

            JButton button = new JButton("start");
            button.setLocation(600, 200);
            button.setSize(100, 50);



            loginIn.addActionListener(e -> {
                String a = username.getText();
                String b = passcode.getText();
                if(a.isEmpty()){
                    loginStatusLabel.setLocation(170, 50);
                    loginStatusLabel.setText("Please enter the Username!");
                    return;
                }
                if(b.isEmpty()){
                    loginStatusLabel.setLocation(170, 50);
                    loginStatusLabel.setText("Please enter the Passcode!");
                    return;
                }
                if(isInUserListUP(a, b)){
                    label.setText(a);
                    user = a;
                    loginFrame.setVisible(false);
                    chessFrame.setVisible(true);
                }else{
                    System.out.println("entry denied");
                    if(isInUserListU(a, b)){
                        loginStatusLabel.setLocation(205, 50);
                        loginStatusLabel.setText("Wrong passcode!");
                    }else{
                        loginStatusLabel.setLocation(195, 50);
                        loginStatusLabel.setText("User doesn't exist!");
                    }
                }
            });


            button.addActionListener(e -> {
                System.out.println("hhhhhh");
            });


            chessFrame.add(button);//先添加的后绘制

            ChessBoardModel model = new ChessBoardModel();
            ChessBoardPanel boardPanel = new ChessBoardPanel(model);
            boardPanel.label = label;

            chessFrame.add(boardPanel);
//            chessFrame.pack();//大小适于内容
            chessFrame.setSize(800, 700);
            chessFrame.setLocationRelativeTo(null);
            //chessFrame.setVisible(true);
        });
    }

    //总体判断用户名和密码，用户名是否存在的单独判断在后面
    public static boolean isInUserListUP(String name, String passcode){
        File file = new File(".\\UserInfo.txt");
        Scanner in;
        try{
            in = new Scanner(file);
            while(in.hasNextLine()){
                String existingUsername = in.nextLine();
                if(name.equals(existingUsername)){
                    String existingPasscode = in.nextLine();
                    if(passcode.equals(existingPasscode)){
                        return true;
                    }
                }else{
                    in.nextLine();
                }
            }
            return false;
        }catch(FileNotFoundException e){
            System.out.println("File UserInfo.txt not found!");
        }
        return false;
    }

    public static boolean isInUserListU(String name, String passcode){
        File file = new File(".\\UserInfo.txt");
        Scanner in;
        try{
            in = new Scanner(file);
            while(in.hasNextLine()){
                String existingUsername = in.nextLine();
                if(name.equals(existingUsername)){
//                    String existingPasscode = in.nextLine();
//                    if(passcode.equals(existingPasscode)){
//                        return true;
//                    }
                    return true;
                }else{
                    in.nextLine();
                }
            }
            System.out.println(name + " doesn't exist as a username!");
            return false;
        }catch(FileNotFoundException e){
            System.out.println("File UserInfo.txt not found!");
        }
        return false;
    }


    /*这个是在添加新用户时使用的，直接调用的话可以执行添加，
    返回的boolean值会告诉是否存在该用户，若存在（用户名相同），则返回false，不存在并且添加成功则返回true
    为注册的按钮添加事件监听，新页面中使用这个方法
     */
    public static boolean addNewUser(String name, String passcode){
        if(isInUserListUP(name, passcode)){
            return false;
        }
        write(name);
        write(passcode);
        return true;
    }

    public static void write(String s){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("UserInfo.txt", true));
            writer.write(s + "\n");
            writer.flush();
            writer.close();
        }catch(IOException e){
            System.out.println("Error, writing " + s + " to UserInfo.txt failed!");
        }
    }
}
