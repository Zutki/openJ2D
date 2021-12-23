import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Player {
    
    private final File[] playerImages = {new File("assets/steve/SteveLeft.png"), new File("assets/steve/SteveFront.png"), new File("assets/steve/SteveRight.png")};
    // player image
    private BufferedImage image;
    private BufferedImage images[] = new BufferedImage[3];
    // current player position
    private Point position;
    private Physics phyx;
    
    // player data
    private int[] inventory = new int[9];
    // TODO: Implement inventory class

    // player default constructor
    public Player(Physics _phyx) {
        loadImages();
        phyx = _phyx;
        loadImage(1);
        // set the players inventory as empty
        for (int i = 0; i < inventory.length; i++) {
            inventory[i] = 0;
        }
        position = new Point(5, 5);
    }

    // load player image
    private void loadImages() {
        for (int i = 0; i < images.length; i++) {
            try {
                images[i] = ImageIO.read(playerImages[i]);
                images[i] = Tools.resize(images[i], World.BLOCK_SIZE, World.BLOCK_SIZE*2);
            } 
            catch (IOException exc) {
                System.out.println("Error opening player image: " + exc.getMessage());
            }
        }
        images[2] = Tools.getPlayerFacingLeft(new File("assets/steve/2021_12_21_blue-christmas-boy-19566197.png"));
        images[2] = Tools.resize(images[2], World.BLOCK_SIZE, World.BLOCK_SIZE*2);

        images[1] = Tools.getPlayerFacingFront(new File("assets/steve/2021_12_21_blue-christmas-boy-19566197.png"));
        images[1] = Tools.resize(images[1], World.BLOCK_SIZE, World.BLOCK_SIZE*2);
    }
    private void loadImage(int facing) {
        image = images[facing];
    }

    // credit: https://www.tabnine.com/code/java/methods/java.awt.image.BufferedImage/getScaledInstance

    public void draw(Graphics g, ImageObserver observer) {
        g.drawImage(image, position.x * World.BLOCK_SIZE, position.y * World.BLOCK_SIZE, observer);
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if ((key == KeyEvent.VK_UP || key == KeyEvent.VK_W) && phyx.canMoveUp() && phyx.canJump()) {
            position.translate(0, -1);
            phyx.resetCounter();
        }
        if ((key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) && phyx.canMoveRight()) {
            loadImage(0);
            position.translate(1, 0);
        }
        if ((key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) && phyx.canMoveDown()) {
            position.translate(0, 1);
            phyx.resetCounter();
        }
        if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
            loadImage(1);
        }
        if ((key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) && phyx.canMoveLeft()) {
            loadImage(2);
            position.translate(-1, 0);
        }
    }

    public Point getPos() {
        return position;
    }
    public void setPos(Point pos) {
        position = pos;
    }
}
