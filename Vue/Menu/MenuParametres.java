package Vue.Menu;


import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
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

    BoutonBall btnSkin1;
    BoutonBall btnSkin2;
    BoutonBall btnSkin3;
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
        btnSkin1 = new BoutonBall("basketBall.png", 50) ;
        btnSkin1.setLocation(middleW-100,middleH-25-140);
        btnSkin1.addActionListener(e -> {
            c.gameview.court.setSkin1();
        }); 
        add(btnSkin1);

        // BoutonMenu skin2
        btnSkin2 = new BoutonBall("ball.png", 50) ;
        btnSkin2.setLocation(middleW-100,middleH-25-70);
        btnSkin2.addActionListener(e -> {
            c.gameview.court.setSkin2();
        }); 
        add(btnSkin2);

        // BoutonMenu skin3
        btnSkin3= new BoutonBall("soccerBall.png", 50) ;
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
        
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(new BoutonMenu.BoutonClavier(new BoutonMenu[]{}, ()->controleur.launchMenu()));

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
    public class BoutonBall extends JButton {
    
        int diametre ;
        ImageIcon imageIconNormal;
        ImageIcon imageIconOnHover;
    
        public BoutonBall(String texteImage, int diametre) {
            this.diametre = diametre;

            BufferedImage tempNormal =  ImageImport.getImage(texteImage, diametre, diametre) ;
            
            BufferedImage tempHover =  ImageImport.getImage("hoverBall.png", diametre, diametre) ;
            
            Graphics g  = tempHover.createGraphics() ;
            g.drawImage(ImageImport.getImage(texteImage, diametre-6, diametre-6), 3, 3, this) ;
            
            imageIconNormal =  new ImageIcon(tempNormal) ;
            imageIconOnHover =  new ImageIcon(tempHover) ;


            setIcon(imageIconNormal);
            addMouseListener((MouseListener) new MouseAdapter() {
                public void mouseEntered(MouseEvent evt) {setIcon(imageIconOnHover);}
                public void mouseExited(MouseEvent evt) {setIcon(imageIconNormal);}
                public void mousePressed(MouseEvent evt) {setIcon(imageIconOnHover);}
            });
            // Parametrages du bouton
            setBorderPainted(false); 
            setContentAreaFilled(false); 
            setFocusPainted(false); 
            setOpaque(false);
            setSize(diametre, diametre);
        }
    
}

}

