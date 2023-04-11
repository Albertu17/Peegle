package Vue;
import java.awt.Dimension;
import java.awt.Container;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import Vue.Menu.*; 

public class Controleur extends JFrame{
    
    public GameView gameview;
    public int width;
    public int height;
    public Container container;

    public Controleur(){

        // lancement de l'import des images 
        ImageImport.setImage(true); 

        // mise en pleine ecran
        // setExtendedState(JFrame.MAXIMIZED_BOTH); 
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize(); // Récupère taille de l'écran utilisateur.
        width = (int) size.getWidth();
        height = (int) size.getHeight();
        setSize(width, height); // Met la fenêtre en plein écran.
        setUndecorated(true);
       
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Peggle");

        // empeche l'utilisateur de resize la fenetre :
        setResizable(false);
       
        setVisible(true);
        container = getContentPane();
        container.setLayout(null);
    }

    // override pour prendre l'attribut width de cette classe et pas celui de la frame (plus stable).
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


    public void launchGameview(String nomNiveau){
        this.getContentPane().removeAll();
        this.setLayout(null);
        gameview = new GameView(this, nomNiveau);
        if (gameview != null){
            add(gameview) ;
        }
        gameview.setVisible(true);
        this.repaint();
    }
    // public void launchMenu(){
    //     launchMenu(new Menu(this));
    // }
    public void launchMenu(JPanel menu){
        this.getContentPane().removeAll();
        this.setLayout(null);
        if (menu != null){
            add(menu) ;
        }
        menu.setVisible(true);
        this.repaint();
    }



    public static void main(String[] args) {



        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                Controleur c = new Controleur() ;
                c.launchMenu(new SelectNiveau(c, true));
                c.repaint();
                
            }

        });


    }
}
