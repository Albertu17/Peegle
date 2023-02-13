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

    private double g=300; // m/s
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
            double vx = ballSpeedX;
            double vy = ballSpeedY;

            double s = ux*vx+ uy*vy;
            double d1 = Math.sqrt(ux* ux + uy*uy);
            double d2 = Math.sqrt(vx* vx + vy*vy);
            double alpha =Math.acos(s/(d1*d2));
            System.out.println("angle d'incidence : " + alpha*180/Math.PI);
            System.out.println("touche");
            System.out.println("vitesse en x : " + ballSpeedX);
            System.out.println("vitesse en y : " + ballSpeedY);
            atoucher = true;
            double beta = Math.PI - alpha;
            double angle = beta - r.angle;
            if (beta < 0){
                beta = -beta;
            }
            System.out.println("angle de rebond : " + angle*180/Math.PI);
            // calcul de la nouvelle vitesse
            ballSpeedX=d2*Math.cos(angle);
            ballSpeedY=d2*Math.sin(angle);
            System.out.println("vitesse en x : " + ballSpeedX);
            System.out.println("vitesse en y : " + ballSpeedY);
            System.out.println("vitesse totale : " + Math.sqrt(ballSpeedX*ballSpeedX + ballSpeedY*ballSpeedY));

        } else {}
        ballX = nextBallX;
        ballY = nextBallY;
    }


    public boolean touchedWallX(double nextBallX){
        return nextBallX < 0 || nextBallX> court.getWidth() - ballRadius;
    }

    public boolean touchedWallY(double nextBallY){
        return nextBallY < 0 || nextBallY > court.getHeight() - ballRadius*2 - 15;
    }
    



    public Rectangle touchedWall(double deltaT){
        Rectangle r=null;
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