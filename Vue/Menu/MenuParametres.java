package Vue.Menu;


import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.*;

import Vue.Controleur;
import Vue.ImageImport;


public class MenuParametres extends JPanel {

    // Controleur
    private Controleur controleur;
    int width;
    int height;

    private int middleH;
    private int middleW;

    private BufferedImage background;

    JButton btnSkin1;
    JButton btnSkin2;
    JButton btnSkin3;
    JButton btnRetour;

    JButton plus;
    JButton minus;

    JLabel vitesse;

    public MenuParametres(Controleur c){

        this.controleur = c ;
        width = controleur.getWidth();
        height = controleur.getHeight();
        middleW = width /2;
        middleH = height/2 + 50;
        setSize(width, height);
        setLayout(null);
        setVisible(true);
        
        // background
        background = ImageImport.getImage("Menu/menuBackground.jpg", width, height);


        // BoutonMenu skin1
        btnSkin1 = new BoutonMenu("skin1", 200, 50);
        btnSkin1.setLocation(middleW-100,middleH-25-140);
        btnSkin1.addActionListener(e -> {
            c.gameview.court.setSkin1();
        }); 
        add(btnSkin1);

        // BoutonMenu skin2
        btnSkin2 = new BoutonMenu("skin2", 200, 50);
        btnSkin2.setLocation(middleW-100,middleH-25-70);
        btnSkin2.addActionListener(e -> {
            c.gameview.court.setSkin2();
        }); 
        add(btnSkin2);

        // BoutonMenu skin3
        btnSkin3 = new BoutonMenu("skin3", 200, 50);
        btnSkin3.setLocation(middleW-100,middleH-25);
        btnSkin3.addActionListener(e -> {
            c.gameview.court.setSkin3();
        }); 
        add(btnSkin3);

        // BoutonMenu back
        btnRetour = new BoutonMenu("back", 200, 50);
        btnRetour.setLocation(40,40);
        btnRetour.addActionListener(e -> controleur.launchMenu());
        add(btnRetour);

        plus = new BoutonMenu("Plus", 100, 50);
        plus.setLocation(middleW-400,middleH-25-70);
        plus.addActionListener(e->{ c.gameview.court.upVitesse();
            double t = c.gameview.court.canon.getVitesseTir();
            String s = String.valueOf(+t);
            vitesse.setText(s);
        });
        add(plus);
        minus = new BoutonMenu("Minus",100, 50);
        minus.setLocation(middleW-600,middleH-25-70);
        minus.addActionListener(e->{
            c.gameview.court.downVitesse();
            double t = c.gameview.court.canon.getVitesseTir();
            String s = String.valueOf(+t);
            vitesse.setText(s);
        });
        add(minus);
        
        double t = c.gameview.court.getCanon().getVitesseTir();
        String s = String.valueOf(+t);
        vitesse = new JLabel(s);
        vitesse.setFont(new Font("Verdana",Font.PLAIN,30));
        vitesse.setBounds(middleW-500, middleH,200,50);
        add(vitesse);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, this);
    }
}