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
   private int dropItem;

   // TODO:
   //private final int hardness = 5;
   // ADVANCED TODO:
   // block sound
    
   public Block(int block_id, Point pos) {
       position = pos;
       id = block_id;
       dropItem = block_id;
       
   }
   public Block(int block_id, Point pos, int drop_item_id) {
       id = block_id;
       position = pos;
       dropItem = drop_item_id;
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
   }
}
