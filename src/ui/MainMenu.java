package ui;

import core.GameManager;
import utils.Constants;
import utils.AssetsLoader;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class MainMenu implements MouseListener {
    private GameManager gameManager;
    private BufferedImage backgroundImage;

    // Button positions and sizes
    private Rectangle startButton;
    private Rectangle levelSelectButton;
    private Rectangle exitButton;
    private Rectangle titleArea;

    // UI State
    private boolean mousePressed;
    private Rectangle hoveredButton;

    public MainMenu(GameManager gameManager) {
        this.gameManager = gameManager;
        initializeUI();
        loadAssets();
    }

    private void initializeUI() {
        int centerX = Constants.WINDOW_WIDTH / 2;
        int startY = Constants.WINDOW_HEIGHT / 2;

        // Title area
        titleArea = new Rectangle(
                centerX - 200, 50, 400, 100
        );

        // Buttons
        startButton = new Rectangle(
                centerX - Constants.BUTTON_WIDTH / 2,
                startY - 60,
                Constants.BUTTON_WIDTH,
                Constants.BUTTON_HEIGHT
        );

        levelSelectButton = new Rectangle(
                centerX - Constants.BUTTON_WIDTH / 2,
                startY,
                Constants.BUTTON_WIDTH,
                Constants.BUTTON_HEIGHT
        );

        exitButton = new Rectangle(
                centerX - Constants.BUTTON_WIDTH / 2,
                startY + 60,
                Constants.BUTTON_WIDTH,
                Constants.BUTTON_HEIGHT
        );
    }

    private void loadAssets() {
        backgroundImage = AssetsLoader.getUIImage("main_menu_bg");
    }

    public void update() {
        // Animation or dynamic content updates can go here
    }

    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw background
        drawBackground(g2d);

        // Draw title
        drawTitle(g2d);

        // Draw buttons
        drawButton(g2d, startButton, "START GAME", hoveredButton == startButton);
        drawButton(g2d, levelSelectButton, "LEVEL SELECT", hoveredButton == levelSelectButton);
        drawButton(g2d, exitButton, "EXIT", hoveredButton == exitButton);

        // Draw instructions
        drawInstructions(g2d);

        // Draw version info
        drawVersionInfo(g2d);
    }

    private void drawBackground(Graphics2D g2d) {
        if (backgroundImage != null) {
            g2d.drawImage(backgroundImage, 0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT, null);
        } else {
            // Fallback gradient background
            GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(135, 206, 250),
                    0, Constants.WINDOW_HEIGHT, new Color(70, 130, 180)
            );
            g2d.setPaint(gradient);
            g2d.fillRect(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        }
    }

    private void drawTitle(Graphics2D g2d) {
        // Title shadow
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 48));
        FontMetrics fm = g2d.getFontMetrics();
        String title = "FLAPPY BIRD";
        int titleX = (Constants.WINDOW_WIDTH - fm.stringWidth(title)) / 2;
        g2d.drawString(title, titleX + 3, titleArea.y + 53);

        // Title text
        g2d.setColor(Color.WHITE);
        g2d.drawString(title, titleX, titleArea.y + 50);

        // Subtitle
        g2d.setFont(new Font("Arial", Font.PLAIN, 18));
        g2d.setColor(Color.YELLOW);
        String subtitle = "Multi-Level Adventure";
        fm = g2d.getFontMetrics();
        int subtitleX = (Constants.WINDOW_WIDTH - fm.stringWidth(subtitle)) / 2;
        g2d.drawString(subtitle, subtitleX, titleArea.y + 80);
    }