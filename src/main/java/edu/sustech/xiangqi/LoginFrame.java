package edu.sustech.xiangqi;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.*;
import java.util.Scanner;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class LoginFrame extends JFrame{
    public String user;
    public static JLabel loginStatusLabel;
    public RegisterFrame registerFrame = new RegisterFrame("中国象棋 注册页面");
    public Font defaultFont = new Font("微软雅黑", Font.BOLD, 18);



    public LoginFrame(String title){

        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(500, 500);

        JLabel background = new JLabel(new ImageIcon(".\\src\\main\\resources\\back1111.png"));
        background.setSize(500, 500);
        background.setLocation(0, 100);


        JTextField username = new JTextField();
        username.setLocation(150, 100);
        username.setSize(200, 50);
        username.setFont(new Font(defaultFont.getName(), defaultFont.getStyle(), 16));
        this.add(username);

        JLabel usernameLabel = new JLabel();
        usernameLabel.setLocation(75, 100);
        usernameLabel.setSize(200, 50);
        usernameLabel.setText("用户名");
        usernameLabel.setFont(defaultFont);
        this.add(usernameLabel);

        JTextField passcode = new JTextField();
        passcode.setLocation(150, 175);
        passcode.setSize(200, 50);
        passcode.setFont(new Font(defaultFont.getName(), defaultFont.getStyle(), 16));
        this.add(passcode);

        JLabel passcodeLabel = new JLabel();
        passcodeLabel.setLocation(75, 175);
        passcodeLabel.setSize(200, 50);
        passcodeLabel.setText("密码");
        passcodeLabel.setFont(defaultFont);
        this.add(passcodeLabel);

         loginStatusLabel = new JLabel();
        loginStatusLabel.setLocation(155, 50);
        loginStatusLabel.setSize(200, 50);
        loginStatusLabel.setText("  ");
        loginStatusLabel.setFont(defaultFont);
        this.add(loginStatusLabel);

        JButton loginIn = new JButton("登录");
        loginIn.setSize(110, 30);
        loginIn.setLocation(195, 250);
        loginIn.setFont(defaultFont);
        this.add(loginIn);

        JButton register = new JButton("注册");
        register.setSize(110, 30);
        register.setLocation(195, 300);
        register.setFont(defaultFont);
        this.add(register);

        JButton visitor = new JButton("游客登录");
        visitor.setSize(110, 30);
        visitor.setLocation(195, 350);
        visitor.setFont(defaultFont);
        this.add(visitor);

        this.add(background);

        this.setVisible(true);
        GameFrame chessFrame = new GameFrame("中国象棋");

        loginIn.addActionListener(e -> {
            String a = username.getText();
            String b = passcode.getText();
            if(a.isEmpty()){
                loginStatusLabel.setLocation(190, 50);
                loginStatusLabel.setText("请输入用户名！");
//                loginStatusLabel.setFont(defaultFont);
                return;
            }
            if(b.isEmpty()){
                loginStatusLabel.setLocation(195, 50);
                loginStatusLabel.setText("请输入密码！");
//                loginStatusLabel.setFont(defaultFont);
                return;
            }
            if(isInUserListUP(a, b)){
                    chessFrame.label.setText(a);
                    user = a;
                this.setVisible(false);
                chessFrame.setVisible(true);
                chessFrame.user = a;
            }else{
                System.out.println("entry denied");
                if(isInUserListU(a, b)){
                    loginStatusLabel.setLocation(210, 50);
                    loginStatusLabel.setText("密码错误！");
                }else{
                    loginStatusLabel.setLocation(200, 50);
                    loginStatusLabel.setText("用户不存在！");
                }
            }
        });


        visitor.addActionListener(e -> {
            user = "游客";
            this.setVisible(false);
            chessFrame.setVisible(true);
            chessFrame.label.setText("游客");
            chessFrame.user = "游客";
        });


//        JPanel panel = new JPanel();
//        panel.setFocusable(true); // 重要：设置面板可获得焦点
//        panel.requestFocusInWindow(); // 请求焦点
//
//        panel.addKeyListener(new KeyListener() {
//            @Override
//            public void keyPressed(KeyEvent e) {
//                // 检测按下的键是否是 Enter 键
//                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
//                    statusLabel.setText("Enter 键被按下!");
//                    System.out.println("Enter 键被按下");
//                }
//            }
//
//            @Override
//            public void keyReleased(KeyEvent e) {
//                // 检测释放的键是否是 Enter 键
//                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
//                    statusLabel.setText("Enter 键已释放");
//                    System.out.println("Enter 键释放");
//                }
//            }
//
//            @Override
//            public void keyTyped(KeyEvent e) {
//                // 这个事件在按键被键入时触发（按下并释放）
//                // 对于功能键如 Enter，通常不会触发此事件
//            }
//        });

        register.addActionListener(e -> {
            this.setVisible(false);
            RegisterFrame registerFrame = new RegisterFrame("中国象棋 注册页面");
            registerFrame.setVisible(true);
        });
    }



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
