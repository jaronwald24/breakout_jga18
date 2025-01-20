package breakout;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PowerUp {
  private Rectangle shape;
  private String powerUpName;
  private double fallSpeed;

  public PowerUp(int x, int y, int size, String powerUpName, double fallSpeed) {
    this.shape = new Rectangle(x, y, size, size);
    this.shape.setFill(Color.MAGENTA);
    this.powerUpName = powerUpName;
    this.fallSpeed = fallSpeed;
  }

  public void fallDown(double elapsedTime) {
    shape.setY(shape.getY() + fallSpeed * elapsedTime);
  }

  //returns the rectnagle object
  public Rectangle getRectangle() {
    return shape;
  }

  //returns the type of powerup
  public String getPowerUpName() {
    return powerUpName;
  }

}
