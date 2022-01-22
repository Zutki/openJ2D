import java.util.HashMap;
import java.awt.Point;
import java.awt.geom.Point2D;

public class Physics {
    private HashMap<Point, Chunk> chunks = new HashMap<Point, Chunk>();
    private Player player;
    private Point currentChunk;
    public boolean inMovement;


    public Physics(HashMap<Point, Chunk> chunks) {
        this.chunks = chunks;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
    public void setCurrentChunk(Point currentChunk) {
        this.currentChunk = currentChunk;
    }
    public void updateChunk(HashMap<Point, Chunk> chunks) {
        this.chunks = chunks;
    }

    public boolean canMoveInDirection(Point direction) {
        // left
        if (direction.equals(Tools.LEFT)) {
            if (Tools.isFloatWhole(Tools.subFloat(player.position.x, 1.5f))) {
                Point2D.Float blockToGet = new Point2D.Float(Tools.subFloat(player.position.x, 1.5f), player.position.y);
                Block block = chunks.get(currentChunk).blocks[ (int) Math.abs(blockToGet.y) % 16 ][ (int) Math.abs(blockToGet.x) % 16 ];
                if (block != null) {
                    return false;
                }
            }
        }
        // right
        if (direction.equals(Tools.RIGHT)) {
            if (Tools.isFloatWhole(Tools.addFloat(player.position.x, 0.5f))) {
                Point2D.Float blockToGet = new Point2D.Float(Tools.addFloat(player.position.x, 0.5f), player.position.y);
                Block block = chunks.get(currentChunk).blocks[ (int) Math.abs(blockToGet.y) % 16][ (int) Math.abs(blockToGet.x) % 16 ];
                if (block != null) {
                    return false;
                }
            }
        }
        // down
        if (direction.equals(Tools.DOWN)) {
            if (Tools.isFloatWhole(Tools.addFloat(player.position.y, 2.0f))) {
                Point2D.Float blockToGet = new Point2D.Float(player.position.x, Tools.addFloat(player.position.y, 2.0f));
                Block block = chunks.get(currentChunk).blocks[ (int) Math.abs(blockToGet.y) % 16][ (int) Math.abs(blockToGet.x) % 16 ];
                if (block != null) {
                    return false;
                }
            }
        }
        
        return true;
    }


    public void tick() {
        // replace 0.1f with a proper value
        int yOff = (int) Math.abs((player.position.y)+1) % 16;

        // chunk border gravity fixing
        if (yOff == 0 && chunks.get(new Point(currentChunk.x, currentChunk.y+1)).blocks[ yOff ][ (int) Math.abs(player.position.x) % 16 ] == null) {
            player.position.y = Tools.addFloat(player.position.y, 0.1f);
            inMovement = true;
        }
        else if (yOff != 0 && chunks.get(currentChunk).blocks[ yOff ][ (int) Math.abs(player.position.x) % 16 ] == null) {
            player.position.y = Tools.addFloat(player.position.y, 0.1f);
            inMovement = true;
        }
        else {
            inMovement = false;
        }

        //if (chunks.get(currentChunk).blocks[xOff][yOff-1] == null) {
            //player.position.y = Tools.addFloat(player.position.y, 0.1f);
        //}
    }
}
