package minecraft2d.utils.texture;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class TextureMap {
    public HashMap<String, String> textureMap;

    private String resourcePackLocation;

    public TextureMap(String defaultResourcePackZipLocation) {
        loadTextures();
    }
    public TextureMap() {
        resourcePackLocation = "./resources/default.zip";
        loadTextures();
    }

    public void setResourcePack(String location) {
        this.resourcePackLocation = location;
    }

    public void loadTextures() {
        try {
            ZipFile rpFile = new ZipFile(resourcePackLocation);

            FileInputStream fs = new FileInputStream(resourcePackLocation);
            ZipInputStream zs = new ZipInputStream(new BufferedInputStream(fs));

        } catch (FileNotFoundException ex) {
            System.out.println("File "+resourcePackLocation+" does not exist.");
            ex.printStackTrace();
            System.exit(1);
        } catch (IOException ex) {
            System.out.println("Error while trying to read "+resourcePackLocation+" printing stack trace");
            ex.printStackTrace();
            System.exit(1);
        }
    }
}
