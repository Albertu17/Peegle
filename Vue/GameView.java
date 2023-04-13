package Vue;

import Vue.Menu.Menu;
import java.awt.Color;
import javax.swing.*;

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
        court = new Court(courtWidth, courtHeight, niveau);
        court.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        court.setBounds((width-courtWidth)/2, (heigth-courtHeight)/2, courtWidth, courtHeight);
        court.setVisible(true);
        add(court);

        // Background
        Background background = new Background("test.jpg", court,heigth,width);
        background.setBounds(0, 0, width, heigth);
        background.setOpaque(false);
        add(background);

        // JButton bouton retour
        btnRetour = new Menu.BoutonMenu(Menu.BoutonMenu.getImageIcon("back.png"));
        btnRetour.setBounds(40,40,200,50);
        btnRetour.addActionListener(e -> controleur.launchMenu());
        add(btnRetour);
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
