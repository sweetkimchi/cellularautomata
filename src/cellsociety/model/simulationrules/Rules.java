package cellsociety.model.simulationrules;

import cellsociety.model.GridManager;
import cellsociety.model.State;
import java.util.List;

/**
 * Abstract class to provide the most basic tools for implementing rules
 * The purpose of this class is to provide the basis for building a new Rules class for each
 * additional model. It depends on State and GridManager classes. Example usage is to extend the abstract
 * class and implement the abstract methods.
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

  /**
   * Decide state updates each cell at each specific coordinate location according to the rules.
   * Assumption is that all the parameters are non-null values that are properly defined.
   * @param neighborsOfEachTypeAtTheCurrentLocation
   * @param nextStates
   * @param updateStates
   * @param x
   * @param y
   * @param gridManager
   */
  public abstract void decideState(List<Integer> neighborsOfEachTypeAtTheCurrentLocation,
      List<int[][]> nextStates, List<State> updateStates,
      int x, int y, GridManager gridManager);
}