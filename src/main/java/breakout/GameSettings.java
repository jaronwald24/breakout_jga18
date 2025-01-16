package breakout;

public class GameSettings {
  private String level;
  private int score;
  private int lives;

  public GameSettings(String level) {
    this.level = level;
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

  //losing a life and returns whether or not the game is over
  public boolean decreaseLife() {
    lives -= 1;
    return lives > 0;
  }
}
