import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.Graphics2D;
import java.awt.AlphaComposite;

public class Tools {
    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
       Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH); 
       BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

       Graphics2D g2d = dimg.createGraphics();
       g2d.drawImage(tmp, 0, 0, null);
       g2d.dispose();

       return dimg;
   }
    
   // credit: http://www.java2s.com/Code/Java/2D-Graphics-GUI/MakeimageTransparency.htm
   public static BufferedImage makeImageTranslucent(BufferedImage source, float alpha) {
       BufferedImage target = new BufferedImage(source.getWidth(), source.getHeight(), java.awt.Transparency.TRANSLUCENT);
       // Get the images graphics
       Graphics2D g = target.createGraphics();
       // Set the Graphics composite to Alpha
       g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
       // Draw the image into the prepared reciver image
       g.drawImage(source, null, 0, 0);
       // let go of all system resources in this Graphics
       g.dispose();
       // Return the image
       return target;
   }
}
