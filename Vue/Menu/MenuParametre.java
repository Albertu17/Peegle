package Vue.Menu;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.*;

import Vue.Controleur;
import Vue.ImageImport;


public class MenuParametre extends JPanel {

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

    public MenuParametre(Controleur c){

        this.controleur = c ;
        width = controleur.getWidth();
        height = controleur.getHeight();
        middleW = width /2;
        middleH = height/2 + 50;
        setSize(width, height);
        setLayout(null);
        setVisible(true);
        
        // setImage background
        background = ImageImport.getImage("Menu/menuBackground.jpg", this.getWidth(), this.getHeight());

        btnSkin1 = new Menu.BoutonMenu("skin1", 200, 50);
        btnSkin1.setLocation(middleW-100,middleH-25-140);
        btnSkin1.addActionListener(e -> System.exit(0)); // TODO à changer
        add(btnSkin1);

        btnSkin2 = new Menu.BoutonMenu("skin2", 200, 50);
        btnSkin2.setLocation(middleW-100,middleH-25-70);
        btnSkin2.addActionListener(e -> System.exit(0)); // TODO à changer
        add(btnSkin2);

        btnSkin3 = new Menu.BoutonMenu("skin3", 200, 50);
        btnSkin3.setLocation(middleW-100,middleH-25);
        btnSkin3.addActionListener(e -> System.exit(0)); // TODO à changer
        add(btnSkin3);

        btnRetour = new Menu.BoutonMenu("back", 200, 50);
        btnRetour.setLocation(40,40);
        btnRetour.addActionListener(e -> controleur.launchMenu());
        add(btnRetour);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, this);
    }
}