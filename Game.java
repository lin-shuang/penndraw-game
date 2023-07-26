import java.util.ArrayList;
import java.util.List;

import src.PennDraw;
import src.entities.Rock;

public class Game {

    public static int canvasWidth = 800;
    public static int canvasHeight = 600;

    public static void main(String[] args) {
        // Set up the canvas
        PennDraw.setCanvasSize(canvasWidth, canvasHeight);
        PennDraw.setXscale(0, canvasWidth);
        PennDraw.setYscale(0, canvasHeight);

        // Player properties
        double playerX = canvasWidth / 2.0;
        double playerY = canvasHeight / 2.0;
        double playerSpeed = 2.0; // Player movement speed

        // Set world limits
        double worldBorderX = canvasWidth / 2.0;
        double worldBorderY = canvasHeight / 2.0;
        double worldBorderR = 200;

        // Rocks list
        List<Rock> rocks = new ArrayList<>();
        rocks = drawRocks(10, rocks, worldBorderX, worldBorderY, worldBorderR, playerX, playerY);

        //game loop
        while (true) {
            // Clear the canvas
            PennDraw.clear();

            // Check for user input and update player position
            if (PennDraw.isKeyPressed('W')) {
                double newX = playerX + playerSpeed * Math.cos(Math.toRadians(90));
                double newY = playerY + playerSpeed * Math.sin(Math.toRadians(90));
                if (isValidMove(newX, newY, worldBorderX, worldBorderY, worldBorderR)) {                    playerX = newX;
                    playerY = newY;
                }
            }
            if (PennDraw.isKeyPressed('S')) {
                double newX = playerX - playerSpeed * Math.cos(Math.toRadians(90));
                double newY = playerY - playerSpeed * Math.sin(Math.toRadians(90));
                if (isValidMove(newX, newY, worldBorderX, worldBorderY, worldBorderR)) {                    playerX = newX;
                    playerY = newY;
                }
            }
            if (PennDraw.isKeyPressed('A')) {
                double newX = playerX - playerSpeed * Math.cos(Math.toRadians(0));
                double newY = playerY - playerSpeed * Math.sin(Math.toRadians(0));
                if (isValidMove(newX, newY, worldBorderX, worldBorderY, worldBorderR)) {                    playerX = newX;
                    playerY = newY;
                }
            }
            if (PennDraw.isKeyPressed('D')) {
                double newX = playerX + playerSpeed * Math.cos(Math.toRadians(0));
                double newY = playerY + playerSpeed * Math.sin(Math.toRadians(0));
                if (isValidMove(newX, newY, worldBorderX, worldBorderY, worldBorderR)) {                    playerX = newX;
                    playerY = newY;
                }
            }

            // Update the camera based on the mouse direction
            double cameraX = PennDraw.mouseX();
            double cameraY = PennDraw.mouseY();

            // Draw the game objects
            drawBackground();
            drawPlayer(playerX, playerY);
            drawCamera(playerX, playerY, cameraX, cameraY);
            drawWorldBorder(worldBorderX, worldBorderY, worldBorderR);
            
            // Draw rocks
            for(Rock rock : rocks) {
                rock.draw();
            }
        
            // Show the frame
            PennDraw.enableAnimation(60);
            PennDraw.advance();
        }
    }

    // Check if the specified coordinates are within the world border
    private static boolean isValidMove(double x, double y, double borderX, double borderY, double borderRadius) {
        return Math.pow(x - borderX, 2) + Math.pow(y - borderY, 2) <= Math.pow(borderRadius, 2);
    }

    // Draw the background
    private static void drawBackground() {
        PennDraw.setPenColor(PennDraw.LIGHT_GRAY);
        PennDraw.filledRectangle(canvasWidth / 2.0, canvasHeight / 2.0,
                canvasWidth / 2.0, canvasHeight / 2.0);
    }

    // Draw the player character
    private static void drawPlayer(double x, double y) {
        PennDraw.setPenColor(PennDraw.RED);
        PennDraw.filledCircle(x, y, 10);
    }

    // Draw the camera view based on the player's position and the mouse direction
    private static void drawCamera(double playerX, double playerY, double cameraX, double cameraY) {
        PennDraw.setPenColor(PennDraw.BLACK);
        PennDraw.line(playerX, playerY, cameraX, cameraY);
    }

    // Draw the world border as a red circle
    private static void drawWorldBorder(double x, double y, double radius) {
        PennDraw.setPenColor(PennDraw.RED);
        PennDraw.circle(x, y, radius);
    }

    // Draw rocks
    private static List<Rock> drawRocks(int numRocks, List<Rock> rocks, double worldBorderX, double worldBorderY, double worldBorderR, double playerX, double playerY){
        for (int i = 0; i < numRocks; i++) {
            Rock rock = Rock.generateRandomRock(worldBorderX, worldBorderY, worldBorderR);
            rocks.add(rock);
        }
        return rocks;
    }

    // Check for intersects with a specified point
    public boolean intersects(double x, double y, double radius, double otherX, double otherY) {
        double distanceSquared = Math.pow(x - otherX, 2) + Math.pow(y - otherY, 2);
        double radiusSquared = Math.pow(radius, 2);
        return distanceSquared <= radiusSquared;
    }
}