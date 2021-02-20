package cellsociety.model.gameoflife;

import cellsociety.controller.grid.GridManager;
import cellsociety.model.rules.Rules;
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
  private final String ALIVE_COLOR = "black";
  private final String DEAD_COLOR = "lightgrey";
  private final ArrayList<String> possibleTypes;
  private ArrayList<String> possibleColors;

  /**
   * Default constructor for GameOfLifeRule
   */
  public GameOfLifeRule() {
    /*
    Each cell with one or no neighbors dies, as if by solitude.
    Each cell with four or more neighbors dies, as if by overpopulation.
    Each cell with two or three neighbors survives.
     */
    lowerSurvivalBoundary = 2;
    upperSurvivalBoundary = 3;
    possibleTypes = new ArrayList<>();
    possibleColors = new ArrayList<>();
    possibleColors.add(DEAD_COLOR);
    possibleColors.add(ALIVE_COLOR);
    possibleTypes.add(EMPTY);
    possibleTypes.add(ALIVE);
  }

  protected String determineWhetherAliveOrDead(int numberOfNeighbor, String type) {
    if (type.equals(ALIVE)) {
      if (numberOfNeighbor < lowerSurvivalBoundary) {
        return EMPTY;
      } else if (numberOfNeighbor <= upperSurvivalBoundary) {
        return ALIVE;
      }
    } else if (numberOfNeighbor == 3) {
      return ALIVE;
    }
    return EMPTY;
  }

  /**
   * specifies the starting states of the cells according to the simulation rule
   *
   * @return type of cells
   */
  public String getStartingPositionCellType() {
    return ALIVE;
  }

  /**
   * returns the possible types (e.g. agent x, agent y, empty)
   *
   * @return arraylist of possible types
   */
  @Override
  public ArrayList<String> getPossibleTypes() {
    return possibleTypes;
  }

  /**
   * Returns the possible colors for each type
   *
   * @return arraylist of colors
   */
  @Override
  public ArrayList<String> getPossibleColors() {
    return possibleColors;
  }

  @Override
  public void decideState(List<Integer> neighborsOfEachTypeAtCoordinate, List<int[][]> nextStates,
      int x, int y, GridManager gridManager) {
    gridManager.getStateAtCoordinate(x, y)
        .setType(determineWhetherAliveOrDead(neighborsOfEachTypeAtCoordinate.get(1),
            gridManager.getTypeAtCoordinate(x, y)));
    if (gridManager.getTypeAtCoordinate(x, y).equals(ALIVE)) {
      gridManager.getStateAtCoordinate(x, y).setColor(ALIVE_COLOR);
    } else {
      gridManager.getStateAtCoordinate(x, y).setColor(DEAD_COLOR);
    }
  }
}