import javax.swing.*;


import java.awt.*;
import java.awt.event.MouseEvent ;



public class Canon extends JPanel{
    private Ball balleATirer ;

    private java.awt.Rectangle canon ;

    private Point pivotDeRotation ;

    // Pointer à gauche revient à 0, à droite pi
    // en Radiant
    private double angleOrientation ;

    private int tailleLigneTir  = 85;


    public Canon(int largeurFrame){
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
        

        
        // update de l'orientation du canon
        this.repaint();
        
    }

    public void tirer(){
        // TODO tie de la balle
    }


    @Override
    public void paintComponents(Graphics g) {
        // System.out.println("prijnt");
        // if (balleATirer != null){
        if (true){
            // creation de la ligne de viser :

                g.drawLine(pivotDeRotation.x, pivotDeRotation.y, (int)Math.cos(angleOrientation)*tailleLigneTir+pivotDeRotation.x, (int)Math.sin(angleOrientation)*tailleLigneTir+pivotDeRotation.y) ;
                System.out.println("prijnt");

            // la balle suit le bout du canon :
                placementBallCanon();

        }
        
        // mouvement du canon
        // TODO faire bouger le canon

        super.paintComponents(g);
    }



}