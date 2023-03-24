package Modele;

public class Pegs {
    
    private double x;
    
    private double y;
    
    private double radius;
    
    
    private int couleur;
    private String imageString;
    private boolean toucher = false;
    
    // int 1 à 4 couleur image 
    public Pegs(int x, int y, int radius, int couleur) {
        this.x = (double)x;
        this.y = (double)y;
        this.radius = (double)radius;
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

    // setters avec des doubles
    public void setY(Double y) {
        this.y = y;
    }
    public void setRadius(Double radius) {
        this.radius = radius;
    }
    public void setX(Double x) {
        this.x = x;
    }

    // setters avec des int
    public void setY(int y) {
        this.y = y;
    }
    public void setRadius(int radius) {
        this.radius = radius;
    }
    public void setX(int x) {
        this.x = x;
    }

    // getters doubles 
    public Double getX(boolean t) {
        return x;
    }
    
    public Double getY(boolean t) {
        return y;
    }
    public Double getRadius(boolean t) {
        return radius;
    }

    
    // getters int
    public int getX(){
        return (int)x ;
    }
    public int getY(){
        return (int)y ;
    }

    public int getRadius(){
        return (int)radius ;
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
