package core;

import utils.Constants;

public class LevelManager {
    private int currentLevel;
    private int maxLevel;
    private boolean[] levelUnlocked;

    public LevelManager() {
        this.currentLevel = 1;
        this.maxLevel = Constants.TOTAL_LEVELS;
        this.levelUnlocked = new boolean[maxLevel + 1]; // Index 0 unused

        // Initialize level unlocking (level 1 is always unlocked)
        levelUnlocked[1] = true;
        for (int i = 2; i <= maxLevel; i++) {
            levelUnlocked[i] = false;
        }
    }

    /**
     * Advance to the next level if possible
     */
    public boolean advanceLevel() {
        if (canAdvanceLevel()) {
            currentLevel++;
            unlockLevel(currentLevel);
            return true;
        }
        return false;
    }

    /**
     * Check if player can advance to the next level
     */
    public boolean canAdvanceLevel() {
        return currentLevel < maxLevel;
    }

    /**
     * Set the current level (if unlocked)
     */
    public boolean setCurrentLevel(int level) {
        if (level >= 1 && level <= maxLevel && levelUnlocked[level]) {
            currentLevel = level;
            return true;
        }
        return false;
    }

    /**
     * Unlock a specific level
     */
    public void unlockLevel(int level) {
        if (level >= 1 && level <= maxLevel) {
            levelUnlocked[level] = true;
        }
    }

    /**
     * Check if a level is unlocked
     */
    public boolean isLevelUnlocked(int level) {
        if (level >= 1 && level <= maxLevel) {
            return levelUnlocked[level];
        }
        return false;
    }

    /**
     * Reset to level 1 and lock all other levels
     */
    public void resetProgress() {
        currentLevel = 1;
        for (int i = 2; i <= maxLevel; i++) {
            levelUnlocked[i] = false;
        }
    }

    /**
     * Unlock all levels (cheat/test mode)
     */
    public void unlockAllLevels() {
        for (int i = 1; i <= maxLevel; i++) {
            levelUnlocked[i] = true;
        }
    }

    /**
     * Get level difficulty multiplier
     */
    public double getDifficultyMultiplier() {
        return getDifficultyMultiplier(currentLevel);
    }

    /**
     * Get level difficulty multiplier for specific level
     */
    public double getDifficultyMultiplier(int level) {
        switch (level) {
            case 1: return 1.0;
            case 2: return 1.3;
            case 3: return 1.6;
            default: return 1.0;
        }
    }

    /**
     * Get level name/description
     */
    public String getLevelName() {
        return getLevelName(currentLevel);
    }

    /**
     * Get level name/description for specific level
     */
    public String getLevelName(int level) {
        switch (level) {
            case 1: return "Forest Valley";
            case 2: return "Mountain Pass";
            case 3: return "Sky Temple";
            default: return "Unknown Level";
        }
    }

    /**
     * Get level description
     */
    public String getLevelDescription() {
        return getLevelDescription(currentLevel);
    }

    /**
     * Get level description for specific level
     */
    public String getLevelDescription(int level) {
        switch (level) {
            case 1: return "A peaceful forest with gentle obstacles";
            case 2: return "Rocky mountains with challenging terrain";
            case 3: return "Ancient temple floating in the clouds";
            default: return "Unknown location";
        }
    }

    /**
     * Get recommended score for level completion
     */
    public int getRecommendedScore() {
        return getRecommendedScore(currentLevel);
    }

    /**
     * Get recommended score for specific level completion
     */
    public int getRecommendedScore(int level) {
        return Constants.SCORE_TO_NEXT_LEVEL * level;
    }

    /**
     * Get level completion percentage based on score
     */
    public double getCompletionPercentage(int score) {
        int requiredScore = Constants.SCORE_TO_NEXT_LEVEL;
        int currentLevelScore = score % requiredScore;
        return Math.min(100.0, (currentLevelScore * 100.0) / requiredScore);
    }

    /**
     * Check if level is completed based on score
     */
    public boolean isLevelCompleted(int score) {
        return score >= Constants.SCORE_TO_NEXT_LEVEL * currentLevel;
    }

    /**
     * Get next level preview (for UI)
     */
    public String getNextLevelPreview() {
        if (canAdvanceLevel()) {
            int nextLevel = currentLevel + 1;
            return "Next: " + getLevelName(nextLevel);
        }
        return "Final Level!";
    }

    /**
     * Get all unlocked levels
     */
    public int[] getUnlockedLevels() {
        int count = 0;
        for (int i = 1; i <= maxLevel; i++) {
            if (levelUnlocked[i]) count++;
        }

        int[] unlockedLevels = new int[count];
        int index = 0;
        for (int i = 1; i <= maxLevel; i++) {
            if (levelUnlocked[i]) {
                unlockedLevels[index++] = i;
            }
        }

        return unlockedLevels;
    }

    /**
     * Get progress summary
     */
    public String getProgressSummary() {
        int unlockedCount = 0;
        for (int i = 1; i <= maxLevel; i++) {
            if (levelUnlocked[i]) unlockedCount++;
        }

        return "Progress: " + unlockedCount + "/" + maxLevel + " levels unlocked";
    }

    // Getters
    public int getCurrentLevel() {
        return currentLevel;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public boolean isMaxLevel() {
        return currentLevel == maxLevel;
    }

    public boolean isFirstLevel() {
        return currentLevel == 1;
    }
}