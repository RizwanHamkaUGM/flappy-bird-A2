package entity;

import ui.SkinSelector;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Bird {
    private int x,y;
    private int width, height;
    private int yMotion;
    private BufferedImage image;
    private int level;

    public static final int GRAVITY = 2;
    public static final int JUMP_STRENGTH = 10;

    public Bird(int x, int y, int width, int height, int level ){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.yMotion = 0;
        loadImage(level);
    }

    private void loadImage(int level) {
        String skinPath = SkinSelector.getSkinBirdLevel(level);
        try {
            URL imageUrl = getClass().getResource(skinPath);
            if (imageUrl == null) {
                System.err.println("Gambar tidak ditemukan: " + skinPath);
            } else {
                this.image = ImageIO.read(imageUrl);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Kesalahan saat memuat gambar: " + skinPath);
        }
    }


    public void jump() {
        if (yMotion > 0) {
            yMotion = 0;
        }
        yMotion -= JUMP_STRENGTH;
    }

    public void fall() {
        if (yMotion < 15) {
            yMotion += GRAVITY;
        }
        y += yMotion;
    }

    public void draw(Graphics g) {
        if (image != null) {
            g.drawImage(image, x, y, width, height, null);
        } else {
            // jika gambar tidak ada atau erro digantikan dengan merah
            g.setColor(Color.RED);
            g.fillRect(x, y, width, height);
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

}
