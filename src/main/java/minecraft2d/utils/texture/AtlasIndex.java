package minecraft2d.utils.texture;

import java.awt.Point;

public class AtlasIndex {
    public Point textureStart;
    public int width;
    public int height;
    public int atlasLayer;

    public AtlasIndex(Point textureStart, int width, int height, int atlasLayer) {
        this.textureStart = textureStart;
        this.width = width;
        this.height = height;
        this.atlasLayer = atlasLayer;
    }
}
