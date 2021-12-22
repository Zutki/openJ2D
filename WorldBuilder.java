import java.awt.Point;

public class WorldBuilder {
    private Block[][] blockArray;
    private int terrainLevel;

    public WorldBuilder(Block[][] _blockArray, int _terrainLevel) {
        blockArray = _blockArray;
        terrainLevel = _terrainLevel;
    }

    public Block[][] buildWorld() {
        for (int y = blockArray.length-1; y > blockArray.length-(World.ROWS-terrainLevel)-1; y--) {
            for (int x = 0; x < blockArray[y].length; x++) {
                if (y == terrainLevel) {
                    blockArray[y][x] = new Block(0, new Point(x, y));
                }
                else {
                    blockArray[y][x] = new Block(1, new Point(x, y));
                }
            }
        }

        blockArray[5][5] = new Block(2, new Point(5,5));

        return blockArray;
    }

}
