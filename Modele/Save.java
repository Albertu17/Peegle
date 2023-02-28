package Modele;

import java.util.List;
import java.io.* ;


// le code se trouvant ici sera surement déplacer vers une classe plus intérrésante 
public class Save {
    
    List<String> pegs ;
    String nomSauvegarde ;

    public void save(String nomSauvegarde, int widthCourt, int heightCourt){
        this.nomSauvegarde = nomSauvegarde ;
        save(widthCourt, heightCourt) ;
    }

    /*
     * data type in csv file
     * Premiére ligne avec la taille de l'air de jeu 
     * court width ; court height; 
     * 
     * 
     * 
     * Peggles type ; width ; heigth ; pos x ; pos y ; ? color? ; resistance peegles ;
     */


    public void save(int widthCourt, int heightCourt){
        // save les lignes de l'array list dans un fichier csv
        
        int t = 0 ; 

    }
    
    public List<String> importPegles(String name, int widthCourt, int heightCourt){
        // double ajustement = new size / last size ;
    
        List<String> pegs = new ArrayList<String>() ;

        // update des nouveau coordonnées en fonction de la taille de l'écran precédent
        try {
            File file = new File("Map/" + nomSauvegarde + ".csv");
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String[] line = br.readLine().split(";");

            // obtenir les valeurs de réajustement des pegs
            double reajustementH = widthCourt / Double.valueOf(line[0]);
            double reajustementV = heightCourt /Double.valueOf(line[1]) ;

            while ((line = br.readLine().split(";")) != null) { //detecte quand on est à la derniére ligne 
                // creation des pegs en fonction des infos que on a 

            }

            br.close();
          }
          catch(IOException ioe) {
            ioe.printStackTrace();
          }


        // return un objet de type 
        return pegs ;
    }
}

