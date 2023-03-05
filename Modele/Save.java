package Modele;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.* ;


// le code se trouvant ici sera surement déplacer vers une classe plus intérrésante 
public class Save {
    
    List<String> pegs ;
    String nomSauvegarde ;

    
    /*
     * data type in csv file
     * Premiére ligne avec la taille de l'air de jeu 
     * court width ; court height; 
     * 
     * 
     * 
     * Peggles type ; width ; heigth ; pos x ; pos y ; ? color? ; resistance peegles ;
     */

    
    public void save(String nomSauvegarde, int widthCourt, int heightCourt) throws FileNotFoundException{
        this.nomSauvegarde = nomSauvegarde ;
        save(widthCourt, heightCourt) ;
    }

    public void save(int widthCourt, int heightCourt) throws FileNotFoundException{
        // save les lignes de l'array list dans un fichier csv
        
    
        PrintWriter file = new PrintWriter("Modele/Map/" + nomSauvegarde + ".csv");

        // premiere ligne d'info :
        String ligne  = String.valueOf(widthCourt) +";"+ String.valueOf(heightCourt) ;
        file.println(ligne);
        

        // remplacer par les valeurs à enregistrer
        for (String peg : pegs){
            ligne =  peg ;
            // ligne  = String.valueOf(widthCourt) +";"
            //             + String.valueOf(widthCourt) +";"
            //             + String.valueOf(widthCourt) +";"
            //             + String.valueOf(heightCourt) ;
            file.println(ligne);

        }
        file.close();
        // enregistrement réussi



    }
    
    public List<String> importPegles(String name, int widthCourt, int heightCourt) throws IOException{
        List<String> pegs = new ArrayList<String>() ;

       Scanner save  = new Scanner(new File("Modele/Map/" + nomSauvegarde + ".csv")) ;

       String[] line = save.nextLine().split(";") ;
       
       // obtenir les valeurs de réajustement des pegs pour qu'il s'adepete à la nouvelle taille de l'écran 
       double reajustementH = widthCourt / Double.valueOf(line[0]);
       double reajustementV = heightCourt /Double.valueOf(line[1]) ;

        while(save.hasNextLine()){
            line = save.nextLine().split(";") ;

            // creation des pegs en fonction des infos que on a 
            // update des nouveau coordonnées en fonction de la taille de l'écran actuel

        }

        save.close();

        // return un objet de type 
        return pegs ;
    }

}

