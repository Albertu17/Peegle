package Vue;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private String nom;
    private ArrayList<Ball> balls;
    private ArrayList<Pegs> pegs;
    private ArrayList<Pegs> toucherPegs;
    private Background background;
    private ArrayList<Rectangle> rectangles;
    private int NbDeBall = 250 ;
    private boolean nbDeBallChange=true;
    private int MaxCombo = 0;
    private Font newFont = ImageImport.newFont;
    private int mouseX = 0;
    private int mouseY = 0;
    private boolean GameOver = false;
    private int ScoreMax = 0;
    private int ComboEncours = 0;
    private int frameCount = 0;
    private int afficageCombo = 0;


    public Court(int courtWith, int courtHeight, String nomLevel)  {
        // setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setOpaque(false);
        width = courtWith;
        height = courtHeight;
        nom = nomLevel;

       
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
        // for (int i=0;i<50;i++){
        //     pegs.add(new Pegs(100+i*25, 400, 20, 1));
        //     pegs.add(new Pegs(100+i*25, 500, 20, 1));
        // }


        if (nomLevel != null){
            if(nomLevel.toLowerCase().equals("aleatoire")) this.niveau = Niveau.NiveauAleatoire(width, height, 10, 20) ;
            else this.niveau = Niveau.importPegles(nomLevel, courtWith, courtHeight) ;
            pegs = niveau.getPegs() ;
        } 

        animate();
    }

    public void animate() {
        Timer timer = new Timer(10, new ActionListener() {
            double now=System.nanoTime();
            double last;
            @Override
            public void actionPerformed(ActionEvent e) {
                last = System.nanoTime();
                for (Ball b:balls){
                    if (b.isPresent()) b.updateBall((last-now)*1.0e-9,sceau);
                }
                sceau.move(((last-now)*1.0e-9));
                repaint();
                now=last;
                
            }
        });
        timer.start();
    }

    public BufferedImage getBall(){
        return Ball.getImgBall();
    }
    public void paint(Graphics g) {
        
        super.paint(g);
        //FIN DE PARTIE
        if(pegs.size()==0){
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
            g.setFont(newFont.deriveFont(18f));
            g.setColor(Color.WHITE);
            g.drawString("Level "+nom + " Completed !", 550, 125);
            g.setFont(newFont.deriveFont(26f));
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
        frameCount++;
        if (ComboEncours != 0) {
        g.setFont(newFont.deriveFont(144f));
        if (ComboEncours>MaxCombo) MaxCombo = ComboEncours;
        if (afficageCombo>5) g.setColor(Color.RED);
        else if (afficageCombo>3) g.setColor(Color.ORANGE);
        else g.setColor(Color.YELLOW);
        if(frameCount>=10){
            g.drawString("Combo x"+afficageCombo, (int)150, (int)400);
            toucher += afficageCombo*afficageCombo;
            background.repaint();
            frameCount = 0;
            if (afficageCombo<ComboEncours) afficageCombo++;
            else {
                ComboEncours = 0;
                afficageCombo = 0;
            }
        }
    
        else {
            g.drawString("Combo x"+afficageCombo, (int)150, (int)400);
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
            for (Pegs peg:pegs) {
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
        for (Pegs peg:pegs) {
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
        canon.calculCordonnéeLigneViser();
        Graphics2D g2DGameview = (Graphics2D) g;
        g2DGameview.setColor(Color.RED);
        float dash1[] = {20.0f};
        BasicStroke dashed = new BasicStroke(5.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);
        g2DGameview.setStroke(dashed);
        g2DGameview.drawPolyline(canon.getXLigneViser(), canon.getYLigneViser(), 10);

        
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

    @Override
    public void mouseClicked(MouseEvent e) {
        // lancer une balle
        if (!GameOver) {
        
        if (NbDeBall>0){ 
            background.repaint();
            nbDeBallChange=true;
            balls.add(canon.tirer());
            NbDeBall--;
        }
    } else {
        if (mouseX>535 && mouseX<985 && mouseY>695 && mouseY<765){
            //Button next level! clicked
    }
}
        
    }

    public boolean nbBallHasChanged(){
        return nbDeBallChange;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        // Déplacement du canon en fonction de la possition de la souris
        canon.DeplacementCanon(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();

        // Déplacement du canon en fonction de la possition de la souris
        canon.DeplacementCanon(e);    
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
}
