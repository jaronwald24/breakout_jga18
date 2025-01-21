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

  private static final int START_PADDLE_X_POS = 150;
  private static final int START_PADDLE_Y_POS = 350;

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

  /**
   *
   * @return the Rectangle object for the paddle
   */
  public Rectangle getPaddle() {
    return myPaddle;
  }

  /**
   *
   * @param xPosition - x position of the paddle
   * @param level - current level of the game
   */
  public void setPaddlePosition(double xPosition, String level) {
    double leftBoundary;
    double rightBoundary;

    // use the levels to determine boundaries for the paddle (higher level = more flexibility)
    if (level.equals("lvl_01.txt")) {
      leftBoundary = 0;
      rightBoundary = GAME_SIZE - width;
    } else if (level.equals("lvl_02.txt")) {
      leftBoundary = -myPaddle.getWidth() / 2;
      rightBoundary = GAME_SIZE - width + myPaddle.getWidth() / 2;
    } else {
      leftBoundary = -myPaddle.getWidth();
      rightBoundary = GAME_SIZE - width + myPaddle.getWidth();
    }

    // Apply boundaries
    if (xPosition < leftBoundary) {
      xPosition = leftBoundary;
    } else if (xPosition > rightBoundary) {
      xPosition = rightBoundary;
    }

    myPaddle.setX(xPosition);
  }

  //resets paddle position

  public void resetPaddlePosition() {
    myPaddle.setX(START_PADDLE_X_POS);
    myPaddle.setY(START_PADDLE_Y_POS);
  }

  /**
   *
   * @param width - a width that will be set for the paddle
   */
  //sets new width of paddle
  public void setWidth(int width) {
    this.width = width;
    myPaddle.setWidth(width);
  }
}
