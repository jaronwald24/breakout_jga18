package breakout;

import java.util.Random;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import static breakout.Main.SIZE;


public class Ball {
  private int size;
  private Circle myBall;


  public static final int SLOW_BALL_SPEED = 120;
  public static final int FAST__BALL_SPEED = 200;

  //this code was taken from the example code for bouncer lab
  // share one "dice" among all blocks (improves overall randomness of game)
  private static final Random DICE = new Random();
  private Point2D velocity;



  public Ball(int size) {
    this.size = size;
    myBall = new Circle(size/2);
    myBall.setFill(Color.WHITE);

    //the logic on lines 33-34 were adapted from the bouncer lab
    myBall.setCenterX(getRandomInRange(size, SIZE - size));
    myBall.setCenterY(getRandomInRange(size, SIZE - size));

    velocity = new Point2D(SLOW_BALL_SPEED, -SLOW_BALL_SPEED);
  }

  //returns the Circle object of the ball
  public Circle getBall() {
    return myBall;
  }

  //handles the movement of the ball at each moment
  public void moveBall(double elapsedTime) {
    myBall.setCenterX(myBall.getCenterX() + velocity.getX() * elapsedTime);
    myBall.setCenterY(myBall.getCenterY() + velocity.getY() * elapsedTime);
  }

  //handles the ball bouncing off the walls
  public void wallBounces() {
    if (myBall.getCenterX() - myBall.getRadius() <= 0 || myBall.getCenterX() + myBall.getRadius() >= SIZE) {
      velocity = new Point2D(-velocity.getX(), velocity.getY());
    }

    if (myBall.getCenterY() - myBall.getRadius() <= 0 || myBall.getCenterY() + myBall.getRadius() >= SIZE) {
      YChangeBounce();
    }
  }

  //handle change in vertical direction on a Bounce
  public void YChangeBounce() {
      velocity = new Point2D(velocity.getX(), -velocity.getY());
  }

  //gets a random, non zero value in between min and max
  private int getRandomInRange(int min, int max) {
    return min + DICE.nextInt(max - min) + 1;
  }

  private void initalizeMovement() {
    Random random = new Random();

  }
}
