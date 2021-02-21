package cellsociety.model.foragingants;

import cellsociety.controller.grid.GridManager;
import cellsociety.model.cell.State;
import java.util.ArrayList;

public class ForagingAntGridManager extends GridManager {
  private int row;
  private int col;
  private State[][] stateOfCells;
  /**
   * Basic constructor
   *
   * @param row
   * @param col
   */
  public ForagingAntGridManager(int row, int col) {
    super(row, col);
    this.row = row;
    this.col = col;
  }

  public State[][] buildAntGridWithTemplateHelper(ArrayList<String> coordinates, ArrayList<String> possibleTypes, ArrayList<String> possibleColors, int radius) {
    State[][] stateOfCells = new State[row][col];

    for (int r = 0; r < row; r++) {
      for (int c = 0; c < col; c++) {
        State state = new AntState(r, c, possibleTypes.get(possibleTypes.size()-1), possibleColors.get(possibleTypes.size()-1), 0);
        stateOfCells[r][c] = state;
      }
    }

    //build nest
    for(int x = Integer.parseInt(coordinates.get(0)) - radius; x < Integer.parseInt(coordinates.get(0)) + radius; x++){
      for(int y = Integer.parseInt(coordinates.get(1)) - radius; y < Integer.parseInt(coordinates.get(1)) + radius; y++){
        stateOfCells[x][y] = new AntState(x,y,possibleTypes.get(0), possibleColors.get(0),0, 5);
      }
    }

    for(int index = 2; index < coordinates.size(); index+=2) {
      for(int x = Integer.parseInt(coordinates.get(index)) - radius; x < Integer.parseInt(coordinates.get(index)) + radius; x++){
        for(int y = Integer.parseInt(coordinates.get(index)) - radius; y < Integer.parseInt(coordinates.get(index)) + radius; y++){
          stateOfCells[x][y] = new AntState(x,y,possibleTypes.get(1), possibleColors.get(1),5);
        }
      }
    }

    return stateOfCells;
  }
}
