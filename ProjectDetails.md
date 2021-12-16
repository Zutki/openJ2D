# Details of the purpose and usage of each class and file

Last Updated: 12/16/21

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

### Physics
The physics class provides functions like gravity and collision detection ***called by World***

### Ui
The Ui class provides the Ui drawing for the game, it renders things like the hotbar and currently hovered block. ***called by World***

### Item
The Item class provies the information and structure that items use, ***mainly used by Ui***

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
    - Player(Physics physics)
        - loads a player with physics for movement
- Methods
    - draw(Graphics g, ImageObserver observer)
        - draws the player
    - keyPressed(KeyEvent e) {
        - handels input
    - Point getPos()
        - returns the position 
    - setPos()
        - set players position 

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

### Ui
- Constructor
    - Ui(Inventory inv)
        - Ui needs the inventory class so it can make the hotbar
- Methods
    - Item getSelectedItem()
        - returns the currently selected item in the hotbar 
    - moveSlot(int mouseWheelInput)
        - move up or down a slot depening on the value returned from getting the value of the mousewheel
    - setHoveredBlock(Point position, Graphics g, ImageObserver observer)
        - sets the block that will be currently hovered
    - loadUi(Graphics g, ImageObserver observer)
        - loads the parts of the ui that are displayed every frame

### Physics
- Constructor
    - Physics(Block[][] blocks)
        - Block[][] is passed from the world
- Methods
    - setPlayer(Player player)
        - **IMPORTANT** This methods NEEDS to be called, the reason for it being done this way is because player needs physics to be constructed but physics needs player to be constructed
    - boolean canMove{DIRECTION}()
        - returns whether or not if it is possible to move in the direction specified
    - resetCounter()
        - resets the counter of tickCounter, used for movement so that gravity stays consistent
    - boolean canJump()
        - returns whether or not the player can jump 

### Item
- Constructors
    - Item(Id id, int item_id) {
        - creates an item with values
    - Item()
        - creates an empty item (needed because of NullPointerException errors)
- Methods
    - None: variables are accessed by {variable name}.{field} 
