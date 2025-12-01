package edu.sustech.xiangqi.model;

public class ElephantPiece extends AbstractPiece{

    public ElephantPiece(String name, int row, int col, boolean isRed,int number) {
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

        if(piece_target==null){
            if(isRed()&&targetRow>=5){
                return true;
            }
            else if(!isRed()&&targetRow<=4){
                return true;
            }
            return false;
        }
        else {
            if (piece_target.isRed()==piece_current.isRed()){return false;}
            else if((isRed()&&targetRow>=5)||(!isRed()&&targetRow<=4)){
                //model.removePieces(piece_target);
                return true;
            }
        }
        return false;
    }
}
