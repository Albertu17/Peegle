package Vue;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import Vue.MenuDetache.Menu;



public class Controleur extends JFrame{

    public MenuParametre menuParametre;
    public GameView gameview;
    public Menu menu;
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

    public void launchMenu(){
        if(menu == null){
            menu = new Menu(this);
            add(menu);
        }
        menu.setVisible(true);
    }


    public void launchGameview(){
        if (gameview == null){
            gameview = new GameView(this);
            add(gameview) ;
        }
        gameview.setVisible(true);
    }

    public void launchParametre(){
        if (menuParametre == null){
            menuParametre = new MenuParametre(this);
            add(menuParametre);
        }
        menuParametre.setVisible(true);
        this.repaint();
    }

    public void backMenuFromGameView(){
        gameview.setVisible(false);
        launchParametre();
        
    }


    public static void main(String[] args) {



        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                Controleur c = new Controleur() ;
                c.launchGameview();
                c.repaint();
            }

        });


    }
}
