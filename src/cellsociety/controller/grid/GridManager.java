package cellsociety.controller.grid;

import cellsociety.model.cell.State;
import cellsociety.model.rules.Rules;
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
      stateOfCells[s.getxCoord()][s.getyCoord()].setType(type);
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


  public List<int[][]> getNumberOfNeighborsForEachType(ArrayList<String> possibleTypes) {
    ArrayList<int[][]> numberOfNeighborsForEachType = new ArrayList<>();
    for(String type: possibleTypes){
      numberOfNeighborsForEachType.add(numberOfAliveNeighbors(stateOfCells, type));
    }
    return numberOfNeighborsForEachType;
  }

  private int[][] numberOfAliveNeighbors(State[][] statesOfAllCells, String type) {
    int[][] numberOfNeighbors = new int[statesOfAllCells.length][statesOfAllCells[0].length];
    for (int x = 0; x < statesOfAllCells.length; x++) {
      for (int y = 0; y < statesOfAllCells[0].length; y++) {
        checkEightSidesForNumberOfNeighbors(statesOfAllCells, type, numberOfNeighbors, x, y);
      }
      // System.out.println();
    }
    //  System.out.println();
    return numberOfNeighbors;
  }

  private void checkEightSidesForNumberOfNeighbors(State[][] statesOfAllCells, String type,
      int[][] numberOfNeighbors, int x,
      int y) {
    int numberOfNeighbor = 0;
    for (int xCoord = x - 1; xCoord <= x + 1; xCoord++) {
      for (int yCoord = y - 1; yCoord <= y + 1; yCoord++) {
        if (xCoord >= 0 && yCoord >= 0 && xCoord < statesOfAllCells.length
            && yCoord < statesOfAllCells[0].length && statesOfAllCells[xCoord][yCoord].getType()
            .equals(type) && !(xCoord == x && yCoord == y)) {
          numberOfNeighbor++;
        }
      }
    }
    numberOfNeighbors[x][y] = numberOfNeighbor;
  }

  private void printGrid(State[][] stateOfCells) {
    for (int x = 0; x < stateOfCells.length; x++) {
      for (int y = 0; y < stateOfCells[0].length; y++) {
        System.out.print(" " + stateOfCells[y][x].getType() + " ");
      }
      System.out.println();
    }
    System.out.println();
  }

  public void judgeStateOfEachCell(Rules rules) {
    List<int[][]> numberOfNeighborsForEachType = getNumberOfNeighborsForEachType(rules.getPossibleTypes());
    List<int[][]> nextStates = nextStatesOfCells(numberOfNeighborsForEachType);

    for(int x = 0; x < row; x++){
      for(int y = 0; y < col; y++){
        List<Integer> neighborsOfEachTypeAtCoordinate = new ArrayList<>();
        System.out.print(" " + stateOfCells[x][y].getType() + " ");

        for(int index = 0; index < numberOfNeighborsForEachType.size(); index++){
          neighborsOfEachTypeAtCoordinate.add(numberOfNeighborsForEachType.get(index)[x][y]);
        }
        rules.decideState(neighborsOfEachTypeAtCoordinate, nextStates, x, y, this);
      }
      System.out.println();
    }
    updateStatesForAllCells(nextStates, rules.getPossibleTypes(), rules.getPossibleColors());
  }

  private void updateStatesForAllCells(List<int[][]> nextStates,
      ArrayList<String> possibleTypes, ArrayList<String> possibleColors) {
    for(int index = 0; index < nextStates.size(); index++) {
      for (int r = 0; r < row; r++) {
        for (int c = 0; c < col; c++) {
          if (nextStates.get(index)[r][c] == 1) {
            stateOfCells[r][c] = new State(r, c, possibleTypes.get(index), possibleColors.get(index), 0);
          }
        }
      }
    }
  }

  private List<int[][]> nextStatesOfCells(List<int[][]> numberOfNeighborsForEachType) {
    List<int[][]> nextStatesForEachType = new ArrayList<>();
    for(int[][] types : numberOfNeighborsForEachType){
      nextStatesForEachType.add(numberOfAliveNeighbors(stateOfCells, ""));
    }
    return nextStatesForEachType;
  }

  public String getTypeAtCoordinate(int x, int y) {
    return stateOfCells[x][y].getType();
  }

  public int getRow() {
    return row;
  }

  public int getColumn() {
    return col;
  }

  public State[][] getGrid(){
    return stateOfCells;
  }

  public State getStateAtCoordinate(int x, int y) {
    return stateOfCells[x][y];
  }

  public void setStateAtCoordinate(int x, int y, State state) {
    stateOfCells[x][y] = state;
  }
}