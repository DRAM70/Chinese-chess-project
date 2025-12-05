//package edu.sustech.xiangqi;
//
//import edu.sustech.xiangqi.model.ChessBoardModel;
//
//import java.io.*;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.ArrayList;
//import java.util.Scanner;
//
//public class RetractBox {
//    public static boolean emptyCheck(String tmpUser){//如果存档(Temp)为空，返回true
//        user = tmpUser;
//        String relativePath = "UserData/" + user + "/" + user +"Temp.txt";
//        Path path = Paths.get(relativePath).toAbsolutePath().normalize();
//
//        try{
//            if(Files.exists(path)){
//                if(Files.size(path) != 0){
//                    return false;
//                }
//            }
//            return true;
//        }catch (IOException e){
//            System.out.println("???");
//        }
////        NoticeBox noticeBox = new NoticeBox("a", "b", 0, "?");
////        noticeBox.setVisible(true);
//        return true;
//    }
//
//    public static ChessBoardModel originalModel(String tmpUser, ChessBoardModel model, int moveIndex){//可以确定到哪一步？
//        user = tmpUser;
//        if(emptyCheck(user)){
//            return null;
//        }
//
//        if(checkLogLiteExistence()){
//            if(decoder()){
////                ReplayFrame replayFrame = new ReplayFrame(title, user, preModel, style);
////                this.setVisible(false);
////                replayFrame.setVisible(true);
//                readRecord();
//                for(int i = 0; i < moveList.size(); i++){
//                    model.checkMove(moveBreaker(i, 0),
//                            moveBreaker(i, 1),
//                            moveBreaker(i, 2));
//                }
//                return model;
//            }else{
//                deleteBrokenLog();
//                ChoiceBox.notice("警告", "您的存档已损坏！已清理！");
//                return null;
//            }
//        }
//        return null;
//
//
////        if(stepIndex == moveList.size()){
//////            label.setText("已经是最后一步啦！");
////        }else{
////            modelIN.checkMove(moveBreaker(stepIndex, 0),
////                    moveBreaker(stepIndex, 1),
////                    moveBreaker(stepIndex, 2));
////            stepIndex++;
////        }
//    }
//
//    private static int logCodeKey(){
//        File file = new File(".\\UserInfo.txt");
//        Scanner in;
//        try{
//            in = new Scanner(file);
//            while(in.hasNextLine()){
//                String existingUsername = in.nextLine();
//                if(user.equals(existingUsername)){
//                    in.nextLine();
//                    in.nextLine();
//                    in.nextLine();
//                    in.nextLine();
//                    in.nextLine();
//                    return Integer.parseInt(in.nextLine());
//                }else{
//                    in.nextLine();
//                    in.nextLine();
//                    in.nextLine();
//                    in.nextLine();
//                    in.nextLine();
//                    in.nextLine();
//                }
//            }
//            System.out.println(user + " doesn't exist as a username!");
//            return -1;
//        }catch(FileNotFoundException e){
//            System.out.println("File UserInfo.txt not found!");
//        }
//        return -1;
//    }
//
//    private static boolean decoder(){
//        int code = 0;
//
//        File moveFile = new File("UserData/" + user + "/" + user +".txt");
//
//
//        if (!moveFile.setWritable(true)) {
//            System.out.println(user + ".txt can't be set readable!");
//        }
//        ///
//        try(BufferedReader reader = new BufferedReader(new FileReader(moveFile))){
//
//            String currentLine;
//            while((currentLine = reader.readLine()) != null){
//                code += Integer.parseInt(currentLine);
//            }
//            reader.close();
//        }catch(Exception e){
//            System.out.println("encoder failed!");
//        }
//
//        int tempCode = (code-13*17)*31;
//        int logCode = logCodeKey();
//
//        if(tempCode == logCode){
//            return true;
//        }else{
//            return false;
//        }
//
//    }
//
//    private static boolean checkLogLiteExistence(){
//        File file = new File("UserData/" + user + "/" + user +"Temp.txt");
//        if(!file.exists()){
//            return false;
//        }
//        return true;
//    }
//
//    private static void readRecord(){
//        //需要实现checklog来保证log不变
////        decoder();
//        File file = new File("UserData/" + user + "/" + user +".txt");
//        try{
//            Scanner sc = new Scanner(file);
//            moveList = new ArrayList<>();
//            while(sc.hasNextLine()){
//                String move = sc.nextLine();
//                moveList.add(move);
//            }
//        }catch(FileNotFoundException e){
//            //需要添加弹窗
//            System.out.println(user + "'s log reading failed!");
//        }
//    }
//
//    private static int moveBreaker(int index, int part){
//        //part 0: the code for the chess
//        //part 1: the column
//        //part 2: the row
//        String s = moveList.get(index);
//        String chessIndex = "" + s.charAt(0) + s.charAt(1);
//        String column = "" + s.charAt(3);
//        String row = "" + s.charAt(5);
//        if(part == 0){
//            return Integer.parseInt(chessIndex);
//        }
//        if(part == 1){
//            return Integer.parseInt(column);
//        }
//        if(part == 2){
//            return Integer.parseInt(row);
//        }
//        return -1;
//    }
//
//    private static void deleteBrokenLog(){
//        File file = new File("UserData/" + user + "/" + user + "Temp.txt");
//        if(file.delete()){
//            System.out.println(user + "Temp.txt failed to delete!");
//        }else{
//            System.out.println(user + "Temp.txt successfully deleted!");
//        }
//    }
//
//}
