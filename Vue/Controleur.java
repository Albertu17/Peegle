package Vue;
import javax.swing.JFrame;

import Vue.Menu.Menu;

public class Controleur extends JFrame{
    
    public GameView gameview ;
    public Menu menu ;
    Controleur(){
        // lancement de l'import des images 
        ImageImport.setImage(true);



        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setUndecorated(true);
        
        // setSize(1000,1000);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Peggle");
        setVisible(true);
        // setLocationRelativeTo(null);

        // empeche l'utilisateur de resize la fenetre :
        // setResizable(false);
        
        

        System.out.println(this.getSize());
        repaint();


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
        // c.launchMenu();
        c.launchGameview();


        // c.pack();
    }
}
