import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.awt.image.BufferedImage;

public class Id {
    private ArrayList<String> itemNames = new ArrayList<>();
    private ArrayList<BufferedImage> itemImgs = new ArrayList<>();

    public Id() {
        try {
            File itemInfo = new File("itemInfo.txt");
            Scanner reader = new Scanner(itemInfo);
            while (reader.hasNextLine()) {
                String itemData = reader.nextLine();
                System.out.println(itemData);
                String itemName = itemData.split("\" ");
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File Error in reading itemInfo.txt");
            e.printStackTrace();
        }
    }
}

