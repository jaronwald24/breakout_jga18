package breakout;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class SplashScreen {
  private static final int SIZE = 600;

  /**
   * @param title - the title text for the new screen
   * @param description - the description text for the new screen
   * @return - returns the new Screen created for the intro screen
   */
  public Scene createIntroScreen(String title, String description) {
    Group root = new Group();

    Text titleText = createText(title, SIZE / 2.5, SIZE/3.5, Color.WHITE, new Font(18));
    Text descriptionText = createText(description, SIZE / 4.0, SIZE/3.0, Color.WHITE, new Font(18));
    Text instructions = createText("Press enter to begin!",SIZE / 3.0, SIZE/2.5, Color.WHITE, new Font(18));

    root.getChildren().addAll(titleText, descriptionText, instructions);

    return new Scene(root, SIZE, SIZE, Color.BLACK);
  }

  //creates, sets, and places text
  private Text createText(String content, double x, double y, Color color, Font font) {
    Text text = new Text();
    text.setText(content);
    text.setX(x);
    text.setY(y);
    text.setFill(color);
    text.setFont(font);
    return text;
  }


}
