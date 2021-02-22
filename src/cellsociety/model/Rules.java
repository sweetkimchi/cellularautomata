package cellsociety.model;

import cellsociety.model.GridManager;
import cellsociety.model.State;
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
   * returns the possible types (e.g. agent x, agent y, empty)
   *
   * @return arraylist of possible types
   */
  public abstract List<String> getPossibleTypes();

  /**
   * Returns the possible colors for each type
   *
   * @return arraylist of colors
   */
  public abstract List<String> getPossibleColors();

  public abstract void decideState(List<Integer> neighborsOfEachTypeAtCoordinate,
      List<int[][]> nextStates, List<State> updateStates,
      int x, int y, GridManager gridManager);
}