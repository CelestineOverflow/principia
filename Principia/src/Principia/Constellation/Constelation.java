package Principia.Constellation;

import org.w3c.dom.DOMImplementation;

import java.awt.*;
import java.util.*;

public class Constelation {
    private ArrayList<Planet> planets;
    private double maxMass;
    private double timeInterval;
    private int planetCount;
    private Random rand;
    private final double MIN_MASS = 0.5;
    private final double G_CONSTANT = 1;
    private Dimension windowDimension;
    public Constelation (int planetCount, Dimension windowDimension){
        this.planetCount =  planetCount;
        this.windowDimension = windowDimension;
        this.maxMass = 10.0;
        planets = new ArrayList<Planet>();
        rand = new Random();
        spawnPlanets();
    }
    public void spawnPlanets(){
        for(int i = 0; i < planetCount; i++){
            double mass = rand.nextDouble() + MIN_MASS;
            Point location = new Point(rand.nextInt(windowDimension.width), rand.nextInt(windowDimension.height));
            Speed speed = Speed.getRandomSpeed();
            var newPlanet = new Planet(mass, new DoubleLocation(location), speed, this);
            planets.add(newPlanet);
        }
    }
    public DoubleLocation getCenterOfMass(Planet selectedPlanet){
        double x = 0;
        double y = 0;
        for (Planet planet : planets){
            x+= planet.getMass() * (planet.getLocation().x - selectedPlanet.getLocation().x);
            y+= planet.getMass() * (planet.getLocation().y - selectedPlanet.getLocation().y);
        }
        x = x / getTotalMass();
        y = y / getTotalMass();
        DoubleLocation centerOfMass = new DoubleLocation(x, y);
        return centerOfMass;
    }
    public double getTotalMass(){
        double totalMass = 0.0;
        for (Planet planet : planets){
            totalMass += planet.getMass();
        }
        return totalMass;
    }
    public void getAcceleration(ArrayList<Planet> ArrayOfPlanets){
        Set<Planet> remove = new HashSet<Planet>();
        Set<Planet> add = new HashSet<Planet>();
        Iterator<Planet> planetIterator0 = ArrayOfPlanets.iterator();
        while (planetIterator0.hasNext()){
            Planet planet0 = planetIterator0.next();
            Acceleration acceleration = new Acceleration(0.0, 0.0);
            Iterator<Planet> planetIterator1 = planets.iterator();
            while (planetIterator1.hasNext()){
                Planet planet1 = planetIterator1.next();
                double distanceBetweenPlanets = DoubleLocation.AbsoluteDistance(planet0.getLocation(), planet1.getLocation());
                if(planet0 != planet1 && distanceBetweenPlanets > 5) {
                    acceleration = Acceleration.addTwoAcceleration(acceleration , getAccelerationBetween(planet0, planet1));
                    //selectedPlanet.addAcceleration(acceleration);
                } else if (planet0 != planet1){
                    Planet tempPlanet = addPlanets(planet0, planet1);
                    int tempIndex = planets.indexOf(planet0);
                    planetIterator1.remove();
                    planets.set(tempIndex, tempPlanet);
                    planetIterator0 = planets.iterator();
                    break;
                }
            }
            planet0.setAcceleration(acceleration);
        }
    }
    private Acceleration getAccelerationBetween(Planet selectedPlanet, Planet otherPlanet){
        DoubleLocation selectedLocation = selectedPlanet.getLocation();
        DoubleLocation otherLocation = otherPlanet.getLocation();
        double absoluteDistance = DoubleLocation.AbsoluteDistance(selectedLocation, otherLocation);
        double distanceInX = DoubleLocation.distanceInX(selectedLocation, otherLocation);
        double distanceInY = DoubleLocation.distanceInY(selectedLocation, otherLocation);
        //Force of gravity
        double force = G_CONSTANT * (selectedPlanet.getMass() * otherPlanet.getMass()) / Math.pow(absoluteDistance, 2);
        Double absoluteAcceleration = force / selectedPlanet.getMass();
        //Use to decompose the Absolute Acceleration in its x and y components
        double hypotenuse = Math.sqrt(Math.pow(distanceInX, 2.0) + Math.pow(distanceInY, 2.0));
        return new Acceleration((absoluteAcceleration / hypotenuse) * (distanceInX), (absoluteAcceleration / hypotenuse) * (distanceInY));
    }

    public void draw() {
        for (Planet planet : planets){
            planet.draw();
        }
    }
    private Planet addPlanets(Planet planet0, Planet planet1){
        double totalMass = planet0.getMass() + planet1.getMass();
        double speedInX = (planet1.getSpeed().x * planet1.getMass() + planet0.getSpeed().x * planet0.getMass()) / totalMass;
        double speedInY = (planet1.getSpeed().y * planet1.getMass() + planet0.getSpeed().y * planet0.getMass()) / totalMass;
        Speed speed = new Speed(speedInX, speedInY);
        var planet = new Planet(totalMass, planet0.getLocation(), speed, this);
        return planet;
    }
    public void update() {
        updateMaxMass();
        getAcceleration(planets);
        for (Planet planet: planets){
            planet.update();
            planet.resetAcceleration();
        }
    }
    public void updateMaxMass(){
        for (Planet planet: planets){
            if (planet.getMass() > maxMass) maxMass = planet.getMass();
        }
    }
    public double getMaxMass(){
        return this.maxMass;
    }
}
