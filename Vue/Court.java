package Vue;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;
import javax.swing.Timer;

import Modele.*;

public class Court extends JPanel implements MouseInputListener {

    private int width;
    private int height;
    private Canon canon;
    private Sceau sceau;
    private Niveau niveau ;
    private int toucher;
    private ArrayList<Ball> balls;
    private ArrayList<Pegs> pegs;
    private ArrayList<Pegs> toucherPegs;
    private Background background;
    private ArrayList<Rectangle> rectangles;
    private int NbDeBall = 250 ;
    private boolean nbDeBallChange=true;
    private int MaxCombo = 0;
    private Font arcade = ImageImport.arcade;
    private int mouseX = 0;
    private int mouseY = 0;
    private boolean GameOver = false;
    private int ScoreMax = 0;
    private int ComboEncours = 0;
    private int frameCount = 0;
    private int afficageCombo = 0;

    private boolean enPause;
    // Pour l'éditeur de niveaux
    private EditeurNiveaux eN;
    private boolean editMode;
    
    public Court(int courtWith, int courtHeight, Niveau niveau) {
        // setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setOpaque(false);
        width = courtWith;
        height = courtHeight;
        this.niveau = niveau;
        pegs = clonePegs(niveau.getPegs()); // Crée une copie en profondeur des pegs du niveau.

        // Par défaut
        enPause = false;
        eN = null;
        editMode = false;

        // Listeners
        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        // ArrayLists
        balls = new ArrayList<>();
        rectangles = new ArrayList<>();
        //pegs = new ArrayList<>();
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

    public BufferedImage getBall(){
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

    public void augmenteNbDeBall(){
        nbDeBallChange=true;
        NbDeBall ++;
    }

    public void setBallChanged(boolean b){
        nbDeBallChange=b;
    }
    
    public int getNbDeBall(){
        return  NbDeBall;
    }

    public boolean nbBallHasChanged(){
        return nbDeBallChange;
    }

    public int getScore() {
        return toucher;
    } 

    public int getScoreMax(){
        return  89; // a changer selon les niveaux
    }

    public void setBackground(Background background) {
        this.background = background;
    }

    public int getBallRadius(){
        return (int) Ball.ballRadius;
    }

    public void setSkin2(){
        for(Ball ball:balls){
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

    public ArrayList<Pegs> clonePegs (ArrayList<Pegs> originalPegs) {
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
            double now=System.nanoTime();
            double last;
            @Override
            public void actionPerformed(ActionEvent e) {
                if (enPause) timer.stop(); // Arrêt de tout le timer.
                else {
                    last = System.nanoTime();
                    for (Ball b:balls){
                        if (b.isPresent()) b.updateBall((last-now)*1.0e-9,sceau);
                    }
                    sceau.move(((last-now)*1.0e-9));
                    repaint();
                    now=last;
                }
            }
        });
        timer.start();
    }

    public void paint(Graphics g) {
        
        super.paint(g);
        // FIN DE PARTIE
        if (!editMode &&  pegs.size()==0) {
            BufferedImage WinScreen;
            if (mouseX>535 && mouseX<985 && mouseY>695 && mouseY<765){
             WinScreen = ImageImport.getImage("WinScreen.png", width, height);
            }
            else {
                 WinScreen = ImageImport.getImage("WinScreenDisabled.png", width, height);
            }
            GameOver = true;
            setBorder(null);
            g.drawImage(WinScreen, 0, 0, this);
            background.setOver(true);
            background.repaint();
            g.setFont(arcade.deriveFont(18f));
            g.setColor(Color.WHITE);
            g.drawString("Level "+ niveau.getNom() + " Completed !", 550, 125);
            g.setFont(arcade.deriveFont(26f));
            g.drawString("Score: "+toucher, 500, 210);
            g.drawString("Balles Restantes: "+NbDeBall, 500, 260);
            g.drawString("Balles Utilisees: "+(250-NbDeBall), 500, 310);
            g.drawString("Max Score: "+ ScoreMax, 500, 360);
            if (toucher>ScoreMax){
                g.drawString("Nouveau Max Score !!!", 500, 610);
            }
            return;
        }
        
        // System.out.println(toucher);
        // canon.repaint();
        g.setColor(Color.BLACK);
        for (Ball ball:balls) {   
            if (ball.isPresent()) {
                g.setColor(Color.BLACK);
                g.drawImage(ball.getImage(), (int)ball.ballX, (int)ball.ballY, this);
            }
        }

        if (!editMode) {
            frameCount++;
            if (ComboEncours != 0) {
                g.setFont(arcade.deriveFont(144f));
                if (ComboEncours>MaxCombo) MaxCombo = ComboEncours;
                if (afficageCombo>5) g.setColor(Color.RED);
                else if (afficageCombo>3) g.setColor(Color.ORANGE);
                else g.setColor(Color.YELLOW);
                if (frameCount>=10) {
                    g.drawString("Combo x"+afficageCombo, (int)150, (int)400);
                    toucher += afficageCombo*afficageCombo;
                    background.repaint(); // Condition pour l'editeur de niveau
                    frameCount = 0;
                    if (afficageCombo<ComboEncours) afficageCombo++;
                    else {
                        ComboEncours = 0;
                        afficageCombo = 0;
                    }
                } else g.drawString("Combo x"+afficageCombo, (int)150, (int)400);
            }
        }

        //remove ball hit the ground
        boolean remove = false;
        for (int i=0;i<balls.size();i++) {
            if (balls.get(i).getHitGround()) { 
                ComboEncours = balls.get(i).getCombo();
                balls.remove(i);
                remove = true;
            }
        }
        
        if (remove) {
            for (Pegs peg: pegs) {
                if (peg.getHit() && !toucherPegs.contains(peg)) {
                    toucherPegs.add(peg);
                }
            }
        }
    
        if (toucherPegs.size()>0){
            Pegs peganim = toucherPegs.get(0);
            g.drawOval(peganim.getX(), peganim.getY(), peganim.getRadius(), peganim.getRadius());
            pegs.remove(peganim);
            toucherPegs.remove(peganim);
        }

        //g.drawRect((int)sceau.X, (int)sceau.Y, (int)sceau.longeur, (int)sceau.hauteur);
        g.drawImage(sceau.getImage(), (int) sceau.X, (int)sceau.Y, this);


        g.setColor(Color.RED);
        for (Rectangle rect:rectangles) {
            g.drawLine(rect.x0, rect.y0, rect.caculX1(), rect.caculY1());
        }

        Graphics2D g2d = (Graphics2D) g;   
        for (Pegs peg: pegs) {
            if (peg.getHit()) g2d.drawImage(ImageImport.getImage(peg.getImageStringTouche()), peg.getX(), peg.getY(), peg.getRadius(), peg.getRadius(), this);
            else g2d.drawImage(ImageImport.getImage(peg.getImageString()), peg.getX(), peg.getY(), peg.getRadius(), peg.getRadius(), this);
            //image pegs toucher
        }

        // traçage ligne de viser
        if (!enPause) {
            canon.calculCordonnéeLigneViser();
            Graphics2D g2DGameview = (Graphics2D) g;
            g2DGameview.setColor(Color.RED);
            float dash1[] = {20.0f};
            BasicStroke dashed = new BasicStroke(5.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);
            g2DGameview.setStroke(dashed);
            g2DGameview.drawPolyline(canon.getXLigneViser(), canon.getYLigneViser(), 10);
        }

        // Draw preview pour l'editeur de niveaux
        if (editMode && eN.caseActive != null && enPause && !eN.enModif) {
            Pegs pV = eN.caseActive.modeleActuel; // preview transparent
            float alpha = (float) 0.2; //draw at 20% opacity
            AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha);
            g2d.setComposite(ac);
            g2d.drawImage(ImageImport.getImage(pV.getImageString()), pV.getX(), pV.getY(), pV.getRadius(), pV.getRadius(), this);
        }
    }

    public void mouseClicked(MouseEvent e) {
        // lancer une balle
        if (!GameOver) {
            if (NbDeBall>0){ 
                if (!editMode) {
                    background.repaint();
                    balls.add(canon.tirer());
                }
                nbDeBallChange=true;
                NbDeBall--;
            }
        } else if (mouseX>535 && mouseX<985 && mouseY>695 && mouseY<765){
            //Button next level! clicked
        }
    }

    public void mousePressed(MouseEvent e) {
        // lancer une balle
        if (!enPause) balls.add(canon.tirer());
        else if (editMode && eN.enModif) {
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
        }
        else {
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
        if (!enPause) canon.DeplacementCanon(e);
        else if (editMode && eN.enModif) {
            if (eN.pegSelectionne != null) {
                eN.pegSelectionne.setX(e.getX() - eN.pegSelectionne.getRadius()/2);
                eN.pegSelectionne.setY(e.getY() - eN.pegSelectionne.getRadius()/2);
                setPegs(clonePegs(niveau.getPegs()));
                repaint();
            }
        }
    }

    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        // Déplacement du canon en fonction de la position de la souris
        if (!enPause) canon.DeplacementCanon(e);
        // Pour faire apparaître un preview du peg qu'on poserait à cet endroit
        else if (editMode && !eN.enModif) {
            eN.caseActive.modeleActuel.setX(e.getX() - eN.caseActive.radius/2);
            eN.caseActive.modeleActuel.setY(e.getY() - eN.caseActive.radius/2);
            repaint();
        }
    }

    public void mouseEntered(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
}
