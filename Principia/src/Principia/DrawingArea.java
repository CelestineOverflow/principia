package Principia;

import Principia.Constellation.Constelation;
import Principia.Constellation.Pen;

import javax.swing.*;
import java.awt.*;

public class DrawingArea extends JPanel {
    Constelation constelation;
    Dimension preferredSize;
    public DrawingArea(Dimension preferredSize){
        this.preferredSize = preferredSize;
        setPreferredSize(preferredSize);
        setBackground(Color.BLACK);
        constelation = new Constelation(500, preferredSize);
    }
    public void paintComponent(Graphics g){
        Pen.set(g);
        Graphics2D pen = Pen.get();
        constelation.draw();
    }
    public void Update(){
        constelation.update();
    }

    public void respawn() {
        constelation = new Constelation(500, preferredSize);
    }
}