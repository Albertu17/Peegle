package Modele;

public class Pegs {
    
    private int x;
    private int y;
    private int radius;//DIAMETRE PAS CONFONDRE
    private int couleur;
    private String imageString;
    private boolean toucher = false;
    public boolean atoucherpegs = false;

    // int 1 à 4 couleur image 
    public Pegs(int x, int y, int radius, int couleur) {
        this.x = x;
        this.y = y;
        this.radius = radius;
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

    public int getRadius() {
        return radius;
    }
    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getCouleur() {
        return couleur;
    }
    public void setCouleur(int couleur) {
        this.couleur = couleur;
    }

    public void toucher() {
        toucher = true;
    }

    public boolean getHit() {
        return toucher;
    }

    public boolean contains(double i, double j) {
        if (Math.sqrt(Math.pow(i-(x +radius/2) , 2)+Math.pow(j-(y+radius/2), 2))< radius/2 + Ball.ballRadius) {
            return true;
        }
        return false;
    }
    
    // celui d'albert
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
