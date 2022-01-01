import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;

// image handleing
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Player {
    // images for the player
    private final File playerDefaultImage = new File("assets/steve/steve.png");
    
    private BufferedImage[] playerImages = new BufferedImage[3];
    private BufferedImage currentPlayerImage;
    
    // other variables
    public Point2D.Float position; 
    private float movementDistance = 0.1f; // Distance that is moved per keypress
    
    public KeyEvent keyPressed;

    public Player(String username) {
        // load the skin
        loadImages(username);

        position = new Point2D.Float(World.COLUMNS / 2, World.ROWS/2); // center of the screen / center of the the first chunk
        
        currentPlayerImage = playerImages[1]; // set the image as front facing
    }

    // image loading
    private void loadImages(String username) {
        if (username.equals("")) {
            BufferedImage playerSkin = null;
            try {
                playerSkin = ImageIO.read(playerDefaultImage);
            }
            catch (IOException e) {
                System.out.println(e);
            }

            playerImages[0] = Tools.getPlayerFacingLeft(playerSkin);
            playerImages[1] = Tools.getPlayerFacingFront(playerSkin);
            playerImages[2] = Tools.getPlayerFacingRight(playerSkin);
        } else {
            BufferedImage playerSkin = Tools.fetchMinecraftSkin(username);
            playerImages[0] = Tools.getPlayerFacingLeft(playerSkin);
            playerImages[1] = Tools.getPlayerFacingFront(playerSkin);
            playerImages[2] = Tools.getPlayerFacingRight(playerSkin);
        }

        for (int i = 0; i < playerImages.length; i++) {
            playerImages[i] = Tools.resize(playerImages[i], World.BLOCK_SIZE, World.BLOCK_SIZE*2);
        }
    }

    public void draw(Graphics g, ImageObserver observer) {
        g.drawImage(currentPlayerImage, (int) (World.COLUMNS * World.BLOCK_SIZE/2-(World.BLOCK_SIZE/2)), (int) (World.ROWS * World.BLOCK_SIZE/2 - (World.BLOCK_SIZE)), observer);
    }

    public void tick() {
        if (keyPressed != null) {
            if (keyPressed.getKeyCode() == KeyEvent.VK_A || keyPressed.getKeyCode() == KeyEvent.VK_LEFT) {
                position.x = Tools.subFloat(position.x, movementDistance);
            }
            if (keyPressed.getKeyCode() == KeyEvent.VK_D || keyPressed.getKeyCode() == KeyEvent.VK_RIGHT) {
                position.x = Tools.addFloat(position.x, movementDistance);
            }
        }
    }

}
