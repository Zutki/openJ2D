import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Ui {
    private static final int HOTBAR_WIDTH = World.BLOCK_SIZE * 9;
    private static final int HOTBAR_HEIGHT = World.BLOCK_SIZE;

    private static final File hotbar = new File("assets/ui/hotbar.png");
    private static final File selected = new File("assets/ui/selected.png");
    private static final File hoverBlock = new File("assets/stone.png");
    
    private BufferedImage hotbarImage;
    private BufferedImage selectedImage;
    private BufferedImage hoverBlockImg;

    private int selectedSlot = 0;

    //private Point hotbarPos = new Point((World.ROWS / 2) World.BLOCK_SIZE), (World.COLUMNS - 1) * World.BLOCK_SIZE);
    private Point hotbarPos = new Point((World.COLUMNS * World.BLOCK_SIZE) / 2, 0);

    public Ui() {
       loadHotbar(); 
       System.out.println(hotbarPos);
        

       // TEMP CODE
       // This will be replaced by a better implementation
       // For now it stays as there is no way to select blocks
       try {
           hoverBlockImg = ImageIO.read(hoverBlock);
           hoverBlockImg = Tools.resize(hoverBlockImg, World.BLOCK_SIZE, World.BLOCK_SIZE);
       }
       catch (IOException e)
       {}
       hoverBlockImg = Tools.makeImageTranslucent(hoverBlockImg, 0.25f);
    }
    
    // move up or down a slot by the mouse wheel (mwIn)
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
    }
    
    // load the image for the hotbar
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
    
    // draw the block the mouse is currently hovering over at pos
    private Point hovered;
    // this is needed because java likes to complain when it is not done this way
    public void setHoveredBlock(Point pos, Graphics g, ImageObserver observer) {
        hovered = pos;
        drawHoverBlock(g, observer);
    }
    
    // function called in setHoveredBlock to draw the block
    private void drawHoverBlock(Graphics g, ImageObserver observer) {
        g.drawImage(hoverBlockImg, hovered.x * World.BLOCK_SIZE, hovered.y * World.BLOCK_SIZE, observer);
        
    }
    

    // load all UI elements
    public void loadUI(Graphics g, ImageObserver observer) {
        // hotbar
        g.drawImage(hotbarImage, hotbarPos.x, hotbarPos.y, observer);
        g.drawImage(selectedImage, hotbarPos.x + HOTBAR_HEIGHT * selectedSlot, hotbarPos.y, observer);
    }

}
