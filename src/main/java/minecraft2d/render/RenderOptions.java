package minecraft2d.render;

import java.awt.*;

/**
 * Used for defining render options in the game such as the size of blocks (pixels) and the amount of rows and columns to draw
 * in the window.
 * @author Zutki
 * @version 0.0.1-ALPHA
 */
public class RenderOptions {
    public int blockSize;
    public int rows;
    public int cols;

    public RenderOptions() {
        blockSize = 70;
        rows = 12;
        cols = 18;
    }

    public RenderOptions(int blockSize, int rows, int cols) {
        this.blockSize = blockSize;
        this.rows = rows;
        this.cols = cols;
    }

    public Dimension getDimension() {
        return new Dimension(blockSize * cols, blockSize * rows);
    }
}
