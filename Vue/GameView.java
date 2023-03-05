package Vue;


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

import Modele.Ball;
import Modele.Rectangle;
import Vue.Canon;
import Vue.ImageImport;



public class GameView extends JPanel implements MouseInputListener{

    private Controleur controleur ;

    private int width;
    private int heigth;



    //private Circle ball;
    private ArrayList<Ball> balls=new ArrayList<>();
    private ArrayList<Rectangle> rectanlgle=new ArrayList<>();

    // Canon

    Canon canon ;
    Shapes s;


    public GameView(Controleur c) {
    
    this.controleur = c ;

    


    
    width= controleur.getWidth();
    heigth= controleur.getHeight() ;

    setSize(width, heigth);
    setLayout(null);
    setVisible(true);  

    
    // creation canon :
        canon = new Canon(this) ;
        this.add(canon) ;
        canon.setVisible(true);
        canon.setBalleATirer(new Ball(0, 0, 0, 0, this));
    
    // Creation de de la Shape // paint les balles
        s = new Shapes();
        add(s);
        s.setVisible(true);
    
   
    animate();
    


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
                    b.updateBall((last-now)*1.0e-9);
                }
                repaint() ;
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




    
    
    
    
    public class Shapes extends JPanel{

        Shapes(){
            setSize(width,heigth);
        }
        
        public void paint(Graphics g){
            super.paint(g);
            g.setColor(Color.BLACK);
            for (Ball ball:balls) g.fillOval((int)ball.ballX,(int)ball.ballY,(int)ball.ballRadius,(int)ball.ballRadius);
    
    
            g.setColor(Color.RED);
            for (Rectangle rect:rectanlgle) g.drawLine(rect.x0, rect.y0, rect.caculX1(), rect.caculY1());


            // ligne de viser du canon :
            canon.calculCordonnéeLigneViser();
            Graphics2D g2D = (Graphics2D) g ; 
            g2D.setColor(Color.RED);
            float dash1[] = {20.0f};
            BasicStroke dashed = new BasicStroke(3.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);
            g2D.setStroke(dashed);
            g2D.drawPolyline(canon.getXLigneViser(), canon.getYLigneViser(), 6);
    
        }

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
