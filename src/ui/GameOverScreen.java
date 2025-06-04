package ui;

import core.GameManager;
import utils.Constants;
import utils.AssetsLoader;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class GameOverScreen implements MouseListener {
    private GameManager gameManager;
    private BufferedImage backgroundImage;

    // Button positions and sizes
    private Rectangle restartButton;
    private Rectangle menuButton;
    private Rectangle exitButton;
    private Rectangle gameOverArea;
    private Rectangle scoreArea;

    // Animation variables
    private float alpha = 0.0f;
    private boolean animationComplete = false;
    private long animationStartTime;

    // UI State
    private Rectangle hoveredButton;
    private int finalScore;
    private int currentLevel;
    private boolean newHighScore;

    public GameOverScreen(GameManager gameManager) {
        this.gameManager = gameManager;
        initializeUI();
        loadAssets();
    }

    private void initializeUI() {
        int centerX = Constants.WINDOW_WIDTH / 2;
        int centerY = Constants.WINDOW_HEIGHT / 2;

        // Game Over text area
        gameOverArea = new Rectangle(
                centerX - 200, centerY - 150, 400, 80
        );

        // Score area
        scoreArea = new Rectangle(
                centerX - 150, centerY - 70, 300, 100
        );

        // Buttons
        restartButton = new Rectangle(
                centerX - Constants.BUTTON_WIDTH / 2,
                centerY + 50,
                Constants.BUTTON_WIDTH,
                Constants.BUTTON_HEIGHT
        );

        menuButton = new Rectangle(
                centerX - Constants.BUTTON_WIDTH / 2,
                centerY + 110,
                Constants.BUTTON_WIDTH,
                Constants.BUTTON_HEIGHT
        );

        exitButton = new Rectangle(
                centerX - Constants.BUTTON_WIDTH / 2,
                centerY + 170,
                Constants.BUTTON_WIDTH,
                Constants.BUTTON_HEIGHT
        );
    }

    private void loadAssets() {
        backgroundImage = AssetsLoader.getUIImage("game_over_bg");
    }

    public void show(int score, int level, boolean isNewHighScore) {
        this.finalScore = score;
        this.currentLevel = level;
        this.newHighScore = isNewHighScore;
        this.alpha = 0.0f;
        this.animationComplete = false;
        this.animationStartTime = System.currentTimeMillis();
    }

    public void update() {
        // Fade in animation
        if (!animationComplete) {
            long elapsed = System.currentTimeMillis() - animationStartTime;
            alpha = Math.min(1.0f, elapsed / 1000.0f); // 1 second fade in

            if (alpha >= 1.0f) {
                animationComplete = true;
            }
        }
    }

    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Set alpha for fade in effect
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

        // Draw semi-transparent overlay
        drawOverlay(g2d);

        // Draw background
        drawBackground(g2d);

        // Draw game over text
        drawGameOverText(g2d);

        // Draw score information
        drawScoreInfo(g2d);

        // Draw buttons (only if animation is complete)
        if (animationComplete) {
            drawButton(g2d, restartButton, "RESTART (R)", hoveredButton == restartButton);
            drawButton(g2d, menuButton, "MAIN MENU (M)", hoveredButton == menuButton);
            drawButton(g2d, exitButton, "EXIT", hoveredButton == exitButton);
        }

        // Draw instructions
        drawInstructions(g2d);

        // Reset alpha
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }

    private void drawOverlay(Graphics2D g2d) {
        // Semi-transparent black overlay
        g2d.setColor(new Color(0, 0, 0, 150));
        g2d.fillRect(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
    }

    private void drawBackground(Graphics2D g2d) {
        if (backgroundImage != null) {
            // Draw background image with some transparency
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f * alpha));
            g2d.drawImage(backgroundImage,
                    gameOverArea.x, gameOverArea.y,
                    gameOverArea.width, gameOverArea.height, null);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        }
    }

    private void drawGameOverText(Graphics2D g2d) {
        // "GAME OVER" text with shadow
        g2d.setFont(new Font("Arial", Font.BOLD, 48));
        FontMetrics fm = g2d.getFontMetrics();
        String gameOverText = "GAME OVER";
        int textX = (Constants.WINDOW_WIDTH - fm.stringWidth(gameOverText)) / 2;
        int textY = gameOverArea.y + 50;

        // Shadow
        g2d.setColor(Color.BLACK);
        g2d.drawString(gameOverText, textX + 3, textY + 3);

        // Main text
        g2d.setColor(Color.RED);
        g2d.drawString(gameOverText, textX, textY);
    }

    private void drawScoreInfo(Graphics2D g2d) {
        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        g2d.setColor(Color.WHITE);

        // Final Score
        String scoreText = "Final Score: " + finalScore;
        FontMetrics fm = g2d.getFontMetrics();
        int scoreX = (Constants.WINDOW_WIDTH - fm.stringWidth(scoreText)) / 2;
        g2d.drawString(scoreText, scoreX, scoreArea.y + 30);

        // Level reached
        g2d.setFont(new Font("Arial", Font.PLAIN, 18));
        String levelText = "Level Reached: " + currentLevel;
        fm = g2d.getFontMetrics();
        int levelX = (Constants.WINDOW_WIDTH - fm.stringWidth(levelText)) / 2;
        g2d.drawString(levelText, levelX, scoreArea.y + 55);

        // New high score indicator
        if (newHighScore) {
            g2d.setFont(new Font("Arial", Font.BOLD, 20));
            g2d.setColor(Color.YELLOW);
            String highScoreText = "NEW HIGH SCORE!";
            fm = g2d.getFontMetrics();
            int highScoreX = (Constants.WINDOW_WIDTH - fm.stringWidth(highScoreText)) / 2;
            g2d.drawString(highScoreText, highScoreX, scoreArea.y + 85);
        }
    }

    private void drawButton(Graphics2D g2d, Rectangle button, String text, boolean hovered) {
        // Button background
        if (hovered) {
            g2d.setColor(new Color(220, 20, 60, 200));
        } else {
            g2d.setColor(new Color(139, 69, 19, 150));
        }
        g2d.fillRoundRect(button.x, button.y, button.width, button.height, 15, 15);

        // Button border
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRoundRect(button.x, button.y, button.width, button.height, 15, 15);

        // Button text
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        FontMetrics fm = g2d.getFontMetrics();
        int textX = button.x + (button.width - fm.stringWidth(text)) / 2;
        int textY = button.y + (button.height + fm.getAscent()) / 2;

        // Text shadow
        g2d.setColor(Color.BLACK);
        g2d.drawString(text, textX + 1, textY + 1);

        // Text
        g2d.setColor(Color.WHITE);
        g2d.drawString(text, textX, textY);
    }

    private void drawInstructions(Graphics2D g2d) {
        if (animationComplete) {
            g2d.setFont(new Font("Arial", Font.PLAIN, 14));
            g2d.setColor(new Color(255, 255, 255, 200));

            String[] instructions = {
                    "Press R to restart",
                    "Press M for main menu",
                    "Click buttons to navigate"
            };

            int startY = Constants.WINDOW_HEIGHT - 80;
            FontMetrics fm = g2d.getFontMetrics();

            for (int i = 0; i < instructions.length; i++) {
                int textX = (Constants.WINDOW_WIDTH - fm.stringWidth(instructions[i])) / 2;
                g2d.drawString(instructions[i], textX, startY + i * 20);
            }
        }
    }

    // Mouse event handlers
    @Override
    public void mouseClicked(MouseEvent e) {
        if (!animationComplete) return;

        Point clickPoint = e.getPoint();

        if (restartButton.contains(clickPoint)) {
            gameManager.setState(Constants.GameState.PLAYING);
            // Reset and restart game will be handled by GameManager
        } else if (menuButton.contains(clickPoint)) {
            gameManager.setState(Constants.GameState.MAIN_MENU);
        } else if (exitButton.contains(clickPoint)) {
            System.exit(0);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Not used
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Not used
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Not used
    }

    @Override
    public void mouseExited(MouseEvent e) {
        hoveredButton = null;
    }

    public void mouseMoved(MouseEvent e) {
        if (!animationComplete) return;

        Point mousePoint = e.getPoint();
        hoveredButton = null;

        if (restartButton.contains(mousePoint)) {
            hoveredButton = restartButton;
        } else if (menuButton.contains(mousePoint)) {
            hoveredButton = menuButton;
        } else if (exitButton.contains(mousePoint)) {
            hoveredButton = exitButton;
        }
    }

    // Getters
    public boolean isAnimationComplete() {
        return animationComplete;
    }

    public float getAlpha() {
        return alpha;
    }
}