package Vue;
import Modele.*;

import java.awt.image.BufferedImage;

public class Sceau{
    public final int longeur = 100; // m
    public final int hauteur = 50;
    public double speedX = 100; // m

    private int bordure = (longeur * 65) / 405;

    public double X, Y; // m
    private Court court;
    private BufferedImage image; 

    Sceau(Court court){
        System.out.println(bordure);
        this.court = court;
        X = court.getWidth()/2 - longeur/2;
        Y = court.getHeight() - (hauteur);

        image = ImageImport.getImage("bucket.png", longeur, hauteur);

        // tester si le sceau est bien dans le court
        
    }


    public BufferedImage getImage(){
        return image;
    }


    public void move(double deltaT){
        double nextX = X + deltaT * speedX;

        // speedX = speedX
        if (touchedWallX(nextX)){
            speedX = -speedX;
            nextX = X + deltaT * speedX;
        };
        //System.out.println(inside(b));
        X = nextX;
    }

    public boolean inside(Ball b){
        return X + bordure <= b.nextBallX && b.nextBallX + Ball.ballRadius*2 <= X + longeur - bordure &&
        Y  <= b.nextBallY && b.nextBallY <= Y + hauteur;

    }

    public boolean touchedWallX(double nextX){
        return nextX < 0 || nextX> court.getWidth() - longeur;
    }
    public boolean toucheBordureSceau(Ball b){
        return ((X <= b.nextBallX + Ball.ballRadius*2  && b.nextBallX   <= X + bordure) || (X + longeur - bordure  <= b.nextBallX + Ball.ballRadius*2  && b.nextBallX <= X  + longeur) ) && 
        Y <= b.nextBallY && b.nextBallY <= Y + hauteur; //hardcoding pour toucher plus bas
    }

    public Court getCourt(){
        return court;
    }
}