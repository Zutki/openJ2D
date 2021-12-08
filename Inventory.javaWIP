import java.awt.image.BufferedImage;

public class Inventory {
    private int[] hotbar = new int[9];
    private int[] inventory = new int[28]; // includes hotbar
    private int[] itemCounts = new int[28];
    
    private BufferedImage[] imageInv = new BufferedImage[inventory.length];
    private BufferedImage[] imageHotbar = new BufferedImage[hotbar.length];

    private int selectedSlot = 0;

    public Inventory(int[] inv, int[] itemCnt) {
        inventory = inv;
        itemCounts = itemCnt;
        
        int hbSlot = 0;
        for (int i = inventory.length-1; i < inventory.length - hotbar.length; i--) {
            hotbar[hbSlot] = inventory[i];
            hbSlot++;
        }
    }
    
    // generate inventory empty
    // using 0 as 0 is blockID for null
    public Inventory() {
        for (int i = 0; i < inventory.length; i++) {
            inventory[i] = 0;
        }
        for (int i = 0; i < hotbar.length; i++) {
            hotbar[i] = 0;
        }
        for (int i = 0; i < itemCounts.length; i++) {
            itemCounts[i] = 0;
        }
    }
    
    // function to find the index of an int inside an integer array
    private int findIndex(int[] ints, int num) {
        for (int i = 0; i < ints.length; i++) {
            if (ints[i] == num) {
                return i;
            }
        }
        return -1;
    }

    
    // find the first avaible slot
    // if the inventory is full then it will return -1
    public int emptySlot() {
        if (inventory.indexOf(0) != -1) {
            return inventory.indexOf(0);
        }
        else {
            return -1;
        }
    }
    
    // add an item to the inventory, if the item already exists then it will add to the count
    // if it was able to find an item to add/add to then it will return true
    // if not, then it will return false
    public boolean addItem(int id, int amount) {
        // check if the item is already in the inventory
        int index = findIndex(inventory, id);
        if (findIndex(inventory, id) != -1) {
            itemCounts[index] += amount;
            return true;
        }
        // item does NOT exist in inventory
        else {
            // there is no free inventory space
            if (emptySlot() == -1) {
                return false;
            }
            // there is available space in the inventory
            else {
                int slot = emptySlot();
                inventory[slot] = id;
                itemCount[slot] = amount;
                return true;
            }
        }

    }
    
    // remove amount from an item in the inventory
    public void removeItem(int id, int amount) {
        int itemIndex = inventory.indexOf(id);
        if (itemIndex != -1) {
            itemCounts[itemIndex] -= amount;
        }
    }
    
    // sets the specifid slot with the specifid ID
    public void setSlot(int id, int amount, int slot) {
        inventory[slot] = id;
        itemCounts[slot] = amount;
    }
    
    // TODO
    // need to implement block ids first
    public void generateGraphics() {

    }

    private void regenerateHotbar() {
        int hbSlot = 0;
        for (int i = inventory.length-1; i < inventory.length - hotbar.length; i--) {
            hotbar[hbSlot] = inventory[i];
            hbSlot++;
        }
    }
}
