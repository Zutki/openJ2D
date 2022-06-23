package minecraft2d.utils.registry;

import minecraft2d.block.Block;
import minecraft2d.dimension.Dimension;
import minecraft2d.item.Item;
import java.util.HashMap;

/**
 * The Registry class provides a critical function for the game, in that it registers objects for in-game use.
 * @author Zutki
 * @version 0.0.1-ALPHA
 */
public class Registry {
    public static final int ITEM = 0;
    public static final int BLOCK = 1;
    public static final int DIMENSION = 2;

    /* NOTE: 2022-06-22
    I am not entirely sure if it is a good idea to use a singular hashmap to store EVERY
    single registered block, item, dimension, or whatever else is added by mods.
    And I know this is an excuse, but I am not a good java developer, I only kinda know what I'm doing
    TODO (LOW PRIORITY): Have better data structure here
     */

    private HashMap<Object, HashMap<String, RegistryEntry>> registry = new HashMap<Object, HashMap<String, RegistryEntry>>();

    public Registry() {
        registry.put(Item.class, new HashMap<String, RegistryEntry>());
        registry.put(Block.class, new HashMap<String, RegistryEntry>());
        registry.put(Dimension.class, new HashMap<String, RegistryEntry>());
    }

    /**
     * Registers an item into the item registry
     * @param identifier identifier for item
     * @param item the item to register
     * @return the registry entry for the item
     */
    public RegistryEntry registerItem(Identifier identifier, Item item) {
        RegistryEntry regItem = new RegistryEntry(identifier, item);
        registry.get(Item.class).put(identifier.getName(), regItem);
        return regItem;
    }
    public RegistryEntry registerBlock(Identifier identifier, Block block) {
        RegistryEntry regBlock = new RegistryEntry(identifier, block);
        registry.get(Block.class).put(identifier.getName(), regBlock);
        return regBlock;
    }
}
