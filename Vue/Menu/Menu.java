package Vue.Menu;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.*;

import Vue.Controleur;
import Vue.ImageImport;

public class Menu extends JPanel {

    private int largeur;
    private int hauteur;

    private int middleH;
    private int middleW;

    private Controleur controleur ;

    JButton btnPlay;
    JButton btnCampagne;
    JButton btnOptions;
    JButton btnEditeur;
    JButton btnQuit;

    private BufferedImage background;
    private BufferedImage title;

    public Menu(Controleur c) {

        this.controleur = c ;
        this.hauteur=c.height;
        this.largeur=c.width;
        setLayout(null); // À mettre car selon les machines le layout par défault n'est pas
        // le même
        setSize(largeur, hauteur);

        try {
            background = ImageImport.getImage("Menu/menuBackground.jpg");
            background = resizeImage(background, largeur, hauteur);
            title = ImageImport.getImage("Menu/trucjojo.png");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        middleW = largeur/2;
        middleH = hauteur/2 + 50;

        btnPlay = new BoutonMenu("play", 200, 50);
        btnPlay.setLocation(middleW-100, middleH-25-140);
        btnPlay.addActionListener(e -> controleur.launchGameview("Perso/Triangle")); //TODO remplacer par la campagne
        add(btnPlay);

        btnCampagne = new BoutonMenu("campaing", 200, 50);
        btnCampagne.setLocation(middleW-100,middleH-25-70);
        btnCampagne.addActionListener(e -> controleur.launchSelectNiveau()); //TODO pas forcement le bon truc
        add(btnCampagne);
        
        btnOptions = new BoutonMenu("options", 200, 50);
        btnOptions.setLocation(middleW-100,middleH-25); 
        btnOptions.addActionListener(e -> controleur.launchParametres());
        add(btnOptions);

        btnEditeur = new BoutonMenu("editeur", 200, 50);
        btnEditeur.setLocation(middleW-100,middleH-25+70); 
        btnEditeur.addActionListener(e -> controleur.launchEditeurNiveaux());
        add(btnEditeur);

        btnQuit = new BoutonMenu("quit", 200, 50);
        btnQuit.setLocation(middleW-100,middleH-25+140);
        btnQuit.addActionListener(e -> System.exit(0));
        add(btnQuit);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, this);
        g.drawImage(title, middleW-title.getWidth()/2-10, 40, this);
    }
    
    BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_DEFAULT);
        BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
        return outputImage;
    }
 }