import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class World2 extends JPanel implements ActionListener/*, KeyListener, MouseListener, MouseWheelListener*/ {
    private boolean debugMode = true;

    // Delay between frames
    public static final int TICK_DELAY = 12;

    // Controls the size of blocks and the world
    public static final int BLOCK_SIZE = 70;
    public static final int ROWS = 12;
    public static final int COLUMNS = 18;

    // id system, used by everything
    public static final Id itemIDS = new Id();

    // suppress serialization warning
    private static final long serialVersionUID = 490905409104883233L;
    
    // this block is the top left corner of the world viewing window
    // the purpose of this is for figuring out the positions to draw blocks at relative to the viewable area
    // example:
    // A block is at (170, 0) and the blockOffset is at (160, 0), the block will be drawn at (10, 0) relative to the viewable area
    private Point blockOffset = new Point(0, 0);

    // The current chunk the player is inside
    private Point currentChunk = new Point(0, 0);

    // number of chunks that will be simulated/rendered, some of these chunks are not visible
    private int renderDistance = 5;
    
    // the chunks that will be rendered
    private ArrayList<ArrayList<Chunk>> renderedChunks = new ArrayList<ArrayList<Chunk>>();

    // 2D ArrayList of chunks
    // it feels cursed to write this
    private ArrayList<ArrayList<Chunk>> chunks = new ArrayList<ArrayList<Chunk>>();

    private Timer timer;

    public World2() {
        // set the window size to fit the columns and rows
        setPreferredSize(new Dimension(BLOCK_SIZE * COLUMNS, BLOCK_SIZE * ROWS));

        // set the sky color
        setBackground(new Color(123, 167, 237));

        timer = new Timer(TICK_DELAY, this);
        timer.start();
        
        // generate spawn chunks, this is the size of render distance
        for (int row = 0; row < renderDistance*2+1; row++) {
            chunks.add(new ArrayList<Chunk>());
            for (int col = 0; col < renderDistance*2+1; col++) {
                chunks.get(row).add(col, new Chunk(new Point(col-renderDistance, row-renderDistance)));
            }
        }
    
        // DEBUG: print all the currently created chunks
        for (ArrayList<Chunk> chunkArray: chunks) {
            for (Chunk chunk: chunkArray) {
                System.out.print("("+chunk.position.x + ", "+chunk.position.y+")");
            }
            System.out.println();
        }

        // get the rendered chunks
        // this is an = because at world generation the generated chunks are the same size as the render distance
        renderedChunks = chunks;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Toolkit.getDefaultToolkit().sync();

        // drawing the blocks
        // loop through the rendered chunks
        for (ArrayList<Chunk> chunkArray: renderedChunks) {
            for (Chunk chunk: chunkArray) {
                // loop through the blocks in the chunk and draw them
                for (Block[] blocks: chunk.blocks) {
                    for (Block block: blocks) {
                        if (block != null) {
                            block.drawBlock(g, this);
                        }
                    }
                }
            }
        }

        if (debugMode) {
            // block grid
            g.setColor(Color.BLACK);
            for (int row = 0; row < ROWS; row++) {
                for (int col = 0; col < COLUMNS; col++) {
                    if ((row + col) % 2 == 1) {
                        g.drawRect(col * BLOCK_SIZE, row * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                    }
                }
            }

            // chunk grid
            g.setColor(Color.RED);

            for (ArrayList<Chunk> chunkArray: chunks) {
                for (Chunk chunk: chunkArray) {
                    g.drawRect(chunk.position.x * 16 * BLOCK_SIZE, chunk.position.y * 16 * BLOCK_SIZE, 16 * BLOCK_SIZE, 16 * BLOCK_SIZE);
                }
            }
        }
    }

    private ArrayList<ArrayList<Chunk>> getRenderedChunks() {
        ArrayList<ArrayList<Chunk>> renderChunks = new ArrayList<ArrayList<Chunk>>();
        
        for (int row = currentChunk.y; row < renderDistance * 2 + 1 + currentChunk.y; row++) {
            renderChunks.add(new ArrayList<Chunk>());
            for (int col = currentChunk.x; col < renderDistance * 2 + 1 + currentChunk.x; col++) {
                renderChunks.get(row).add(col, chunks.get(row).get(col));
            }
        }
        return renderChunks;
    }
}
