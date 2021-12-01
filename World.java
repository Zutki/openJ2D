import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class World extends JPanel /*implements ActionListener KeyListener*/ {
    
    // Tick delay (ms)
    private final int TICK_DELAY = 25;

    // Controls the size of blocks
    public static final int BLOCK_SIZE = 50;
    public static final int ROWS = 12;
    public static final int COLUMS = 18;

    // suppress serialization warning
    private static final long serialVersionUID = 490905409104883233L;

    private Player player;

    // keep a reference to the timer object that triggers actionPerformed() in
    // case we need access to it in another method
    private Timer timer;
    
    public World() {
        // set the game world size
        setPreferredSize(new Dimension(BLOCK_SIZE * COLUMS, BLOCK_SIZE * ROWS));
        
        // set the world background color (sky)
        setBackground(new Color(123, 167, 237));

        // make the player
        // for now only default
        player = new Player();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // draw our graphics
        player.draw(g, this);
        Toolkit.getDefaultToolkit().sync();
    }
}
