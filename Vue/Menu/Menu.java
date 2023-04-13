package Vue.Menu;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
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

        btnPlay = new BoutonMenu("planche_play_");
        btnPlay.setBounds(middleW-100,middleH-25-140,200,50); 
        btnPlay.addActionListener(e -> controleur.launchGameview("Perso/Triangle")); //TODO remplacer par la campagne
        add(btnPlay);

        btnCampagne = new BoutonMenu("planche_campaing_");
        btnCampagne.setBounds(middleW-100,middleH-25-70,200,50);
        btnCampagne.addActionListener(e -> controleur.launchSelectNiveau(true)); //TODO pas forcement le bon truc
        add(btnCampagne);
        
        btnOptions = new BoutonMenu("planche_options_");
        btnOptions.setBounds(middleW-100,middleH-25,200,50); 
        btnOptions.addActionListener(e -> controleur.launchParametre());
        add(btnOptions);

        btnEditeur = new BoutonMenu("planche_");
        btnEditeur.setBounds(middleW-100,middleH-25+70,200,50); 
        btnEditeur.addActionListener(e -> controleur.launchEditeurNiveaux());
        add(btnEditeur);

        btnQuit = new BoutonMenu("planche_quit_");
        btnQuit.setBounds(middleW-100,middleH-25+140,200,50);
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

    public static class BoutonMenu extends JButton {

        ImageIcon imageIconNormal;
        ImageIcon imageIconOnHover;

        public BoutonMenu(String texteImage) {
            imageIconNormal = getImageIcon(texteImage + "blanche.png");
            imageIconOnHover = getImageIcon(texteImage + "jaune.png");
            setIcon(imageIconNormal);
            addMouseListener((MouseListener) new MouseAdapter() {
                public void mouseEntered(MouseEvent evt) {setIcon(imageIconOnHover);}
                public void mouseExited(MouseEvent evt) {setIcon(imageIconNormal);}
            });
            parametrages();
        }

        public BoutonMenu(ImageIcon imageIcon) {
            super(imageIcon);
            parametrages();
        }

        public void parametrages() {
            setBorderPainted(false); 
            setContentAreaFilled(false); 
            setFocusPainted(false); 
            setOpaque(false);
        }

        public static ImageIcon getImageIcon(String texteImage) {
            ImageIcon imageIcon = new ImageIcon(ImageImport.getImage("Menu/" + texteImage));
            Image image = ((ImageIcon) imageIcon).getImage(); // transform it 
            Image newimg = image.getScaledInstance(200, 50,  java.awt.Image.SCALE_SMOOTH);
            return new ImageIcon(newimg);
        }
    }
 }