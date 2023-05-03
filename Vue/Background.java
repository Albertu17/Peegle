package Vue;

import javax.swing.JPanel;

import Modele.Ball;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;


public class Background extends JPanel{
    private BufferedImage backgroundImage;
    
    private Font newFont = ImageImport.arcade;
    private Court court;

  // score 
    private int longeur,largeur;
    private int midBordureCourtX,midBordureCourtY;
    private BufferedImage scoreSign;

    private int width;
    private int heigth;
    private int scoreMax;

    // balle
    private BufferedImage panneauBalle;
    private BufferedImage ball;
    private boolean GameOver = false;



  // Some code to initialize the background image.
  // Here, we use the constructor to load the image. This
  // can vary depending on the use case of the panel.
  public Background(String fileName, Court court, int h, int w) {
    heigth=h;
    width=w;
    this.court = court;
    this.court.setBackground(this);
    this.scoreMax = court.getScoreMax();
    Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
    System.out.println(size.width);
    System.out.println(size.height);
    if (ImageImport.getImage(fileName) != null){
        backgroundImage = ImageImport.getImage(fileName,size.width, size.height );
        for (int i = 0; i < 10; i++) {
        float[] blurKernel = { 1 / 16f, 2 / 16f, 1 / 16f, 2 / 16f, 4 / 16f, 2 / 16f, 1 / 16f, 2 / 16f, 1 / 16f };
        BufferedImageOp blur = new ConvolveOp(new Kernel(3, 3, blurKernel));
        backgroundImage = blur.filter(backgroundImage, null);
        
        }

        int bordureDroiteLargeur = (width-court.getWidth())/2 + court.getWidth();
        int bordureDroiteHauteur = (heigth-court.getHeight())/2 + court.getHeight();
    
        midBordureCourtX = bordureDroiteLargeur + 30; 
        midBordureCourtY = bordureDroiteHauteur - court.getHeight() + 50;
        largeur = (width -30) - midBordureCourtX;
        longeur =  (bordureDroiteHauteur - 50) - midBordureCourtY;
        scoreSign = ImageImport.getImage("scoreSign.png", largeur -30, 60);
        ball = ImageImport.getImage("ball.png", 50, 50);

        panneauBalle = ImageImport.getImage("Menu/planche_blanche.png", largeur, 60);

        ball = court.getBall();

    }
    else{
        System.out.println("Image not found");
    }
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    
    Graphics2D g2d = (Graphics2D) g;
    

    // Draw the background image.
    g2d.drawImage(backgroundImage, null, 3, 3);
    if (GameOver){
      return;
    }
    if (newFont != null) {
    g.setFont(newFont.deriveFont(20f));
    }
    g.setColor(Color.WHITE);
      //Use ARCADE_N.TTF font
     g.drawString("Score: "+court.getScore(), 10, 50);


    


    // score 
    g.drawRect(midBordureCourtX , midBordureCourtY, largeur, longeur);
    g.drawImage(scoreSign,midBordureCourtX +15 ,(midBordureCourtY - scoreSign.getHeight() -5),this);
    int scorebarre = court.getScore();
    if (scorebarre>scoreMax) scorebarre = scoreMax;
    int score = 0 ;
    if (scoreMax != 0) score = scorebarre * longeur / scoreMax;
    int point = midBordureCourtY+longeur-score;

    if (score<=longeur*1/3){
      g.setColor(Color.RED);
    }
    else if (score<=longeur*2/3){
      g.setColor(Color.ORANGE);
    }
    else {
      g.setColor(Color.GREEN);
    }
    g.fillRect(midBordureCourtX+1,point,largeur-1,(midBordureCourtY+longeur)-point);

    // balle 



    int ligne = 0;
    int pointDeDepartX = 40;
    int pointDeDepartY = court.getY() + court.getHeight() - 45;
    int XBall = pointDeDepartX - Ball.ballRadius;

    // for (int i = 0; i<court.getNbDeBall();i++){
    //   if (i==2) ligne++;
    //   else if (i-3%5==0) ligne++;
    //   g.drawImage(ball,pointDeDepartX,(pointDeDepartY- Ball.ballRadius*2),this);

      
    // }
    for (int i = 0; i<court.getNbDeBall();i++){
      if (i%5==0) ligne++;
      g.drawImage(ball,XBall+ i%5 * (Ball.ballRadius*2 + 10),pointDeDepartY - ligne * (Ball.ballRadius*2 + 10),this);
    }
    g.setColor(Color.WHITE);
    g.drawRect(XBall- 10, pointDeDepartY- ligne * (Ball.ballRadius*2 + 10) - 10, 5* (Ball.ballRadius*2 + 10) + 10, ligne * (Ball.ballRadius*2 + 10) );
    g.drawImage(panneauBalle, XBall, pointDeDepartY - ligne * (Ball.ballRadius*2 + 10) - 80, this);
    g .setFont(newFont.deriveFont(18f));
    g.drawString("Balle ", XBall + 10, pointDeDepartY - ligne * (Ball.ballRadius*2 + 10) - 60);
    g.drawString("x"+court.getNbDeBall(), XBall + 10, pointDeDepartY - ligne * (Ball.ballRadius*2 + 10) - 40);
    
    

    


    

    
}
public void setOver(boolean gameOver) {
  GameOver = gameOver;

}
}