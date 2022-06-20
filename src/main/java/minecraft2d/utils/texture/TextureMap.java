package minecraft2d.utils.texture;

import minecraft2d.App;
import minecraft2d.utils.file.ZipUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

// TODO (LOW PRIORITY): Make this work with directories

/**
 * The TextureMap class maps item names to textures
 * For example hay_block_top would have the texture hay_block_top.png mapped to it
 * @author Zutki
 * @version 0.0.1-ALPHA
 */
public class TextureMap {
    public HashMap<String, BufferedImage> textureMap = new HashMap<>();

    private String baseResourcePack;

    private boolean useExisting;

    /**
     * Default constructor uses the default location of the resource pack set in App.java
     * @param useExisting whether to use the already existing pack in the temp folder (DOES NOTHING AS OF V0.0.1-ALPHA)
     */
    public TextureMap(boolean useExisting) {
        // create the tmp directory where created asset files are stored
        File tmp = new File("./resources/tmp/");

        this.useExisting = useExisting;

        tmp.mkdir();

        // TODO: uncomment when no longer in debug
        //tmp.deleteOnExit();

        baseResourcePack = App.DEFAULT_RESOURCE_PACK_LOCATION;
        loadTextures(baseResourcePack);
    }

    /**
     * This method changes the current <b>base</b> resource pack
     * @param baseResourcePack the location of the zip file to load
     */
    public void setBaseResourcePackResourcePackZip(String baseResourcePack) {
        this.baseResourcePack = baseResourcePack;
        loadTextures(baseResourcePack);
    }

    /**
     * Loads textures into the texture map, overwriting any textures
     * @param locationOfTextures location of the zip file to use
     */
    public void loadTextures(String locationOfTextures) {
        String[] ignoreList = {
                "realms",
                "shaders",
                "pack.mcmeta",
                "pack.png"
        };
        // register textures
        try {
            // use zipUtils to get an array of the files
            ArrayList<File> extractedFiles = ZipUtils.extractZip(locationOfTextures, "resources/tmp/", ignoreList);
            for (File file : extractedFiles) { // loop though each file returned by the extraction
                // using guard clauses
                if (file == null) { continue; } // make sure it's not null
                if (file.isDirectory()) { continue; } // make sure it's not a directory
                if (!file.getName().endsWith(".png")) { continue; }

                String name = file.getName().substring(0, file.getName().lastIndexOf("."));
                System.out.println("Registering texture with name: " + name);
                BufferedImage tmp = ImageIO.read(file);
                forceRegisterTexture(name, tmp); // forcefully register it
            }
        }
        catch (IOException ex) {
            System.out.println("Error while trying to extract pack, printing stacktrace");
            ex.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * The registerTextures method loads a new texture into the textureMap when an object is registered that doesn't have a texture provided by the resource pack.
     * First checks if the item already has a texture registered and returns true if it was.
     * <br><b>If the object is already registered it will not register the texture.</b> If you want to register a texture no matter what use {@link #forceRegisterTexture(String, BufferedImage)}
     * @param name the name of the item, must be the same that the object was registered with
     * @param image the BufferedImage that represents the object
     * @return returns whether the item already has a texture registered
     */
    public boolean registerTexture(String name, BufferedImage image) {
        if (textureMap.containsKey(name)) {
            return true;
        }
        else {
            textureMap.put(name, image);
            return false;
        }
    }

    /**
     * The forceRegisterTexture will load a texture into the texture overwriting any texture that already has the provided name
     * @param name the name of the item, must be the name the object was registered with
     * @param image the BufferedImage that represents the object
     */
    public void forceRegisterTexture(String name, BufferedImage image) {
        if (textureMap.containsKey(name)) {
            textureMap.replace(name, image);
        }
        else {
            textureMap.put(name, image);
        }
    }
}
