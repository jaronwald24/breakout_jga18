# Breakout Plan
### Justin Aronwald

## Interesting Breakout Variants

 * Super Breakout - I find the different powerups pretty interesting, especially since I played a game very similar to this. I think that adding powerups allows you to scale up the difficulty a lot too, which only makes the game more fun. I'd rather a harder game with powerups, than a slower, easier game.

 * Bricks n Balls - This is just another game that I used to be addicted to. I like the continuous challenge of the floor getting closer. I also think it's an interesting concept to be able to collect balls, so you have to balance destroying bricks and collecting balls


## Paddle Ideas

 * Idea #1 - the paddle should definitely bounce differently depending on where it hits. Like an atari breakout type game, I think that you should be able to control the ball depending on the side it hits. 

 * Idea #2 - I don't want the paddle to warp, but the paddle can extend halfway into the wall, in order to direct the ball in a way. So, the user can basically put the paddle halfway through the border, so they can adjust the hit effectively


## Block Ideas

 * Idea #1 - blocks that can't be destroyed, so you have to avoid these 

 * Idea #2 - blocks that drop power ups 

 * Idea #3 - blocks that require multiple hits to be destroyed


## Power-up Ideas

 * Idea #1 - having a ball multiplier that splits the ball into 2x, 4x, or even 8x balls

 * Idea #2 - double the length of the paddle

 * Idea #3 - double the points earned from destroying blocks


## Cheat Key Ideas

 * Idea #1 - give extra lives

 * Idea #2 - slow down the speed of the ball

 * Idea #3 - immediately clear the level

 * Idea #4 - freeze the ball


## Level Descriptions

 * Level 1: Ideally, this is the most basic level. So, to start, I'd keep it with just rows of blocks, with a slow ball as well. For this easier level, I'd have few power ups, dominated by doubling points and extending the paddle so to make it not super easy. If at all, there would be 5-10% of the blocks be unbreakable and 30-45% of the blocks would require multiple hits.  

 If each number represents the number of hits, with -1 being unbreakable, a picture could be:

        4 -1 4 4 4 4 4
        -1 3 2 3 2 3 2
        1 1 1 -1 1 1 1
        1 1 1 1 1 1 1 

 * Level 2: This would be the more difficult level. With this one, I'd have blocks scattered in a pyramid/diagonal design with gaps to make it more difficult. Here, there would be more consistent power up drops, with a faster ball speed. If possible, I'd have the ball speed up when half the bricks have been cleared. Because of the difficulty, the powerups would be more extreme, like muultiplying the number of balls. 

If each number represents the number of hits, with -1 being unbreakable, and x being a gap, a picture could be:

        5 x 5 x 5 x 5
       4 -1 4 4 -1 4 4 4
      3 x x 3 x x 3 x x 3
     2 2 -1 2 2 2 2 -1 2 2 2

## Class Ideas

 * Block - I think a block class is needed, so that we can just call this block class to create new instances of the block. It would need all the different identifiers like number of hits needed and what type of block it is. One method it would need, is a method that defines what happens when the block is hit, so a onHit method.

 * Ball - A ball class would be beneficial, as we modify the ball's position and interactions constantly. Furthermore, if we replicate the number of balls, it would be helpful to have this class. One method we will need is a simple move method, that moves the ball and checks for collisions.

 * Paddle - We need a paddle class to handle the interactions with the paddle, whether it be the bounces or the extensions. One method for this could be a change size method that modifies the paddle when the power up occurs. 

 * Game - I think a game class would be extremely helpful in order to maintain the overall state of the game. That could include the levels, scoring, or whether or not the game has started. One method could be the start game method, which sets the blocks up, resets the lives, and just overall initializes the game.

