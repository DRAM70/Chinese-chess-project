package edu.sustech.xiangqi;

import edu.sustech.xiangqi.model.ChessBoardModel;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Scanner;

public class ToolBox {
    private static String user;
    private static ArrayList<String> moveList = new ArrayList<>();

    public static void confirmToEnd(String userOp){//本方法可以保存已完成的存档，保留log的缩减形式以供确认
        user = userOp;
        initializeLog();
        confirmWriter();
        encoder(0);
    }
    public static void tempEnd(String userOp){//本方法可以暂时存储temp不变，保留log的缩减形式以供确认
        user = userOp;
        initializeLog();
        //confirmWriter();
        encoder(1);
    }



    public static boolean emptyCheck(String tmpUser){//如果存档(Temp)为空，返回true
        user = tmpUser;
        String relativePath = "UserData/" + user + "/" + user +"Temp.txt";
        Path path = Paths.get(relativePath).toAbsolutePath().normalize();

        try{
            if(Files.exists(path)){
                if(Files.size(path) != 0){
                    return false;
                }
            }
            return true;
        }catch (IOException e){
            System.out.println("???");
        }
//        NoticeBox noticeBox = new NoticeBox("a", "b", 0, "?");
//        noticeBox.setVisible(true);
        return true;
    }

    public static ChessBoardModel originalModel(String tmpUser, ChessBoardModel model){//返回之前未完成的model
        user = tmpUser;
        if(emptyCheck(user)){
            return null;
        }

        if(checkLogLiteExistence()){
            if(decoder()){
//                ReplayFrame replayFrame = new ReplayFrame(title, user, preModel, style);
//                this.setVisible(false);
//                replayFrame.setVisible(true);
                readRecord();
                for(int i = 0; i < moveList.size(); i++){
                    model.checkMove(moveBreaker(i, 0),
                            moveBreaker(i, 1),
                            moveBreaker(i, 2));
                }
                return model;
            }else{
                deleteBrokenLog();
                ChoiceBox.notice("警告", "您的存档已损坏！已清理！");
                return null;
            }
        }
        return null;


//        if(stepIndex == moveList.size()){
////            label.setText("已经是最后一步啦！");
//        }else{
//            modelIN.checkMove(moveBreaker(stepIndex, 0),
//                    moveBreaker(stepIndex, 1),
//                    moveBreaker(stepIndex, 2));
//            stepIndex++;
//        }
    }

    private static int logCodeKey(){
        File file = new File(".\\UserInfo.txt");
        Scanner in;
        try{
            in = new Scanner(file);
            while(in.hasNextLine()){
                String existingUsername = in.nextLine();
                if(user.equals(existingUsername)){
                    in.nextLine();
                    in.nextLine();
                    in.nextLine();
//                    in.nextLine();
//                    in.nextLine();
                    return Integer.parseInt(in.nextLine());
                }else{
                    in.nextLine();
                    in.nextLine();
                    in.nextLine();
                    in.nextLine();
                    in.nextLine();
                    in.nextLine();
                }
            }
            System.out.println(user + " doesn't exist as a username!");
            return -1;
        }catch(FileNotFoundException e){
            System.out.println("File UserInfo.txt not found!");
        }
        return -1;
    }

    private static boolean decoder(){
        int code = 0;

        File moveFile = new File("UserData/" + user + "/" + user +"Temp.txt");


        if (!moveFile.setWritable(true)) {
            System.out.println(user + ".txt can't be set readable!");
        }
        ///
        try(BufferedReader reader = new BufferedReader(new FileReader(moveFile))){

            String currentLine;
            while((currentLine = reader.readLine()) != null){
                code += Integer.parseInt(currentLine);
            }
            reader.close();
        }catch(Exception e){
            System.out.println("encoder failed!");
        }

        int tempCode = (code-13*17)*31;
        int logCode = logCodeKey();

        if(tempCode == logCode){
            return true;
        }else{
            return false;
        }

    }

    private static boolean checkLogLiteExistence(){
        File file = new File("UserData/" + user + "/" + user +"Temp.txt");
        if(!file.exists()){
            return false;
        }
        return true;
    }

    private static void readRecord(){
        //需要实现checklog来保证log不变
//        decoder();
        File file = new File("UserData/" + user + "/" + user +"Temp.txt");
        try{
            Scanner sc = new Scanner(file);
            moveList = new ArrayList<>();
            while(sc.hasNextLine()){
                String move = sc.nextLine();
                moveList.add(move);
            }
        }catch(FileNotFoundException e){
            //需要添加弹窗
            System.out.println(user + "'s log reading failed!");
        }
    }

    private static int moveBreaker(int index, int part){
        //part 0: the code for the chess
        //part 1: the column
        //part 2: the row
        String s = moveList.get(index);
        String chessIndex = "" + s.charAt(0) + s.charAt(1);
        String column = "" + s.charAt(3);
        String row = "" + s.charAt(5);
        if(part == 0){
            return Integer.parseInt(chessIndex);
        }
        if(part == 1){
            return Integer.parseInt(column);
        }
        if(part == 2){
            return Integer.parseInt(row);
        }
        return -1;
    }

    private static void deleteBrokenLog(){
        File file = new File("UserData/" + user + "/" + user + "Temp.txt");
        if(file.delete()){
            System.out.println(user + "Temp.txt failed to delete!");
        }else{
            System.out.println(user + "Temp.txt successfully deleted!");
        }
    }




    //下面是结束时用的
    private static void initializeLog(){
        try{
            String relativePath = "UserData/" + user + "/" + user +".txt";
            File file = new File(relativePath);
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));



            writer.write( "");
            System.out.println(user + "'s log file is initialized!");



            writer.flush();
            writer.close();
        }catch(IOException e){
            System.out.println("Error, file " + user + "Temp.txt is broken! And log initializing failed!");
        }
    }

    private static void confirmWriter(){
        File bluePrint = new File("UserData/" + user + "/" + user +"Temp.txt");
        File formalPrint = new File("UserData/" + user + "/" + user +".txt");


        if (!bluePrint.setWritable(true)) {
            System.out.println(user + "Temp.txt can't be set readable!");
        }

        if (!formalPrint.setWritable(true)) {
            System.out.println(user + ".txt can't be set readable!");
        }

        try{
            Path filePath = Paths.get("UserData/" + user + "/" + user +".txt");
            Files.newBufferedWriter(filePath, StandardOpenOption.TRUNCATE_EXISTING).close();
            System.out.println(user + ".txt is empty now!");
        }catch(IOException e){
            System.out.println("Fail to empty " + user + ".txt!");
        }



        try(BufferedReader reader = new BufferedReader(new FileReader(bluePrint));
            BufferedWriter writer = new BufferedWriter(new FileWriter(formalPrint))){

            String currentLine;



            while((currentLine = reader.readLine()) != null){
                writer.write(currentLine);
                writer.newLine();
            }
            writer.flush();
            writer.close();
            reader.close();
        }catch(Exception e){
            System.out.println("confirmWriter failed!");
        }


        try{
            Path filePath = Paths.get("UserData/" + user + "/" + user +"Temp.txt");
            Files.newBufferedWriter(filePath, StandardOpenOption.TRUNCATE_EXISTING).close();
            System.out.println(user + "Temp.txt is empty now!");
        }catch(IOException e){
            System.out.println("Fail to empty " + user + "Temp.txt!");
        }
    }

    private static void lineRewriterCode(int a, int index){
        File originalFile = new File(".\\UserInfo.txt");
        File tmpFile = new File(".\\UserInfoTemp.txt");


        if (!originalFile.setWritable(true)) {
            System.out.println("UserInfo can't be set readable!");
        }

        if (!tmpFile.setWritable(true)) {
            System.out.println("UserInfo can't be set readable!");
        }




        try(BufferedReader reader = new BufferedReader(new FileReader(originalFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tmpFile))){

            String currentLine;

            while((currentLine = reader.readLine()) != null){
                writer.write(currentLine);
                writer.newLine();
                if(user.equals(currentLine)){
                    writer.write(reader.readLine());
                    writer.newLine();

                    writer.write(reader.readLine());
                    writer.newLine();



                    if(index ==0){
                        reader.readLine();
                        writer.write("" + a);
                        writer.newLine();

                        writer.write(reader.readLine());
                        writer.newLine();
                    }

                    if(index == 1){
                        writer.write(reader.readLine());
                        writer.newLine();

                        reader.readLine();
                        writer.write("" + a);
                        writer.newLine();
                    }

                    writer.write(reader.readLine());
                    writer.newLine();

                    writer.write(reader.readLine());
                    writer.newLine();
                }else{
                    writer.write(reader.readLine());
                    writer.newLine();
                    writer.write(reader.readLine());
                    writer.newLine();
                    writer.write(reader.readLine());
                    writer.newLine();
                    writer.write(reader.readLine());
                    writer.newLine();
                    writer.write(reader.readLine());
                    writer.newLine();
                    writer.write(reader.readLine());
                    writer.newLine();
                }
            }
        }catch(Exception e){
            System.out.println("LineRewriter failed!");
        }

        try{
            Path filePath = Paths.get(".\\UserInfo.txt");
            Files.newBufferedWriter(filePath, StandardOpenOption.TRUNCATE_EXISTING).close();
            System.out.println("UserInfo.txt is empty now!");
        }catch(IOException e){
            System.out.println("Fail to empty UserInfo.txt!");
        }




        try(BufferedReader reader = new BufferedReader(new FileReader(tmpFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(originalFile))){





            String currentLine;

            while((currentLine = reader.readLine()) != null){
                writer.write(currentLine);
                writer.newLine();
            }
            writer.flush();
            writer.close();
            reader.close();

            if(!tmpFile.delete()){
                System.out.println("File deletion failed!");
                throw new IOException("File deletion failed!");
            }
        }catch(Exception e){
            System.out.println("LineRewriter failed!");
        }
    }

    private static void encoder(int index){
        int code = 0;

        String extra = "";
        if(index == 1){
            extra = "Temp";
        }
        File moveFile = new File("UserData/" + user + "/" + user + extra + ".txt");//这里已经挪动完毕了


        if (!moveFile.setWritable(true)) {
            System.out.println(user + ".txt can't be set readable!");
        }
        ///
        try(BufferedReader reader = new BufferedReader(new FileReader(moveFile))){

            String currentLine = reader.readLine();
            while(currentLine != null){
                code += Integer.parseInt(currentLine);
                currentLine = reader.readLine();
            }
            reader.close();
        }catch(Exception e){
            System.out.println("encoder failed!");
        }



        lineRewriterCode((code-13*17)*31, index);
    }


    public static void deleteVisitorFile(){
        try{
            File fileToDelete = new File("UserData/游客6060/游客6060.txt");
            File fileToDelete2 = new File("UserData/游客6060/游客6060Temp.txt");
            if(fileToDelete.exists()){
                if(fileToDelete.delete() && fileToDelete2.delete()){
                    System.out.println("visitor log successfully deleted!");
                }else{
                    System.out.println("visitor log deleting failed!");
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }


}
