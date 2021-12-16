import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.util.Scanner;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Id {
    // Syncronized Lists
    private ArrayList<String> itemNames = new ArrayList<>();
    private ArrayList<BufferedImage> itemImgs = new ArrayList<>();

    public Id() {
        try {
            File itemInfo = new File("itemInfo.txt"); // open the itemInfo file
            Scanner reader = new Scanner(itemInfo); // make a scanner using the content from it
            // loop through every line in itemInfo.txt
            // to find the item's name and image location
            // the item ID corresponds to the line number
            //
            // Proper itemInfo.txt formatting should be
            // "{Item Name}" : "{Item image path}"
            // however, for some time being, it will be
            // {Item Name}:{Item image path}
            
            while (reader.hasNextLine()) {
                // TODO: Fix this jank
                // This is absolute jank,
                // THIS IS ABSOLUTELY NOT HOW IT SHOULD BE DONE
                // I've been trying to get this done using regex or something
                // reasonable, but after many hours wasted on a school night, I have
                // given up on being reasonable
                // I am just gonna use jank :(
                
                String itemData = reader.nextLine();               
                String itemName = itemData.substring(0, itemData.indexOf(":"));
                // v--------v
                // Dirt Block: ....
                
                String itemImg = itemData.substring(itemData.indexOf(":")+1, itemData.length());
                //            v-------------v
                // Dirt Block:assets/dirt.png

                System.out.println("Name: "+itemName+"\nItem Image Path: "+itemImg);

                // Add name to list
                itemNames.add(itemName);

                // get the image and add it to the list
                File image = new File(itemImg);
                try {
                    itemImgs.add(Tools.resize(ImageIO.read(image), World.BLOCK_SIZE, World.BLOCK_SIZE));
                }
                catch (IOException exc) {
                    System.out.println("Error opening block image: "+exc.getMessage());
                }

                System.out.println();
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File Error in reading itemInfo.txt");
            e.printStackTrace();
        }
    }

    public ArrayList<String> getItemNames() {
        return itemNames;
    }
    public ArrayList<BufferedImage> getItemImages() {
        return itemImgs;
    }

    public String getItemNameByID(int id) {
        return itemNames.get(id);
    }
    public BufferedImage getItemImageByID(int id) {
        return itemImgs.get(id);
    }
}

