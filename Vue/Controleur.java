package Vue;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import Modele.Niveau;
import Vue.Menu.*;

public class Controleur extends JFrame{

    public MenuParametres menuParametres;
    public GameView gameview;
    public EditeurNiveaux editeurNiveaux ;
    public Menu menu;
    public SelectNiveau selectNiveau ;
    public int width;
    public int height;

    public Controleur() {

        // lancement de l'import des images 
        ImageImport.setImage(true); 

        // mise en pleine écran
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize(); // Récupère taille de l'écran utilisateur.
        Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
        width = (int) size.getWidth();
        height = (int) size.getHeight() - insets.top; // Recoupe la taille du bandeau d'options sur mac.
        setSize(width, height); // Met la fenêtre en plein écran.

        // Paramétrages supplémentaires de la fenêtre
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Peggle");
        setUndecorated(true);
        setResizable(false); // empêche l'utilisateur de resize la fenêtre.
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
    public void setFocusClavier(JPanel panel){
        panel.setFocusable(true);
        panel.requestFocusInWindow() ;
    }

    public void launchMenu(){
        removeAll();
        if(menu == null){
            menu = new Menu(this);
        }
        add(menu);
        menu.setVisible(true);
        menu.requestFocusInWindow() ;
        this.repaint();
    }

    public void launchParametres(){
        removeAll();
        if (menuParametres == null){
            menuParametres = new MenuParametres(this);
        }
        add(menuParametres);
        menuParametres.setVisible(true);
        menuParametres.requestFocusInWindow() ;
        this.repaint();
    }

    public void launchEditeurNiveaux(){
        removeAll();
        if (editeurNiveaux == null){
            editeurNiveaux = new EditeurNiveaux(this);
        }
        add(editeurNiveaux);
        editeurNiveaux.setVisible(true);
        editeurNiveaux.requestFocusInWindow(); 
        this.repaint();
    }

    public void launchSelectNiveau(){
        removeAll();
        selectNiveau = new SelectNiveau(this);
        add(selectNiveau);
        selectNiveau.setVisible(true);
        this.repaint();
    }
    
    public void backMenuFromGameView(){
        gameview.setVisible(false);
        launchParametres();
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
        setFocusClavier(gameview.court);
    }

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
                c.launchMenu();
            }
        });
    }

    public void setNiveauSuivant() {
        String nomNiveau = Niveau.getNiveauSuivant();
        if (nomNiveau != null){
            launchGameview("Campagne/" + nomNiveau);
        }
        else{
            launchGameview("");
            gameview.court.askReset();
        }
    }
}
