import java.util.Random;
import java.math.*;
public class Ball{

    public final double ballRadius = 25.0; // m

    public double ballX, ballY; // m
    public double ballSpeedX, ballSpeedY; // m

    private int height=500;
    private int width=500;

    private int yMarxgin = 3;

    private double masse = 0.33;

    private double g=200; // m/s

    /* Important coordonée de la balle centre en X mais tout en haut pour Y */

    Ball(){
        ballX=200;
        ballY=0;
        ballSpeedX=50;
        ballSpeedY=1;
    }

    public void updateBall(double deltaT) {
        // first, compute possible next position if nothing stands in the way
        double nextBallX = ballX + deltaT * ballSpeedX;
        double nextBallY = ballY + deltaT * ballSpeedY + 1/2*g*(deltaT*deltaT);

        //ballSpeedX = ballSpeedX + g*deltaT;
        ballSpeedY = ballSpeedY + g*deltaT;
        // next, see if the ball would meet some obstacle
        if (nextBallY < 0 || nextBallY > height- ballRadius*2 - yMarxgin) { 
            ballSpeedY = -ballSpeedY*0.8;
            nextBallY = ballY + deltaT * ballSpeedY + 1/2*g*(deltaT*deltaT);
        }

        if (nextBallX < 0 || nextBallX> width - ballRadius){
            ballSpeedX = -ballSpeedX;
            nextBallX = ballX + deltaT * ballSpeedX;
        }
        ballX = nextBallX;
        ballY = nextBallY;
    }




}