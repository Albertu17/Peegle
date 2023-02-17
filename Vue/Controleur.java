package Vue;
import javax.swing.JFrame;

import Vue.Menu.Menu;

public class Controleur extends JFrame{
    
    public GameView gameview ;
    public Menu menu ;
    Controleur(){

        // lancement de l'import des images 
        ImageImport.setImage(true);


        // mise en pleine ecran
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setUndecorated(true);
       
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Peggle");

        // empeche l'utilisateur de resize la fenetre :
        setResizable(false);
       
        setVisible(true);
    }

    void launchMenu(){
        menu = new Menu(this) ;
        add(menu) ;
        menu.setVisible(true);
    }

    void launchGameview(){
        gameview = new GameView(this);
        add(gameview) ;
        gameview.setVisible(true);
    }



    public static void main(String[] args) {
        Controleur c = new Controleur() ;
        // lance le menu
            // c.launchMenu();
            
        // lance direct le jeu
            c.launchGameview();

        c.repaint();

    }
}
