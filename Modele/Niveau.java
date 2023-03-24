package Modele;

import java.beans.Transient;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.awt.Dimension;
import java.awt.Size ;

import java.util.ArrayList;

public class Niveau implements Serializable {

    // dossier des partie sauvegarder
    public static String cheminDosierSauvegarde = "Maps/";

    public void setPegs(ArrayList<Pegs> pegs) {
        this.pegs = pegs;
    }

    private final String nom;
    private ArrayList<Pegs> pegs;
    private int nbBillesInitiales;
    private int score1Etoile;
    private int score2Etoiles;
    private int score3Etoiles;
    private transient Dimension tailleCourt ;

    public Niveau(String nom) {
        this.nom = nom;
    }

    // public Niveau(String nom, Dimension tailleCourt){
    //     this = importDepuisSauvegarde(nom, tailleCourt) ;
    // }



    
    
    
    
    
    // tout ce qui est liéer à la sauvegarde des niveaux
        
    public static Niveau importDepuisSauvegarde(String nom, Dimension tailleCourt){
        Niveau nv = new Niveau(nom) ;
         //permet de savoir où recreer les pegs
        nv.tailleCourt = tailleCourt ;
         // import le fichier de sauvegarde
         try {
            final FileInputStream fichier = new FileInputStream(cheminDosierSauvegarde+ nom +".peggles");
            ObjectInputStream obj = new ObjectInputStream(fichier) ;
            nv = (Niveau) obj.readObject() ;

            obj.close();
            fichier.close();
             
         } catch (Exception e) {
             System.out.println(e);
         }

         return nv ;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        Double minHeight_Width = Math.min(tailleCourt.getWidth(), tailleCourt.getHeight());
        //permet de stocker les coordonnées en pourcentage de la taille de l'écran 
        for(Pegs peg :pegs){
            peg.setX(peg.getX(true)/tailleCourt.getWidth());
            peg.setY(peg.getY(true)/tailleCourt.getWidth());
            peg.setRadius(peg.getX(true)/minHeight_Width);
        }
        out.defaultWriteObject();
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();

        //permet de decompresser les coordonnées en entier 
        for(Pegs peg :pegs){
            peg.setX(peg.getX(true)*tailleCourt.getWidth());
            peg.setY(peg.getY(true)*tailleCourt.getWidth());
            peg.setRadius(peg.getX(true)*Math.min((tailleCourt.getWidth(), tailleCourt.getHeight()));
        }

    }

    public void save() {
        // enregistrer un objet
        try {
            // Saving of object in a file
            FileOutputStream file = new FileOutputStream(cheminDosierSauvegarde + nom + ".peggles");
            ObjectOutputStream out = new ObjectOutputStream(file);
            // Method for serialization of object
            out.writeObject(this);
            out.close();
            file.close();
            // Success message
            System.out.println("Object has been serialized");
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

}
