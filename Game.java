import src.PennDraw;

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

        while (true) {
            // Clear the canvas
            PennDraw.clear();

            // Check for user input and update player position
            if (PennDraw.isKeyPressed('W')) {
                double newX = playerX + playerSpeed * Math.cos(Math.toRadians(90));
                double newY = playerY + playerSpeed * Math.sin(Math.toRadians(90));
                if (isValidMove(newX, newY)) {
                    playerX = newX;
                    playerY = newY;
                }
            }
            if (PennDraw.isKeyPressed('S')) {
                double newX = playerX - playerSpeed * Math.cos(Math.toRadians(90));
                double newY = playerY - playerSpeed * Math.sin(Math.toRadians(90));
                if (isValidMove(newX, newY)) {
                    playerX = newX;
                    playerY = newY;
                }
            }
            if (PennDraw.isKeyPressed('A')) {
                double newX = playerX - playerSpeed * Math.cos(Math.toRadians(0));
                double newY = playerY - playerSpeed * Math.sin(Math.toRadians(0));
                if (isValidMove(newX, newY)) {
                    playerX = newX;
                    playerY = newY;
                }
            }
            if (PennDraw.isKeyPressed('D')) {
                double newX = playerX + playerSpeed * Math.cos(Math.toRadians(0));
                double newY = playerY + playerSpeed * Math.sin(Math.toRadians(0));
                if (isValidMove(newX, newY)) {
                    playerX = newX;
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

            // Show the frame
            PennDraw.enableAnimation(60);
            PennDraw.advance();
        }
    }

    // Check if the specified coordinates are within the valid game area
    private static boolean isValidMove(double x, double y) {
        // Example: Allow movement within the canvas bounds
        int canvasWidth = 800;
        int canvasHeight = 600;
        return x >= 0 && x <= canvasWidth && y >= 0 && y <= canvasHeight;
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
}