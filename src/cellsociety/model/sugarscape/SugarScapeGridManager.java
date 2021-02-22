package cellsociety.model.sugarscape;

import cellsociety.controller.grid.GridManager;
import cellsociety.model.cell.State;
import java.util.ArrayList;
import java.util.Random;

public class SugarScapeGridManager extends GridManager {

  private int row;
  private int col;
  private State[][] stateOfCells;
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

  public void makeSugarScapeGridWithRandomSeed(double emptyRatio, double patchRatio, int numberOfAgents, int sugarMetabolism, int vision,
      int randomSeed, ArrayList<String> possibleTypes, ArrayList<String> possibleColors) {


  }
}
