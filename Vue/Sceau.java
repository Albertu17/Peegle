package Vue;
import Modele.*;

import java.awt.image.BufferedImage;

public class Sceau{
    public final int longeur = 100; // m
    public final int hauteur = 50;
    private int bordure = 10; 
    public double speedX = 100; // m

    public double X, Y; // m
    private Court court;
    private BufferedImage image; 

    Sceau(Court court){
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
        return X <= b.nextBallX && b.nextBallX + Ball.ballRadius*2 <= X + longeur &&
        Y  <= b.nextBallY && b.nextBallY <= Y + hauteur;
        
        // return X <= b.nextBallX && b.nextBallX + Ball.ballRadius*2 <= X + longeur &&
        // Y  + 20 <= b.nextBallY && b.nextBallY + 20 <= Y + hauteur;

    }

    public boolean touchedWallX(double nextX){
        return nextX < 0 || nextX> court.getWidth() - longeur;
    }
    public boolean toucheBordureSceau(Ball b){
        return ((X <= b.nextBallX + Ball.ballRadius*2 && b.nextBallX  <= X + bordure) || (X + longeur - bordure  <= b.nextBallX + Ball.ballRadius*2 && b.nextBallX <= X  + longeur) ) && 
        Y + 10  <= b.nextBallY && b.nextBallY + 10 <= Y + hauteur; //hardcoding pour toucher plus bas
    }

    public Court getCourt(){
        return court;
    }
}