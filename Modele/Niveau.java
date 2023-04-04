package Modele;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.util.ArrayList;

public class Niveau {
    
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
    }
    

    public void save(int widthCourt, int heightCourt) throws FileNotFoundException{
        // save les lignes de l'array list dans un fichier csv
        
    
        PrintWriter file = new PrintWriter("Modele/Map/" + nom + ".csv");

        // premiere ligne d'info :
        String ligne  = String.valueOf(widthCourt) +";"+ String.valueOf(heightCourt) ;
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

    }
    
    public List<Pegs> importPegles(String name, int widthCourt, int heightCourt) throws IOException{
        pegs = new ArrayList<Pegs>() ;

       Scanner save  = new Scanner(new File("Modele/Map/" + nom + ".csv")) ;

       String[] line = save.nextLine().split(";") ;
       
       // obtenir les valeurs de réajustement des des pegs pour qu'il s'adepete à la nouvelle taille de l'écran 
       double reajustementH = widthCourt / Double.valueOf(line[0]);
       double reajustementV = heightCourt /Double.valueOf(line[1]) ;

       // creation des pegs en fonction des infos que on a 
      // update des nouveau coordonnées en fonction de la taille de l'écran actuel
       while(save.hasNextLine()){
            line = save.nextLine().split(";") ;

            int x =  (int) (reajustementH*Double.valueOf(line[0]) );
            int y =  (int) (reajustementV*Double.valueOf(line[1]) );
            int radius = (int)(Double.valueOf(line[2]) * Math.min(reajustementH, reajustementV) ) ;
            int couleur = Integer.valueOf(line[3]) ;

            pegs.add(new Pegs(x, y, radius, couleur)) ;

        }

        save.close();

        // return un objet de type 
        return pegs ;
    }

}
