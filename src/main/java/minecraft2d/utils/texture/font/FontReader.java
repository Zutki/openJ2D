package minecraft2d.utils.texture.font;

import minecraft2d.utils.ArrayUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class FontReader {
    public static final int ASCII = 0;
    // TODO: Add more types

    private BufferedImage fontImage;
    private ImageFont imageFont;

    public FontReader (BufferedImage fontImage, int type) {
        this.fontImage = fontImage;
        switch (type) {
            case ASCII:
                imageFont = generateAscii();
                break;
        }
    }

    private ImageFont generateAscii() {
        BufferedImage[][] charImgMatrix = new BufferedImage[16][16]; // read the image as a 2D array for ease
        BufferedImage[] charImgArray = new BufferedImage[256]; // there are 256 characters in ascii, the image provides as such, this will be used to create the ImageFont

        // the offset per character, since the image is a square,
        // and each character is the same width/height we can determine a pixel offset
        int charSize = fontImage.getWidth() / 16;

        int i = 0;
        for (int y = 0; y < 16; y++) {
            for (int x = 0; x < 16; x++) {
                charImgArray[i] = fontImage.getSubimage(x * charSize, y * charSize, charSize, charSize);
                i++;
            }
        }

        return new ImageFont() { // create a new ImageFont
            @Override
            public BufferedImage convertString(String text, Color color, int size) {
                int i = 0; // iterator for determining where to draw the character
                BufferedImage outputImage = new BufferedImage(size*text.length(), size, BufferedImage.TYPE_INT_ARGB); // create a new image that we'll use as the output
                Graphics2D img = outputImage.createGraphics(); // create a graphics2D object so we can draw to it
                for (char c : text.toCharArray()) {
                    // This check needs to be performed since the minecraft font is not monospaced, so for thin characters they need to be drawn differently
                    if (ArrayUtils.contains(new char[]{
                            '!', '\'', '*', ',', '.', ':', ';', '`', 'i', 'l'
                    }, c)) {
                        img.drawImage(charImgArray[c], i * size+size/4, 0, size, size, null); // draw the character inside the image
                    }
                    else {
                        img.drawImage(charImgArray[c], i * size, 0, size, size, null); // draw the character inside the image
                    }
                    i++;
                }
                return outputImage; // return the completed image
            }
        };
    }

    public ImageFont getFont() {
        return imageFont;
    }
}
