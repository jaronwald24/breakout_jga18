package breakout;

import java.util.Objects;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Paddle {
  private int width;
  private int height;
  private double xPosition;
  private double yPosition;
  private Rectangle myPaddle;
  private static final int GAME_SIZE = 400;

  public Paddle(int width, int height, double xPosition, double yPosition) {
    this.width = width;
    this.height = height;
    this.xPosition = xPosition;
    this.yPosition = yPosition;

    //set up the paddle Rectangle object
    this.myPaddle = new Rectangle(width, height);
    this.myPaddle.setFill(Color.WHITE);
    this.myPaddle.setX(xPosition);
    this.myPaddle.setY(yPosition);
  }

  public Rectangle getPaddle() {
    return myPaddle;
  }

  public void setPaddlePosition(double xPosition, String level) {
    if (level.equals("lvl_01.txt")) {
      if (xPosition < 0) {
        xPosition = 0;
      }
      else if (xPosition > GAME_SIZE - width) {
        xPosition = GAME_SIZE - width;
      }
    } else if (level.equals("lvl_02.txt")) {
      if (xPosition < -myPaddle.getWidth() / 2) {
        xPosition = -myPaddle.getWidth() / 2;
      }
      else if (xPosition > GAME_SIZE - width + myPaddle.getWidth() / 2) {
        xPosition = GAME_SIZE - width + myPaddle.getWidth() / 2;
      }
    } else {
      if (xPosition < -myPaddle.getWidth()) {
        xPosition = -myPaddle.getWidth();
      }
      else if (xPosition > GAME_SIZE - width + myPaddle.getWidth()) {
        xPosition = GAME_SIZE - width + myPaddle.getWidth();
      }
    }
    myPaddle.setX(xPosition);
  }
}
