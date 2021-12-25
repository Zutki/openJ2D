import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class World extends JPanel implements ActionListener, KeyListener, MouseListener, MouseWheelListener{
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

    private final Player player;
    private Block[][] blocks = new Block[ROWS][COLUMNS]; // makes the blocks for the world
    private final Block[][] backBlocks = new Block[ROWS][COLUMNS]; // back panel blocks
    private final Physics physics;
    
    // load ui
    private final Ui ui;

    // load the inventory
    private Inventory inv;

    // keep a reference to the timer object that triggers actionPerformed() in
    // case we need access to it in another method
    private Timer timer;

    // building the world
    public World(String username) {
        // set the game world size
        setPreferredSize(new Dimension(BLOCK_SIZE * COLUMNS, BLOCK_SIZE * ROWS));
        
        // set the world background color (sky)
        setBackground(new Color(123, 167, 237));
        
        // initialize the inventory
        inv = new Inventory();
        inv.setItem(new Item(itemIDS, 5), 0);
        inv.setItem(new Item(itemIDS, 2), 4);
        inv.setItem(new Item(itemIDS, 0), 3);
        // initialize the UI
        ui = new Ui(inv);

        // this code is here to fix an issue with the block not initially rendering when the game loads
        ui.moveSlot(-1);
        ui.moveSlot(1);

        // make the player
        physics = new Physics(blocks);
        player = new Player(physics, username);
        physics.setPlayer(player);

        timer = new Timer(TICK_DELAY, this);
        timer.start();

        // generate terrain
        WorldBuilder wb = new WorldBuilder(blocks, 8);
        blocks = wb.buildWorld();
    }
    
    // adds a block to the world
    
    // if a block is already present where it is trying to add
    // it will not add the block if there is a block already 
    // present where it is trying to add
    private void addBlock(Point pos, int id) {
        if (blocks[pos.y][pos.x] == null) {
            blocks[pos.y][pos.x] = new Block(id, pos);
        }
    }
    
    // adds a background block to the world
    private void addBackgroundBlock(Point pos, int id) {
        if (backBlocks[pos.y][pos.x] == null) {
            backBlocks[pos.y][pos.x] = new Block(id, pos);
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
        
        Toolkit.getDefaultToolkit().sync();
        
        // draw background Blocks
        for (Block[] row: backBlocks) {
            for (Block block: row) {
                if (block != null) {
                    block.drawBlock(0.5f, g, this);
                }
            }
        }

        player.draw(g, this);

        // draw terrain
        for (Block[] row: blocks) {
            for (Block block: row) {
                if (block != null) {
                    block.drawBlock(g, this);
                }
            }
        }
        // show an outline over the current block

        // get the mouse position
        Point mousePos = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(mousePos, this); // convert position from screen to local position
        Point blockHovered = new Point((int) Math.round((double) mousePos.x / BLOCK_SIZE - 0.5), (int) Math.round((double) mousePos.y / BLOCK_SIZE - 0.5)); // get the hovered block
        
        ui.setHoveredBlock(blockHovered, g, this);



        // load the UI on top of everything
        ui.loadUI(g, this);
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
        // if the user is holding alt while clicking then ignore this statement
        if (me.getButton() == MouseEvent.BUTTON1 &&
                (me.getModifiersEx() & InputEvent.ALT_DOWN_MASK) == 0) {
            // check if the block the user is clicking on is the player
            if (!blockCl.equals(player.getPos()) && !blockCl.equals(new Point(player.getPos().x, player.getPos().y + 1))) {
                int itemId = ui.getSelectedItem().item_id;
                if (itemId != -1) {
                    addBlock(blockCl, itemId);
                }
            }
        }
        
        // add a block to the background, these blocks are not considered in physics
        else if (me.getButton() == MouseEvent.BUTTON1 &&
                (me.getModifiersEx() & InputEvent.ALT_DOWN_MASK) != 0) {
            int itemId = ui.getSelectedItem().item_id;
            if (itemId != -1) {
                addBackgroundBlock(blockCl, itemId);
            }
        }

        // remove a block when the user right clicks and is not holding alt
        if (me.getButton() == MouseEvent.BUTTON3 &&
                (me.getModifiersEx() & InputEvent.ALT_DOWN_MASK) == 0) {
            blocks[blockCl.y][blockCl.x] = null;
        }
        // remove a block from the background
        else if (me.getButton() == MouseEvent.BUTTON3 && 
                (me.getModifiersEx() & InputEvent.ALT_DOWN_MASK) != 0) {
            backBlocks[blockCl.y][blockCl.x] = null;
        }
        //System.out.println("screen(X,Y) = " + Math.round((double) screenX / BLOCK_SIZE - 0.5) + "," + Math.round((double) screenY / BLOCK_SIZE - 1));
    }
    // react to mouse wheel
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        //System.out.println(e.getWheelRotation());
        ui.moveSlot(e.getWheelRotation());
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
