package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
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

    public static final int BALL_SIZE = 20;
    public static final int BALL_SPEED = 100;

    public static final int PADDLE_WIDTH = 100;
    public static final int PADDLE_HEIGHT = 20;
    public static final int PADDLE_SPEED = 20;
    public static final int PADDLE_X_POS = 100;
    public static final int PADDLE_Y_POS = 300;


    // This scene contains the various shapes and has methods that act upon them
    private Scene startScene;

    private Ball startBall;
    private Paddle startPaddle;

    /**
     * Initialize what will be displayed.
     */
    @Override
    public void start(Stage stage) {

        startBall = new Ball(BALL_SIZE, BALL_SPEED, 0, 1);
        startPaddle = new Paddle(PADDLE_WIDTH, PADDLE_HEIGHT, PADDLE_X_POS, PADDLE_Y_POS);

        Group root = new Group(startBall.getBall(), startPaddle.getPaddle());

        startScene = new Scene(root, SIZE, SIZE, DUKE_BLUE);

        startScene.setOnKeyPressed(e -> handleKeyboardInput(e.getCode()));

        stage.setScene(startScene);

        stage.setTitle(TITLE);
        stage.show();

        // set the animation to let the game run
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(new KeyFrame(Duration.seconds(SECOND_DELAY), e -> step(SECOND_DELAY)));
        animation.play();
    }

    //handles the keyboard presses -- specifically for moving paddle
    private void handleKeyboardInput(KeyCode code) {
        switch(code) {
            case RIGHT -> startPaddle.setPaddlePosition(startPaddle.getPaddle().getX() + PADDLE_SPEED);
            case LEFT -> startPaddle.setPaddlePosition(startPaddle.getPaddle().getX() - PADDLE_SPEED);
        }
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