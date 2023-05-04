package Vue;

import Vue.Menu.BoutonMenu;
import Vue.Menu.Menu;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.*;

import Modele.Ball;
import Modele.Niveau;

public class GameView extends JPanel {

    public Controleur controleur ;
    private int width;
    private int heigth;

    Niveau niveau;

    // Court
    Court court;
    private int courtWidth;
    private int courtHeight;

    JButton btnRetour;

    // menu pause
    JeuEnpause jeuEnpause ;

    public GameView(Controleur c, String nomNiveau) {

        this.controleur = c ;

        width = controleur.getWidth();
        heigth = controleur.getHeight();
        setSize(width, heigth);
        setLayout(null);
        setVisible(true); 
        setBackground(Color.BLACK);

        courtWidth = width - 400;
        courtHeight = heigth - 200;

        // Affectation du niveau
        if (nomNiveau == null) niveau = new Niveau(nomNiveau);
        else if (nomNiveau.toLowerCase().equals("aleatoire")) niveau = Niveau.NiveauAleatoire(width, heigth, 10, 20);
        else niveau = Niveau.importPegles(nomNiveau, courtWidth, courtHeight);
        
        jeuEnpause = new JeuEnpause() ;


        // Court
        court = new Court(courtWidth, courtHeight, niveau, c);
        court.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        court.setBounds((width-courtWidth)/2, (heigth-courtHeight)/2, courtWidth, courtHeight);
        court.setVisible(true);
        add(court);

        // JButton bouton retour
        btnRetour = new BoutonMenu("Back", 5* (Ball.ballRadius*2 + 10), 50);
        btnRetour.setLocation(35-Ball.ballRadius,20);
        btnRetour.setVisible(true);
        btnRetour.addActionListener(e -> controleur.launchMenu());
        add(btnRetour);


        // Background
        Background background = new Background("test.jpg", court,heigth,width);
        background.setBounds(0, 0, width, heigth);
        background.setOpaque(false);
        add(background);


    }

    public void launchMenuPause(){
        jeuEnpause.setVisible(true);
        jeuEnpause.menuEnpause.setVisible(true) ;
        court.setEnPause(true);
        repaint() ;
    }

    class JeuEnpause extends JPanel{
        MenuEnpause menuEnpause ;
        JeuEnpause(){
            menuEnpause = new MenuEnpause() ;
            add(menuEnpause) ;
        }

        @Override
        public void paintComponents(Graphics g) {
            super.paintComponents(g);
            g.setColor(new Color(255, 255, 255, 150));
            g.drawRect(0, 0, width, heigth);
        }
    }

    class MenuEnpause extends JPanel{
        BufferedImage arrierePlan ;
        JButton resume ;
        JButton restart ;
        JButton quit ;


        MenuEnpause(){
            // mettre le jeux en pause
            

            setSize(courtWidth/2, courtHeight);
            setLocation(courtWidth / 2 - this.getWidth() /2 , courtHeight / 2 - this.getHeight() / 2);
            setOpaque(false);
            setVisible(true);
            GameView.this.add(this) ;

            // definir image de fond :
            arrierePlan = ImageImport.getImage("ResumeScreen.png", courtWidth/2, courtHeight) ;

            // ajout des boutons

            resume = new BoutonMenu("Resume", 5* (Ball.ballRadius*2 + 10), 50);
            resume.setLocation(35-Ball.ballRadius,20);
            resume.setVisible(true);
            resume.addActionListener(e ->{
                court.setEnPause(false);
                this.setVisible(false);
                repaint();
            });
            add(resume);
            
            restart = new BoutonMenu("Restart", 5* (Ball.ballRadius*2 + 10), 50);
            restart.setLocation(35-Ball.ballRadius,20);
            restart.setVisible(true);
            restart.addActionListener(e ->{
                this.setVisible(false);
                court = new Court(courtWidth, courtHeight, niveau, controleur) ;
                repaint();
            });
            add(restart);

            quit = new BoutonMenu("Quit", 5* (Ball.ballRadius*2 + 10), 50);
            quit.setLocation(35-Ball.ballRadius,20);
            quit.setVisible(true);
            quit.addActionListener(e -> controleur.launchMenu());
            add(quit);


        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(arrierePlan, 0,0, this) ;
        }
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return width;
    }

    public int getCourtWidth() {
        return courtWidth;
    }

    public int getCourtHeight() {
        return courtHeight;
    }
    public void setSkin2(){
        court.setSkin2();
    }
}
