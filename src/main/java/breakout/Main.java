package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
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

    public static final int BALL_SIZE = 20;
    public static final int BALL_SPEED = 100;

    private Ball startBall;

    // ball class embodies everything needed
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

        public Circle getBall() {
            return myBall;
        }

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


    /**
     * Initialize what will be displayed.
     */
    @Override
    public void start(Stage stage) {

        startBall = new Ball(BALL_SIZE, BALL_SPEED, 0, 1);

        Group root = new Group();
        root.getChildren().add(startBall.getBall());

        Scene scene = new Scene(root, SIZE, SIZE, DUKE_BLUE);
        stage.setScene(scene);

        stage.setTitle(TITLE);
        stage.show();

        // set the animation to let the game run
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(new KeyFrame(Duration.seconds(SECOND_DELAY), e -> step(SECOND_DELAY)));
        animation.play();
    }

    // the following code has been adapted from the bounce lab
    // Handle game "rules" for every "moment":
    private void step (double elapsedTime) {
        // update "actors" attributes a little bit at a time and at a "constant" rate (no matter how many frames per second)
        startBall.moveBall(elapsedTime);
    }


    public static void main (String[]args){
            launch(args);
        }

}