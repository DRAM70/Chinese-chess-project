package edu.sustech.xiangqi.model;

/**
 * 帅/将
 */
public class GeneralPiece extends AbstractPiece {

    public GeneralPiece(String name, int row, int col, boolean isRed) {
        super(name, row, col, isRed);
    }

    @Override
    public boolean canMoveTo(int targetRow, int targetCol, ChessBoardModel model) {
        // TODO: 实现将/帅的移动规则
        int currentRow = getRow();
        int currentCol = getCol();
        if (currentRow == targetRow && currentCol == targetCol) {
            return false;
        }

        //将和帅的移动规则
        //只能横向和纵向移动一格且只能在九宫内移动
        int rowDiff = Math.abs(targetRow - currentRow);
        int colDiff = Math.abs(targetCol - currentCol);
        if(((rowDiff==1&&colDiff==0)||(rowDiff==0&&colDiff==1))){
            if (isRed()){
                return targetRow <= 9 && targetRow >= 7 && targetCol >= 3 && targetCol <= 5;
            }
            else {
                return targetCol >= 3 && targetCol <= 5 && targetRow >= 0 && targetRow <= 2;
            }
        }

        return false;
    }
}
