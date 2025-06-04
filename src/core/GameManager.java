package core;

import entity.Bird;
import entity.Background;
import entity.Obstacle;
import utils.Constants;
import utils.AssetsLoader;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameManager implements KeyListener {
    private Constants.GameState currentState;
    private Bird bird;
    private Background background;
    private List<Obstacle> obstacles;
    private LevelManager levelManager;

    private int score;
    private int gameSpeed;
    private boolean gameRunning;
    private boolean spacePressed;

    // Game timing
    private long lastObstacleSpawn;
    private final long obstacleSpawnDelay = 2000; // milliseconds

    public GameManager() {
        initialize();
    }

    private void initialize() {
        // Load all game assets
        AssetsLoader.loadAllAssets();

        // Initialize managers
        levelManager = new LevelManager();

        // Initialize game state
        currentState = Constants.GameState.MAIN_MENU;
        obstacles = new ArrayList<>();

        resetGame();
    }

    public void resetGame() {
        // Reset bird
        bird = new Bird(
                Constants.BIRD_START_X,
                Constants.BIRD_START_Y,
                Constants.BIRD_WIDTH,
                Constants.BIRD_HEIGHT,
                levelManager.getCurrentLevel()
        );

        // Reset background
        background = new Background(levelManager.getCurrentLevel());

        // Clear obstacles
        obstacles.clear();

        // Reset game variables
        score = 0;
        gameSpeed = getSpeedForLevel(levelManager.getCurrentLevel());
        gameRunning = false;
        spacePressed = false;
        lastObstacleSpawn = System.currentTimeMillis();

        // Spawn initial obstacles
        spawnInitialObstacles();
    }

    public void startGame() {
        currentState = Constants.GameState.PLAYING;
        gameRunning = true;
    }

    public void update() {
        switch (currentState) {
            case PLAYING:
                updateGameplay();
                break;
            case MAIN_MENU:
                // Main menu logic handled by UI
                break;
            case GAME_OVER:
                // Game over logic handled by UI
                break;
            case LEVEL_TRANSITION:
                handleLevelTransition();
                break;
        }
    }

    private void updateGameplay() {
        if (!gameRunning) return;

        // Update bird physics
        bird.fall();

        // Update obstacles
        updateObstacles();

        // Spawn new obstacles
        if (shouldSpawnObstacle()) {
            spawnObstacle();
        }

        // Check collisions
        if (checkCollisions()) {
            gameOver();
            return;
        }

        // Check level progression
        if (score >= Constants.SCORE_TO_NEXT_LEVEL * levelManager.getCurrentLevel()) {
            if (levelManager.canAdvanceLevel()) {
                currentState = Constants.GameState.LEVEL_TRANSITION;
            }
        }
    }

    private void updateObstacles() {
        Iterator<Obstacle> iterator = obstacles.iterator();
        while (iterator.hasNext()) {
            Obstacle obstacle = iterator.next();
            obstacle.move(gameSpeed);

            // Remove obstacles that are off-screen
            if (obstacle.getX() + obstacle.getWidth() < 0) {
                iterator.remove();
                score++;
            }
        }
    }

    private boolean shouldSpawnObstacle() {
        return System.currentTimeMillis() - lastObstacleSpawn > obstacleSpawnDelay;
    }

    private void spawnObstacle() {
        int level = levelManager.getCurrentLevel();
        int gapY = 100 + (int)(Math.random() * (Constants.WINDOW_HEIGHT - 300));

        // Top obstacle
        Obstacle topObstacle = new Obstacle(
                Constants.WINDOW_WIDTH,
                0,
                Constants.OBSTACLE_WIDTH,
                gapY,
                level
        );

        // Bottom obstacle
        Obstacle bottomObstacle = new Obstacle(
                Constants.WINDOW_WIDTH,
                gapY + Constants.OBSTACLE_GAP,
                Constants.OBSTACLE_WIDTH,
                Constants.WINDOW_HEIGHT - (gapY + Constants.OBSTACLE_GAP),
                level
        );

        obstacles.add(topObstacle);
        obstacles.add(bottomObstacle);

        lastObstacleSpawn = System.currentTimeMillis();
    }

    private void spawnInitialObstacles() {
        for (int i = 0; i < Constants.OBSTACLE_COUNT; i++) {
            lastObstacleSpawn = System.currentTimeMillis() - obstacleSpawnDelay - (i * 1000);
            spawnObstacle();
        }
    }

    private boolean checkCollisions() {
        Rectangle birdBounds = bird.getBounds();

        // Check ground and ceiling collision
        if (bird.getY() <= 0 || bird.getY() + bird.getHeight() >= Constants.WINDOW_HEIGHT) {
            return true;
        }

        // Check obstacle collisions
        for (Obstacle obstacle : obstacles) {
            if (birdBounds.intersects(obstacle.getBounds())) {
                return true;
            }
        }

        return false;
    }

    private void gameOver() {
        currentState = Constants.GameState.GAME_OVER;
        gameRunning = false;
    }

    private void handleLevelTransition() {
        levelManager.advanceLevel();
        gameSpeed = getSpeedForLevel(levelManager.getCurrentLevel());

        // Update background for new level
        background.setLevel(levelManager.getCurrentLevel());

        // Reset for new level but keep score
        bird = new Bird(
                Constants.BIRD_START_X,
                Constants.BIRD_START_Y,
                Constants.BIRD_WIDTH,
                Constants.BIRD_HEIGHT,
                levelManager.getCurrentLevel()
        );

        obstacles.clear();
        spawnInitialObstacles();

        currentState = Constants.GameState.PLAYING;
    }

    private int getSpeedForLevel(int level) {
        switch (level) {
            case 1: return Constants.GAME_SPEED_LEVEL_1;
            case 2: return Constants.GAME_SPEED_LEVEL_2;
            case 3: return Constants.GAME_SPEED_LEVEL_3;
            default: return Constants.GAME_SPEED_LEVEL_1;
        }
    }

    public void render(Graphics g) {
        // Draw background
        background.draw(g);

        // Draw obstacles
        for (Obstacle obstacle : obstacles) {
            obstacle.draw(g);
        }

        // Draw bird
        bird.draw(g);

        // Draw UI elements
        drawUI(g);
    }

    private void drawUI(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 24));

        // Draw score
        g.drawString("Score: " + score, 10, 30);

        // Draw level
        g.drawString("Level: " + levelManager.getCurrentLevel(), 10, 60);

        // Draw next level progress
        int currentLevelScore = score % Constants.SCORE_TO_NEXT_LEVEL;
        g.drawString("Next Level: " + currentLevelScore + "/" + Constants.SCORE_TO_NEXT_LEVEL, 10, 90);
    }

    // KeyListener implementation
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        switch (currentState) {
            case MAIN_MENU:
                if (key == KeyEvent.VK_SPACE || key == KeyEvent.VK_ENTER) {
                    startGame();
                }
                break;

            case PLAYING:
                if (key == KeyEvent.VK_SPACE && !spacePressed) {
                    bird.jump();
                    spacePressed = true;
                }
                if (key == KeyEvent.VK_ESCAPE) {
                    currentState = Constants.GameState.MAIN_MENU;
                    gameRunning = false;
                }
                break;

            case GAME_OVER:
                if (key == KeyEvent.VK_R) {
                    resetGame();
                    startGame();
                } else if (key == KeyEvent.VK_M) {
                    resetGame();
                    currentState = Constants.GameState.MAIN_MENU;
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            spacePressed = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }

    // Getters
    public Constants.GameState getCurrentState() {
        return currentState;
    }

    public int getScore() {
        return score;
    }

    public int getCurrentLevel() {
        return levelManager.getCurrentLevel();
    }

    public boolean isGameRunning() {
        return gameRunning;
    }

    // Setters
    public void setState(Constants.GameState state) {
        this.currentState = state;
    }
}