package Vue;

import java.awt.Color;
import java.awt.Graphics;

import Modele.Pegs;

public class EditeurNiveaux extends GameView {

    EditeurNiveaux(Controleur c) {
        super(c, false);
        court.removeAll();
    }

    public void paint(Graphics g) {
        g.setColor(Color.GREEN);
    }
}
