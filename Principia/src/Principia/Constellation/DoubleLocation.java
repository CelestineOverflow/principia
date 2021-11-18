package Principia.Constellation;

import java.awt.*;
import java.util.Vector;

public class DoubleLocation {
    public double x;
    public double y;
    public DoubleLocation(Point location){
        x = location.x;
        y = location.y;
    }
    public DoubleLocation(Double x, Double y){
        this.x = x;
        this.y = y;
    }
    public static double AbsoluteDistance(DoubleLocation location0, DoubleLocation location1){
        return Math.sqrt(Math.pow((location0.x - location1.x),2) + Math.pow((location0.y - location1.y),2));
    }
    public static double distanceInX(DoubleLocation location0, DoubleLocation location1){
        return location1.x - location0.x;
    }
    public static double distanceInY(DoubleLocation location0, DoubleLocation location1){
        return location1.y - location0.y;
    }
    public Point getPointLocation(){
        Point location = new Point((int) x, (int) y);
        return location;
    }
}
