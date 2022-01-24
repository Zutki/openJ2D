import java.awt.Point;

/**
 * The type World builder.
 * @author Zutki
 */
public class WorldBuilder {
    private final Block[][] blockArray;
    private final int terrainLevel;

    /**
     * Instantiates a new World builder.
     *
     * @param _blockArray   the block array
     * @param _terrainLevel the terrain level
     */
    public WorldBuilder(Block[][] _blockArray, int _terrainLevel) {
        blockArray = _blockArray;
        terrainLevel = _terrainLevel;
    }

    /**
     * Builds the world, which is made up of a 2D array of Blocks
     *
     * @return the Block[][]
     */
    public Block[][] buildWorld() {
        for (int y = blockArray.length-1; y > blockArray.length-(World.ROWS-terrainLevel)-1; y--) {
            for (int x = 0; x < blockArray[y].length; x++) {
                if (y == terrainLevel) {
                    blockArray[y][x] = new Block(1, new Point(x, y));
                }
                else {
                    blockArray[y][x] = new Block(0, new Point(x, y));
                }
            }
        }

        blockArray[5][5] = new Block(2, new Point(5,5));

        return blockArray;
    }

}
