package Vue;


import javax.swing.JFrame;

import Modele.Niveau;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.*;
import javax.swing.*;

public class GameView extends JPanel {

    public Controleur controleur ;

    private int width;
    private int heigth;

    // Court
    Court court;
    private int courtWidth;
    private int courtHeight;

    GameView(Controleur c) {

        this.controleur = c ;

        width = controleur.getWidth();
        heigth = controleur.getHeight();
        setSize(width, heigth);
        setLayout(null);
        setVisible(true); 

        
        courtWidth = width - 200;
        courtHeight = heigth - 200;
        
        // Court
        court = new Court(courtWidth, courtHeight, "aleatoire"); // spécifier le niveau à importer
        court.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        court.setBounds((width-courtWidth)/2, (heigth-courtHeight)/2, courtWidth, courtHeight);
        court.setVisible(true);
        add(court);
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

}
