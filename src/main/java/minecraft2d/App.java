/**
 * Minecraft 2D, done again because I am not a good coder
 * @author Zutki
 * @gameVersion 0.0.1-ALPHA
 * @version 0.0.2
 */

package minecraft2d;

import minecraft2d.utils.registry.Registry;
import minecraft2d.utils.texture.TextureAtlaser;
import minecraft2d.utils.texture.TextureMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import render.Event;
import render.RenderEngine;
import uk.org.lidalia.sysoutslf4j.context.SysOutOverSLF4J;

import java.io.*;
import java.nio.file.NotDirectoryException;

/**
 * The main class that initializes and starts up the game
 */
public class App {
    public static final String version = "v0.0.1-ALPHA";
    public static final String DEFAULT_RESOURCE_PACK_LOCATION = "src/main/resources/default.zip";
    public static Logger LOGGER = LoggerFactory.getLogger(App.class);
    public static TextureMap textureMap;
    public static Registry registry;
    public static final PrintStream sysout = System.out; // this is here in case something absolutely needs to be written without slf4j

    public static RenderEngine renderEngine;
    public static Thread renderThread;

    // Core engine directories
    public static File rootDir;
    public static File resourcesDir;
    public static File modsDir;
    public static File atlasDir;
    public static File atlasPackDir;

    public static TextureAtlaser blockAtlas;
    public static TextureAtlaser itemAtlas;

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
        // Define some directories used by the engine
        rootDir = new File("MC2D/");
        resourcesDir = new File("MC2D/resources/");
        modsDir = new File("MC2D/mods/");
        atlasDir = new File("MC2D/atlas/");
        atlasPackDir = new File("MC2D/atlas/pack/");

        // check if any of the core dirs exists, if they don't create them
        if (!rootDir.exists()) { rootDir.mkdir(); }
        if (!resourcesDir.exists()) { resourcesDir.mkdir(); }
        if (!modsDir.exists()) { modsDir.mkdir(); }
        if (!atlasDir.exists()) { atlasDir.mkdir(); }
        if (!atlasPackDir.exists()) { atlasPackDir.mkdir(); }

        // things to do with registries and textures
        LOGGER.info("Attempting to create texture map from file "+ DEFAULT_RESOURCE_PACK_LOCATION);
        textureMap = new TextureMap(true); // TODO: remove or rework TextureMap

        try {
            // TODO: replace with reference instead of absolute path
            blockAtlas = new TextureAtlaser(new File("MC2D/resources/tmp/assets/minecraft/textures/block/"), "block");
            itemAtlas = new TextureAtlaser(new File("MC2D/resources/tmp/assets/minecraft/textures/item/"), "item");
        } catch (NotDirectoryException e) {
            LOGGER.error("wtf, this is not supposed to happen");
            throw new RuntimeException(e);
        }

        registry = new Registry();


        // render engine initialization
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

        init();
    }
}
