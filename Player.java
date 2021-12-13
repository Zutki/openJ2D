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
    
    private final File playerImage = new File("assets/steve_.png"); 
    // player image
    private BufferedImage image;
    // current player position
    private Point position;
    private Physics phyx;
    
    // player data
    private int[] inventory = new int[9];
    // TODO: Implement inventory class

    private int health;
    private int food;
    
    // player default constructor
    public Player(Physics _phyx) {
        phyx = _phyx;
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
            image = Tools.resize(image, World.BLOCK_SIZE, World.BLOCK_SIZE*2);
        } 
        catch (IOException exc) {
            System.out.println("Error opening player image: " + exc.getMessage());
        }
    }

    // credit: https://www.tabnine.com/code/java/methods/java.awt.image.BufferedImage/getScaledInstance

    public void draw(Graphics g, ImageObserver observer) {
        g.drawImage(image, position.x * World.BLOCK_SIZE, position.y * World.BLOCK_SIZE, observer);
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_UP && phyx.canMoveUp()) {
            position.translate(0, -1);
        }
        if (key == KeyEvent.VK_RIGHT && phyx.canMoveRight()) {
            position.translate(1, 0);
        }
        if (key == KeyEvent.VK_DOWN && phyx.canMoveDown()) {
            position.translate(0, 1);
        }
        if (key == KeyEvent.VK_LEFT && phyx.canMoveLeft()) {
            position.translate(-1, 0);
        }
        //System.out.println(position);
    }

    // executed every tick
    public void tick() {
        // prevent the player from moving off the edge of the board sideways
        if (position.x < 0) {
            position.x = 0;
        } else if (position.x >= World.COLUMNS) {
            position.x = World.COLUMNS - 1;
        }
        // prevent the player from moving off the edge of the board vertically
        if (position.y < 0) {
            position.y = 0;
        } else if (position.y >= World.ROWS) {
            position.y = World.ROWS - 1;
        }
    }

    public Point getPos() {
        return position;
    }
}
