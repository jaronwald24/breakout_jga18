package breakout;


import javafx.scene.shape.Rectangle;

public class Block {
  //standard information
  public static final int width = 50;
  public static final int height = 10;
  private double xPosition;
  private double yPosition;
  private Rectangle myBlock;

  //potential add ons
  private boolean isPowerUp;
  private int health;

  public Block(double xPosition, double yPosition, boolean isPowerUp, int health) {
    this.xPosition = xPosition;
    this.yPosition = yPosition;
    this.isPowerUp = isPowerUp;
    this.health = health;

    myBlock = new Rectangle(width, height);
    myBlock.setX(xPosition);
    myBlock.setY(yPosition);
  }

  public boolean isDestroyed() {
    return health <= 0;
  }

  public boolean hit() {
    health--;
    return isDestroyed();
  }
}
