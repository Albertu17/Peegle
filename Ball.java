import java.util.ArrayList;
import java.util.Random;
import java.math.*;
import java.sql.Array;
public class Ball{

    public final double ballRadius = 25.0; // m

    public double ballX, ballY; // m
    public double ballSpeedX, ballSpeedY; // m

    private int height=500;
    private int width=500;

    private int yMarxgin = 3;

    private boolean ispresent = true;

    private double g=200; // m/s

    private ArrayList<Ball> ballAdjacentes=new ArrayList<>();

    /* Important coordonée de la balle centre en X mais tout en haut pour Y */

    Ball(int x,int y,int vx0,int vy0){
        ballX=x;
        ballY=y;
        ballSpeedX=vx0;
        ballSpeedY=vy0;
    }

    public void updateBall(double deltaT) {
        // first, compute possible next position if nothing stands in the way
        double nextBallX = ballX + deltaT * ballSpeedX;
        double nextBallY = ballY + deltaT * ballSpeedY + 1/2*g*(deltaT*deltaT);

        //ballSpeedX = ballSpeedX + g*deltaT;
        ballSpeedY = ballSpeedY + g*deltaT;
        // next, see if the ball would meet some obstacle
        if (nextBallY < 0 || nextBallY > height- ballRadius*2 - yMarxgin || touchedBall()) { 
            ballSpeedY = -ballSpeedY*0.8;
            nextBallY = ballY + deltaT * ballSpeedY + 1/2*g*(deltaT*deltaT);
        }

        if (nextBallX < 0 || nextBallX> width - ballRadius || touchedBall()){
            ballSpeedX = -ballSpeedX;
            nextBallX = ballX + deltaT * ballSpeedX;
        }
        ballX = nextBallX;
        ballY = nextBallY;
    }

    public boolean touchedBall(){
        return false;

    }




}