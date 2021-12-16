import java.awt.image.BufferedImage;

public class Item {
    public BufferedImage image;
    public String name;
    public int item_id;

    public Item(Id id, int _item_id) {
        name = id.getItemNameByID(_item_id);
        item_id = _item_id;
        image = id.getItemImageByID(_item_id);
    }
}
