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
            image = ImageIO.read(new File("C:/Users/jojos/Desktop/Peegle/2022-os1-os1a-peegle/Image/cannonPetit.png"));
        } catch (Exception e) {
            System.out.println(e);
        }  
        
        
        // position du canon initial à la vertical
        angleOrientation = Math.PI/2 ;     
        
        
        // setPreferredSize(new Dimension(50,50));

        int taillecarre = Math.max(image.getWidth(), image.getHeight()) ;
        
        // setBounds(0, 0, image.getWidth(), image.getHeight());
        setBounds(0, 0, taillecarre, taillecarre);

       
        setLocation(largeurFrame/2 - getWidth()/2, 50);
        
        // pivotDeRotation = new Point(largeurFrame/2 - getWidth()/2 + image.getWidth()/2 , 100 + image.getHeight()/2) ;
        pivotDeRotation = new Point(getX() + getWidth()/2, getY() + getHeight()/2) ;
        // setBackground(Color.RED);
        // setForeground(getBackground());

        Graphics2D g2D = (Graphics2D) image.getGraphics() ;
        g2D.drawImage(image,0, 0, this) ;

    }

    public void setBalleATirer(Ball balleATirer) {
        this.balleATirer = balleATirer;

        // Placement graphique :
        placementBallCanon();

    }

    private void placementBallCanon(){
        // TODO mettre la boule au bout du canon
        balleATirer.ballX = pivotDeRotation.x + Math.cos(angleOrientation)*this.getHeight() ;
        balleATirer.ballY = pivotDeRotation.x + Math.sin(angleOrientation)*this.getHeight(); 
    }

   
    
    public void DeplacementCanon(MouseEvent e){
        // calcul angle du canon 
        angleOrientation = Math.atan2((e.getY() - pivotDeRotation.y), (pivotDeRotation.x-e.getX()));

        // Correction pour eviter des positions incongrue
        if (angleOrientation >= 160*(Math.PI/180)) angleOrientation = 160*(Math.PI/180);
        else if (angleOrientation < 20*(Math.PI/180) ) angleOrientation = 20*(Math.PI/180) ; 
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
        
        g2D.rotate( Math.PI/2- angleOrientation, getWidth()/2, getHeight()/2);
        g2D.drawImage(image, 0, 0, this) ;
    }


}