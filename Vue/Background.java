package Vue;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


public class Background extends JPanel{
    private BufferedImage backgroundImage;
    private BufferedImage ball;
    private InputStream targetStream;
    private Font newFont;
    private Court court;


    private int longeur,largeur;
    private int midBordureCourtX,midBordureCourtY;
    private BufferedImage scoreSign;
    private int ancien_point = 0;

    private int width;
    private int heigth;
    private int scoreMax;


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
        
        try {
            targetStream = new FileInputStream("./Vue/Font/ARCADE_N.TTF");
            newFont =  Font.createFont(Font.TRUETYPE_FONT, targetStream);
        } catch (FontFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Font not found");
            e.printStackTrace();
        }
        }

        int bordureDroiteLargeur = (width-court.getWidth())/2 + court.getWidth();
        int bordureDroiteHauteur = (heigth-court.getHeight())/2 + court.getHeight();
    
        midBordureCourtX = bordureDroiteLargeur + 30; 
        midBordureCourtY = bordureDroiteHauteur - court.getHeight() + 50;
        largeur = (width -30) - midBordureCourtX;
        longeur =  (bordureDroiteHauteur - 50) - midBordureCourtY;
        scoreSign = ImageImport.getImage("scoreSign.png", largeur -30, 60);
        ball = ImageImport.getImage("ball.png", 50, 50);

    }
    else{
        System.out.println("Image not found");
    }
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    // Draw the background image.
    Graphics2D g2d = (Graphics2D) g;
    g2d.drawImage(backgroundImage, null, 3, 3);
    if (newFont != null) {
    g.setFont(newFont.deriveFont(20f));
    }
    g.setColor(Color.WHITE);
      //Use ARCADE_N.TTF font
     g.drawString("Score: "+court.getScore(), 10, 50);
    


    g.drawRect(midBordureCourtX , midBordureCourtY, largeur, longeur);
    g.drawImage(scoreSign,midBordureCourtX +15 ,(midBordureCourtY - scoreSign.getHeight() -5),this);


    

    int score = court.getScore() * longeur / scoreMax;
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
    

    //g.fillRect(midBordureCourtX+1,point,largeur-1,(midBordureCourtY+longeur)-point);
    

    
}

}