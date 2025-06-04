package utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class AssetsLoader {
    private static Map<String, BufferedImage> imageCache = new HashMap<>();
    private static boolean assetsLoaded = false;

    // Asset file names
    private static final String[] BIRD_ASSETS = {
            "bird_level1.png",
            "bird_level2.png",
            "bird_level3.png"
    };

    private static final String[] BACKGROUND_ASSETS = {
            "background_level1.png",
            "background_level2.png",
            "background_level3.png"
    };

    private static final String[] OBSTACLE_ASSETS = {
            "obstacle_level1.png",
            "obstacle_level2.png",
            "obstacle_level3.png"
    };

    private static final String[] UI_ASSETS = {
            "main_menu_bg.png",
            "game_over_bg.png",
            "button_start.png",
            "button_restart.png",
            "button_menu.png",
            "level_complete.png"
    };

    /**
     * Load all game assets into memory
     */
    public static void loadAllAssets() {
        if (assetsLoaded) return;

        System.out.println("Loading game assets...");

        // Load bird assets
        for (int i = 0; i < BIRD_ASSETS.length; i++) {
            String key = "bird_level" + (i + 1);
            String path = Constants.ASSET_PATH_BIRDS + BIRD_ASSETS[i];
            loadImage(key, path);
        }

        // Load background assets
        for (int i = 0; i < BACKGROUND_ASSETS.length; i++) {
            String key = "background_level" + (i + 1);
            String path = Constants.ASSET_PATH_BACKGROUNDS + BACKGROUND_ASSETS[i];
            loadImage(key, path);
        }

        // Load obstacle assets
        for (int i = 0; i < OBSTACLE_ASSETS.length; i++) {
            String key = "obstacle_level" + (i + 1);
            String path = Constants.ASSET_PATH_OBSTACLES + OBSTACLE_ASSETS[i];
            loadImage(key, path);
        }

        // Load UI assets
        for (String asset : UI_ASSETS) {
            String key = asset.replace(".png", "");
            String path = Constants.ASSET_PATH_UI + asset;
            loadImage(key, path);
        }

        assetsLoaded = true;
        System.out.println("Assets loading completed!");
    }

    /**
     * Load individual image and store in cache
     */
    private static void loadImage(String key, String path) {
        try {
            URL imageUrl = AssetsLoader.class.getResource(path);
            if (imageUrl != null) {
                BufferedImage image = ImageIO.read(imageUrl);
                imageCache.put(key, image);
                System.out.println("Loaded: " + key);
            } else {
                System.err.println("Asset not found: " + path);
                // Create placeholder image
                imageCache.put(key, createPlaceholderImage(50, 50));
            }
        } catch (IOException e) {
            System.err.println("Error loading asset: " + path);
            e.printStackTrace();
            // Create placeholder image
            imageCache.put(key, createPlaceholderImage(50, 50));
        }
    }

    /**
     * Get cached image by key
     */
    public static BufferedImage getImage(String key) {
        return imageCache.get(key);
    }

    /**
     * Get bird image for specific level
     */
    public static BufferedImage getBirdImage(int level) {
        return getImage("bird_level" + level);
    }

    /**
     * Get background image for specific level
     */
    public static BufferedImage getBackgroundImage(int level) {
        return getImage("background_level" + level);
    }

    /**
     * Get obstacle image for specific level
     */
    public static BufferedImage getObstacleImage(int level) {
        return getImage("obstacle_level" + level);
    }

    /**
     * Get UI image by name
     */
    public static BufferedImage getUIImage(String name) {
        return getImage(name);
    }

    /**
     * Create placeholder image when asset is missing
     */
    private static BufferedImage createPlaceholderImage(int width, int height) {
        BufferedImage placeholder = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        java.awt.Graphics2D g2d = placeholder.createGraphics();
        g2d.setColor(java.awt.Color.MAGENTA);
        g2d.fillRect(0, 0, width, height);
        g2d.setColor(java.awt.Color.BLACK);
        g2d.drawRect(0, 0, width-1, height-1);
        g2d.drawString("MISSING", 5, height/2);
        g2d.dispose();
        return placeholder;
    }

    /**
     * Check if assets are loaded
     */
    public static boolean isAssetsLoaded() {
        return assetsLoaded;
    }

    /**
     * Clear all cached assets (for memory management)
     */
    public static void clearAssets() {
        imageCache.clear();
        assetsLoaded = false;
        System.out.println("Assets cleared from memory");
    }
}