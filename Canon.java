import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent ;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.geom.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


import javax.imageio.ImageIO;
import javax.swing.JPanel;



public class Canon extends JPanel{
    
    
    int[] x = new int[11];
    int[] y = new int[11];
    double angleOrientation_old;
    
    private Ball balleATirer ;
    private BufferedImage image; 
    // Pointer à gauche revient à 0, à droite pi
    // en Radiant
    private double angleOrientation ;
    private GameView court ;

    
    // parametre du canon :
        private int tailleLigneTir  = 500;
        private Point pivotDeRotation ;
        private double vitesseTir = 150 ;
        private double tailleCanon = 9/100.0; // en pourcentage de la taille de l'écran



    public Canon(GameView court){

        // à changer par un import global de toute les images
        try {    
            image = ImageIO.read(new File("Image\\cannonGrandV3.png"));
        } catch (Exception e) {
            System.out.println(e);
        }  

        // definition du Gameview
        this.court = court ;
        int largeurFrame = court.getWidth() ;
        
        // Mise à l'echelle du canon :
 
        int tailleImage = (int) (largeurFrame * (tailleCanon) ) ;

        BufferedImage resizedImage = new BufferedImage(tailleImage, tailleImage, image.getType());
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(image, 0, 0, tailleImage, tailleImage, null);
        graphics2D.dispose();
        image = resizedImage;


        
        // position du canon initial à la vertical
        angleOrientation = Math.PI/2 ;     
        
        setBounds(0, 0, image.getWidth(), image.getHeight());
        setLocation(largeurFrame/2 - getWidth()/2, getHeight()/4);

        // definition point de pivot de rotation
        pivotDeRotation = new Point(getX() + getWidth()/2, getY() + getHeight()/2) ;
    }

    // TODO pas forcément utile ça dépend de l'implémentation futur
    public void setBalleATirer(Ball balleATirer) {
        this.balleATirer = balleATirer;
        
        // Placement graphique :
        placementBallCanon();
        
    }
    
    private void placementBallCanon(){
        balleATirer.ballX = pivotDeRotation.x -balleATirer.ballRadius/2 - Math.cos(angleOrientation)*( this.getHeight()/2 );
        balleATirer.ballY = pivotDeRotation.y -balleATirer.ballRadius/2 + Math.sin(angleOrientation)*( this.getHeight()/2 ); 
        
    }


     /**
     * @description Défini la vitesse de la balle pour amorcer le tir 
     *  place un nouvelle balle dans le lanceur
     * @author Thibault
     * @return Ball // ball qui vient d'être tirer
     */
    public Ball tirer(){
        placementBallCanon();

        // definition de la vitesse de la balle
        balleATirer.ballSpeedX = - vitesseTir*Math.cos(angleOrientation);
        balleATirer.ballSpeedY =  vitesseTir*Math.sin(angleOrientation);

        // creation d'une nouvelle ball
        Ball Ball_lancer = balleATirer ;
        balleATirer = new Ball(0, 0, 0, 0, balleATirer.getCourt()) ;

        // return la balle tirer
        return Ball_lancer ;
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
       
        
    }
    

    @Override
    public void paint(Graphics g) {

        Graphics gGameview = court.getGraphics() ;
       
        Point depart = new Point((int)(pivotDeRotation.x + court.getInsets().left -  Math.cos(angleOrientation)*(this.getHeight()/2)), (int)(pivotDeRotation.y + Math.sin(angleOrientation)*(this.getHeight()/2) + court.getInsets().top )) ;

        // ligne de viser
        Graphics2D g2D = (Graphics2D) gGameview ;
        /*x=-t×v_0*×cos(α)
y=t*×v_0×sin(α)+(gt^2)/2 */
        //calcul du point d'arrivé de la ligne de visée
        x[0] = depart.x;
        y[0] = depart.y;
        double deltaT = 0.2;
        if(angleOrientation != angleOrientation_old){
        for (int t = 1; t < 11; t++) {
            x[t] = (int)(depart.x - deltaT*t * vitesseTir * Math.cos(angleOrientation));
            y[t] = (int)(depart.y + deltaT *t* vitesseTir * Math.sin(angleOrientation) + 100.0*deltaT * deltaT*t*t  / 2.0);
        }
    }
        angleOrientation_old = angleOrientation;
        // draw the line of the given polynom of 2nd degree
        float dash1[] = {20.0f};
        BasicStroke dashed = new BasicStroke(5.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);
        g2D.setStroke(dashed);
        g2D.drawPolyline(x, y, 11);
        
        
        super.paint(g);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g ;
        
        // orientation du canon :
        g2D.rotate( Math.PI/2- angleOrientation, getWidth()/2, getHeight()/2);
        g2D.drawImage(image, 0, 0, this) ;
        
    }


}