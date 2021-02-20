package cellsociety.model.watormodel;

import cellsociety.controller.grid.GridManager;
import cellsociety.model.cell.State;
import cellsociety.model.rules.Rules;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Purpose: This class contains the rules for the segregation model. Rules include the types of the
 * players as well as logic to update each cell. Assumptions: xml file is correctly formatted and
 * supplies the correct information to the constructor. Dependencies: Depends on SimulationEngine to
 * declare constructors based on the parameters read from XML files. Depends on GridManager to
 * provide it with the grid to work with.
 *
 * @author Ji Yun Hyo
 */
public class SegregationModelRules extends Rules {

  private final String AGENTX_COLOR = "red";
  private final String AGENTY_COLOR = "blue";
  private final String EMPTY_COLOR = "lightgrey";
  private ArrayList<String> possibleTypes;
  private ArrayList<String> possibleColors;
  private String AGENTX = "agentx";
  private String AGENTY = "agenty";
  private String EMPTY = "empty";
  private double THRESHHOLD;
  private Random random;
  private int DELAY = 10000;

  /**
   * Default constructor
   */
  public SegregationModelRules(long randomSeed, double THRESHHOLD) {
    random = new Random(randomSeed);
    possibleTypes = new ArrayList<>();
    possibleColors = new ArrayList<>();
    possibleTypes.add(EMPTY);
    possibleTypes.add(AGENTY);
    possibleTypes.add(AGENTX);
    possibleColors.add(EMPTY_COLOR);
    possibleColors.add(AGENTY_COLOR);
    possibleColors.add(AGENTX_COLOR);
    this.THRESHHOLD = THRESHHOLD;
  }

  private void randomlyAssignAgentsToNewPlaces(State[][] statesOfAllCells, String type, int[][] emptyNeighbors) {
    int counter = 0;
    while (true) {
      int x = random.nextInt(statesOfAllCells.length);
      int y = random.nextInt(statesOfAllCells[0].length);
      if (statesOfAllCells[x][y].getType().equals(EMPTY) && emptyNeighbors[x][y] == 1
          && counter < DELAY) {
        statesOfAllCells[x][y] = new State(x, y, type);
        setColor(statesOfAllCells[x][y]);
        return;
      } else if (statesOfAllCells[x][y].getType().equals(EMPTY) && counter >= DELAY) {
        statesOfAllCells[x][y] = new State(x, y, type);
        setColor(statesOfAllCells[x][y]);
        return;
      }
      counter++;
    }

  }


  private void printGrid(int[][] stateOfCells) {
    for (int x = 0; x < stateOfCells.length; x++) {
      for (int y = 0; y < stateOfCells[0].length; y++) {
        System.out.print(" " + stateOfCells[y][x] + " ");
      }
      System.out.println();
    }
    System.out.println();
  }


  private State[][] relocateDissatisfiedNeighbors(int[][] dissatisfiedNeighbors,
      State[][] statesOfAllCells, int[][] emptyNeighbors) {
    for (int i = 0; i < dissatisfiedNeighbors.length; i++) {
      for (int j = 0; j < dissatisfiedNeighbors[0].length; j++) {
        if (dissatisfiedNeighbors[i][j] == 1) {
          randomlyAssignAgentsToNewPlaces(statesOfAllCells, AGENTY, emptyNeighbors);
          statesOfAllCells[i][j] = new State(i, j, EMPTY);
          setColor(statesOfAllCells[i][j]);
        }
        if (dissatisfiedNeighbors[i][j] == 2) {
          randomlyAssignAgentsToNewPlaces(statesOfAllCells, AGENTX, emptyNeighbors);
          statesOfAllCells[i][j] = new State(i, j, EMPTY);
          setColor(statesOfAllCells[i][j]);
        }
      }
    }
    return statesOfAllCells;
  }

  private void setColor(State state) {
    if (state.getType().equals(AGENTX)) {
      state.setColor(AGENTX_COLOR);
    } else if (state.getType().equals(AGENTY)) {
      state.setColor(AGENTY_COLOR);
    } else {
      state.setColor(EMPTY_COLOR);
    }
  }

  /**
   * judges the state of each cell using the rule of the specific model class
   *
   * @param statesOfAllCells             starting states of all cells
   * @param numberOfNeighborsForEachType
   * @return updated states of all cells
   */
  @Override
  public State[][] judgeStateOfEachCell(State[][] statesOfAllCells,
      List<int[][]> numberOfNeighborsForEachType) {
    int[][] numberOfAGENTXNeighbors = numberOfAliveNeighbors(statesOfAllCells, AGENTX);
    int[][] numberOfAGENTYNeighbors = numberOfAliveNeighbors(statesOfAllCells, AGENTY);

    //declare empty arrays
    int[][] dissatisfiedNeighbors = numberOfAliveNeighbors(statesOfAllCells, "");
    int[][] emptyNeighbors = numberOfAliveNeighbors(statesOfAllCells, "");


    //loop through each one
    for (int x = 0; x < statesOfAllCells.length; x++) {
      for (int y = 0; y < statesOfAllCells[0].length; y++) {
        //apply the rule
        int agentXNeighbor = numberOfAGENTXNeighbors[x][y];
        int agentYNeighbor = numberOfAGENTYNeighbors[x][y];
        double total = agentYNeighbor + agentXNeighbor;
        double ratio = 0;

        if (statesOfAllCells[x][y].getType().equals(AGENTY) && total != 0) {
          ratio = ((double) agentYNeighbor) / total;
          if (ratio < THRESHHOLD) {
            dissatisfiedNeighbors[x][y] = 1;
          }
        }
        if (statesOfAllCells[x][y].getType().equals(AGENTX) && total != 0) {
          //    System.out.println(ratio);
          ratio = ((double) agentXNeighbor) / total;
          if (ratio < THRESHHOLD) {

            dissatisfiedNeighbors[x][y] = 2;
          }
        }
        if (statesOfAllCells[x][y].getType().equals(EMPTY)) {
          emptyNeighbors[x][y] = 1;
        }

      }
    }
    statesOfAllCells = relocateDissatisfiedNeighbors(dissatisfiedNeighbors, statesOfAllCells,
        emptyNeighbors);
    //   printGrid(dissatisfiedNeighbors);
    return statesOfAllCells;
  }

  /**
   * specifies the starting states of the cells according to the simulation rule
   *
   * @return type of cells
   */
  @Override
  public String getStartingPositionCellType() {
    return null;
  }

  /**
   * returns the possible types (e.g. agent x, agent y, empty)
   *
   * @return arraylist of possible types
   */
  @Override
  public ArrayList<String> getPossibleTypes() {
    return possibleTypes;
  }

  /**
   * Returns the possible colors for each type
   *
   * @return arraylist of colors
   */
  @Override
  public ArrayList<String> getPossibleColors() {
    return possibleColors;
  }

  @Override
  public void decideState(List<Integer> neighborsOfEachTypeAtCoordinate, List<int[][]> nextStates,
      int x, int y, GridManager gridManager) {

    int agentXNeighbor = neighborsOfEachTypeAtCoordinate.get(2);
    int agentYNeighbor = neighborsOfEachTypeAtCoordinate.get(1);
    double total = agentYNeighbor + agentXNeighbor;
    double ratio = 0;

    if (gridManager.getTypeAtCoordinate(x,y).equals(AGENTY) && total != 0) {
      ratio = ((double) agentYNeighbor) / total;
      if (ratio < THRESHHOLD) {

        nextStates.get(1)[x][y] = 1;
      }
    }
    if (gridManager.getTypeAtCoordinate(x,y).equals(AGENTX) && total != 0) {
      //    System.out.println(ratio);
      ratio = ((double) agentXNeighbor) / total;
      if (ratio < THRESHHOLD) {
        nextStates.get(2)[x][y] = 1;
      }
    }
    if (gridManager.getTypeAtCoordinate(x,y).equals(EMPTY)) {
      nextStates.get(0)[x][y] = 1;
    }

    relocateDissatisfiedNeighbors(nextStates, gridManager);
  }

  private void relocateDissatisfiedNeighbors(List<int[][]> nextStates, GridManager gridManager) {
    for(int index = 1; index < nextStates.size(); index++){
      for (int i = 0; i < nextStates.get(index).length; i++) {
        for (int j = 0; j < nextStates.get(index)[0].length; j++) {
          if (nextStates.get(index)[i][j] == 1) {
            randomlyAssignAgentsToNewPlaces(gridManager, getPossibleTypes().get(index), nextStates.get(0));
            gridManager.setStateAtCoordinate(i,j,  new State(i, j, EMPTY));
            setColor(gridManager.getStateAtCoordinate(i,j));
          }
        }
      }
    }
  }

  private void randomlyAssignAgentsToNewPlaces(GridManager gridManager, String type, int[][] emptyNeighbors) {
    int counter = 0;
    while (true) {
      int x = random.nextInt(gridManager.getRow());
      int y = random.nextInt(gridManager.getColumn());
      if (gridManager.getTypeAtCoordinate(x,y).equals(EMPTY) && emptyNeighbors[x][y] == 1
          && counter < DELAY) {
        gridManager.setStateAtCoordinate(x,y, new State(x, y, type));
        setColor(gridManager.getStateAtCoordinate(x,y));
        return;
      } else if (gridManager.getTypeAtCoordinate(x,y).equals(EMPTY) && counter >= DELAY) {
        gridManager.setStateAtCoordinate(x,y, new State(x, y, type));
        setColor(gridManager.getStateAtCoordinate(x,y));
        return;
      }
      counter++;
    }

  }

}