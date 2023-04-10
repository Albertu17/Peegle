package Vue;

import java.awt.*;

import javax.swing.*;


public class Test {
    
public static void main(String[] args) {
  
    ImageImport.setImage(false) ;
    Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize(); 
    int hauteur = (int)tailleEcran.getHeight(); 
    int largeur = (int)tailleEcran.getWidth();
    Court court  =new Court(hauteur-100, largeur-100, "") ;
    JFrame frame = new JFrame() ;
    frame.setSize(tailleEcran); 
    frame.setVisible(true);
    frame.add(court) ;
    court.setVisible(true);
    court.setBounds(100, 100, hauteur-100, largeur-100);
    }
}