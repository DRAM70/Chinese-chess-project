package edu.sustech.xiangqi;

import javax.swing.*;
import java.io.*;
import java.util.Scanner;

public class RegisterFrame extends JFrame {
    


    public RegisterFrame(String name){
        //register的界面
        super(name);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(500, 500);

        JLabel background = new JLabel(new ImageIcon(".\\src\\main\\resources\\back1111.png"));
        background.setSize(500, 500);
        background.setLocation(0, 100);

        JTextField username1 = new JTextField();
        username1.setLocation(150, 100);
        username1.setSize(200, 50);
        this.add(username1);

        JLabel usernameLabel1 = new JLabel();
        usernameLabel1.setLocation(50, 100);
        usernameLabel1.setSize(200, 50);
        usernameLabel1.setText("New Username");
        this.add(usernameLabel1);

        JTextField passcode1 = new JTextField();
        passcode1.setLocation(150, 175);
        passcode1.setSize(200, 50);
        this.add(passcode1);

        JLabel passcodeLabel1 = new JLabel();
        passcodeLabel1.setLocation(50, 175);
        passcodeLabel1.setSize(200, 50);
        passcodeLabel1.setText("New Passcode");
        this.add(passcodeLabel1);

        JButton confirm = new JButton("CONFIRM");
        confirm.setSize(100, 30);
        confirm.setLocation(200, 250);
        this.add(confirm);

        JButton back = new JButton("return");
        back.setSize(80, 20);
        back.setLocation(210, 300);
        this.add(back);

        JLabel loginStatusLabel1 = new JLabel();
        loginStatusLabel1.setLocation(155, 50);
        loginStatusLabel1.setSize(200, 50);
        loginStatusLabel1.setText("  ");
        this.add(loginStatusLabel1);

        this.add(background);


        confirm.addActionListener(e -> {
            String c = username1.getText();
            String d = passcode1.getText();
            if (c.isEmpty()) {
                loginStatusLabel1.setLocation(170, 50);
                loginStatusLabel1.setText("Please enter the Username!");
                return;
            }
            if (d.isEmpty()) {
                loginStatusLabel1.setLocation(170, 50);
                loginStatusLabel1.setText("Please enter the Passcode!");
                return;
            }
            if(isInUserListU(c)){
                loginStatusLabel1.setLocation(190, 50);
                loginStatusLabel1.setText("User already existed!");
            }
            if (addNewUser(c, d)) {
                LoginFrame loginFrame = new LoginFrame("中国象棋 登录页面");
                loginFrame.setVisible(true);
                this.setVisible(false);
                LoginFrame.loginStatusLabel.setText("Please login now!");
                LoginFrame.loginStatusLabel.setLocation(200, 50);
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
