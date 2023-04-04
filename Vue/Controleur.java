package Vue;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import Vue.Menu.Menu;

public class Controleur extends JFrame{
    
    public GameView gameview;
    public Menu menu;
    public int width;
    public int height;

    Controleur(){

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

    public void launchMenu(){
        if (menu == null){
            menu = new Menu(this) ;
            add(menu) ;
        }
        if (gameview != null ) gameview.setVisible(false) ;
        menu.setVisible(true);
    }

    public void launchGameview(){
        if (gameview == null){
            gameview = new GameView(this, true);
            add(gameview);
        }
        if (menu != null) menu.setVisible(false);
        gameview.setVisible(true);
    }



    public static void main(String[] args) {

        // décide du démrrage avec ou sans menu
        boolean menu = false ;

        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                Controleur c = new Controleur() ;

                if (menu){
                    // lance le menu
                    c.launchMenu();
                }else{
                    // lance direct le jeu
                    // c.launchGameview();
                    EditeurNiveaux e = new EditeurNiveaux(c);
                    c.add(e);

                }
                c.repaint();
            }
        });
    }
}
