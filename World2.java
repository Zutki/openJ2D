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

    private Chunk testChunk = new Chunk(new Point(0, 0));

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
        
        // add a horizontal array of chunks
        // this will be at (0, x)
        chunks.add(new ArrayList<Chunk>());
        
        // add the first chunk
        // this chunk is at 0, 0
        chunks.get(0).add(new Chunk(new Point(0, 0)));

        testChunk.blocks[4][4] = new Block(0, new Point(4, 4));
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Toolkit.getDefaultToolkit().sync();

        for (int blockY = 0; blockY < 16; blockY++) {
            for (int blockX = 0; blockX < 16; blockX++) {
                if (testChunk.blocks[blockY][blockX] != null) {
                    testChunk.blocks[blockY][blockX].drawBlock(g, this);
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
            g.drawRect(testChunk.position.x, testChunk.position.y, 16*BLOCK_SIZE, 16*BLOCK_SIZE);
        }
    }
}
