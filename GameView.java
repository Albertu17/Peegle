

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.Timer;
import javax.swing.event.MouseInputListener;



public class GameView extends JFrame implements MouseInputListener{

    private int width;
    private int heigth;



    //private Circle ball;
    private ArrayList<Ball> balls=new ArrayList<>();
    private ArrayList<Rectangle> rectanlgle=new ArrayList<>();
    private ArrayList<Pegs> pegs=new ArrayList<>();

    // Canon

    Canon canon ;
    private Sceau sceau;


    GameView(int w,int h) {


    ImageImport.setImage(true);


    
    width=w;
    heigth=h;

    setSize(width, heigth);
    setTitle("Test");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // balls.add(new Ball(220,3,2,0,this));
    // rectanlgle.add(new Rectangle(100, 300, 300,45));

    balls.add(new Ball(5,0,20,1,this));
    sceau = new Sceau(this);
    //rectanlgle.add(new Rectangle(0, 400, 500,45));


    // creation canon :
    canon = new Canon(this) ;
    this.add(canon) ;
    canon.setVisible(true);
    // balls.add(new Ball(300,400,-100,-100,this));
    //rectanlgle.add(new Rectangle(0, 400, 300,-45));

    canon.setBalleATirer(new Ball(0, 0, 0, 0, this));

    // balls.add(new Ball(225,300,0,0,this));
    // rectanlgle.add(new Rectangle(200, 400, 100, -45));
    // balls.add(new Ball(20,30,10,-10,this));
    //generate triangle of pegs
    for (int i=0;i<10;i++){
        for (int j=0;j<i;j++){
            pegs.add(new Pegs(100+i*50,700+j*50,20));
        }
    }
    


    Shapes s = new Shapes();
    add(s);
    setVisible(true);    
    animate();
    // setLayout(null);

    

    this.addMouseListener(this);
    this.addMouseMotionListener(this);
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

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return width;
    }

    public ArrayList<Rectangle> getRectangle(){
        return rectanlgle;
    }

    public Sceau getSceau(){
        return sceau;
    }







    public static void main(String[] args) {

       GameView g = new GameView(500,500);
    //    GameView g = new GameView(500,500);
       
    }

    public class Shapes extends JPanel{
    public void paint(Graphics g){
        int toucher=0;
        for (Pegs peg:pegs) {
            if (peg.getHit()) {
                toucher++;

            }
        }
        System.out.println(toucher);
        canon.repaint();
        setSize(width,heigth);
        g.setColor(Color.BLACK);
        for (Ball ball:balls) 
        {   
            if (ball.isPresent()){
                g.setColor(Color.BLACK);
                g.fillOval((int)ball.ballX,(int)ball.ballY,(int)ball.ballRadius*2,(int)ball.ballRadius*2);
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
            if (balls.get(i).getHitGround()) {
                balls.remove(i);
            }
        }

        //g.drawRect((int)sceau.X, (int)sceau.Y, (int)sceau.longeur, (int)sceau.hauteur);
        g.drawImage(sceau.getImage(), (int) sceau.X, (int)sceau.Y, this);


        g.setColor(Color.RED);
        for (Rectangle rect:rectanlgle) {
            g.drawLine(rect.x0, rect.y0, rect.caculX1(), rect.caculY1());}
        for (Pegs peg:pegs) {
            if (peg.getHit()) {
                g.setColor(Color.GREEN);
            }
            else {
                g.setColor(Color.RED);
            }
            g.fillOval(peg.getX(), peg.getY(), peg.getRadius(), peg.getRadius());
        }
    }

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
