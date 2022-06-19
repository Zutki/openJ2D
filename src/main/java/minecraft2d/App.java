/**
 * Minecraft 2D, done again because I am not a good coder
 * @author Zutki
 * @version 0.0.1-ALPHA
 */

package minecraft2d;

import minecraft2d.utils.texture.TextureMap;

import javax.swing.*;
import java.io.*;
import java.util.HashMap;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * The main class that initializes and starts up the game, does this by providing all the needed listeners
 */
public class App {
    public static final String version = "v0.0.1-ALPHA";
    public static final String DEFAULT_RESOURCE_PACK_LOCATION = "/home/david/code/java/modDev/Minecraft2D/build/resources/main/resources/default.zip";
    public static Logger logger = new Logger(System.out);
    public static TextureMap textureMap;

    private static void init() {
        textureMap = new TextureMap();
    }

    private static void initWindow() {
        JFrame window = new JFrame("Minecraft 2D");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        // Setup system to use the logger
        System.setOut(logger);
        System.setErr(logger);

        System.out.println("Starting Minecraft 2D "+version);
        System.out.println("Attempting to create texture map from file "+ DEFAULT_RESOURCE_PACK_LOCATION);


        init();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                initWindow();
            }
        });
    }
}
