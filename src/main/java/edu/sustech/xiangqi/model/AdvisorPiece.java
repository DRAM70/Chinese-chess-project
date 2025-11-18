package edu.sustech.xiangqi.model;

public class AdvisorPiece extends AbstractPiece{

    public AdvisorPiece(String name,int row,int col,boolean isRed){
        super(name, row, col, isRed);
    }

    @Override
    public boolean canMoveTo(int targetRow, int targetCol, ChessBoardModel model) {

        int currentRow = getRow();
        int currentCol = getCol();

        if (currentRow == targetRow && currentCol == targetCol) {
            return false;
        }

        AbstractPiece piece_target= model.getPieceAt(targetRow,targetCol);
        AbstractPiece piece_current=model.getPieceAt(currentRow,currentCol);

        //士/仕的移动规则
        //只能斜方向移动一格且只能在九宫内移动
        int rowDiff = Math.abs(targetRow - currentRow);
        int colDiff = Math.abs(targetCol - currentCol);
        if(rowDiff==1&&colDiff==1){
            if(isRed()){
                if(targetRow <= 9 && targetRow >= 7 && targetCol >= 3 && targetCol <= 5) {
                    if(piece_target==null){
                        return true;
                    }
                    else {
                        if (piece_target.isRed()==piece_current.isRed()){return false;}
                        else {
                            model.removePieces(piece_target);
                            return true;
                        }
                    }
                }
            }
            else {
                if (targetCol >= 3 && targetCol <= 5 && targetRow >= 0 && targetRow <= 2){
                    if(piece_target==null){
                        return true;
                    }
                    else {
                        if (piece_target.isRed()==piece_current.isRed()){return false;}
                        else {
                            model.removePieces(piece_target);
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }
}
