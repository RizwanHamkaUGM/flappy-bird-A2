package entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Background {
    private BufferedImage image;
    private int level;

    public Background(int level){
        this.level = level;
        loadbackground(level);
    }

    private void loadbackground(int level){
        try {
            switch (level){
                case 1:
                    image = ImageIO.read(new File("asset/background/level1.png"));
                    break;
                case 2:
                    image = ImageIO.read(new File("asset/background/level2.png"));
                    break;
                case 3:
                    image = ImageIO.read(new File("asset/background/level2.png"));
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g) {
        g.drawImage(image,0,0,null);
    }

    public void setLevel(int newlevel) {
        if (this.level != newlevel){
            this.level = newlevel;
            loadbackground(newlevel);
        }
    }
}
