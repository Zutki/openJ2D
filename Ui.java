import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * UI Class contains functions for displaying and loading elements into the hotbar.
 *
 * @author Zutki
 */
public class Ui {

    /**
     * The width of the hotbar.
     */
    private static final int HOTBAR_WIDTH = World.BLOCK_SIZE * 9;

    /**
     * The height of the hotbar.
     */
    private static final int HOTBAR_HEIGHT = World.BLOCK_SIZE;

    /**
     * The image of the hotbar.
     */
    private static final File hotbar = new File("assets/ui/hotbar.png");
    /**
     * The image of a selected element in a hotbar.
     */
    private static final File selected = new File("assets/ui/selected.png");

    /**
     * The Hotbar image.
     */
    private BufferedImage hotbarImage;
    /**
     * The Selected image.
     */
    private BufferedImage selectedImage;
    /**
     * The Hover block img.
     */
    private BufferedImage hoverBlockImg;

    /**
     * The Selected slot. Its default value is 0, the first slot of the hotbar.
     */
    private int selectedSlot = 0;

    /**
     * Declares an Inventory class.
     */
    private final Inventory inv;

    /**
     * The Hotbar position.
     */
    private final Point hotbarPos = new Point((World.COLUMNS * World.BLOCK_SIZE) / 2, 0);
//private Point hotbarPos = new Point((World.ROWS / 2) World.BLOCK_SIZE), (World.COLUMNS - 1) * World.BLOCK_SIZE);

    /**
     * Instantiates a new Ui class.
     *
     * @param _inv the inv
     */
    public Ui(Inventory _inv) {
       loadHotbar(); 
       inv = _inv;
    }

    /**
     * Gets the currently selected block.
     *
     * @return the selected item
     */
    public Item getSelectedItem() {
        return inv.getItem(selectedSlot);
    }

    /**
     * Move up or down a slot by the mouse wheel
     *
     * @param mwIn an integer indicated the mouse wheel's position
     */
    public void moveSlot(int mwIn) {
        // scrolling down
        if (mwIn == -1) {
            // prevent indexOutOfRange error
            if (selectedSlot == 0) {
                selectedSlot = 8;
            }
            else {
                selectedSlot += mwIn;
            }
        }
        // scrolling up
        else {
            // prevent indexOutOfRange error
            if (selectedSlot == 8) {
                selectedSlot = 0;
            }
            else {
                selectedSlot += mwIn;
            }
        }
        if (inv.getItem(selectedSlot).image != null) {
            hoverBlockImg = Tools.makeImageTranslucent(inv.getItem(selectedSlot).image, 0.25f);
        }
        else {
            hoverBlockImg = null;
        }
    }

    /**
     * Load the image for the hotbar
     */
    private void loadHotbar() {

        try {
            // load hotbar
            hotbarImage = ImageIO.read(hotbar);
            hotbarImage = Tools.resize(hotbarImage, HOTBAR_WIDTH, HOTBAR_HEIGHT);
            
            // load the image for the selected hotbar slot
            selectedImage = ImageIO.read(selected);
            selectedImage = Tools.resize(selectedImage, HOTBAR_HEIGHT, HOTBAR_HEIGHT);
        }
        catch (IOException e) {
            System.out.println("Error loading UI image: "+e);
        }

    }

    /**
     * draw the block the mouse is currently hovering over at pos.
     */
    private Point hovered;

    /**
     * Sets hovered block. This is needed because java likes to complain when it is not done this way
     *
     * @param pos      the position to set the hovered block at
     * @param g        the graphics
     * @param observer the observer
     */
    public void setHoveredBlock(Point pos, Graphics g, ImageObserver observer) {
        hovered = pos;
        drawHoverBlock(g, observer);
    }

    /**
     * Draw hover block. This function called in setHoveredBlock to draw the block
     *
     * @param g        the graphics
     * @param observer the observer
     */
    private void drawHoverBlock(Graphics g, ImageObserver observer) {
        g.drawImage(hoverBlockImg, hovered.x * World.BLOCK_SIZE, hovered.y * World.BLOCK_SIZE, observer);
        
    }


    /**
     * Loads all the  UI elements.
     *
     * @param g        the graphics
     * @param observer the observer
     */
    public void loadUI(Graphics g, ImageObserver observer) {
        // hotbar
        g.drawImage(hotbarImage, hotbarPos.x, hotbarPos.y, observer);
        g.drawImage(selectedImage, hotbarPos.x + HOTBAR_HEIGHT * selectedSlot, hotbarPos.y, observer);
        
        // items in hotbar
        BufferedImage[] items = inv.getUiImages();
        for (int i = 0; i < inv.getHotbar().length; i++) {
            if (items[i] != null) {
                g.drawImage(items[i], hotbarPos.x + HOTBAR_HEIGHT * i + Item.IMAGE_OFFSET/2, hotbarPos.y + Item.IMAGE_OFFSET/2, observer);
            }
        }
    }

}
