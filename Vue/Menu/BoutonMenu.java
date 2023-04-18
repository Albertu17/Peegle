package Vue.Menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import Vue.ImageImport;

public class BoutonMenu extends JButton {
    
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
            public void mousePressed(MouseEvent evt) {setIcon(imageIconNormal);}
        });
        // Parametrages du bouton
        setBorderPainted(false); 
        setContentAreaFilled(false); 
        setFocusPainted(false); 
        setOpaque(false);
        setSize(width, height);
    }

    public void setCouleur(boolean jaune){
        if (jaune) setIcon(imageIconOnHover) ;
        else setIcon(imageIconNormal);
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
        g.drawString(texte, width/2 - metrics.stringWidth(texte)/2, height/2 + metrics.getAscent()/2);
        return new ImageIcon(buffImg);
    }

    // Retourne une font dont la taille est adaptée aux dimensions du bouton.
    public Font rightFont (String texte, Graphics g) {
        Font rightF = ImageImport.cartoon.deriveFont(1000f); // Très grande taille de police par défault
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
        int textWidthMax = width * 5/6;
        if (textWidth > textWidthMax) {
            double widthRatio = (double) textWidthMax / (double) textWidth;
            rightF = rightF.deriveFont((float) Math.floor(fontSize * widthRatio));
            fontSize = rightF.getSize();
            metrics = g.getFontMetrics(rightF);
        }

        return rightF;
    }
}
