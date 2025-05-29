package entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

public class Bird {
    private int x,y;
    private int width, height;
    private int yMotion;
    private BufferedImage image;

    public static final int GRAVITY = 2;
    public static final int JUMP_STRENGTH = 10;

    public Bird(int x, int y, int width, int height, String imagePath){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.yMotion = 0;
        loadImage(imagePath);
    }

    private void loadImage(String imagePath) {
        URL imageUrl = getClass().getResource(imagePath);
        if (imageUrl == null) {
            System.err.println(("Image not found: " + imagePath));
        } else {
            this.image = ImageIO.read(imageUrl);
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
