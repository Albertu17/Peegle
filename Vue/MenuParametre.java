package Vue;


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



public class MenuParametre extends JPanel {
    public Controleur controleur;

    private BufferedImage background;

    private int largeur;
    private int hauteur;

    private int middleH;
    private int middleW;

    JButton skin1;
    Icon imageSkin1;
    JButton skin2;
    Icon imageSkin2;
    JButton skin3;
    Icon imageSkin3;

    Icon imageBack;
    JButton back;





     public MenuParametre(Controleur c){
           ImageImport.setImage(true);
        this.controleur = c ;

        largeur = controleur.getWidth();
        hauteur = controleur.getHeight();
        middleW = largeur /2;
        middleH = hauteur/2 + 50;
        setSize(largeur, hauteur);
        setLayout(null);
        setVisible(true);
        background = ImageImport.getImage("Menu/menuBackground.jpg");

        
        imageSkin1 = new ImageIcon(ImageImport.getImage("MenuParametre/skin1.png"));
        Image image = ((ImageIcon) imageSkin1).getImage(); // transform it 
        Image newimg = image.getScaledInstance(200, 50,  java.awt.Image.SCALE_SMOOTH);
        imageSkin1 = new ImageIcon(newimg);

        skin1 = new JButton(imageSkin1);
        skin1.setBounds(middleW-100,middleH-25-140,200,50);

        skin1.addMouseListener((MouseListener) new MouseAdapter() 
        {
           public void mouseEntered(MouseEvent evt) 
           {
                //skin1.setIcon(imageIconPlay2);
           }
           public void mouseExited(MouseEvent evt) 
           {
                skin1.setIcon(imageSkin1);
           }
              public void mouseClicked(MouseEvent evt) 
              {
                System.exit(0);
              }
        });
        imageSkin2 = new ImageIcon(ImageImport.getImage("MenuParametre/skin2.png"));
        image = ((ImageIcon) imageSkin2).getImage(); // transform it 
        newimg = image.getScaledInstance(200, 50,  java.awt.Image.SCALE_SMOOTH);
        imageSkin2 = new ImageIcon(newimg);

        skin2 = new JButton(imageSkin2);
        skin2.setBounds(middleW-100,middleH-25-70,200,50);

        skin2.addMouseListener((MouseListener) new MouseAdapter() 
        {
           public void mouseEntered(MouseEvent evt) 
           {
                //skin1.setIcon(imageIconPlay2);
           }
           public void mouseExited(MouseEvent evt) 
           {
                skin2.setIcon(imageSkin2);
           }
              public void mouseClicked(MouseEvent evt) 
              {
                System.exit(0);
              }
        });

        imageSkin3 = new ImageIcon(ImageImport.getImage("MenuParametre/skin3.png"));
        image = ((ImageIcon) imageSkin3).getImage(); // transform it 
        newimg = image.getScaledInstance(200, 50,  java.awt.Image.SCALE_SMOOTH);
        imageSkin3 = new ImageIcon(newimg);

        skin3 = new JButton(imageSkin3);
        skin3.setBounds(middleW-100,middleH-25,200,50);

        skin3.addMouseListener((MouseListener) new MouseAdapter() 
        {
           public void mouseEntered(MouseEvent evt) 
           {
                //skin1.setIcon(imageIconPlay2);
           }
           public void mouseExited(MouseEvent evt) 
           {
                skin3.setIcon(imageSkin3);
           }
              public void mouseClicked(MouseEvent evt) 
              {
                
              }
        });

        imageBack = new ImageIcon(ImageImport.getImage("back.png"));
        image = ((ImageIcon) imageBack).getImage(); // transform it 
        newimg = image.getScaledInstance(200, 50,  java.awt.Image.SCALE_SMOOTH);
        imageBack = new ImageIcon(newimg);


        back = new JButton(imageBack);
        back.setBounds(40,40,200,50);
        back.addMouseListener((MouseListener) new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) 
            {
                //skin1.setIcon(imageIconPlay2);
            }
            public void mouseExited(MouseEvent evt) 
            {
                back.setIcon(imageBack);
            }
               public void mouseClicked(MouseEvent evt) 
               {
                //c.backMenuFromGameView();
                System.exit(0);
               }
        });
        add(back);

        add(skin1);
        add(skin2);
        add(skin3);





    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.drawImage(background, 0, 0, this);
    }
    
    BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_DEFAULT);
        BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
        return outputImage;
    }



    
}