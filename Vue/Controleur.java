package Vue;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Controleur extends JFrame{
    
    public GameView gameview;
    public int width;
    public int height;

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
    }

    // override pour prendre l'attribut width de cette classe et pas celui de la frame (plus stable).
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


    public void launchGameview(String nomNiveau){
        gameview = new GameView(this, nomNiveau);
        if (gameview == null){
            add(gameview) ;
        }
        gameview.setVisible(true);
    }



    public static void main(String[] args) {



        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                Controleur c = new Controleur() ;
                c.launchGameview("Triangle");
                c.repaint();
                
            }

        });


    }
}
