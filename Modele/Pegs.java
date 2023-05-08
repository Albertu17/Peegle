package Modele;

import java.awt.Point;

public class Pegs implements Cloneable {
    
    private double x, y; // Coordonnées du centre du peg
    private int speed = 100; // px/s
    private int valeurFctMouvement;
    private int largeurCourt;
    private Point centreCourt;
    private int radius, diametre;
    private int couleur;
    private String imageString;
    private boolean touche = false;
    public boolean atoucherpegs = false;

    // int 1 à 4 couleur image 
    public Pegs(int x, int y, int radius, int couleur) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        diametre = radius*2;
        this.couleur = couleur;
        imageString = intColorToString(couleur);
    }

    public static String intColorToString(int couleur) {
        String s = "";
        switch (couleur) {
            case 1:
                s = "blueball.png";
                break;
            case 2:
                s = "redball.png";
                break;
            case 3:
                s = "violetball.png";
                break;
            case 4:
                s = "vertball.png";
                break;
        }
        return s;
    }

    public void setImageString(String imageString) {
        this.imageString = imageString;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setValeurFctMouvement(int valeurFctMouvement) {
        this.valeurFctMouvement = valeurFctMouvement;
    }

    public void setLargeurCourt(int largeurCourt) {
        this.largeurCourt = largeurCourt;
    }

    public void setCentreCourt(Point centreCourt) {
        this.centreCourt = centreCourt;
    }

    public int getDiametre() {
        return diametre;
    }

    public void setDiametre(int diametre) {
        this.diametre = diametre;
        radius = diametre / 2;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
        diametre = radius * 2;
    }

    public int getCouleur() {
        return couleur;
    }

    public void setCouleur(int couleur) {
        this.couleur = couleur;
    }

    public void setTouche(boolean touche) {
        this.touche = touche;
    }

    public boolean getHit() {
        return touche;
    }

    public boolean contains(double i, double j) {
        if (Math.sqrt(Math.pow(i - x, 2) + Math.pow(j - y, 2)) < radius + Ball.ballRadius) {
            return true;
        }
        return false;
    }

    // Retourne true si le point dont les coordonnées sont passées en argument est au sein du peg.
    public boolean contains(int i, int j) {
        return Math.pow(i - x, 2) + Math.pow(j - y, 2) <= Math.pow(radius,2);
    }

    public String getImageString() {
        return imageString;
    }

    public String getImageStringTouche() {
        if (couleur != 2){
            return "redball.png";
        } else {
            return "violetball.png";
        }
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public void fonctionDeMouvement(double deltaT) {
        switch (valeurFctMouvement) {
            case 1:
                traverseeGaucheDroite(deltaT, largeurCourt);
                break;
            case 2:
                traverseeDroiteGauche(deltaT, largeurCourt);
                break;
            case 3:
                rotationCentrale(deltaT, centreCourt);
                break;
            default:
                break;
        }
    }

    public void traverseeGaucheDroite(double deltaT, int largeurCourt) {
        double nextX = x + deltaT * speed;
        if (nextX - radius > largeurCourt) nextX = -radius;
        setX(nextX);
    }

    public void traverseeDroiteGauche(double deltaT, int largeurCourt) {
        double nextX = x - deltaT * speed;
        if (nextX + radius < 0) nextX = largeurCourt + radius;
        setX(nextX);
    }

    public void rotationCentrale(double deltaT, Point centre) {
        double angle = Math.PI - Math.atan2(centre.y - y, centre.x - x);
        if (angle < 0) angle += 2*Math.PI;
        System.out.println(angle);
        setX(centre.x + Math.cos(angle) * Math.abs(centre.x - (x+deltaT * speed)));
        setY(centre.y + Math.sin(angle) * Math.abs(centre.y - (y+deltaT * speed)));
    }
}
