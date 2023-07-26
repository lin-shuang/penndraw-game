package src.entities;

import java.util.Random;
import src.PennDraw;

public class Rock {
    private double x;
    private double y;
    private double radius;

    // Constructor
    public Rock(double x, double y, double radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    // Draw the rock
    public void draw() {
        PennDraw.setPenColor(PennDraw.GRAY);
        PennDraw.filledCircle(x, y, radius);
    }

    // Generate a random rock position within the world border and within radius 50
    public static Rock generateRandomRock(double borderX, double borderY, double borderRadius) {
        Random random = new Random();
        double x, y;

        do {
            double angle = random.nextDouble() * 2 * Math.PI;
            double distance = random.nextDouble() * (borderRadius - 20); // 20 is the rock's minimum distance from the border
            x = borderX + distance * Math.cos(angle);
            y = borderY + distance * Math.sin(angle);
        } while (Math.pow(x - borderX, 2) + Math.pow(y - borderY, 2) <= Math.pow(50, 2)); //forbidden zone radius 50

        double radius = random.nextDouble() * 10 + 10; // Random radius between 10 and 20

        return new Rock(x, y, radius);
    }

    // Getters and setters (optional based on your requirements)
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getRadius() {
        return radius;
    }
}
