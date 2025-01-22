package breakout;


import static java.util.Map.entry;

import java.util.Map;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

//
public class Block {
  //standard information
  public static final int width = 50;
  public static final int height = 10;
  private final Rectangle myBlock;

  //potential add ons
  private final boolean isPowerUp;
  private final boolean isUnbreakable;
  private int health;

  //Color mapper
  private final Map<Integer, Color> colorMapper = Map.ofEntries(
      entry(-1, Color.GREY),
      entry(0, Color.BLACK),
      entry(1, Color.GREEN),
      entry(2, Color.YELLOW),
      entry(3, Color.ORANGE),
      entry(4, Color.RED),
      entry(5, Color.DARKGOLDENROD)
  );

  public Block(double xPosition, double yPosition, boolean isPowerUp, boolean isUnbreakable, int health) {
    this.isPowerUp = isPowerUp;
    this.isUnbreakable = isUnbreakable;

    this.health = health;

    myBlock = new Rectangle(width, height);
    myBlock.setX(xPosition);
    myBlock.setY(yPosition);

    updateBlockColor();
  }

  /**
   *
   * @return - the Rectangle object of the ball
   */
  public Rectangle getBlock() {
    return myBlock;
  }


  /**
   *
   * @return - boolean represneting whether or no a block is destroyed
   */
  public boolean isDestroyed() {
    return health <= 0;
  }


  /**
   * This handles whenever a block is hit by the ball
   * @return - whether or not a block has health 0
   */
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

  /**
   *
   * @param x - horizontal position of the powerUp
   * @param y - vertical position of the powerUp
   * @return - returns the power up of class PowerUp
   */
  public PowerUp dropPowerUp(int x, int y) {
    String[] powerUpNames = {"expands", "speedUp", "ballGrow"};
    String randomPowerUpName = powerUpNames[(int) (Math.random() * powerUpNames.length)];

    PowerUp powerUp = new PowerUp(x, y, 20, randomPowerUpName, 100);

    return powerUp;
  }

  /**
   *
   * @return - whether or not a block is unbreakable
   */
  public boolean isUnbreakable() {
    return isUnbreakable;
  }
}
