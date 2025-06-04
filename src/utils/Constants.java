package utils;

public class Constants {
    // Game Window
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;
    public static final String GAME_TITLE = "Flappy Bird - Multi Level";

    // Game Physics
    public static final int BIRD_WIDTH = 50;
    public static final int BIRD_HEIGHT = 50;
    public static final int BIRD_START_X = 100;
    public static final int BIRD_START_Y = WINDOW_HEIGHT / 2;

    // Obstacle Settings
    public static final int OBSTACLE_WIDTH = 80;
    public static final int OBSTACLE_GAP = 200;
    public static final int OBSTACLE_SPAWN_DISTANCE = 300;
    public static final int OBSTACLE_COUNT = 4;

    // Game Speed (pixels per frame)
    public static final int GAME_SPEED_LEVEL_1 = 3;
    public static final int GAME_SPEED_LEVEL_2 = 4;
    public static final int GAME_SPEED_LEVEL_3 = 5;

    // Level Settings
    public static final int TOTAL_LEVELS = 3;
    public static final int SCORE_TO_NEXT_LEVEL = 10;

    // Game States
    public enum GameState {
        MAIN_MENU,
        PLAYING,
        GAME_OVER,
        LEVEL_TRANSITION
    }

    // Asset Paths
    public static final String ASSET_PATH_BACKGROUNDS = "/assets/backgrounds/";
    public static final String ASSET_PATH_BIRDS = "/assets/birds/";
    public static final String ASSET_PATH_OBSTACLES = "/assets/obstacles/";
    public static final String ASSET_PATH_UI = "/assets/ui/";

    // UI Button Sizes
    public static final int BUTTON_WIDTH = 200;
    public static final int BUTTON_HEIGHT = 50;

    // Colors (fallback when images not available)
    public static final java.awt.Color BIRD_COLOR = java.awt.Color.YELLOW;
    public static final java.awt.Color OBSTACLE_COLOR = java.awt.Color.GREEN;
    public static final java.awt.Color BACKGROUND_COLOR = java.awt.Color.CYAN;

    // Game Timing
    public static final int FPS = 60;
    public static final int FRAME_DELAY = 1000 / FPS;
}