package cellsociety.model.gameoflife;

import cellsociety.model.cell.State;
import cellsociety.model.rules.Rules;
import java.util.ArrayList;

/**
 *
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
   * Default Constructor
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

  public State[][] judgeStateOfEachCell(State[][] statesOfAllCells) {
    int[][] numberOfAliveNeighbors = numberOfAliveNeighbors(statesOfAllCells, ALIVE);
    for (int x = 0; x < statesOfAllCells.length; x++) {
      for (int y = 0; y < statesOfAllCells[0].length; y++) {
        statesOfAllCells[x][y].type = decideState(numberOfAliveNeighbors[x][y],
            statesOfAllCells[x][y].type);
        if (statesOfAllCells[x][y].type.equals(ALIVE)) {
          statesOfAllCells[x][y].setColor(ALIVE_COLOR);
        } else {
          statesOfAllCells[x][y].setColor(DEAD_COLOR);
        }
      }
    }
    return statesOfAllCells;
  }


  protected String decideState(int numberOfNeighbor, String type) {
    if (type.equals(ALIVE)) {
      if (numberOfNeighbor < lowerSurvivalBoundary) {
        return EMPTY;
      } else if(numberOfNeighbor <= upperSurvivalBoundary){
        return ALIVE;
      }
    } else if(numberOfNeighbor == 3){
      return ALIVE;
    }
    return "";
  }

  public String getStartingPositionCellType(){
    return ALIVE;
  }

  @Override
  public ArrayList<String> getPossibleTypes() {
    return null;
  }
}