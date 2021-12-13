import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class World extends JPanel implements ActionListener, KeyListener, MouseListener, MouseWheelListener {
    // DEBUG MODE
    private boolean debugMode = false;

    // Tick delay (ms)
    public static final int TICK_DELAY = 12;

    // Controls the size of blocks and the world
    public static final int BLOCK_SIZE = 70;
    public static final int ROWS = 12;
    public static final int COLUMNS = 18;

    // make the id for EVERY component to use
    public static final Id itemIDS = new Id();

    // suppress serialization warning
    private static final long serialVersionUID = 490905409104883233L;

    private Player player;
    private Block[][] blocks = new Block[ROWS][COLUMNS]; // makes the blocks for the world
    private Physics physics;

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
        physics = new Physics(blocks);
        player = new Player(physics);
        physics.setPlayer(player);

        timer = new Timer(TICK_DELAY, this);
        timer.start();

        // generate terrain
        WorldBuilder wb = new WorldBuilder(blocks, 8);
        blocks = wb.buildWorld();
    }
    
    // adds a block to the world
    //
    // if a block is already present where it is trying to add
    // it will not add the block if there is a block already 
    // present where it is trying to add
    private void addBlock(Point pos, int id) {
        if (blocks[pos.y][pos.x] == null) {
            blocks[pos.y][pos.x] = new Block(id, pos);
        }
    }

    
    // this function is executed every tick, tick is defined earlier in the code
    // timer is the runner of this function
    @Override
    public void actionPerformed(ActionEvent e) {
        physics.tick(); // physics tick
        repaint(); // redraw
    }
    
    // this function is drawing the world
    // this function is also called by repaint();
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
        // This draws a grid to represent all the block positions
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
    
    // reacts to a key being pressed
    @Override
    public void keyPressed(KeyEvent e) {
        // react to key down events
        player.keyPressed(e);
    }
    
    // reacts to a mouse button being pressed
    @Override
    public void mouseClicked(MouseEvent me) {
        int screenX = me.getX();
        int screenY = me.getY();
        
        Point blockCl = new Point((int) Math.round((double) screenX / BLOCK_SIZE - 0.5), (int) Math.round((double) screenY / BLOCK_SIZE - 1));
        
        // add a block when the user left clicks
        if (me.getButton() == MouseEvent.BUTTON1) {
            addBlock(blockCl, 2);
        }
        // remove a block when the user right clicks
        if (me.getButton() == MouseEvent.BUTTON3) {
            blocks[blockCl.y][blockCl.x] = null;
        }
        //System.out.println("screen(X,Y) = " + Math.round((double) screenX / BLOCK_SIZE - 0.5) + "," + Math.round((double) screenY / BLOCK_SIZE - 1));
    }
    // react to mouse wheel
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        System.out.println(e.getWheelRotation());
    }

    // Required overrides
    // these aren't currently used but 
    // are required because KeyEvent and MouseEvent are implements
    @Override
    public void keyTyped(KeyEvent e) {
        // this is not used but must be defined as part of the KeyListener interface
    }
    @Override
    public void keyReleased(KeyEvent e) {
        // react to key up events
    }
    @Override
    public void mousePressed(MouseEvent me) {
        // required
    }
    @Override
    public void mouseReleased(MouseEvent me) {
        // required
    }
    @Override
    public void mouseEntered(MouseEvent me) {
        // required
    }
    @Override
    public void mouseExited(MouseEvent me) {
        // required
    }
}
