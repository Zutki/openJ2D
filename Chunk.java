import java.awt.Point;

public class Chunk {
    public Block[][] blocks = new Block[16][16];
    public Point position;
    
    public Chunk(Point pos) {
        position = pos;
    }
    public Chunk(int x, int y) {
        position = new Point(x, y);
    }

    public Chunk(Point pos, Block[][] blocks) {
        this.blocks = blocks;
        position = pos;
    }
    public Chunk(int x, int y, Block[][] blocks) {
        this.blocks = blocks;
        position = new Point(x, y);
    }

}
