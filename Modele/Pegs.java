package Modele;

public class Pegs {
    
    private int x;
    private int y;
    private int radius;
    private int couleur;
    private String imageString;
    private boolean toucher = false;

    // int 1 à 4 couleur image 
    public Pegs(int x, int y, int radius, int couleur) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.couleur = couleur;
        if (couleur == 1) {
            imageString = "blueball.png";
        }
        else if (couleur == 2) {
            imageString = "redball.png";
        }
        else if (couleur == 3) {
            imageString = "violetball.png";
        }
        else if (couleur == 4) {
            imageString = "vertball.png";
    }
}
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getCouleur() {
        return couleur;
    }

    public void toucher() {
        toucher = true;
    }

    public boolean getHit() {
        return toucher;
    }

    public boolean contains(int i, int j) {
        if (Math.sqrt(Math.pow(i-x, 2)+Math.pow(j-y, 2))<=radius) {
            return true;
        }
        return false;
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
}
