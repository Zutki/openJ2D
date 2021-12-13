import java.awt.Point;
import java.util.Timer;

public class Physics {
    private Block[][] blocks;
    private Player player;
    private Timer timer;
    private int ticksToWait;

    // Constructor
    public Physics(Block[][] _blocks) {
        ticksToWait = 1000 / World.TICK_DELAY / 3;
        blocks = _blocks;
        timer = new Timer();
    }
    // this is not part of the constructor but NEEDS to be called
    // else it will throw an error
    public void setPlayer(Player _player) {
        player = _player;
    }

    // collision detection
    
    // Structure:
    // if statement checks for bounds
    // else statement checks for block collisions
    
    public boolean canMoveDown() {
        if (player.getPos().y == World.ROWS-2) {
            return false;
        }
        else {
            return blocks[player.getPos().y+2][player.getPos().x] == null && player.getPos().y != World.ROWS;
        }
    }
    public boolean canMoveUp() {
        if (player.getPos().y == 0) {
            return false;
        }
        else {
            return blocks[player.getPos().y-1][player.getPos().x] == null && player.getPos().y != 0;
        }
    }
    public boolean canMoveLeft() {
        if (player.getPos().x == 0) {
            return false;
        }
        else {
            return blocks[player.getPos().y][player.getPos().x-1] == null &&
                blocks[player.getPos().y+1][player.getPos().x-1] == null;
        }
    }
    public boolean canMoveRight() {
        if (player.getPos().x == World.COLUMNS-1) {
            return false;
        }
        else {
            return blocks[player.getPos().y][player.getPos().x+1] == null &&
                blocks[player.getPos().y+1][player.getPos().x+1] == null;
        }
    }

    // Gravity
    private int tickCounter = 0;
    public void resetCounter() {
        tickCounter = 0;
    }

    public void tick() {
        tickCounter++;
        if (tickCounter >= ticksToWait) {
            tickCounter = 0;
            if (canMoveDown()) {
                Point playerPos = player.getPos();
                playerPos.translate(0, +1);
                player.setPos(playerPos);
            }
        }
    }

    // jumping
    public boolean canJump() {
        return blocks[player.getPos().y+2][player.getPos().x] != null;
    }
}
