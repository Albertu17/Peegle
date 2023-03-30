package Modele;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.text.StyledEditorKit.BoldAction;

import java.util.ArrayList;

public class Niveau {

    private static String dosierSauvegarde ="Maps/" ;
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

    public Niveau (String nom) {
        this.nom = nom;
        pegs = new ArrayList<>() ;
    }

    public static Niveau aletoireNiveau(int widthCourt, int heightCourt, int radiusPegs, int radiusBall){
        Niveau nv = new Niveau("Aleatoire") ;
        int nbrPegs = 50;
        int x,y ;
        boolean posible ;
        SecureRandom rand ;
        
        for(int comptpeg = 0 ; comptpeg<nbrPegs; comptpeg++){
            do{
                rand = new SecureRandom();
                posible = true ;
                x = randInt(rand, 2*radiusPegs, widthCourt-2*radiusPegs) ;
                y = randInt(rand, heightCourt/4, heightCourt-2*radiusPegs) ;
                System.out.print("x, y :");
                System.out.print(x);
                System.out.print(", ");
                System.out.println(y);
                for(Pegs peg : nv.pegs){
                    if (Math.abs(x - peg.getX()) < 2*radiusPegs+3*radiusBall  && Math.abs(y - peg.getY()) < 2*radiusPegs+3*radiusBall ){
                        posible = false;
                        break ;
                    }
                }
            }
            while(! posible) ;
            nv.pegs.add(new Pegs(x, y, radiusPegs, randInt(rand, 1, 4))) ;
            System.out.println(comptpeg);
            nv.save(widthCourt, heightCourt);
        }


        return nv ;
    }

    public static int randInt(SecureRandom rand, int a, int b){
        return rand.nextInt(b-a +1) +a ;
    }


    // enregistrement d'un niveau    

    public void save(int widthCourt, int heightCourt){
        // save les lignes de l'array list dans un fichier csv
        
    
        PrintWriter file;
        try {
            file = new PrintWriter(dosierSauvegarde + nom + nomExtension);
    
            // premiere ligne d'info :
            String ligne  = String.valueOf(widthCourt) +";"
                                + String.valueOf(heightCourt) +";"
                                + String.valueOf(nbBillesInitiales) +";"
                                + String.valueOf(score1Etoile) +";"
                                + String.valueOf(score2Etoiles) +";"
                                + String.valueOf(score3Etoiles) ;
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
    
    public static Niveau importPegles(String name, int widthCourt, int heightCourt){
        Niveau nv = new Niveau(name) ;

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
