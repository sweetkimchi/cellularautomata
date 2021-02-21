package cellsociety.controller.grid;

import cellsociety.model.cell.State;
import cellsociety.model.foragingants.ForagingAntGridManager;
import cellsociety.model.rules.Rules;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Builds and manages the grid for all models. Depends on the simulation engine to initiate the
 * process. Depends on the models to supply the correct parameters. Assumes that all parameters are
 * correct.
 *
 * @author Ji Yun Hyo
 */
public class GridManager {

  private final int row;
  private final int col;
  private State[][] stateOfCells;
  private int numberOfSides = 8;
  private Map<String,Integer> summary;

  /**
   * Basic constructor
   *
   * @param row
   * @param col
   */
  public GridManager(int row, int col) {
    this.row = row;
    this.col = col;
    summary = new HashMap<>();

  }

  /**
   * Builds the initial stage with specific coordinates that are supplied to the GridManager
   *
   * @param template coordinates of starting states
   */
  public void buildGridWithTemplate(ArrayList<State> template,
      ArrayList<String> possibleTypes, ArrayList<String> possibleColors, int diameter) {
    State[][] stateOfCells = new State[row][col];
    for (int r = 0; r < row; r++) {
      for (int c = 0; c < col; c++) {
        State state = new State(r, c, possibleTypes.get(0), possibleColors.get(0), 0);
        stateOfCells[r][c] = state;
      }
    }
    System.out.println();

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
      stateOfCells[s.getxCoord()][s.getyCoord()] = new State(s.getxCoord(), s.getyCoord(), possibleTypes.get(1), possibleColors.get(1), 0);
    }
    this.stateOfCells = stateOfCells;
  }

  /**
   * Builds the starting states for models that require adjustments based on the parameters.
   *
   * @param emptyRatio      ratio of empty cells to occupied cells
   * @param populationRatio ratio between different types of players
   * @param seed            random seed to reproduce results
   * @param possibleTypes   names of all players of a model
   * @param possibleColors  colors of all types
   */
  public void buildGridWithRandomSeed(double emptyRatio, double populationRatio, int seed,
      ArrayList<String> possibleTypes, ArrayList<String> possibleColors) {
    Random random = new Random(seed);
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

  public Map<String,Integer> getSummaryOfTypes(){
    return summary;
  }

  /**
   * Stores the grid for future use. It is not really used but GridManager does want to keep a
   * record of the current state of all cells.
   *
   * @param stateOfCells the updated cells
   */
  public void updateGrid(State[][] stateOfCells) {
    this.stateOfCells = stateOfCells;
  }


  public List<int[][]> getNumberOfNeighborsForEachType(ArrayList<String> possibleTypes, int numberOfSides) {
    ArrayList<int[][]> numberOfNeighborsForEachType = new ArrayList<>();
    for (String type : possibleTypes) {
      numberOfNeighborsForEachType.add(numberOfAliveNeighbors(stateOfCells, type, numberOfSides));
    }
    return numberOfNeighborsForEachType;
  }

  private int[][] numberOfAliveNeighbors(State[][] statesOfAllCells, String type, int numberOfSides) {
    int[][] numberOfNeighbors = new int[statesOfAllCells.length][statesOfAllCells[0].length];
    for (int x = 0; x < statesOfAllCells.length; x++) {
      for (int y = 0; y < statesOfAllCells[0].length; y++) {
        checkEightSidesForNumberOfNeighbors(statesOfAllCells, type, numberOfNeighbors, x, y, numberOfSides);
      }
      // System.out.println();
    }
    //  System.out.println();
    return numberOfNeighbors;
  }

  private void checkEightSidesForNumberOfNeighbors(State[][] statesOfAllCells, String type,
      int[][] numberOfNeighbors, int x,
      int y, int numberOfSides) {
    int numberOfNeighbor = 0;
    for (int xCoord = x - 2; xCoord <= x + 2; xCoord++) {
      for (int yCoord = y - 1; yCoord <= y + 1; yCoord++) {
        if (checkNumberOfSidesToSeeWhetherToIncludeThisCell(statesOfAllCells, type, x, y, numberOfSides, xCoord, yCoord)) {
          numberOfNeighbor++;
        }
      }
    }
    numberOfNeighbors[x][y] = numberOfNeighbor;
  }

  private boolean checkNumberOfSidesToSeeWhetherToIncludeThisCell(State[][] statesOfAllCells, String type, int x, int y,
      int numberOfSides, int xCoord, int yCoord) {
    boolean count = true;
    if(xCoord >= 0 && yCoord >= 0 && xCoord < statesOfAllCells.length
        && yCoord < statesOfAllCells[0].length && statesOfAllCells[xCoord][yCoord].getType()
        .equals(type) && !(xCoord == x && yCoord == y)){
      if(numberOfSides == 8 && (xCoord != x-2 && xCoord != x+2)){
        return true;
      }
      if(numberOfSides == 6 && isHexagonalNeighborForOddYCoord(x, y, xCoord, yCoord)){
        // && !(xCoord == x - 1 && yCoord == y + 1)
        return true;
      }
      if(numberOfSides == 6 && isHexagonalNeighborForEvenYCoord(x, y, xCoord, yCoord)) {
        //&& !(xCoord == x + 1 && yCoord == y - 1) && !(xCoord == x + 1 && yCoord == y + 1)
        return true;
      }
      if(numberOfSides == 4 && isOneOfTheNWSEDirection(x, y, xCoord, yCoord)){
        return true;
      }
      if(numberOfSides == 3 && !isFacingUp(xCoord, yCoord) && isLeftRightUpNeighbors(x, y,
          xCoord, yCoord, yCoord - 1)){
        return true;
      }
      if(numberOfSides == 3 && isFacingUp(xCoord, yCoord) && isLeftRightUpNeighbors(x, y,
          xCoord, yCoord, yCoord + 1)){
        return true;
      }
      if(numberOfSides == 12 && isFacingUp(xCoord, yCoord) && isOneOfTheSidesForFacingUpTriangles(x,y,xCoord,yCoord)){
        return true;
      }
      if(numberOfSides == 12 && !isFacingUp(xCoord, yCoord) && isOneOfTheSidesForFacingDownTriangles(x,y,xCoord,yCoord)){
        return false;
      }

    }
    return false;
  }

  private boolean isOneOfTheSidesForFacingDownTriangles(int x, int y, int xCoord, int yCoord) {
    return (y == yCoord - 1 && (x <= xCoord + 2 && x >= xCoord - 2)) || (y == yCoord && (x <= xCoord + 2 && x >= xCoord - 2)) || (y == yCoord + 1 && (x <= xCoord + 1 && x >= xCoord -1));
  }

  private boolean isOneOfTheSidesForFacingUpTriangles(int x, int y, int xCoord, int yCoord) {
    return (y == yCoord - 1 && (x <= xCoord + 1 && x >= xCoord - 1)) || (y == yCoord && (x <= xCoord + 2 && x >= xCoord - 2)) || (y == yCoord + 1 && (x <= xCoord + 2 && x >= xCoord -2));
  }

  private boolean isFacingUp(int xCoord, int yCoord){
    return (xCoord % 2 == 0 && yCoord % 2 == 0) || (yCoord % 2 != 0 && xCoord % 2 != 0);
  }

  private boolean isLeftRightUpNeighbors(int x, int y, int xCoord, int yCoord, int i) {
    return (x == xCoord - 1 && y == yCoord) || (x == xCoord + 1 && y == yCoord) || (x == xCoord
        && y == i);
  }

  private boolean isOneOfTheNWSEDirection(int x, int y, int xCoord, int yCoord) {
    return !(x == xCoord - 1 && y == yCoord - 1) && !(x == xCoord - 1 && y == yCoord + 1) && !(
        x == xCoord + 1 && y == yCoord - 1) && !(x == xCoord + 1 && y == yCoord + 1) && (xCoord != x-2 && xCoord != x+2);
  }

  private boolean isHexagonalNeighborForOddYCoord(int x, int y, int xCoord, int yCoord) {
    return yCoord % 2 != 0 && !(x == xCoord - 1 && y == yCoord - 1) && !(x == xCoord - 1
        && y == yCoord + 1) && (xCoord != x-2 && xCoord != x+2);
  }

  private boolean isHexagonalNeighborForEvenYCoord(int x, int y, int xCoord, int yCoord) {
    return yCoord % 2 == 0 && !(x == xCoord + 1 && y == yCoord + 1) && !(x == xCoord + 1
        && y == yCoord - 1) && (xCoord != x-2 && xCoord != x+2);
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
    List<int[][]> numberOfNeighborsForEachType = getNumberOfNeighborsForEachType(
        rules.getPossibleTypes(), numberOfSides);
    List<int[][]> nextStates = nextStatesOfCells(numberOfNeighborsForEachType);

    for (int x = 0; x < row; x++) {
      for (int y = 0; y < col; y++) {
        List<Integer> neighborsOfEachTypeAtCoordinate = new ArrayList<>();
        //      System.out.print(" " + stateOfCells[x][y].getType() + " ");

        for (int index = 0; index < numberOfNeighborsForEachType.size(); index++) {
          neighborsOfEachTypeAtCoordinate.add(numberOfNeighborsForEachType.get(index)[x][y]);
        }
        rules.decideState(neighborsOfEachTypeAtCoordinate, nextStates, x, y, this);
      }
      //     System.out.println();
    }
    updateStatesForAllCells(nextStates, rules.getPossibleTypes(), rules.getPossibleColors());
  }

  public List<String> saveSimulation(){
    ArrayList<String> state = new ArrayList<>();
    for(int i=0; i < stateOfCells.length; i++){
      for(int j=0; j < stateOfCells.length; j++){

        state.add(String.valueOf(stateOfCells[i][j].getxCoord()));
        state.add(String.valueOf(stateOfCells[i][j].getyCoord()));
        state.add(stateOfCells[i][j].getType());
        state.add(String.valueOf(stateOfCells[i][j].getColor()));
        state.add(String.valueOf(stateOfCells[i][j].getEnergy()));
        state.add(String.valueOf(stateOfCells[i][j].getNumberOfMoves()));
      }
    }
    return state;
  }

  private void updateStatesForAllCells(List<int[][]> nextStates,
      ArrayList<String> possibleTypes, ArrayList<String> possibleColors) {
    summary = new HashMap<>();
    for(String types: possibleTypes){
      summary.put(types, 0);
    }
    for (int index = 0; index < nextStates.size(); index++) {
      for (int r = 0; r < row; r++) {
        for (int c = 0; c < col; c++) {
          summary.put(stateOfCells[r][c].getType(), summary.get(stateOfCells[r][c].getType()) + 1);
          if (nextStates.get(index)[r][c] == 1) {
            stateOfCells[r][c] = new State(r, c, possibleTypes.get(index),
                possibleColors.get(index), 0);
          }
        }
      }
    }
  }

  private List<int[][]> nextStatesOfCells(List<int[][]> numberOfNeighborsForEachType) {
    List<int[][]> nextStatesForEachType = new ArrayList<>();
    for (int[][] types : numberOfNeighborsForEachType) {
      nextStatesForEachType.add(numberOfAliveNeighbors(stateOfCells, "", numberOfSides));
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

  //This is a method that needs to go
  public State[][] getGrid() {
    return stateOfCells;
  }

  public State getStateAtCoordinate(int x, int y) {
    return this.stateOfCells[x][y];
  }

  public void setStateAtCoordinate(int x, int y, State state) {
    this.stateOfCells[x][y] = state;
  }

  public String getColorAtCoordinate(int x, int y) {
    return stateOfCells[x][y].getColor();
  }

  public void buildAntGridWithTemplate(ArrayList<String> coordinates,
      ArrayList<String> possibleTypes, ArrayList<String> possibleColors,
      int radius, int numberOfSides) {
    ForagingAntGridManager foragingAntGridManager = new ForagingAntGridManager(row, col);
    this.stateOfCells = foragingAntGridManager.buildAntGridWithTemplateHelper(coordinates, possibleTypes, possibleColors, radius);
    this.numberOfSides = numberOfSides;
  }
}

