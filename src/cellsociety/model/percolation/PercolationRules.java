package cellsociety.model.percolation;

import cellsociety.model.cell.State;
import cellsociety.model.rules.Rules;
import java.util.ArrayList;
import java.util.Random;

/**
 * Purpose: This class contains the rules for the percolation model. Rules include the types of the
 * players as well as logic to update each cell. Assumptions: xml file is correctly formatted and
 * supplies the correct information to the constructor. Dependencies: Depends on SimulationEngine to
 * declare constructors based on the parameters read from XML files. Depends on GridManager to
 * provide it with the grid to work with.
 *
 * @author Ji Yun Hyo
 */
public class PercolationRules extends Rules {

  private final String BLOCK_COLOR = "black";
  private final String WATER_COLOR = "blue";
  private final String EMPTY_COLOR = "lightgrey";
  private final ArrayList<String> possibleTypes;
  private final ArrayList<String> possibleColors;
  private final String BLOCK = "block";
  private final String WATER = "water";
  private final String EMPTY = "empty";
  private final Random random;
  private double probsOfFire;

  /**
   * Default constructor
   */
  public PercolationRules(long randomSeed) {
    random = new Random(randomSeed);
    possibleTypes = new ArrayList<>();
    possibleColors = new ArrayList<>();
    possibleTypes.add(EMPTY);
    possibleTypes.add(WATER);
    possibleTypes.add(BLOCK);
    possibleColors.add(EMPTY_COLOR);
    possibleColors.add(WATER_COLOR);
    possibleColors.add(BLOCK_COLOR);
  }

  private void decideState(State[][] statesOfAllCells, String type, int[][] emptyNeighbors) {

    System.out.println(type);
    for (int x = 0; x < statesOfAllCells.length; x++) {
      for (int y = 0; y < statesOfAllCells[0].length; y++) {
        if (statesOfAllCells[x][y].getType().equals(EMPTY) && emptyNeighbors[x][y] == 1) {
          statesOfAllCells[x][y] = new State(x, y, type);
          setColor(statesOfAllCells[x][y]);
          return;
        }
      }
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


  /**
   * Purpose: Judges each of the cells according to the logic of the game Assumptions:
   * statesOfAllCells correctly contains all valid states
   *
   * @param statesOfAllCells starting states of all cells
   * @return the updated states of all cells
   */
  public State[][] judgeStateOfEachCell(State[][] statesOfAllCells) {
    int[][] numberOfFireNeighbors = numberOfAliveNeighbors(statesOfAllCells, BLOCK);
    int[][] numberOfTreeNeighbors = numberOfAliveNeighbors(statesOfAllCells, WATER);
    int[][] dissatisfiedNeighbors = numberOfAliveNeighbors(statesOfAllCells, "");
    int[][] waterNextRound = numberOfAliveNeighbors(statesOfAllCells, "");
    for (int x = 0; x < statesOfAllCells.length; x++) {
      for (int y = 0; y < statesOfAllCells[0].length; y++) {
        if (statesOfAllCells[x][y].getType().equals(WATER)) {
          if (x - 1 >= 0 && y >= 0 && statesOfAllCells[x - 1][y].getType().equals(EMPTY)) {
            waterNextRound[x - 1][y] = 1;
          }
          if (x >= 0 && y - 1 >= 0 && statesOfAllCells[x][y - 1].getType().equals(EMPTY)) {

            waterNextRound[x][y - 1] = 1;

          }
          if (x + 1 < statesOfAllCells.length && y >= 0 && statesOfAllCells[x + 1][y].getType()
              .equals(EMPTY)) {

            waterNextRound[x + 1][y] = 1;

          }
          if (x >= 0 && y + 1 < statesOfAllCells[0].length && statesOfAllCells[x][y + 1].getType()
              .equals(
                  EMPTY)) {

            waterNextRound[x][y + 1] = 1;

          }
        }
      }
    }
    statesOfAllCells = setToFire(waterNextRound, statesOfAllCells);
    //  printGrid(waterNextRound);
    return statesOfAllCells;
  }


  private State[][] setToFire(int[][] waterNextRound, State[][] statesOfAllCells) {
    for (int i = 0; i < waterNextRound.length; i++) {
      for (int j = 0; j < waterNextRound[0].length; j++) {
        if (waterNextRound[i][j] == 1) {
          statesOfAllCells[i][j] = new State(i, j, WATER);
          setColor(statesOfAllCells[i][j]);
        }
      }
    }
    return statesOfAllCells;
  }

  private void setColor(State state) {
    if (state.getType().equals(BLOCK)) {
      state.setColor(BLOCK_COLOR);
    } else if (state.getType().equals(WATER)) {
      state.setColor(WATER_COLOR);
    } else {
      state.setColor(EMPTY_COLOR);
    }
  }

  /**
   * specifices the starting states of the cells according to the simulation rule
   *
   * @return type of cells
   */
  @Override
  public String getStartingPositionCellType() {
    return null;
  }

  @Override
  public ArrayList<String> getPossibleTypes() {
    return possibleTypes;
  }

  @Override
  public ArrayList<String> getPossibleColors() {
    return possibleColors;
  }


}