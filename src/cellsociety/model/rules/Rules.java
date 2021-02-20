package cellsociety.model.rules;

import cellsociety.model.cell.State;
import java.util.ArrayList;

/**
 * Abstract class to provide the most basic tools for implementing rules
 *
 * @author Ji Yun Hyo
 */
public abstract class Rules {

  private String ALIVE_COLOR;
  private String DEAD_COLOR;

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

  private void checkEightSidesForNumberOfNeighbors(State[][] statesOfAllCells, String type, int[][] numberOfNeighbors, int x,
      int y) {
    int numberOfNeighbor = 0;
    if (x - 1 >= 0 && y - 1 >= 0 && statesOfAllCells[x - 1][y - 1].getType().equals(type)) {
      numberOfNeighbor++;
    }
    if (x - 1 >= 0 && y >= 0 && statesOfAllCells[x - 1][y].getType().equals(type)) {
      numberOfNeighbor++;
    }
    if (x - 1 >= 0 && y + 1 < statesOfAllCells[0].length && statesOfAllCells[x - 1][y
        + 1].getType().equals(type)) {
      numberOfNeighbor++;
    }
    if (y - 1 >= 0 && statesOfAllCells[x][y - 1].getType().equals(type)) {
      numberOfNeighbor++;
    }
    if (y + 1 < statesOfAllCells[0].length && statesOfAllCells[x][y + 1].getType().equals(type)) {
      numberOfNeighbor++;
    }
    if (x + 1 < statesOfAllCells.length && y - 1 >= 0 && statesOfAllCells[x + 1][y - 1].getType()
        .equals(type)) {
      numberOfNeighbor++;
    }
    if (x + 1 < statesOfAllCells.length && y >= 0 && statesOfAllCells[x + 1][y].getType()
        .equals(type)) {
      numberOfNeighbor++;
    }
    if (x + 1 < statesOfAllCells.length && y + 1 < statesOfAllCells[0].length
        && statesOfAllCells[x + 1][y + 1].getType().equals(type)) {
      numberOfNeighbor++;
    }
    //     System.out.print(" " + numberOfNeighbor + " ");
    numberOfNeighbors[x][y] = numberOfNeighbor;
    //    statesOfAllCells[x][y].alive = decideState(numberOfNeighbor);
  }

  /**
   * judges the state of each cell using the rule of the specific model class
   *
   * @param statesOfAllCells starting states of all cells
   * @return updated states of all cells
   */
  public abstract State[][] judgeStateOfEachCell(State[][] statesOfAllCells);

  /**
   * specifices the starting states of the cells according to the simulation rule
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