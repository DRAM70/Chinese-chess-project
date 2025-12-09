package edu.sustech.xiangqi.model;

import edu.sustech.xiangqi.ToolBox;
import edu.sustech.xiangqi.audio.BackgroundMusic;

import javax.swing.*;
import java.awt.*;
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
    private boolean isRedTurn=true;
    private boolean doLogWrite;
    private String extra;

    protected void removePieces(AbstractPiece piece){
        pieces.remove(piece);
    }
    protected void addPiece(AbstractPiece piece){
        pieces.add(piece);
    }

    public ChessBoardModel(String user, boolean doLogWrite, int index) {
        if(index == 1){
            extra = "Temp";
        }
        if(index == 2){
            extra = "AITemp";
        }
        if(index == 3){
            extra = "TimingTemp";
        }
        this.user = user;
        pieces = new ArrayList<>();
        this.doLogWrite = doLogWrite;
        initializePieces();
        if(doLogWrite){
            initializeExtraLog();
        }
    }



    private void initializePieces() {
        // 黑方棋子
        pieces.add(new GeneralPiece("將", 0, 4, false,31));
        pieces.add(new SoldierPiece("卒", 3, 0, false,32));
        pieces.add(new SoldierPiece("卒", 3, 2, false,33));
        pieces.add(new SoldierPiece("卒", 3, 4, false,34));
        pieces.add(new SoldierPiece("卒", 3, 6, false,35));
        pieces.add(new SoldierPiece("卒", 3, 8, false,36));
        pieces.add(new HorsePiece("马",0,1,false,37));
        pieces.add(new HorsePiece("马",0,7,false,38));
        pieces.add(new ChariotPiece("車",0,0,false,39));
        pieces.add(new ChariotPiece("車",0,8,false,40));
        pieces.add(new ElephantPiece("象",0,2,false,41));
        pieces.add(new ElephantPiece("象",0,6,false,42));
        pieces.add(new CannonPiece("炮",2,1,false,43));
        pieces.add(new CannonPiece("炮",2,7,false,44));
        pieces.add(new AdvisorPiece("士",0,3,false,45));
        pieces.add(new AdvisorPiece("士",0,5,false,46));

        // 红方棋子
        pieces.add(new GeneralPiece("帅", 9, 4, true,11));
        pieces.add(new SoldierPiece("兵", 6, 0, true,12));
        pieces.add(new SoldierPiece("兵", 6, 2, true,13));
        pieces.add(new SoldierPiece("兵", 6, 4, true,14));
        pieces.add(new SoldierPiece("兵", 6, 6, true,15));
        pieces.add(new SoldierPiece("兵", 6, 8, true,16));
        pieces.add(new HorsePiece("马",9,1,true,17));
        pieces.add(new HorsePiece("马",9,7,true,18));
        pieces.add(new ChariotPiece("車",9,0,true,19));
        pieces.add(new ChariotPiece("車",9,8,true,20));
        pieces.add(new ElephantPiece("相",9,2,true,21));
        pieces.add(new ElephantPiece("相",9,6,true,22));
        pieces.add(new CannonPiece("炮",7,1,true,23));
        pieces.add(new CannonPiece("炮",7,7,true,24));
        pieces.add(new AdvisorPiece("仕",9,3,true,25));
        pieces.add(new AdvisorPiece("仕",9,5,true,26));
    }

    //用来获取当前回合状态
    public boolean isRedTurn(){
        return isRedTurn;
    }

    public void switchTurn(){
        isRedTurn=!isRedTurn;
    }

    public boolean isCurrentTurnPiece(AbstractPiece piece){
        if(piece==null){
            return false;
        }
        return (isRedTurn&&piece.isRed())||(!isRedTurn&&!piece.isRed());
    }

    //检测将帅是否照面
    public boolean isGeneralMeeting(){
        GeneralPiece redG=null;
        GeneralPiece blackG=null;
        //遍历找到将帅位置
        for(AbstractPiece piece:pieces){
            if(piece instanceof GeneralPiece){
                if (piece.isRed()){
                    redG=(GeneralPiece)piece;
                }
                else {
                    blackG=(GeneralPiece)piece;
                }
            }
        }
        if(redG.getCol()!=blackG.getCol()){
            return false;
        }
        else {
            for(int i=redG.getRow()-1;i>=blackG.getRow()+1;i--){
                if(getPieceAt(i,redG.getCol())!=null){
                    return false;
                }
            }
            return true;
        }
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


    //这是暂停全棋盘棋子移动的method
    //i是true，暂停，i是false，继续
    int a=0;
    public void pauseButton(boolean i){
        if(i){
            a++;
        }
    }


    public boolean checkMove(int toNumber,int toRow,int toCol){
        doLogWrite = false;
        if((toNumber>=11&&toNumber<=26)||(toNumber>=31&&toNumber<=46)){
            for(int i=0;i<pieces.size();i++){
                if(pieces.get(i).getNumber()==toNumber){
                    if(movePiece(pieces.get(i),toRow,toCol)){
                        SwingUtilities.invokeLater(()->{
                            for(Window win:Window.getWindows()){
                                if(win.isVisible()){
                                    win.repaint();
                                }
                            }
                        });
                    }
                }
            }
        }
        return false;
    }

    public void switchLogWrite(boolean choice){
        doLogWrite = choice;
    }


    public boolean isInCheck(){
        AbstractPiece generalPiece=null;
        for(AbstractPiece piece:getPieces()){
            String name= piece.getName();
            if((isRedTurn()&&name.equals("帅"))||(!isRedTurn()&&name.equals("將"))){
                generalPiece=piece;
                break;
            }
        }

        if(generalPiece==null){
            return false;
        }
        int GeneralRow=generalPiece.getRow();
        int GeneralCol=generalPiece.getCol();
        for(AbstractPiece opponentPiece:getPieces()){
            if(opponentPiece.isRed()==isRedTurn){
                continue;
            }
            if(opponentPiece.canMoveTo(GeneralRow,GeneralCol,this)){
                return true;
            }
        }
        return false;
    }


    public boolean isCheckmate(){
        //遍历被将军方所有棋子，模拟是否可以解除将军
        List<AbstractPiece> currentPieces=new ArrayList<>();
        for(AbstractPiece piece:getPieces()){
            if(piece.isRed()==isRedTurn){
                currentPieces.add(piece);
            }
        }
        for(AbstractPiece piece:currentPieces){
            int formerRow=piece.getRow();
            int formerCol=piece.getCol();
            for(int targetRow=0;targetRow<=9;targetRow++){
                for(int targetCol=0;targetCol<=8;targetCol++){
                    if(!piece.canMoveTo(targetRow,targetCol,this)||this.isGeneralMeeting()||!this.isCurrentTurnPiece(piece)){
                        continue;
                    }

                    AbstractPiece removement =getPieceAt(targetRow,targetCol);
                    piece.setRow(targetRow);
                    piece.setCol(targetCol);
                    if(removement!=null){
                        getPieces().remove(removement);
                    }

                    boolean stillInCheck=isInCheck();
                    piece.setRow(formerRow);
                    piece.setCol(formerCol);
                    if(removement!=null){
                        getPieces().add(removement);
                    }

                    if(!stillInCheck){
                        return false;
                    }
                }
            }
        }
//        return true;
//    }
//
//
//    public boolean isTrapped(){
//
//        for(AbstractPiece eachPiece:pieces){
//            int oldRow=eachPiece.getRow();
//            int oldCol=eachPiece.getCol();
//            for(int targetRow=0;targetRow<=9;targetRow++){
//                for(int targetCol=0;targetCol<=8;targetCol++){
//                    AbstractPiece changeStep =getPieceAt(targetRow,targetCol);
//                    if(!movePiece(eachPiece,targetRow,targetCol)){
//                        continue;
//                    }
//                    eachPiece.setRow(oldRow);
//                    eachPiece.setCol(oldCol);
//                    if(changeStep!=null){
//                        getPieces().add(changeStep);
//                    }
//                    return false;
//                }
//            }
//        }
        return true;
    }


    public boolean movePiece(AbstractPiece piece, int newRow, int newCol) {
        if(a%2==1){
            return false;
        }

        if (!isValidPosition(newRow, newCol)) {
            return false;
        }

        if(!isCurrentTurnPiece(piece)){
            return false;
        }

        //这个！使boolean值不直接回传，进而实现后续交换回合
        if (!piece.canMoveTo(newRow, newCol, this)) {
            return false;
        }

        //这段先留着，应该是没用了（不确定）

//        //被将时，如果能解除则优先，其它走法违法
//        if(isInCheck()){
//            if(isCheckmate()){
//                if(GameFrame.label != null){
//                    GameFrame.label.setText("将死");
//                }
//                return false;
//            }
//            else{
////                if(GameFrame.label != null){
////                    GameFrame.label.setText("将");
////                }
//        ToolBox.labelTextPlayer("将");
//                int formerRow= piece.getRow();
//                int formerCol= piece.getCol();
//                AbstractPiece removement =getPieceAt(newRow,newCol);
//                piece.setRow(newRow);
//                piece.setCol(newCol);
//                if(removement !=null){
//                    pieces.remove(removement);
//                }
//                boolean alsoInCheck=isInCheck();
//                piece.setRow(formerRow);
//                piece.setCol(formerCol);
//                if(removement!=null&&!pieces.contains(removement)){
//                    pieces.add(removement);
//                }
//                if(alsoInCheck){
//                    return false;
//                }
//            }
//
//
//        }

        //模拟移动并记录，判断是否照面
        int formerRow= piece.getRow();
        int formerCol= piece.getCol();
        AbstractPiece removement =getPieceAt(newRow,newCol);
        piece.setRow(newRow);
        piece.setCol(newCol);
        if(removement !=null){
            pieces.remove(removement);
        }
        boolean willFace=isGeneralMeeting();
        // 这里检查 不能主动被将
        boolean willBeChecked=isInCheck();
        piece.setRow(formerRow);
        piece.setCol(formerCol);
        if(removement!=null&&!pieces.contains(removement)){
            pieces.add(removement);
        }

        if(willBeChecked){
//            if(GameFrame.label != null){
//                GameFrame.label.setText("被将");
//            }
            ToolBox.labelTextStatus("被将");
            BackgroundMusic.playGeneralInDanger();
            return false;
        }

        else if (willFace){
//            if(GameFrame.label != null){
//                GameFrame.label.setText("将帅照面");
//            }
            ToolBox.labelTextStatus("将帅照面");
            return false;
        }

        else {
            piece.setRow(newRow);
            piece.setCol(newCol);
            if(removement!=null){
                if(isRedTurn){
                    System.out.println("吃黑方"+piece.getName());
                }
                else {
                    System.out.println("吃红方"+piece.getName());
                }
                pieces.remove(removement);
            }
        }

        //实现交换回合
        switchTurn();
        if(isRedTurn){
//            if(GameFrame.label != null){
//                GameFrame.label.setText("红方执子");
//            }
            ToolBox.labelTextPlayer("红方执子");
        }
        else {
//            if(GameFrame.label != null){
//                GameFrame.label.setText("黑方执子");
//            }
            ToolBox.labelTextPlayer("黑方执子");
        }


        //被将时，如果能解除则优先，其它走法违法
        if(isInCheck()){
            if(isCheckmate()){
//                if(GameFrame.label != null){
//                    GameFrame.label.setText("将死");
//                }
                ToolBox.labelTextStatus("将死");
                return false;
            }
            else{
//                if(GameFrame.label != null){
//                    GameFrame.label.setText("将");
//                }
                ToolBox.labelTextStatus("将");
            }

        }

//        else{
//            if(isTrapped()){
//                return false;
//            }
//        }
//
        piece.moveTo(newRow, newCol);
        //这里是一个示例，具体还有待开发
        if(doLogWrite){
            String move = "" + piece.getNumber()  + "0" +  newRow + "0" + newCol + "0" + ((piece.isRed()) ? 1 : 2);
            logWriter(move);
        }
        BackgroundMusic.playClick();
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
    public void logWriter(String move){
        try{
            String relativePath = "UserData/" + user + "/" + user + extra + ".txt";
            File file = new File(relativePath);
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));


            String s = move + "\n";
            writer.write(s);
//            System.out.println(user + ": New step log written! Log: " + move);
            System.out.print(user + ": Newly written log content: " + s);



            writer.flush();
            writer.close();
        }catch(IOException e){
            System.out.println("Error, file" + user + extra + ".txt is broken! And step log writing failed!");
        }
    }

    public void initializeExtraLog(){
        try{
            String relativePath = "UserData/" + user + "/" + user  +  extra +".txt";
            File file = new File(relativePath);
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));



            writer.write( "");
            System.out.println(user + "'s Templog file is initialized!");



            writer.flush();
            writer.close();
        }catch(IOException e){
            System.out.println("Error, file " + user + extra +  ".txt is broken! And log initializing failed!");
        }
    }
}
