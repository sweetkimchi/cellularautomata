package cellsociety.model.rules;

import cellsociety.controller.grid.GridManager;
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

  public abstract void decideState(List<Integer> neighborsOfEachTypeAtCoordinate,
      List<int[][]> nextStates, int x, int y, GridManager gridManager);
}