package Vue;

import java.awt.Color;
import java.awt.Graphics;

public class EditeurNiveaux extends GameView {

    EditeurNiveaux(int w, int h) {
        super(w, h);
        Pegs p = new Pegs(0, 0, 20);
    }

    public void paint(Graphics g) {
        g.setColor(Color.GREEN);
    }
    
}
