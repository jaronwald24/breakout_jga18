package breakout;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Paddle {
  private int width;
  private int height;
  private double xPosition;
  private double yPosition;
  private Rectangle myPaddle;

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

  public void setPaddlePosition(double xPosition) {
    if (xPosition < 0) {
      xPosition = 0;
    }
    else if (xPosition > Main.SIZE - width) {
      xPosition = Main.SIZE - width;
    }
    myPaddle.setX(xPosition);
  }
}
