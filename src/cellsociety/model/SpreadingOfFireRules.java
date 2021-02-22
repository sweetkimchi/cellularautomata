package cellsociety.model;

import java.util.ArrayList;
import java.util.List;
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
  public SpreadingOfFireRules(long randomSeed, double probsOfFire, String emptyColor,
      String treeColor, String fireColor) {
    random = new Random(randomSeed);
    possibleTypes = new ArrayList<>();
    possibleColors = new ArrayList<>();
    possibleTypes.add(EMPTY);
    possibleTypes.add(TREE);
    possibleTypes.add(FIRE);
    possibleColors.add(emptyColor);
    possibleColors.add(treeColor);
    possibleColors.add(fireColor);
    this.probsOfFire = probsOfFire;
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
    if (gridManager.getTypeAtCoordinate(x, y).equals(FIRE)) {
      updateStates.add(new State(x, y, EMPTY, EMPTY_COLOR, 0));
    }
    if (gridManager.getTypeAtCoordinate(x, y).equals(TREE)
        && neighborsOfEachTypeAtCoordinate.get(2) > 0) {
      double randomNumber = random.nextDouble();
      if (randomNumber < probsOfFire) {
        updateStates.add(new State(x, y, FIRE, FIRE_COLOR, 0));
      }
    }
  }
}