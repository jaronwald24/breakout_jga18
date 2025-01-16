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

  //losing a life and returns whether or not the game is over
  public boolean decreaseLife() {
    lives -= 1;
    return lives > 0;
  }
}
