import javax.swing.JFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class MenuPrincipal extends JFrame {

    JPanel zoneTexte = new JPanel();
    JPanel bouttons = new JPanel();
    JLabel nomPeggle = new JLabel("Peggle");
    JButton campagne = new JButton("Campagne");
    JButton libre = new JButton("Jeu Libre");
    JButton parametres = new JButton("Parametres");
    JButton quitter = new JButton("Quitter");

    MenuPrincipal(){
        setSize(600,700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
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

        
        campagne.addActionListener((ActionEvent e)->{
            hide();
            new GameView(500, 500);
        });

        libre.addActionListener(e ->{
            hide();
            new MenuLibre();
        });



        parametres.addActionListener(e ->{
            hide();
            new MenuParametre();
        } );


        quitter.addActionListener((ActionEvent e)->{
            System.exit(0);
        });
    }





    public static void main(String[] args) {
        new MenuPrincipal();
    }
    
}



