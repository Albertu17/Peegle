package Menu;
import javax.swing.JFrame;

import GameView;
import Controleur ; 

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class MenuPrincipal extends JPanel {

    MenuLibre menuLibre ;
    MenuParametre menuParametre ;
    
    


    JPanel zoneTexte = new JPanel();
    JPanel bouttons = new JPanel();
    JLabel nomPeggle = new JLabel("Peggle");
    JButton campagne = new JButton("Campagne");
    JButton libre = new JButton("Jeu Libre");
    JButton parametres = new JButton("Parametres");
    JButton quitter = new JButton("Quitter");


    MenuPrincipal(){
        
        setLayout(new BorderLayout());
        zoneTexte.setPreferredSize(new Dimension(600,150));
        zoneTexte.setLayout(new BorderLayout());
        zoneTexte.add(nomPeggle,BorderLayout.CENTER);
        nomPeggle.setHorizontalAlignment((int) CENTER_ALIGNMENT);
        nomPeggle.setFont(new Font("Verdana", Font.PLAIN, 60));

        bouttons.setPreferredSize((new Dimension(600,550)));
        bouttons.setLayout(new GridLayout(4,1));
        bouttons.add(campagne);
        bouttons.add(libre);
        bouttons.add(parametres);
        bouttons.add(quitter);

        add(zoneTexte,BorderLayout.NORTH);
        add(bouttons,BorderLayout.CENTER);
        
        menuLibre  = new MenuLibre() ;
        menuParametre = new MenuParametre() ;

        
        campagne.addActionListener((ActionEvent e)->{
            setVisible(false);
            
        });

        libre.addActionListener(e ->{
            setVisible(false);
            menuLibre.setVisible(true);
        });



        parametres.addActionListener(e ->{
            setVisible(false);
            menuParametre.setVisible(true);
        } );


        quitter.addActionListener((ActionEvent e)->{
            System.exit(0);
        });
    }





    public static void main(String[] args) {
        new MenuPrincipal();
    }
    
}



