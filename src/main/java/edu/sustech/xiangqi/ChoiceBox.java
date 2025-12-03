package edu.sustech.xiangqi;

import javax.swing.*;

public class ChoiceBox {
    public static boolean choiceBox(String title, String message){
        int userChoice = JOptionPane.showConfirmDialog(
                null,
                message,
                title,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
        if(userChoice == JOptionPane.YES_OPTION){
            System.out.println("ChoiceBox choosing YES");
            return true;
        }else if(userChoice == JOptionPane.NO_OPTION){
            System.out.println("ChoiceBox choosing NO");
            return false;
        }else{
            System.out.println("ChoiceBox closed, automatically choosing NO");
            return false;
        }
    }
}
