package Modele;

public class Rectangle {
    public double angle;
    
    public int x0,y0,x1,y1;
    public double longeur;

    Rectangle(int x0,int y0,int longeur,double degre){
        this.x0=x0;
        this.y0=y0;

        this.angle=-degre * Math.PI/180;
        this.longeur=longeur;
    }

    public int caculX1() {
        x1 = x0 + (int) (longeur*Math.cos(angle));
        return  x1;
    }

    public int caculY1() {
        y1 = y0 + (int) (longeur*Math.sin(angle));
        return y1;
    }
}