package ui;

public class SkinSelector {
    public static String getSkinBirdLevel(int level) {
        return "/assets/bird/level" + level + "_skin.png";
    }

    public static String getSkinObslevel(int level) {
        return "/assets/obstacle/level" + level + "_skin.png";
    }
}
