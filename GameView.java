

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

import javax.swing.Timer;



public class GameView extends JFrame{

    private int width;
    private int heigth;



    //private Circle ball;
    private ArrayList<Ball> balls=new ArrayList<>();
    private ArrayList<Rectangle> rectanlgle=new ArrayList<>();


    GameView(int w,int h) {

    width=w;
    heigth=h;

    setSize(width, heigth);
    setTitle("Test");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // balls.add(new Ball(220,3,2,0,this));
    // rectanlgle.add(new Rectangle(100, 300, 300,45));

    balls.add(new Ball(250,3,5,0,this));
    rectanlgle.add(new Rectangle(100, 300, 500,45));

    
    // balls.add(new Ball(0,3,100,0,this));
    // rectanlgle.add(new Rectangle(200, 300, 300,-45));

    // balls.add(new Ball(300,400,-100,-100,this));
    // rectanlgle.add(new Rectangle(0, 100, 300,-45));


    // balls.add(new Ball(20,30,10,-10,this));

    // balls.add(new Ball(60,10,75,10,this));
    // balls.add(new Ball(100,30,-65,10,this));

    // balls.add(new Ball(200,10,-100,10,this));
    // balls.add(new Ball(250,30,10,100,this));

    // balls.add(new Ball(400,10,10,10,this));
    // balls.add(new Ball(300,30,10,200,this));
    Shapes s = new Shapes();
    add(s);
    setVisible(true);    
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
                    b.updateBall((last-now)*1.0e-9);
                }
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







    public static void main(String[] args) {

       GameView g = new GameView(500,500);
       

    }

    public class Shapes extends JPanel{
  

    public void paint(Graphics g){
        setSize(width,heigth);
        g.setColor(Color.BLACK);
        for (Ball ball:balls) g.fillOval((int)ball.ballX,(int)ball.ballY,(int)ball.ballRadius,(int)ball.ballRadius);


        g.setColor(Color.RED);
        for (Rectangle rect:rectanlgle) {
            g.drawLine(rect.x0, rect.y0, rect.caculX1(), rect.caculY1());}
    }
    } 
}
