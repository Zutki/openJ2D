import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class World extends JPanel implements ActionListener, KeyListener {
    // DEBUG MODE
    private boolean debugMode = true;

    // Tick delay (ms)
    private final int TICK_DELAY = 25;

    // Controls the size of blocks
    public static final int BLOCK_SIZE = 70;
    public static final int ROWS = 12;
    public static final int COLUMNS = 18;

    // make the id for EVERY component to use
    public static final Id itemIDS = new Id();

    // suppress serialization warning
    private static final long serialVersionUID = 490905409104883233L;

    private Player player;
    private Block[][] blocks = new Block[ROWS][COLUMNS]; // makes the blocks for the world

    // keep a reference to the timer object that triggers actionPerformed() in
    // case we need access to it in another method
    private Timer timer;
    
    // building the world
    public World() {
        // set the game world size
        setPreferredSize(new Dimension(BLOCK_SIZE * COLUMNS, BLOCK_SIZE * ROWS));
        
        // set the world background color (sky)
        setBackground(new Color(123, 167, 237));

        // make the player
        // for now only default
        player = new Player();

        timer = new Timer(TICK_DELAY, this);
        timer.start();

        // generate terrain
        for (int y = blocks.length-1; y > blocks.length-4; y--) {
            for (int x = 0; x < blocks[y].length; x++) {
                if (y == 9) {
                    blocks[y][x] = new Block(1, new Point(x, y));
                }
                else {
                    blocks[y][x] = new Block(0, new Point(x, y));
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        player.tick();
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // draw our graphics
        player.draw(g, this);
        Toolkit.getDefaultToolkit().sync();

        // draw terrain
        for (Block[] row: blocks) {
            for (Block block: row) {
                if (block != null) {
                    block.drawBlock(g, this);
                }
            }
        }

        // DEBUG
        if (debugMode) {
            for (int row = 0; row < ROWS; row++) {
                for (int col = 0; col < COLUMNS; col++) {
                    if ((row + col) % 2 == 1) {
                        g.drawRect(col * BLOCK_SIZE, row * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                    }
                }
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // this is not used but must be defined as part of the KeyListener interface
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // react to key down events
        player.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // react to key up events
    }
}
