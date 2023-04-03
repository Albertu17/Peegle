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
        setBackground(Color.BLACK);

        
        courtWidth = width - 200;
        courtHeight = heigth - 200;
        
        // Court
        court = new Court(courtWidth, courtHeight, "Triangle"); // spécifier le niveau à importer
        court.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        court.setBounds((width-courtWidth)/2, (heigth-courtHeight)/2, courtWidth, courtHeight);
        court.setVisible(true);
        Background background = new Background("test.jpg", court);
        background.setBounds(0, 0, width, heigth);
        background.setOpaque(false);
        add(court);
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

}
