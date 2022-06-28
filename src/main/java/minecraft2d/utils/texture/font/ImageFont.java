package minecraft2d.utils.texture.font;

import java.awt.Color;
import java.awt.image.BufferedImage;

public interface ImageFont {
    public BufferedImage convertString(String text, Color color, int Size);
}
