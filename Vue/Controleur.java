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
            gameview = new GameView(this);
            add(gameview) ;
        }
        if (menu != null) menu.setVisible(false) ;
        gameview.setVisible(true);
    }



    public static void main(String[] args) {
        Controleur c = new Controleur() ;


        // decide de comment tu veux demarrer le jeux
        boolean menu = true ;

        if (menu){
            // lance le menu
            c.launchMenu();
        }else{
            // lance direct le jeu
            c.launchGameview();
        }


        c.repaint();

    }
}
