package Vue;


import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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

    JButton back;
    Icon imageBack;

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
        
        // Court
        court = new Court(courtWidth, courtHeight, nomNiveau); // spécifier le niveau à importer
        court.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        court.setBounds((width-courtWidth)/2, (heigth-courtHeight)/2, courtWidth, courtHeight);
        court.setVisible(true);
        Background background = new Background("test.jpg", court,heigth,width);
        background.setBounds(0, 0, width, heigth);
        background.setOpaque(false);
        add(court);
        add(background);
    
        imageBack = new ImageIcon(ImageImport.getImage("back.png"));
        Image image = ((ImageIcon) imageBack).getImage(); // transform it 
        Image newimg = image.getScaledInstance(200, 50,  java.awt.Image.SCALE_SMOOTH);
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
                controleur.launchMenu(new Menu(controleur));
                setSkin2();
            }
        });
        add(back);
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
