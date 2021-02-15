package cellsociety.controller.grid;

import cellsociety.model.cell.State;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Builds and manages the grid for all models. Depends on the simulation engine to initiate the process. Depends on the models to supply the correct parameters. Assumes that all parameters are correct.
 * @author Ji Yun Hyo
 */
public class GridManager {

  private final int row;
  private final int col;
  private final List<List<State>> grid;
  private State[][] stateOfCells;
  private final String EMPTY = "empty";
  private final String ALIVE = "alive";

  /**
   * Basic constructor
   * @param row
   * @param col
   */
  public GridManager(int row, int col) {
    this.row = row;
    this.col = col;
    grid = new ArrayList<>();
  }

  /**
   * Builds the initial stage with specific coordinates that are supplied to the GridManager
   * @param template coordinates of starting states
   * @param type type of player occupying the starting states
   * @return updated grid that contains the starting states of the model
   */
  public State[][] buildGridWithTemplate(ArrayList<State> template, String type) {
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
      stateOfCells[s.getxCoord()][s.getyCoord()].type = type;
    }
    this.stateOfCells = stateOfCells;
    return stateOfCells;
  }

  /**
   * Builds the starting states for models that require adjustments based on the parameters.
   * @param emptyRatio ratio of empty cells to occupied cells
   * @param populationRatio ratio between different types of players
   * @param seed random seed to reproduce results
   * @param possibleTypes names of all players of a model
   * @param possibleColors colors of all types
   * @return the starting states of all cells
   */
  public State[][] buildGridWithRandomSeed(double emptyRatio, double populationRatio, int seed, ArrayList<String> possibleTypes, ArrayList<String> possibleColors){
//    System.out.println("FEAWFEW" + emptyRatio);
//    System.out.println("????" + (emptyRatio + (1-emptyRatio) * populationRatio));
    Random random = new Random(seed);
    State[][] stateOfCells = new State[row][col];
    for (int r = 0; r < row; r++) {
      for (int c = 0; c < col; c++) {
        double probability = random.nextDouble();
  //      System.out.println(probability);
        if(probability < emptyRatio){
          State state = new State(r, c, possibleTypes.get(0), possibleColors.get(0), 0);
          stateOfCells[r][c] = state;
        }else if(probability < emptyRatio + (1-emptyRatio) * populationRatio){
          State state = new State(r, c, possibleTypes.get(1), possibleColors.get(1), 0);
          stateOfCells[r][c] = state;
        }else{
          State state = new State(r, c, possibleTypes.get(2), possibleColors.get(2), 0);
          stateOfCells[r][c] = state;
        }
      }
    }
    this.stateOfCells = stateOfCells;
    return stateOfCells;
  }

  /**
   * Stores the grid for future use. It is not really used but GridManager does want to keep a record of the current state of all cells.
   * @param stateOfCells the updated cells
   */
  public void updateGrid(State[][] stateOfCells) {
    this.stateOfCells = stateOfCells;
  }


}