package edu.sustech.xiangqi.model;

public class ElephantPiece extends AbstractPiece{

    public ElephantPiece(String name, int row, int col, boolean isRed) {
        super(name, row, col, isRed);
    }

    @Override
    public boolean canMoveTo(int targetRow, int targetCol, ChessBoardModel model) {
        int currentRow = getRow();
        int currentCol = getCol();

        if (currentRow == targetRow && currentCol == targetCol) {
            return false;
        }

        //象的移动规则
        //象走“田”
        int rowDiff = targetRow - currentRow;
        int colDiff = targetCol - currentCol;

        if((rowDiff!=2&&rowDiff!=-2)||(colDiff!=2&&colDiff!=-2)){return false;}
        if(rowDiff==2&&colDiff==2){
            AbstractPiece piece=model.getPieceAt(currentRow+1,currentCol+1);
            if(piece!=null){return false;}
        }
        else if (rowDiff==-2&&colDiff==2){
            AbstractPiece piece=model.getPieceAt(currentRow-1,currentCol+1);
            if(piece!=null){return false;}
        }
        else if(rowDiff==2&&colDiff==-2){
            AbstractPiece piece=model.getPieceAt(currentRow+1,currentCol-1);
            if (piece!=null){return false;}
        }
        else{
            AbstractPiece piece=model.getPieceAt(currentRow-1,currentCol-1);
            if(piece!=null){return false;}
        }

        return true;
    }
}
