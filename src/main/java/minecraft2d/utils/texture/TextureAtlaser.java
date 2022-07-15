package minecraft2d.utils.texture;

import minecraft2d.App;
import minecraft2d.utils.file.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.HashMap;

// TODO (Low priority): Make it memory efficient to store/generate large texture atlases
// TODO (High priority): Add unzipping at some part along the way
// TODO (VERY High priority): Add way to handle textures with .mcmeta files!

/**
 * The textureAtlases class creates texture atlases for the engine to use, the reason of creating texture atlases instead
 * of storing each texture in memory is that this is a more efficient practice and uses less memory than if the program
 * were to store all the files in an array or hashmap
 *
 * <br><br>
 * Note: All the textures provided HAVE to be the same resolution or smaller,
 * To solve this issue in a memory efficient way is beyond my skill
 *
 * @author Zutki
 * @version 0.0.1-ALPHA
 */
public class TextureAtlaser {
    public static final Logger LOGGER = LoggerFactory.getLogger(TextureAtlaser.class);
    /**
     * The amount of textures the stored in the width dimension
     */
    public static final int width = 32;
    /**
     * The amount of textures stored in the height dimensions
     */
    public static final int height = 32;

    /**
     * The size of the biggest texture found, used to define the size of the texture atlas and place textures
     */
    private int textureSize = 0;

    private File dir;

    /**
     * textureAtlas contains the generated textureAtlas
     */
    private BufferedImage[] textureAtlases;

    /**
     * atlasIndex maps texture names to AtlasIndexes
     */
    private HashMap<String, AtlasIndex> atlasIndex = new HashMap<String, AtlasIndex>();

    /**
     * Whether the atlas is for the engine or not
     */
    private boolean engineAtlas = false;
    private String type;

    /**
     * Creates the TextureAtlas from the provided directory
     * @param dirToConvert the directory to read and convert into a texture atlas
     * @throws NotDirectoryException If dirToConvert is not a directory
     */
    public TextureAtlaser(File dirToConvert, String type) throws NotDirectoryException {
        if (!dirToConvert.isDirectory()) {
            throw new NotDirectoryException("The provided object MUST be a directory");
        }
        this.dir = dirToConvert;
        this.type = type;
        readDir();
    }

    public TextureAtlaser(File dirToConvert, boolean engine) throws NotDirectoryException {
        if (!dirToConvert.isDirectory()) {
            throw new NotDirectoryException("The provided object MUST be a directory");
        }
        this.dir = dirToConvert;
        this.engineAtlas = engine;
        this.type = type;
        readDir();
    }

    /**
     * Part one of creating the texture atlases, reading the directory
     */
    private void readDir() {
        File[] files = dir.listFiles(); // read all files in the directory
        ArrayList<BufferedImage> textures = new ArrayList<>(); // create an array to store the textures
        ArrayList<String> names = new ArrayList<>();

        assert files != null;
        for (File file : files) { // iterate through each files in the directory
            if (file.getName().endsWith(".png")) { // if they end in a .png then it's an image file

                // TODO DEBUG: REMOVE THIS, SEE 3RD TODO FOR THIS CLASS!!!!!!
                if (new File(file.getAbsoluteFile() + ".mcmeta").exists()) {
                    LOGGER.warn("dev hasn't implemented a way to handel textures with mcmetas, skipping file "+ file.getName());
                    continue;
                }


                LOGGER.info("Reading texture "+file.getName());
                try { // try to add the image to the array
                    BufferedImage tmp = ImageIO.read(file);
                    // if the size of the largest image in the texture is bigger than the biggest texture size recorded then change textureSize to the new biggest
                    if (tmp.getWidth() > textureSize) {
                        LOGGER.info("New biggest texture found, setting maximum texture size to " + tmp.getWidth());
                        textureSize = tmp.getWidth();
                    }
                    names.add(file.getName());
                    textures.add(tmp);
                } catch (IOException e) {
                    LOGGER.error("Was unable to read file!");
                    throw new RuntimeException(e);
                }
            }
        }

        createAtlases(textures, names);
    }

    /**
     * part two of creating the texture atlases, creating them
     * @param textures the ArrayList of textures to convert into atlases
     */
    private void createAtlases(ArrayList<BufferedImage> textures, ArrayList<String> names) {
        ArrayList<BufferedImage> atlases = new ArrayList<>();
        int atlasCount = 0;

        while (textures.size() != 0) {
            // create the buffered image that will be our atlas
            BufferedImage atlas = new BufferedImage(width*textureSize, height*textureSize, BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D atlasGraphics = atlas.createGraphics(); // setup graphics from it
            // iterate through each possible spot in the atlas and draw the texture there
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    // draw the image in the atlas
                    atlasGraphics.drawImage(textures.get(0), x*textureSize, y*textureSize, null);

                    // generate the name
                    String name = names.get(0);
                    name = name.substring(0, name.lastIndexOf("."));

                    // create the index used to access the texture
                    AtlasIndex index = new AtlasIndex(
                            new Point(x*textureSize, y*textureSize),
                            textures.get(0).getWidth(),
                            textures.get(0).getHeight(),
                            atlasCount);

                    // add the index to the atlas index
                    atlasIndex.put(name, index);

                    // remove the name and texture from the ArrayList since it makes the code a bit easier to work with
                    textures.remove(0);
                    names.remove(0);
                    // once we run out of textures to atlas, then we break out of the loops
                    if (textures.size() == 0) { break; }
                }
                if (textures.size() == 0) { break; }
            }
            // increment the current atlas we are on
            atlasCount++;
            // and add the created atlas to the atlas array
            atlases.add(atlas);
        }

        // here we create the actual file for the atlas
        for (int i = 0; i < atlasCount; i++) {
            // TODO: Fix issue with relative file paths
            // create the output file name by getting the location of pack dirs from the initiator class
            //File outputFile =  engineAtlas ? new File(App.atlasPackDir.getAbsolutePath() + "atlas" + i) : new File(App.atlasDir.getAbsolutePath() + "atlas" + i);
            //outputFile = new File("MC2D/atlas/pack/"+type+"Atlas"+i+".png");
            File outputFile = engineAtlas ? FileUtils.addPaths(App.atlasDir, type+"_atlas" + i + ".png") : FileUtils.addPaths(App.atlasPackDir, type+"_atlas"+i+".png");

            // attempt to write the file
            try {
                ImageIO.write(atlases.get(i), "png", outputFile);
            } catch (IOException e) {
                LOGGER.info("Could not create texture atlas "+outputFile.getAbsoluteFile());
                throw new RuntimeException(e);
            }
        }
        // and finally the atlas array to the created array
        textureAtlases = new BufferedImage[atlasCount];
        textureAtlases = atlases.toArray(textureAtlases);
    }

    private void createMetaAtlases() {

    }

    /**
     * Returns the texture from a texture atlas based on the assigned name
     * @param name the name from which to fetch the texture
     * @return the fetched texture
     */
    public BufferedImage getTexture(String name) {
        AtlasIndex index = atlasIndex.get(name); // use the atlas index to find the index associated with the name
        if (index == null) { // if the name is not mapped, then log an error, and return null
            LOGGER.error("No texture found for name "+ name);
            return null;
        }

        // return a sub-image from the entire atlas which contains the texture requested
        return textureAtlases[(index.atlasLayer)].getSubimage(index.textureStart.x, index.textureStart.y, index.width, index.height);
    }
}
