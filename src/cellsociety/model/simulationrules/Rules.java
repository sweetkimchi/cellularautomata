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
   * @param neighborsOfEachTypeAtTheCurrentLocation counts the number of neighbors that are specific "type" of
   *                                                neighbors at the specific x, y coordinate location for all
   *                                                possible types of the simulation.
   * @param markStateForFurtherAnalysis this list of integer array acts as the means for each rules class
   *                                    to keep track of each possible type of state. List contains one integer
   *                                    array per possible type of state.
   * @param updateStates List of states contains all the states that are to be updated at the end of each iteration.
   *                     UpdateStates is sent to each Rules class where rules are applied. If a state has to be
   *                     updated, the rules class adds the state to updateStates.
   * @param x x coordinate
   * @param y y coordinate
   * @param gridManager gridManager object needed to control and oversee state checking
   */
  public abstract void decideState(List<Integer> neighborsOfEachTypeAtTheCurrentLocation,
      List<int[][]> markStateForFurtherAnalysis, List<State> updateStates,
      int x, int y, GridManager gridManager);
}