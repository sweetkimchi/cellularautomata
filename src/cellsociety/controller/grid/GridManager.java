package cellsociety.controller.grid;

import cellsociety.model.cell.State;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class GridManager {

  private final int row;
  private final int col;
  private final List<List<State>> grid;
  private State[][] stateOfCells;
  private final String EMPTY = "empty";
  private final String ALIVE = "alive";

  /**
   * @param row
   * @param col
   */
  public GridManager(int row, int col) {
    this.row = row;
    this.col = col;
    grid = new ArrayList<>();
  }

  public State[][] buildGrid(ArrayList<State> template, String type) {
    State[][] stateOfCells = new State[row][col];
    for (int r = 0; r < row; r++) {
      for (int c = 0; c < col; c++) {
        State state = new State(r, c, EMPTY);
        stateOfCells[r][c] = state;
      }
    }

    int xSize = 0;
    int ySize = 0;

    for (State s : template) {
      if (xSize < s.getxCoord()) {
        xSize = s.getxCoord();
      }
      if (ySize < s.getyCoord()) {
        ySize = s.getyCoord();
      }
    }

    for (State s : template) {
      stateOfCells[row / 2 + s.getxCoord() - xSize / 2][col / 2 + s.getyCoord()
          - ySize / 2].type = type;
    }
    this.stateOfCells = stateOfCells;
    return stateOfCells;
  }

  public State[][] getStateOfCells() {
    return stateOfCells;
  }

  public void printGrid() {
    for (int x = 0; x < row; x++) {
      for (int y = 0; y < col; y++) {
        if (stateOfCells[x][y].type.equals(ALIVE)) {
          //           System.out.print(" O ");
        } else {
          //            System.out.print(" . ");
        }
      }
      //         System.out.println();
    }
    //     System.out.println();
  }

  public void updateGrid(State[][] stateOfCells) {
    this.stateOfCells = stateOfCells;
  }


  public List<List<State>> getGrid() {
    return grid;
  }


}