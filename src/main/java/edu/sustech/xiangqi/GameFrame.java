package edu.sustech.xiangqi;

import edu.sustech.xiangqi.model.ChessBoardModel;
import edu.sustech.xiangqi.ui.ChessBoardPanel;

import javax.swing.*;

public class GameFrame extends JFrame{
    public String user;
    public JLabel  label = new JLabel("开始");

  //  public JFrame chessFrame;

    public GameFrame(String title){
        super(title);
            //上面是login的界面，下面是象棋的界面

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel label = new JLabel("开始");
        label.setLocation(600, 100);
        label.setSize(100, 50);
        this.add(label);//先添加的后绘制

        JButton button = new JButton("start");
        button.setLocation(600, 200);
        button.setSize(100, 50);

        button.addActionListener(e -> {
            System.out.println("hhhhhh");
        });


        this.add(button);//先添加的后绘制

        ChessBoardModel model = new ChessBoardModel();
        ChessBoardPanel boardPanel = new ChessBoardPanel(model);
        boardPanel.label = label;

        this.add(boardPanel);
//            chessFrame.pack();//大小适于内容
        this.setSize(800, 700);
        this.setLocationRelativeTo(null);
        //chessFrame.setVisible(true);

    }



}


