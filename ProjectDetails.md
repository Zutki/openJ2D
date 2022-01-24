# Details of the purpose and usage of each class and file

Last Updated: 12/16/21

## Classes
### World
The World class is the controller of the game, it handles things to do with drawing the window size properly, and draws the world, almost everything in the game uses some aspect of this.

### Block
The block class defines all the attributes of a block, it is ***used by [World](#world)*** to create the world.

### Id
The ID class is a very important class, it is the main system that is ***used by [Blocks](#block) and [Inventory](#inventory)*** it implements the methods required to get the Image and Name of an item/block.

### Minecraft
The Minecraft class is mainly just an initiator class, it is not used by anything but runs the game, it ***provides the [world](#world)*** with things like the Mouse Listener, the Key Listener, and Action Listener. It also calls the world constructor.

### Player
The player class defines all attributes of a player (health, inventory, hunger, image, and position)
The player class also handles movement for the player. ***Called by [World](#world)***

### Tools
The tools class is not a class to be constructed, it is only a collection of tools that is used, mainly resize().

### WorldBuilder
The WorldBuilder class handles terrain generation. ***Called by [World](#world)***

### Physics
The physics class provides functions like gravity and collision detection ***called by [World](#world)***

### Ui
The Ui class provides the Ui drawing for the game, it renders things like the hotbar and currently hovered block. ***called by [World](#world)***

### Item
The Item class provides the information and structure that items use, ***mainly used by [Ui](#ui)***

### Inventory (WIP)
The <u> Inventory </u> currently only houses a hotbar.

### JSONReader
The <u> JSONReader </u> class reads in a <b>.json</b> file and returns a <u>[JSONObject](#JSONObject)</u>. The philosophy behind the approach of this class is that the file itself is a key and the contents of the file its value. <b>Mainly used by [Id.java](#id) </b>

### JSONObject
The <u> JSONObject </u> is used by the <u> [JSONReader](#jsonreader) </u> class to interpret a .json file. <b> Mainly used by [Id.java](#id) </b>

---
## Methods of Classes

### Block
- Constructors:
    - `Block(int itemID, Point position)`
    - `Block(int itemID, Point position, int Id_of_drop_item)`
- Methods
    - `drawBlock(Graphics g, ImageObserver observer)`
    - `getPos()`
    - `setPos()`

### ID
- Constructor
    - `Id()`
        - This needs to be called for it to function correctly
        - item names and images are defined in itemInfo.txt
            - from itemInfo.txt, item ids correspond to line numbers
            - the format for itemInfo.txt is
                ```json
                [
                    {
                        "name": "example_block_name",
                        "path": "./example_path",
                        "id": 0
                    }
                ]
                ```
- Methods
    - `String[] getItemNames()`
        - returns an array of all item Names, corresponding to item id order 
    
    - `BufferedImage[] getItemImages()`
        - returns an array of all item images, corresponding to item id order
    
    - `String getItemNameByID(int itemID)`
        - returns the name of an item from the given item id
    
    - `BufferedImage getItemImageByID(int itemID)`
        - returns the image for an item from the given item id

### Player
- Constructors
    - `Player(Physics physics)`
        - loads a player with physics for movement
- Methods
    - `void draw(Graphics g, ImageObserver observer)`
        - draws the player
    - `void keyPressed(KeyEvent e)`
        - handles input
    - `Point getPos()`
        - returns the position 
    - `void setPos()`
        - set players position 

### Tools
- Methods
    - `BufferedImage resize(BufferedImage img, int newWidth, int newHeight)`
        - resizes the given image to the size specified 
    - `BufferedImage makeImageTranslucent(BufferedImage source, float alpha)`
        - returns a BufferedImage whose translucency is specified by _alpha_,
          the % opacity of _source_.
    - `BufferedImage fetchMinecraftSkin(String username)`
        - returns a BufferedImage of the minecraft user specified through the use of Minecraft's API
    - `BufferedImage getPlayerFacing{Direction}(BufferedImage skinImage)`
        - create a new BufferedImage containing only the cropped images of the
          given Minecraft _skinImage_ that are facing {Direction}

### WorldBuilder
- Constructor
    - `WorldBuilder(Block[][] blockArray, int terrainLevel)`
        - loads the block array from world and sets the default terrain level
- Methods
    - `Block[][] buildWorld()`
        - generates the terrain and returns it

### Ui
- Constructor
    - `Ui(Inventory inv)`
        - Ui needs the inventory class so it can make the hotbar
- Methods
    - `Item getSelectedItem()`
        - returns the currently selected item in the hotbar 
    - `moveSlot(int mouseWheelInput)`
        - move up or down a slot depending on the value returned from getting the value of the mousewheel
    - `setHoveredBlock(Point position, Graphics g, ImageObserver observer)`
        - sets the block that will be currently hovered
    - `loadUi(Graphics g, ImageObserver observer)`
        - loads the parts of the ui that are displayed every frame

### Physics
- Constructor
    - `Physics(Block[][] blocks)`
        - Block[][] is passed from the world
- Methods
    - `void setPlayer(Player player)`
        - **IMPORTANT** This method NEEDS to be called, the reason for it being done this way is because player needs physics to be constructed but physics needs player to be constructed
    - `boolean canMove{DIRECTION}()`
        - returns whether or not if it is possible to move in the direction specified
    - `void resetCounter()`
        - resets the counter of tickCounter, used for movement so that gravity stays consistent
    - `boolean canJump()`
        - returns whether or not the player can jump 

### Item
- Constructors
    - `Item(Id id, int item_id)`
        - creates an item with values
    - `Item()`
        - creates an empty item (needed because of NullPointerException errors)
- Methods
    - None: variables are accessed by {variable name}.{field} 

### Inventory (WIP)
- Constructors
    - `Inventory()`
        - creates a hotbar for the player
- Methods
    - `boolean isSlotAvailable(Item _item)`
        - checks if slot in inventory contains no items
    - `void setInventory(Item[] items)`
        - sets the hotbar to an array of Items
    - `void setItem(Item item, int index)`
        - sets the hotbar at the index to the given item
    - `BufferedImage[] getUiImages()`
        - returns an array of UiImages for each slot in the hotbar
    - `BufferedImage[] getImages()`
        - returns an array of Images for each slot in the hotbar
    - `String[] getNames()`
        - returns the names for each slot in the hotbar
    - `Item getItem(int index)`
        - returns an item of the hotbar inside of the index
    - `Item[] getHotbar()`
        - returns the hotbar

### JSONReader
- Constructors
    - none.
- Methods
    - `JSONObject interpretFile(File file) throws IOException`
        - parses the given file and returns a JSONObject of parsed file.
    - `JSONObject interpretJSONString(String fileName, Reader inputStreamReader)`
        - similar to interpretFile, but works for all input streams.

### JSONObject
- Constructors
    - `JSONObject(String key, String value)`
        - a .json object contains a key and a value. By default, the key is the file name, and the value is the contents of the file.
        - will parse the given key and value provided
- Methods
    - `Object get(String key) throws IllegalArgumentException`
        - Keys a value from given key, and throws IllegalArgumentException if there are no such keys
    - `ArrayList<String> getKeys()`
        - Returns a list of all available keys from the JSONObject