package Vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.MouseInputListener;

import Modele.Pegs;

public class EditeurNiveaux {

    public Controleur controleur ;

    private int width;
    private int heigth;

    // Court
    Court court;
    private int courtWidth;
    private int courtHeight;
    JPanel panneauBoutons;

    Container fond;
    Pegs pegActif;

    EditeurNiveaux(Controleur c) {

        this.controleur = c;
        fond = c.container;
        width = controleur.getWidth();
        heigth = controleur.getHeight();

        // Court
        courtWidth = width * 5/6;
        courtHeight = heigth * 5/6;
        court = new Court(courtWidth, courtHeight);
        court.animated = false;
        court.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        court.setBounds(0, 0, courtWidth, courtHeight);
        court.setVisible(true);
        fond.add(court, BorderLayout.CENTER);

        // case Peg bleu
        casePeg casePegBleu = new casePeg(width-courtWidth, courtHeight * 1/4, 1);
        casePegBleu.setBounds(courtWidth, 0, width - courtWidth, courtHeight * 1/4);
        fond.add(casePegBleu);

        // case Peg Rouge
        casePeg casePegRouge = new casePeg(width-courtWidth, courtHeight * 1/4, 2);
        casePegRouge.setBounds(courtWidth, courtHeight * 1/4, width - courtWidth, courtHeight * 1/4);
        fond.add(casePegRouge);

        // case Peg Violet
        casePeg casePegViolet = new casePeg(width-courtWidth, courtHeight * 1/4, 3);
        casePegViolet.setBounds(courtWidth, courtHeight * 1/2, width - courtWidth, courtHeight * 1/4);
        fond.add(casePegViolet);

        // case peg Vert
        casePeg casePegVert = new casePeg(width-courtWidth, courtHeight * 1/4, 4);
        casePegVert.setBounds(courtWidth, courtHeight * 3/4, width - courtWidth, courtHeight * 1/4);
        fond.add(casePegVert);
    }

    public class casePeg extends JPanel implements MouseInputListener{

        Pegs peg;
        int radius, x, y;
        int largeur, hauteur;

        public casePeg(int largeur, int hauteur, int couleur) {
            this.largeur = largeur;
            this.hauteur = hauteur;
            peg = new Pegs(largeur/2, hauteur*3/4/2, 50, couleur);
            radius = peg.getRadius();
            x = peg.getX();
            y = peg.getY();

            setLayout(null);
            JSlider sliderTaillPeg = new JSlider(0, 100, 50);
            sliderTaillPeg.setBounds(0, hauteur*3/4, largeur, hauteur*1/4);
            sliderTaillPeg.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            sliderTaillPeg.addChangeListener(e -> {
                peg.setRadius(sliderTaillPeg.getValue());
                paint(this.getGraphics());
            });
            add(sliderTaillPeg);
        }

        public void paint(Graphics g) {
            super.paint(g);
            radius = peg.getRadius();
            Graphics2D g2d = (Graphics2D) g;
            g2d.drawImage(ImageImport.getImage(peg.getImageString()), x-radius/2, y-radius/2, radius, radius, this);
        }

        public void mouseClicked(MouseEvent e) {
            pegActif = peg;
        }

        public void mousePressed(MouseEvent e) {}
        public void mouseReleased(MouseEvent e) {}
        public void mouseEntered(MouseEvent e) {}
        public void mouseExited(MouseEvent e) {}
        public void mouseDragged(MouseEvent e) {}
        public void mouseMoved(MouseEvent e) {}
    }
}
