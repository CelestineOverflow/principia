package Principia.Constellation;

import java.util.Random;

public class Acceleration {
    public double x;
    public double y;
    public Acceleration(double x, double y){
        this.x = x;
        this.y = y;
    }
    public static Acceleration addTwoAcceleration(Acceleration Acc0, Acceleration Acc1){
        return new Acceleration(Acc0.x + Acc1.x, Acc0.y + Acc1.y);
    }
}
