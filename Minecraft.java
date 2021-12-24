import javax.swing.*;

class Minecraft {
    private static String username = "Ssundee"; // empty string means no username, game will assume default skin

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
            System.out.println("Invalid argument\n"+
                    "List of avaiable arguments and flags\n"+
                    "-u/--username {USERNAME}  -  gets the skin of the username supplied uses it in-game\n"+
                    "\tIf this flag is not supplied the game will use the default steve skin");
        }

        // invoke later is here because it prevents some issues
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                initWindow(username);
            }
        });
    }
}
