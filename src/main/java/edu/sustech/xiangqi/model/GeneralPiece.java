package edu.sustech.xiangqi.model;

/**
 * 帅/将
 */
public class GeneralPiece extends AbstractPiece {

    public GeneralPiece(String name, int row, int col, boolean isRed,int number) {
        super(name,row,col,isRed,number);
    }

    @Override
    public boolean canMoveTo(int targetRow, int targetCol, ChessBoardModel model) {

        int currentRow = getRow();
        int currentCol = getCol();
        if (currentRow == targetRow && currentCol == targetCol) {
            return false;
        }

        //将和帅的移动规则
        //只能横向和纵向移动一格且只能在九宫内移动

        AbstractPiece piece_target= model.getPieceAt(targetRow,targetCol);
        AbstractPiece piece_current=model.getPieceAt(currentRow,currentCol);

        int rowDiff = Math.abs(targetRow - currentRow);
        int colDiff = Math.abs(targetCol - currentCol);
        if(((rowDiff==1&&colDiff==0)||(rowDiff==0&&colDiff==1))){
            if (isRed()){
                if(targetRow <= 9 && targetRow >= 7 && targetCol >= 3 && targetCol <= 5) {
                    if(piece_target==null){
                        return true;
                    }
                    else {
                        if (piece_target.isRed()==piece_current.isRed()){return false;}
                        else {
                            //model.removePieces(piece_target);
                            return true;
                        }
                    }
                }
            }
            else {
                if( targetCol >= 3 && targetCol <= 5 && targetRow >= 0 && targetRow <= 2){
                    if(piece_target==null){
                        return true;
                    }
                    else {
                        if (piece_target.isRed()==piece_current.isRed()){return false;}
                        else {
                            //model.removePieces(piece_target);
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }
}
