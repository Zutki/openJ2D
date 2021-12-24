import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.Point;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

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
    
   public Block(int block_id, Point pos, float brightness) {
       position = pos;
       id = block_id;
       dropItem = block_id;
       texture = World.itemIDS.getItemImageByID(id);

       float[] elements = {brightness};
       Kernel kernel = new Kernel(1, 1, elements);
       ConvolveOp op = new ConvolveOp(kernel);
       BufferedImage temp = new BufferedImage(texture.getWidth(), texture.getHeight(), texture.getType());
       op.filter(texture, temp);
       texture = temp;
   }

   public void drawBlock(Graphics g, ImageObserver observer) {

        g.drawImage(texture, position.x * World.BLOCK_SIZE, position.y * World.BLOCK_SIZE, observer);
   }

   public Point getPos() {
       return position;
   }

   public void setPos(Point pos) {
       position = pos;
   }
}
