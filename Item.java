import java.awt.image.BufferedImage;

public class Item {
    public BufferedImage uiImage;
    public BufferedImage image;
    public String name;
    public int item_id;

    public static final int IMAGE_OFFSET = 26;

    public Item(Id id, int _item_id) {
        name = id.getItemNameByID(_item_id);
        item_id = _item_id;
        image = id.getItemImageByID(_item_id);
        uiImage = Tools.resize(image, World.BLOCK_SIZE-IMAGE_OFFSET, World.BLOCK_SIZE-IMAGE_OFFSET);
    }
    public Item() {
        name = null;
        item_id = -1;
        image = null;
    }
}
