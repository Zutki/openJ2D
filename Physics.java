import java.awt.Point;
public class Physics {
    private Block[][] blocks;
    private Player player;

    public Physics(Block[][] _blocks) {
        blocks = _blocks;
    }
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
}
