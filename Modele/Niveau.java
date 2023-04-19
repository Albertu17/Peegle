package Modele;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;
import Vue.ImageImport;

public class Niveau {

    private static String dosierSauvegarde ="Niveau/" ;
    private static String nomExtension = ".pegs" ;

    private ArrayList<Pegs> pegs;
    private int nbBillesInitiales;
    private int score1Etoile;
    private int score2Etoiles;
    private int score3Etoiles;
    private boolean campagne ;
    private String nom;

    
    public ArrayList<Pegs> getPegs() {
        return pegs;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public void setPegs(ArrayList<Pegs> pegs) {
        this.pegs = pegs;
    }
    public void isCampagne(boolean campagne) {
        this.campagne = campagne;
    }

    public String getNom() {
        return nom;
    }
    private String getDossier(){
        return (campagne ? "Campagne/" : "Perso/" )+getNom();
    }


    public Niveau (String nom) {
        this.nom = nom;
        pegs = new ArrayList<>() ;
    }


    public static Niveau NiveauAleatoire(int widthCourt, int heightCourt, int radiusBall, int diametrePegs){
        Niveau nv = new Niveau("Aleatoire") ;
        int nbrPegs = randInt(60, 200); //aproximatif
        int espaceMinEntre2Pegs = (int) 2.5*radiusBall ;
        int x,y ;

        int debutHeight = heightCourt/4 ; //evite d'avoir des balels trop hautes
        
        // segemente l'aire de jeux en carré le plus petit possible tel que les contraintes soit respecté
        int nbrSegW  = (int) (widthCourt / (espaceMinEntre2Pegs + diametrePegs)  );
        int nbrSegH  = (int) ((heightCourt-debutHeight) / (espaceMinEntre2Pegs + diametrePegs)  );

        int nbrSegParPegs = (int) (((nbrSegH-1)*(nbrSegW-1))/nbrPegs) ; // Nombre de segmentation pour chaque pegs, permet de savoir la probabilité d'avoir un pegs dans ce carré

        for (int w = 0 ; w < nbrSegW -1 ; w++){
            for(int h = 0 ; h < nbrSegH -1; h++ ){
                if (randInt(1, nbrSegParPegs) == 1){ // placer un élément au hasard
                    x=  (int) ( (w+0.5)*(widthCourt/(double)nbrSegW) ) ;
                    y=  (int) ( (h+0.5)*((heightCourt-debutHeight)/(double)nbrSegH)  + debutHeight) ;
                    nv.pegs.add(new Pegs(x, y, diametrePegs, randInt(1, 4))) ;
                }
            }
        }

        // TODO determiner le scoreEtoiles et nombres de balles initials

        return nv ;
    }

    public static int randInt(int a, int b){
        return (new SecureRandom()).nextInt(b-a +1) +a ;
    }
    
    public static void createIconeNiveau(String niveau, boolean campagne){
        int width = 1080 ;
        int height = 520;

        Niveau nv = importPegles((campagne? "Campagne/" : "Perso/") + niveau, width, height) ;
        BufferedImage tempImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = (tempImage.createGraphics());
        Graphics2D g2d = (Graphics2D) g;      
        g2d.setColor(Color.white);
        g2d.fill(new RoundRectangle2D.Float(0, 0, width, height, 60, 60));

        
        for (Pegs peg:nv.getPegs()) {
            g2d.drawImage(ImageImport.getImage(peg.getImageString()), peg.getX(), peg.getY(), peg.getDiametre(), peg.getDiametre(), null);
        }
        try{
            ImageIO.write(tempImage, "png", new File("Vue/Image/IconeNiveau/" +(nv.campagne? "Campagne/" : "Perso/")+ nv.getNom() +".png"));
        }catch(Exception ex){
            System.out.println("Impossible d'enregistrer l'image.");
            System.out.println(ex);
        }

        // ajout dans ImportImage si actif
        if (ImageImport.isAtif()){
            ImageImport.addImage("IconeNiveau/"+nv.getDossier()+".png");
        } 

    }
    
    public static void main(String[] args) {
        ImageImport.setImage(false);
        boolean campagne =true ;
        for(String name : new File("Niveau/"+ (campagne? "Campagne" : "Perso")).list()){
            name  = name.substring(0, name.length() -5) ;
            createIconeNiveau(name, campagne);
        }
        campagne =false ;
        for(String name : new File("Niveau/"+ (campagne? "Campagne" : "Perso")).list()){
            name  = name.substring(0, name.length() -5) ;
            createIconeNiveau(name, campagne);
        }
    }


    public static List<String> getAllNameNiveau(boolean campagne){
        String[] atraiter = new File(dosierSauvegarde+ (campagne? "Campagne" : "Perso")).list() ;
        List<String> ret = new ArrayList<>() ;
        for (int i = 0 ; i < atraiter.length ; i++){
            ret.add(atraiter[i].substring(0, atraiter[i].length() -5) ); //enlever l'extension .pegs
        }
        return ret ;
    }
    public static List<String> getAllNameNiveau(){
        List<String> tout  = getAllNameNiveau(true) ;
        tout.addAll(getAllNameNiveau(false) );
        return tout ;
    }

    // enregistrement d'un niveau    

    public void save(int widthCourt, int heightCourt){
        // save les lignes de l'array list dans un fichier csv
        PrintWriter file;
        try {
            file = new PrintWriter(dosierSauvegarde + getDossier() + nomExtension);
    
            // premiere ligne d'info :
            String ligne  = String.valueOf(widthCourt) +";"
                                + String.valueOf(heightCourt) +";"
                                + String.valueOf(nbBillesInitiales) +";"
                                + String.valueOf(score1Etoile) +";"
                                + String.valueOf(score2Etoiles) +";"
                                + String.valueOf(score3Etoiles) +";"
                                + String.valueOf(campagne ? "1" : "0") ;
            file.println(ligne);
            
    
            // remplacer par les valeurs à enregistrer
            for (Pegs peg : pegs){
                ligne  = String.valueOf(peg.getX()) +";" 
                            + String.valueOf(peg.getY()) +";" 
                            + String.valueOf(peg.getDiametre()) +";" 
                            + String.valueOf(peg.getCouleur());
                file.println(ligne);
    
            }
            file.close();
            // enregistrement réussi
        } catch (FileNotFoundException e) {
            System.out.println("La sauvegarde a raté");
            e.printStackTrace();
        }

        // update l'icone du niveau
        createIconeNiveau(this.getNom(), this.campagne); 
    }
    
    public static Niveau importPegles(String name, int widthCourt, int heightCourt){
        Niveau nv = new Niveau(name.split("/")[name.split("/").length -1]) ;//permet de récupéré uniquement le nom et pas le chemin d'accés

       try (Scanner save = new Scanner(new File(dosierSauvegarde + name + nomExtension))) {
        String[] line = save.nextLine().split(";") ;
           
           // obtenir les valeurs de réajustement des des pegs pour qu'il s'adepete à la nouvelle taille de l'écran 
           double reajustementH = widthCourt / Double.valueOf(line[0]);
           double reajustementV = heightCourt /Double.valueOf(line[1]);

            // remise des valeurs de Niveau :
            nv.nbBillesInitiales = Integer.valueOf(line[2]);
            nv.score1Etoile = Integer.valueOf(line[3]);
            nv.score2Etoiles = Integer.valueOf(line[4]);
            nv.score3Etoiles = Integer.valueOf(line[5]);
            nv.campagne = line[6].equals("1") ? true : false ;


           // creation des pegs en fonction des infos que on a 
          // update des nouveau coordonnées en fonction de la taille de l'écran actuel
           while(save.hasNextLine()){
                line = save.nextLine().split(";") ;

                int x =  (int) (reajustementH*Double.valueOf(line[0]) );
                int y =  (int) (reajustementV*Double.valueOf(line[1]) );
                int radius = (int)(Double.valueOf(line[2]) * Math.min(reajustementH, reajustementV) ) ;
                int couleur = Integer.valueOf(line[3]) ;

                nv.pegs.add(new Pegs(x, y, radius, couleur)) ;

            }

            save.close();
        } catch (NumberFormatException | FileNotFoundException e) {
            System.out.println("Le nom de fichier ne coorespond pas à un fichier existant");
            e.printStackTrace();
        }

       // return un objet de type 
        return nv ;
    }
    public static List<String> RechercheNameNiveau(boolean campagne2, String nom2) {
        List<String> ret = new ArrayList<>() ;
        for (String name : getAllNameNiveau(campagne2)){
            
            if ((name.toLowerCase()).contains(nom2.toLowerCase())){
                ret.add(name);
            }
        }
        return ret ;
    }

}
