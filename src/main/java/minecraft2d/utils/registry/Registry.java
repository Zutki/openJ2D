package minecraft2d.utils.registry;

import minecraft2d.App;
import minecraft2d.item.Item;

import java.util.ArrayList;

/**
 * The Registry class provides a critical function for the game, in that it registers objects for in-game use.
 * @author Zutki
 * @version 0.0.1-ALPHA
 */
public class Registry {
    public static final int ITEM = 0;
    public static final int BLOCK = 1;
    public static final int DIMENSION = 2;

    private ArrayList<Item> registeredItems = new ArrayList<>();

    public Registry() {

    }
}
