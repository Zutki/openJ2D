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
       return blocks[player.getPos().x][player.getPos().y+1] == null;
    }
    public boolean canMoveUp() {
       return blocks[player.getPos().x][player.getPos().y-1] == null;
    }
    public boolean canMoveLeft() {
       return blocks[player.getPos().x-1][player.getPos().y] == null;
    }
    public boolean canMoveRight() {
       return blocks[player.getPos().x+1][player.getPos().y] == null;
    }
}
