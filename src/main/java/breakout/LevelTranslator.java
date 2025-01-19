package breakout;

import static breakout.Main.BLOCK_X_SPACING;
import static breakout.Main.BLOCK_Y_SPACING;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.scene.Group;

public class LevelTranslator {
  private ArrayList<Block> createdBlocks;

  public LevelTranslator() {
    createdBlocks = new ArrayList<>();
  }

  // generates the blocks from the text file
  public ArrayList<Block> generateBlocksFromFile(String fileName)
      throws FileNotFoundException {

    createdBlocks.clear();

    File levelFile = new File("src/main/resources/" + fileName);
    Scanner scanner = new Scanner(levelFile);

    createdBlocks = new ArrayList<>();

    int row = 0;
    // loop through each row
    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();
      String[] blocks = line.split(" ");

      //loop throw each value (col) in the row
      for (int col = 0; col < blocks.length; col++) {
        String block = blocks[col];
        if (block.equals("x")) {
          continue;
        }

        // get the block position
        double xPosition = col * (Block.width + BLOCK_X_SPACING);
        double yPosition = row * (Block.height + BLOCK_Y_SPACING);

        Block newBlock;
        if (block.equals("-1")) {
          newBlock = new Block(xPosition, yPosition, false, true, -1);
        } else {
          newBlock = new Block(xPosition, yPosition, false, false, Integer.parseInt(block));
        }

        createdBlocks.add(newBlock);
      }
      row++;
    }

    scanner.close();
    return createdBlocks;
  }
}
