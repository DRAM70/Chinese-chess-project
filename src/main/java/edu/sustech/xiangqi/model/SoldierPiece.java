package edu.sustech.xiangqi.model;

/**
 * 兵/卒
 */
public class SoldierPiece extends AbstractPiece {

    public SoldierPiece(String name, int row, int col, boolean isRed,int number) {
        super(name, row, col, isRed,number);
    }

    @Override
    public boolean canMoveTo(int targetRow, int targetCol, ChessBoardModel model) {
        int currentRow = getRow();
        int currentCol = getCol();

        if (currentRow == targetRow && currentCol == targetCol) {
            return false;
        }

        int rowDiff = targetRow - currentRow;
        int colDiff = Math.abs(targetCol - currentCol);

        AbstractPiece piece_target= model.getPieceAt(targetRow,targetCol);
        AbstractPiece piece_current=model.getPieceAt(currentRow,currentCol);

        // 兵/卒的移动规则：
        // 1. 未过河前只能向前走一步
        // 2. 过河后可以向前、向左、向右走一步，但不能后退
        if (isRed()) {
            // 红方兵（向上走，row减小）
            boolean crossedRiver = currentRow < 5; // 过了楚河汉界

            if (!crossedRiver) {
                // 未过河：只能向前（向上）走一步
                if(rowDiff == -1 && colDiff == 0) {
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
            } else {
                // 过河后：可以向前、向左、向右走一步
                if ((rowDiff == -1 && colDiff == 0) ||(rowDiff == 0 && colDiff == 1)){
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
        } else {
            // 黑方卒（向下走，row增大）
            boolean crossedRiver = currentRow >= 5; // 过了楚河汉界

            if (!crossedRiver) {
                // 未过河：只能向前（向下）走一步
                if(rowDiff == 1 && colDiff == 0) {
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
            } else {
                // 过河后：可以向前、向左、向右走一步
                if ((rowDiff == 1 && colDiff == 0)||(rowDiff == 0 && colDiff == 1)){
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
