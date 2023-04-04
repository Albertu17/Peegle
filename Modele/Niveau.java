package Modele;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

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
    public String getNom() {
        return nom;
    }

    private int nbBillesInitiales;
    private int score1Etoile;
    private int score2Etoiles;
    private int score3Etoiles;

    public Niveau (String nom) {
        this.nom = nom;
        pegs = new ArrayList<>() ;
    }

   private class RandInt extends SecureRandom{
        private int randInt(int a, int b){
            return this.nextInt(b-a +1) +a ;
        }
    }

    public static Niveau NiveauAleatoire(int widthCourt, int heightCourt, int radiusBall, int diametrePegs){
        Niveau nv = new Niveau("Aleatoire") ;
        RandInt rd = nv.new RandInt() ;
        int espaceMinEntre2Pegs = (int) 2.5*radiusBall ;
        int x,y ;
        int debutHeight = heightCourt/4 ; //evite d'avoir des balels trop hautes
        
        // segemente l'aire de jeux en carré le plus petit possible tel que les contraintes soit respecté
        int nbrSegW  = (int) (widthCourt / (espaceMinEntre2Pegs + diametrePegs)  );
        int nbrSegH  = (int) ((heightCourt-debutHeight) / (espaceMinEntre2Pegs + diametrePegs)  );

        int nbrPegs = rd.randInt(((nbrSegH-1)*(nbrSegW-1))/7, 3*((nbrSegH-1)*(nbrSegW-1))/4); //aproximativement le nombre de pegs posé
        int nbrSegParPegs = (int) (((nbrSegH-1)*(nbrSegW-1))/nbrPegs)+1 ; // Nombre de segmentation pour chaque pegs, permet de savoir la probabilité d'avoir un pegs dans ce carré

        for (int w = 0 ; w < nbrSegW -1 ; w++){
            for(int h = 0 ; h < nbrSegH -1; h++ ){
                if (rd.randInt(1, nbrSegParPegs) == 1){ // placer un élément au hasard
                    x=  (int) ( (w+0.5)*(widthCourt/(double)nbrSegW) ) ;
                    y=  (int) ( (h+0.5)*((heightCourt-debutHeight)/(double)nbrSegH)  + debutHeight) ;
                    nv.pegs.add(new Pegs(x, y, diametrePegs, rd.randInt(1, 4))) ;
                }
            }
        }
        // TODO determiner le scoreEtoiles et nombres de balles initials
        return nv ;
    }
    

   

    public static Niveau aletoireNiveau2(int widthCourt, int heightCourt, int diametrePegs, int radiusBall){
        Niveau nv = new Niveau("Aleatoire") ;
        int nbrPegs = 30;
        int x,y ;
        boolean posible ;
        RandInt rd = nv.new RandInt() ;
        for(int comptpeg = 0 ; comptpeg<nbrPegs; comptpeg++){
            
            do{
                posible = true ;
                x = rd.randInt( diametrePegs, widthCourt-diametrePegs) ;
                y = rd.randInt( heightCourt/4, heightCourt-diametrePegs) ;
                for(Pegs peg : nv.pegs){
                    if (Math.abs(x - peg.getX()) < diametrePegs+3*radiusBall  || Math.abs(y - peg.getY()) < diametrePegs+3*radiusBall ){
                        posible = false;
                        break ;
                    }
                }
                System.out.println(x);
                System.out.println(y);
            }
            while(! posible) ;
            nv.pegs.add(new Pegs(x, y, diametrePegs, rd.randInt( 1, 4))) ;
            // System.out.println(comptpeg);
        }

        System.out.println(nv.pegs.size());

        return nv ;
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
