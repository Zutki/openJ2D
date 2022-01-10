import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.event.*;
import javax.swing.*;
import java.util.HashMap;

public class World extends JPanel implements ActionListener, KeyListener/*, MouseListener, MouseWheelListener*/ {
    private boolean debugMode = true;

    // Delay between frames in ms
    public static final int TICK_DELAY = 12;

    // Size of blocks and the number of blocks in the view frame
    public static int BLOCK_SIZE = 70;
    public static int ROWS = 12;
    public static int COLUMNS = 18;

    // the id system, used for block names and rendering
    public static final Id itemIDS = new Id();

    // suppress serialization warning
    private static final long serialVersionUID = 490905409104883233L;

    // this block is at the top left corner of the screen.
    // used to offset blocks being drawn
    private Point2D.Float blockOffset = new Point2D.Float(0.0f, 0.0f);

    // the current chunk the player is inside
    private Point currentChunk = new Point(0, 0);
    
    // number of chunks generated/simulated/rendered
    // making this number higher lags the game
    private int renderDistance = 2;

    // HashMap of all the chunks
    private HashMap<Point, Chunk> chunks = new HashMap<Point, Chunk>();

    private Timer timer;

    private Player player;

    private WorldBuilder worldBuilder;
    
    // framerate calculation
    private int frames = 0;
    private long start;
    private int framerate;

    public World() {
        // set the window size to fit the columns and rows
        setPreferredSize(new Dimension(BLOCK_SIZE * COLUMNS, BLOCK_SIZE * ROWS));

        // set the sky color
        setBackground(new Color(123, 167, 237));

        timer = new Timer(TICK_DELAY, this);
        timer.start();


        // instance the player
        player = new Player("");

        // setup world builder
        worldBuilder = new WorldBuilder(chunks, 5, renderDistance);

        // spawn chunk generation
        currentChunk = new Point(0, 0);
        worldBuilder.generateNewChunks(currentChunk);
        
        start = System.currentTimeMillis();

    }
    
    // run per frame
    @Override
    public void actionPerformed(ActionEvent e) {
        frames++;
        // only run when the player is moving
        if (player.keyPressed != null) {
            blockOffset.x = Tools.subFloat(player.position.x, COLUMNS / 2);
            blockOffset.y = Tools.subFloat(player.position.y, ROWS / 2);

            // java likes cracking some really banger jokes
            Point previousChunk = new Point(currentChunk.x, currentChunk.y);

            if (player.position.x < 0) {
                currentChunk.x = (int) player.position.x/16-1;
            }
            else {
                currentChunk.x = (int) player.position.x/16;
            }

            if (player.position.y < 0) {
                currentChunk.y = (int) player.position.y/16-1;
            }
            else {
                currentChunk.y = (int) player.position.y/16;
            }

            // this is run when the player enters a new chunk
            if (!previousChunk.equals(currentChunk)) {
                System.out.println("Entered New Chunk");
                worldBuilder.generateNewChunks(currentChunk);
            }
        }

        player.tick();
        repaint();

        // count the frames that happened within a second
        // when 1 second has passed reset the frame counter and reset the start time
        if (System.currentTimeMillis() - start >= 1000) {
            framerate = frames;
            frames = 0;
            start = System.currentTimeMillis();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        // boilerplate
        super.paintComponent(g);
        Toolkit.getDefaultToolkit().sync();

        // drawing blocks
        // this is done by reading the blocks around the player
        // TODO: optimise this bit of code
        for (int y = 0; y < renderDistance * 2 + 1; y++) {
            for (int x = 0; x < renderDistance * 2 + 1; x++) {
                int xOff = x-renderDistance; // example: 0 - 11 to -5 - 5
                int yOff = y-renderDistance; // same as above but for y

                Chunk workingChunk = chunks.get(new Point(xOff+currentChunk.x, yOff+currentChunk.y));
                for (Block[] blocks: workingChunk.blocks) {
                    for (Block block: blocks) {
                        if (block != null) {
                            block.drawBlock(g, this, blockOffset);
                        }
                    }
                }
            }
        }

        player.draw(g, this);

        // debug stuff
        if (debugMode) {
            drawDebugLines(g);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Noto Sans", Font.PLAIN, 20));
            g.drawString(String.format("Player Position: %.2f, %.2f @ Chunk: %d, %d", player.position.x, player.position.y, currentChunk.x, currentChunk.y) , 10, 20);
            g.drawString(String.format("FPS: %d", framerate), 10, 50);
        }
    }

    private void drawDebugLines(Graphics g) {
        for (Chunk chunk: chunks.values()) {
            // block grid
            g.setColor(Color.BLACK);
            for (int y = 0; y < 16; y++) {
                for (int x = 0; x < 16; x++) {
                    if ((y + x) % 2 == 1) {
                        g.drawRect((x + chunk.position.x*16) * BLOCK_SIZE - (int) (BLOCK_SIZE * blockOffset.x),
                                (y + chunk.position.y * 16) * BLOCK_SIZE - (int) (BLOCK_SIZE * blockOffset.y),
                                BLOCK_SIZE, BLOCK_SIZE);
                    }
                }
            }
            // chunk grid
            g.setColor(Color.RED);
            g.drawRect(chunk.position.x * 16 * BLOCK_SIZE - (int) (BLOCK_SIZE * blockOffset.x),
                    chunk.position.y * 16 * BLOCK_SIZE - (int) (BLOCK_SIZE * blockOffset.y),
                    16 * BLOCK_SIZE, 16 * BLOCK_SIZE);
        }
    }

    // react to key events
    // NOTE: slight issue with key reactions
    @Override
    public void keyPressed(KeyEvent e) {
        player.keyPressed = e;

        // debug teleportation
        if (e.getKeyCode() == KeyEvent.VK_F) {
            player.position = new Point2D.Float(11 * 16, player.position.y);
        }

        if (e.getKeyCode() == KeyEvent.VK_F1) {
            debugMode = !debugMode;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {
        player.keyPressed = null;
    }
}
