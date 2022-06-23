package minecraft2d.world;

import minecraft2d.render.RenderThread;
import minecraft2d.world.settings.DebugSettings;
import minecraft2d.render.RenderOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

/**
 * The Game class.
 * Holds the information for the game, such as:
 * <li>
 *     <ul>Registered blocks</ul>
 *     <ul>The player</ul>
 *     <ul>The current level</ul>
 * </li>
 * Also does the job of ordering events such as render calls, game window stuff, input, essentially the essentials.
 * @author Zutki
 * @version 0.0.1-ALPHA
 */
public class Game extends JPanel {
    // suppress serialization warning
    private static final long serialVersionUID = 490905409104883233L;

    // debug info settings, aims to replicate the usage of F3 in Minecraft;
    DebugSettings debugSettings = new DebugSettings();
    static final Logger LOGGER = LoggerFactory.getLogger(Game.class);

    public RenderThread renderThread;

    // The delay between each game Tick (ms)
    public static final int TICK_DELAY = 12;

    // Defining the Window options
    // private because any changes to this should result in a window resizing and other stuff
    private RenderOptions renderOptions = new RenderOptions();

    private Timer timer;

    /**
     * Start the game
     * Sets everything up and defines some important stuff
     */
    public Game() {
        setPreferredSize(renderOptions.getDimension());
        setBackground(new Color(123, 167, 237));
    }

    public void startRenderThread() {
        LOGGER.info("Starting render thread");
        Thread renderingThread = new Thread(renderThread);
        renderingThread.start();
        LOGGER.info("Render thread started");
    }
}
