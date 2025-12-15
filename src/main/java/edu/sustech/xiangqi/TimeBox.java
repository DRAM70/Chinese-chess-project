package edu.sustech.xiangqi;

import edu.sustech.xiangqi.model.ChessBoardModel;

public class TimeBox {
    private int redTime;
    private int blackTime;
    public static boolean isRedTurn = true;
    private Thread timerThread;
    private boolean isRunning;
    private ChessBoardModel timeModel;
    public static boolean isPause = false;
//    private boolean isPause;


    public TimeBox(int redTime, int blackTime, ChessBoardModel timeModel){
        this.redTime = redTime;
        this.blackTime = blackTime;
        this.timeModel = timeModel;
        start();
    }

    public int getRedTime(){
        return redTime;
    }

    public int getBlackTime(){
        return blackTime;
    }

    public void start(){
        if(isRunning)return;



        isRunning = true;
        timerThread = new Thread(() -> {
            while (isRunning) {
                try{
                    Thread.sleep(100);
                }catch(InterruptedException e){
                    Thread.currentThread().interrupt();//清除线程中断状态
                }
                while(!isPause){
                    try{
                        Thread.sleep(1000);

                        if(isRedTurn){
                            redTime--;
                            //label设定时间
                            TimingFrame.redTimeLabel.setText(formatTime(redTime));
                            timeModel.redTime = redTime;
                            TimingFrame.redTime = redTime;
                            TimingFrame.blackTimeLabel.setText(formatTime(blackTime));
                            timeModel.blackTime = blackTime;
                            TimingFrame.blackTime = blackTime;
                        }else{
                            blackTime--;
                            TimingFrame.blackTimeLabel.setText(formatTime(blackTime));
                            timeModel.blackTime = blackTime;
                            TimingFrame.blackTime = blackTime;
                            TimingFrame.redTimeLabel.setText(formatTime(redTime));
                            timeModel.redTime = redTime;
                            TimingFrame.redTime = redTime;
                            //label设定时间
                        }

                        if((isRedTurn && redTime <= 0) || (!isRedTurn && blackTime <= 0)){
                            stop();
                            TimingFrame.getInstance().end("计时结束！");
                            Thread.currentThread().interrupt();
                            break;
                        }

                    }catch(InterruptedException e){
                        Thread.currentThread().interrupt();
                        break;
                    }
                }

            }
        });

        timerThread.start();
    }

    public void stop(){
        isRunning = false;
        if(timerThread != null){
            timerThread.interrupt();
        }
    }

    public static void switchTurn(){
        isRedTurn = !isRedTurn;
    }

    public static String formatTime(int seconds){
        int min = seconds / 60;
        int sec = seconds % 60;
        return String.format("%02d:%02d", min, sec);
    }

}
