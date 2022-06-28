/**
 * Minecraft 2D, done again because I am not a good coder
 * @author Zutki
 * @version 0.0.1-ALPHA
 */

package minecraft2d;

import minecraft2d.utils.registry.Registry;
import minecraft2d.utils.texture.TextureMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import render.Event;
import render.RenderEngine;
import uk.org.lidalia.sysoutslf4j.context.SysOutOverSLF4J;

import java.io.*;

/**
 * The main class that initializes and starts up the game, does this by providing all the needed listeners
 */
public class App {
    public static final String version = "v0.0.1-ALPHA";
    public static final String DEFAULT_RESOURCE_PACK_LOCATION = "/home/user/Downloads/default.zip";
    public static Logger LOGGER = LoggerFactory.getLogger(App.class);
    public static TextureMap textureMap;
    public static Registry registry;
    public static final PrintStream sysout = System.out; // this is here in case something absolutely needs to be written without slf4j

    public static RenderEngine renderEngine;
    private static Thread renderThread;

    private static void crashException() throws Exception {
        throw new Exception("Intentional crash");
    }

    /**
     * Crashes the game and prints the stacktrace
     */
    public static void crash() {
        try {
            crashException();
        }
        catch (Exception e) {
            LOGGER.error("Intentional Crash");
            e.printStackTrace();
            System.exit(0);
        }
    }

    // TODO (MEDIUM PRIORITY): Multithread texture registration
    // Textures are only needed once drawing needs to happen, until then it is not as important
    // Useful link for that: https://www.w3schools.com/java/java_threads.asp
    private static void init() {
        File resources = new File("resources/");
        resources.mkdir();
        textureMap = new TextureMap(true);
        registry = new Registry();

        LOGGER.info("Starting render thread");
        renderEngine = RenderEngine.getInstance();

        RenderEngine.addEventToQueue(() -> {
            RenderEngine.setWindowDimension(1920 / 2, 1080 / 2);
            RenderEngine.setTitle("MC2D "+version);
            RenderEngine.setMaxFramerate(60);
        });

        renderThread = new Thread(renderEngine, "Render Thread");
        renderThread.start();
    }

    public static void main(String[] args) {
        SysOutOverSLF4J.sendSystemOutAndErrToSLF4J(); // called so that system.out calls are handled by slf4j

        LOGGER.info("Starting Minecraft 2D "+version);
        LOGGER.info("Attempting to create texture map from file "+ DEFAULT_RESOURCE_PACK_LOCATION);

        init();
    }
}
