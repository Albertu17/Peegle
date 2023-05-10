package Vue.Menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import javax.swing.*;
import Modele.Ball;

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
    private JButton btnRetour;

   
    private Skin skin ; 

    JButton plus;
    JButton minus;

    JLabel vitesse;

    public MenuParametres(Controleur c) {

        this.controleur = c;
        width = controleur.getWidth();
        height = controleur.getHeight();
        middleW = width / 2;
        middleH = height / 2 + 50;
        setSize(width, height);
        setLayout(null);
        setVisible(true);

        // background
        background = ImageImport.getImage("Menu/menuBackground.jpg", width, height);



        // BoutonMenu back
        btnRetour = new BoutonMenu("Retour", 200, 50);
        btnRetour.setLocation(40, 40);
        btnRetour.addActionListener(e -> controleur.launchMenu());
        add(btnRetour);


        skin = new Skin(width/5, height/2) ;
        skin.setVisible(true);
        skin.setLocation(100, 100);
        add(skin) ;

       
       

        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(new BoutonMenu.BoutonClavier(new BoutonMenu[] {}, () -> controleur.launchMenu()));
    }


    public BufferedImage getEditedImage(String txt, int width, int height) {
        BufferedImage buffImg = ImageImport.getImage("Menu/planche_blanche.png", width, height);
        Graphics g = buffImg.getGraphics();
        Font rightFont = ImageImport.rightSizeCarton(txt, width);
        FontMetrics metrics = g.getFontMetrics(rightFont);
        g.setFont(rightFont);
        g.setColor(Color.WHITE);
        g.drawString(txt, width/2 - metrics.stringWidth(txt)/2, height/2  + metrics.getAscent()/2);
        return buffImg ;
    }

    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, this);
    }
    
    public class Skin extends JPanel{
        private String[] allNameImage = new String[] { "Ball/ball.png", "Ball/basketBall.png", "Ball/smileysBall.png",
        "Ball/soccerBall.png", "Ball/tennisBall.png" };
        private BoutonBall[] tabBouton;

        private BufferedImage image ;


        Skin(int width, int height){
            setSize(width, height);
            setOpaque(false);

            image = getEditedImage("    Skins   ", (width*2)/3, 50) ;

            tabBouton = new BoutonBall[5];

            // BoutonMenu skin1
            tabBouton[0] = new BoutonBall(0, 50);
            tabBouton[0].setLocation(middleW , middleH - 25 - 140);

            add(tabBouton[0]);
            
            // BoutonMenu skin2
            tabBouton[1] = new BoutonBall(1, 50);
            tabBouton[1].setLocation(middleW , middleH - 25 - 70);
            
            add(tabBouton[1]);
            // add(tab);
            
            // BoutonMenu skin3
            tabBouton[2] = new BoutonBall(2, 50);
            tabBouton[2].setLocation(middleW , middleH - 25);
            add(tabBouton[2]);
            tabBouton[3] = new BoutonBall(3, 50);
            tabBouton[3].setLocation(middleW , middleH +35);
            add(tabBouton[3]);
            tabBouton[4] = new BoutonBall(4, 50);
            tabBouton[4].setLocation(middleW , middleH +100);
            add(tabBouton[4]);

            iluminateButton() ;
            repaint();
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image,0, 0, image.getWidth(), image.getHeight(),  this) ;
        }
        

        private void iluminateButton() {
            for (int i = 0; i < allNameImage.length; i++) {
                tabBouton[i].repaint();
            }
        }

        class BoutonBall extends JButton {

            int diametre, selecteur;
            ImageIcon imageIconNormal;
            ImageIcon imageIconOnHover;
    
    
            BoutonBall(int selecteur, int diametre) {
                this.diametre = diametre;
                this.selecteur = selecteur ;
                String texteImage = allNameImage[selecteur];
    
                BufferedImage tempNormal = ImageImport.getImage(texteImage, diametre, diametre);
    
                BufferedImage tempHover = ImageImport.getImage("Ball/hoverBall.png", diametre, diametre);
    
                Graphics g = tempHover.createGraphics();
                g.drawImage(ImageImport.getImage(texteImage, diametre - 6, diametre - 6), 3, 3, this);
    
                imageIconNormal = new ImageIcon(tempNormal);
                imageIconOnHover = new ImageIcon(tempHover);
    
                setIcon(imageIconNormal);
                addMouseListener((MouseListener) new MouseAdapter() {
                    public void mouseEntered(MouseEvent evt) {
                        setIcon(imageIconOnHover);
                    }
    
                    public void mouseExited(MouseEvent evt) { 
                        setIcon(imageIconNormal);
                    }
    
                    public void mousePressed(MouseEvent evt) {
                        Ball.setSelecteurImage(selecteur);
                        Ball.setImage((ImageImport.getImage(texteImage, 20, 20)));
                        iluminateButton() ;
                    }
                });
                // Parametrages du bouton
                setBorderPainted(false);
                setContentAreaFilled(false);
                setFocusPainted(false);
                setOpaque(false);
                setSize(diametre, diametre);
            }
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (selecteur == Ball.getSelecteurImage()) {
                    g.setColor(Color.RED);
                    g.fillOval((this.diametre*4)/10, (this.diametre*4)/10, this.diametre/5, this.diametre/5);
                }
            }
        }
    }

    

}
