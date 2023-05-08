package Modele;

import java.awt.Image;
import java.awt.image.BufferedImage;

import Vue.Court;
import Vue.ImageImport;
import Vue.Sceau;
import java.io.*;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
public class Ball{

    public final static int ballRadius = 10; // m

    public double ballX, ballY; // m
    public double ballSpeedX, ballSpeedY; // m

    public double nextBallX,nextBallY;

    // private int height=500;
    // private int width=500;
    private static boolean musicOn = true;

    private int yMarxgin = 3;
    public double p1,p2;

    private boolean ispresent = true;
    private boolean atoucher = false;
    private boolean atoucherpegs = false;
    private boolean hitground = false;

    private double g=300; // m/s
    private double coeffRebond = 0.8;
    private int combo = 0;

    private static int selecteurImage = 0 ;
    public static int getSelecteurImage() {
        return selecteurImage;
    }

    public static void setSelecteurImage(int selecteurImage) {
        Ball.selecteurImage = selecteurImage;
    }
    private static BufferedImage image = ImageImport.getImage("Ball/ball.png", (int) ballRadius*2, (int) ballRadius*2);
    public static void setImage(BufferedImage skin){
        Ball.image = skin ;
    }
   
 
    private Court court;
    private Pegs pegderniertoucher;
    public Clip audioClip;

    public boolean inLevel = true;

    double x,y;


    /* Important coordonée de la balle centre en X mais tout en haut pour Y */

    public Court getCourt() {
        return court;
    }

    public static BufferedImage getImgBall(){
        return image;
    }

    public Ball(int x,int y,int vx0,int vy0,Court c){
        ballX=x;
        ballY=y;
        ballSpeedX=vx0;
        ballSpeedY=vy0;
        court=c;
    }
    public double getG() {
        return g;
    }

    public void setPresent(boolean b){
        ispresent = b;
    }

    public boolean isPresent(){
        return ispresent;
    }

    public void updateBall(double deltaT,Sceau sceau) {
        // first, compute possible next position if nothing stands in the way
        nextBallX = ballX + deltaT * ballSpeedX;
        nextBallY = ballY + deltaT * ballSpeedY + 1/2*g*(deltaT*deltaT);

        ballSpeedY = ballSpeedY + g*deltaT;
        
        // next, see if the ball would meet some obstacle
        if (touchedWallY(nextBallY)) { 
            ballSpeedY = -ballSpeedY*coeffRebond;
            // System.out.println("touched wall Y");
            nextBallY = ballY + deltaT * ballSpeedY + 1/2*g*(deltaT*deltaT);
        }

        if (touchedWallX(nextBallX)){
            ballSpeedX = -ballSpeedX;
            nextBallX = ballX + deltaT * ballSpeedX;
        }
        if (sceau.toucheBordureSceau(this)){
            ballSpeedY =- ballSpeedY;
        }

        if (sceau.inside(this)){
            System.out.println("inside");
            sceau.getCourt().augmenteNbDeBall();
            hitground=true;
            ispresent=false;
        }
        

        Pegs p = touchedPegs();



        
        if (p!=null && p != pegderniertoucher){
        if (p!=null && !atoucherpegs && inLevel){
            if(musicOn){
                try {
                    playSound();
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
            if (!p.getHit()){combo++;}
            p.setTouche(true);
            double ux = (nextBallX+ ballRadius) - (p.getX());
            double uy = (nextBallY+ballRadius) - (p.getY());
            double vx = ballSpeedX;
            double vy = ballSpeedY;
            ballSpeedX = vx - 2*ux*(ux*vx + uy*vy)/(ux*ux + uy*uy);
            ballSpeedY = vy - 2*uy*(ux*vx + uy*vy)/(ux*ux + uy*uy);
            ballSpeedX = coeffRebond * ballSpeedX;
            ballSpeedY = coeffRebond * ballSpeedY;
            
            pegderniertoucher = p;
        }
        }else if (p==null){
            pegderniertoucher = null;
        }
        ballX = nextBallX;
        ballY = nextBallY;
    }

    public void inLevelTrue(){
        inLevel = true;
    }
    public void inLevelFalse(){
        inLevel = false;
    }


    public boolean touchedWallX(double nextBallX){
        return nextBallX < 0 || nextBallX> court.getWidth() - ballRadius;
    }

    public boolean touchedWallY(double nextBallY){
        if (nextBallY > court.getHeight() - ballRadius*2 - 15){
            hitground=true;
        }
        return nextBallY < 0 || nextBallY > court.getHeight() - ballRadius*2 - 15;
    }

    private void playSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File audioFile = new File("Modele/hitsound.wav");
 
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
        AudioFormat format = audioStream.getFormat();
 
        DataLine.Info info = new DataLine.Info(Clip.class, format); 
        audioClip = (Clip) AudioSystem.getLine(info);
        audioClip.open(audioStream);
        audioClip.start();
    }


    


    public Pegs touchedPegs(){
        Pegs p=null;
        for (Pegs peg: court.getPegs()){
            
            if(peg.contains(nextBallX + ballRadius, nextBallY + ballRadius )){
                p=peg;
            }
        }
        if (p==null){
            atoucherpegs=false;
        }
        return p;
    }

    public boolean getHitGround(){
        return hitground;
    }

    public Image getImage() {
        return image;
    }

    public int getCombo() {
        return combo;
    }

    public void setCombo(int i) {
        combo = i;
    }

    public static void setMusicOn(){
        Ball.musicOn = true;
    }
    public static void setMusicOff(){
        Ball.musicOn = false;
    }
    



}