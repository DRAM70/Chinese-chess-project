package edu.sustech.xiangqi.audio;


import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.URL;

public class BackgroundMusic {
    private static AudioClip audio;



    public static void playLoop(){
        String path = ".\\src\\main\\resources\\backLoop2.wav";
//        String path = "/backLoop.wav";
        try{
            Clip clip;
            AudioInputStream audio = AudioSystem.getAudioInputStream(new File(path));
            clip = AudioSystem.getClip();
            clip.open(audio);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
            //独立线程播放，解决音乐中断问题

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