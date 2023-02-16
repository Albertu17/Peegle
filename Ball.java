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
    public double p1,p2;

    private boolean ispresent = true;
    private boolean atoucher = false;

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

    public void setPresent(boolean b){
        ispresent = b;
    }

    public boolean isPresent(){
        return ispresent;
    }

    public void updateBall(double deltaT) {
        // first, compute possible next position if nothing stands in the way
        nextBallX = ballX + deltaT * ballSpeedX;
        nextBallY = ballY + deltaT * ballSpeedY + 1/2*g*(deltaT*deltaT);

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
        

        Rectangle r = touchedWall(deltaT);
        if (r!=null && !atoucher){
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
            double vx = ballSpeedX+p1;
            double vy = ballSpeedY+p2;

            double s = ux*vx+ uy*vy;
            double d1 = Math.sqrt(ux* ux + uy*uy);
            double d2 = Math.sqrt(vx* vx + vy*vy);
            double alpha =Math.acos(s/(d1*d2));
            System.out.println("angle d'incidence : " + alpha*180/Math.PI);
            System.out.println("touche");
            atoucher = true;
            double beta = Math.PI - alpha;
            double angle = -beta;
            ballSpeedX=d2*Math.cos(angle);
            ballSpeedY=d2*Math.sin(angle);

        } else {}
        ballX = nextBallX;
        ballY = nextBallY;
    }


    public boolean touchedWallX(double nextBallX){
        return nextBallX < 0 || nextBallX> court.getWidth() - ballRadius;
    }

    public boolean touchedWallY(double nextBallY){
        return nextBallY < 0 || nextBallY> court.getHeight() - ballRadius;
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

    public Rectangle touchedWall(double deltaT){
        Rectangle r=null;
        // double nextBallX2= nextBallX + deltaT * nextBallSpeedX;
        // double nextBallY2 = nextBallY + deltaT * nextBallSpeedY + 1/2*g*(deltaT*deltaT);
        // double nextBallSpeedY2= nextBallSpeedY + g*deltaT;
        // double nextBallSpeedX2=  nextBallSpeedX;


        // double nextBallX3 = nextBallX2 + deltaT * nextBallSpeedX2;
        // double nextBallY3 = nextBallY2 + deltaT * nextBallSpeedY2 + 1/2*g*(deltaT*deltaT);
        // double nextBallSpeedY3= nextBallSpeedY2 + g*deltaT;
        // double nextBallSpeedX3=  nextBallSpeedX2;

        for (Rectangle rect:court.getRectangle()){     
            if (Math.min(rect.x0,rect.x1) <= nextBallX && nextBallX <= Math.max(rect.x0,rect.x1) &&
            Math.min(rect.y0,rect.y1) <= nextBallY && nextBallY <= Math.max(rect.y0,rect.y1)) { // Je regarde si ma balle est dans la surface de mon trait/rectangle 
                    // je vais chercher à résoudre l'équation d'un point qui appartient à ma droite de la forme y=ax+b
                    // pour cela : 
                    double ux = rect.x0 - rect.x1; // calcul du vecteur en x de mon trait
                    double uy = rect.y0 - rect.y1; // calcul du vecteur en y de mon trait
                    double vx = ballSpeedX; // calcul du vecteur en x de ma vitesse
                    double vy = ballSpeedY; // calcul du vecteur en y de ma vitesse

                    double coeffDirecteurDroite = uy/ux;
                    double coeffDirecteurVecteur = vy/vx;

                    double b1 = rect.y0 - coeffDirecteurDroite*rect.x0; // je prends un point qui appartient a ma droite de mon trait pour trouver la constante b dans y=ax+b <=> b = y - ax;
                    

                    double tmpx = nextBallX + ballRadius;
                    double tmpy = nextBallY + ballRadius;
                    double tmpx2,tmpy2;
                    double d1x,d2x;
                    double d1y,d2y;
                    double x,y;
                    double b2; // je prends un point qui appartient a ma droite de mon vecteur vitesse pour trouver la constante b dans y=ax+b <=> b = y - ax;
                    for (int i=0;i<8;i++){
                        if (coeffDirecteurDroite!=coeffDirecteurVecteur){ 
                            b2=tmpy- coeffDirecteurVecteur*tmpx;;
                            x = (b2-b1)/(coeffDirecteurDroite-coeffDirecteurVecteur); // systeme équation intersection pour x : y = a'x + b' et y = ax + b <=> x = (b-b') / (a'x-ax)
                            y = (-b2*coeffDirecteurDroite+b1*coeffDirecteurVecteur)/(coeffDirecteurVecteur-coeffDirecteurDroite);  // systeme équation intersection pour y : y = a'x + b' et y = ax + b <=> y = (-b*a'x+b'*a) / (a'x-ax)
                            this.x = x; // sert juste pour l'affichahe ie le sauvegader dans une instance de la classe balle 
                            this.y = y;
                            tmpx2 = nextBallX + ballRadius + ballRadius*Math.cos(2*Math.PI + i*Math.PI/4);
                            tmpy2 = nextBallY + ballRadius + ballRadius*Math.sin(2*Math.PI + i*Math.PI/4);
                            d1x = Math.abs(x-tmpx);
                            d2x = Math.abs(x-tmpx2);
                            d1y = Math.abs(y-tmpy);
                            d2y = Math.abs(y-tmpy2);
                            if (d1x>=d2x) tmpx=tmpx2;
                            if (d1y>=d2y) tmpy=tmpy2;                            
                        }

                        
                    }
                    p1 = tmpx; // point en haut à gauche de la balle (point rose)
                    p2 = tmpy;
                    b2=tmpy- coeffDirecteurVecteur*tmpx;
                    // p1 = nextBallX+ballRadius + normeCoeffDirecteurVecteur*Math.cos(ballSpeedY/ballSpeedX);
                    // p2 = nextBallY+ballRadius + normeCoeffDirecteurVecteur*Math.sin(ballSpeedY/ballSpeedX);
    
                    if (coeffDirecteurDroite!=coeffDirecteurVecteur){ // je teste si les droites s'intersectent ie les vecteurs ne sont pas colinéaires.
                        x = (b2-b1)/(coeffDirecteurDroite-coeffDirecteurVecteur); // systeme équation intersection pour x : y = a'x + b' et y = ax + b <=> x = (b-b') / (a'x-ax)
                        y = (-b2*coeffDirecteurDroite+b1*coeffDirecteurVecteur)/(coeffDirecteurVecteur-coeffDirecteurDroite);  // systeme équation intersection pour y : y = a'x + b' et y = ax + b <=> y = (-b*a'x+b'*a) / (a'x-ax)
                        this.x = x; // sert juste pour l'affichahe ie le sauvegader dans une instance de la classe balle 
                        this.y = y;
                            if (Math.abs(x-nextBallX) <= 3*ballRadius  &&  Math.abs(y-nextBallY) <= 3*ballRadius){ // je test si mon potentiel point d'intersection est suffisament proche de ma balle 
                                // dijonction des cas :
                                // Il est possible que la balle aille trop vite, c'est à dire les les coordonnées peuvent passer par exemple en x de 300 à 305 
                                // et donc "passer à travers de notre point d'interseciton"
                                // Je dois faire donc une dijonction de cas par rapport la vitesse pour savoir si ma balle est passée à travers mon point grace aux inégalité
                                // Par exemple 1er cas ma balle descend et va à droite 
                                // Je regarde donc si les coordonées de ma balle en x sont supérieurs à mon point en x (est passée à droite)
                                // et si ma balle en y est passée en dessous de mon point d'interseciton c a d nextBallY +ballRadius*2 > y
                                if (ballSpeedY>=0){
                                    if (ballSpeedX>=0){

                                        if (p1+10 >= x && p2+10 >= y) {
                                            
                                            // nextBallX = nextBallX - Math.abs(x-nextBallX);
                                            // nextBallY = nextBallY - Math.abs(y-nextBallY);
                                            return rect;
                                        }
                                    }
                                    else {
                                        if (p1-10 <= x && p2+10 >= y) {
                                            // nextBallX = nextBallX + Math.abs(x-nextBallX);
                                            // nextBallY = nextBallY - Math.abs(y-nextBallY);
                                            return rect;
                                        }
                                    }
                                }
                                else {
                                    if (ballSpeedX>=0){
                                        if (p1+10 >= x && p2-10 <= y) {
                                            // nextBallX = nextBallX - ballRadius*2;
                                            // nextBallY = nextBallY - ballRadius*2;
                                            return rect;
                                        }
                                    }
                                    else {
                                        if (p1-10 <= x && p2-10 <= y) {
                                            // nextBallX = nextBallX + Math.abs(x-nextBallX);
                                            // nextBallY = nextBallY + Math.abs(y-nextBallY);
                                            return rect;
                                        }
                                    }
                                }
                                
    
                            }
                            else atoucher=false;
                            

                    }
                }
                else ispresent=false;
            } 

        return r;
    }


}