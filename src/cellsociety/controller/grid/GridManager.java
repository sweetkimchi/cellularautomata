package cellsociety.controller.grid;

import cellsociety.model.cell.State;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class GridManager {

  private static final int ROWSIZE = 100;
  private static final int COLUMNSIZE = 100;
  /**
   *
   */
  private State[][] stateOfCells;
  private final int row;
  private final int col;
  private final List<List<State>> grid;

  /**
   * Default constructor
   */
  public GridManager(int row, int col) {
    this.row = row;
    this.col = col;
    grid = new ArrayList<>();
  }

  public State[][] buildGrid(ArrayList<State> template) {
    State[][] stateOfCells = new State[row][col];
    for (int r = 0; r < row; r++) {
      for (int c = 0; c < col; c++) {
        State state = new State(r, c, false);
        stateOfCells[r][c] = state;
      }
    }
    for (State s : template) {
      stateOfCells[row / 2 + s.getxCoord()][col / 2 + s.getyCoord()].alive = true;
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
        if (stateOfCells[x][y].alive) {
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