package Vue.Menu;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.List;
import java.io.FileInputStream;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import Modele.*;
import Vue.ControleurVThibault;
import Vue.ImageImport;

public class SelectNiveau extends JPanel{

    private List<String> allNameNiveau ;
    private int page_act ;
    private boolean campagne ;
    private Font font ;
    private ControleurVThibault controleur ;

    private JPanel[] affichage ;
    private BufferedImage background ;

    private Fleche next ;
    private Fleche previous ;

    public SelectNiveau(ControleurVThibault c, boolean campagne){
        controleur = c ;
        setBounds(0, 0, controleur.getWidth(), controleur.getHeight());
        c.add(this) ;
        this.setVisible(true); 
        this.setLayout(null);
        
        // setImage background
        background = ImageImport.getImage("Menu/menuBackground.jpg", this.getWidth(), this.getHeight());

        this.campagne = campagne ;
        allNameNiveau = Niveau.getAllNameNiveau(campagne) ;

        // import de la police 
        try {
            InputStream targetStream = new FileInputStream("./Vue/Font/cartoonist_kooky.ttf");
            font =  Font.createFont(Font.TRUETYPE_FONT, targetStream);
            font = font.deriveFont(26f);
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
        next.setVisible((page+1)*6 < allNameNiveau.size());

        this.repaint();
    }

    private String pathIcone(){
        return "IconeNiveau/"+ (campagne? "Campagne/" : "Perso/") ;
    }

    private String[] getNamePage(int page){
        int NbrElement = allNameNiveau.size() - page*6 ;
        if (NbrElement > 6) NbrElement  =6 ;
        String[] ret = new String[NbrElement] ;
        for (int i = 0 ; i < ret.length ;i++){
            ret[i] = allNameNiveau.get(page*6 + i) ;
        }
        return ret ;
    }

    class PresNiveau extends JPanel{
        private BufferedImage apercu ;
        private BufferedImage cadre ;
        private ButtonBJ button ;

        PresNiveau(String nomNiveau, int largeurPres, int hauteur_pres){
            setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
            int hauteurBouton  = 40 ; 
            apercu = ImageImport.getImage(pathIcone()+nomNiveau+".png", largeurPres, hauteur_pres - hauteurBouton-10) ;
            cadre = ImageImport.getImage("Menu/CadreCampagne.png", largeurPres, hauteur_pres - hauteurBouton-10) ;
            JPanel invisiblePhoto = new JPanel() ;
            invisiblePhoto.setSize(largeurPres, hauteur_pres - hauteurBouton-7);
            invisiblePhoto.setOpaque(false);
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
                        controleur.launchGameview((campagne? "Campagne/" : "Perso/") + nomNiveau);
                    }
                });
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // afficher background
            g.drawImage(apercu, 0, 0, apercu.getWidth(), apercu.getHeight(), this);
            g.drawImage(cadre, 0, 0, cadre.getWidth(), cadre.getHeight(), this);
        }
     

        class ButtonBJ extends JButton{
            Icon imageBlanche;
            Icon imageJaune;

            ButtonBJ(String nomNiveau, int hauteur){
                BufferedImage imageBTemp  = ImageImport.getImage("Menu/planche blanche.png", 100, 100) ;
                Graphics g = imageBTemp.getGraphics();
                FontMetrics metrics = (g).getFontMetrics(font);
                int largeurtexte = metrics.stringWidth(nomNiveau) ;
                int hauteurtexte = metrics.getAscent() ;
                int largeur = largeurtexte+ (hauteur - hauteurtexte)/2 ;
               

                imageBTemp  = ImageImport.getImage("Menu/planche blanche.png", largeur, hauteur) ;
                BufferedImage imageJTemp  = ImageImport.getImage("Menu/planche jaune.png", largeur, hauteur);

                setSize(largeur, hauteur);
                
                // ajouter le nom de la partie sur l'image bouton 
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
                ControleurVThibault c  = new ControleurVThibault() ;
                c.launchMenu(new SelectNiveau(c, true));                
            }

        });
    }
}
