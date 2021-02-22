package cellsociety.model.simulationrules;

import cellsociety.model.GridManager;
import cellsociety.model.State;
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

  private final String AGENTX_COLOR;
  private final String AGENTY_COLOR;
  private final String EMPTY_COLOR;

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
  public SegregationModelRules(long randomSeed, double THRESHHOLD, String colorX, String colorY,
      String emptyColor) {
    random = new Random(randomSeed);
    this.THRESHHOLD = THRESHHOLD;
    this.AGENTX_COLOR = colorX;
    this.AGENTY_COLOR = colorY;
    this.EMPTY_COLOR = emptyColor;

    initializeColorsAndTypes();
  }

  private void initializeColorsAndTypes() {
    possibleTypes = new ArrayList<>();
    possibleColors = new ArrayList<>();
    possibleTypes.add(EMPTY);
    possibleTypes.add(AGENTY);
    possibleTypes.add(AGENTX);

    possibleColors.add(EMPTY_COLOR);
    possibleColors.add(AGENTY_COLOR);
    possibleColors.add(AGENTX_COLOR);
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
   * returns the possible types (e.g. agent x, agent y, empty)
   *
   * @return arraylist of possible types
   */
  @Override
  public List<String> getPossibleTypes() {
    return possibleTypes;
  }

  /**
   * Returns the possible colors for each type
   *
   * @return arraylist of colors
   */
  @Override
  public List<String> getPossibleColors() {
    return possibleColors;
  }

  /**
   * Decide state updates each cell at each specific coordinate location according to the rules.
   * Assumption is that all the parameters are non-null values that are properly defined.
   * @param neighborsOfEachTypeAtTheCurrentLocation counts the number of neighbors that are specific "type" of
   *                                                neighbors at the specific x, y coordinate location for all
   *                                                possible types of the simulation.
   * @param markStateForFurtherAnalysis this list of integer array acts as the means for each rules class
   *                                    to keep track of each possible type of state. List contains one integer
   *                                    array per possible type of state.
   * @param updateStates List of states contains all the states that are to be updated at the end of each iteration.
   *                     UpdateStates is sent to each Rules class where rules are applied. If a state has to be
   *                     updated, the rules class adds the state to updateStates.
   * @param x x coordinate
   * @param y y coordinate
   * @param gridManager gridManager object needed to control and oversee state checking
   */
  @Override
  public void decideState(List<Integer> neighborsOfEachTypeAtTheCurrentLocation, List<int[][]> markStateForFurtherAnalysis,
      List<State> updateStates, int x, int y,
      GridManager gridManager) {

    int agentXNeighbor = neighborsOfEachTypeAtTheCurrentLocation.get(2);
    int agentYNeighbor = neighborsOfEachTypeAtTheCurrentLocation.get(1);
    double total = agentYNeighbor + agentXNeighbor;
    double ratio = 0;

    if (gridManager.getTypeAtCoordinate(x, y).equals(AGENTY) && total != 0) {
      ratio = ((double) agentYNeighbor) / total;
      if (ratio < THRESHHOLD) {

        markStateForFurtherAnalysis.get(1)[x][y] = 1;
      }
    }
    if (gridManager.getTypeAtCoordinate(x, y).equals(AGENTX) && total != 0) {
      ratio = ((double) agentXNeighbor) / total;
      if (ratio < THRESHHOLD) {
        markStateForFurtherAnalysis.get(2)[x][y] = 1;
      }
    }
    if (gridManager.getTypeAtCoordinate(x, y).equals(EMPTY)) {
      markStateForFurtherAnalysis.get(0)[x][y] = 1;
    }

    relocateDissatisfiedNeighbors(markStateForFurtherAnalysis, gridManager);
    removeEmptyCells(markStateForFurtherAnalysis);
  }

  private void removeEmptyCells(List<int[][]> nextStates) {
    for (int[][] empty : nextStates) {
      for (int i = 0; i < empty.length; i++) {
        for (int j = 0; j < empty[0].length; j++) {
          empty[i][j] = 0;
        }
      }

    }
  }

  private void relocateDissatisfiedNeighbors(List<int[][]> nextStates, GridManager gridManager) {
    for (int index = 1; index < nextStates.size(); index++) {
      for (int i = 0; i < nextStates.get(index).length; i++) {
        for (int j = 0; j < nextStates.get(index)[0].length; j++) {
          if (nextStates.get(index)[i][j] == 1) {
            randomlyAssignAgentsToNewPlaces(gridManager, getPossibleTypes().get(index),
                nextStates.get(0));
            gridManager.setStateAtCoordinate(i, j, new State(i, j, EMPTY));
            setColor(gridManager.getStateAtCoordinate(i, j));
            nextStates.get(index)[i][j] = 0;
          }
        }
      }
    }
  }

  private void randomlyAssignAgentsToNewPlaces(GridManager gridManager, String type,
      int[][] emptyNeighbors) {
    int counter = 0;
    while (true) {
      int x = random.nextInt(gridManager.getRow());
      int y = random.nextInt(gridManager.getColumn());
      if (gridManager.getTypeAtCoordinate(x, y).equals(EMPTY) && emptyNeighbors[x][y] == 1
          && counter < DELAY) {
        gridManager.setStateAtCoordinate(x, y, new State(x, y, type));
        setColor(gridManager.getStateAtCoordinate(x, y));
        return;
      } else if (gridManager.getTypeAtCoordinate(x, y).equals(EMPTY) && counter >= DELAY) {
        gridManager.setStateAtCoordinate(x, y, new State(x, y, type));
        setColor(gridManager.getStateAtCoordinate(x, y));
        return;
      }
      counter++;
    }

  }

}