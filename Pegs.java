public class Pegs {
    private int x;
    private int y;
    private int radius;
    private boolean toucher = false;
    public Pegs(int x, int y, int radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
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
    
    
}
