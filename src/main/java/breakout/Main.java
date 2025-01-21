package breakout;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * Feel free to completely change this code or delete it entirely.
 *
 * @author Justin Aronwald
 */
public class Main extends Application {

    // useful names for constant values used
    private static final String TITLE = "Example JavaFX Animation";
    private static final Color DUKE_BLUE = new Color(0, 0.188, 0.529, 1);
    private static final int SIZE = 400;

    private static final int FRAMES_PER_SECOND = 60;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private static final Paint BACKGROUND = Color.AZURE;

    //ball constants
    private static final int BALL_SIZE = 20;
    private static final int BALL_SPEED = 200;

    //paddle constants
    private static final int PADDLE_WIDTH = 100;
    private static final int PADDLE_HEIGHT = 10;
    private static final int PADDLE_SPEED = 40;
    private static final int PADDLE_X_POS = 100;
    private static final int PADDLE_Y_POS = 350;

    //block constants
    private static final int BLOCK_X_SPACING = 10;
    private static final int BLOCK_Y_SPACING = 30;
    private static final int TOP_ROW_SPACING = 30;
    private static final int NUMBER_OF_ROWS = 2;

    //game settings constants
    private static int REMAINING_LIVES = 3;


    // This scene contains the various shapes and has methods that act upon them
    private Scene startScene;

    //initialization of classes
    private Timeline animation;
    private Ball startBall;
    private Paddle startPaddle;
    private ArrayList<Block> blocks;
    private GameSettings gameSettings;
    private LevelTranslator levelTranslator;

    //various Texts displayed
    private Text livesText;
    private Text scoreText;
    private Text levelText;

    // prev variables for powerupsp
    private double prevXVelocity;
    private double prevYVelocity;
    private double originalSpeed;
    private int prevBallSize;



    private ArrayList<PowerUp> activePowerUps = new ArrayList<>();

    private Group root;
    /**
     * Initialize what will be displayed.
     */
    @Override
    public void start(Stage stage) throws FileNotFoundException {
        SplashScreen splashScreen = new SplashScreen();
        Scene splashScene = splashScreen.createIntroScreen(stage, "Welcome", "Use the arrows to move the paddle.");
        stage.setScene(splashScene);
        stage.show();

        //on click of the enter button, the game begins
        splashScene.setOnKeyPressed(e -> {
          if (Objects.requireNonNull(e.getCode()) == KeyCode.ENTER) {
            try {
              showGameScreen(stage);
            } catch (FileNotFoundException ex) {
              throw new RuntimeException(ex);
            }
          }
        });

    }

    //initializes all of the game setup and animation
    private void showGameScreen(Stage stage) throws FileNotFoundException {
        startBall = new Ball(BALL_SIZE);
        startPaddle = new Paddle(PADDLE_WIDTH, PADDLE_HEIGHT, PADDLE_X_POS, PADDLE_Y_POS);
        gameSettings = new GameSettings(REMAINING_LIVES, "lvl_01.txt");
        levelTranslator = new LevelTranslator();

        blocks = setUpBlocks();
        root = new Group(startBall.getBall(), startPaddle.getPaddle());

        //add blocks to root
        for (Block block : blocks) {
            root.getChildren().add(block.getBlock());
        }
        //add text to root
        setUpGameSettings();


        startScene = new Scene(root, SIZE, SIZE, DUKE_BLUE);
        startScene.setOnKeyPressed(e -> handleKeyboardInput(e.getCode()));
        stage.setScene(startScene);

        stage.setTitle(TITLE);
        stage.show();

        // set the animation to let the game run
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(new KeyFrame(Duration.seconds(SECOND_DELAY), e -> {
            try {
                step(SECOND_DELAY);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }));
        animation.play();
    }

    //handles the keyboard presses -- specifically for moving paddle
    private void handleKeyboardInput(KeyCode code) {
        switch(code) {
            case RIGHT -> startPaddle.setPaddlePosition(startPaddle.getPaddle().getX() + PADDLE_SPEED, gameSettings.getLevel());
            case LEFT -> startPaddle.setPaddlePosition(startPaddle.getPaddle().getX() - PADDLE_SPEED, gameSettings.getLevel());
        }
    }

    private void handlePaddleIntersection() {
        Shape intersection = Shape.intersect(startPaddle.getPaddle(), startBall.getBall());
        if (!intersection.getBoundsInLocal().isEmpty()) {
            handlePositionalBounce();
            Point2D curVelocity = startBall.getVelocity();
            startBall.changeVelocity(curVelocity.getX(), -curVelocity.getY());
        }
    }

    private void handleBlockIntersection() throws FileNotFoundException {
        //The iterator from line 127-128 was prompted using ChatGPT after noticing that my solution was inefficient and quadratic runtime.
        Iterator<Block> iterator = blocks.iterator();
        while (iterator.hasNext()) {
            Block block = iterator.next();
            Shape intersection = Shape.intersect(block.getBlock(), startBall.getBall());
            if (!intersection.getBoundsInLocal().isEmpty()) {
                Point2D curVelocity = startBall.getVelocity();
                startBall.changeVelocity(curVelocity.getX(), -curVelocity.getY());
                if (block.isUnbreakable()) {
                    continue;
                }

                incrementScore(block);

                // handle powerups
                if (block.isPowerUpBlock()) {
                    int x = (int) block.getBlock().getX();
                    int y = (int) block.getBlock().getY();
                    PowerUp powerUp = block.dropPowerUp(x, y);
                    activePowerUps.add(powerUp);
                    root.getChildren().add(powerUp.getRectangle());
                }
                if (block.hit()) {
                    iterator.remove();
                    root.getChildren().remove(block.getBlock());
                }

            }
        }
        //level or game is over
        if (allBreakableBlocksCleared()) {
            boolean nextLevelExists = gameOverOrSetUpNextLevel();
            if (!nextLevelExists) {
               endGame(true);
            }
        }
    }

    // the following code has been adapted from the bounce lab
    // Handle game "rules" for every "moment":
    private void step (double elapsedTime) throws FileNotFoundException {
        // update "actors" attributes a little bit at a time and at a "constant" rate (no matter how many frames per second)
        startBall.moveBall(elapsedTime);
        if (startBall.wallBounces()) {
            decreaseLives();
        }
        handlePaddleIntersection();
        handleBlockIntersection();
        powerUpPaddleInteraction(elapsedTime);
    }

    //create the arrays of blocks to start game
    private ArrayList<Block> setUpBlocks() throws FileNotFoundException {
        //create list of blocks based on the number of rows and blocks per row
        ArrayList<Block> blocks = levelTranslator.generateBlocksFromFile(gameSettings.getLevel());

        return blocks;
    }

    // sets the text of initial lives
    public void setUpGameSettings() {
        livesText = createText("Lives Remaining: " + gameSettings.getLives(), 10, TOP_ROW_SPACING, Color.WHITE, Font.font("Arial", 16));
        scoreText = createText("Score: " + gameSettings.getScore(), SIZE / 2.25, TOP_ROW_SPACING, Color.WHITE, Font.font("Arial", 16));

        // Get the integer part of the level
        String level = gameSettings.getLevel();
        int levelNumber = Integer.parseInt(level.substring(4, 6));

        levelText = createText("Current Level: " + levelNumber, SIZE / 1.5, TOP_ROW_SPACING, Color.WHITE, Font.font("Arial", 16));

        root.getChildren().addAll(livesText, scoreText, levelText);
    }

    // sets the next level up with bricks or user wins the game
    public boolean gameOverOrSetUpNextLevel() throws FileNotFoundException {
        for (Block block : blocks) {
            if (block.isUnbreakable()) {
                root.getChildren().remove(block.getBlock());
            }
        }
        boolean nextLevelExists = gameSettings.advanceToNextLevel();

        if (!nextLevelExists) {
            return false;
        }

        String level = gameSettings.getLevel();
        int levelNumber = Integer.parseInt(level.substring(4, 6));
        levelText.setText("Current level: " + levelNumber);

        blocks = setUpBlocks();

        //add blocks to root
        for (Block block : blocks) {
            root.getChildren().add(block.getBlock());
        }
        return true;
    }

    public void decreaseLives() {
        if(!gameSettings.decreaseLife()){
            endGame(false);
        }
        livesText.setText("Lives Remaining: " + gameSettings.getLives());
    }

    //ends the game, clears elements
    public void endGame(boolean winner) {
        animation.stop();
        root.getChildren().removeAll(root.getChildren());
        String message;

        if (winner) {
            message = "Game Over, you won!";
        } else {
            message = "Game Over, you lost!";
        }

        Text endText = createText(message, 10, TOP_ROW_SPACING / 2, Color.BLACK, Font.font("Arial", 40));
        Text instructions = createText("Press enter to play again.", 10, TOP_ROW_SPACING / 3.0, Color.BLACK, Font.font("Arial", 16));
        //lines 260-263 were AI generated to assist with spacing

        // Create a VBox to hold all the elements and center them
        VBox layout = new VBox(20); // Spacing of 20 between elements
        layout.setAlignment(Pos.CENTER); // Center elements horizontally and vertically
        layout.getChildren().addAll(endText, livesText, scoreText, instructions);

        root.getChildren().add(layout);

        startScene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                try {
                    REMAINING_LIVES = 3;
                    showGameScreen((Stage) root.getScene().getWindow());
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

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

    //increment score in game settings then display it
    public void incrementScore(Block block) {
        gameSettings.incrementScore(block);
        scoreText.setText("Score: " + gameSettings.getScore());
    }

    private void handlePositionalBounce() {
        double horizontalBallPosition = startBall.getBall().getCenterX();
        double horizontalPaddlePosition = startPaddle.getPaddle().getX();

        double paddleWidth = startPaddle.getPaddle().getWidth();
        // The calculation of value * 2 - 1 gets the relative position from (-1,1)
        double relativeHitPosition = (horizontalBallPosition - horizontalPaddlePosition) / paddleWidth * 2 - 1;

        double newHorizontalVelocity = relativeHitPosition * BALL_SPEED;

        Point2D curVelocity = startBall.getVelocity();
        startBall.changeVelocity(newHorizontalVelocity, curVelocity.getY());
    }

    //handles the powerUp interacting with the paddle
    private void powerUpPaddleInteraction(double elapsedTime) {
        //logic created by me, but adapted from code originally written by ChatGPT
        Iterator<PowerUp> iterator = activePowerUps.iterator();
        while (iterator.hasNext()) {
            PowerUp powerUp = iterator.next();
            powerUp.fallDown(elapsedTime);

            //check for intersection with the paddle
            Shape intersection = Shape.intersect(startPaddle.getPaddle(), powerUp.getRectangle());
            if (!intersection.getBoundsInLocal().isEmpty()) {
                applyPowerUp(powerUp.getPowerUpName());
                root.getChildren().remove(powerUp.getRectangle());
                iterator.remove();
            }

            //remove power-ups that fall off the screen
            if (powerUp.getRectangle().getY() > SIZE) {
                root.getChildren().remove(powerUp.getRectangle());
                iterator.remove();
            }
        }
    }

    private void applyPowerUp(String powerUpName) {
        System.out.println("Applying power up " + powerUpName + startPaddle.getPaddle().getWidth());
        switch (powerUpName) {
            //paddle expansion
            case "expands" -> {
                startPaddle.setWidth((int) (startPaddle.getPaddle().getWidth() * 1.5));
            }
            //ball speeds increases
            case "speedUp" -> {
                double curXVelocity = startBall.getVelocity().getX();
                double curYVelocity = startBall.getVelocity().getY();
                originalSpeed = Math.sqrt(Math.pow(curXVelocity, 2) + Math.pow(curYVelocity, 2));
                startBall.changeVelocity(startBall.getVelocity().getX() * 2, startBall.getVelocity().getY() * 2);
            }
            case "balLGrow" -> {
                prevBallSize = startBall.getSize();
                startBall.setBallSize(startBall.getSize() * 1.5);
            }
            //add more if needed
        }
        System.out.println(startPaddle.getPaddle().getWidth());
        //reset after the duraction expires
        Timeline timeout = new Timeline(new KeyFrame(Duration.millis(7000), e -> resetPowerUpEffect(powerUpName)));
        timeout.setCycleCount(1);
        timeout.play();
    }

    private void resetPowerUpEffect(String powerUpName) {
        switch (powerUpName) {
            case "expands" -> {
                startPaddle.setWidth(PADDLE_WIDTH);
            }
            case "speedUp" -> {
                resetSpeedUp();
            }
            case "balLGrow" -> {
                startBall.setBallSize(prevBallSize);
            }
            //add more if needed
        }
    }

    private boolean allBreakableBlocksCleared() {
        for (Block block : blocks) {
            // if a block is breakable
            if (!block.isUnbreakable()) {
                return false;
            }
        }
        return true;
    }

    private void resetSpeedUp() {
        double currentXVelocity = startBall.getVelocity().getX();
        double currentYVelocity = startBall.getVelocity().getY();

        //get speed
        double currentSpeed = Math.sqrt(Math.pow(currentXVelocity, 2) + Math.pow(currentYVelocity, 2));

        startBall.changeVelocity((currentXVelocity / currentSpeed) * originalSpeed, (currentYVelocity / currentSpeed) * originalSpeed);
    }


    public static void main (String[]args){
            launch(args);
        }

}