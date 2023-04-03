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
    private Niveau niveau ;
    private int toucher;
    private ArrayList<Ball> balls;
    private ArrayList<Rectangle> rectangles;
    private ArrayList<Pegs> pegs;
    ArrayList<Pegs> toucherPegs;

    public Court(int courtWith, int courtHeight, String nomLevel)  {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        width = courtWith;
        height = courtHeight;

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
        for (int i=0;i<50;i++){
            for (int j=0;j<25;j++){
                pegs.add(new Pegs(600+i*20,300+j*20,10,1));
            }
        }


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
        g.drawString("Score: "+toucher, 10, 50);
        // System.out.println(toucher);
        // canon.repaint();
        g.setColor(Color.BLACK);
        for (Ball ball:balls) {   
            if (ball.isPresent()) {
                g.setColor(Color.BLACK);
                g.drawImage(ball.getImage(), (int)ball.ballX, (int)ball.ballY, this);
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
        for (Pegs peg:pegs) {
            if (peg.getHit()) {
                toucherPegs.add(peg);
            }
        }
    }
        if (toucherPegs.size()>0){
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

    @Override
    public void mouseClicked(MouseEvent e) {
        // lancer une balle
        balls.add(canon.tirer()) ;
        
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
        // Déplacement du canon en fonction de la possition de la souris
        canon.DeplacementCanon(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // Déplacement du canon en fonction de la possition de la souris
        canon.DeplacementCanon(e);    
    } 
}
