import javax.swing.*;

class Minecraft {

    private static void initWindow() {
        JFrame window = new JFrame("Minecraft 2D");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // create the JPanel to draw the game
        // also initialize the game loop
        World world = new World();
        window.add(world);
        window.addKeyListener(world);
        window.addMouseListener(world);
        window.addMouseWheelListener(world);

        window.setResizable(false);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    public static void main(String[] args) {
        // invoke later is here because it prevents some issues
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                initWindow();
            }
        });
    }
}
