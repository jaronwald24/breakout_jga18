package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.Random;


/**
 * Feel free to completely change this code or delete it entirely.
 *
 * @author Justin Aronwald
 */
public class Main extends Application {

    // useful names for constant values used
    public static final String TITLE = "Example JavaFX Animation";
    public static final Color DUKE_BLUE = new Color(0, 0.188, 0.529, 1);
    public static final int SIZE = 400;

    public static final int FRAMES_PER_SECOND = 60;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final Paint BACKGROUND = Color.AZURE;

    public class Ball {

        private String img;
        private int size;
        private int speed;
        private int direction;
        private int screen_width;
        private int screen_height;
        private ImageView myBall;
        private int xDirection;
        private int yDirection;
        private double startX;
        private double startY;

        public Ball(int size, int speed, int direction, int screen_width, int screen_height,
            int xDirection, int yDirection) {
            this.size = size;
            this.speed = speed;
            this.direction = direction;
            this.img = "duke-seal-logo.png";
            this.screen_width = screen_width;
            this.screen_height = screen_height;
            this.xDirection = xDirection;
            this.yDirection = yDirection;

            // setup the imageView
            myBall = new ImageView(new Image(Main.class.getResourceAsStream(img)));
            myBall.setFitWidth(size);
            myBall.setFitHeight(size);

            Random rand = new Random();
            double randomX = rand.nextInt(screen_width - (int) myBall.getFitWidth());
            myBall.setX(randomX);

            double randomY = rand.nextInt(screen_height - (int) myBall.getFitHeight());
            myBall.setY(randomY);
        }

        public ImageView getImageView() {
            return myBall;
        }

        public void moveBall(double elapsedTime) {
            myBall.setX(myBall.getX() + xDirection * speed * elapsedTime);
            myBall.setY(myBall.getY() + yDirection * speed * elapsedTime);

            if (myBall.getX() + size >= SIZE) {
                xDirection *= -1;
            } else if (myBall.getX() <= 0) {
                xDirection *= -1;
            }

            if (myBall.getY() + size >= SIZE) {
                yDirection *= -1;
            } else if (myBall.getY() <= 0) {
                yDirection *= -1;
            }

        }
    }


    /**
     * Initialize what will be displayed.
     */
    @Override
    public void start(Stage stage) {
        Circle shape = new Circle(200, 200, 40);
        shape.setFill(Color.LIGHTSTEELBLUE);

        Group root = new Group();
        root.getChildren().add(shape);

        Scene scene = new Scene(root, SIZE, SIZE, DUKE_BLUE);
        stage.setScene(scene);

        stage.setTitle(TITLE);
        stage.show();
    }

        public static void main (String[]args){
            launch(args);
        }

}