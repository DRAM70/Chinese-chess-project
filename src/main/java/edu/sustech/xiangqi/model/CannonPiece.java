package edu.sustech.xiangqi.model;

public class CannonPiece extends AbstractPiece{

    public CannonPiece(String name,int row,int col,boolean isRed,int number){
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

        //在不吃子的情况下，炮与车的移动规则相同
        int rowDiff = Math.abs(targetRow - currentRow);
        int colDiff = Math.abs(targetCol - currentCol);

        if(rowDiff>0&&colDiff>0){
            return false;
        }

        if (rowDiff == 0) {
            if (targetCol > currentCol) {
                int a=0;
                for (int i = currentCol + 1; i < targetCol; i++) {
                    AbstractPiece piece = model.getPieceAt(currentRow, i);
                    if (piece != null) {
                        a++;
                    }
                }
                if(a==1&&piece_target!=null&&piece_target.isRed()!=piece_current.isRed()){
                    //model.removePieces(piece_target);
                    return true;
                }
                if(piece_target==null){
                    for (int i = currentCol + 1; i < targetCol; i++) {
                        AbstractPiece piece = model.getPieceAt(currentRow, i);
                        if(piece!=null){return false;}
                    }
                    return true;
                }
                return false;
            }

            else {
                int b=0;
                for (int i = currentCol - 1; i > targetCol; i--) {
                    AbstractPiece piece = model.getPieceAt(currentRow, i);
                    if (piece != null) {
                        b++;
                    }
                }
                if(b==1&&piece_target!=null&&piece_target.isRed()!=piece_current.isRed()){
                    //model.removePieces(piece_target);
                    return true;
                }
                if(piece_target==null){
                    for (int i = currentCol - 1; i > targetCol; i--) {
                        AbstractPiece piece = model.getPieceAt(currentRow, i);
                        if(piece!=null){return false;}
                    }
                    return true;
                }
                return false;
            }
        }

        if(colDiff==0){
            if(targetRow>currentRow){
                int c =0;
                for (int i = currentRow + 1; i < targetRow; i++) {
                    AbstractPiece piece = model.getPieceAt(i, currentCol);
                    if (piece != null) {
                        c++;
                    }
                }
                if(c ==1&&piece_target!=null&&piece_target.isRed()!=piece_current.isRed()){
                    //model.removePieces(piece_target);
                    return true;
                }
                if(piece_target==null){
                    for (int i = currentRow + 1; i < targetRow; i++) {
                        AbstractPiece piece = model.getPieceAt(i,currentCol);
                        if(piece!=null){return false;}
                    }
                    return true;
                }
                return false;
            }

            else {
                int d =0;
                for (int i = currentRow - 1; i > targetRow; i--) {
                    AbstractPiece piece = model.getPieceAt(i, currentCol);
                    if (piece != null) {
                        d++;
                    }
                }
                if(d ==1&&piece_target!=null&&piece_target.isRed()!=piece_current.isRed()){
                    //model.removePieces(piece_target);
                    return true;
                }
                if(piece_target==null){
                    for (int i = currentRow - 1; i > targetRow; i--) {
                        AbstractPiece piece = model.getPieceAt(i,currentCol);
                        if(piece!=null){return false;}
                    }
                    return true;
                }
                return false;
            }
        }
        return false;
    }
}
