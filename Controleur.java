import javax.swing.JFrame;

public class Controleur extends JFrame{
    
    Controleur(){
        setSize(600,700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
    }

    public void launchJeux(){
        
    }


    public static void main(String[] args) {
        new Controleur() ;
    }
}
