import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.Graphics2D;

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
        position = new Point(5, 5);
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
            image = resize(image, World.BLOCK_SIZE, World.BLOCK_SIZE*2);
        } 
        catch (IOException exc) {
            System.out.println("Error opening player image: " + exc.getMessage());
        }
    }

    // credit: https://www.tabnine.com/code/java/methods/java.awt.image.BufferedImage/getScaledInstance
    private static BufferedImage resize(BufferedImage img, int newW, int newH) { 
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

    public void draw(Graphics g, ImageObserver observer) {
        g.drawImage(image, position.x * World.BLOCK_SIZE, position.y * World.BLOCK_SIZE, observer);
    }

    public Point getPos() {
        return position;
    }
}
