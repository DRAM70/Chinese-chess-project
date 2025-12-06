package edu.sustech.xiangqi.ui;

import java.awt.*;

public enum Style {
    DEFAULT(new Color(220, 179, 92), Color.BLACK),
    DARK(Color.GRAY, Color.WHITE),
    BLUE(new Color(0, 123, 167), Color.WHITE);


    //可能还会加上棋子颜色



    private final Color backgroundColor;
    private final Color labelColor;

    private Style(Color backgroundColor, Color labelColor){
        this.backgroundColor = backgroundColor;
        this.labelColor = labelColor;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public Color getLabelColor(){
        return labelColor;
    }
}
