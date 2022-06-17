/**
 * Minecraft 2D, done again because I am not a good coder
 * @author Zutki
 * @version 0.0.1-ALPHA
 */

package minecraft2d;

import javax.swing.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * The main class that initializes and starts up the game, does this by providing all the needed listeners
 */
public class App {
    public static final String version = "v0.0.1-ALPHA";
    public static final String DEFAULT_RESOURCE_PACK_LOCATION = "./resources/default.zip";

    private static void init() {
        JFrame window = new JFrame("Minecraft 2D");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private static HashMap<String, String> textureMap = new HashMap<>();

    public static HashMap<String, String> createTextureMap(String location) {
        try {
            ZipFile rpZip = new ZipFile(DEFAULT_RESOURCE_PACK_LOCATION);

            FileInputStream fs = new FileInputStream(DEFAULT_RESOURCE_PACK_LOCATION);
            ZipInputStream zs = new ZipInputStream(new BufferedInputStream(fs));

        } catch (FileNotFoundException ex) {
            System.out.println("File \"default.zip\" not found, please provide the file default.zip in a resources folder in the same folder that the jar was executed in");
            ex.printStackTrace();
            System.exit(1);
        } catch (IOException ex) {
            System.out.println("Error while trying to read \"default.zip\" printing stack trace");
            ex.printStackTrace();
            System.exit(1);
        }
        return null;
    }

    public static void main(String[] args) {

        System.out.println("Starting Minecraft 2D "+version);
        System.out.println("Attempting to create texture map from file "+ DEFAULT_RESOURCE_PACK_LOCATION);
        //textureMap = createTextureMap(DEFAULT_RESOURCE_PACK_LOCATION);

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                init();
            }
        });
    }
}
