package minecraft2d.utils.texture.font;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Font reader processes the files Minecraft uses for its fonts, such as ascii.png
 * The way it is meant to be used is that upon construction the file type you want processes gets passed
 * <br>
 * Once constructed the getFont() method can be used to draw the text
 *
 * @author Zutki
 * @gameVersion 0.0.1-Alpha
 * @version 0.0.1
 */
public class FontReader {
    public static final Logger LOGGER = LoggerFactory.getLogger(FontReader.class);

    /**
     * The value used for ascii.png type textures
     */
    public static final int ASCII = 0;
    // TODO: Add more types

    private final BufferedImage fontImage;
    private ImageFont imageFont;

    /**
     * Constructs the font reader
     * @param fontImage the image to reader, ex: ascii.png
     * @param type the type of file you are attempting to process
     */
    public FontReader (BufferedImage fontImage, int type) {
        this.fontImage = fontImage;
        switch (type) {
            case ASCII:
                if (fontImage.getWidth() < 128) { // the math here might have issues with images smaller than 128 pixels, warn the user about this
                    LOGGER.warn("ascii.png images smaller than 128 pixels can cause issues.");
                }
                imageFont = generateAscii();
                break;
        }
    }

    private ImageFont generateAscii() {
        int[][] lookUpTable = { // This lookup table exists for the reason of each character in minecraft tends to have the same size regardless of rp
                                // another reason it exists is because Minecraft's font is non-monospace so each character can have variable width
                                // the way the lookup table is used is that each number is the numerator out of 8, this will be setup in a proportion to figure out how many
                                // pixels are responsible for each char
             //  0  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15
                {8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8}, // 0
                {8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8}, // 1
                {8, 1, 3, 5, 5, 5, 5, 1, 3, 3, 3, 5, 1, 5, 1, 5}, // 2
                {5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 1, 1, 4, 5, 5, 5}, // 3
                {5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5}, // 4
                {5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 3, 5, 3, 5, 5}, // 5
                {2, 5, 5, 5, 5, 5, 4, 5, 5, 1, 5, 4, 2, 5, 5, 5}, // 6
                {5, 5, 5, 5, 3, 5, 5, 5, 5, 5, 5, 3, 1, 3, 6, 8}, // 7
                {8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8}, // 8
                {8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 5, 8, 8, 5}, // 9
                {8, 8, 8, 8, 8, 8, 4, 4, 8, 8, 5, 8, 8, 8, 6, 6}, // 10
                {8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8}, // 11
                {8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8}, // 12
                {8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8}, // 12
                {8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 7, 5, 8}, // 13
                {6, 5, 5, 5, 8, 8, 5, 6, 4, 8, 8, 8, 4, 4, 8, 8}  // 14
        };

        BufferedImage[] charImgArray = new BufferedImage[256]; // there are 256 characters in ascii, the image provides as such, this will be used to create the ImageFont

        // the offset per character, since the image is a square,
        // and each character is the same width/height we can determine a pixel offset
        int charSize = fontImage.getWidth() / 16;
        int paddingSize = charSize / 8; // the equivalent of 1 pixel in an 8x8 character, which is what everything here is proportional to
                                        // also note that the reason errors can prop up for images less than 128 px is because it will produce values like 0.5 from
                                        // proportion, which since we are working with ints, they will be interpreted as 0 and cause issues

        int i = 0;
        for (int y = 0; y < 16; y++) {
            for (int x = 0; x < 16; x++) {
                BufferedImage img = fontImage.getSubimage(x * charSize, y * charSize, charSize, charSize); // get the character

                // Create the image that will be added to the charImgArray, but add the character padding to both sides
                /* Graphical explanation
                +-+-----+-+
                | |  #  | |
                | | #_# | |
                | |#   #| |
                +-+-----+-+
                 ^       ^
                 padding
                 */
                BufferedImage compiledChar = new BufferedImage(lookUpTable[y][x] + paddingSize*2, charSize, BufferedImage.TYPE_INT_ARGB);
                compiledChar.createGraphics().drawImage(img, 1, 0, null); // draw the character
                charImgArray[i] = compiledChar; // add it to the array
                i++;
            }
        }

        // create a new ImageFont
        return (text, color, sizeMultiplier) -> {
            float size = sizeMultiplier / 10f;
            BufferedImage outputImage = new BufferedImage(99999, (int)(charSize*size), BufferedImage.TYPE_INT_ARGB); // create a new image that we'll use as the output
            Graphics2D img = outputImage.createGraphics(); // create a graphics2D object so we can draw to it
            int width = 0;
            for (char c : text.toCharArray()) {
                BufferedImage charImg = charImgArray[c];
                img.drawImage(charImg, width, 0, (int)(charImg.getWidth()*size), (int)(charImg.getHeight()*size),  null);
                width += charImg.getWidth()*size;
            }
            img.dispose();
            return outputImage; // return the completed image0
        };
    }

    /**
     * Get the font so that text can be drawn
     * @return the ImageFont object which has the convertString() method for drawing
     */
    public ImageFont getFont() {
        return imageFont;
    }
}
