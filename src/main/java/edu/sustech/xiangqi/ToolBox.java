package edu.sustech.xiangqi;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class ToolBox {
    private static String user;

    public static void confirmToEnd(String userOp){//本方法可以保存存档，保留log的缩减形式以供确认
        user = userOp;
        initializeLog();
        confirmWriter();
        encoder();
    }




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

    private static void lineRewriterCode(int a){
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

                    reader.readLine();
                    writer.write("" + a);
                    writer.newLine();
                }else{
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

    private static void encoder(){
        int code = 0;


        File moveFile = new File("UserData/" + user + "/" + user +".txt");


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



        lineRewriterCode((code-13*17)*31);
    }


}
