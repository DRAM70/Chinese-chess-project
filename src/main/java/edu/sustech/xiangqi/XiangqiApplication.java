package edu.sustech.xiangqi;

import edu.sustech.xiangqi.model.ChessBoardModel;
import edu.sustech.xiangqi.ui.ChessBoardPanel;

import javax.swing.*;

public class XiangqiApplication {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame loginFrame = new JFrame("login");
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


            JButton loginIn = new JButton("login in");
            loginIn.setSize(100, 30);
            loginIn.setLocation(200, 250);
            loginFrame.add(loginIn);

            JButton register = new JButton("register");
            register.setSize(100, 30);
            register.setLocation(200, 300);
            loginFrame.add(register);

            loginFrame.setVisible(true);

            JFrame frame = new JFrame("中国象棋");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


            JLabel label = new JLabel("开始");
            label.setLocation(600, 100);
            label.setSize(100, 50);
            frame.add(label);//先添加的后绘制

            JButton button = new JButton("start");
            button.setLocation(600, 200);
            button.setSize(100, 50);
            button.addActionListener(e -> {
                label.setText("hh");
                System.out.println("hhhhhh");
            });
            loginIn.addActionListener(e -> {
                String a = username.getText();
                String b = passcode.getText();
                isInUserList(a, b);
                label.setText(a);
                loginFrame.setVisible(false);
                frame.setVisible(true);
            });
            frame.add(button);//先添加的后绘制

            ChessBoardModel model = new ChessBoardModel();
            ChessBoardPanel boardPanel = new ChessBoardPanel(model);
            boardPanel.label = label;

            frame.add(boardPanel);
//            frame.pack();//大小适于内容
            frame.setSize(800, 700);
            frame.setLocationRelativeTo(null);
            //frame.setVisible(true);
        });
    }


    public static boolean isInUserList(String name, String passcode){

        return false;
    }
}
