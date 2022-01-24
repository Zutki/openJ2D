import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.Point;
import java.awt.Color;

/**
 * The Block class contains data on a 'block' in Minecraft.
 * @author Zutki
 */
public class Block {
    /**
     * The image of the block's texture.
     */
    private BufferedImage texture;

    /**
     * The position of where the block is located.
     */
    private Point position;
    /**
     * The Id of the block.
     */
    private final int id;

    /**
     * The drop item.
     */
    private final int dropItem;

   // TODO:
   //private final int hardness = 5;
   // ADVANCED TODO:
   // block sound

    /**
     * Instantiates a new Block.
     *
     * @param block_id the block's id
     * @param pos      the position of where to render the block
     */
    public Block(int block_id, Point pos) {
       position = pos;
       id = block_id;
       dropItem = block_id;
       texture = World.itemIDS.getItemImageByID(id);
   }

    /**
     * Draws block onto the screen.
     *
     * @param g        the graphics
     * @param observer the observer
     */
    public void drawBlock(Graphics g, ImageObserver observer) {

        g.drawImage(texture, position.x * World.BLOCK_SIZE, position.y * World.BLOCK_SIZE, observer);
   }

    /**
     * Draw block onto screen with a transparent black box rendered on top of it to simulate reduced brightness.
     *
     * @param opacity  the opacity of the black box
     * @param g        the graphics
     * @param observer the observer
     */
// draw the block with a black square overlaying it, so it looks darker
   public void drawBlock(float opacity, Graphics g, ImageObserver observer) {
       g.drawImage(texture, position.x * World.BLOCK_SIZE, position.y * World.BLOCK_SIZE, observer);
       Color col = g.getColor();
       g.setColor(new Color(0, 0, 0, opacity));
       g.fillRect(position.x * World.BLOCK_SIZE, position.y * World.BLOCK_SIZE, World.BLOCK_SIZE, World.BLOCK_SIZE);
       g.setColor(col);
   }

    /**
     * Gets position of the block.
     *
     * @return the position of the block
     */
    public Point getPos() {
       return position;
   }

    /**
     * Sets position of the block.
     *
     * @param pos the position of the block
     */
    public void setPos(Point pos) {
       position = pos;
   }
}
