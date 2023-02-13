package Vue;
import javax.swing.JFrame;

import Vue.Menu.Menu;

public class Controleur extends JFrame{
    
    public GameView gameView ;
    public Menu menu ;
    Controleur(){
        // lancement de l'import des images 
        ImageImport.setImage(true);



        // setExtendedState(JFrame.MAXIMIZED_BOTH); 
        // setUndecorated(true);
        setSize(1000,1000);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Peggle");
        setVisible(true);
        setLocationRelativeTo(null);
        Menu m = new Menu(this);
        add(m);


    }



    public static void main(String[] args) {
        Controleur c = new Controleur() ;

    }
}
