package Vue;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Graphics;

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
    private ArrayList<Ball> balls;
    private ArrayList<Rectangle> rectangles;
    private ArrayList<Pegs> pegs;

    public Court(int courtWith, int courtHeight)  {
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

        // Canon
        canon = new Canon(this) ;
        add(canon);
        canon.setVisible(true);
        canon.setBalleATirer(new Ball(0, 0, 0, 0, this));

        // Balls
        balls.add(new Ball(5,0,20,1,this));
        // balls.add(new Ball(220,3,2,0,this));
        // rectangles.add(new Rectangle(100, 300, 300,45));
        //rectangles.add(new Rectangle(0, 400, 500,45));

        // balls.add(new Ball(225,300,0,0,this));
        // rectangles.add(new Rectangle(200, 400, 100, -45));
        // balls.add(new Ball(20,30,10,-10,this));
        // setLayout(null);

        // Sceau
        sceau = new Sceau(this);

        //generate triangle of pegs
        for (int i=0;i<10;i++){
            for (int j=0;j<i;j++){
                pegs.add(new Pegs(100+i*50,300+j*50,20));
            }
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
        int toucher = 0;
        for (Pegs peg:pegs) {
            if (peg.getHit()) toucher++;
        }
        System.out.println(toucher);
        // canon.repaint();
        g.setColor(Color.BLACK);
        for (Ball ball:balls) {   
            if (ball.isPresent()) {
                g.setColor(Color.BLACK);
                g.fillOval((int)ball.ballX, (int)ball.ballY, (int)ball.ballRadius*2, (int)ball.ballRadius*2);
            }

            //g.setColor(Color.BLUE);
            // g.fillOval((int)ball.x,(int)ball.y,5,5);
            // g.setColor(Color.RED);
            // g.fillOval((int)(ball.p1),(int)(ball.p2),5,5);
            // g.setColor(Color.PINK);
            // g.fillOval((int)(ball.p1),(int)(ball.p2),5,5);
        }

        //remove ball hit the ground
        for (int i=0;i<balls.size();i++) {
            if (balls.get(i).getHitGround()) balls.remove(i);
        }

        //g.drawRect((int)sceau.X, (int)sceau.Y, (int)sceau.longeur, (int)sceau.hauteur);
        g.drawImage(sceau.getImage(), (int) sceau.X, (int)sceau.Y, this);


        g.setColor(Color.RED);
        for (Rectangle rect:rectangles) {
            g.drawLine(rect.x0, rect.y0, rect.caculX1(), rect.caculY1());
        }
        for (Pegs peg:pegs) {
            if (peg.getHit()) g.setColor(Color.GREEN);
            else g.setColor(Color.RED);
            g.fillOval(peg.getX(), peg.getY(), peg.getRadius(), peg.getRadius());
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
