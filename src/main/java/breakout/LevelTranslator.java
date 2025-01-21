package breakout;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.scene.Group;

public class LevelTranslator {
  private ArrayList<Block> createdBlocks;
  private static final int BLOCK_X_SPACING = 10;
  private static final int BLOCK_Y_SPACING = 50;
  private static final int SIZE = 600;

  public LevelTranslator() {
    createdBlocks = new ArrayList<>();
  }

  // generates the blocks from the text file

  /**
   *
   * @param fileName - the new filename
   * @return - the arrayList of blocks that is created from the text files
   * @throws FileNotFoundException
   */
  public ArrayList<Block> generateBlocksFromFile(String fileName)
      throws FileNotFoundException {

    createdBlocks.clear();

    File levelFile = new File("src/main/resources/" + fileName);
    Scanner scanner = new Scanner(levelFile);

    createdBlocks = new ArrayList<>();

    int row = 0;
    int totalCols = 0;

    // get the maximum number of cols
    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();
      totalCols = Math.max(totalCols, line.split(" ").length);
    }
    scanner.close();
    scanner = new Scanner(levelFile);

    //logic adapted from Main for dynamic block spacing
    double totalRowWidth = totalCols * Block.width + (totalCols - 1) * BLOCK_X_SPACING;
    double startX = (SIZE - totalRowWidth) / 2;

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
        double xPosition = startX + col * (Block.width + BLOCK_X_SPACING);
        double yPosition = BLOCK_Y_SPACING + row * (Block.height + BLOCK_Y_SPACING);

        Block newBlock;
        if (block.equals("-1")) {
          newBlock = new Block(xPosition, yPosition, false, true, -1);
        } else if (block.equals("5")) {
          newBlock = new Block(xPosition, yPosition, true, false, 1);
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
