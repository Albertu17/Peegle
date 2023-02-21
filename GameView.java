

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.*;
import javax.swing.*;



public class GameView extends JFrame {

    private int width;
    private int heigth;

    // Court
    Court court;
    private int courtWidth;
    private int courtHeight;

    GameView(int w,int h) {

        ImageImport.setImage(true);
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize(); // Récupère taille de l'écran utilisateur.
        heigth = (int) size.getHeight();
        width = (int) size.getWidth();
        courtWidth = width - 200;
        courtHeight = heigth - 200;
        setSize(width, heigth);
        setLayout(null); // Layout null pour placer les éléments selon les coordonnées
        setTitle("Peegles");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        // Court
        court = new Court(courtWidth, courtHeight);
        court.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        court.setBounds(50, 50, courtWidth, courtHeight);
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


    public static void main(String[] args) {

        // GameView g = new GameView(500,500);
        //    GameView g = new GameView(500,500);
        EditeurNiveaux eN = new EditeurNiveaux(500, 500);
       
    }
}
