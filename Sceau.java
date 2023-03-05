import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Image;

public class Sceau{
    public final int longeur = 100; // m
    public final int hauteur = 50;
    private int bordure = 10; 

    public double X, Y; // m
    public double speedX = 100; // m
    private GameView court;
    private BufferedImage image;
    

    Sceau(GameView c){
        court=c;
        X = court.getWidth()/2 - longeur/2;
        Y = court.getWidth() - (hauteur + hauteur/2);

        try {    
            image = ImageIO.read(new File("./bucket.png"));
        } catch (Exception e) {
            System.out.println(e);
        }  
        image = resize(image, longeur, hauteur);
    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {  
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
    

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
    
        return dimg;
    } 

    public BufferedImage getImage(){
        return image;
    }


    public void move(double deltaT){
        double nextX = X + deltaT * speedX;

        // speedX = speedX
        if (touchedWallX(nextX)){
            speedX = -speedX;
            nextX = X + deltaT * speedX;
        };
        //System.out.println(inside(b));
        X = nextX;
    }

    public boolean inside(Ball b){
        return X <= b.nextBallX && b.nextBallX + b.ballRadius*2 <= X + longeur &&
        Y  + 20 <= b.nextBallY && b.nextBallY + 20 <= Y + hauteur;

    }

    public boolean touchedWallX(double nextX){
        return nextX < 0 || nextX> court.getWidth() - longeur;
    }

    public boolean toucheBordureSceau(Ball b){
        return ((X <= b.nextBallX + b.ballRadius*2 && b.nextBallX  <= X + bordure) || (X + longeur - bordure  <= b.nextBallX + b.ballRadius*2 && b.nextBallX <= X  + longeur) ) && 
        Y + 10  <= b.nextBallY && b.nextBallY + 10 <= Y + hauteur; //hardcoding pour toucher plus bas
    }
}