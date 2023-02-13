package Vue.Menu;

import java.awt.Color;

import javax.swing.JPanel;
import Vue.Controleur;

public class Menu extends JPanel {
    

    Controleur controleur ;
    MenuPrincipal menuPrincipal;

    public Menu(Controleur controleur){
        this.controleur = controleur ;
        setVisible(true);
        menuPrincipal = new MenuPrincipal(controleur);
        add(menuPrincipal);

    }

}
