

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

    private static final int largeur = 500;
    private static final int hauteur = 500;



    //private Circle ball;
    private ArrayList<Ball> balls=new ArrayList<>();

    GameView() {

    setSize(largeur, hauteur);
    setTitle("Test");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    balls.add(new Ball(10,10,-10,10));
    balls.add(new Ball(20,30,10,-10));

    balls.add(new Ball(60,10,75,10));
    balls.add(new Ball(100,30,-65,10));

    balls.add(new Ball(200,10,-100,10));
    balls.add(new Ball(250,30,10,100));

    balls.add(new Ball(400,10,10,10));
    balls.add(new Ball(300,30,10,200));
    Shapes ball = new Shapes();
    add(ball);
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
                //ball.ball.updateBall((last-now)*1.0e-9);
                for (Ball b:balls){
                    b.updateBall((last-now)*1.0e-9);
                }
                repaint();
                now=last;
                
            }
        });
        timer.start();
    }







    public static void main(String[] args) {

        GameView g = new GameView();
        
    }

    public class Shapes extends JPanel{
  

    public void paint(Graphics g){
        // setSize(largeur,hauteur);
        // g.setColor(Color.BLACK);
        // g.fillOval((int)ball.ballX,(int)ball.ballY,(int)ball.ballRadius,(int)ball.ballRadius);

        setSize(largeur,hauteur);
        g.setColor(Color.BLACK);
        for (Ball ball:balls) g.fillOval((int)ball.ballX,(int)ball.ballY,(int)ball.ballRadius,(int)ball.ballRadius);
    }
    } 
}

























    // // class parameters
    // private final Court court;
    // private final Pane gameRoot; // main node of the game
    // private final double scale;
    // private final double xMargin = 50.0, racketThickness = 10.0; // pixels

    // // children of the game main node
    // private final Rectangle racketA, racketB;
    // private final Circle ball;

    // /**
    //  * @param court le "modèle" de cette vue (le terrain de jeu de raquettes et tout ce qu'il y a dessus)
    //  * @param root  le nœud racine dans la scène JavaFX dans lequel le jeu sera affiché
    //  * @param scale le facteur d'échelle entre les distances du modèle et le nombre de pixels correspondants dans la vue
    //  */
    // public GameView(Court court, Pane root, double scale) {
    //     this.court = court;
    //     this.gameRoot = root;
    //     this.scale = scale;

    //     root.setMinWidth(court.getWidth() * scale + 2 * xMargin);
    //     root.setMinHeight(court.getHeight() * scale);

    //     racketA = new Rectangle();
    //     racketA.setHeight(court.getRacketSize() * scale);
    //     racketA.setWidth(racketThickness);
    //     racketA.setFill(Color.BLACK);

    //     racketA.setX(xMargin - racketThickness);
    //     racketA.setY(court.getRacketA() * scale);

    //     racketB = new Rectangle();
    //     racketB.setHeight(court.getRacketSize() * scale);
    //     racketB.setWidth(racketThickness);
    //     racketB.setFill(Color.BLACK);

    //     racketB.setX(court.getWidth() * scale + xMargin);
    //     racketB.setY(court.getRacketB() * scale);

    //     ball = new Circle();
    //     ball.setRadius(court.getBallRadius());
    //     ball.setFill(Color.BLACK);

    //     ball.setCenterX(court.getBallX() * scale + xMargin);
    //     ball.setCenterY(court.getBallY() * scale);

    //     gameRoot.getChildren().addAll(racketA, racketB, ball);


    // }

    // public void animate() {
    //     new AnimationTimer() {
    //         long last = 0;

    //         @Override
    //         public void handle(long now) {
    //             if (last == 0) { // ignore the first tick, just compute the first deltaT
    //                 last = now;
    //                 return;
    //             }
    //             court.update((now - last) * 1.0e-9); // convert nanoseconds to seconds
    //             last = now;
    //             racketA.setY(court.getRacketA() * scale);
    //             racketB.setY(court.getRacketB() * scale);
    //             ball.setCenterX(court.getBallX() * scale + xMargin);
    //             ball.setCenterY(court.getBallY() * scale);
    //         }
    //     }.start();
    // }

