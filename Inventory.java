import java.awt.image.BufferedImage;

public class Inventory {
    private Item[] hotbar = new Item[9];
    private Id id;
    
    // NOTICE:
    // At this current stage in development the
    // inventory is only the hotbar
    // later down the line it will be expanded into a proper inventory system

    public Inventory(Id _id) {
        id = _id;
    }

    public Inventory(Id _id, Item[] _hotbar) {
        id = _id;
        hotbar = _hotbar;
    }
    
    public BufferedImage[] getImages() {
        BufferedImage[] imgs = new BufferedImage[9];
        for (int i = 0; i < 9; i++) {
            imgs[i] = hotbar[i].image;
        }
        return imgs;
    }

    public String[] getNames() {
        String[] names = new String[9];
        for (int i = 0; i < 9; i++) {
            names[i] = hotbar[i].name;
        }
        return names;
    }
    public Item getItem(int index) {
        return hotbar[index];
    }

}
