package cellsociety.model;

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
  private ArrayList<String> possibleColors;

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

  @Override
  public void decideState(List<Integer> neighborsOfEachTypeAtCoordinate, List<int[][]> nextStates,
      List<State> updateStates, int x, int y,
      GridManager gridManager) {
    if (gridManager.getTypeAtCoordinate(x, y).equals(EMPTY)
        && neighborsOfEachTypeAtCoordinate.get(1) == 3) {
      updateStates.add(new State(x, y, ALIVE, ALIVE_COLOR, 0));
    }
    if (gridManager.getTypeAtCoordinate(x, y).equals(ALIVE)
        && neighborsOfEachTypeAtCoordinate.get(1) < lowerSurvivalBoundary
        || neighborsOfEachTypeAtCoordinate.get(1) > upperSurvivalBoundary) {
      updateStates.add(new State(x, y, EMPTY, DEAD_COLOR, 0));
    }
  }
}