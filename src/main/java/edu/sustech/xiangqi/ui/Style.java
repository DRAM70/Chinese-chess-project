package edu.sustech.xiangqi.ui;

import java.awt.*;

public enum Style {
    DEFAULT(new Color(220, 179, 92)),
    DARK(Color.DARK_GRAY);


    //可能还会加上棋子颜色



    private final Color backgroundColor;

    private Style(Color backgroundColor){
        this.backgroundColor = backgroundColor;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }
}
