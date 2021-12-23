import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.Graphics2D;
import java.awt.AlphaComposite;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

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

   // Player image decoding from skin image
   public static BufferedImage getPlayerFacingFront(File skinImage) {
       // define some buffered images for different parts of the body
       BufferedImage bi = null;
       BufferedImage torso;
       BufferedImage head;
       BufferedImage lArm;
       BufferedImage rArm;
       BufferedImage lLeg;
       BufferedImage rLeg;
        
       // crop out the parts of the image we need
       try {
           bi = ImageIO.read(skinImage);
       }
       catch (IOException e) {
           System.out.println(e);
       }
       
       torso = bi.getSubimage(20, 20, 8, 12);
       head = bi.getSubimage(8, 8, 8, 8);
       lArm = bi.getSubimage(36, 52, 4, 12);
       rArm = bi.getSubimage(44, 20, 4, 12);
       lLeg = bi.getSubimage(20, 52, 4, 12);
       rLeg = bi.getSubimage(4, 20, 4, 12);
       
       // draw the player
       int height = 32;
       int width = 16;

       BufferedImage playerImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
       Graphics2D g2d = playerImg.createGraphics();
       
       // head
       g2d.drawImage(head, 4, 0, null);
       
       // torso
       g2d.drawImage(torso, 4, 8, null);
        
       // left arm
       g2d.drawImage(lArm, 0, 8, null);
        
       // right arm
       g2d.drawImage(rArm, 12, 8, null);
        
       // left leg
       g2d.drawImage(lLeg, 4, 20, null);
    
       // right leg
       g2d.drawImage(rLeg, 8, 20, null);
        
       // return the image
       g2d.dispose();
       return playerImg;
   }

   public static BufferedImage getPlayerFacingLeft(File skinImage) {
       // define parts of the image
       BufferedImage bi = null;
       BufferedImage head;
       BufferedImage arm;
       BufferedImage leg;

       // load image
       try {
           bi = ImageIO.read(skinImage);
       }
       catch (IOException e) {
           System.out.println(e);
       }

       // crop full image to get body parts
       head = bi.getSubimage(16, 8, 8, 8);
       arm = bi.getSubimage(40, 52, 4, 12);
       leg = bi.getSubimage(24, 52, 4, 12);
        
       // draw the player
       int height = 32;
       int width = 8;

       BufferedImage playerImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
       Graphics2D g2d = playerImg.createGraphics();

       // head
       g2d.drawImage(head, 0, 0, null);

       // arm
       g2d.drawImage(arm, 4, 8, null);

       // leg
       g2d.drawImage(leg, 12, 12, null);

       // return image
       g2d.dispose();
       return playerImg;
   }
}
