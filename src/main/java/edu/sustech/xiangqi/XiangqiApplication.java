package edu.sustech.xiangqi;


import javax.swing.*;

public class XiangqiApplication {
    public static String user;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame("中国象棋 登陆界面");
        });
    }

}
