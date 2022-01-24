import javax.swing.*;

/**
 * The Minecraft class is used to initiate a new Window to render the game, and creates and provides the World class
 * data for a Mouse, Key, and Action listener.
 * @author Zutki
 */
class Minecraft {
    /**
     * The constant username.
     */
    private static String username = ""; // empty string means no username, game will assume default skin

    /**
     * Initiates a window for the Minecraft game.
     *
     * @param _username the username
     */
    private static void initWindow(String _username) {
        JFrame window = new JFrame("Minecraft 2D");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // create the JPanel to draw the game
        // also initialize the game loop
        World world = new World(_username);
        window.add(world);
        window.addKeyListener(world);
        window.addMouseListener(world);
        window.addMouseWheelListener(world);

        window.setResizable(false);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    /**
     * The entry point of application. Checks if a username is given via command line arguments.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        // flags
        if (args.length > 1) {
            // username for skins
            if (args[0].equals("-u") || args[0].equals("--username")) {
                username = args[1];
            }
        }

        // error reporting for flags
        if (args.length == 1) {
            System.out.println("""
                    Invalid argument
                    List of available arguments and flags:
                    \t-u/--username {USERNAME}  -  gets the skin of the username supplied uses it in-game
                    \tIf this flag is not supplied the game will use the default steve skin""");
        }

        // invoke later is here because it prevents some issues
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                initWindow(username);
            }
        });
    }
}
