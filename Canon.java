import javax.imageio.ImageIO;
import javax.swing.*;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.event.MouseEvent ;
// import java.awt.graphics2D ;    



public class Canon extends JPanel{
    private Ball balleATirer ;

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
            image = ImageIO.read(new File("Image\\cannonSolo1.png"));
        } catch (Exception e) {
            System.out.println(e);
        }  
        
        pivotDeRotation = new Point(largeurFrame/2 - getWidth()/2 + image.getWidth()/2 , 100 + image.getHeight()/2) ;

        // position du canon initial à la vertical
        angleOrientation = Math.PI/2 ;     
        
        
        // setPreferredSize(new Dimension(50,50));
        
        setBounds(0, 0, 200, 200);
        setLocation(largeurFrame/2 - getWidth()/2, 100);

        // setBackground(Color.RED);
        // setForeground(getBackground());
    }

    public void setBalleATirer(Ball balleATirer) {
        this.balleATirer = balleATirer;

        // Placement graphique :
        placementBallCanon();

    }

    private void placementBallCanon(){
        // TODO mettre la boule au bout du canon
        // balleATirer.ballX = 1;
        // balleATirer.ballSpeedY =1; 
    }

   
    
    public void DeplacementCanon(MouseEvent e){
        // calcul angle du canon 
        angleOrientation = Math.atan2((e.getY() - pivotDeRotation.y), (pivotDeRotation.x-e.getX()));
        // System.out.println(angleOrientation*(180/Math.PI));

        
        // update de l'orientation du canon + balle
        repaint();
        
    }

    public void tirer(){
        // TODO tir de la balle donner une vitesse initial
        // balleATirer.ballSpeedX = vitesseTir;
        // balleATirer.ballSpeedY =  vitesseTir;

    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g ;

        //g2D.rotate( angleOrientation, 0, 0);
        //g2D.drawImage(image, 0, 0, null);  
        
        g2D.rotate( Math.PI/2- angleOrientation, getWidth()/2, 0);
        g2D.drawImage(image, getWidth()/2, 0, this) ;
    }

    // public static void main(String[] args) {
    //     JFrame test = new JFrame();
    //     test.setSize(200,200);
    //     test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //     test.setVisible(true);
    //     Canon c = new Canon(200);
    //     test.add(c);
    //     test.repaint();
    //     c.repaint();
    // }



}