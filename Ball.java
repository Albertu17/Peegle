import java.util.ArrayList;
import java.util.Random;
import java.math.*;
import java.sql.Array;
import java.awt.*;
public class Ball{

    public final double ballRadius = 12.5; // m

    public double ballX, ballY; // m
    public double ballSpeedX, ballSpeedY; // m

    public double nextBallX,nextBallY;

    // private int height=500;
    // private int width=500;

    private int yMarxgin = 3;

    private boolean ispresent = true;

    private double g=100; // m/s
    private double coeffRebond = 0.8;

    private GameView court;



    /* Important coordonée de la balle centre en X mais tout en haut pour Y */

    public GameView getCourt() {
        return court;
    }

    Ball(int x,int y,int vx0,int vy0,GameView g){
        ballX=x;
        ballY=y;
        ballSpeedX=vx0;
        ballSpeedY=vy0;
        court=g;
    }
    public double getG() {
        return g;
    }

    public void updateBall(double deltaT) {
        // first, compute possible next position if nothing stands in the way
        double nextBallX = ballX + deltaT * ballSpeedX;
        double nextBallY = ballY + deltaT * ballSpeedY + 1/2*g*(deltaT*deltaT);
        Rectangle r = touchedWall(nextBallX,nextBallY);
        //ballSpeedX = ballSpeedX + g*deltaT;
        ballSpeedY = ballSpeedY + g*deltaT;
        // next, see if the ball would meet some obstacle
        if (touchedWallY(nextBallY)) { 
            ballSpeedY = -ballSpeedY*coeffRebond;
            nextBallY = ballY + deltaT * ballSpeedY + 1/2*g*(deltaT*deltaT);
        }

        if (touchedWallX(nextBallX)){
            ballSpeedX = -ballSpeedX;
            nextBallX = ballX + deltaT * ballSpeedX;
        }
        if (r!=null){


            // double alpha = r.angle;
            // double beta = Math.PI - alpha;
            // double angle = Math.PI/2 
            // double tan = Math.tan(r.angle);
            // System.out.println(tan);
            // ballSpeedX = (ballSpeedY+ballSpeedX)*tan*Math.cos(r.angle);
            // ballSpeedY = -ballSpeedY*tan*Math.sin(r.angle);
            // ballSpeedX = 100;
            // ballSpeedY = -ballSpeedY;
            //double tanBeta = ballSpeedY/ballSpeedX; // en dégré
            //double angleRebond = 90 - tanBeta; // En dégré
            // double angle = -tanBeta*Math.PI/180;
            // System.out.println(angle);
            // ballSpeedX = (ballSpeedY+ballSpeedX)*Math.sin(angle);
            // ballSpeedY = (ballSpeedY+ballSpeedX)*Math.cos(angle);
            //System.out.println(tan);

            // double vx = Math.abs(ballX - ballSpeedX);
            // double vy = Math.abs(ballY - ballSpeedY);

            //double ux = 
            double ux = r.x0 - r.x1;
            double uy = r.y0 - r.y1;


            double s = ux*ballSpeedX+ uy*ballSpeedY;
            double d1 = Math.sqrt(ux* ux + uy*uy);
            double d2 = Math.sqrt(ballSpeedX* ballSpeedX + ballSpeedY*ballSpeedY);
            double alpha = Math.acos(s/(d1*d2));
            // System.out.println(alpha*180/Math.PI);
            double beta;
            if (alpha>90){
                    alpha = 180-alpha;
            }
            beta = 2*alpha;
            // System.out.println(beta*180/Math.PI);

            double nx = ballSpeedX;
            double ny = ballSpeedY;
            ballSpeedX = nx*Math.cos(beta) + ny*Math.sin(beta);
            ballSpeedY = -nx*Math.sin(beta) + ny*Math.cos(beta);
        }
        ballX = nextBallX;
        ballY = nextBallY;
       
    }


    public boolean touchedWallX(double nextBallX){
        return nextBallX < 0 || nextBallX> court.getWidth() - ballRadius;
    }

    public boolean touchedWallY(double nextBallY){
        return nextBallY < 0 || nextBallY > court.getHeight() - ballRadius*2 - 15;
    }
    


    public Rectangle touchedWall(double nextBallX, double nextBallY){
        Rectangle r=null;
        for (Rectangle rect:court.getRectangle()){
            if ((int) rect.x0 <= (int)  ballX &&  (int) ballX <= (int)  rect.x1
            &&  (int)  rect.y0 <= (int)  ballY && (int)  ballY <= (int)  rect.y1) {
               
                double x = rect.x0 + nextBallX-rect.x0;
                double adjacent = rect.x1 - x;
                double tan = Math.tan(rect.angle);
                double y = rect.y1 - tan*adjacent;
                // System.out.println(x + " " + y);
                // System.out.println(ballX + " " + ballY);
                if ((int) x<= (int) nextBallX && (int) y <= (int) nextBallY) {
                    // System.out.println("touch");
                    return rect;}
            }

            
            }
        return r;
    

    }




}