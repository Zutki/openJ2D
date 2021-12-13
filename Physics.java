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
    public boolean canMoveDown() {
       return blocks[player.getPos().y+1][player.getPos().x] == null && 
           blocks[player.getPos().y+2][player.getPos().x] == null;
    }
    public boolean canMoveUp() {
       return blocks[player.getPos().y-1][player.getPos().x] == null;
    }
    public boolean canMoveLeft() {
       return blocks[player.getPos().y][player.getPos().x-1] == null &&
           blocks[player.getPos().y+1][player.getPos().x-1] == null;
    }
    public boolean canMoveRight() {
       return blocks[player.getPos().y][player.getPos().x+1] == null &&
           blocks[player.getPos().y+1][player.getPos().x+1] == null;
    }
}
