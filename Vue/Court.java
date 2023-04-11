package Vue;
import java.awt.event.MouseEvent;
import java.io.InputStream;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;
import javax.swing.Timer;

import Modele.*;

public class Court extends JPanel implements MouseInputListener {

    private int width;
    private int height;
    private Canon canon;
    private Sceau sceau;
    private int toucher;
    private ArrayList<Ball> balls;
    private ArrayList<Rectangle> rectangles;
    private ArrayList<Pegs> pegs;
    ArrayList<Pegs> toucherPegs;

    // Pour l'éditeur de niveaux
    boolean editMode;
    EditeurNiveaux eN;

    Niveau niveau;

    public Court(int courtWith, int courtHeight, Niveau niveau)  {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        width = courtWith;
        height = courtHeight;
        this.niveau = niveau;

        // Pour l'éditeur de niveaux
        editMode = false; // Par défault
        eN = null; // Par défault

        // Listeners
        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        // ArrayLists
        balls = new ArrayList<>();
        rectangles = new ArrayList<>();
        pegs = new ArrayList<>();
        toucherPegs = new ArrayList<>();

        // Canon
        canon = new Canon(this) ;
        setLayout(null);
        add(canon);
        canon.setVisible(true);
        //the canon doesn't show up fix the problem
        canon.setBalleATirer(new Ball(0, 0, 0, 0, this));

        // Balls
        toucher = 0 ;

        // Sceau
        sceau = new Sceau(this);

        animate();
    }

    public Niveau getNiveau() {
        return niveau;
    }

    //generate triangle of pegs
    public void addTriangleOfPegs() {
        for (int i=0;i<10;i++){
            for (int j=0;j<i;j++){
                pegs.add(new Pegs(100+i*50,300+j*50,20,1));
                pegs.add(new Pegs(100+i*50,600-j*50,20,3));
            }
        }
    }

    public void animate() {
        final Timer timer = new Timer(10, null);
        timer.addActionListener(new ActionListener() {
            double now=System.nanoTime();
            double last;
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!editMode) {
                    last = System.nanoTime();
                    for (Ball b:balls){
                        if (b.isPresent()) b.updateBall((last-now)*1.0e-9,sceau);
                    }
                    sceau.move(((last-now)*1.0e-9));
                } else timer.stop(); // Arrêt de tout le timer.
                repaint();
                now=last;
            }
        });
        timer.start();
    }

    public void paint(Graphics g) {
        super.paint(g);
        
        //Use ARCADE_N.TTF font
        try {
            InputStream targetStream = new FileInputStream("./Vue/Font/ARCADE_N.TTF");
            Font newFont =  Font.createFont(Font.TRUETYPE_FONT, targetStream);
            g.setFont(newFont.deriveFont(20f));
        } catch (FontFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        g.drawString("Score: " + toucher, 10, 50);
        // System.out.println(toucher);
        // canon.repaint();
        g.setColor(Color.BLACK);
        for (Ball ball:balls) {   
            if (ball.isPresent()) {
                g.setColor(Color.BLACK);
                g.fillOval((int)ball.ballX, (int)ball.ballY, (int)ball.ballRadius*2, (int)ball.ballRadius*2);
            }
        }

        //remove ball hit the ground
        boolean remove = false;
        for (int i=0;i<balls.size();i++) {
            if (balls.get(i).getHitGround()) {
                balls.remove(i);
                remove = true;
            }
        }
        if (remove) {
            for (Pegs peg: niveau.getPegs()) {
                if (peg.getHit()) {
                    toucherPegs.add(peg);
                }
            }
        }
        if (toucherPegs.size() > 0) {
            Pegs peganim = toucherPegs.get(0);
            g.drawOval(peganim.getX(), peganim.getY(), peganim.getRadius(), peganim.getRadius());
            pegs.remove(peganim);
            toucherPegs.remove(peganim);
            toucher++;
        }

        //g.drawRect((int)sceau.X, (int)sceau.Y, (int)sceau.longeur, (int)sceau.hauteur);
        g.drawImage(sceau.getImage(), (int) sceau.X, (int)sceau.Y, this);


        g.setColor(Color.RED);
        for (Rectangle rect:rectangles) {
            g.drawLine(rect.x0, rect.y0, rect.caculX1(), rect.caculY1());
        }

        for (Pegs peg: niveau.getPegs()) {
            Graphics2D g2d = (Graphics2D) g;        
            if (peg.getHit()) {
                g2d.drawImage(ImageImport.getImage(peg.getImageStringTouche()), peg.getX(), peg.getY(), peg.getRadius(), peg.getRadius(), this);
            }
            else {
                g2d.drawImage(ImageImport.getImage(peg.getImageString()), peg.getX(), peg.getY(), peg.getRadius(), peg.getRadius(), this);
            }
            //image pegs toucher
        }

        // traçage ligne de viser
        if (!editMode) {
            canon.calculCordonnéeLigneViser();
            Graphics2D g2DGameview = (Graphics2D) g;
            g2DGameview.setColor(Color.RED);
            float dash1[] = {20.0f};
            BasicStroke dashed = new BasicStroke(5.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);
            g2DGameview.setStroke(dashed);
            g2DGameview.drawPolyline(canon.getXLigneViser(), canon.getYLigneViser(), 10);
        };
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ArrayList<Rectangle> getRectangles() {
        return rectangles;
    }

    public Sceau getSceau() {
        return sceau;
    }

    public ArrayList<Pegs> getPegs() {
        return pegs;
    }

    public void mousePressed(MouseEvent e) {
        // lancer une balle
        if (!editMode) balls.add(canon.tirer());
        else if (eN != null && eN.enModif) {
            boolean sourisSurPeg = false;
            for (Pegs p : niveau.getPegs()) {
                if (Math.pow(e.getX() - p.getX(), 2) + Math.pow(e.getY() - p.getY(), 2) <= Math.pow(p.getRadius(),2)) {
                    eN.pegSelectionne = p;
                    sourisSurPeg = true;
                    break;
                }
                if (!sourisSurPeg) eN.pegSelectionne = null;
            }
        }
        else {
            int radius = eN.caseActive.radius;
            niveau.getPegs().add(new Pegs(e.getX() - radius/2, e.getY() - radius/2, radius, eN.caseActive.couleur));
            repaint();
        }
    }

    public void mouseDragged(MouseEvent e) {
        // Déplacement du canon en fonction de la possition de la souris
        if (!editMode) canon.DeplacementCanon(e);
        else if (eN != null && eN.enModif) {
            if (eN.pegSelectionne != null) {
                eN.pegSelectionne.setX(e.getX());
                eN.pegSelectionne.setY(e.getY());
                repaint();
            }
        }
    }

    public void mouseMoved(MouseEvent e) {
        // Déplacement du canon en fonction de la possition de la souris
        if (!editMode) canon.DeplacementCanon(e);
    }

    public void mouseClicked(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}
