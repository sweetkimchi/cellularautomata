package cellsociety.model.gameoflife;

import cellsociety.model.cell.State;
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
  }

  /**
   * judges the state of each cell using the rule of the specific model class
   *
   * @param statesOfAllCells             starting states of all cells
   * @param numberOfNeighborsForEachType
   * @return updated states of all cells
   */
  @Override
  public State[][] judgeStateOfEachCell(State[][] statesOfAllCells,
      List<int[][]> numberOfNeighborsForEachType) {
    int[][] numberOfAliveNeighbors = numberOfAliveNeighbors(statesOfAllCells, ALIVE);
    for (int x = 0; x < statesOfAllCells.length; x++) {
      for (int y = 0; y < statesOfAllCells[0].length; y++) {
        statesOfAllCells[x][y].setType(decideState(numberOfAliveNeighbors[x][y],
            statesOfAllCells[x][y].getType()));
        if (statesOfAllCells[x][y].getType().equals(ALIVE)) {
          statesOfAllCells[x][y].setColor(ALIVE_COLOR);
        } else {
          statesOfAllCells[x][y].setColor(DEAD_COLOR);
        }
      }
    }
    //printGrid(statesOfAllCells);
    return statesOfAllCells;
  }

  private void printGrid(State[][] stateOfCells) {
    for (int x = 0; x < stateOfCells.length; x++) {
      for (int y = 0; y < stateOfCells[0].length; y++) {
        if(stateOfCells[x][y].getType().equals(ALIVE)){
          System.out.print(" O ");
        }else{
          System.out.print(" X ");
        }

      }
      System.out.println();
    }
    System.out.println();
  }

  protected String decideState(int numberOfNeighbor, String type) {
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
    return null;
  }

  /**
   * Returns the possible colors for each type
   *
   * @return arraylist of colors
   */
  @Override
  public ArrayList<String> getPossibleColors() {
    return null;
  }
}