# Breakout Design
## Justin Aronwald


## Design Goals
The design goals were obviously dominated by the overall functional breakout game. Once that was accomplished, I wanted the design to be fairly easy to add new power ups, and new levels. So, when a user wants a new level, they can just create whatever assortment of blocks and then it will automatically be translated into the game. Similarly, I wanted to define the power ups in a way that all you have to do is just add the power up in the application, removal, and the string array of powerups, and it will then randomly be in the mix. 

Besides that, I wanted to try focusing on modularity of code. I've never tried to make my code better-looking and more readable, so that was in the back of my mind.

## High-Level Design
At a high level, there exists the core classes that handle each aspect of the game.
These classes include:

Main class - this class serves to initialize the core components, set up the game loop, and transition between screens. It manages the game scene and the game loop animation. It coordinates the interactions with the Ball, Paddle, Block, and LevelTranslator. The main class is also responsible for handling the end and restart of the game, the ball interactions with a paddle, and a lot of the logic for the power ups.

Ball class - this class represents the ball and it's movement. It handles wall collision and trajectory changes. It stores the position, size, and velocity of the ball as well. 

Paddle class - this class handles the paddle controlled by the player, which interacts with the ball and the powerups. It stores hte paddle size, position, and appearance. It interacts largely with the Main class. 

Block class - this class represents the individual blocks in the game, that is hit and destroyed by the ball. It stores the position, size, health, and other properties relating to whether it's a power up. It handles the effect of a ball collision, including reducing health, dropping a power up, and updating the color based on a Map from health to color. It tells the Main class when a block is destroyed, and then signals a PowerUp to be dropped.

LevelTranslator class - this class generates the block configuration for each level, based on the level files located in the resources folder. It parses the text files to create Block objects and arranges them based on the file. It gives the Main class an ArrayList of Blocks for each level.

GameSettings class - this class serves to maintain game state variables like the lives, score, and current level. It's responsibilities include tracking the player's lives and score, changing levels, advancing to to the next level, or determining whether the game is over. It interacts with Main to update the score and lives displayed on screen. 

SplashScreen class - this class creates and manages the game's introduction screen. It creates a new scene with an introductory message and instructions. It gives the scene to Main to display, and triggers the start of the game when a user presses enter. 

PowerUp class - this class maintains the special effects triggered when special blocks are hit. It's responsibilities include defining the power-up and animating the power up. PowerUps are dropped by the Block objects, they interact with the Paddle class to apply the effect, and they are managed by the Main class. 

In terms of flow, the Main class sets up the screen, initializing the Ball, Paddle, and Blocks. THe SplashScreen class handles hte initial screen, and transitions to the game. The Main class handles animation through the step method. The Ball class interacts with the Paddle and the Block objects to adjust velocity and trigger hits. When all bricks are cleared, the Main class uses the GameSettings class to load the next level using the LevelTranslator class. Finally, the Main class ends the game when appropriate.

## Assumptions or Simplifications
One assumption was the collisions were handled geometrically, using the Shape.intersect. This approach simplified the integration, but led to edge cases where the ball could stick the paddle or block. 

Another assumption is that the game operates at a fixed size, so it has limited scalability. To accommodate this, one would have to change the size in a couple classes.

The power ups were also simplified, due to a lack of time. 

## Changes from the Plan

Originally, I planned for a ball multiplier power up to be able to add multiple balls, but I wasn't able to figure out the logic behind multiple balls.

I also planned for a double points power up, but ended switching that to a ball growth power up. Other smaller details include cheat key ideas, where I wanted to freeze the ball, but I changed that around to being able to manually increase your score and size of the ball.

Other than those small changes, everything is fairly consistent with my plan.


## How to Add New Levels
To add new levels, users need to make a new file with the name "lvl_insertNumberHere.txt" using the following format:
 * x means a gap
 * numbers 1-4 signify the health of a brick
 * -1 means a brick is unbreakable
 * 5 means the brick is a random powerup
 * use a space between each brick/gap
 * Important: the max amount of bricks at my current screen size is 9, so only include up to 9 bricks horizontally.

If I wanted to add a new power up, the steps are:
* In Block, add the name of the power up to the String array of power ups
* In Main, add the case in the applyPowerUp function
* In Main, add the case in the resetPowerUpEffect function
* If needed, define the change in the according class