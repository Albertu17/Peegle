package Vue.Menu;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.*;

import Vue.Controleur;
import Vue.ImageImport;

public class Menu extends JPanel {

    private int width;
    private int height;

    private int middleH;
    private int middleW;

    private Controleur controleur ;

    BoutonMenu btnPlay;
    BoutonMenu btnCampagne;
    BoutonMenu btnOptions;
    BoutonMenu btnEditeur;
    BoutonMenu btnQuit;

    private BufferedImage background;
    private BufferedImage title;

    public Menu(Controleur c) {

        controleur = c ;
        width = c.width;
        height = c.height;
        setLayout(null); // À mettre car selon les machines le layout par défault n'est pas le même.
        setSize(width, height);

        // background
        background = ImageImport.getImage("Menu/menuBackground.jpg", width, height);
        title = ImageImport.getImage("Menu/trucjojo.png");

        middleW = width/2;
        middleH = height/2 + 50;

        // BoutonMenu play
        btnPlay = new BoutonMenu("play", 200, 50);
        btnPlay.setLocation(middleW-100, middleH-25-140);
        btnPlay.addActionListener(e -> controleur.launchGameview("Perso/Triangle")); //TODO remplacer par la campagne
        add(btnPlay);

        // BoutonMenu campagne
        btnCampagne = new BoutonMenu("campaing", 200, 50);
        btnCampagne.setLocation(middleW-100,middleH-25-70);
        btnCampagne.addActionListener(e -> controleur.launchSelectNiveau());
        add(btnCampagne);
        
        // BoutonMenu options
        btnOptions = new BoutonMenu("options", 200, 50);
        btnOptions.setLocation(middleW-100,middleH-25); 
        btnOptions.addActionListener(e -> controleur.launchParametres());
        add(btnOptions);

        // BoutonMenu editor
        btnEditeur = new BoutonMenu("editor", 200, 50);
        btnEditeur.setLocation(middleW-100,middleH-25+70); 
        btnEditeur.addActionListener(e -> controleur.launchEditeurNiveaux());
        add(btnEditeur);

        // BoutonMenu quit
        btnQuit = new BoutonMenu("quit", 200, 50);
        btnQuit.setLocation(middleW-100,middleH-25+140);
        btnQuit.addActionListener(e -> System.exit(0));
        add(btnQuit);
        setFocusable(true);
        addKeyListener(new BoutonMenu.BoutonClavier(new BoutonMenu[]{btnPlay, btnCampagne, btnOptions, btnEditeur, btnQuit}, () -> System.exit(0)));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, this);
        g.drawImage(title, middleW-title.getWidth()/2-10, 40, this);
    }
    
    // TODO pas utilisée car ImageImport.getImage() peut le faire
    BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_DEFAULT);
        BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
        return outputImage;
    }
 }