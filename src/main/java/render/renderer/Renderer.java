package render.renderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Renderer extends JPanel implements ActionListener {
    private Timer timer;
    private int maxFramerate = 60;
    private int frames = 0; // used to calculate the running framerate
    private int framerate = 0; // the actual framerate
    private long start = 0; // used to calculate if a second passed

    public Renderer(Dimension windowDimensions) {
        setPreferredSize(windowDimensions);
        setBackground(Color.RED);
        timer = new Timer(1000/maxFramerate, this);
        timer.start();
        start = System.currentTimeMillis();
    }

    public void setMaxFramerate(int framerate) {
        maxFramerate = framerate;
        timer.setDelay(1000 / maxFramerate);
    }

    public int getMaxFramerate() { return maxFramerate; }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
        frames++;
        if (System.currentTimeMillis() - start >= 1000) {
            start = System.currentTimeMillis();
            framerate = frames;
            frames = 0;
        }
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        String[] fonts =
                GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

        TextUtil.drawText("FPS:"+framerate, TextUtil.RIGHT, Color.BLACK, new Font(fonts[0], Font.BOLD, 24), new Point(1,25), g);
        TextUtil.drawText("Hello World!", TextUtil.CENTERED, Color.BLACK, new Font(fonts[0], Font.BOLD, 48), new Point(getWidth()/2, getHeight()/2), g);

        // General recommendation for smooth animations on all systems
        Toolkit.getDefaultToolkit().sync();
    }

}
