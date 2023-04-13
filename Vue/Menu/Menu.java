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
    JButton btnOption;
    JButton btnQuit;

    Icon imageIconPlay2;
    Icon imageIconPlay;

    Icon imageIconOptions2;
    Icon imageIconOptions;

    Icon imageIconCampaing2;
    Icon imageIconCampaing;

    Icon imageIconQuit2;
    Icon imageIconQuit;

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

        // imageIconPlay = new ImageIcon(ImageImport.getImage("Menu/planche police blanche.png"));
        // Image image = ((ImageIcon) imageIconPlay).getImage(); // transform it 
        // Image newimg = image.getScaledInstance(200, 50,  java.awt.Image.SCALE_SMOOTH);
        // imageIconPlay = new ImageIcon(newimg);

        // imageIconPlay2 = new ImageIcon(ImageImport.getImage("Menu/planche police jaune.png"));
        // image = ((ImageIcon) imageIconPlay2).getImage(); // transform it 
        // newimg = image.getScaledInstance(200, 50,  java.awt.Image.SCALE_SMOOTH);
        // imageIconPlay2 = new ImageIcon(newimg);



        imageIconOptions = new ImageIcon(ImageImport.getImage("Menu/planche option blanc.png"));
        Image image = ((ImageIcon) imageIconOptions).getImage(); // transform it 
        Image newimg = image.getScaledInstance(200, 50,  java.awt.Image.SCALE_SMOOTH);
        imageIconOptions = new ImageIcon(newimg);

        imageIconOptions2 = new ImageIcon(ImageImport.getImage("Menu/planche option jaune.png"));
        image = ((ImageIcon) imageIconOptions2).getImage(); // transform it 
        newimg = image.getScaledInstance(200, 50,  java.awt.Image.SCALE_SMOOTH);
        imageIconOptions2 = new ImageIcon(newimg);



        imageIconCampaing = new ImageIcon(ImageImport.getImage("Menu/planche campaing blanc.png"));
        image = ((ImageIcon) imageIconCampaing).getImage(); // transform it 
        newimg = image.getScaledInstance(200, 50,  java.awt.Image.SCALE_SMOOTH);
        imageIconCampaing = new ImageIcon(newimg);

        imageIconCampaing2 = new ImageIcon(ImageImport.getImage("Menu/planche CAMPAING JAUNE.png"));
        image = ((ImageIcon) imageIconCampaing2).getImage(); // transform it 
        newimg = image.getScaledInstance(200, 50,  java.awt.Image.SCALE_SMOOTH);
        imageIconCampaing2 = new ImageIcon(newimg);



        imageIconQuit = new ImageIcon(ImageImport.getImage("Menu/planche quit blanc.png"));
        image = ((ImageIcon) imageIconQuit).getImage(); // transform it 
        newimg = image.getScaledInstance(200, 50,  java.awt.Image.SCALE_SMOOTH);
        imageIconQuit = new ImageIcon(newimg);

        imageIconQuit2 = new ImageIcon(ImageImport.getImage("Menu/planche quit jaune.png"));
        image = ((ImageIcon) imageIconQuit2).getImage(); // transform it 
        newimg = image.getScaledInstance(200, 50,  java.awt.Image.SCALE_SMOOTH);
        imageIconQuit2 = new ImageIcon(newimg);


        middleW = largeur /2;
        middleH = hauteur/2 + 50;

        btnPlay = new BoutonMenu("play_");
        // btnPlay = new JButton(imageIconPlay);
        btnPlay.setBounds(middleW-100,middleH-25-140,200,50); 
        // btnPlay.setBorderPainted(false); 
        // btnPlay.setContentAreaFilled(false); 
        // btnPlay.setFocusPainted(false); 
        // btnPlay.setOpaque(false);
        // btnPlay.addMouseListener((MouseListener) new MouseAdapter() 
        // {
        //    public void mouseEntered(MouseEvent evt) 
        //    {
        //         btnPlay.setIcon(imageIconPlay2);
        //    }
        //    public void mouseExited(MouseEvent evt) 
        //    {
        //         btnPlay.setIcon(imageIconPlay);
        //    }
        //       public void mouseClicked(MouseEvent evt) 
        //       {
        //         controleur.launchGameview("Perso/Triangle"); //TODO remplacer par la campagne
        //       }
        // });

        btnCampagne = new JButton(imageIconCampaing);
        btnCampagne.setBounds(middleW-100,middleH-25-70,200,50);
        btnCampagne.setBorderPainted(false); 
        btnCampagne.setContentAreaFilled(false); 
        btnCampagne.setFocusPainted(false); 
        btnCampagne.setOpaque(false);
        
        btnCampagne.addMouseListener((MouseListener) new MouseAdapter() 
        {
           public void mouseEntered(MouseEvent evt) 
           {
            btnCampagne.setIcon(imageIconCampaing2);
           }
           public void mouseExited(MouseEvent evt) 
           {
            btnCampagne.setIcon(imageIconCampaing);
           }
              public void mouseClicked(MouseEvent evt) 
              {
                //TODO par forcement le bon truc
                controleur.launchSelectNiveau(true);
              }
        });
        

        btnOption = new JButton(imageIconOptions);
        btnOption.setBounds(middleW-100,middleH-25,200,50);  
        btnOption.setBorderPainted(false); 
        btnOption.setContentAreaFilled(false); 
        btnOption.setFocusPainted(false); 
        btnOption.setOpaque(false);
        btnOption.addMouseListener((MouseListener) new MouseAdapter() 
        {
           public void mouseEntered(MouseEvent evt) 
           {
            btnOption.setIcon(imageIconOptions2);
           }
           public void mouseExited(MouseEvent evt) 
           {
            btnOption.setIcon(imageIconOptions);
           }
           public void mouseClicked(MouseEvent evt) 
           {
            controleur.launchParametre();
           }
        });

        btnQuit = new JButton(imageIconQuit);
        btnQuit.setBounds(middleW-100,middleH-25+70,200,50);  
        btnQuit.setBorderPainted(false); 
        btnQuit.setContentAreaFilled(false); 
        btnQuit.setFocusPainted(false); 
        btnQuit.setOpaque(false);
        btnQuit.addMouseListener((MouseListener) new MouseAdapter() 
        {
           public void mouseEntered(MouseEvent evt) 
           {
            btnQuit.setIcon(imageIconQuit2);
           }
           public void mouseExited(MouseEvent evt) 
           {
            btnQuit.setIcon(imageIconQuit);
           }
           public void mouseClicked(MouseEvent evt) 
           {
            System.exit(0);
           }
        });

        add(btnPlay);
        add(btnCampagne);
        add(btnOption);
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

    public class BoutonMenu extends JButton {

        ImageIcon imageIconNormal;
        ImageIcon imageIconOnHover;

        public BoutonMenu(String texteImage) {
            imageIconNormal = new ImageIcon(ImageImport.getImage("Menu/planche_" + texteImage + "blanche.png"));
            Image image = ((ImageIcon) imageIconNormal).getImage(); // transform it 
            Image newimg = image.getScaledInstance(200, 50,  java.awt.Image.SCALE_SMOOTH);
            imageIconNormal = new ImageIcon(newimg);
    
            imageIconOnHover = new ImageIcon(ImageImport.getImage("Menu/planche_" + texteImage + "jaune.png"));
            image = ((ImageIcon) imageIconOnHover).getImage(); // transform it 
            newimg = image.getScaledInstance(200, 50,  java.awt.Image.SCALE_SMOOTH);
            imageIconOnHover = new ImageIcon(newimg);

            setBorderPainted(false); 
            setContentAreaFilled(false); 
            setFocusPainted(false); 
            setOpaque(false);
            setIcon(imageIconNormal);
            addMouseListener((MouseListener) new MouseAdapter() {
                public void mouseEntered(MouseEvent evt) { setIcon(imageIconOnHover);}
                public void mouseExited(MouseEvent evt) { setIcon(imageIconNormal); }
            });
        }
    }
 }