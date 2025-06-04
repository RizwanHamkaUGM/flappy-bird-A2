package main;

import core.GameManager;
import ui.MainMenu;
import ui.GameOverScreen;
import utils.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class FlappyBirdGame extends JPanel implements Runnable, MouseMotionListener {
    private GameManager gameManager;
    private MainMenu mainMenu;
    private GameOverScreen gameOverScreen;

    private Thread gameThread;
    private boolean running;

    // Game window
    private JFrame frame;

    public FlappyBirdGame() {
        initializeWindow();
        initializeGame();
        setupInputListeners();
    }

    private void initializeWindow() {
        // Setup main window
        frame = new JFrame(Constants.GAME_TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        frame.setLocationRelativeTo(null);

        // Setup panel
        this.setPreferredSize(new Dimension(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
        this.setBackground(Constants.BACKGROUND_COLOR);
        this.setFocusable(true);

        frame.add(this);
        frame.pack();

        // Try to set icon (if available)
        try {
            // You can add game icon here
            // ImageIcon icon = new ImageIcon(getClass().getResource("/assets/ui/game_icon.png"));
            // frame.setIconImage(icon.getImage());
        } catch (Exception e) {
            System.out.println("Game icon not found, using default");
        }
    }

    private void initializeGame() {
        gameManager = new GameManager();
        mainMenu = new MainMenu(gameManager);
        gameOverScreen = new GameOverScreen(gameManager);

        running = true;
    }

    private void setupInputListeners() {
        // Keyboard listener
        this.addKeyListener(gameManager);

        // Mouse listeners for UI
        this.addMouseListener(mainMenu);
        this.addMouseListener(gameOverScreen);
        this.addMouseMotionListener(this);

        // Request focus for keyboard input
        this.requestFocus();
    }

    public void startGame() {
        frame.setVisible(true);

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double nsPerFrame = 1000000000.0 / Constants.FPS;
        double delta = 0;

        while (running) {
            long currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / nsPerFrame;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }

            // Small sleep to prevent excessive CPU usage
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void update() {
        switch (gameManager.getCurrentState()) {
            case MAIN_MENU:
                mainMenu.update();
                break;

            case PLAYING:
            case LEVEL_TRANSITION:
                gameManager.update();
                break;

            case GAME_OVER:
                gameOverScreen.update();
                break;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        switch (gameManager.getCurrentState()) {
            case MAIN_MENU:
                mainMenu.render(g2d);
                break;

            case PLAYING:
            case LEVEL_TRANSITION:
                gameManager.render(g2d);

                // Draw level transition message if needed
                if (gameManager.getCurrentState() == Constants.GameState.LEVEL_TRANSITION) {
                    drawLevelTransition(g2d);
                }
                break;

            case GAME_OVER:
                // Draw game state first (paused game)
                gameManager.render(g2d);

                // Draw game over screen on top
                gameOverScreen.show(
                        gameManager.getScore(),
                        gameManager.getCurrentLevel(),
                        false // TODO: Implement high score tracking
                );
                gameOverScreen.render(g2d);
                break;
        }

        // Draw FPS counter (debug)
        if (Constants.FPS > 0) { // Only if debugging is enabled
            drawDebugInfo(g2d);
        }
    }

    private void drawLevelTransition(Graphics2D g2d) {
        // Semi-transparent overlay
        g2d.setColor(new Color(0, 0, 0, 150));
        g2d.fillRect(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);

        // Level transition text
        g2d.setFont(new Font("Arial", Font.BOLD, 36));
        g2d.setColor(Color.WHITE);
        String levelText = "LEVEL " + gameManager.getCurrentLevel();
        FontMetrics fm = g2d.getFontMetrics();
        int textX = (Constants.WINDOW_WIDTH - fm.stringWidth(levelText)) / 2;
        int textY = Constants.WINDOW_HEIGHT / 2;

        // Text shadow
        g2d.setColor(Color.BLACK);
        g2d.drawString(levelText, textX + 2, textY + 2);

        // Main text
        g2d.setColor(Color.YELLOW);
        g2d.drawString(levelText, textX, textY);

        // Subtitle
        g2d.setFont(new Font("Arial", Font.PLAIN, 18));
        String subtitle = "Get Ready!";
        fm = g2d.getFontMetrics();
        int subtitleX = (Constants.WINDOW_WIDTH - fm.stringWidth(subtitle)) / 2;
        g2d.setColor(Color.WHITE);
        g2d.drawString(subtitle, subtitleX, textY + 40);
    }

    private void drawDebugInfo(Graphics2D g2d) {
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        g2d.setColor(Color.WHITE);

        // Calculate FPS (simple approximation)
        long currentTime = System.currentTimeMillis();
        String debugInfo = String.format("FPS: %d | State: %s | Score: %d",
                Constants.FPS,
                gameManager.getCurrentState(),
                gameManager.getScore()
        );

        g2d.drawString(debugInfo, 10, Constants.WINDOW_HEIGHT - 30);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // Forward mouse events to appropriate UI components
        switch (gameManager.getCurrentState()) {
            case MAIN_MENU:
                mainMenu.mouseMoved(e);
                break;
            case GAME_OVER:
                gameOverScreen.mouseMoved(e);
                break;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // Not used in this game
    }

    public void stopGame() {
        running = false;
        if (gameThread != null) {
            try {
                gameThread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    // Main method to start the game
    public static void main(String[] args) {
        // Set system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
        } catch (Exception e) {
            System.out.println("Could not set system look and feel");
        }

        // Create and start game
        SwingUtilities.invokeLater(() -> {
            FlappyBirdGame game = new FlappyBirdGame();
            game.startGame();
        });
    }
}