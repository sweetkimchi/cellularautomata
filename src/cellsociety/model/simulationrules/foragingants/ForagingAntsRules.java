package cellsociety.model.simulationrules.foragingants;

import cellsociety.model.GridManager;
import cellsociety.model.State;
import cellsociety.model.simulationrules.Rules;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Purpose: This class contains the rules for the Foraging ant model. Rules include the types of the
 * players as well as logic to update each cell. Assumptions: xml file is correctly formatted and
 * supplies the correct information to the constructor. Dependencies: Depends on SimulationEngine to
 * declare constructors based on the parameters read from XML files. Depends on GridManager to
 * provide it with the grid to work with.
 *
 * @author Ji Yun Hyo
 */
public class ForagingAntsRules extends Rules {

  private final Random random;
  private String NEST_COLOR;
  private String ANT_COLOR;
  private String PHERMONE_COLOR;
  private String FOOD_COLOR;
  private String EMPTY_COLOR;
  private String WEAK_PHERMONE_COLOR;
  private ArrayList<String> possibleTypes;
  private ArrayList<String> possibleColors;
  private String ANT = "ant";
  private String FOOD = "food";
  private String NEST = "nest";
  private String PHEROMONE = "pheromone";
  private String EMPTY = "empty";
  private int numberOfAnts;
  private int numberOfSides;
  private int pheromoneAmount;
  private double moveBias;

  /**
   * Foraging Ant constructor with all necessary constructors. Assumption is that all parameters
   * are valid. Depends on the Rules abstract class
   * @param numberOfAnts
   * @param randomSeed
   * @param numberOfSides number of sides to be checked for neighbors.
   * @param nestColor
   * @param antColor
   * @param phermoneColor
   * @param foodColor
   * @param emptyColor
   * @param weakPhermoneColor
   * @param moveBias
   * @param pheromoneAmount the strength of the pheromone emitted by the ants
   */
  public ForagingAntsRules(int numberOfAnts, int randomSeed, int numberOfSides, String nestColor,
      String antColor, String phermoneColor, String foodColor, String emptyColor,
      String weakPhermoneColor, double moveBias, int pheromoneAmount) {
    this.numberOfAnts = numberOfAnts;
    random = new Random(randomSeed);
    this.pheromoneAmount = pheromoneAmount;
    this.moveBias = moveBias;
    this.numberOfSides = numberOfSides;
    this.NEST_COLOR = nestColor;
    this.ANT_COLOR = antColor;
    this.PHERMONE_COLOR = phermoneColor;
    this.FOOD_COLOR = foodColor;
    this.EMPTY_COLOR = emptyColor;
    this.WEAK_PHERMONE_COLOR = weakPhermoneColor;
    initializeColorsAndTypes();
  }

  private void initializeColorsAndTypes() {
    possibleTypes = new ArrayList<>();
    possibleColors = new ArrayList<>();
    possibleTypes.add(NEST);
    possibleTypes.add(FOOD);
    possibleTypes.add(ANT);
    possibleTypes.add(PHEROMONE);
    possibleTypes.add(EMPTY);

    possibleColors.add(NEST_COLOR);
    possibleColors.add(FOOD_COLOR);
    possibleColors.add(ANT_COLOR);
    possibleColors.add(PHERMONE_COLOR);
    possibleColors.add(EMPTY_COLOR);
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
  public void decideState(List<Integer> neighborsOfEachTypeAtTheCurrentLocation,
      List<int[][]> markStateForFurtherAnalysis,
      List<State> updateStates, int x, int y,
      GridManager gridManager) {
    if (gridManager.getTypeAtCoordinate(x, y).equals(EMPTY)
        && neighborsOfEachTypeAtTheCurrentLocation.get(0) > 0 && numberOfAnts > 0) {
      //      markStateForFurtherAnalysis.get(2)[x][y] = 1;
      updateStates.add(new AntState(x, y, ANT, ANT_COLOR, 0, false,
          determineDirection(gridManager.getStateAtCoordinate(x, y), x, y)));
      numberOfAnts--;
    }

    if (gridManager.getTypeAtCoordinate(x, y).equals(ANT)) {
      gridManager.setStateAtCoordinate(x, y, decideAntState(gridManager, x, y, markStateForFurtherAnalysis, getPossibleTypes(), getPossibleColors()));
    }

    if (gridManager.getTypeAtCoordinate(x, y).equals(PHEROMONE)) {
      gridManager.setStateAtCoordinate(x, y, decidePhermoneState(gridManager, x, y));
    }
  }

  private State decidePhermoneState(GridManager gridManager, int x, int y) {
    if (gridManager.getStateAtCoordinate(x, y).getEnergy() <= 0) {
      return new AntState(x, y, EMPTY, EMPTY_COLOR, 0, 0);
    } else {
      ArrayList<State> emptyCells = new ArrayList<>();
      checkForNeighbors(gridManager, x, y, emptyCells, EMPTY);
      while (!emptyCells.isEmpty()) {
        //cannot reproduce yet
        int index = random.nextInt(emptyCells.size());
        State dummy = emptyCells.get(index);
        //    System.out.println(index);
        gridManager
            .setStateAtCoordinate(dummy.getxCoord(), dummy.getyCoord(),
                new AntState(dummy.getxCoord(),
                    dummy.getyCoord(), PHEROMONE, WEAK_PHERMONE_COLOR,
                    0, gridManager.getStateAtCoordinate(x, y).getEnergy() - pheromoneAmount / 2));
        emptyCells.remove(index);
      }
      return new AntState(x, y,
          PHEROMONE, PHERMONE_COLOR, 0, gridManager.getStateAtCoordinate(x, y).getEnergy() - 1);
    }
  }

  private State decideAntState(GridManager gridManager, int x, int y, List<int[][]> nextStates,
      ArrayList<String> possibleTypes, ArrayList<String> possibleColors) {
    ArrayList<State> emptyCells = new ArrayList<>();
    ArrayList<State> foodCells = new ArrayList<>();
    ArrayList<State> phermoneCells = new ArrayList<>();
    ArrayList<State> nestCells = new ArrayList<>();

    if (!gridManager.getStateAtCoordinate(x, y).hasFood()) {

      checkForNeighbors(gridManager, x, y, nestCells, NEST);
      checkForNeighbors(gridManager, x, y, foodCells, FOOD);
      checkForNeighbors(gridManager, x, y, emptyCells, EMPTY);
      checkForNeighbors(gridManager, x, y, phermoneCells, PHEROMONE);
      if (!foodCells.isEmpty()) {
        int index = random.nextInt(foodCells.size());
        State dummy = foodCells.get(index);

        //if there is still food left, get the food
        if (dummy.getEnergy() > 0) {
          gridManager
              .setStateAtCoordinate(dummy.getxCoord(), dummy.getyCoord(),
                  new AntState(dummy.getxCoord(),
                      dummy.getyCoord(), FOOD, FOOD_COLOR, 0, dummy.getEnergy() - 1));
          return new AntState(x, y, ANT, ANT_COLOR, 0, true, "up");
        } else {
          gridManager
              .setStateAtCoordinate(dummy.getxCoord(), dummy.getyCoord(),
                  new AntState(dummy.getxCoord(),
                      dummy.getyCoord(), EMPTY, EMPTY_COLOR, 0, 0));
          return new AntState(x, y, ANT, ANT_COLOR, 0, true, "up");
        }
      }
      //if there is phermone follow the phermone
      if (!phermoneCells.isEmpty() && !emptyCells.isEmpty()) {
        State dummy = null;
        for (State phermone : phermoneCells) {
          if (phermone.getColor().equals(WEAK_PHERMONE_COLOR)) {
            dummy = phermone;
            break;
          }
        }
        if (dummy == null) {
          int index = random.nextInt(emptyCells.size());
          dummy = emptyCells.get(index);
        }
        gridManager
            .setStateAtCoordinate(dummy.getxCoord(), dummy.getyCoord(),
                new AntState(dummy.getxCoord(),
                    dummy.getyCoord(), ANT, ANT_COLOR,
                    gridManager.getStateAtCoordinate(x, y).getNumberOfMoves() + 1, false,
                    determineDirection(dummy, x, y)));
        //if the phermone doesn't have enough health, return empty
        return new AntState(x, y, PHEROMONE, PHERMONE_COLOR, 0,
            gridManager.getStateAtCoordinate(dummy.getxCoord(), dummy.getyCoord()).getEnergy());

      }
      //if there are spaces to move to, MOVE
      if (!emptyCells.isEmpty()) {
        //cannot reproduce yet
        int index = random.nextInt(emptyCells.size());
        State dummy = emptyCells.get(index);
        gridManager
            .setStateAtCoordinate(dummy.getxCoord(), dummy.getyCoord(),
                new AntState(dummy.getxCoord(),
                    dummy.getyCoord(), ANT, ANT_COLOR,
                    0, false, determineDirection(dummy, x, y)));
        return new AntState(x, y, EMPTY, EMPTY_COLOR, 0);

      }
    } else {
      //if ant has food
      moveTowardsNest(gridManager, x, y, nestCells, NEST);
      moveTowardsNest(gridManager, x, y, foodCells, FOOD);
      moveTowardsNest(gridManager, x, y, emptyCells, EMPTY);
      moveTowardsNest(gridManager, x, y, phermoneCells, PHEROMONE);
      if (!nestCells.isEmpty()) {
        int index = random.nextInt(nestCells.size());
        State dummy = nestCells.get(index);
        return new AntState(dummy.getxCoord(),
            dummy.getyCoord(), ANT, ANT_COLOR,
            0, false, determineDirection(dummy, x, y));
      }
      if (!emptyCells.isEmpty()) {
        //cannot reproduce yet
        int index = random.nextInt(emptyCells.size());
        State dummy = emptyCells.get(index);
        gridManager
            .setStateAtCoordinate(dummy.getxCoord(), dummy.getyCoord(),
                new AntState(dummy.getxCoord(),
                    dummy.getyCoord(), ANT, ANT_COLOR,
                    0, true, determineDirection(dummy, x, y)));
        return new AntState(x, y, PHEROMONE, PHERMONE_COLOR, 0, pheromoneAmount);


      }
      emptyCells = new ArrayList<>();
      checkForNeighbors(gridManager, x, y, emptyCells, EMPTY);
      if (!emptyCells.isEmpty()) {
        //cannot reproduce yet
        int index = random.nextInt(emptyCells.size());
        State dummy = emptyCells.get(index);
        gridManager
            .setStateAtCoordinate(dummy.getxCoord(), dummy.getyCoord(),
                new AntState(dummy.getxCoord(),
                    dummy.getyCoord(), ANT, ANT_COLOR,
                    0, true, determineDirection(dummy, x, y)));
        return new AntState(x, y, PHEROMONE, PHERMONE_COLOR, 0, pheromoneAmount);


      }
    }
    return gridManager.getStateAtCoordinate(x, y);
  }

  private void moveTowardsNest(GridManager gridManager, int x, int y, ArrayList<State> cells,
      String type) {
    int xNest = Integer.parseInt(gridManager.getNestCoordinates().get(0));
    int yNest = Integer.parseInt(gridManager.getNestCoordinates().get(1));
    int distance = manhattanDistance(xNest, yNest, x, y);
    if (x - 1 >= 0 && gridManager.getTypeAtCoordinate(x - 1, y).equals(type)
        && manhattanDistance(xNest, yNest, x - 1, y) < distance) {
      //left cell
      cells.add(gridManager.getStateAtCoordinate(x - 1, y));
    }
    if (x >= 0 && y - 1 >= 0 && gridManager.getTypeAtCoordinate(x, y - 1).equals(type)
        && manhattanDistance(xNest, yNest, x, y - 1) < distance) {
      //upper cell
      cells.add(gridManager.getStateAtCoordinate(x, y - 1));
    }
    if (y + 1 < gridManager.getColumn() && gridManager.getTypeAtCoordinate(x, y + 1)
        .equals(type) && manhattanDistance(xNest, yNest, x, y + 1) < distance) {
      //lower cell
      cells.add(gridManager.getStateAtCoordinate(x, y + 1));
    }
    if (x + 1 < gridManager.getRow() && gridManager.getTypeAtCoordinate(x + 1, y)
        .equals(type) && manhattanDistance(xNest, yNest, x + 1, y) < distance) {
      //right cell
      cells.add(gridManager.getStateAtCoordinate(x + 1, y));
    }
  }

  private int manhattanDistance(int xNest, int yNest, int x, int y) {
    return Math.abs(x - xNest) + Math.abs(y - yNest);
  }

  private void checkForNeighbors(GridManager gridManager, int x, int y, ArrayList<State> emptyCells,
      String type) {
    double probability = random.nextDouble();
    if (x - 1 >= 0 && gridManager.getTypeAtCoordinate(x - 1, y).equals(type)
        && probability < moveBias) {
      //left cell
      emptyCells.add(gridManager.getStateAtCoordinate(x - 1, y));
    }
    if (x >= 0 && y - 1 >= 0 && gridManager.getTypeAtCoordinate(x, y - 1).equals(type)
        && probability < moveBias) {
      //upper cell
      emptyCells.add(gridManager.getStateAtCoordinate(x, y - 1));
    }
    if (y + 1 < gridManager.getColumn() && gridManager.getTypeAtCoordinate(x, y + 1)
        .equals(type)) {
      //lower cell
      emptyCells.add(gridManager.getStateAtCoordinate(x, y + 1));
    }
    if (x + 1 < gridManager.getRow() && gridManager.getTypeAtCoordinate(x + 1, y)
        .equals(type)) {
      //right cell
      emptyCells.add(gridManager.getStateAtCoordinate(x + 1, y));
    }
  }

  //0 - up
  //
  private String determineDirection(State dummy, int x, int y) {
    if (dummy.getxCoord() > x) {
      return "right";
    } else if (dummy.getxCoord() < x) {
      return "left";
    } else if (dummy.getyCoord() > y) {
      return "up";
    } else {
      return "down";
    }
  }
}
