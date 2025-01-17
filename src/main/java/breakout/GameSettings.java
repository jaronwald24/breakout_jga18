package breakout;

public class GameSettings {
  private String level;
  private int score;
  private int lives;

  public GameSettings(int lives, String level) {
    this.level = level;
    this.lives = lives;
    score = 0;
  }

  //increase score when block is hit
  public void incrementScore() {
    score++;
  }

  //get user score
  public int getScore() {
    return score;
  }

  // getter for the lives
  public int getLives() {
    return lives;
  }

  //getter for the level
  public String getLevel() {
    return level;
  }
  //setter for the level
  public void setLevel(String level) {
    this.level = level;
  }

  //losing a life and returns whether or not the game is over
  public boolean decreaseLife() {
    lives -= 1;
    return lives > 0;
  }

  //advance to the next level
  public void advanceToNextLevel() {
    int currentLevel = Integer.parseInt(level.substring(4, 6));
    String nextLevel = String.format("lvl_%02d.txt", currentLevel + 1);
    System.out.println("New Level: " + nextLevel);
    setLevel(nextLevel);
  }
}
