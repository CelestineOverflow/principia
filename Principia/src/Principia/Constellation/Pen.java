package Principia.Constellation;

import java.awt.*;

public class Pen {

    private static Graphics pen = null;

    public static void set(Graphics pen) {
        Pen.pen = pen;
    }

    public static Graphics2D get() {
        return (Graphics2D) pen;
    }
}