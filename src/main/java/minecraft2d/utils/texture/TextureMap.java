package minecraft2d.utils.texture;

import minecraft2d.App;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.NoSuchFileException;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * The TextureMap class maps item names to textures
 * For example hay_block_top would have the texture hay_block_top.png mapped to it
 * @author Zutki
 * @version 0.0.1-ALPHA
 */
public class TextureMap {
    public HashMap<String, BufferedImage> textureMap;

    private String baseResourcePack;

    /**
     * Default constructor uses the default location of the resource pack set in App.java
     */
    public TextureMap() {
        baseResourcePack = App.DEFAULT_RESOURCE_PACK_LOCATION;
        loadTexturesFromZip(baseResourcePack);
    }

    /**
     * This method changes the current <b>base</b> resource pack
     * @param baseResourcePack the location of the zip file to load
     */
    public void setBaseResourcePackResourcePackZip(String baseResourcePack) {
        this.baseResourcePack = baseResourcePack;
        loadTexturesFromZip(baseResourcePack);
    }


    /**
     * The loadTexturesFromZip method loads textures into the textureMap
     * if the texture already exists in the map it will overwrite it with the new one
     * @param location the location of the file
     */
    public void loadTexturesFromZip(String location) {
        FileInputStream fs;
        ZipInputStream zs;
        ZipEntry ze;

        try {
            ZipFile rpFile = new ZipFile(location);

            fs = new FileInputStream(location);
            zs = new ZipInputStream(new BufferedInputStream(fs));

            File file = new File("./assets/");
            file.mkdir();

            while ((ze = zs.getNextEntry()) != null) {
                convertZipEntryToFile(ze, zs);
            }
            zs.close();

        } catch (NoSuchFileException ex) {
            System.out.println("File "+ location +" does not exist.");
            ex.printStackTrace();
            System.exit(1);
        } catch (IOException ex) {
            System.out.println("Error while trying to read "+ location +" printing stack trace");
            ex.printStackTrace();
            System.exit(1);
        }
    }

    private File convertZipEntryToFile(ZipEntry zipEntry, ZipInputStream zipInputStream) throws IOException {
        System.out.println(zipEntry.getName());
        File file = new File(zipEntry.getName());


        if (!zipEntry.isDirectory()) {
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            fileOutputStream.write(zipInputStream.readAllBytes());
            System.out.println(fileOutputStream);
            //System.exit(0);
        }
        else {
            file.mkdir();
            return null;
        }
        return null;
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
