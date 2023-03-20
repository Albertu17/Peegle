package Modele;

import java.util.ArrayList;

public class Niveau {
    
    private ArrayList<Pegs> pegs;
    private final String nom;
    private int nbBillesInitiales;
    private int score1Etoile;
    private int score2Etoiles;
    private int score3Etoiles;

    public Niveau (String nom) {
        this.nom = nom;
    }

}
