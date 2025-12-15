package edu.sustech.xiangqi.ui;

import edu.sustech.xiangqi.model.AdvancedAI;
import edu.sustech.xiangqi.model.ChessAI;
import edu.sustech.xiangqi.model.ChessBoardModel;
import edu.sustech.xiangqi.model.AbstractPiece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChessBoardPanel extends JPanel {
    //这个panel的作用是在已经有了棋子之后，并且知道他们的位置，进行绘制的
    private final ChessBoardModel model;
    public JLabel label;
    /**
     * 单个棋盘格子的尺寸（px）
     */
    private static final int CELL_SIZE = 64;

    /**
     * 棋盘边界与窗口边界的边距
     */
    private static final int MARGIN = 40;

    /**
     * 棋子的半径
     */
    private static final int PIECE_RADIUS = 25;

    private AbstractPiece selectedPiece = null;
    private AbstractPiece formerPiece=null;
    private int getCurrentRow;
    private int getCurrentCol;
    private int getLastRow=-1;
    private int getLastCol=-1;
    private boolean AISwitch = false;
    private int getTargetRow=-1;
    private int getTargetCol=-1;

    public ChessBoardPanel(ChessBoardModel model, int style) {
        this.model = model;
        setPreferredSize(new Dimension(
                CELL_SIZE * (ChessBoardModel.getCols() - 1) + MARGIN * 2,
                CELL_SIZE * (ChessBoardModel.getRows() - 1) + MARGIN * 2
        ));
//        switch(style){
//            case 0:
//                setBackground(new Color(220, 179, 92));
//                break;
//            case 1:
//                setBackground(Color.DARK_GRAY);
//                break;
//        }

        Style[] styleList = Style.values();
        setBackground(styleList[style].getBackgroundColor());

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleMouseClick(e.getX(), e.getY());
            }
        });
    }

    public void repaint(int style){
        if(style == 0){
            setBackground(new Color(220, 179, 92));
        }else{
            setBackground(Color.DARK_GRAY );
        }
        this.getParent().repaint();

    }


    private void handleMouseClick(int x, int y) {
            int col = Math.round((float)(x - MARGIN) / CELL_SIZE);
            int row = Math.round((float)(y - MARGIN) / CELL_SIZE);

            if (!model.isValidPosition(row, col)) {
                label.setText("请选择");
                return;
            }

            if (selectedPiece == null) {
                AbstractPiece clickedPiece=model.getPieceAt(row,col);
                if(clickedPiece!=null&&model.isCurrentTurnPiece(clickedPiece)){
                    selectedPiece = model.getPieceAt(row, col);
                    getCurrentRow=row;
                    getCurrentCol=col;
                    label.setText("选中棋子了");
                }
            }
            else {
//                if(model.isRedTurn()){
//                    label.setText("红方执子");
//                }
//                else {
//                    label.setText("黑方执子");
//                }

                if(model.movePiece(selectedPiece,row,col)){
                    getLastRow=getCurrentRow;
                    getLastCol=getCurrentCol;
                    getTargetRow=row;
                    getTargetCol=col;
                    formerPiece=selectedPiece;

                }
                selectedPiece = null;
            }
            // 处理完点击事件后，需要重新绘制ui界面才能让界面上的棋子“移动”起来
            // Swing 会将多个请求合并后再重新绘制，因此调用 repaint 后gui不会立刻变更
            // repaint 中会调用 paintComponent，从而重新绘制gui上棋子的位置等
            this.getParent().repaint();
        if(AISwitch){

//            AIDebate();//这里ai和棋盘还是一起渲染的
//            try{
//                Thread.sleep(100);
//            }catch (InterruptedException e){
//                Thread.currentThread().interrupt();//清除线程中断状态
//            }
            AdvancedAIDebate();
        }
    }

    public void switchAI(boolean choice){
        AISwitch = choice;
    }

    public void AIDebate(){
        ChessAI chessAI=new ChessAI(model);
        int[] AIMove=chessAI.getAIMove();
        if(AIMove!=null){
            int formerRow=AIMove[0];
            int formerCol=AIMove[1];
            int targetRow=AIMove[2];
            int targetCol=AIMove[3];

            AbstractPiece AIPiece=model.getPieceAt(formerRow,formerCol);
            if(AIPiece!=null&&!model.isRedTurn()){
                model.movePiece(AIPiece,targetRow,targetCol);
                System.out.println("aaa");
            }
        }
        this.getParent().repaint();
    }

    public void AdvancedAIDebate(){
        AdvancedAI advancedAI=new AdvancedAI(model);
        int[] AIMove=advancedAI.getBestMove();
        if(AIMove!=null){System.out.println(AIMove.length);
            int formerRow=AIMove[0];
            int formerCol=AIMove[1];
            int targetRow=AIMove[2];
            int targetCol=AIMove[3];

            AbstractPiece AIPiece=model.getPieceAt(formerRow,formerCol);

            if(AIPiece!=null&&!model.isRedTurn()){
                model.movePiece(AIPiece,targetRow,targetCol);
//                model.aiSwitch(true);
//                model.checkMove(AIPiece.getNumber(), targetRow, targetCol);
            }
        }
        this.getParent().repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Demo的GUI都是由Swing中基本的组件组成的，比如背景的格子是用许多个line组合起来实现的，棋子是先绘制一个circle再在上面绘制一个text实现的
        // 因此绘制GUI的过程中需要自己手动计算每个组件的位置（坐标）
        drawBoard(g2d);
        drawPieces(g2d);
        drawHighlight(g2d);
    }

    /**
     * 绘制合法路径高亮
     */
    private void drawHighlight(Graphics2D g){
        if(selectedPiece==null){
            return;
        }

        g.setColor(new Color(0,255,0,120));
        int highlightSize=15;
        for(int row=0;row<=9;row++){
            for(int col=0;col<=8;col++){
                if(selectedPiece.canMoveTo(row,col,model)){
                    int lastRow=selectedPiece.getRow();
                    int lastCol=selectedPiece.getCol();
                    selectedPiece.setRow(row);
                    selectedPiece.setCol(col);
                    boolean isMeeting=model.isGeneralMeeting();
                    selectedPiece.setRow(lastRow);
                    selectedPiece.setCol(lastCol);
                    if(!isMeeting){
                        int x=MARGIN+col*CELL_SIZE;
                        int y=MARGIN+row*CELL_SIZE;
                        g.fillOval(x-highlightSize/2,y-highlightSize/2,highlightSize,highlightSize);
                    }
                }
            }
        }
    }

    /**
     * 绘制棋盘
     */
    private void drawBoard(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(2));

        // 绘制横线
        for (int i = 0; i < ChessBoardModel.getRows(); i++) {
            int y = MARGIN + i * CELL_SIZE;
            g.drawLine(MARGIN, y, MARGIN + (ChessBoardModel.getCols() - 1) * CELL_SIZE, y);
        }

        // 绘制竖线
        for (int i = 0; i < ChessBoardModel.getCols(); i++) {
            int x = MARGIN + i * CELL_SIZE;
            if (i == 0 || i == ChessBoardModel.getCols() - 1) {
                // 两边的竖线贯通整个棋盘
                g.drawLine(x, MARGIN, x, MARGIN + (ChessBoardModel.getRows() - 1) * CELL_SIZE);
            } else {
                // 中间的竖线分为上下两段（楚河汉界断开）
                g.drawLine(x, MARGIN, x, MARGIN + 4 * CELL_SIZE);
                g.drawLine(x, MARGIN + 5 * CELL_SIZE, x, MARGIN + (ChessBoardModel.getRows() - 1) * CELL_SIZE);
            }
        }

        //绘制红方斜线
        int x1=MARGIN+CELL_SIZE*3;
        int x2=MARGIN+CELL_SIZE*5;
        int y1=MARGIN+CELL_SIZE*7;
        int y2=MARGIN+CELL_SIZE*9;
        g.drawLine(x1,y1,x2,y2);
        g.drawLine(x2,y1,x1,y2);

        //绘制黑方斜线
        int y3= MARGIN;
        int y4=MARGIN+CELL_SIZE*2;
        g.drawLine(x1,y3,x2,y4);
        g.drawLine(x2,y3,x1,y4);

        // 绘制“楚河”和“汉界”这两个文字
        g.setColor(Color.BLACK);
        g.setFont(new Font("楷体", Font.BOLD, 24));

        int riverY = MARGIN + 4 * CELL_SIZE + CELL_SIZE / 2;

        String chuHeText = "楚河";
        FontMetrics fm = g.getFontMetrics();
        int chuHeWidth = fm.stringWidth(chuHeText);
        g.drawString(chuHeText, MARGIN + CELL_SIZE * 2 - chuHeWidth / 2, riverY + 8);

        String hanJieText = "汉界";
        int hanJieWidth = fm.stringWidth(hanJieText);
        g.drawString(hanJieText, MARGIN + CELL_SIZE * 6 - hanJieWidth / 2, riverY + 8);
    }

    /**
     * 绘制棋子
     */
    private void drawPieces(Graphics2D g) {
        // 遍历棋盘上的每一个棋子，每次循环绘制该棋子
        for (AbstractPiece piece : model.getPieces()) {
            // 计算每一个棋子的坐标
            int x = MARGIN + piece.getCol() * CELL_SIZE;
            int y = MARGIN + piece.getRow() * CELL_SIZE;

            boolean isSelected = (piece == selectedPiece);

            // 先绘制circle
            g.setColor(new Color(245, 222, 179));
            g.fillOval(x - PIECE_RADIUS, y - PIECE_RADIUS, PIECE_RADIUS * 2, PIECE_RADIUS * 2);
            // 绘制circle的黑色边框
            g.setColor(Color.BLACK);
            g.setStroke(new BasicStroke(2));
            g.drawOval(x - PIECE_RADIUS, y - PIECE_RADIUS, PIECE_RADIUS * 2, PIECE_RADIUS * 2);

            if (isSelected) {
                if(selectedPiece.isRed()){
                    g.setColor(new Color(255, 10, 25));
                }
                else {
                    g.setColor(new Color(0,100,255));
                }
                drawCornerBorders(g, x, y);
            }

            // 再在circle上面绘制对应的棋子名字
            if (piece.isRed()) {
                g.setColor(new Color(200, 0, 0));
            } else {
                g.setColor(Color.BLACK);
            }
            g.setFont(new Font("楷体", Font.BOLD, 22));
            FontMetrics fm = g.getFontMetrics();
            int textWidth = fm.stringWidth(piece.getName());
            int textHeight = fm.getAscent();
            g.drawString(piece.getName(), x - textWidth / 2, y + textHeight / 2 - 2);
        }

        int lastX=MARGIN+getLastCol*CELL_SIZE;
        int lastY=MARGIN+getLastRow*CELL_SIZE;
        if(lastY>=0&&lastX>=0){
            if(formerPiece!=null){
                if(formerPiece.isRed()){
                    g.setColor(new Color(255, 10, 25));
                }
                if(!formerPiece.isRed()){
                    g.setColor(new Color(0,100,255));
                }
            }
            drawCornerBorders(g,lastX,lastY);
        }


        int currentX=MARGIN+CELL_SIZE*getTargetCol;
        int currentY=MARGIN+CELL_SIZE*getTargetRow;
        if(currentY>=0&&currentX>=0){
            if(formerPiece!=null){
                if(formerPiece.isRed()){
                    g.setColor(new Color(255, 10, 25));
                }
                if(!formerPiece.isRed()){
                    g.setColor(new Color(0,100,255));
                }
            }
            drawCornerBorders(g,currentX,currentY);
        }

    }

    /**
     * 绘制选中棋子时的蓝色外边框效果
     */
    private void drawCornerBorders(Graphics2D g, int centerX, int centerY) {
        g.setStroke(new BasicStroke(3));

        int cornerSize = 32;
        int lineLength = 12;

        // 选中效果的边框实际上是8条line，每两个line组成一个角落的边框

        // 左上角的边框
        g.drawLine(centerX - cornerSize, centerY - cornerSize,
                centerX - cornerSize + lineLength, centerY - cornerSize);
        g.drawLine(centerX - cornerSize, centerY - cornerSize,
                centerX - cornerSize, centerY - cornerSize + lineLength);

        // 右上角的边框
        g.drawLine(centerX + cornerSize, centerY - cornerSize,
                centerX + cornerSize - lineLength, centerY - cornerSize);
        g.drawLine(centerX + cornerSize, centerY - cornerSize,
                centerX + cornerSize, centerY - cornerSize + lineLength);

        // 左下角的边框
        g.drawLine(centerX - cornerSize, centerY + cornerSize,
                centerX - cornerSize + lineLength, centerY + cornerSize);
        g.drawLine(centerX - cornerSize, centerY + cornerSize,
                centerX - cornerSize, centerY + cornerSize - lineLength);

        // 右下角的边框
        g.drawLine(centerX + cornerSize, centerY + cornerSize,
                centerX + cornerSize - lineLength, centerY + cornerSize);
        g.drawLine(centerX + cornerSize, centerY + cornerSize,
                centerX + cornerSize, centerY + cornerSize - lineLength);
    }
}
