package edu.sustech.xiangqi.model;

import java.util.*;

import edu.sustech.xiangqi.model.AbstractPiece;
import edu.sustech.xiangqi.model.ChessBoardModel;


public class AdvancedAI {
    private final ChessBoardModel model;
    //0-空
    //1.0-兵
    //2.0-士
    //4.0-象
    //4.0-马
    //4.5-炮
    //9.0-車
    //MAX-将
    private static final double[] pieceValue={
            0,10,20,20,40,45,90,1000
    };

    private static final int[][] soldierLocationValue={
            {0,0,0,0,0,0,0,0,0},
            {5,5,5,5,5,5,5,5,5},
            {10,10,10,10,10,10,10,10,10},
            {20,20,20,20,20,20,20,20,20},
            {30,30,30,30,30,30,30,30,30},
            {60,60,60,60,60,60,60,60,60},
            {80,80,80,80,80,80,80,80,80},
            {100,100,100,100,100,100,100,100,100},
            {150,150,150,150,150,150,150,150,150},
            {200,200,200,250,250,250,200,200,200}
    };

    private static final int[][] chariotLocationValue={
            {30,0,0,20,50,20,0,0,30},
            {40,0,0,30,60,30,0,0,40},
            {50,0,0,40,70,40,0,0,50},
            {60,0,0,50,80,50,0,0,60},
            {70,0,0,60,90,60,0,0,70},
            {80,0,0,70,100,70,0,0,80},
            {90,0,0,80,110,80,0,0,90},
            {100,0,0,90,120,90,0,0,100},
            {110,0,0,100,130,100,0,0,110},
            {120,0,0,110,140,110,0,0,120},
    };

    private static final int[][] horseLocationValue={
            {0,10,0,10,0,10,0,10,0},
            {10,20,20,20,20,20,20,20,10},
            {0,20,30,30,30,30,30,20,0},
            {10,30,40,40,40,40,40,30,10},
            {0,20,-50,-50,-100,-50,-50,20,0},
            {0,20,-50,-50,-100,-50,-50,20,0},
            {10,30,40,40,40,40,40,30,10},
            {0,20,30,30,30,30,30,20,0},
            {10,20,20,20,20,20,20,20,10},
            {0,10,0,10,0,10,0,10,0},
    };

    private static final int[][] cannonLocationValue={
            {20,0,0,10,30,10,0,0,20},
            {10,20,20,20,20,20,20,20,10},
            {30,0,0,20,50,20,0,0,30},
            {20,10,10,10,10,10,10,10,20},
            {10,0,0,10,30,10,0,0,10},
            {10,0,0,10,30,10,0,0,10},
            {20,10,10,10,10,10,10,10,20},
            {30,0,0,20,50,20,0,0,30},
            {10,20,20,20,20,20,20,20,10},
            {20,0,0,10,30,10,0,0,20},
    };

    public AdvancedAI(ChessBoardModel model){
        this.model=model;
    }

    private List<int[]>getValidMoves(boolean isBlack){
        List<int[]> validMoves=new ArrayList<>();
        List<AbstractPiece> AIPieces=new ArrayList<>(model.getPieces());
        if(AIPieces.isEmpty()){
            return null;
        }
        for(AbstractPiece piece:AIPieces){
            if(piece.isRed()==isBlack){
                continue;
            }
            int formerRow=piece.getRow();
            int formerCol=piece.getCol();
            for(int toRow=0;toRow<=9;toRow++){
                for(int toCol=0;toCol<=8;toCol++){
                    AbstractPiece targetPiece=model.getPieceAt(toRow,toCol);
                    if(model.moveWithoutLog(piece,toRow,toCol)){
                        model.switchTurn();
                        piece.setCol(formerCol);
                        piece.setRow(formerRow);
                        if(targetPiece!=null){
                            model.addPiece(targetPiece);
                        }
                        validMoves.add(new int[]{formerRow,formerCol,toRow,toCol});
                    }
                }
            }
        }
        return validMoves;
    }


//    List<int[]> validMoves_BLACK =new ArrayList<>();
//    List<int[]> validMoves_RED =new ArrayList<>();

    public int[] getBestMove(){

        //System.out.println("合法走步数量"+validMoves.size());
        double bestValue=Double.MIN_VALUE;
        List<int[]> bestMove=new ArrayList<>();


            for(int[] move: Objects.requireNonNull(getValidMoves(true))){
                int formerRow=move[0];
                int formerCol=move[1];
                int toRow=move[2];
                int toCol=move[3];

                AbstractPiece formerPiece=model.getPieceAt(formerRow,formerCol);
                AbstractPiece toPiece= model.getPieceAt(toRow,toCol);
                model.moveWithoutLog(formerPiece,toRow,toCol);
                model.switchTurn();
                //double value=evaluate();
                double value=minimax(5,false,Double.MIN_VALUE,Double.MAX_VALUE);
                //System.out.println("当前走法价值"+value);
                formerPiece.setRow(formerRow);
                formerPiece.setCol(formerCol);
                if(toPiece!=null){
                    model.addPiece(toPiece);
                }

                if(value>bestValue){
                    bestValue=value;
                    bestMove.clear();
                    bestMove.add(move);
                }
                else if(value==bestValue){
                    bestMove.add(move);
                }
            }


        Random random=new Random();
        if(!bestMove.isEmpty()){
            return bestMove.get(random.nextInt(bestMove.size()));
        }
        return null;
    }

    private double evaluate(){
        double totalBlackValue=0;
        double totalRedValue=0;

        for(AbstractPiece piece: model.getPieces()){
            double pieceValue=getPieceValue(piece);
            double positionValue=getPositionValue(piece);
            if(!piece.isRed()){
                totalBlackValue=totalBlackValue+pieceValue+positionValue;
            }
            else {
                totalRedValue=totalRedValue+pieceValue+positionValue;
            }
        }
        return totalBlackValue-totalRedValue;
    }

    private double getPieceValue(AbstractPiece piece){
        int type=getPieceType(piece);
        double baseValue=pieceValue[type];

        if(type==1&&!piece.isRed()){
            if(piece.getRow()>4){
                return 2.0;
            }
        }
        return baseValue;
    }

    private int getPositionValue(AbstractPiece piece){
        if(piece.isRed()){
            int type=getPieceType(piece);
            int row=piece.getRow();
            int col=piece.getCol();
            switch (type){
                case 1:return -soldierLocationValue[9-row][col];
                case 6:return -chariotLocationValue[9-row][col];
                default:return 0;
            }
        }

        int type=getPieceType(piece);
        int row=piece.getRow();
        int col=piece.getCol();

        switch(type){
            case 1:return soldierLocationValue[row][col];
            case 4:return horseLocationValue[row][col];
            case 5:return cannonLocationValue[row][col];
            case 6:return chariotLocationValue[row][col];
            default:return 0;
        }
    }

    private int getPieceType(AbstractPiece piece){
        String name= piece.getName();
        switch (name){
            case "卒":
            case "兵":
                return 1;
            case "士":
            case "仕":
                return 2;
            case "象":
            case "相":
                return 3;
            case "马":return 4;
            case "炮":return 5;
            case "車":return 6;
            case "將":
            case "帅":
                return 7;
            default:return 0;
        }
    }




    private double minimax(int depth, boolean isBlackTurn, double alpha, double beta) {
        double maxScore = Double.MIN_VALUE;
        double minScore = Double.MAX_VALUE;

        // 终止条件：搜索到最大深度，或分出胜负（将死/困毙）
        if (depth== 0||model.isCheckmate()) {
            return evaluate();
        }

        if (isBlackTurn) { // 黑方回合：最大化收益（Max层）
            List<int[]> blackMoves = getValidMoves(true);
            if (blackMoves != null) {
                for (int[] move : blackMoves) {
                    int formerRow=move[0];
                    int formerCol=move[1];
                    int toRow=move[2];
                    int toCol=move[3];
                    AbstractPiece formerPiece=model.getPieceAt(formerRow,formerCol);
                    AbstractPiece toPiece= model.getPieceAt(toRow,toCol);
                    model.moveWithoutLog(formerPiece,toRow,toCol);
                    model.switchTurn();

                    // 递归搜索红方的应对（深度-1，切换为红方回合）
                    double score = minimax(depth - 1, false, alpha, beta);
                    if(toPiece!=null){
                        double captureGain=getPieceValue(toPiece)-getPieceValue(formerPiece);
                        score+=Math.abs(captureGain*200);
                    }

                    formerPiece.setRow(formerRow);
                    formerPiece.setCol(formerCol);
                    if(toPiece!=null){
                        model.addPiece(toPiece);
                    }

                    // 4. 更新最大收益（Alpha剪枝，减少计算量）
                    maxScore = Math.max(maxScore, score);
                    alpha = Math.max(alpha, maxScore);
                    if (beta <= alpha) break; // 剪枝：红方不会选比当前差的走法
                }
            }
            return maxScore;
        }

        else { // 红方回合：最小化黑方收益（Min层）

            List<int[]> redMoves = getValidMoves(false);
            if (redMoves != null) {
                for (int[] move : redMoves) {
                    int formerRow=move[0];
                    int formerCol=move[1];
                    int toRow=move[2];
                    int toCol=move[3];
                    AbstractPiece formerPiece=model.getPieceAt(formerRow,formerCol);
                    AbstractPiece toPiece= model.getPieceAt(toRow,toCol);
                    model.moveWithoutLog(formerPiece,toRow,toCol);
                    model.switchTurn();

                    // 2. 递归搜索黑方的应对（深度-1，切换为黑方回合）
                    double score = minimax(depth - 1, true, alpha, beta);

                    formerPiece.setRow(formerRow);
                    formerPiece.setCol(formerCol);
                    if(toPiece!=null){
                        model.addPiece(toPiece);
                    }

                    // 4. 更新最小收益（Beta剪枝）
                    minScore = Math.min(minScore, score);
                    beta = Math.min(beta, minScore);
                    if (beta <= alpha) break;
                }
            }
            return minScore;
        }
    }

}
