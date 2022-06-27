package minecraft2d.world;

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

    /**
     * Start the game
     * Sets everything up and defines some important stuff
     */
    public Game() {

    }
}
