package cellsociety.model.rules;

import cellsociety.model.cell.State;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class to provide the most basic tools for implementing rules
 *
 * @author Ji Yun Hyo
 */
public abstract class Rules {

  /**
   * Default constructor
   */
  public Rules() {
  }


  /**
   * Calculates the number of neighbors with a specific type
   *
   * @param statesOfAllCells states of all cells
   * @param type             type of state looking for
   * @return integer array with number of neighbors that were equal to 'type'
   */
  public int[][] numberOfAliveNeighbors(State[][] statesOfAllCells, String type) {
    int[][] numberOfNeighbors = new int[statesOfAllCells.length][statesOfAllCells[0].length];
    for (int x = 0; x < statesOfAllCells.length; x++) {
      for (int y = 0; y < statesOfAllCells[0].length; y++) {
        checkEightSidesForNumberOfNeighbors(statesOfAllCells, type, numberOfNeighbors, x, y);
      }
      // System.out.println();
    }
    //  System.out.println();
    return numberOfNeighbors;
  }

  private void checkEightSidesForNumberOfNeighbors(State[][] statesOfAllCells, String type,
      int[][] numberOfNeighbors, int x,
      int y) {
    int numberOfNeighbor = 0;
    for (int xCoord = x - 1; xCoord <= x + 1; xCoord++) {
      for (int yCoord = y - 1; yCoord <= y + 1; yCoord++) {
        if (xCoord >= 0 && yCoord >= 0 && xCoord < statesOfAllCells.length
            && yCoord < statesOfAllCells[0].length && statesOfAllCells[xCoord][yCoord].getType()
            .equals(type) && !(xCoord == x && yCoord == y)) {
          numberOfNeighbor++;
        }
      }
    }
    numberOfNeighbors[x][y] = numberOfNeighbor;
  }

  /**
   * judges the state of each cell using the rule of the specific model class
   *
   * @param statesOfAllCells starting states of all cells
   * @return updated states of all cells
   */
  public abstract State[][] judgeStateOfEachCell(State[][] statesOfAllCells, List<int[][]> numberOfNeighborsForEachType);

  /**
   * specifies the starting states of the cells according to the simulation rule
   *
   * @return type of cells
   */
  public abstract String getStartingPositionCellType();

  /**
   * returns the possible types (e.g. agent x, agent y, empty)
   *
   * @return arraylist of possible types
   */
  public abstract ArrayList<String> getPossibleTypes();

  /**
   * Returns the possible colors for each type
   *
   * @return arraylist of colors
   */
  public abstract ArrayList<String> getPossibleColors();
}