package edu.sustech.xiangqi.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChessBoardModel {
    // 储存棋盘上所有的棋子，要实现吃子的话，直接通过pieces.remove(被吃掉的棋子)删除就可以
    private final List<AbstractPiece> pieces;
    private static final int ROWS = 10;
    private static final int COLS = 9;
    private String user;

    protected void removePieces(AbstractPiece piece){
        pieces.remove(piece);
    }

    public ChessBoardModel() {
        pieces = new ArrayList<>();
        initializePieces();
    }

    private void initializePieces() {
        // 黑方棋子
        pieces.add(new GeneralPiece("將", 0, 4, false));
        pieces.add(new SoldierPiece("卒", 3, 0, false));
        pieces.add(new SoldierPiece("卒", 3, 2, false));
        pieces.add(new SoldierPiece("卒", 3, 4, false));
        pieces.add(new SoldierPiece("卒", 3, 6, false));
        pieces.add(new SoldierPiece("卒", 3, 8, false));
        pieces.add(new HorsePiece("马",0,1,false));
        pieces.add(new HorsePiece("马",0,7,false));
        pieces.add(new ChariotPiece("車",0,0,false));
        pieces.add(new ChariotPiece("車",0,8,false));
        pieces.add(new ElephantPiece("象",0,2,false));
        pieces.add(new ElephantPiece("象",0,6,false));
        pieces.add(new CannonPiece("炮",2,1,false));
        pieces.add(new CannonPiece("炮",2,7,false));
        pieces.add(new AdvisorPiece("士",0,3,false));
        pieces.add(new AdvisorPiece("士",0,5,false));

        // 红方棋子
        pieces.add(new GeneralPiece("帅", 9, 4, true));
        pieces.add(new SoldierPiece("兵", 6, 0, true));
        pieces.add(new SoldierPiece("兵", 6, 2, true));
        pieces.add(new SoldierPiece("兵", 6, 4, true));
        pieces.add(new SoldierPiece("兵", 6, 6, true));
        pieces.add(new SoldierPiece("兵", 6, 8, true));
        pieces.add(new HorsePiece("马",9,1,true));
        pieces.add(new HorsePiece("马",9,7,true));
        pieces.add(new ChariotPiece("車",9,0,true));
        pieces.add(new ChariotPiece("車",9,8,true));
        pieces.add(new ElephantPiece("相",9,2,true));
        pieces.add(new ElephantPiece("相",9,6,true));
        pieces.add(new CannonPiece("炮",7,1,true));
        pieces.add(new CannonPiece("炮",7,7,true));
        pieces.add(new AdvisorPiece("仕",9,3,true));
        pieces.add(new AdvisorPiece("仕",9,5,true));
    }

    public List<AbstractPiece> getPieces() {
        return pieces;
    }

    public AbstractPiece getPieceAt(int row, int col) {
        for (AbstractPiece piece : pieces) {
            if (piece.getRow() == row && piece.getCol() == col) {
                return piece;
            }
        }
        return null;
    }

    public boolean isValidPosition(int row, int col) {
        return row >= 0 && row < ROWS && col >= 0 && col < COLS;
    }

    public boolean movePiece(AbstractPiece piece, int newRow, int newCol) {
        if (!isValidPosition(newRow, newCol)) {
            return false;
        }

        if (!piece.canMoveTo(newRow, newCol, this)) {
            return false;
        }

        piece.moveTo(newRow, newCol);
        //这里是一个示例，具体还有待开发
        String move = piece.getName() + " moved to (" + newRow + ", " + newCol + ")";
        logWriter(move, true);
        //示例结束
        return true;
        //return piece.legalMove(newRow,newCol,this);
    }

    public static int getRows() {
        return ROWS;
    }

    public static int getCols() {
        return COLS;
    }

    public void setUser(String user) {
        this.user = user;
    }

    //当写入步骤记录时，输入string，写入文档,注意还要输入是否是红方走的棋
    //现在这里还没有完工
    //目前实现了在特定文件里写入特定语句的功能，对于损坏的log还要检测
    //检测log是否损坏的一个想法：结束后对log进行一个hashcode或者什么的加密，形成一串字符，在读取log之前将两者进行比对，任一方不符合对方都会不读取log
    public void logWriter(String move, boolean isRed){
        try{
            String relativePath = "UserData/" + user + ".txt";
            File file = new File(relativePath);
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));



            writer.write( "nenenen6666   " + user + "   " +move +   "\n");
            System.out.println(user + ": New step log written! Log: "  + isRed + move);



            writer.flush();
            writer.close();
        }catch(IOException e){
            System.out.println("Error, file" + user + ".txt is broken! And step log writing failed!");
        }
    }
}
