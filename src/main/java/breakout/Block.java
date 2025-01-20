package breakout;


import static java.util.Map.entry;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

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
  private int startingHealth;

  //Color mapper
  private Map<Integer, Color> colorMapper = Map.ofEntries(
      entry(0, Color.BLACK),
      entry(1, Color.GREEN),
      entry(2, Color.YELLOW),
      entry(3, Color.ORANGE),
      entry(4, Color.RED),
      entry(5, Color.GOLD)
  );

  public Block(double xPosition, double yPosition, boolean isPowerUp, boolean isUnbreakable, int health) {
    this.xPosition = xPosition;
    this.yPosition = yPosition;
    this.isPowerUp = isPowerUp;
    this.isUnbreakable = isUnbreakable;

    this.health = health;
    this.startingHealth = health;

    myBlock = new Rectangle(width, height);
    myBlock.setX(xPosition);
    myBlock.setY(yPosition);

    updateBlockColor();
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
    updateBlockColor();
    return isDestroyed();
  }

  //check if a block is a power up
  public boolean isPowerUpBlock() {
    return isPowerUp;
  }


  // changes the blocks color after hits or at start
  private void updateBlockColor() {
    if (colorMapper.containsKey(health) && !isPowerUpBlock()) {
      myBlock.setFill(colorMapper.get(health));
    } else {
      myBlock.setFill(Color.GOLD);
    }
  }
}
