package Vue;

import java.awt.Color;
import java.awt.Graphics;

import Modele.Pegs;

public class EditeurNiveaux extends GameView {

    EditeurNiveaux(Controleur c) {
        super(c);
        Pegs p = new Pegs(0, 0, 20, 1);
    }

    public void paint(Graphics g) {
        g.setColor(Color.GREEN);
    }
}
