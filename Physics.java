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

    public void tick() {
        // replace 0.1f with a proper value
        int yOff = (int) Math.abs((player.position.y)+1) % 16;

        // chunk border gravity fixing
        if (yOff == 0 && chunks.get(new Point(currentChunk.x, currentChunk.y+1)).blocks[ (int) Math.abs(player.position.x) % 16 ][ yOff ] == null) {
            player.position.y = Tools.addFloat(player.position.y, 0.1f);
            inMovement = true;
        }
        else if (yOff != 0 && chunks.get(currentChunk).blocks[ (int) Math.abs(player.position.x) % 16 ][ yOff ] == null) {
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
