import java.awt.image.BufferedImage;

/**
 * The Item class is a data class used to store values of a Minecraft Block.
 * @author Zutki
 */
public class Item {
    /**
     * The image of the item displayed in the UI.
     */
    public BufferedImage uiImage;
    /**
     * The image of the item.
     */
    public BufferedImage image;
    /**
     * The name of the item.
     */
    public String name;
    /**
     * The Item id.
     */
    public int item_id;

    /**
     * The constant IMAGE_OFFSET for rendering purposes.
     */
    public static final int IMAGE_OFFSET = 26;

    /**
     * Instantiates a new Item.
     *
     * @param id       the id of the item
     * @param _item_id the item's id
     */
    public Item(Id id, int _item_id) {
        name = id.getItemNameByID(_item_id);
        item_id = _item_id;
        image = id.getItemImageByID(_item_id);
        uiImage = Tools.resize(image, World.BLOCK_SIZE-IMAGE_OFFSET, World.BLOCK_SIZE-IMAGE_OFFSET);
    }

    /**
     * Instantiates a new Item with null values.
     */
    public Item() {
        name = null;
        item_id = -1;
        image = null;
    }
}
