# Details of the purpose and usage of each class and file

## Classes
### World
The World class is the controller of the game, it handels things to do with drawing the window size properly, and draws the world, almost everything in the game uses some aspect of this.

### Block
The block class defines all the attributes of a block, it is ***used by World*** to create the world.

### Id
The ID class is a very important class, it is the main system that is ***used by blocks and inventory*** it implements the methods required to get the Image and Name of an item/block.

### Minecraft
The Minecraft class is mainly just an initiator class, it is not used by anything but runs the game, it ***provides the world*** with things like the Mouse Lisitener, the Key Lisitener, and Action Lisitioner. It also calls the world constructor.

### Player
The player class defines all attributes of a player (health, inventory, hunger, image, and position)
The player class also handels movement for the player. ***Called by world***

### Tools
The tools class is not a class to be constructed, it is only a collection of tools that is used, mainly resize().

### WorldBuilder
The worldbuilder class handels terrain generation. ***Called by World***

---
## Methods of Classes

### Block
- Constructors:
    - Block(int itemID, Point position)
    - Block(int itemID, Point position, int Id_of_drop_item)
- Methods
    - drawBlock(Graphics g, ImageObserver observer)
    - getPos()
    - setPos()

### ID
- Constructor
    - Id()
        - This needs to be called for it to function correctly
        - item names and images are defined in itemInfo.txt
            - from itemInfo.txt, item ids correspond to line numbers
            - the format for itemInfo.txt is
                - {Item Name}:{Item image path} 
- Methods
    - ArrayList<String> getItemNames()
        - returns an ArrayList of all item Names, corresponding to item id order 
    
    - ArrayList<BufferedImage> getItemImages()
        - returns an ArrayList of all item images, corresponding to item id order
    
    - String getItemNameByID(int itemID)
        - returns the name of an item from the given item id
    
    - BufferedImage getItemImageByID(int itemID)
        - returns the image for an item from the given item id

### Player
- Constructors
    - Player()
        - loads a player with default stats
    - Player(args)
        - Do not use
- Methods
    - draw(Graphics g, ImageObserver observer)
        - draws the player
    - keyPressed(KeyEvent e) {
        - handels input
    - tick()
        - used by world, just checks to make sure the player is not going off screen
    - Point getPos()
        - returns the position 

### Tools
- Methods
    - BufferedImage resize(BufferedImage img, int newWidth, int newHeight)
        - resizes the given image to the size specified 

### WorldBuilder
- Constructor
    - WorldBuilder(Block[][] blockArray, int terrainLevel)
        - loads the block array from world and sets the default terrain level
- Methods
    - Block[][] buildWorld()
        - generates the terrain and returns it

