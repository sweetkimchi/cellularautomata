package cellsociety.model;

import java.util.ArrayList;
import java.util.List;
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

  private final String BLOCK_COLOR;
  private final String WATER_COLOR;
  private final String EMPTY_COLOR;
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
  public PercolationRules(long randomSeed, String blockColor, String waterColor,
      String emptyColor) {
    random = new Random(randomSeed);
    possibleTypes = new ArrayList<>();
    possibleColors = new ArrayList<>();
    EMPTY_COLOR = emptyColor;
    BLOCK_COLOR = blockColor;
    WATER_COLOR = waterColor;
    possibleTypes.add(EMPTY);
    possibleTypes.add(WATER);
    possibleTypes.add(BLOCK);
    possibleColors.add(EMPTY_COLOR);
    possibleColors.add(WATER_COLOR);
    possibleColors.add(BLOCK_COLOR);
  }

  @Override
  public List<String> getPossibleTypes() {
    return possibleTypes;
  }

  @Override
  public List<String> getPossibleColors() {
    return possibleColors;
  }

  @Override
  public void decideState(List<Integer> neighborsOfEachTypeAtCoordinate, List<int[][]> nextStates,
      List<State> updateStates, int x, int y,
      GridManager gridManager) {
    if (gridManager.getTypeAtCoordinate(x, y).equals(EMPTY)
        && neighborsOfEachTypeAtCoordinate.get(1) > 0) {
      updateStates.add(new State(x, y, WATER, WATER_COLOR, 0));
    }
  }


}