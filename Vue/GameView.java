package Vue;

import Vue.Menu.BoutonMenu;
import Vue.Menu.Menu;
import java.awt.Color;
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
