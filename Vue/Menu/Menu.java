package Vue.Menu;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
    private static Font font;

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
            InputStream targetStream = new FileInputStream("./Vue/Font/cartoonist_kooky.ttf");
            font =  Font.createFont(Font.TRUETYPE_FONT, targetStream);
            font = font.deriveFont(100f);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (FontFormatException ex) {
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
        btnCampagne.addActionListener(e -> controleur.launchSelectNiveau(true)); //TODO pas forcement le bon truc
        add(btnCampagne);
        
        btnOptions = new BoutonMenu("options", 200, 50);
        btnOptions.setLocation(middleW-100,middleH-25); 
        btnOptions.addActionListener(e -> controleur.launchParametre());
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

    public static class BoutonMenu extends JButton {

        int width, height;
        ImageIcon imageIconNormal;
        ImageIcon imageIconOnHover;

        public BoutonMenu(String texteImage, int width, int height) {
            this.width = width;
            this.height = height;
            imageIconNormal = getEditedImageIcon(texteImage, width, height, true);
            imageIconOnHover = getEditedImageIcon(texteImage, width, height, false);
            setIcon(imageIconNormal);
            addMouseListener((MouseListener) new MouseAdapter() {
                public void mouseEntered(MouseEvent evt) {setIcon(imageIconOnHover);}
                public void mouseExited(MouseEvent evt) {setIcon(imageIconNormal);}
            });
            // Parametrages du bouton
            setBorderPainted(false); 
            setContentAreaFilled(false); 
            setFocusPainted(false); 
            setOpaque(false);
            setSize(width, height);
        }

        public ImageIcon getEditedImageIcon (String texte, int width, int height, boolean normal) {
            BufferedImage buffImg;
            if (normal) buffImg = ImageImport.getImage("Menu/planche_blanche.png", width, height);
            else buffImg = ImageImport.getImage("Menu/planche_jaune.png", width, height);
            Graphics g = buffImg.getGraphics();
            Font rightFont = rightFont(texte, g);
            FontMetrics metrics = g.getFontMetrics(rightFont);
            g.setFont(rightFont);
            if (normal) g.setColor(Color.WHITE);
            else g.setColor(Color.YELLOW);
            g.drawString(texte, width/2 - metrics.stringWidth(texte)/2, height * 3/4);
            return new ImageIcon(buffImg);
        }

        // Retourne une font dont la taille est adaptée aux dimensions du bouton.
        public Font rightFont (String texte, Graphics g) {
            Font rightF = font;
            FontMetrics metrics = g.getFontMetrics(rightF);
            int fontSize = rightF.getSize();

            // Rétrécit la taille de la font si la hauteur du texte sera trop grande.
            int textHeight = metrics.getAscent();
            int textHeightMax = height * 1/2;
            if (textHeight > textHeightMax) {
                double heightRatio = (double) textHeightMax / (double) textHeight;
                rightF = rightF.deriveFont((float) Math.floor(fontSize * heightRatio));
                fontSize = rightF.getSize();
                metrics = g.getFontMetrics(rightF);
            }

            // Rétrécit la taille de la font si la largeur du texte sera trop grande.
            int textWidth = metrics.stringWidth(texte);
            int textWidthMax = width;
            if (textWidth > textWidthMax) {
                double widthRatio = (double) textWidthMax / (double) textWidth;
                rightF = rightF.deriveFont((float) Math.floor(fontSize * widthRatio));
                fontSize = rightF.getSize();
                metrics = g.getFontMetrics(rightF);
            }

            return rightF;
        }
    }
 }