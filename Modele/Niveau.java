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

    public Niveau(String nom, Dimension tailleCourt){

        this.tailleCourt = tailleCourt ;
        // import le fichier de sauvegarde
        try {
            final FileInputStream fichier = new FileInputStream(cheminDosierSauvegarde+ nom +".peggles");
            ObjectInputStream obj = new ObjectInputStream(fichier) ;
            this = (Niveau) obj.readObject() ;
            
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        
        for(Pegs peg :pegs){
            peg.setX(peg.getX()/tailleCourt.getWidth());
            peg.setY(peg.getY()/tailleCourt.getWidth());
            peg.setRadius(peg.getX()/Math.min((tailleCourt.getWidth(), tailleCourt.getHeight()));
        }
        out.defaultWriteObject();
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        for(Pegs peg :pegs){
            peg.setX(peg.getX()*tailleCourt.getWidth());
            peg.setY(peg.getY()*tailleCourt.getWidth());
            peg.setRadius(peg.getX()*Math.min((tailleCourt.getWidth(), tailleCourt.getHeight()));
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
