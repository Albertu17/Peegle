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
        

        Rectangle r = touchedWall(deltaT);
        if (r!=null && !atoucher){


            double ux = r.x0 - r.x1;
            double uy = r.y0 - r.y1;
            double vx = ballSpeedX;
            double vy = ballSpeedY;
            // calcul de l'angle du rebond
            double angle = Math.atan2(uy,ux);
            // calcul de la norme du vecteur vitesse
            double norme = Math.sqrt(vx*vx + vy*vy);
            // calcul de l'angle de retour
            double angleRetour = Math.atan2(vy,vx);
            // calcul de l'angle de rebond
            double angleRebond = angleRetour - 2*(angleRetour - angle);
            // calcul de la nouvelle vitesse
            ballSpeedX = norme*Math.cos(angleRebond);
            ballSpeedY = norme*Math.sin(angleRebond);
            atoucher=true;
        } else if (r==null){
            atoucher=false;
        } else {}
        Pegs p = touchedPegs();

        
        if (p!=null && !atoucherpegs){
            if (!p.getHit()){combo++;}
            p.setTouche(true);
            double ux = (nextBallX+ ballRadius) - (p.getX() + p.getRadius()/2);
            double uy = (nextBallY+ballRadius) - (p.getY()+ p.getRadius()/2);
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
        Pegs p=null;
        for (Pegs peg: court.getPegs()){
            
            if(peg.contains(nextBallX + ballRadius, nextBallY + ballRadius )){
                p=peg;
            }
        }
        if (p==null){
            atoucherpegs=false;
        }
        return p;
    }
    public Rectangle touchedWall(double deltaT){
        Rectangle r=null;
        for (Rectangle rect:court.getRectangles()){
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
                else {return r;} 
            }

        return r;
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