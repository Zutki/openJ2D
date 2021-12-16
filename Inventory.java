import java.awt.image.BufferedImage;

public class Inventory {
    private Item[] hotbar = new Item[9];
    
    // NOTICE:
    // At this current stage in development the
    // inventory is only the hotbar
    // later down the line it will be expanded into a proper inventory system
    
    public void setInventory(Item[] items) {
        hotbar = items;
    }

    public void setItem(Item item, int index) {
        hotbar[index] = item;
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

    public Item[] getHotbar() {
        return hotbar;
    }

}
