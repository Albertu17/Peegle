package Modele;

import java.awt.Image;
import java.awt.image.BufferedImage;

import Vue.Court;
import Vue.ImageImport;
import Vue.Sceau;
public class Ball{

    public final static int ballRadius = 10; // m

    public double ballX, ballY; // m
    public double ballSpeedX, ballSpeedY; // m

    public double nextBallX,nextBallY;

    // private int height=500;
    // private int width=500;

    private int yMarxgin = 3;
    public double p1,p2;

    private Pegs dernierPegToucher = null;

    private boolean ispresent = true;
    private boolean atoucher = false;
    private boolean atoucherpegs = false;
    private boolean hitground = false;


    private double g=300; // m/s
    private double coeffRebond = 0.8;
    private int combo = 0;
    private static BufferedImage image = ImageImport.getImage("ball.png", (int) ballRadius*2, (int) ballRadius*2);

    private Court court;

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
        System.out.println((int) ballSpeedX + "    " + (int) ballSpeedY);
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
            // System.out.println("inside");
            sceau.getCourt().augmenteNbDeBall();
            hitground=true;
            ispresent=false;
        }
        
        Pegs p = touchedPegs();

        if (p!=null){
            if (!p.getHit()){combo++;}
            p.setTouche(true);
            double ux = (nextBallX+ballRadius) - (p.getX());
            double uy = (nextBallY+ballRadius) - (p.getY());
            double vx = ballSpeedX;
            double vy = ballSpeedY;
            ballSpeedX = vx - 2*ux*(ux*vx + uy*vy)/(ux*ux + uy*uy);
            ballSpeedY = vy - 2*uy*(ux*vx + uy*vy)/(ux*ux + uy*uy);
            ballSpeedX = coeffRebond * ballSpeedX;
            ballSpeedY = coeffRebond * ballSpeedY;
            atoucherpegs=true;
        }
        else if (p==null){
            atoucherpegs=false;
        }
        ballX = nextBallX;
        ballY = nextBallY;
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
    


    public Pegs touchedPegs(){
        for (Pegs peg: court.getPegs()){
            if (peg.contains(nextBallX + ballRadius, nextBallY + ballRadius)){                
                if (dernierPegToucher != peg) {
                    dernierPegToucher = peg;
                    return peg;
                }
                if (dernierPegToucher.contains(nextBallX + ballRadius, nextBallY + ballRadius)) {
                    dernierPegToucher = peg;
                    return null;
                }
    
            }
        }
        
        dernierPegToucher = null;
        return null;
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
    public void putSkin1(){
        image = ImageImport.getImage("ball.png", 20, 20);
    }

    public void putSkin2(){
        image = ImageImport.getImage("soccerBall.png", 20, 20);
    }


}