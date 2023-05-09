package Vue.Menu;

import java.awt.Color;
import java.awt.Font;
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

    private String[] allNameImage = new String[] { "Ball/ball.png", "Ball/basketBall.png", "Ball/smileysBall.png",
            "Ball/soccerBall.png", "Ball/tennisBall.png" };
    private BoutonBall[] tabBouton;
    BoutonBall btnSkin1;
    BoutonBall btnSkin2;
    BoutonBall btnSkin3;
    JButton btnRetour;
    JButton skin;
    JButton musicOn;
    JButton musicOff;

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
        // add(btnSkin3);

        // BoutonMenu back
        btnRetour = new BoutonMenu("back", 200, 50);
        btnRetour.setLocation(40, 40);
        btnRetour.addActionListener(e -> controleur.launchMenu());
        add(btnRetour);

        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(new BoutonMenu.BoutonClavier(new BoutonMenu[] {}, () -> controleur.launchMenu()));

        skin = new BoutonMenu("Skins",100,50);
        skin.setLocation(middleW-30, middleH-250);
        add(skin);

        musicOn = new BoutonMenu("Music ON", 100,50);
        musicOn.setLocation(middleW + 300, middleH - 25 - 70);
        musicOn.addActionListener(e->{
            c.gameview.startMusic();
        });
        add(musicOn);

        musicOff = new BoutonMenu("Music OFF", 100, 50);
        musicOff.setLocation(middleW + 500, middleH - 25 - 70);
        musicOff.addActionListener(e->{
            c.gameview.stopMusic();
        });
        add(musicOff);

        plus = new BoutonMenu("Plus", 100, 50);
        plus.setLocation(middleW - 400, middleH - 25 - 70);
        plus.addActionListener(e -> {
            c.gameview.court.upVitesse();
            int t = (int) c.gameview.court.getCanon().getVitesseTir();
            String s = String.valueOf(+t);
            vitesse.setText(s);
        });
        add(plus);
        minus = new BoutonMenu("Minus", 100, 50);
        minus.setLocation(middleW - 600, middleH - 25 - 70);
        minus.addActionListener(e -> {
            c.gameview.court.downVitesse();
            int t = (int) c.gameview.court.getCanon().getVitesseTir();
            String s = String.valueOf(+t);
            vitesse.setText(s);
        });
        add(minus);

        int t = (int) c.gameview.court.getCanon().getVitesseTir();
        String s = String.valueOf(+t);
        vitesse = new JLabel(s);
        vitesse.setFont(new Font("Verdana", Font.PLAIN, 30));
        vitesse.setBounds(middleW - 500, middleH, 200, 50);
        add(vitesse);

        iluminateButton();
    }

    private void iluminateButton() {
        for (int i = 0; i < allNameImage.length; i++) {
            tabBouton[i].repaint();
        }
                // JMenuBar alignement
                String[] values = new String[] {"Aucun", "Haut-Bas", "Bas-Haut", "Vertical", "Horizontal"};
                JComboBox<String> comboBoxAlignement = new JComboBox<String>(values);
                // comboBoxAlignement.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                // comboBoxAlignement.setSelectedItem(0);
                comboBoxAlignement.setBounds(middleW-100,middleH-25+70, 200, 50);
                add(comboBoxAlignement);
                comboBoxAlignement.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, this);
    }

    public class BoutonBall extends JButton {

        int diametre, selecteur;
        ImageIcon imageIconNormal;
        ImageIcon imageIconOnHover;


        public BoutonBall(int selecteur, int diametre) {
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
