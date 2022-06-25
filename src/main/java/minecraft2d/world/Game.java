package minecraft2d.world;

import minecraft2d.render.RenderOptions;
import minecraft2d.render.RenderThread;
import minecraft2d.world.settings.DebugSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AS OF VERSION 0.0.1-PRE_ALPHA_TESTING: This exists to learn using LWJGL as I have never used this library before
 *
 * The Game class.
 * Holds the information for the game, such as:
 * <ul>
 *     <li>The player</ul>
 *     <li>The current level</ul>
 * </ul>
 * Also does the job of ordering events such as render calls, game window stuff, input, essentially the essentials.
 * @author Zutki
 * @version 0.0.1-PRE_ALPHA_TESTING
 */
public class Game {

    // debug info settings, aims to replicate the usage of F3 in Minecraft;
    DebugSettings debugSettings = new DebugSettings();
    static final Logger LOGGER = LoggerFactory.getLogger(Game.class);

    public RenderThread renderThread;

    // Defining the Window options
    // private because any changes to this should result in a window resizing and other stuff
    private RenderOptions renderOptions = new RenderOptions();

    // The window handle
    private long window;

    /**
     * Start the game
     * Sets everything up and defines some important stuff
     */
    public Game() {

    }

    public void startRenderThread() {
        LOGGER.info("Starting render thread");
        Thread renderingThread = new Thread(renderThread);
        renderingThread.start();
        LOGGER.info("Render thread started");
    }
}
