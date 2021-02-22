package cellsociety.model;

import cellsociety.model.simulationrules.Rules;
import cellsociety.model.simulationrules.foragingants.ForagingAntGridManager;
import java.util.ArrayList;
import java.util.Collections;
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
  private int numberOfSides;
  private Map<String, Integer> summary;
  private ArrayList<String> coordinates;

  /**
   * Basic constructor for GridManager that takes in the number of sides to consider
   * @param row
   * @param col
   * @param numberOfSides
   */
  public GridManager(int row, int col, int numberOfSides) {
    this.row = row;
    this.col = col;
    this.numberOfSides = numberOfSides;
    summary = new HashMap<>();
  }

  /**
   * Builds the initial stage with specific coordinates that are supplied to the GridManager
   *
   * @param template coordinates of starting states
   */
  public void buildGridWithTemplate(List<State> template,
      List<String> possibleTypes, List<String> possibleColors) {
    State[][] stateOfCells = new State[row][col];
    for (int r = 0; r < row; r++) {
      for (int c = 0; c < col; c++) {
        State state = new State(r, c, possibleTypes.get(0), possibleColors.get(0), 0);
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
      stateOfCells[s.getxCoord()][s.getyCoord()] = new State(s.getxCoord(), s.getyCoord(),
          possibleTypes.get(1), possibleColors.get(1), 0);
    }
    this.stateOfCells = stateOfCells;
  }

  /**
   * Builds the starting states for models that require adjustments based on the parameters.
   * Depends on the correctness of each parameter sent in. The user can call this method
   * with the appropriate parameters after initializing a valid rules class.
   * @param emptyRatio      ratio of empty cells to occupied cells
   * @param populationRatio ratio between different types of players
   * @param seed            random seed to reproduce results
   * @param possibleTypes   names of all players of a model
   * @param possibleColors  colors of all types
   */
  public void buildGridWithRandomSeed(double emptyRatio, double populationRatio, int seed,
      List<String> possibleTypes, List<String> possibleColors) {
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

  /**
   * Counts the number of each possible type and returns the list of all tally.
   * Assumption is that the caller only needs to read and not modify the data.
   * The data is sent as an unmodifiableMap to prevent the user from changing the summary.
   * @return List containing counts for each possible type
   */
  public Map<String, Integer> getTallyOfEachTypeToPresentAsSummary() {
    return Collections.unmodifiableMap(summary);
  }

  private List<int[][]> getNumberOfNeighborsForEachType(List<String> possibleTypes,
      int numberOfSides) {
    ArrayList<int[][]> numberOfNeighborsForEachType = new ArrayList<>();
    for (String type : possibleTypes) {
      numberOfNeighborsForEachType.add(numberOfAliveNeighbors(stateOfCells, type, numberOfSides));
    }
    return numberOfNeighborsForEachType;
  }

  private int[][] numberOfAliveNeighbors(State[][] statesOfAllCells, String type,
      int numberOfSides) {
    int[][] numberOfNeighbors = new int[statesOfAllCells.length][statesOfAllCells[0].length];
    for (int x = 0; x < statesOfAllCells.length; x++) {
      for (int y = 0; y < statesOfAllCells[0].length; y++) {
        checkEightSidesForNumberOfNeighbors(statesOfAllCells, type, numberOfNeighbors, x, y,
            numberOfSides);
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
        if (checkNumberOfSidesToSeeWhetherToIncludeThisCell(statesOfAllCells, type, x, y,
            numberOfSides, xCoord, yCoord)) {
          numberOfNeighbor++;
        }
      }
    }
    numberOfNeighbors[x][y] = numberOfNeighbor;
  }

  private boolean checkNumberOfSidesToSeeWhetherToIncludeThisCell(State[][] statesOfAllCells,
      String type, int x, int y,
      int numberOfSides, int xCoord, int yCoord) {
    boolean isValidNeighbor = false;
    if (xCoord >= 0 && yCoord >= 0 && xCoord < statesOfAllCells.length
        && yCoord < statesOfAllCells[0].length && statesOfAllCells[xCoord][yCoord].getType()
        .equals(type) && !(xCoord == x && yCoord == y)) {
      isValidNeighbor = isValidNeighborOfSquareCell(x, y, numberOfSides, xCoord, yCoord,
          isValidNeighbor);
      isValidNeighbor = isValidNeighborOfHexagonalCell(x, y, numberOfSides, xCoord, yCoord,
          isValidNeighbor);
      isValidNeighbor = isValidNeighborOfTriangleCell(x, y, numberOfSides, xCoord, yCoord,
          isValidNeighbor);
    }
    return isValidNeighbor;
  }

  private boolean isValidNeighborOfHexagonalCell(int x, int y, int numberOfSides, int xCoord,
      int yCoord,
      boolean isValidNeighbor) {
    if (numberOfSides == 6 && isHexagonalNeighborForOddYCoord(x, y, xCoord, yCoord)) {
      // && !(xCoord == x - 1 && yCoord == y + 1)
      isValidNeighbor = true;
    }
    if (numberOfSides == 6 && isHexagonalNeighborForEvenYCoord(x, y, xCoord, yCoord)) {
      //&& !(xCoord == x + 1 && yCoord == y - 1) && !(xCoord == x + 1 && yCoord == y + 1)
      isValidNeighbor = true;
    }
    return isValidNeighbor;
  }

  private boolean isValidNeighborOfSquareCell(int x, int y, int numberOfSides, int xCoord,
      int yCoord,
      boolean isValidNeighbor) {
    if (numberOfSides == 8 && (xCoord != x - 2 && xCoord != x + 2)) {
      isValidNeighbor = true;
    }
    if (numberOfSides == 4 && isOneOfTheNWSEDirection(x, y, xCoord, yCoord)) {
      isValidNeighbor = true;
    }
    return isValidNeighbor;
  }

  /**
   * Purpose is to take orders from SimulationEngine to start updating the grid with the appropriate rules.
   * Assumption is that the Rules class that is sent is is appropriately defined.
   * Dependencies with a few methods such as getNumberOfNeighborsForEachType and nextStatesOfCells,
   * both of which are private methods.
   * @param rules a general rules class with all the rules necessary to judge the state of each cell.
   */
  public void judgeStateOfEachCell(Rules rules) {
    List<int[][]> numberOfNeighborsForEachType = getNumberOfNeighborsForEachType(
        rules.getPossibleTypes(), numberOfSides);
    List<int[][]> nextStates = nextStatesOfCells(numberOfNeighborsForEachType);
    ArrayList<State> updateStates = new ArrayList<>();

    for (int x = 0; x < row; x++) {
      for (int y = 0; y < col; y++) {
        List<Integer> neighborsOfEachTypeAtCoordinate = new ArrayList<>();
        for (int index = 0; index < numberOfNeighborsForEachType.size(); index++) {
          neighborsOfEachTypeAtCoordinate.add(numberOfNeighborsForEachType.get(index)[x][y]);
        }
        rules.decideState(neighborsOfEachTypeAtCoordinate, nextStates, updateStates, x, y, this);
      }
    }
    updateStatesForNextRound(updateStates);
  }

  private boolean isValidNeighborOfTriangleCell(int x, int y, int numberOfSides, int xCoord,
      int yCoord, boolean count) {
    if (numberOfSides == 3 && !isFacingUp(xCoord, yCoord) && isLeftRightUpNeighbors(x, y,
        xCoord, yCoord, yCoord - 1)) {
      count = true;
    }
    if (numberOfSides == 3 && isFacingUp(xCoord, yCoord) && isLeftRightUpNeighbors(x, y,
        xCoord, yCoord, yCoord + 1)) {
      count = true;
    }
    if (numberOfSides == 12 && isFacingUp(xCoord, yCoord) && isOneOfTheSidesForFacingUpTriangles(
        x, y, xCoord, yCoord)) {
      count = true;
    }
    if (numberOfSides == 12 && !isFacingUp(xCoord, yCoord)
        && isOneOfTheSidesForFacingDownTriangles(x, y, xCoord, yCoord)) {
      count = true;
    }
    return count;
  }

  private boolean isOneOfTheSidesForFacingDownTriangles(int x, int y, int xCoord, int yCoord) {
    return (y == yCoord - 1 && (x <= xCoord + 2 && x >= xCoord - 2)) || (y == yCoord && (
        x <= xCoord + 2 && x >= xCoord - 2)) || (y == yCoord + 1 && (x <= xCoord + 1
        && x >= xCoord - 1));
  }

  private boolean isOneOfTheSidesForFacingUpTriangles(int x, int y, int xCoord, int yCoord) {
    return (y == yCoord - 1 && (x <= xCoord + 1 && x >= xCoord - 1)) || (y == yCoord && (
        x <= xCoord + 2 && x >= xCoord - 2)) || (y == yCoord + 1 && (x <= xCoord + 2
        && x >= xCoord - 2));
  }

  private boolean isFacingUp(int xCoord, int yCoord) {
    return (xCoord % 2 == 0 && yCoord % 2 == 0) || (yCoord % 2 != 0 && xCoord % 2 != 0);
  }

  private boolean isLeftRightUpNeighbors(int x, int y, int xCoord, int yCoord, int i) {
    return (x == xCoord - 1 && y == yCoord) || (x == xCoord + 1 && y == yCoord) || (x == xCoord
        && y == i);
  }

  /**
   * Unfinished version of saving simulation. Saves each state and its basic properties.
   * Assumption is that the user does not need to modify the states.
   * @return Unmodifiable list of states to be saved
   */
  public List<String> saveSimulation() {
    ArrayList<String> state = new ArrayList<>();
    for (int i = 0; i < stateOfCells.length; i++) {
      for (int j = 0; j < stateOfCells.length; j++) {
        state.add(String.valueOf(stateOfCells[i][j].getxCoord()));
        state.add(String.valueOf(stateOfCells[i][j].getyCoord()));
        state.add(stateOfCells[i][j].getType());
        state.add(String.valueOf(stateOfCells[i][j].getColor()));
        state.add(String.valueOf(stateOfCells[i][j].getEnergy()));
        state.add(String.valueOf(stateOfCells[i][j].getNumberOfMoves()));
      }
    }
    return Collections.unmodifiableList(state);
  }

  private boolean isOneOfTheNWSEDirection(int x, int y, int xCoord, int yCoord) {
    return !(x == xCoord - 1 && y == yCoord - 1) && !(x == xCoord - 1 && y == yCoord + 1) && !(
        x == xCoord + 1 && y == yCoord - 1) && !(x == xCoord + 1 && y == yCoord + 1) && (
        xCoord != x - 2 && xCoord != x + 2);
  }

  private boolean isHexagonalNeighborForOddYCoord(int x, int y, int xCoord, int yCoord) {
    return yCoord % 2 != 0 && !(x == xCoord - 1 && y == yCoord - 1) && !(x == xCoord - 1
        && y == yCoord + 1) && (xCoord != x - 2 && xCoord != x + 2);
  }

  private boolean isHexagonalNeighborForEvenYCoord(int x, int y, int xCoord, int yCoord) {
    return yCoord % 2 == 0 && !(x == xCoord + 1 && y == yCoord + 1) && !(x == xCoord + 1
        && y == yCoord - 1) && (xCoord != x - 2 && xCoord != x + 2);
  }

  private void updateStatesForNextRound(List<State> updateStates) {
    for (State state : updateStates) {
      stateOfCells[state.getxCoord()][state.getyCoord()] = state;
    }
  }

  /**
   * Returns the coordinates of the nest location for Ants class.
   * @return unmodifiable list containing the coordinates of the nest
   */
  public List<String> getNestCoordinates() {
    return Collections.unmodifiableList(coordinates);
  }

  /**
   * Builds the specific type of Grid for ant simulations. Intead of using the other template,
   * I came up with a faster way of building a grid using x, y coordinates of nest and food plus
   * the radius.
   * @param coordinates coordinates of nest and food locations
   * @param possibleTypes possible types present in the simulation
   * @param possibleColors possible colors present in the simulation
   * @param radius radius of the nest and food locations around the x, y coordinate
   * @param numberOfSides number of sides specified for the simulation
   */
  public void buildAntGridWithTemplate(List<String> coordinates,
      List<String> possibleTypes, List<String> possibleColors,
      int radius, int numberOfSides) {
    this.coordinates = (ArrayList<String>) coordinates;
    ForagingAntGridManager foragingAntGridManager = new ForagingAntGridManager(row, col,
        numberOfSides);
    this.stateOfCells = foragingAntGridManager.buildAntGridWithTemplateHelper(coordinates, possibleTypes, possibleColors, radius);
    this.numberOfSides = numberOfSides;
  }


  private List<int[][]> nextStatesOfCells(List<int[][]> numberOfNeighborsForEachType) {
    List<int[][]> nextStatesForEachType = new ArrayList<>();
    for (int[][] types : numberOfNeighborsForEachType) {
      nextStatesForEachType.add(numberOfAliveNeighbors(stateOfCells, "", numberOfSides));
    }
    return nextStatesForEachType;
  }

  /**
   * Instead of sending the entire grid to the rules class, which is not a good practice of
   * encapsulation, GridManager object can peek the type of object at the specified coordinate
   * @param x x coordinate of the state
   * @param y y coordinate of the state
   * @return the string name of the type
   */
  public String getTypeAtCoordinate(int x, int y) {
    return stateOfCells[x][y].getType();
  }

  /**
   * Returns the row
   * @return row
   */
  public int getRow() {
    return row;
  }

  /**
   * Returns the column
   * @return column
   */
  public int getColumn() {
    return col;
  }

  /**
   * Similar to the getTypeAtCoordinate method, the caller can peek the type at the specific coordinate.
   * Assumption is that the user was informed of this method.
   * @param x x coordinate
   * @param y y coordinate
   * @return State object at the specified location
   */
  public State getStateAtCoordinate(int x, int y) {
    return this.stateOfCells[x][y];
  }

  /**
   * Allow to set the State at the specified Coordinate. This is necessary for simulations for which
   * the states are not updated all at once, but are instead updated one cell at a time.
   * @param x
   * @param y
   * @param state State object
   */
  public void setStateAtCoordinate(int x, int y, State state) {
    this.stateOfCells[x][y] = state;
  }

  /**
   *
   * @param x
   * @param y
   * @return
   */
  public String getColorAtCoordinate(int x, int y) {
    return stateOfCells[x][y].getColor();
  }
}

