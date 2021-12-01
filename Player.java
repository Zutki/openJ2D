import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Player {
    
    private final File playerImage = new File("assets/carved_pumpkin.png"); 
    // player image
    private BufferedImage image;
    // current player position
    private Point position;
    
    // player data
    private int[] inventory = new int[9];
    private int health;
    private int food;
    
    // player default constructor
    public Player() {
        loadImage();
        // set the players inventory as empty
        for (int i = 0; i < inventory.length; i++) {
            inventory[i] = 0;
        }
        health = 20;
        food = 20;
        position = new Point(500, 500);
    }
    // player constructor for save files
    public Player(int[] inv, int h, int f, Point pos) {
        loadImage();
        inventory = inv;
        health = h;
        food = f;
        position = pos;
    }

    // load player image
    private void loadImage() {
        try {
            image = ImageIO.read(playerImage);
        } 
        catch (IOException exc) {
            System.out.println("Error opening player image: " + exc.getMessage());
        }
    }

    public void draw(Graphics g, ImageObserver observer) {
        g.drawImage(image, position.x, position.y, observer);
    }

    public Point getPos() {
        return position;
    }
}
