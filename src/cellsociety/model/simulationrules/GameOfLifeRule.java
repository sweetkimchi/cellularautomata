package cellsociety.model.simulationrules;

import cellsociety.model.GridManager;
import cellsociety.model.State;
import java.util.ArrayList;
import java.util.List;

/**
 * Purpose: This class contains the rules for the Game of Life model. Rules include the types of the
 * players as well as logic to update each cell. Assumptions: xml file is correctly formatted and
 * supplies the correct information to the constructor. Dependencies: Depends on SimulationEngine to
 * declare constructors based on the parameters read from XML files. Depends on GridManager to
 * provide it with the grid to work with.
 *
 * @author Ji Yun Hyo
 */
public class GameOfLifeRule extends Rules {

  /*
  1. Case 1: State.alive = false when numberOfNeighbors <= lowerSurvivalBoundary
  2. Case 2: State.alive = True when lowerSurvivalBoundary < numberOfNeighbors <= numberOfNeighbors
  3. Case 3: State.alive = false when upperSurvivalBoundary < numberOfNeighbors
   */
  private final int lowerSurvivalBoundary;
  private final int upperSurvivalBoundary;
  private final String ALIVE = "alive";
  private final String EMPTY = "empty";
  private final String ALIVE_COLOR;
  private final String DEAD_COLOR;
  private final ArrayList<String> possibleTypes;
  private final ArrayList<String> possibleColors;

  /**
   * Default constructor for GameOfLifeRule
   */
  public GameOfLifeRule(String aliveColor, String deadColor) {
    /*
    Each cell with one or no neighbors dies, as if by solitude.
    Each cell with four or more neighbors dies, as if by overpopulation.
    Each cell with two or three neighbors survives.
     */
    lowerSurvivalBoundary = 2;
    upperSurvivalBoundary = 3;
    possibleTypes = new ArrayList<>();
    possibleColors = new ArrayList<>();
    ALIVE_COLOR = aliveColor;
    DEAD_COLOR = deadColor;
    possibleColors.add(DEAD_COLOR);
    possibleColors.add(ALIVE_COLOR);
    possibleTypes.add(EMPTY);
    possibleTypes.add(ALIVE);
  }

  /**
   * returns the possible types (e.g. agent x, agent y, empty)
   *
   * @return arraylist of possible types
   */
  @Override
  public List<String> getPossibleTypes() {
    return possibleTypes;
  }

  /**
   * Returns the possible colors for each type
   *
   * @return arraylist of colors
   */
  @Override
  public List<String> getPossibleColors() {
    return possibleColors;
  }

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
  @Override
  public void decideState(List<Integer> neighborsOfEachTypeAtTheCurrentLocation, List<int[][]> markStateForFurtherAnalysis,
      List<State> updateStates, int x, int y,
      GridManager gridManager) {
    if (gridManager.getTypeAtCoordinate(x, y).equals(EMPTY)
        && neighborsOfEachTypeAtTheCurrentLocation.get(1) == 3) {
      updateStates.add(new State(x, y, ALIVE, ALIVE_COLOR, 0));
    }
    if (gridManager.getTypeAtCoordinate(x, y).equals(ALIVE)
        && neighborsOfEachTypeAtTheCurrentLocation.get(1) < lowerSurvivalBoundary
        || neighborsOfEachTypeAtTheCurrentLocation.get(1) > upperSurvivalBoundary) {
      updateStates.add(new State(x, y, EMPTY, DEAD_COLOR, 0));
    }
  }
}