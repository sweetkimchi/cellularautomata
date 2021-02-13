package cellsociety.model.rules;

import cellsociety.model.cell.State;
import java.util.ArrayList;

/**
 *
 */
public abstract class Rules {

  private String ALIVE_COLOR;
  private String DEAD_COLOR;
  /**
   * Default constructor
   */
  public Rules() {
  }


  protected int[][] numberOfAliveNeighbors(State[][] statesOfAllCells, String type) {
    int[][] numberOfNeighbors = new int[statesOfAllCells.length][statesOfAllCells[0].length];
    for (int x = 0; x < statesOfAllCells.length; x++) {
      for (int y = 0; y < statesOfAllCells[0].length; y++) {
        int numberOfNeighbor = 0;
        if (x - 1 >= 0 && y - 1 >= 0 && statesOfAllCells[x - 1][y - 1].type.equals(type)) {
          numberOfNeighbor++;
        }
        if (x - 1 >= 0 && y >= 0 && statesOfAllCells[x - 1][y].type.equals(type)) {
          numberOfNeighbor++;
        }
        if (x - 1 >= 0 && y + 1 < statesOfAllCells[0].length && statesOfAllCells[x - 1][y
            + 1].type.equals(type)) {
          numberOfNeighbor++;
        }
        if (y - 1 >= 0 && statesOfAllCells[x][y - 1].type.equals(type)) {
          numberOfNeighbor++;
        }
        if (y + 1 < statesOfAllCells[0].length && statesOfAllCells[x][y + 1].type.equals(type)) {
          numberOfNeighbor++;
        }
        if (x + 1 < statesOfAllCells.length && y - 1 >= 0 && statesOfAllCells[x + 1][y - 1].type.equals(type)) {
          numberOfNeighbor++;
        }
        if (x + 1 < statesOfAllCells.length && y >= 0 && statesOfAllCells[x + 1][y].type.equals(type)) {
          numberOfNeighbor++;
        }
        if (x + 1 < statesOfAllCells.length && y + 1 < statesOfAllCells[0].length
            && statesOfAllCells[x + 1][y + 1].type.equals(type)) {
          numberOfNeighbor++;
        }
        //     System.out.print(" " + numberOfNeighbor + " ");
        numberOfNeighbors[x][y] = numberOfNeighbor;
        //    statesOfAllCells[x][y].alive = decideState(numberOfNeighbor);
      }
      // System.out.println();
    }
    //  System.out.println();
    return numberOfNeighbors;
  }

  /**
   * judges the state of each cell using the rule of the specific model class
   * @param statesOfAllCells starting states of all cells
   * @return updated states of all cells
   */
  public abstract State[][] judgeStateOfEachCell(State[][] statesOfAllCells);

  /**
   * specifices the starting states of the cells according to the simulation rule
   * @return type of cells
   */
  public abstract String getStartingPositionCellType();

  public abstract ArrayList<String> getPossibleTypes();

}