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
        
        pivotDeRotation = new Point(largeurFrame/2, 10) ;

        // position du canon initial à la vertical
        angleOrientation = Math.PI/2 ;     
        
        
        // setPreferredSize(new Dimension(50,50));
        // setLocation(largeurFrame/2 - getWidth(), 100);
        setBounds(largeurFrame/2 - getWidth(), 100, 50, 50);

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

    // @Override
    // public void paint(Graphics g) {
    //     if (true){
    //         // creation de la ligne de viser :
    //             g.setColor(Color.BLACK);
    //             g.drawLine(pivotDeRotation.x, pivotDeRotation.y, (int)(Math.cos(angleOrientation)*tailleLigneTir+pivotDeRotation.x), (int)(Math.sin(angleOrientation)*tailleLigneTir+pivotDeRotation.y)) ;
    //             // System.out.println("x, y : " +String.valueOf((int)(Math.cos(angleOrientation)*tailleLigneTir+pivotDeRotation.x)) + ", "+ String.valueOf((int)(Math.sin(angleOrientation)*tailleLigneTir+pivotDeRotation.y)));
    //             // System.out.println(angleOrientation);

    //         // la balle suit le bout du canon :
    //             placementBallCanon();

    //     }

    //     // mouvement du canon
        

    //     super.paint(g);
    //     // paintComponent(graphics2D);
    // }

    // @Override
    // public void paintComponent(Graphics g) {
    //     super.paintComponent(g);

    //     // g.drawImage(image, 0, 0, this);
        
    //     // System.out.println("paintCompo");
    //     // // if (balleATirer != null){
    //     // if (true){
    //     //     // creation de la ligne de viser :

    //     //         g.drawLine(pivotDeRotation.x, pivotDeRotation.y, (int)Math.cos(angleOrientation)*tailleLigneTir+pivotDeRotation.x, (int)Math.sin(angleOrientation)*tailleLigneTir+pivotDeRotation.y) ;
    //     //         // System.out.println("paintCo");

    //     //     // la balle suit le bout du canon :
    //     //         placementBallCanon();

    //     // }

    //     // this.setPreferredSize(new Dimension(100,100));
    //     // System.out.println("TAille image "+  image.getHeight());
    //     // System.out.println(this.getPreferredSize());

    //     // g.setColor(Color.ORANGE);
    //     // g.drawOval(0, 0, 50, 50);

    //     // TODO faire bouger le canon
    
    //         int newHeigh = image.getWidth();
    //         int newWidth = image.getHeight();
    //         int typeOfImage = image.getType();
    
    //         BufferedImage temp = new BufferedImage(newHeigh, newWidth, typeOfImage);
    //         Graphics2D graphics2D = temp.createGraphics();
    //         graphics2D.rotate( angleOrientation, pivotDeRotation.x, pivotDeRotation.y);
    //         graphics2D.drawImage(image, null, 0, 0);
    //         image = temp ;
        
        

    //     super.paintComponents(g);
    // }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g ;

        g2D.rotate( angleOrientation, 0, 0);
        g2D.drawImage(image, 0, 0, null);  
        
        // g.drawImage(image, 0, 0, this) ;
    }

    public static void main(String[] args) {
        JFrame test = new JFrame();
        test.setSize(200,200);
        test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        test.setVisible(true);
        Canon c = new Canon(200);
        test.add(c);
        test.repaint();
        c.repaint();
    }



}