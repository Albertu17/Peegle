package Modele;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.DrbgParameters.Capability;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.text.StyledEditorKit.BoldAction;

import java.util.ArrayList;

public class Niveau {

    private static String dosierSauvegarde ="Niveau/" ;
    private static String nomExtension = ".pegs" ;

    private ArrayList<Pegs> pegs;

    public ArrayList<Pegs> getPegs() {
        return pegs;
    }

    public void setPegs(ArrayList<Pegs> pegs) {
        this.pegs = pegs;
    }

    private final String nom;
    private int nbBillesInitiales;
    private int score1Etoile;
    private int score2Etoiles;
    private int score3Etoiles;
    private boolean campagne; 

    public Niveau (String nom) {
        this.nom = nom;
        campagne = false ;
        pegs = new ArrayList<>() ;
    }


    private String pathDossier(){
        return dosierSauvegarde + (campagne ? "Campagne" : "Perso"  )+"/";
    }
    

    public void save(int widthCourt, int heightCourt){
        // save les lignes de l'array list dans un fichier csv
        
    
        PrintWriter file;
        try {
            file = new PrintWriter(pathDossier()  + nom + nomExtension);
    
            // premiere ligne d'info :
            String ligne  = String.valueOf(widthCourt) +";"
                                + String.valueOf(heightCourt) +";"
                                + String.valueOf(nbBillesInitiales) +";"
                                + String.valueOf(score1Etoile) +";"
                                + String.valueOf(score2Etoiles) +";"
                                + String.valueOf(score3Etoiles) +";"
                                + (campagne? "1":"0" );
            file.println(ligne);
            
    
            // remplacer par les valeurs à enregistrer
            for (Pegs peg : pegs){
                ligne  = String.valueOf(peg.getX()) +";" 
                            + String.valueOf(peg.getY()) +";" 
                            + String.valueOf(peg.getRadius()) +";" 
                            + String.valueOf(peg.getCouleur()) ; ;
                file.println(ligne);
    
            }
            file.close();
            // enregistrement réussi
        } catch (FileNotFoundException e) {
            System.out.println("LA sauvegarde a raté");
            e.printStackTrace();
        }

    }
    
    public static Niveau importPegles(String name, boolean campagne, int widthCourt, int heightCourt){
        Niveau nv = new Niveau(name) ;
        nv.campagne = campagne ;

       try (Scanner save = new Scanner(new File(nv.pathDossier() + name + nomExtension))) {
        String[] line = save.nextLine().split(";") ;
           
           // obtenir les valeurs de réajustement des des pegs pour qu'il s'adepete à la nouvelle taille de l'écran 
           double reajustementH = widthCourt / Double.valueOf(line[0]);
           double reajustementV = heightCourt /Double.valueOf(line[1]);

            // remise des valeurs de Niveau :
            nv.nbBillesInitiales = Integer.valueOf(line[2]);
            nv.score1Etoile = Integer.valueOf(line[3]);
            nv.score2Etoiles = Integer.valueOf(line[4]);
            nv.score3Etoiles = Integer.valueOf(line[5]);
            nv.campagne = line[6].equals("1") ;


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

}
