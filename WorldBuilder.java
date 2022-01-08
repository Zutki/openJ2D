import java.awt.Point;
import java.util.HashMap;

public class WorldBuilder {
    private final int terrainLevel;
    private HashMap<Point, Chunk> chunks;
    private int renderDistance;

    public WorldBuilder(HashMap<Point, Chunk> chunks, int terrainLevel, int renderDistance) {
        this.chunks = chunks;
        this.terrainLevel = terrainLevel;
        this.renderDistance = renderDistance;
    }

    public void generateNewChunks(Point startingPos) {
        for (int y = 0; y < renderDistance * 2 + 1; y++) {
            for (int x = 0; x < renderDistance * 2 + 1; x++) {
                int xOff = x - renderDistance;
                int yOff = y - renderDistance;
                
                Point workingChunk = new Point(startingPos.x + xOff, startingPos.y + yOff);
                if (chunks.containsKey(workingChunk) == false) {
                    chunks.put(workingChunk, new Chunk(workingChunk));
                }
            }
        }
    }

}
