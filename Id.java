import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;

import utils.JSONObject;
import utils.JSONReader;

public class Id {
    // Syncronized Lists
    private ArrayList<String> itemNames;
    private ArrayList<BufferedImage> itemImgs;

    public Id() {
        try {
            JSONReader reader = new JSONReader(new File("itemInfo.json"));
            JSONObject obj = reader.interpretFile();

            // initializing itemNames and itemImgs
            itemNames = obj.getKeys();
            itemImgs = new ArrayList<>();

            // filling itemImgs with images from itemInfo.json
            for (String imageName : obj.getKeys()) {
                File image = new File((String)obj.get(imageName));
                itemImgs.add(Tools.resize(ImageIO.read(image), World.BLOCK_SIZE, World.BLOCK_SIZE));

                // debug statement
                System.out.printf("Name: %s\nItem Image Path: %s\n\n", imageName, obj.get(imageName));
            }
        } catch (IOException e) {
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

