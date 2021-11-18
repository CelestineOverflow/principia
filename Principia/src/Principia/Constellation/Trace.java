package Principia.Constellation;

import java.awt.*;
import java.util.ArrayList;

public class Trace {
    ArrayList<Point> tracePoints;
    public Trace(Point p0){
        tracePoints = new ArrayList<>();
        tracePoints.add(p0);
    }
    public void add(Point pAdd){
        tracePoints.add(pAdd);
    }
}
