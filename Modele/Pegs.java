package Modele;

import java.awt.Point;

public class Pegs implements Cloneable {
    
    private double x, y; // Coordonnées du centre du peg
    private int radius, diametre;
    private int couleur;
    private String imageString;
    private boolean touche = false;
    public boolean atoucherpegs = false;

    // Pour les fonctions de mouvement
    private int speed = 100; // px/s
    private int[] valeursFctMouvement;
    private Point courtCenter;
    private double radiusToCourtCenter;
    private int courtWidth;
    private int courtHeight;
    private Point rectCenter;
    private double radiusToRectCenter;
    private double rectWidth;
    private double rectHeight;
    private double angleToCenterOfRotation;

    // int 1 à 4 couleur image 
    public Pegs(int x, int y, int radius, int couleur) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        diametre = radius*2;
        this.couleur = couleur;
        imageString = intColorToString(couleur);
        valeursFctMouvement = new int[3];
        angleToCenterOfRotation = -1; // Valeur pour signifier que la variable n'a pas été définie.
    }

    public static String intColorToString(int couleur) {
        String s = "Pegs/";
        switch (couleur) {
            case 1:
                s += "blueball.png";
                break;
            case 2:
                s += "redball.png";
                break;
            case 3:
                s += "violetball.png";
                break;
            case 4:
                s += "vertball.png";
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

    public void setValeursFctMouvement(int[] valeursFctMouvement) {
        this.valeursFctMouvement = valeursFctMouvement;
    }

    public void setCourtCenter(Point courtCenter) {
        this.courtCenter = courtCenter;
    }

    public void setRadiusToCourtCenter() {
        radiusToCourtCenter = Math.sqrt(Math.pow(Math.abs(courtCenter.x - x), 2) + Math.pow(Math.abs(courtCenter.y - y), 2));
    }

    public void updateRadiusToRectCenter() {
        radiusToRectCenter = Math.sqrt(Math.pow(Math.abs(rectCenter.x - x), 2) + Math.pow(Math.abs(rectCenter.y - y), 2));
    }

    public void setCourtWidth(int courtWidth) {
        this.courtWidth = courtWidth;
    }

    public void setCourtHeight(int courtHeight) {
        this.courtHeight = courtHeight;
    }

    public void setRectCenter(Point rectCenter) {
        this.rectCenter = rectCenter;
    }

    public void setRectWidth(double rectWidth) {
        this.rectWidth = rectWidth;
    }

    public void setRectHeight(double rectHeight) {
        this.rectHeight = rectHeight;
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
            return "Pegs/redball.png";
        } else {
            return "Pegs/violetball.png";
        }
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public void fonctionDeMouvement(double deltaT) {
        switch (valeursFctMouvement[0]) { // Mouvement global
            case 1: // Mouvement non-global
                switch (valeursFctMouvement[1]) { // Traversées
                    case 1:
                        traverseeGaucheDroite(deltaT, courtWidth);
                        break;
                    case 2:
                        traverseeDroiteGauche(deltaT, courtWidth);
                        break;
                    default:
                        break;
                }
                switch (valeursFctMouvement[2]) { // Rotations locales
                    case 1: // Rotation autour du centre du rectangle, sens horaire
                        rotationAutourPoint(deltaT, rectCenter, radiusToRectCenter, radiusToRectCenter, true);
                        break;
                    case 2: // Rotation autour du centre du rectangle, sens anti-horaire
                        rotationAutourPoint(deltaT, rectCenter, radiusToRectCenter, radiusToRectCenter, false);
                        break;
                    case 3: // Rotation suivant l'ellipse inscrite dans le rectangle, sens horaire
                        rotationAutourPoint(deltaT, rectCenter, rectWidth/2, rectHeight/2, true);
                        break;
                    case 4: // Rotation suivant l'ellipse inscrite dans le rectangle, sens anti-horaire
                        rotationAutourPoint(deltaT, rectCenter, rectWidth/2, rectHeight/2, false);
                        break;              
                    default:
                        break;
                }
                break;
            case 2: // Rotation centrale horaire
                rotationAutourPoint(deltaT, courtCenter, radiusToCourtCenter, radiusToCourtCenter, true);
                break;
            case 3: // Rotation centrale anti-horaire
                rotationAutourPoint(deltaT, courtCenter, radiusToCourtCenter, radiusToCourtCenter, false);
                break;
            default:
                break;
        }
    }

    public void traverseeGaucheDroite(double deltaT, int largeurCourt) {
        double nextX = x + deltaT * speed;
        if (nextX - radius > largeurCourt) nextX = -radius;
        setX(nextX);
        setRectCenter(new Point((int) (rectCenter.getX() + deltaT * speed), (int) rectCenter.getY()));
        updateRadiusToRectCenter();
    }

    public void traverseeDroiteGauche(double deltaT, int largeurCourt) {
        double nextX = x - deltaT * speed;
        if (nextX + radius < 0) nextX = largeurCourt + radius;
        setX(nextX);
        setRectCenter(new Point((int) (rectCenter.getX() - deltaT * speed), (int) rectCenter.getY()));
        updateRadiusToRectCenter();
    }

    public void rotationAutourPoint(double deltaT, Point centre, double radiusX, double radiusY, boolean horaire) {
        int signe = horaire ? 1 : -1;
        if (angleToCenterOfRotation == -1) angleToCenterOfRotation = Math.PI - Math.atan2(centre.y - y, centre.x - x);
        else angleToCenterOfRotation -= signe*deltaT*speed/(2*Math.PI); // TODO étudier par quoi diviser
        setX(centre.x + Math.cos(angleToCenterOfRotation) * radiusX);
        setY(centre.y - Math.sin(angleToCenterOfRotation) * radiusY); // - le produit car sur un JPanel l'axe y est renversé.
    }
}
