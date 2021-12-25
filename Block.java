import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.Point;
import java.awt.Color;

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
       texture = World.itemIDS.getItemImageByID(id);
       
   }
    
   public void drawBlock(Graphics g, ImageObserver observer) {

        g.drawImage(texture, position.x * World.BLOCK_SIZE, position.y * World.BLOCK_SIZE, observer);
   }

   // draw the block with a black square overlaying it, so it looks darker
   public void drawBlock(float opacity, Graphics g, ImageObserver observer) {
       g.drawImage(texture, position.x * World.BLOCK_SIZE, position.y * World.BLOCK_SIZE, observer);
       Color col = g.getColor();
       g.setColor(new Color(0, 0, 0, opacity));
       g.fillRect(position.x * World.BLOCK_SIZE, position.y * World.BLOCK_SIZE, World.BLOCK_SIZE, World.BLOCK_SIZE);
       g.setColor(col);
   }

   public Point getPos() {
       return position;
   }

   public void setPos(Point pos) {
       position = pos;
   }
}
