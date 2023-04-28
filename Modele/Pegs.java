package Modele;

public class Pegs implements Cloneable {
    
    private int x, y; // Coordonnées du centre du peg
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
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
    // public boolean contains(int i, int j) {
    //     return Math.pow(i - x, 2) + Math.pow(j - y, 2) <= Math.pow(radius,2);
    // }

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

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
