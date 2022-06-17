package minecraft2d.world.settings;

/**
 * Used for defining debug settings in the game
 * @author Zutki
 * @version 0.0.1-ALPHA
 */
public class DebugSettings {
    public boolean showInfoText;
    public boolean showChunkBorders;
    public boolean showBlockBoundaries;

    public DebugSettings() {
        showInfoText = false;
        showChunkBorders = false;
        showBlockBoundaries = false;
    }
    public DebugSettings(boolean showInfoText, boolean showChunkBorders, boolean showBlockBoundaries) {
        this.showInfoText = showInfoText;
        this.showChunkBorders = showChunkBorders;
        this.showBlockBoundaries = showBlockBoundaries;
    }
}
