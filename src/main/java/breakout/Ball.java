package breakout;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import static breakout.Main.SIZE;


public class Ball {
  private int size;
  private int speed;
  private Circle myBall;
  private int xDirection;
  private int yDirection;

  public Ball(int size, int speed, int xDirection, int yDirection) {
    this.size = size;
    this.speed = speed;
    this.xDirection = xDirection;
    this.yDirection = yDirection;

    this.myBall = new Circle(size/2);
    this.myBall.setFill(Color.WHITE);
    this.myBall.setCenterX(SIZE/2);
    this.myBall.setCenterY(SIZE/2);
  }

  //returns the Circle object of the ball
  public Circle getBall() {
    return myBall;
  }

  //handles the movement of the ball at each moment
  public void moveBall(double elapsedTime) {
    myBall.setCenterX(myBall.getCenterX() + xDirection * speed * elapsedTime);
    myBall.setCenterY(myBall.getCenterY() + yDirection * speed * elapsedTime);

    if (myBall.getCenterX() - myBall.getRadius() <= 0 || myBall.getCenterX() + myBall.getRadius() >= SIZE) {
      xDirection *= -1;
    }

    if (myBall.getCenterY() - myBall.getRadius() <= 0 || myBall.getCenterY() + myBall.getRadius() >= SIZE) {
      yDirection *= -1;
    }
  }
}
