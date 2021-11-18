package Principia.Constellation;

import java.util.Random;

public class Speed {
    private static Random rand;
    private static double DEFAULT_SPEED_MULTIPLIER = 10;
    public double x;
    public double y;
    public Speed(double x, double y){
        this.x = x;
        this.y = y;
    }
    public static Speed getRandomSpeed(){
        rand = new Random();
        Double speedInX = (rand.nextDouble() - 0.5) * DEFAULT_SPEED_MULTIPLIER;
        Double speedInY = (rand.nextDouble() - 0.5) * DEFAULT_SPEED_MULTIPLIER;
        return new Speed(speedInX, speedInY);
    }
}
