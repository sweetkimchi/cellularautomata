package cellsociety.model.spreadingoffire;

import cellsociety.model.cell.State;
import cellsociety.model.rules.Rules;
import java.util.ArrayList;
import java.util.Random;

/**
 * Purpose: This class contains the rules for the Spreading of Fire model. Rules include the types
 * of the players as well as logic to update each cell. Assumptions: xml file is correctly formatted
 * and supplies the correct information to the constructor. Dependencies: Depends on
 * SimulationEngine to declare constructors based on the parameters read from XML files. Depends on
 * GridManager to provide it with the grid to work with.
 *
 * @author Ji Yun Hyo
 */
public class SpreadingOfFireRules extends Rules {

  private final String FIRE_COLOR = "brown";
  private final String TREE_COLOR = "green";
  private final String EMPTY_COLOR = "yellow";
  private final ArrayList<String> possibleTypes;
  private final ArrayList<String> possibleColors;
  private final String FIRE = "fire";
  private final String TREE = "tree";
  private final String EMPTY = "empty";
  private final double probsOfFire;
  private final Random random;

  /**
   * Defines the constructor of spreading of fire simulation
   *
   * @param randomSeed  random seed so that the results can be reproduced
   * @param probsOfFire probability of catching fire if the tree is adjacent to a burning tree
   */
  public SpreadingOfFireRules(long randomSeed, double probsOfFire) {
    random = new Random(randomSeed);
    possibleTypes = new ArrayList<>();
    possibleColors = new ArrayList<>();
    possibleTypes.add(EMPTY);
    possibleTypes.add(TREE);
    possibleTypes.add(FIRE);
    possibleColors.add(EMPTY_COLOR);
    possibleColors.add(TREE_COLOR);
    possibleColors.add(FIRE_COLOR);
    this.probsOfFire = probsOfFire;
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
    int[][] numberOfFireNeighbors = numberOfAliveNeighbors(statesOfAllCells, FIRE);
    int[][] numberOfTreeNeighbors = numberOfAliveNeighbors(statesOfAllCells, TREE);
    int[][] dissatisfiedNeighbors = numberOfAliveNeighbors(statesOfAllCells, "");
    int[][] fireNextRound = numberOfAliveNeighbors(statesOfAllCells, "");
    int[][] emptyNextRound = numberOfAliveNeighbors(statesOfAllCells, "");
    for (int x = 0; x < statesOfAllCells.length; x++) {
      for (int y = 0; y < statesOfAllCells[0].length; y++) {
        if (statesOfAllCells[x][y].getType().equals(FIRE)) {
          if (x - 1 >= 0 && y >= 0 && statesOfAllCells[x - 1][y].getType().equals(TREE)) {
            double randomNumber = random.nextDouble();
            if (randomNumber < probsOfFire) {
              fireNextRound[x - 1][y] = 1;
            }
          }
          if (x >= 0 && y - 1 >= 0 && statesOfAllCells[x][y - 1].getType().equals(TREE)) {
            double randomNumber = random.nextDouble();
            if (randomNumber < probsOfFire) {
              fireNextRound[x][y - 1] = 1;
            }
          }
          if (x + 1 < statesOfAllCells.length && y >= 0 && statesOfAllCells[x + 1][y].getType()
              .equals(TREE)) {
            double randomNumber = random.nextDouble();
            if (randomNumber < probsOfFire) {
              fireNextRound[x + 1][y] = 1;
            }
          }
          if (x >= 0 && y + 1 < statesOfAllCells[0].length && statesOfAllCells[x][y + 1].getType()
              .equals(TREE)) {
            double randomNumber = random.nextDouble();
            if (randomNumber < probsOfFire) {
              fireNextRound[x][y + 1] = 1;
            }
          }
          emptyNextRound[x][y] = 1;
        }
      }
    }
    statesOfAllCells = setToFire(fireNextRound, statesOfAllCells, emptyNextRound);
    return statesOfAllCells;
  }


  private State[][] setToFire(int[][] fireNextRound, State[][] statesOfAllCells,
      int[][] emptyNextRound) {
    for (int i = 0; i < fireNextRound.length; i++) {
      for (int j = 0; j < fireNextRound[0].length; j++) {
        if (fireNextRound[i][j] == 1) {
          statesOfAllCells[i][j] = new State(i, j, FIRE);
          setColor(statesOfAllCells[i][j]);
        }
        if (emptyNextRound[i][j] == 1) {
          statesOfAllCells[i][j] = new State(i, j, EMPTY);
          setColor(statesOfAllCells[i][j]);
        }
      }
    }
    return statesOfAllCells;
  }

  private void setColor(State state) {
    if (state.getType().equals(FIRE)) {
      state.setColor(FIRE_COLOR);
    } else if (state.getType().equals(TREE)) {
      state.setColor(TREE_COLOR);
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