package edu.sustech.xiangqi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.Scanner;

public class RegisterFrame extends JFrame {
    public Font defaultFont = new Font("微软雅黑", Font.BOLD, 18);
    


    public RegisterFrame(String name){
        //register的界面
        super(name);
        this.setLayout(null);
        this.setSize(500, 500);
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


        ImageIcon originalIcon = new ImageIcon(".\\src\\main\\resources\\loginBackground2.jpg");
        Image scaledImage = originalIcon.getImage().getScaledInstance(500, 500, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel background = new JLabel(scaledIcon);
        background.setSize(500, 500);
        background.setLocation(0, 0);

        JTextField username1 = new JTextField();
        username1.setLocation(150, 100);
        username1.setSize(200, 50);
        username1.setFont(new Font(defaultFont.getName(), defaultFont.getStyle(), 16));
        this.add(username1);

        JLabel usernameLabel1 = new JLabel();
        usernameLabel1.setLocation(50, 100);
        usernameLabel1.setSize(200, 50);
        usernameLabel1.setText("新用户名");
        usernameLabel1.setFont(defaultFont);
        this.add(usernameLabel1);

        JTextField passcode1 = new JTextField();
        passcode1.setLocation(150, 175);
        passcode1.setSize(200, 50);
        passcode1.setFont(new Font(defaultFont.getName(), defaultFont.getStyle(), 16));
        this.add(passcode1);

        JLabel passcodeLabel1 = new JLabel();
        passcodeLabel1.setLocation(50, 175);
        passcodeLabel1.setSize(200, 50);
        passcodeLabel1.setText("新密码");
        passcodeLabel1.setFont(defaultFont);
        this.add(passcodeLabel1);

        JButton confirm = new JButton("确认");
        confirm.setSize(100, 30);
        confirm.setLocation(200, 250);
        confirm.setFont(defaultFont);
        this.add(confirm);

        JButton back = new JButton("返回");
        back.setSize(80, 20);
        back.setLocation(210, 300);
        back.setFont(defaultFont);
        this.add(back);

        JLabel loginStatusLabel1 = new JLabel();
        loginStatusLabel1.setLocation(155, 50);
        loginStatusLabel1.setSize(200, 50);
        loginStatusLabel1.setText("  ");
        loginStatusLabel1.setFont(defaultFont);
        this.add(loginStatusLabel1);

        this.add(background);


        confirm.addActionListener(e -> {
            String c = username1.getText();
            String d = passcode1.getText();
            if (c.isEmpty()) {
                loginStatusLabel1.setLocation(190, 50);
                loginStatusLabel1.setText("请输入用户名！");
                return;
            }
            if (d.isEmpty()) {
                loginStatusLabel1.setLocation(195, 50);
                loginStatusLabel1.setText("请输入密码！");
                return;
            }
            if(isInUserListU(c)){
                loginStatusLabel1.setLocation(195, 50);
                loginStatusLabel1.setText("用户已存在！");
                return;
            }
            if (addNewUser(c, d)) {
                LoginFrame loginFrame = new LoginFrame("中国象棋 登录页面");
                loginFrame.setVisible(true);
                this.setVisible(false);
                LoginFrame.loginStatusLabel.setText("请登录！");
                LoginFrame.loginStatusLabel.setFont(defaultFont);
                LoginFrame.loginStatusLabel.setLocation(220, 50);
            }
        });

        back.addActionListener(e -> {
            LoginFrame loginFrame = new LoginFrame("中国象棋 登录页面");
            loginFrame.setVisible(true);
            this.setVisible(false);
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
            System.out.println("File UserInfo.txt not found! in username and passcode check");
        }
        return false;
    }

    public static boolean isInUserListU(String name){
        File file = new File(".\\UserInfo.txt");
        Scanner in;
        try{
            in = new Scanner(file);
            while(in.hasNextLine()){
                String existingUsername = in.nextLine();
                if(name.equals(existingUsername)){
                    //这一段是展示原理
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
            System.out.println("File UserInfo.txt not found! in username search");
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
        newUserData(name);
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

    public static void newUserData(String s){
        try{


//
//            // 使用 BufferedWriter 写入文件
//            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
//
//                writer.write("使用相对路径创建的文件");
//                writer.newLine();
//                writer.write("当前时间: " + java.time.LocalDateTime.now());
//                writer.newLine();
//                writer.write("文件路径: " + file.getAbsolutePath());
//
//                System.out.println("文件创建成功!");
//                System.out.println("相对路径: " + relativePath);
//                System.out.println("绝对路径: " + file.getAbsolutePath());
//
//            } catch (IOException e) {
//                System.out.println("写入文件时出错: " + e.getMessage());
//                e.printStackTrace();
//            }
            String relativePath = "UserData/" + s + ".txt";
            File file = new File(relativePath);
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));



            writer.write(s + "\n");



            writer.flush();
            writer.close();
        }catch(IOException e){
            System.out.println("Error, file" + s + ".txt is broken!");
        }
    }

    private void deleteFile(){
        try{
            File fileToDelete = new File("UserData/游客6060.txt");
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
