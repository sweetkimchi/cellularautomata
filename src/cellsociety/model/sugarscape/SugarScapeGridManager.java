package cellsociety.model.sugarscape;

import cellsociety.controller.grid.GridManager;
import cellsociety.model.cell.State;
import java.util.ArrayList;
import java.util.Random;

public class SugarScapeGridManager extends GridManager {

  private int row;
  private int col;
  /**
   * Basic constructor
   *
   * @param row
   * @param col
   */
  public SugarScapeGridManager(int row, int col) {
    super(row, col);
    this.row = row;
    this.col = col;
  }

  public void makeSugarScapeGridWithRandomSeed(double emptyRatio, double patchRatio, int randomSeed, ArrayList<String> possibleTypes, ArrayList<String> possibleColors) {
    Random random = new Random(randomSeed);
    State[][] stateOfCells = new State[row][col];
    for (int x = 0; x < row; x++) {
      for (int y = 0; y < col; y++) {
        double probability = random.nextDouble();
        //      System.out.println(probability);
        if (probability < emptyRatio) {
          State state = new State(x, y, possibleTypes.get(0), possibleColors.get(0), 0);
          stateOfCells[x][y] = state;
        } else if (probability < emptyRatio + (1 - emptyRatio) * populationRatio) {
          State state = new State(x, y, possibleTypes.get(1), possibleColors.get(1), 0);
          stateOfCells[x][y] = state;
        } else {
          int index = random.nextInt(possibleTypes.size() - 2) + 2;
          State state = new State(x, y, possibleTypes.get(index), possibleColors.get(index), 0);
          stateOfCells[x][y] = state;
        }
      }
    }
    this.stateOfCells = stateOfCells;

  }
}
