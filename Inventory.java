import java.awt.image.BufferedImage;

public class Inventory {
    private Item[] hotbar = new Item[9];
    private int[] itemCounts = new int[9];
    
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

    // returns whether or not it is possible to add an item to the inventory
    public boolean isSlotAvailable(Item _item) {
        for (int i = 0; i < 9; i++) {
            // if the item already exists the inventory then run this check
            // if the count of the item is over 64 then the slot is full and we 
            // continue to the next slot
            if (hotbar[i].item_id == _item.item_id &&
                    itemCounts[i] >= 64) {
                continue;
            }
            // item is already in slot
            // if the count of the item is less than 64 then the slot can take more items
            else if (hotbar[i].item_id == _item.item_id &&
                    itemCounts[i] < 64) {
                return true;
            }

            if (hotbar[i].item_id == -1) {
                return true;
            }
        }
        // none of the checks for open spaces passed, return false
        return false;
    }
    
    // add an item to the inventory
    public void addItem(Item _item) {
        // check if the item is already present
        for (int i = 0; i < 9; i++) {
            if (hotbar[i].item_id == _item.item_id &&
                    itemCounts[i] < 64) {
                itemCounts[i]++;
                break;
            }
        }
        // add to an empty slot
        // NOTE: this is done this way because I am attempting to reflect minecraft
        // and in Minecraft if an item is already present in the inventory it will add to the stack instead of an empty space first
        for (int i = 0; i < 9; i++) {
            if (hotbar[i].item_id == -1) {
                hotbar[i] = _item;
                itemCounts[i]++;
            }
        }
    }
    
    // sets inventory with an array of items and sets the counts of them
    public void setInventory(Item[] items, int[] counts) {
        hotbar = items;
        itemCounts = counts;
    }
    
    // set the slot in the inventory to the specified item with the specified count
    public void setItem(Item item, int index, int count) {
        hotbar[index] = item;
        itemCounts[index] = count;
    }

    // set the slot in the invory to the specified item with the count of one
    public void setItem(Item item, int index) {
        hotbar[index] = item;
        itemCounts[index] = 1;
    }
    
    // gets the resized images of the items in the hotbar
    public BufferedImage[] getUiImages() {
        BufferedImage[] imgs = new BufferedImage[9];
        for (int i = 0; i < 9; i++) {
            imgs[i] = hotbar[i].uiImage;
        }
        return imgs;
    }
    
    // gets the images of the items in the hotbar
    public BufferedImage[] getImages() {
        BufferedImage[] imgs = new BufferedImage[9];
        for (int i = 0; i < 9; i++) {
            imgs[i] = hotbar[i].image;
        }
        return imgs;
    }
    
    // gets the names of the items in the hotbar
    public String[] getNames() {
        String[] names = new String[9];
        for (int i = 0; i < 9; i++) {
            names[i] = hotbar[i].name;
        }
        return names;
    }
    
    // gets the item at the specified index
    public Item getItem(int index) {
        return hotbar[index];
    }
    
    // gets the items in the hotbar
    public Item[] getHotbar() {
        return hotbar;
    }
}
