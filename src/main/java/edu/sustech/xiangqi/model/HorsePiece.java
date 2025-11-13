package edu.sustech.xiangqi.model;

public class HorsePiece extends AbstractPiece {

    public HorsePiece(String name, int row, int col, boolean isRed){super(name, row, col, isRed);}

    @Override
    public boolean canMoveTo(int targetRow, int targetCol, ChessBoardModel model) {
        int currentRow = getRow();
        int currentCol = getCol();
        if (currentRow == targetRow && currentCol == targetCol) {
            return false;
        }

        int rowDiff = targetRow - currentRow;
        int colDiff = Math.abs(targetCol - currentCol);

        //马的移动规则
        //走“日”
        int[][] movement={{2,1},{2,-1},{1,2},{1,-2},{-1,2},{-1,-2},{-2,-1},{-2,1}};
        //遍历移动路径是否合法
        for(int i=0;i< movement.length;i++){
            int move1=currentRow+movement[i][0];
            int move2=currentCol+movement[i][1];
            if(targetRow==move1&&targetCol==move2&&move1>=0&&move1<10&&move2>=0&&move2<9){

                //蹩马腿
                //if()
                return true;
            }
        }
        return false;
    }

}
