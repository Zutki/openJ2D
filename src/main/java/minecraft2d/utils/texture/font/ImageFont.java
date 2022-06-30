package minecraft2d.utils.texture.font;

import java.awt.Color;
import java.awt.image.BufferedImage;

public interface ImageFont {
    /**
     * Converts a string into a drawable image
     * @param text the text to convert
     * @param color the color to draw it as
     * @param size the size multiplier at which to draw it at (Note: integers are read as if they have been divided by 10
     * @return the BufferedImage representation of the text
     */
    BufferedImage convertString(String text, Color color, int size);
}
