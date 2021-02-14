package cellsociety.controller.grid;

import cellsociety.model.cell.State;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

  public State[][] buildGridWithRandomSeed(double emptyRatio, double populationRatio, int seed, ArrayList<String> possibleTypes, ArrayList<String> possibleColors){
    System.out.println("FEAWFEW" + emptyRatio);
    System.out.println("????" + (emptyRatio + (1-emptyRatio) * populationRatio));
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

  public State[][] getStateOfCells() {
    return stateOfCells;
  }

  public void printGrid(State[][] stateOfCells) {
    for (int x = 0; x < row; x++) {
      for (int y = 0; y < col; y++) {
        if (stateOfCells[x][y].type.equals("shark")) {
                     System.out.print(" S ");
        } else if(stateOfCells[x][y].type.equals("fish")){
                      System.out.print(" F ");
        }
        else{
          System.out.print(" E ");
        }
      }
              System.out.println();
    }
        System.out.println();
  }

  public void updateGrid(State[][] stateOfCells) {
    this.stateOfCells = stateOfCells;
  }


  public List<List<State>> getGrid() {
    return grid;
  }


}