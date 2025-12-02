package edu.sustech.xiangqi;


import edu.sustech.xiangqi.audio.BackgroundMusic;

import javax.swing.*;

public class XiangqiApplication {
    public static String user;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BackgroundMusic.playLoop();
            LoginFrame loginFrame = new LoginFrame("中国象棋 登录界面");
        });
    }

}
