import javax.imageio.ImageIO;
import javax.swing.*;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.event.MouseEvent ;



public class Canon extends JPanel{
    private Ball balleATirer ;

    private java.awt.Rectangle canon ;

    private BufferedImage image; 

    
    // Pointer à gauche revient à 0, à droite pi
    // en Radiant
    private double angleOrientation ;
    
    private int tailleLigneTir  = 85;
    private Point pivotDeRotation ;
    private int vitesseTir = 10 ;



    public Canon(int largeurFrame){

        // à changer par un import global de toute les images
        try {    
            image = ImageIO.read(new File("Image\\cannonSolo.png"));
        } catch (Exception e) {
            System.out.println(e);
        } 

        canon = new java.awt.Rectangle() ;
        canon.setSize(10,20) ;
        canon.setLocation((int)( largeurFrame/2 - canon.getWidth()/2), 10) ;   
        
        pivotDeRotation = new Point(largeurFrame/2, 10) ;

        // position du canon initial à la vertical
        angleOrientation = Math.PI/2 ;        
    }

    public void setBalleATirer(Ball balleATirer) {
        this.balleATirer = balleATirer;

        // Placement graphique :
        placementBallCanon();

    }

    private void placementBallCanon(){
        // TODO mettre la boule au bout du canon
    }

   
    
    public void DeplacementCanon(MouseEvent e){
        // calcul angle du canon 
        angleOrientation = Math.atan2((e.getY() - pivotDeRotation.y), (pivotDeRotation.x-e.getX()));
        // System.out.println(angleOrientation*(180/Math.PI));

        // update de l'orientation du canon
        this.repaint();
        
    }

    public void tirer(){
        // TODO tir de la balle
        // donner une vitesse initial :
    }

    @Override
    public void paint(Graphics g) {
        if (true){
            // creation de la ligne de viser :
                g.setColor(Color.BLACK);
                g.drawLine(pivotDeRotation.x, pivotDeRotation.y, (int)(Math.cos(angleOrientation)*tailleLigneTir+pivotDeRotation.x), (int)(Math.sin(angleOrientation)*tailleLigneTir+pivotDeRotation.y)) ;
                // System.out.println("x, y : " +String.valueOf((int)(Math.cos(angleOrientation)*tailleLigneTir+pivotDeRotation.x)) + ", "+ String.valueOf((int)(Math.sin(angleOrientation)*tailleLigneTir+pivotDeRotation.y)));
                // System.out.println(angleOrientation);

            // la balle suit le bout du canon :
                placementBallCanon();

        }

        // mouvement du canon
        // TODO faire bouger le canon
    
            int newHeigh = image.getWidth();
            int newWidth = image.getHeight();
            int typeOfImage = image.getType();
    
            BufferedImage temp = new BufferedImage(newHeigh, newWidth, typeOfImage);
            Graphics2D graphics2D = temp.createGraphics();
            graphics2D.rotate( angleOrientation, pivotDeRotation.x, pivotDeRotation.y);
            graphics2D.drawImage(image, null, 0, 0);
            image = temp ;

        super.paint(g);
    }

    @Override
    public void paintComponents(Graphics g) {
        System.out.println("paint");
        // if (balleATirer != null){
        if (true){
            // creation de la ligne de viser :

                g.drawLine(pivotDeRotation.x, pivotDeRotation.y, (int)Math.cos(angleOrientation)*tailleLigneTir+pivotDeRotation.x, (int)Math.sin(angleOrientation)*tailleLigneTir+pivotDeRotation.y) ;
                System.out.println("paintCo");

            // la balle suit le bout du canon :
                placementBallCanon();

        }
        
        

        super.paintComponents(g);
    }



}