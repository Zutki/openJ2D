import utils.JSONObject;
import utils.JSONReader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class is used to read in the block configuration file (itemInfo.json), and to parse each block's name, image,
 * and ID to be later used within the game when rendering blocks.
 *
 * @author samminhch
 * @author Zutki
 */
public class Id {
    private final int NAMES = 0;
    private final int IMAGES = 1;
    private Object[][] itemData; // row 0 = String[] itemNames, row 1 = BufferedImage[] itemImgs

    /**
     * The default constructor for the <b>Id</b> class. The class reads in data from <i>itemInfo.json</i> and parses
     * information for each block contained in that <i>.json</i> file. The data is parsed as so:
     * <p>
     *     <ol>
     *         <li>
     *             Parse the name of the block, path of the block's image, and its id.
     *         </li>
     *         <li>
     *             Parse the path of the block's image to a BufferedImage
     *         </li>
     *         <li>
     *             Store the name and block's image to the 2D <code>Object[]</code> <i>itemData</i>, with its index
     *             being the block's ID.
     *         </li>
     *     </ol>
     * </p>
     */
    public Id() {
        try {
            JSONObject obj = JSONReader.interpretFile(new File("itemInfo.json"));

            ArrayList<JSONObject> blocks = (ArrayList<JSONObject>) obj.get("itemInfo.json");
            itemData = new Object[2][blocks.size()];

            for (JSONObject block : blocks) {
                // parse data from each JSONObject
                long id = (long) block.get("id");
                int index = (int) id;
                String name = (String) block.get("name");
                String path = (String) block.get("path");

                // set the itemData at given index to
                itemData[NAMES][index] = name;
                itemData[IMAGES][index] = Tools.resize(ImageIO.read(new File(path)), World.BLOCK_SIZE, World.BLOCK_SIZE);
                // debug statement
                System.out.printf("%s (id: %d) located at path: %s\n", name, id, path);
            }
        } catch (IOException e) {
            System.out.println("File Error in reading itemInfo.json");
            e.printStackTrace();
        }
    }

    /**
     * Get item names string [ ].
     *
     * @return a <code>String[]</code> containing the names of all the items in <i>itemInfo.json</i>
     */
    public String[] getItemNames() {
        return (String[]) itemData[NAMES];
    }

    /**
     * Get item images buffered image [ ].
     *
     * @return a <code>BufferedImage[]</code> containing all the images specified by the image path from each block in <i>itemInfo.json</i>
     */
    public BufferedImage[] getItemImages() {
        return (BufferedImage[]) itemData[IMAGES];
    }

    /**
     * Gets item name by id.
     *
     * @param id the ID of the block
     * @return a <code>String</code> containing the name of the item at its ID.
     */
    public String getItemNameByID(int id) {
        return (String) itemData[NAMES][id];
    }

    /**
     * Gets item image by id.
     *
     * @param id the ID of the block
     * @return a <code>BufferedImage</code> which is the image of the block at its ID.
     */
    public BufferedImage getItemImageByID(int id) {
        return (BufferedImage) itemData[IMAGES][id];
    }
}

