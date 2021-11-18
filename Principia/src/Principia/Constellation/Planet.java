package Principia.Constellation;

import java.awt.*;
import java.util.ArrayList;

public class Planet {
    private double mass;
    private DoubleLocation location;
    private DoubleLocation lastLocation;
    private Speed speed;
    private final int DEFAULT_DIAMETER = 1;
    private int diameter;
    private Color planetColor;
    private long lastTime;
    private ArrayList<Point> trace = new ArrayList<>(1000);
    private Acceleration acceleration;
    private Constelation parent;
    public Planet(double mass, DoubleLocation location, Speed speed, Constelation parent){
        this.parent = parent;
        this.mass = mass;
        this.location = location;
        this.speed = speed;
        this.planetColor = getColor(mass);
        this.acceleration = new Acceleration(0.0, 0.0);
        lastTime = System.currentTimeMillis();
    }
    private void setDiameter(){
        diameter = (int) (DEFAULT_DIAMETER * ((mass/20) + 5));
    }
    public double getMass() {
        return mass;
    }
    public DoubleLocation getLocation() {
        return location;
    }

    public void draw() {
        setDiameter();
        Graphics2D pen = Pen.get();
        pen.setColor(planetColor);
        Point pointLocation = location.getPointLocation();
        int x0, x1, y0, y1;
        int radius = diameter/2;
        x0 = pointLocation.x - radius;
        y0 = pointLocation.y - radius;
        pen.fillOval(x0, y0, diameter, diameter);
        int sizeOfTrace = trace.size();
        for (int i = 0; i < sizeOfTrace-1; i++){
            Point p0 = trace.get(i);
            Point p1 = trace.get(i+1);
            pen.setColor(Color.black);
            pen.drawLine(p0.x, p0.y, p1.x, p1.y);
        }
    }
    private Color getColor(double mass){
        Color color;
        double maxMass = parent.getMaxMass();
        if(mass <= maxMass/4*1){
            int green = mapDoubleToInt(mass, 0, maxMass/4*1, 0, 255);
            color = new Color(255, green, 0);
        }else if (mass<= maxMass/4*2) {
            int red = mapDoubleToInt(mass, maxMass/4*1, maxMass/4*2, 255, 0);
            color = new Color(red, 255, 0);
        }else if (mass<=maxMass/4*3){
            int blue = mapDoubleToInt(mass, maxMass/4*2, maxMass/4*3, 0, 255);
            color = new Color(0, 255, blue);
        } else
            color = Color.BLACK;
        return color;
    }
    private int mapDoubleToInt(double x, double inMin, double inMax, int outMin, int outMax){
        return (int) ((x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin);
    }

    public void update() {
        if (lastLocation == null){
            lastLocation = new DoubleLocation(location.x, location.y);
            trace.add(lastLocation.getPointLocation());
        }
        long currentTime = System.currentTimeMillis();
        long spanOfTime = currentTime - lastTime; // span of time in MILLISECONDS!
        spanOfTime*=3;
        speed.x += acceleration.x * spanOfTime;
        speed.y += acceleration.y * spanOfTime;
        location.x += (speed.x * spanOfTime) * 1E-3;
        location.y += (speed.y * spanOfTime) * 1E-3;
        lastTime = currentTime;
        Double distance = DoubleLocation.AbsoluteDistance(lastLocation, location);
        if (distance > 2) {
            if(trace.size()>=1000){
                trace.remove(0);
            }
            trace.add(lastLocation.getPointLocation());
            lastLocation = new DoubleLocation(location.x, location.y);
        }
    }
    public void resetAcceleration(){
        acceleration = new Acceleration(0.0, 0.0);
    }

    public void setSpeed(Speed speed) {
        this.speed = speed;
    }

    public void setAcceleration(Acceleration acceleration) {
        this.acceleration = acceleration;
    }

    public Speed getSpeed() {
        return speed;
    }
}
