package cellsociety.model.gameoflife;

import cellsociety.model.cell.State;
import cellsociety.model.rules.Rules;

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

  /**
   * Default constructor
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
    int[][] numberOfAliveNeighbors = numberOfAliveNeighbors(statesOfAllCells);
    for (int x = 0; x < statesOfAllCells.length; x++) {
      for (int y = 0; y < statesOfAllCells[0].length; y++) {
        statesOfAllCells[x][y].alive = decideState(numberOfAliveNeighbors[x][y],
            statesOfAllCells[x][y].alive);
        if(statesOfAllCells[x][y].alive){
          statesOfAllCells[x][y].setColor("yellow");
        }else{
          statesOfAllCells[x][y].setColor("black");
        }
      }
    }
    return statesOfAllCells;
  }


  private boolean decideState(int numberOfNeighbor, boolean alive) {
    if (alive) {
      if (numberOfNeighbor < lowerSurvivalBoundary) {
        return false;
      } else
          return numberOfNeighbor <= upperSurvivalBoundary;
    } else {
        return numberOfNeighbor == 3;
    }

  }
}