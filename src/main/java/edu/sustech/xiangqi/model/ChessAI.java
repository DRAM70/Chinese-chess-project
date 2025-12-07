package edu.sustech.xiangqi.model;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import edu.sustech.xiangqi.model.AbstractPiece;
import edu.sustech.xiangqi.model.ChessBoardModel;

public class ChessAI {
    private final ChessBoardModel model;
    private final Random random;

    //获取棋盘状态
    public ChessAI(ChessBoardModel model){
        this.model=model;
        this.random=new Random();
    }

    public int[] getAIMove(){
        List<int[]> validMoves=new ArrayList<>();
        List<AbstractPiece> AIPieces=new ArrayList<>(model.getPieces());
        if(AIPieces.isEmpty()){
            return null;
        }

        for(AbstractPiece piece:AIPieces){
            int formerRow=piece.getRow();
            int formerCol=piece.getCol();
            for(int toRow=0;toRow<=9;toRow++){
                for(int toCol=0;toCol<=8;toCol++){
                    if(!piece.isRed()){
                        AbstractPiece targetPiece=model.getPieceAt(toRow,toCol);
                        if(model.movePiece(piece,toRow,toCol)){
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
        }

        if(!validMoves.isEmpty()){
            int randomIndex= random.nextInt(validMoves.size());
            return validMoves.get(randomIndex);
        }
        return null;
    }

    private List<AbstractPiece> getAIPieces(){
        List<AbstractPiece> AIPieces=new ArrayList<>();
        for(AbstractPiece piece: model.getPieces()){
            if(!piece.isRed()){
                AIPieces.add(piece);
            }
        }
        return AIPieces;
    }
}
