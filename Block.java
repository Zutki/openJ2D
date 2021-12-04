import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.imageio.ImageIO;
import java.awt.Image;
import java.awt.Graphics2D;

public class Block {
   private BufferedImage texture;
   private Point position;
   private int id;

   // TODO:
   //private final int hardness = 5;
   // ADVANCED TODO:
   // block sound
    
   public Block(int block_id, Point pos) {
       position = pos;
       
   }

   public Point getPos() {
       return position;
   }

   public void setPos(Point pos) {
       position = pos;
   }

   public void break() {
       // TODO:
       // remove self
       // report to world that we dropped
   }
}
