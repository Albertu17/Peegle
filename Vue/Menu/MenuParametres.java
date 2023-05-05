package Vue.Menu;


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

    BoutonMenu btnSkin1;
    BoutonMenu btnSkin2;
    BoutonMenu btnSkin3;
    JButton btnRetour;

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
        btnSkin1.addActionListener(e -> System.exit(0)); // TODO à changer
        add(btnSkin1);

        // BoutonMenu skin2
        btnSkin2 = new BoutonMenu("skin2", 200, 50);
        btnSkin2.setLocation(middleW-100,middleH-25-70);
        btnSkin2.addActionListener(e -> System.exit(0)); // TODO à changer
        add(btnSkin2);

        // BoutonMenu skin3
        btnSkin3 = new BoutonMenu("skin3", 200, 50);
        btnSkin3.setLocation(middleW-100,middleH-25);
        btnSkin3.addActionListener(e -> System.exit(0)); // TODO à changer
        add(btnSkin3);

        // BoutonMenu back
        btnRetour = new BoutonMenu("back", 200, 50);
        btnRetour.setLocation(40,40);
        btnRetour.addActionListener(e -> controleur.launchMenu());
        add(btnRetour);
        
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(new BoutonMenu.BoutonClavier(new BoutonMenu[]{btnSkin1, btnSkin2, btnSkin3}, ()->controleur.launchMenu()));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, this);
    }
}