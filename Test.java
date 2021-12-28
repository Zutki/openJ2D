import javax.swing.*;

class Test {
    private static void initWindow() {
        JFrame window = new JFrame("Minecraft 2D");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        World2 world = new World2();
        window.add(world);
        window.setResizable(false);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                initWindow();
            }
        });
    }
}
