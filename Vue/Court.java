package Vue;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;

import org.w3c.dom.Text;

import javax.swing.Timer;

import Modele.*;

public class Court extends JPanel implements MouseInputListener, KeyListener {

    private int width;
    private int height;
    private Canon canon;
    private Sceau sceau;
    private Niveau niveau;
    private int toucher;
    private ArrayList<Ball> balls;
    private ArrayList<Pegs> pegs;
    private ArrayList<Pegs> toucherPegs;
    private Background background;
    private ArrayList<Rectangle> rectangles;
    private int NbDeBall = 125;
    private boolean nbDeBallChange = true;
    private int MaxCombo = 0;
    private Font arcade = ImageImport.arcade;
    private int mouseX = 0;
    private int mouseY = 0;
    private boolean GameOver = false;
    private int ScoreMax;
    private int ComboEncours = 0;
    private int frameCount = 0;
    private int afficageCombo = 0;

    private boolean enPause;
    // Pour l'éditeur de niveaux
    private EditeurNiveaux eN;
    private boolean editMode;
    private Controleur controleur;

    public Court(int courtWith, int courtHeight, Niveau niveau, Controleur c) {
        controleur = c;
        // setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setOpaque(false);
        width = courtWith;
        height = courtHeight;
        this.niveau = niveau;
        pegs = clonePegs(niveau.getPegs()); // Crée une copie en profondeur des pegs du niveau.
        ScoreMax = niveau.getScoreMax();

        // Par défaut
        enPause = false;
        eN = null;
        editMode = false;

        // Listeners
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addKeyListener(this);

        // ArrayLists
        balls = new ArrayList<>();
        rectangles = new ArrayList<>();
        // pegs = new ArrayList<>();
        toucherPegs = new ArrayList<>();

        // Canon
        canon = new Canon(this);
        setLayout(null);
        add(canon);
        canon.setVisible(true);
        // the canon doesn't show up fix the problem

        canon.setBalleATirer(new Ball(0, 0, 0, 0, this));

        // Balls
        toucher = 0;

        // Sceau
        sceau = new Sceau(this);

        animate();
    }

    public BufferedImage getBall() {
        return Ball.getImgBall();
    }

    public boolean getEnPause() {
        return enPause;
    }

    public void setEnPause(boolean enPause) {
        this.enPause = enPause;
    }

    public Niveau getNiveau() {
        return niveau;
    }

    public void seteN(EditeurNiveaux eN) {
        this.eN = eN;
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

    public ArrayList<Ball> getBalls() {
        return balls;
    }

    public ArrayList<Pegs> getToucherPegs() {
        return toucherPegs;
    }

    public Sceau getSceau() {
        return sceau;
    }

    public void setPegs(ArrayList<Pegs> pegs) {
        this.pegs = pegs;
    }

    public ArrayList<Pegs> getPegs() {
        return pegs;
    }

    public void augmenteNbDeBall() {
        nbDeBallChange = true;
        NbDeBall++;
    }

    public void setBallChanged(boolean b) {
        nbDeBallChange = b;
    }

    public int getNbDeBall() {
        return NbDeBall;
    }

    public boolean nbBallHasChanged() {
        return nbDeBallChange;
    }

    public int getScore() {
        return toucher;
    }

    public int getScoreMax() {
        return niveau.getScoreMax();
    }

    public void setBackground(Background background) {
        this.background = background;
    }

    public int getBallRadius() {
        return (int) Ball.ballRadius;
    }

    public void setSkin2() {
        for (Ball ball : balls) {
            ball.putSkin2();
        }
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    public void activerModeEditeur(EditeurNiveaux eN) {
        setEnPause(true);
        seteN(eN);
        setEditMode(true);
    }

    public ArrayList<Pegs> clonePegs(ArrayList<Pegs> originalPegs) {
        ArrayList<Pegs> clones = new ArrayList<>();
        for (Pegs p : originalPegs) {
            try {
                clones.add((Pegs) p.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        return clones;
    }

    public void animate() {
        final Timer timer = new Timer(10, null);
        timer.addActionListener(new ActionListener() {
            double now = System.nanoTime();
            double last;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (enPause)
                    timer.stop(); // Arrêt de tout le timer.
                else {
                    last = System.nanoTime();
                    for (Ball b : balls) {
                        if (b.isPresent())
                            b.updateBall((last - now) * 1.0e-9, sceau);
                    }
                    sceau.move(((last - now) * 1.0e-9));
                    if (!editMode && pegs.size() == 0) {
                        WinPanel pan = new WinPanel(width / 2, height);
                        pan.setLocation(width / 2 - pan.getWidth() / 2, height / 2 - pan.getHeight() / 2);
                        add(pan);
                        enPause = true;
                    }

                    repaint();
                    now = last;
                }
            }
        });
        timer.start();
    }

    public class WinPanel extends JPanel {
        BufferedImage WinScreen;
        BufferedImage WinScreenDisable;
        boolean exited;
        int width;
        int height;

        

        public Font rightSize(String txt, int tailleMax) {
            Font rightF = arcade.deriveFont(1000f); // Très grande taille de police par défault
            FontMetrics metrics = WinScreen.createGraphics().getFontMetrics(rightF);
            int fontSize = rightF.getSize();
            int textWidth = metrics.stringWidth(txt);
            // int textWidthMax = (WinPanel.this.getWidth()*5)/6;
            if (textWidth > tailleMax) {
                System.out.println("in");
                double widthRatio = (double) tailleMax / (double) textWidth;
                rightF = rightF.deriveFont((float) Math.floor(fontSize * widthRatio));
                fontSize = rightF.getSize();
                metrics = WinScreen.createGraphics().getFontMetrics(rightF);
            }
            return rightF;
        }

        WinPanel(int width, int height) {
            this.width = width;
            this.height = height;
            // idépendant de la classe, pour la fin du jeu :
            GameOver = true;
            canon.setVisible(false);
            Court.this.setBorder(null);
            background.setOver(true);
            background.repaint();

            // parametre du Panel :
            setOpaque(false);
            setLayout(null);
            setVisible(true);
            setSize(width, height);

            WinScreen = ImageImport.getImage("WinScreen.png", width, height);
            WinScreenDisable = ImageImport.getImage("WinScreenDisabled.png", width, height);
            exited = false;

            addMouseListener((MouseListener) new MouseAdapter() {
                public void mouseEntered(MouseEvent evt) {
                    exited = true;
                    repaint();
                }

                public void mouseExited(MouseEvent evt) {
                    exited = false;
                    repaint();
                }

                public void mousePressed(MouseEvent evt) {
                    controleur.launchMenu();
                } // TODO que faire quand le niveau est fini
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (exited)
                g.drawImage(WinScreen, 0, 0, this);
            else
                g.drawImage(WinScreenDisable, 0, 0, this);

            g.setFont(rightSize("Level " + niveau.getNom() + " Completed !", (width * 635) / 781));
            g.setColor(Color.WHITE);
            g.drawString("Level " + niveau.getNom() + " Completed !", (width * 90) / 781, (height * 50) / 876);

            // if()
            int x = (height * 45) / 876 ;
            int y =(height * 175) / 876 ;
            g.setFont(rightSize("Balles Utilisees: 1000", (width * (876-90)) / 876));
            g.drawString("Score: " + toucher, x, y);  
            y += (height * 75) / 876  ;          
            g.drawString("Balles Restantes: " + NbDeBall, x, y);
            y += (height * 75) / 876  ;          
            g.drawString("Balles Utilisees: " + (250 - NbDeBall), x, y);
            y += (height * 75) / 876  ;          
            g.drawString("Max Score: " + niveau.getScoreMax(), x, y);
            if (toucher > ScoreMax) {
                y = (height *600) / 876  ;          
                g.drawString("Nouveau Max Score !!!", x, y);
                niveau.setScoreMax(toucher);
            }

        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Controleur c = new Controleur();
                c.launchGameview("Campagne/Test2");
            }
        });
    }

    public void paint(Graphics g) {

        super.paint(g);
        if (!editMode && pegs.size() == 0)
            return;
        // FIN DE PARTIE
        // if (!editMode && pegs.size()==0) {
        // canon.setVisible(false);
        // BufferedImage WinScreen;
        // if (mouseX>535 && mouseX<985 && mouseY>695 && mouseY<765){
        // WinScreen = ImageImport.getImage("WinScreen.png", width, height);
        // }
        // else {
        // WinScreen = ImageImport.getImage("WinScreenDisabled.png", width, height);
        // }
        // GameOver = true;
        // setBorder(null);
        // g.drawImage(WinScreen, 0, 0, this);
        // background.setOver(true);
        // background.repaint();
        // g.setFont(arcade.deriveFont(18f));
        // g.setColor(Color.WHITE);
        // g.drawString("Level "+ niveau.getNom() + " Completed !", 550, 125);
        // g.setFont(arcade.deriveFont(26f));
        // g.drawString("Score: "+toucher, 500, 210);
        // g.drawString("Balles Restantes: "+NbDeBall, 500, 260);
        // g.drawString("Balles Utilisees: "+(250-NbDeBall), 500, 310);
        // g.drawString("Max Score: "+ niveau.getScoreMax(), 500, 360);
        // if (toucher>ScoreMax){
        // g.drawString("Nouveau Max Score !!!", 500, 610);
        // niveau.setScoreMax(toucher);
        // }
        // return;
        // }

        // System.out.println(toucher);
        // canon.repaint();
        g.setColor(Color.BLACK);
        for (Ball ball : balls) {
            if (ball.isPresent()) {
                g.setColor(Color.BLACK);
                g.drawImage(ball.getImage(), (int) ball.ballX, (int) ball.ballY, this);
            }
        }

        if (!editMode) {
            frameCount++;
            if (ComboEncours != 0) {
                g.setFont(arcade.deriveFont(144f));
                if (ComboEncours > MaxCombo)
                    MaxCombo = ComboEncours;
                if (afficageCombo > 5)
                    g.setColor(Color.RED);
                else if (afficageCombo > 3)
                    g.setColor(Color.ORANGE);
                else
                    g.setColor(Color.YELLOW);
                if (frameCount >= 10) {
                    g.drawString("Combo x" + afficageCombo, (int) 150, (int) 400);
                    toucher += afficageCombo * afficageCombo;
                    background.repaint(); // Condition pour l'editeur de niveau
                    frameCount = 0;
                    if (afficageCombo < ComboEncours)
                        afficageCombo++;
                    else {
                        ComboEncours = 0;
                        afficageCombo = 0;
                    }
                } else
                    g.drawString("Combo x" + afficageCombo, (int) 150, (int) 400);
            }
        }

        // remove ball hit the ground
        boolean remove = false;
        for (int i = 0; i < balls.size(); i++) {
            if (balls.get(i).getHitGround()) {
                ComboEncours = balls.get(i).getCombo();
                balls.remove(i);
                remove = true;
            }
        }

        if (remove) {
            for (Pegs peg : pegs) {
                if (peg.getHit() && !toucherPegs.contains(peg)) {
                    toucherPegs.add(peg);
                }
            }
        }

        if (toucherPegs.size() > 0) {
            Pegs peganim = toucherPegs.get(0);
            g.drawOval(peganim.getX() - peganim.getRadius(), peganim.getY() - peganim.getRadius(),
                    peganim.getDiametre(), peganim.getDiametre());
            pegs.remove(peganim);
            toucherPegs.remove(peganim);
        }

        // g.drawRect((int)sceau.X, (int)sceau.Y, (int)sceau.longeur,
        // (int)sceau.hauteur);
        g.drawImage(sceau.getImage(), (int) sceau.X, (int) sceau.Y, this);

        g.setColor(Color.RED);
        for (Rectangle rect : rectangles) {
            g.drawLine(rect.x0, rect.y0, rect.caculX1(), rect.caculY1());
        }

        Graphics2D g2d = (Graphics2D) g;
        for (Pegs peg : pegs) {
            if (peg.getHit())
                g2d.drawImage(ImageImport.getImage(peg.getImageStringTouche()), peg.getX() - peg.getRadius(),
                        peg.getY() - peg.getRadius(), peg.getDiametre(), peg.getDiametre(), this);
            else
                g2d.drawImage(ImageImport.getImage(peg.getImageString()), peg.getX() - peg.getRadius(),
                        peg.getY() - peg.getRadius(), peg.getDiametre(), peg.getDiametre(), this);
            // image pegs toucher
        }

        // traçage ligne de viser
        if (!enPause) {
            canon.calculCordonnéeLigneViser();
            Graphics2D g2DGameview = (Graphics2D) g;
            g2DGameview.setColor(Color.RED);
            float dash1[] = { 20.0f };
            BasicStroke dashed = new BasicStroke(5.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1,
                    0.0f);
            g2DGameview.setStroke(dashed);
            g2DGameview.drawPolyline(canon.getXLigneViser(), canon.getYLigneViser(), 10);
        }

        // Draw preview pour l'editeur de niveaux
        if (editMode && eN.caseActive != null && enPause && !eN.enModif) {
            Pegs pV = eN.caseActive.modeleActuel; // preview transparent
            float alpha = (float) 0.2; // draw at 20% opacity
            AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
            g2d.setComposite(ac);
            g2d.drawImage(ImageImport.getImage(pV.getImageString()), pV.getX() - pV.getRadius(),
                    pV.getY() - pV.getRadius(), pV.getDiametre(), pV.getDiametre(), this);
        }
    }

    public void mouseClicked(MouseEvent e) {
        // lancer une balle
        if (!GameOver) {

        } else if (mouseX > 535 && mouseX < 985 && mouseY > 695 && mouseY < 765) {
            controleur.launchMenu();
        }
    }

    public void mousePressed(MouseEvent e) {
        // lancer une balle
        if (!enPause && !GameOver) {
            if (NbDeBall > 0) {
                balls.add(canon.tirer());
                nbDeBallChange = true;
                NbDeBall--;
                if (!editMode)
                    background.repaint();
            }
        } else if (editMode && eN.enModif) {
            boolean sourisSurPeg = false;
            for (Pegs p : niveau.getPegs()) {
                if (p.contains(e.getX(), e.getY())) {
                    eN.pegSelectionne = p;
                    eN.sliderPegSelectionne.setValue(p.getRadius());
                    eN.sliderPegSelectionne.repaint();
                    eN.boutonsModifActifs(true);
                    sourisSurPeg = true;
                    break;
                }
                if (!sourisSurPeg) {
                    eN.pegSelectionne = null;
                    eN.boutonsModifActifs(false);
                }
            }
        } else {
            try {
                niveau.getPegs().add((Pegs) eN.caseActive.modeleActuel.clone());
                pegs.add((Pegs) eN.caseActive.modeleActuel.clone());
            } catch (CloneNotSupportedException e1) {
                e1.printStackTrace();
            }
            repaint();
        }
    }

    public void mouseExited(MouseEvent e) {
        if (editMode && enPause && !eN.enModif) {
            eN.caseActive.modeleActuel.setX(-100); // Fait disparaître le preview du court
            eN.caseActive.modeleActuel.setY(-100);
            repaint();
        }
    }

    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        // Déplacement du canon en fonction de la position de la souris
        if (!enPause)
            canon.DeplacementCanon(e);
        else if (editMode && eN.enModif) {
            if (eN.pegSelectionne != null) {
                eN.pegSelectionne.setX(mouseX);
                eN.pegSelectionne.setY(mouseY);
                setPegs(clonePegs(niveau.getPegs()));
                repaint();
            }
        }
    }

    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        // Déplacement du canon en fonction de la position de la souris
        if (!enPause)
            canon.DeplacementCanon(e);
        // Pour faire apparaître un preview du peg qu'on poserait à cet endroit
        else if (editMode && !eN.enModif) {
            eN.caseActive.modeleActuel.setX(mouseX);
            eN.caseActive.modeleActuel.setY(mouseY);
            repaint();
        }
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case (KeyEvent.VK_RIGHT):
                canon.deplacementCanon(false);
                break;
            case (KeyEvent.VK_LEFT):
                canon.deplacementCanon(true);
                break;

            case (KeyEvent.VK_ENTER):
            case (KeyEvent.VK_SPACE):
                if (!enPause && !GameOver) {
                    if (NbDeBall > 0) {
                        balls.add(canon.tirer());
                        nbDeBallChange = true;
                        NbDeBall--;
                        if (!editMode)
                            background.repaint();
                    }
                }
                break;
            case (KeyEvent.VK_ESCAPE):
                controleur.gameview.launchMenuPause(true); 
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
