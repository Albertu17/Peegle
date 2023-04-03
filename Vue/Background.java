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


  // Some code to initialize the background image.
  // Here, we use the constructor to load the image. This
  // can vary depending on the use case of the panel.
  public Background(String fileName, Court court) {
    this.court = court;
    this.court.setBackground(this);
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
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        }
        
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
      
      g.setFont(newFont.deriveFont(20f));
      g.setColor(Color.WHITE);
        //Use ARCADE_N.TTF font
        g.drawString("Score: "+court.getScore(), 10, 50);

    

    
}

}