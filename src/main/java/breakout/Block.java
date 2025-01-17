package breakout;


import javafx.scene.shape.Rectangle;

//
public class Block {
  //standard information
  public static final int width = 50;
  public static final int height = 10;
  private double xPosition;
  private double yPosition;
  private Rectangle myBlock;

  //potential add ons
  private boolean isPowerUp;
  private boolean isUnbreakable;
  private int health;

  public Block(double xPosition, double yPosition, boolean isPowerUp, boolean isUnbreakable, int health) {
    this.xPosition = xPosition;
    this.yPosition = yPosition;
    this.isPowerUp = isPowerUp;
    this.isUnbreakable = isUnbreakable;
    this.health = health;

    myBlock = new Rectangle(width, height);
    myBlock.setX(xPosition);
    myBlock.setY(yPosition);
  }

  //returns the rectangle object
  public Rectangle getBlock() {
    return myBlock;
  }


  // this checks if a block has been destroyed
  public boolean isDestroyed() {
    return health <= 0;
  }

  //this handles whenever a block is hit by the ball
  public boolean hit() {
    health--;
    return isDestroyed();
  }
}
