package edu.sustech.xiangqi.audio;


import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

public class BackgroundMusic {
    private static AudioClip audio;



    public static void playLoop(){
        String path = ".\\src\\main\\resources\\backLoop2.wav";
//        String path = "/backLoop.wav";
        try{
            if(audio != null){
                audio.stop();
            }

            URL url = new java.io.File(path).toURI().toURL();
            audio = Applet.newAudioClip(url);
            audio.loop();
        }catch(Exception e){
            System.out.println("unable to play background music " + path);
        }
    }


    //playClick()是普通移动子后的音效
    public static void playClick(){
        playShort("click.wav");
    }

    //将军时的音效
    public static void playGeneralInDanger(){
        playShort("generalInDanger.wav");
    }

    //吃子时的音效
    public static void playEat(){
        playShort("eat.wav");
    }



    public static void playShort(String name){
        try{
            String path = ".\\src\\main\\resources\\" + name;
            URL url = new java.io.File(path).toURI().toURL();
            audio = Applet.newAudioClip(url);
            audio.play();
        }catch(Exception e){
            System.out.println("unable to play background music " + name);
        }
    }
}