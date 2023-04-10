import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StreamCorruptedException;
import java.io.FileInputStream;

import javax.management.PersistentMBean;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.xml.transform.Templates;

import Vue.Controleur;
import Vue.ImageImport;

public class SelectNiveau extends JPanel{

    private String[] allNameNiveau ;
    private int page_act ;
    private boolean campagne ;
    private Font font ;
    private Controleur controleur ;

    private JPanel[] affichage ;
    private BufferedImage background ;

    private Fleche next ;
    private Fleche previous ;

    SelectNiveau(Controleur c, boolean campagne){
        controleur = c ;
        setBounds(0, 0, controleur.getWidth(), controleur.getHeight());
        c.add(this) ;
        this.setVisible(true); 
        this.setLayout(null);
        
        // setImage background
        background = ImageImport.getImage("Menu/menuBackground.jpg", this.getWidth(), this.getHeight());

        this.campagne = campagne ;
        allNameNiveau = getAllNameNiveau(campagne) ;

        // import de la police 
        try {
            InputStream targetStream = new FileInputStream("./Vue/Font/cartoonist_kooky.ttf");
            font =  Font.createFont(Font.TRUETYPE_FONT, targetStream);
            font = font.deriveFont(25f);
            // font.setFont(font.deriveFont(font.getStyle() | Font.BOLD)); //mets en gras
        } catch (Exception e) {
            System.out.println("ereur font");
            e.printStackTrace();
        }

        page_act = 0 ;

        int largeurFleche = 45 ;
        int hauteurFleche = 50 ;
        int x = this.getWidth()-100 ;
        int y = this.getHeight()-100 ;
        // définir place des boutons 
            next = new Fleche(true, largeurFleche, hauteurFleche);
            previous = new Fleche(false, largeurFleche, hauteurFleche) ;
            next.setBounds( x, y, largeurFleche, hauteurFleche);
            previous.setBounds( x - (next.getWidth() + 15) , y, largeurFleche, hauteurFleche);
            this.add(previous);
            this.add(next) ;
        afficherPage(page_act);
    }
    class Fleche extends JButton{
        Icon imageBlanche;
        Icon imageJaune;

        Fleche(boolean next, int width, int height){
            imageBlanche = new ImageIcon(ImageImport.getImage("Menu/Fleche/"+ (next?"D":"G") + " blanche.png", width, height));
            imageJaune = new ImageIcon(ImageImport.getImage("Menu/Fleche/"+ (next?"D":"G") + " jaune.png", width, height));
            // enlever tout les contours du JButton ne laisse que l'image
                this.setBorderPainted(false); 
                this.setContentAreaFilled(false); 
                this.setFocusPainted(false); 
                this.setOpaque(false);
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

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, this);
    }

    private void afficherPage(int page){
        if (affichage != null) for (JPanel	pan : affichage) if (pan != null) this.remove(pan); //nettoye les anciens images et texte
        affichage  = new JPanel[6] ;

        int espacementLargeurPres = this.getWidth()/13 ;
        int largeurPres = espacementLargeurPres*3 ;
        int milieu  = this.getHeight() /2 ;
        int hauteur_pres  = milieu - 150 ;
        int x,y ;

        // affichage icone plus bouton de selections
        String[] aafficher = getNamePage(page) ;
        for(int i = 0 ; i < aafficher.length ; i++){
            affichage[i] = new PresNiveau(aafficher[i], largeurPres, hauteur_pres) ;
            x = (1+(i%3)*4)*espacementLargeurPres ;
            if (i < 3) y = milieu - hauteur_pres -25 ;
            else y  = milieu +25 ;
            affichage[i].setBounds(x, y, largeurPres, hauteur_pres); 
            this.add(affichage[i]) ;
            affichage[i].setVisible(true);
        }

        // affichage des boutons retour et suivant :
        previous.setVisible(page > 0) ;
        next.setVisible((page+1)*6 < allNameNiveau.length);

        this.repaint();
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
        String[] atraiter = new File("Niveau/"+ (campagne? "Campagne" : "Perso")).list() ;
        String[] ret = new String[atraiter.length] ;
        for (int i = 0 ; i < ret.length ; i++){
            ret[i] = atraiter[i].substring(0, atraiter[i].length() -5) ; //enlever l'extension .pegs
        }
        return ret ;
    }
    
    class PresNiveau extends JPanel{
        private BufferedImage apercu ;
        private ButtonBJ button ;

        PresNiveau(String nomNiveau, int largeurPres, int hauteur_pres){
            setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
            int hauteurBouton  = 40 ; 
            apercu = ImageImport.getImage(pathIcone()+nomNiveau+".png", largeurPres, hauteur_pres - hauteurBouton-10) ;
            Component invisiblePhoto = Box.createRigidArea(new Dimension(0, hauteur_pres - hauteurBouton-7)) ;
            this.add(invisiblePhoto); //ajout d'un bloc pour l'image et l'affichage du layout
            button = new ButtonBJ(nomNiveau, hauteurBouton) ;
            this.add(Box.createRigidArea(new Dimension((largeurPres-button.getWidth())/2, 0))); //ajout d'un bloc pour l'image et l'affichage du layout
            this.add(button); 


            button.setVisible(true);
            this.setOpaque(false);
            (invisiblePhoto).addMouseListener((MouseListener) new MouseAdapter() 
                {
                    public void mouseEntered(MouseEvent evt) 
                    {
                        button.setIcon(button.imageJaune);
                    }
                    public void mouseExited(MouseEvent evt) 
                    {
                        button.setIcon(button.imageBlanche);
                    }
                    public void mouseClicked(MouseEvent evt) 
                    {   
                        // TODO mettre ouverture de la partie
                        System.out.print("lancera la partie : ");
                        System.out.println((campagne? "Campagne/" : "Perso/") + nomNiveau);
                        controleur.remove(SelectNiveau.this); //TODO faire un truc dans le controleur
                        controleur.launchGameview((campagne? "Campagne/" : "Perso/") + nomNiveau);
                    }
                });
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // afficher background
            g.drawImage(apercu, 0, 0, apercu.getWidth(), apercu.getHeight(), this);
        }
     

        class ButtonBJ extends JButton{
            Icon imageBlanche;
            Icon imageJaune;

            ButtonBJ(String nomNiveau, int hauteur){
                // super();

                BufferedImage imageBTemp  = ImageImport.getImage("Menu/planche blanche.png", 100, 100) ;
                Graphics g = imageBTemp.getGraphics();
                FontMetrics metrics = (g).getFontMetrics(font);
                int largeurtexte = metrics.stringWidth(nomNiveau) ;
                int hauteurtexte = metrics.getHeight() ;
                int largeur = largeurtexte+ (hauteur - hauteurtexte)/2 ;
               

                imageBTemp  = ImageImport.getImage("Menu/planche blanche.png", largeur, hauteur) ;
                BufferedImage imageJTemp  = ImageImport.getImage("Menu/planche jaune.png", largeur, hauteur);

                setSize(largeur, hauteur);
                
                // ajouter le nom de la partie sur l'image bouton 
                //TODO selectionner la bonne font et ajuster la taille de la font ainsi que positionnenment
                // blanc

                    g = imageBTemp.getGraphics();
                    g.setFont(font);
                    g.setColor(Color.WHITE);
                    g.drawString(nomNiveau, (largeur - largeurtexte)/2, (hauteur - hauteurtexte)/2+hauteurtexte);
                    
                    // jaune
                    g = imageJTemp.getGraphics();
                    g.setFont(font);
                    g.setColor(Color.YELLOW);
                    g.drawString(nomNiveau, (largeur - largeurtexte)/2, (hauteur - hauteurtexte)/2+hauteurtexte);


                imageBlanche =  new ImageIcon(imageBTemp);
                imageJaune =  new ImageIcon(imageJTemp) ;

                // enlever tout les contours du JButton ne laisse que l'image
                    this.setBorderPainted(false); 
                    this.setContentAreaFilled(false); 
                    this.setFocusPainted(false); 
                    this.setOpaque(false);
                setIcon(imageBlanche);
                
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
                        // TODO mettre ouverture de la partie
                        System.out.print("lancera la partie : ");
                        System.out.println((campagne? "Campagne/" : "Perso/") + nomNiveau);
                        controleur.remove(SelectNiveau.this); //TODO faire un truc dans le controleur
                        controleur.launchGameview((campagne? "Campagne/" : "Perso/") + nomNiveau);
                    }
                });
            
            }
        }

    }

    public static void main(String[] args){

        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                Controleur c  = new Controleur() ;
                // c.launchMenu(new SelectNiveau(c, true));
                SelectNiveau sn  = new SelectNiveau(c, true) ;
                
            }

        });
    }
}
