import java.util.ArrayList;
import java.util.Random;
import java.math.*;
import java.sql.Array;
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

    Ball(int x,int y,int vx0,int vy0,GameView g){
        ballX=x;
        ballY=y;
        ballSpeedX=vx0;
        ballSpeedY=vy0;
        court=g;
    }

    public void updateBall(double deltaT) {
        // first, compute possible next position if nothing stands in the way
        double nextBallX = ballX + deltaT * ballSpeedX;
        double nextBallY = ballY + deltaT * ballSpeedY + 1/2*g*(deltaT*deltaT);
        Rectangle r = touchedWall(nextBallX,nextBallY,deltaT);
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
            double ux = Math.abs(r.x0 - r.x1);
            double uy = Math.abs(r.y0 - r.y1);


            // double s = ux*ballSpeedX+ uy*ballSpeedY;
            // double d1 = Math.sqrt(ux* ux + uy*uy);
            // double d2 = Math.sqrt(ballSpeedX* ballSpeedX + ballSpeedY*ballSpeedY);
            // double alpha = Math.acos(s/(d1*d2));
            // System.out.println(alpha*180/Math.PI);
            // double beta;

            // beta = Math.PI-alpha;
            // System.out.println(beta*180/Math.PI);

            double teta = Math.atan(ballSpeedY/ballSpeedX);
            double beta;
            
            beta=r.angle;
            double angle = Math.PI+teta-2*beta;
            System.out.println(angle*180/Math.PI);

            double nx = ballSpeedX;
            double ny = ballSpeedY;
            ballSpeedX=Math.sqrt(ballSpeedX*ballSpeedX + ballSpeedY*ballSpeedY)*Math.cos(angle);
            ballSpeedY=Math.sqrt(ballSpeedX*ballSpeedX + ballSpeedY*ballSpeedY)*Math.sin(angle);
  

            // ballSpeedX = nx*Math.sin(alpha) + ny*Math.cos(alpha);
            // ballSpeedY = nx*Math.cos(alpha) - ny*Math.sin(alpha);

            // ballSpeedX = -100;
            // ballSpeedY = -ballSpeedY;
            // ispresent=true;
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
    


    // public Rectangle touchedWall(double nextBallX, double nextBallY){
    //     Rectangle r=null;
    //     System.out.println("no");
    //     for (Rectangle rect:court.getRectangle()){
    //         if ((int) rect.x0 <= (int)  ballX &&  (int) ballX <= (int)  rect.x1
    //         &&  (int)  rect.y0 <= (int)  ballY && (int)  ballY <= (int)  rect.y1) {


               
    //         double x = rect.x0 + nextBallX-rect.x0;
    //         double adjacent = rect.x1 - x;
    //         double tan = Math.tan(rect.angle);
    //         double y = rect.y1 - tan*adjacent;
    //         // System.out.println((nextBallX+ballSpeedX) + " "+ (nextBallY+ballSpeedY));
            
    //         if ((ballY+ballSpeedY) >= y && (ballX+ballSpeedX) <= x) return rect;
    //             // System.out.println(x + " " + y);
    //             // System.out.println(ballX + " " + ballY);
    //         // if (x<= 2*nextBallX-ballSpeedX && y <= 2*nextBallY+ballSpeedY) {
    //         //         System.out.println("touch");
    //         //         //return rect;
    //         //     }
    //         }

    //         // double vRectX = r.x0 - r.x1;
    //         // double vRectY = r.y0 - r.y1;
    //         // double vVitX = nextBallX-ballSpeedX;
    //         // double vVitY = nextBallY-ballSpeedY;
    //         // double e1 = vVitX - r.x0 / vRectX;
    //         // double e2 = vVitY - r.y0 / vRectY;
    //         // if (e1==e2) 

            
    //         }
    //     return r;

    // }

    public Rectangle touchedWall(double nextBallX, double nextBallY,double deltaT){
        Rectangle r=null;
        for (Rectangle rect:court.getRectangle()){     
            if (Math.min(rect.x0,rect.x1) <= nextBallX && nextBallX <= Math.max(rect.x0,rect.x1) &&
            Math.min(rect.y0,rect.y1)<= nextBallY && nextBallY <= Math.max(rect.y0,rect.y1)) {
                if (!ispresent){
                    double x = rect.x0 + nextBallX-rect.x0;
                    double adjacent = rect.x1 - x;
                    double tan = Math.tan(rect.angle);
                    double y = rect.y1 - tan*adjacent;
                    double nextBallX2=nextBallX;
                    double nextBallY2=nextBallY;
                    //System.out.println(x + " " + y);



                    for (int i=0;i<1;i++){
                        //System.out.println(i + " " +  nextBallX2 + " " + nextBallY2);
                        if (ballSpeedY>=0){
                            if (ballSpeedX>=0){
                                if (nextBallX2+ballRadius>=x && nextBallY2+ballRadius>=y) {
                                    return rect;}
                            }
                            else {
                                if (nextBallX2<=x && nextBallY2+ballRadius>=y) {

                                    return rect;}
                            }
                        }
                        else {
                            if (ballSpeedX>=0){
                                if (nextBallX2>=x && nextBallY2>=y) {
                                    return rect;}
                            }
                            else {
                                if (nextBallX2<=x && nextBallY2>=y) {
                                    return rect;}
                            }
                        }
                        
                        nextBallX2 = nextBallX2 + deltaT * ballSpeedX;
                        nextBallY2 = nextBallY2 + deltaT * ballSpeedY + 1/2*g*(deltaT*deltaT);
                }

            }
            else ispresent=false; 
              
        }   
            
        }
        return r;

    }




}