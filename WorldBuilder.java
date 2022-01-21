import java.awt.Point;
import java.util.HashMap;

public class WorldBuilder {
    private int terrainLevel;
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
                

                // if the chunks is ungenerated
                if (chunks.containsKey(workingChunk) == false) {
                    Chunk chunk = new Chunk(workingChunk);
                    // go per block in a chunk
                    if ((int) (Math.random() * 2) + 1 == 2) {
                        //terrainLevel++;
                    }
                    else {
                        //terrainLevel--;
                    }

                    for (int row = 0; row < 16; row++) {
                        for (int col = 0; col < 16; col++) {
                            // set up the position of the block we are working with
                            Point blockPos = new Point(workingChunk.x * 16 + col, workingChunk.y * 16 + row);
                    
                            // grass block
                            if (blockPos.y == terrainLevel) {
                                chunk.blocks[row][col] = new Block(1, blockPos);
                            }
                            // dirt
                            if (blockPos.y > terrainLevel && blockPos.y < terrainLevel + 3) {
                                chunk.blocks[row][col] = new Block(0, blockPos);
                            }
                            // stone / ores
                            if (blockPos.y > terrainLevel + 2) {
                                int randomNumber = (int) (Math.random() * 50) + 1;
                                // iron ore
                                if (randomNumber == 1) {
                                    chunk.blocks[row][col] = new Block(11, blockPos);
                                }
                                // stone
                                else {
                                    chunk.blocks[row][col] = new Block(2, blockPos);
                                }
                            }
                        }
                    }
                    
                    // add the chunk
                    chunks.put(workingChunk, chunk);
                }
            }
        }
    }

}
