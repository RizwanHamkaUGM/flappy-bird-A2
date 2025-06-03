package entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Obstacle {
    private int x;
    private int y;
    private int width;
    private int height;
    private BufferedImage image;

    public Obstacle(int x, int y, int width, int height, String ImagePath ) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        loadimage(ImagePath);
    }

    private void loadimage(String ImagePath) {
        try {
            URL imageUrl = getClass().getResource(ImagePath);
            if (imageUrl == null) {
                System.err.println("Gambar tidak ditemukan: " + ImagePath);
            } else {
                this.image = ImageIO.read(imageUrl);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Kesalahan saat memuat gambar: " + ImagePath);
        }
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

    public void move(int speed) {
        this.x -= speed;
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

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
