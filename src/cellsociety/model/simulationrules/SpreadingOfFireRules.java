package cellsociety.model.simulationrules;

import cellsociety.model.GridManager;
import cellsociety.model.State;
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
    if (gridManager.getTypeAtCoordinate(x, y).equals(FIRE)) {
      updateStates.add(new State(x, y, EMPTY, EMPTY_COLOR, 0));
    }
    if (gridManager.getTypeAtCoordinate(x, y).equals(TREE)
        && neighborsOfEachTypeAtTheCurrentLocation.get(2) > 0) {
      double randomNumber = random.nextDouble();
      if (randomNumber < probsOfFire) {
        updateStates.add(new State(x, y, FIRE, FIRE_COLOR, 0));
      }
    }
  }
}