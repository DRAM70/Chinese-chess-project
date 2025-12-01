package edu.sustech.xiangqi.model;

public class HorsePiece extends AbstractPiece {

    public HorsePiece(String name, int row, int col, boolean isRed,int number){super(name, row, col, isRed,number);}


    @Override
    public boolean canMoveTo(int targetRow, int targetCol, ChessBoardModel model) {
        int currentRow = getRow();
        int currentCol = getCol();
        if (currentRow == targetRow && currentCol == targetCol) {
            return false;
        }

        int rowDiff = targetRow - currentRow;
        int colDiff = targetCol - currentCol;

        AbstractPiece piece_target= model.getPieceAt(targetRow,targetCol);
        AbstractPiece piece_current=model.getPieceAt(currentRow,currentCol);

        //马的移动规则
        //走“日”
            if((rowDiff==2&&colDiff==1)||(rowDiff==2&&colDiff==-1)){
                AbstractPiece piece = model.getPieceAt(currentRow+1,currentCol );
                if(piece==null){
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

            if((rowDiff==-2&&colDiff==1)||(rowDiff==-2&&colDiff==-1)){
                AbstractPiece piece = model.getPieceAt(currentRow-1,currentCol );
                if(piece==null){
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

            if((rowDiff==1&&colDiff==2)||(rowDiff==-1&&colDiff==2)){
                AbstractPiece piece = model.getPieceAt(currentRow,currentCol+1 );
                if(piece==null){
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

            if((rowDiff==1&&colDiff==-2)||(rowDiff==-1&&colDiff==-2)){
                AbstractPiece piece = model.getPieceAt(currentRow,currentCol-1 );
                if(piece==null){
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



        return false;
    }
}