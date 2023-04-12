package Vue;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import Vue.Menu.*;

public class Controleur extends JFrame{

    public MenuParametre menuParametre;
    public GameView gameview;
    public Menu menu;
    public SelectNiveau selectNiveau ;
    public int width;
    public int height;

    public Controleur() {

        // lancement de l'import des images 
        ImageImport.setImage(true); 

        // mise en pleine ecran
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

    public void removeAll(){
        this.getContentPane().removeAll();
        this.setLayout(null);
    }

    public void launchMenu(){
        removeAll();
        if(menu == null){
            menu = new Menu(this);
        }
        add(menu);
        menu.setVisible(true);
        this.repaint();
    }

    public void launchParametre(){
        removeAll();
        if (menuParametre == null){
            menuParametre = new MenuParametre(this);
        }
        add(menuParametre);
        menuParametre.setVisible(true);
        this.repaint();
    }

    public void launchSelectNiveau(boolean campagne){
        removeAll();
        selectNiveau = new SelectNiveau(this, campagne);
        add(selectNiveau);
        selectNiveau.setVisible(true);
        this.repaint();
    }
    
    public void backMenuFromGameView(){
        gameview.setVisible(false);
        launchParametre();
        this.repaint();
    }
    
    public void launchGameview(String nomNiveau){
        this.removeAll();
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
        this.removeAll();
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
                //c.launchMenu();
                //c.repaint();
                // Pour accéder à l'éditeur de niveaux pour l'instant:
                c.add(new EditeurNiveaux(c.width, c.height));
            }
        });
    }
}
