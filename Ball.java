import java.util.ArrayList;
import java.util.Random;
import java.math.*;
import java.sql.Array;
public class Ball{

    public final double ballRadius = 12.5/2; // m

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

    double x,y;

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
        double nextBallSpeedY = ballSpeedY + g*deltaT;
        double nextBallSpeedX = ballSpeedX;
        
        // next, see if the ball would meet some obstacle
        if (touchedWallY(nextBallY)) { 
            nextBallSpeedY = -ballSpeedY*coeffRebond;
            nextBallY = ballY + deltaT * ballSpeedY + 1/2*g*(deltaT*deltaT);
        }

        if (touchedWallX(nextBallX)){
            nextBallSpeedX = -ballSpeedX;
            nextBallX = ballX + deltaT * ballSpeedX;
        }
        Rectangle r = touchedWall(nextBallX,nextBallY,nextBallSpeedX,nextBallSpeedY,deltaT);
        if (r!=null){
            // double ux = Math.abs(r.x0 - r.x1);
            // double uy = Math.abs(r.y0 - r.y1);


            // double s = ux*ballSpeedX+ uy*ballSpeedY;
            // double d1 = Math.sqrt(ux* ux + uy*uy);
            // double d2 = Math.sqrt(ballSpeedX* ballSpeedX + ballSpeedY*ballSpeedY);
            // double alpha = Math.acos(s/(d1*d2));
            // System.out.println(alpha*180/Math.PI);
            // double beta;

            // beta = Math.PI-alpha;
            // System.out.println(beta*180/Math.PI);

            // double teta = Math.atan(ballSpeedY/ballSpeedX);
            // double beta;
            
            // beta=r.angle;
            // double angle = Math.PI+teta-2*beta;
            // System.out.println(angle*180/Math.PI);

            // double nx = ballSpeedX;
            // double ny = ballSpeedY;
            // ballSpeedX=Math.sqrt(ballSpeedX*ballSpeedX + ballSpeedY*ballSpeedY)*Math.cos(angle);
            // ballSpeedY=Math.sqrt(ballSpeedX*ballSpeedX + ballSpeedY*ballSpeedY)*Math.sin(angle);
  

            // ballSpeedX = nx*Math.sin(alpha) + ny*Math.cos(alpha);
            // ballSpeedY = nx*Math.cos(alpha) - ny*Math.sin(alpha);

            // ballSpeedX = -100;
            // ballSpeedY = -ballSpeedY;
            // ispresent=true;


            double ux = r.x0 - r.x1;
            double uy = r.y0 - r.y1;

            double s = ux*nextBallSpeedX+ uy*nextBallSpeedY;
            double d1 = Math.sqrt(ux* ux + uy*uy);
            double d2 = Math.sqrt(nextBallSpeedX* nextBallSpeedX + nextBallSpeedY*nextBallSpeedY);
            double alpha =Math.acos(s/(d1*d2));
            //System.out.println(alpha*180/Math.PI);
            System.out.println("otuche");
            double beta = Math.PI - alpha;
            double angle = -beta;
            nextBallSpeedX=d2*Math.cos(angle);
            nextBallSpeedY=d2*Math.sin(angle);
            ispresent=true;

        }
        ballX = nextBallX;
        ballY = nextBallY;
        ballSpeedX=nextBallSpeedX;
        ballSpeedY=nextBallSpeedY;
    }


    public boolean touchedWallX(double nextBallX){
        return nextBallX < 0 || nextBallX> court.getWidth() - ballRadius;
    }

    public boolean touchedWallY(double nextBallY){
        return nextBallY < 0 || nextBallY > court.getHeight() - ballRadius*2 - 15;
    }
    


    // // public Rectangle touchedWall(double nextBallX, double nextBallY){
    // //     Rectangle r=null;
    // //     System.out.println("no");
    // //     for (Rectangle rect:court.getRectangle()){
    // //         if ((int) rect.x0 <= (int)  ballX &&  (int) ballX <= (int)  rect.x1
    // //         &&  (int)  rect.y0 <= (int)  ballY && (int)  ballY <= (int)  rect.y1) {


               
    // //         double x = rect.x0 + nextBallX-rect.x0;
    // //         double adjacent = rect.x1 - x;
    // //         double tan = Math.tan(rect.angle);
    // //         double y = rect.y1 - tan*adjacent;
    // //         // System.out.println((nextBallX+ballSpeedX) + " "+ (nextBallY+ballSpeedY));
            
    // //         if ((ballY+ballSpeedY) >= y && (ballX+ballSpeedX) <= x) return rect;
    // //             // System.out.println(x + " " + y);
    // //             // System.out.println(ballX + " " + ballY);
    // //         // if (x<= 2*nextBallX-ballSpeedX && y <= 2*nextBallY+ballSpeedY) {
    // //         //         System.out.println("touch");
    // //         //         //return rect;
    // //         //     }
    // //         }

    // //         // double vRectX = r.x0 - r.x1;
    // //         // double vRectY = r.y0 - r.y1;
    // //         // double vVitX = nextBallX-ballSpeedX;
    // //         // double vVitY = nextBallY-ballSpeedY;
    // //         // double e1 = vVitX - r.x0 / vRectX;
    // //         // double e2 = vVitY - r.y0 / vRectY;
    // //         // if (e1==e2) 

            
    // //         }
    // //     return r;

    // // }

    // public Rectangle touchedWall(double nextBallX, double nextBallY,double deltaT){
    //     Rectangle r=null;
    //     //System.out.println("-------------------------------------------");
        
    //     for (Rectangle rect:court.getRectangle()){   
    //         System.out.println("'-------------------------'");
    //         if (Math.min(rect.x0,rect.x1) <= nextBallX && nextBallX <= Math.max(rect.x0,rect.x1) &&
    //         Math.min(rect.y0,rect.y1)<= nextBallY && nextBallY <= Math.max(rect.y0,rect.y1)) {
    //                 double nextBallX2=nextBallX;
    //                 double nextBallY2=nextBallY;
    //                 double x = Math.min(rect.x0,rect.x1) + nextBallX2-Math.min(rect.x0,rect.x1);
    //                 double adjacent = x- Math.min(rect.x0,rect.x1);
    //                 System.out.println(adjacent);
    //                 double tan = Math.tan(-rect.angle);
    //                 System.out.println(tan*adjacent);
    //                 double y = Math.min(rect.y1,rect.y0) + tan*adjacent;
    //                 System.out.println(x + " " + y);
                    
    //                 for (int i=0;i<1;i++){
    //                         System.out.println(i + " " +  nextBallX2 + " " + nextBallY2);
    //                     if (ballSpeedY>=0){
                            
    //                         if (ballSpeedX>=0){
    //                             if (nextBallX2+ballRadius>=x && nextBallY2+ballRadius>=y) {
                                    
    //                                 return rect;}
    //                         }
    //                         else {
    //                             if (nextBallX2<=x && nextBallY2+ballRadius>=y) {
                                    
    //                                 return rect;}
    //                         }
    //                     }
    //                     // else {
                        
    //                     //     if (ballSpeedX>=0){
    //                     //         if (nextBallX2>=x && nextBallY2<=y) {
                                    
    //                     //             return rect;}
    //                     //     }
    //                     //     else {
    //                     //         if (nextBallX2<=x && nextBallY2>=y) {
    //                     //             return rect;}
    //                     //     }
    //                     // }
                        
    //                     nextBallX2 = nextBallX2 + deltaT * ballSpeedX;
    //                     nextBallY2 = nextBallY2 + deltaT * ballSpeedY + 1/2*g*(deltaT*deltaT);
    //             }

    //         }              
    //     } 
        
    //     return r;
    // }

    // public Rectangle touchedWall(double nextBallX, double nextBallY,double nextBallSpeedX, double nextBallSpeedY,double deltaT){
    //     Rectangle r=null;
    //     int j=0;
    //     System.out.println("-------------------------------------------");
    //     double nextBallX2=nextBallX;
    //     double nextBallY2=nextBallY;
    //     for (Rectangle rect:court.getRectangle()){     
    //         if (Math.min(rect.x0,rect.x1) <= nextBallX && nextBallX <= Math.max(rect.x0,rect.x1) &&
    //         Math.min(rect.y0,rect.y1) <= nextBallY && nextBallY <= Math.max(rect.y0,rect.y1)) {
    //             System.out.println(rect.toString());
    //             for (int i=0;i<1;i++){
    //                 double ux = rect.x0 - rect.x1;
    //                 double uy = rect.y0 - rect.y1;
    //                 double vx = nextBallSpeedX;
    //                 double vy = nextBallSpeedY;
    

    
    
    //                 double coeffDirecteurDroite = uy/ux;
    //                 double b1 = rect.y0 - coeffDirecteurDroite*rect.x0;
                    
    
    //                 double coeffDirecteurVecteur = vy/vx;
    //                 double b2 = (nextBallY2 + ballRadius)- coeffDirecteurVecteur*(nextBallX2 + ballRadius) ;
    
    //                 if (coeffDirecteurDroite!=coeffDirecteurVecteur){
    //                     double x,y;
    //                     x = (b2-b1)/(coeffDirecteurDroite-coeffDirecteurVecteur);
    //                     y = (-b2*coeffDirecteurDroite+b1*coeffDirecteurVecteur)/(coeffDirecteurVecteur-coeffDirecteurDroite);
    //                     this.x = (b2-b1)/(coeffDirecteurDroite-coeffDirecteurVecteur);
    //                     this.y = (-b2*coeffDirecteurDroite+b1*coeffDirecteurVecteur)/(coeffDirecteurVecteur-coeffDirecteurDroite);
    //                     //System.out.println(j);
    //                     if (nextBallSpeedX>=0 && x<=nextBallX2) return null;
    //                     if (nextBallSpeedX<=0 && x>=nextBallX2) return null;
    //                     if (nextBallSpeedY>=0 && y<=nextBallY2) return null;
    //                     if (nextBallSpeedY<=0 && y>=nextBallY2) return null;
    //                     if (Math.min(rect.x0,rect.x1) <= x && x <= Math.max(rect.x0,rect.x1)
    //                     && Math.min(rect.y0,rect.y1)<= y && y <= Math.max(rect.y0,rect.y1)){
    //                         System.out.println(x+" "+y);
    //                         System.out.println(nextBallX2+ballRadius+" "+nextBallY2+ballRadius);
    //                         if (((int) nextBallX2+ballRadius)==((int)x) && ((int)nextBallY2+ballRadius)==((int)y)) return rect;
                            
    //                             //System.out.println(nextBallX2 + " "+ nextBallY);
    //                             if (nextBallSpeedY>=0){
    //                                 System.out.println(1);             
    //                                 if (nextBallSpeedX>=0){
    //                                     System.out.println(1);
    //                                     System.out.println(((int) nextBallX2+ballRadius*2) + ">=" +  ((int) x) + " " + ((int) nextBallY2+ballRadius*2) + ">=" + ((int) y));
    //                                     if (((int) nextBallX2+ballRadius*2) >= ((int) x) && ((int) nextBallY2+ballRadius*2)>=((int) y)) {
    //                                         System.out.println(1);
    //                                         return rect;
    //                                     }
    //                                 }
    //                                 else {
    //                                     System.out.println(((int) nextBallX2+ballRadius) + "<=" +  ((int) x) + " " + ((int) nextBallY2+ballRadius*2) + ">=" + ((int) y));
    //                                     if (((int) nextBallX2) <= ((int) x) && ((int) nextBallY2+ballRadius*2)>=((int) y)) {
    //                                         return rect;
    //                                     }
    //                                     }
    //                             }
    //                             else {
    //                                 System.out.println(2);
    //                                 if (nextBallSpeedX>=0){
    //                                     System.out.println(nextBallX2+ballRadius*2 +  ">=" + x + " " + nextBallY2 + "<=" + y );
    //                                     if (((int) nextBallX2+ballRadius*2) >= ((int) x) && ((int) nextBallY2)<=((int) y)) {
    //                                         return rect;
    //                                     }
    //                                     }
    //                                     else {
    //                                         System.out.println(2);
    //                                         System.out.println(((int) nextBallX2) + "<=" +  ((int) x) + " " + ((int) nextBallY2) + ">=" + ((int) y));
    //                                         if (((int) nextBallX2) <= ((int) x) && ((int) nextBallY2)>=((int) y)) {
    //                                             return rect;
    //                                         }
    //                                         }
    //                                     }
                                                    

    //                         }
    //                     }

    //             }
    //         }
                    
    //             }
    //         j++;  

    //     return r;
    // }

    public Rectangle touchedWall(double nextBallX, double nextBallY,double nextBallSpeedX, double nextBallSpeedY,double deltaT){
        Rectangle r=null;
        double nextBallX2= nextBallX + deltaT * nextBallSpeedX;
        double nextBallY2 = nextBallY + deltaT * nextBallSpeedY + 1/2*g*(deltaT*deltaT);
        double nextBallSpeedY2= nextBallSpeedY + g*deltaT;
        double nextBallSpeedX2=  nextBallSpeedX;


        double nextBallX3 = nextBallX2 + deltaT * nextBallSpeedX2;
        double nextBallY3 = nextBallY2 + deltaT * nextBallSpeedY2 + 1/2*g*(deltaT*deltaT);
        double nextBallSpeedY3= nextBallSpeedY2 + g*deltaT;
        double nextBallSpeedX3=  nextBallSpeedX2;

        for (Rectangle rect:court.getRectangle()){     
            if (Math.min(rect.x0,rect.x1) <= nextBallX && nextBallX <= Math.max(rect.x0,rect.x1) &&
            Math.min(rect.y0,rect.y1) <= nextBallY && nextBallY <= Math.max(rect.y0,rect.y1)) {
                    double ux = rect.x0 - rect.x1;
                    double uy = rect.y0 - rect.y1;
                    double vx = nextBallSpeedX;
                    double vy = nextBallSpeedY;

                    double coeffDirecteurDroite = uy/ux;
                    double b1 = rect.y0 - coeffDirecteurDroite*rect.x0;
                    double coeffDirecteurVecteur = vy/vx;

                    double p1 = nextBallX2+ballRadius + coeffDirecteurVecteur/ballRadius*Math.cos(coeffDirecteurVecteur);
                    double p2 = nextBallY2+ballRadius + coeffDirecteurVecteur/ballRadius*-Math.sin(coeffDirecteurVecteur);

                    double b2 = p2- coeffDirecteurVecteur*p1;
    
                    if (coeffDirecteurDroite!=coeffDirecteurVecteur){
                        double x,y;
                        x = (b2-b1)/(coeffDirecteurDroite-coeffDirecteurVecteur);
                        y = (-b2*coeffDirecteurDroite+b1*coeffDirecteurVecteur)/(coeffDirecteurVecteur-coeffDirecteurDroite);
                        this.x = x;
                        this.y = y;

                        if (nextBallSpeedX>=0 && x<=nextBallX2) return null;
                        if (nextBallSpeedX<=0 && x>=nextBallX2) return null;
                        if (nextBallSpeedY>=0 && y<=nextBallY2) return null;
                        if (nextBallSpeedY<=0 && y>=nextBallY2) return null;
                        if (Math.min(rect.x0,rect.x1) <= x && x <= Math.max(rect.x0,rect.x1)
                        && Math.min(rect.y0,rect.y1)<= y && y <= Math.max(rect.y0,rect.y1)){

                            // System.out.println("1        " + (int) nextBallX+"      "+ (int)nextBallY);
                            // System.out.println("------------------------------");
                            // System.out.println("2        " +(int) nextBallX2+"      "+ (int)nextBallY2);
                            // System.out.println("------------------------------");
                            System.out.println("3        " + Math.ceil(p1)+"      "+  Math.ceil(p2));
                            System.out.println("------------------------------");
                            System.out.println("point :   " +  Math.ceil(x)+"         "+  Math.ceil(y));
                            if (Math.ceil(p1)==Math.ceil(x) && Math.ceil(p2)==Math.ceil(y)) return rect;
                            // if ((int) nextBallSpeedX2<= (int)x && (int)x<= (int) nextBallX3 
                            // && (int) nextBallSpeedY2<=(int) y && (int) y<=(int) nextBallY3 
                            // ) return rect;
                            

                        }
                    }
                }
            } 

        return r;
    }


}

