package breakout;

import java.util.Random;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javax.management.monitor.GaugeMonitorMBean;


public class Ball {
  private static final int GAME_SIZE = 600;
  private int size;
  private Circle myBall;


  private static final int SLOW_BALL_SPEED = 350;

  //this code was taken from the example code for bouncer lab
  // share one "dice" among all blocks (improves overall randomness of game)
  private static final Random DICE = new Random();
  private Point2D velocity;



  public Ball(int size) {
    //size = diameter
    this.size = size;
    myBall = new Circle(size/2);
    myBall.setFill(Color.WHITE);

    //the logic on lines 33-34 were adapted from the bouncer lab
    myBall.setCenterX(getRandomInRange(size, GAME_SIZE - size));
    myBall.setCenterY(getRandomInRange(size, GAME_SIZE - size));

    velocity = new Point2D(SLOW_BALL_SPEED, -SLOW_BALL_SPEED);
  }

  /**
   *
   * @return the Circle object of the ball
   */
  public Circle getBall() {
    return myBall;
  }

  /**
   *
   * @param size - the new size that the ball will become
   */
  public void setBallSize(double size) {
    this.size = (int) size;
    myBall.setRadius(size/2);
  }

  /**
   *
   * @return - the size of the ball
   */
  public double getSize() {
    return size;
  }

  /**
   *
   * @param elapsedTime - the time for each step for the animation
   */
  public void moveBall(double elapsedTime) {
    myBall.setCenterX(myBall.getCenterX() + velocity.getX() * elapsedTime);
    myBall.setCenterY(myBall.getCenterY() + velocity.getY() * elapsedTime);
  }

  /**
   *
   * @return - a boolean representing whether or not a life is lost
   */
  public boolean wallBounces() {
    if (myBall.getCenterX() - myBall.getRadius() <= 0 || myBall.getCenterX() + myBall.getRadius() >= GAME_SIZE) {
      velocity = new Point2D(-velocity.getX(), velocity.getY());
    }

    if (myBall.getCenterY() - myBall.getRadius() <= 0 ) {
      Point2D curVelocity = getVelocity();
      changeVelocity(curVelocity.getX(), -curVelocity.getY());
    }

    if (myBall.getCenterY() + myBall.getRadius() >= GAME_SIZE) {
      resetBall();
      return true;
    }

    return false;
  }

  /**
   *
   * @param xVelocity - new velocity in x direction
   * @param yVelocity - new velocity in y direction
   */
  public void changeVelocity(double xVelocity, double yVelocity) {
    velocity = new Point2D(xVelocity, yVelocity);
  }

  /**
   *
   * @return - the velocity vector
   */
  public Point2D getVelocity() {
    return velocity;
  }

  //gets a random, non zero value in between min and max
  private int getRandomInRange(int min, int max) {
    return min + DICE.nextInt(max - min) + 1;
  }

  //Resets the location of the ball and sets it moving upward
  public void resetBall() {
    myBall.setCenterX(getRandomInRange(size, GAME_SIZE - size));
    myBall.setCenterY(getRandomInRange(size, GAME_SIZE - size + 100));

    velocity = new Point2D(SLOW_BALL_SPEED, -SLOW_BALL_SPEED);
  }

}
