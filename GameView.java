package PreProjet;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;

import javax.swing.Timer;


public class GameView extends JFrame{

    private static final int largeur = 500;
    private static final int hauteur = 500;

    JPanel panel = new JPanel();

    private Circle ball;

    GameView() {

    add(panel);
    setSize(largeur, hauteur);
    setTitle("Test");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    ball = new Circle(new Ball());
    add(ball);
    setVisible(true);    
    //animate();
    Timer timer = new Timer(100, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {

            System.out.print('a');

        }
    });

    timer.start();

    }

    // public void animate() {
    //     Timer chrono = new Timer();
    //     int seconds = 0;
    //     chrono.schedule(new TimerTask() {
    //         public void run(){
    //             ball.ball.updateBall(seconds);
    //             repaint();
    //         }
    //     },0,1);
    // }




    public static void main(String[] args) {

        GameView g = new GameView();
        
    }

    public class Circle extends JPanel{
        Ball ball;
        Circle (Ball b){
            ball=b;
        }
        public void paint(Graphics g){
            setSize(largeur,hauteur);
            g.setColor(Color.GREEN);
            g.fillOval((int)ball.ballX,(int)ball.ballY,(int)ball.ballRadius,(int)ball.ballRadius);
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

