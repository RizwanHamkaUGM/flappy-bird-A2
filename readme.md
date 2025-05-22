# 🐦 Flappy Bird Game

## 🏗️ Project Structure

```
flappy-bird-project/
│
├── assets/                          # Game assets
│   ├── images/
│   │   ├── backgrounds/             # Level-specific backgrounds
│   │   │   ├── bg_level1.png
│   │   │   ├── bg_level2.png
│   │   │   └── bg_level3.png
│   │   ├── birds/                   # Customizable bird skins
│   │   │   ├── bird_skin1.png
│   │   │   ├── bird_skin2.png
│   │   │   └── bird_skin3.png
│   │   └── obstacles/               # Level-specific obstacles
│   │       ├── obstacle_level1.png
│   │       ├── obstacle_level2.png
│   │       └── obstacle_level3.png
│   └── sounds/                      # Audio files
│       ├── jump.wav
│       ├── point.wav
│       └── game_over.wav
│
├── src/
│   ├── main/
│   │   └── FlappyBirdGame.java      # Application entry point
│   │
│   ├── core/                        # Game logic
│   │   ├── GameManager.java         # State management & game flow
│   │   └── LevelManager.java        # Level progression logic
│   │
│   ├── entity/                      # Game objects
│   │   ├── Bird.java                # Player character
│   │   ├── Obstacle.java            # Game obstacles
│   │   └── Background.java          # Dynamic backgrounds
│   │
│   ├── ui/                          # User interface
│   │   ├── MainMenu.java            # Start screen & main menu
│   │   ├── SkinSelector.java        # Bird customization screen
│   │   └── GameOverScreen.java      # Game over & score display
│   │
│   └── utils/                       # Utilities
│       ├── AssetsLoader.java        # Asset loading & management
│       └── Constants.java           # Global constants & configuration
│
├── .gitignore
├── README.md
└── build.gradle                     # Build configuration
```

## 🚀 Getting Started

### Prerequisites
- **Java 8** or higher
- **Gradle** (or Maven if using pom.xml)
- **IDE** (IntelliJ IDEA, Eclipse, or VS Code recommended)

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/flappy-bird-project.git
   cd flappy-bird-project
   ```

2. **Build the project**
   ```bash
   ./gradlew build
   ```

3. **Run the game**
   ```bash
   ./gradlew run
   ```

### Alternative IDE Setup
1. Import the project into your preferred IDE
2. Ensure Java 8+ is configured
3. Run `FlappyBirdGame.java` as the main class
