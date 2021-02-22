package cellsociety.model.simulationrules.foragingants;

import cellsociety.model.GridManager;
import cellsociety.model.State;
import java.util.List;

/**
 * Purpose of this class is to facilitate making of Ant grid. The entire purpose for this class
 * is the create and return the grid to GridManager.
 * @author Ji Yun Hyo
 */
public class ForagingAntGridManager extends GridManager {

  private final int row;
  private final int col;

  /**
   * Basic constructor including the number of sides to check for neighbors
   * @param row
   * @param col
   * @param numberOfSides number of sides to check for neighbors
   */
  public ForagingAntGridManager(int row, int col, int numberOfSides) {
    super(row, col, numberOfSides);
    this.row = row;
    this.col = col;
  }

  /**
   * Purpose of this method is to allow GridManager to call
   * @param coordinates coordinates of nests and food sources
   * @param possibleTypes types defined by the simulation
   * @param possibleColors colors defined by the simulation
   * @param radius radius of nests and food sources
   * @return starting states and the grid containing them
   */
  public State[][] buildAntGridWithTemplateHelper(List<String> coordinates,
      List<String> possibleTypes, List<String> possibleColors, int radius) {
    State[][] stateOfCells = new State[row][col];

    for (int r = 0; r < row; r++) {
      for (int c = 0; c < col; c++) {
        State state = new AntState(r, c, possibleTypes.get(possibleTypes.size() - 1),
            possibleColors.get(possibleTypes.size() - 1), 0);
        stateOfCells[r][c] = state;
      }
    }

    //build nest
    for (int x = Integer.parseInt(coordinates.get(0)) - radius;
        x < Integer.parseInt(coordinates.get(0)) + radius; x++) {
      for (int y = Integer.parseInt(coordinates.get(1)) - radius;
          y < Integer.parseInt(coordinates.get(1)) + radius; y++) {
        stateOfCells[x][y] = new AntState(x, y, possibleTypes.get(0), possibleColors.get(0), 0, 1);
      }
    }

    for (int index = 2; index < coordinates.size(); index += 2) {
      for (int x = Integer.parseInt(coordinates.get(index)) - radius;
          x < Integer.parseInt(coordinates.get(index)) + radius; x++) {
        for (int y = Integer.parseInt(coordinates.get(index + 1)) - radius;
            y < Integer.parseInt(coordinates.get(index + 1)) + radius; y++) {
          stateOfCells[x][y] = new AntState(x, y, possibleTypes.get(1), possibleColors.get(1), 2,
              1);
        }
      }
    }

    return stateOfCells;
  }
}
