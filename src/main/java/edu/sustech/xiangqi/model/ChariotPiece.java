package edu.sustech.xiangqi.model;

public class ChariotPiece extends AbstractPiece {

    public ChariotPiece(String name, int row, int col, boolean isRed,int number) {
        super(name, row, col, isRed,number);
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

        //在没有阻挡的情况下，車可以横或竖走任意格数
        int rowDiff = Math.abs(targetRow - currentRow);
        int colDiff = Math.abs(targetCol - currentCol);

        if(rowDiff>0&&colDiff>0){
            return false;
        }
        if (rowDiff == 0) {
            if (targetCol > currentCol) {
                for (int i = currentCol + 1; i < targetCol; i++) {
                    AbstractPiece piece = model.getPieceAt(currentRow, i);
                    if (piece != null) {
                        return false;
                    }
                }
            } else {
                for (int i = currentCol - 1; i > targetCol; i--) {
                    AbstractPiece piece = model.getPieceAt(currentRow, i);
                    if (piece != null) {
                        return false;
                    }
                }
            }
        }

        if(colDiff==0){
            if(targetRow>currentRow){
                for(int j=currentRow+1;j<targetRow;j++){
                    AbstractPiece piece=model.getPieceAt(j,currentCol);
                    if(piece!=null){
                        return false;
                    }
                }
            }
            else {
                for(int j=currentRow-1;j>targetRow;j--){
                    AbstractPiece piece=model.getPieceAt(j,currentCol);
                    if(piece!=null){
                        return false;
                    }
                }
            }
        }

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
