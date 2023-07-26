import java.util.ArrayList;
import java.util.List;

import javafx.scene.input.KeyEvent;
import src.PennDraw;
import src.entities.Rock;

public class Game {

    public static int canvasWidth = 800;
    public static int canvasHeight = 600;
    public static double[] playerCoords = {canvasWidth/2.0, canvasHeight/2.0}; //[X, Y]


    public static void main(String[] args) {
        // Set up the canvas
        PennDraw.setCanvasSize(canvasWidth, canvasHeight);
        PennDraw.setXscale(0, canvasWidth);
        PennDraw.setYscale(0, canvasHeight);

        // Player properties
        double playerSpeed = 2.0;
        boolean playerCanMove = true;
        double cameraX, cameraY;

        // Set world limits
        double worldBorderX = canvasWidth / 2.0;
        double worldBorderY = canvasHeight / 2.0;
        double worldBorderR = 200;

        // Rocks list
        List<Rock> rocks = new ArrayList<>();
        rocks = drawRocks(10, rocks, worldBorderX, worldBorderY, worldBorderR);

        //game loop
        while (true) {
            // Clear the canvas
            PennDraw.clear();

            // Player movement
            handlePlayerInput(playerSpeed, worldBorderX, worldBorderY, worldBorderR, rocks, canvasWidth, canvasHeight);

            // Update the camera based on the mouse direction
            cameraX = PennDraw.mouseX();
            cameraY = PennDraw.mouseY();

            // Draw the game objects
            drawBackground();
            drawPlayer(playerCoords);
            drawCamera(playerCoords, cameraX, cameraY);
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
    private static void drawPlayer(double[] playerCoords) {
        PennDraw.setPenColor(PennDraw.RED);
        PennDraw.filledCircle(playerCoords[0], playerCoords[1], 10);
    }

    // Draw the camera view based on the player's position and the mouse direction
    private static void drawCamera(double[] playerCoords, double cameraX, double cameraY) {
        PennDraw.setPenColor(PennDraw.BLACK);
        PennDraw.line(playerCoords[0], playerCoords[1], cameraX, cameraY);
    }

    // Draw the world border as a red circle
    private static void drawWorldBorder(double x, double y, double radius) {
        PennDraw.setPenColor(PennDraw.RED);
        PennDraw.circle(x, y, radius);
    }

    // Draw rocks
    private static List<Rock> drawRocks(int numRocks, List<Rock> rocks, double worldBorderX, double worldBorderY, double worldBorderR){
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

    // Method to handle player movement input
    private static void handlePlayerInput(double playerSpeed, double borderX, double borderY, double borderRadius,
                                          List<Rock> rocks, int canvasWidth, int canvasHeight) {
        if (PennDraw.isKeyPressed('W')) {
            movePlayer(90, playerSpeed, borderX, borderY, borderRadius, rocks, canvasWidth, canvasHeight);
        }
        if (PennDraw.isKeyPressed('S')) {
            movePlayer(270, playerSpeed, borderX, borderY, borderRadius, rocks, canvasWidth, canvasHeight);
        }
        if (PennDraw.isKeyPressed('A')) {
            movePlayer(180, playerSpeed, borderX, borderY, borderRadius, rocks, canvasWidth, canvasHeight);
        }
        if (PennDraw.isKeyPressed('D')) {
            movePlayer(0, playerSpeed, borderX, borderY, borderRadius, rocks, canvasWidth, canvasHeight);
        }
    }

    // Move the player in the specified direction
    private static void movePlayer(double angleDegrees, double playerSpeed, double borderX, double borderY,
                                   double borderRadius, List<Rock> rocks, int canvasWidth, int canvasHeight) {
        double newX = playerCoords[0] + playerSpeed * Math.cos(Math.toRadians(angleDegrees));
        double newY = playerCoords[1] + playerSpeed * Math.sin(Math.toRadians(angleDegrees));
        boolean canMove = true;

        // Check collision with each rock
        for (Rock rock : rocks) {
            if (rock.collidesWith(newX, newY)) {
                canMove = false;
                break;
            }
        }

        if (canMove && isValidMove(newX, newY, borderX, borderY, borderRadius)) {
            playerCoords[0] = newX;
            playerCoords[1] = newY;
        }
    }
}