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
    private int vitesseTir = 50 ;



    public Canon(int largeurFrame){

        // à changer par un import global de toute les images
        try {    
            image = ImageIO.read(new File("Image\\cannonPetit.png"));
        } catch (Exception e) {
            System.out.println(e);
        }  
        
        
        // position du canon initial à la vertical
        angleOrientation = Math.PI/2 ;     
        
        
        // setPreferredSize(new Dimension(50,50));

        
        setBounds(0, 0, image.getWidth(), image.getHeight());

       
        setLocation(largeurFrame/2 - getWidth()/2, 50);

        pivotDeRotation = new Point(getX() + getWidth()/2, getY() + getHeight()/2) ;

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
        balleATirer.ballX = pivotDeRotation.x - Math.cos(angleOrientation)*(this.getHeight()/2);
        balleATirer.ballY = pivotDeRotation.y + Math.sin(angleOrientation)*(this.getHeight()/2); 

        System.out.println("x, y : " + String.valueOf(balleATirer.ballX) + ", " + String.valueOf(balleATirer.ballY));
    }

   
    /**
     * @author Thibault
     * @param Mouse Event
     * @description Permet de faire bouger le canon en fonction des coordonnées de la souris
     */
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

    /**
     * @description Défini la vitesse de la balle pour amorcer le tir 
     *  place un nouvelle balle dans le lanceur
     * @author Thibault
     * @return Ball // ball qui vient d'être tirer
     */
    public Ball tirer(){
        // TODO tir de la balle donner une vitesse initial
        balleATirer.ballSpeedX = - vitesseTir*Math.cos(angleOrientation);
        balleATirer.ballSpeedY =  vitesseTir*Math.sin(angleOrientation);

        // creation d'une nouvelle ball
        Ball lancer = balleATirer ;
        balleATirer = new Ball(0, 0, 0, 0, balleATirer.getCourt()) ;
        return lancer ;
    }


    @Override
    public void paint(Graphics g) {

        /// ball
        placementBallCanon();
        g.setColor(Color.BLACK);
        g.fillOval((int)balleATirer.ballX,(int)balleATirer.ballY,(int)balleATirer.ballRadius, (int)balleATirer.ballRadius);
        super.paint(g);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g ;
        
        // orientation du canon :
        g2D.rotate( Math.PI/2- angleOrientation, getWidth()/2, getHeight()/2);
        g2D.drawImage(image, 0, 0, this) ;

        


        // ligne de viser
        // BasicStroke pinceau_pointille = new BasicStroke(12.0f, )
    }


}