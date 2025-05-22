## 📁 Struktur Folder

flappy-bird-project/
│
├── assets/
│   ├── images/
│   │   ├── backgrounds/
│   │   │   ├── bg_level1.png
│   │   │   ├── bg_level2.png
│   │   │   └── bg_level3.png
│   │   ├── birds/
│   │   │   ├── bird_skin1.png
│   │   │   ├── bird_skin2.png
│   │   │   └── bird_skin3.png
│   │   └── obstacles/
│   │       ├── obstacle_level1.png
│   │       ├── obstacle_level2.png
│   │       └── obstacle_level3.png
│   └── sounds/
│       ├── jump.wav
│       ├── point.wav
│       └── game_over.wav
│
├── src/
│   ├── main/
│   │   └── FlappyBirdGame.java        # Main class untuk menjalankan game
│   │
│   ├── core/
│   │   ├── GameManager.java           # Atur transisi antar state, level, skor, dll.
│   │   └── LevelManager.java          # Logika perubahan level berdasarkan skor
│   │
│   ├── entity/
│   │   ├── Bird.java                  # Representasi player (burung)
│   │   ├── Obstacle.java              # Representasi rintangan
│   │   └── Background.java            # Representasi background sesuai level
│   │
│   ├── ui/
│   │   ├── MainMenu.java              # UI start dan select skin
│   │   ├── SkinSelector.java          # UI pemilihan skin
│   │   └── GameOverScreen.java       # UI game over dan skor akhir
│   │
│   ├── utils/
│   │   ├── AssetsLoader.java          # Memuat semua gambar, suara, dll.
│   │   └── Constants.java             # Konstanta global seperti ukuran layar, kecepatan
│
├── .gitignore
├── README.md
└── build.gradle / pom.xml            # Tergantung kamu pakai Gradle atau Maven
