import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Vue.Controleur;
import Vue.ImageImport;

public class SelectNiveau extends JPanel{

    private String[] allNameNiveau ;
    private int page_act ;
    private boolean campagne ;
    private Font font ;
    private Controleur controleur ;

    private Fleche next ;
    private Fleche previous ;

    SelectNiveau(Controleur c, boolean campagne){
        controleur = c ;
        allNameNiveau = getAllNameNiveau(campagne) ;
        try {
            InputStream targetStream = new FileInputStream("./Vue/Font/cartoonist_kooky.ttf");
            font =  Font.createFont(Font.TRUETYPE_FONT, targetStream);
        } catch (Exception e) {
            e.printStackTrace();
        }


        page_act = 0 ;
        
        next = new Fleche(true);
        previous = new Fleche(false) ;
        
        afficherPage(page_act);
    }
    class Fleche extends JButton{
        Icon imageBlanche;
        Icon imageJaune;

        Fleche(boolean next){
            super();
            imageBlanche = new ImageIcon(ImageImport.getImage("Menu/Fleche "+ (next?"D":"G") + " blanche.png", 150, 40));
            imageJaune = new ImageIcon(ImageImport.getImage("Menu/planche jaune.png", 150, 40));
            
            this.addMouseListener((MouseListener) new MouseAdapter() 
            {
                public void mouseEntered(MouseEvent evt) 
                {
                    Fleche.this.setIcon(imageJaune);
                }
                public void mouseExited(MouseEvent evt) 
                {
                    Fleche.this.setIcon(imageBlanche);
                }
                public void mouseClicked(MouseEvent evt) 
                {   
                    if (next) afficherPage(++page_act);
                    else afficherPage(--page_act);
                }
            });
        
        setIcon(imageBlanche);
        }
    }

    private void afficherPage(int page){
        // affichage icone plus bouton de selections
        String[] aafficher = getNamePage(page) ;
        for(int i = 0 ; i < aafficher.length ; i++){
            if (i < 3){

            }else{

            }
        }

        // affichage des boutons retour et suivant :
        next.setVisible(page > 0) ;
        previous.setVisible((page+1)*6 < allNameNiveau.length);

    }
    private String pathIcone(){
        return "IconeNiveau/"+ (campagne? "Campagne/" : "Perso/") ;
    }

    private String[] getNamePage(int page){
        int NbrElement = allNameNiveau.length - page*6 ;
        if (NbrElement > 6) NbrElement  =6 ;
        String[] ret = new String[NbrElement] ;
        for (int i = 0 ; i < ret.length ;i++){
            ret[i] = allNameNiveau[page*6 + i] ;
        }
        return ret ;
    }

    public String[] getAllNameNiveau(boolean campagne){
        return new File("Niveau/"+ (campagne? "Campagne" : "Perso")).list() ;
    }
    
    class PresNiveau extends JPanel{
        private BufferedImage apercu ;
        private ButtonBJ button ;

        PresNiveau(String nomNiveau){
            apercu = ImageImport.getImage(campagne? "Campagne/" : "Perso/" + nomNiveau, WIDTH, HEIGHT) ;

            button = new ButtonBJ(nomNiveau) ;
            this.add(button); 
            button.setVisible(true);
        }


     

        class ButtonBJ extends JButton{
            Icon imageBlanche;
            Icon imageJaune;

            ButtonBJ(String nomNiveau){
                super();
                imageBlanche = new ImageIcon(ImageImport.getImage("Menu/planche blanche.png", 150, 40));
                imageJaune = new ImageIcon(ImageImport.getImage("Menu/planche jaune.png", 150, 40));
                
                this.addMouseListener((MouseListener) new MouseAdapter() 
                {
                    public void mouseEntered(MouseEvent evt) 
                    {
                        ButtonBJ.this.setIcon(imageJaune);
                    }
                    public void mouseExited(MouseEvent evt) 
                    {
                        ButtonBJ.this.setIcon(imageBlanche);
                    }
                    public void mouseClicked(MouseEvent evt) 
                    {   
                        controleur.launchGameview(nomNiveau);
                    }
                });
            
            setIcon(imageBlanche);
            }
        }

    }

    public static void main(String[] args){
        Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize(); 
        int hauteur = (int)tailleEcran.getHeight(); 
        int largeur = (int)tailleEcran.getWidth();
        SelectNiveau sn  =new SelectNiveau(new Controleur(), true) ;
        JFrame frame = new JFrame() ;
        frame.setSize(tailleEcran);
        frame.setVisible(true);
        frame.add(sn) ;
        sn.setVisible(true);
    }
}
