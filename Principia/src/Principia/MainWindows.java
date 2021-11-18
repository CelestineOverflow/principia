package Principia;
import javax.swing.*;
import java.awt.*;

public class MainWindows extends JFrame {
    private final boolean stop;
    private long previousTime;
    private static final long TIME_PER_SIMULATION = 60000;
    public MainWindows(String Title){
        setTitle(Title);
        double RESOLUTION_DIVISIONS = 0.8;
        setDefaultScreenSize(RESOLUTION_DIVISIONS);
        setResizable(false);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        var drawingArea = new DrawingArea(getPreferredSize());
        add(drawingArea);
        pack();
        stop = false;
        previousTime = System.currentTimeMillis();
        SwingWorker gameLooper = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                while (!stop) {
                    long currentTime = System.currentTimeMillis();
                    drawingArea.Update();
                    if ((currentTime - previousTime) > TIME_PER_SIMULATION) {
                        drawingArea.respawn();
                        previousTime = System.currentTimeMillis();
                    }
                    add(drawingArea);
                    repaint();
                }
                return null;
            }
        };

        gameLooper.execute();
    }
    public void setDefaultScreenSize(double divisions){
        Dimension resolution = new Dimension((int) (Toolkit.getDefaultToolkit().getScreenSize().width * divisions), (int)(Toolkit.getDefaultToolkit().getScreenSize().height * divisions));
        setPreferredSize(resolution);
    }
    public static void main(String[] args) {
        MainWindows main = new MainWindows("Principia");
        main.setVisible(true);
    }
}
