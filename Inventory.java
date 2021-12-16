import java.awt.image.BufferedImage;

public class Inventory {
    private Item[] hotbar = new Item[9];
    
    // NOTICE:
    // At this current stage in development the
    // inventory is only the hotbar
    // later down the line it will be expanded into a proper inventory system
    

    public Inventory() {
        // This code is here because if it isn't then there will be a NullPointerException
        for (int i = 0; i < 9; i++) {
            hotbar[i] = new Item();
        }
    }

    public boolean isSlotAvailable(Item _item) {
        // NOTICE:
        // This code does NOT account for item counts and if an item is already present then it will count it as full and ignore it
        // loop through the inventory and check if the item is already there
        for (int i = 0; i < 9; i++) {
            if (hotbar[i].item_id == -1) {
                return true;
            }
        }
        return false;
    }

    public void setInventory(Item[] items) {
        hotbar = items;
    }

    public void setItem(Item item, int index) {
        hotbar[index] = item;
    }
    
    public BufferedImage[] getUiImages() {
        BufferedImage[] imgs = new BufferedImage[9];
        for (int i = 0; i < 9; i++) {
            imgs[i] = hotbar[i].uiImage;
        }
        return imgs;
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
