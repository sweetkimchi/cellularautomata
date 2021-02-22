package cellsociety.model.simulationrules;

import cellsociety.model.GridManager;
import cellsociety.model.State;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Purpose: This class contains the rules for the WaTor model. Rules include the types of the
 * players as well as logic to update each cell. Assumptions: xml file is correctly formatted and
 * supplies the correct information to the constructor. Dependencies: Depends on SimulationEngine to
 * declare constructors based on the parameters read from XML files. Depends on GridManager to
 * provide it with the grid to work with.
 *
 * @author Ji Yun Hyo
 */
public class WaTorModelRules extends Rules {

  private final String FISH_COLOR;
  private final String SHARK_COLOR;
  private final String EMPTY_COLOR;
  private final ArrayList<String> possibleTypes;
  private final ArrayList<String> possibleColors;
  private final String FISH = "fish";
  private final String SHARK = "shark";
  private final String EMPTY = "empty";
  private final int ENERGY_FROM_FISH;
  private final Random random;
  private final int REPRODUCE_BOUNDARY;
  private final int DEFAULT_ENERGY;

  /**
   * WaTor Model Constructor that initializes the model
   *
   * @param randomSeed        random seed to reproduce the results
   * @param energyFish        energy of the fish
   * @param reproduceBoundary number of steps needed to reproduce
   * @param sharkEnergy       energy of shark
   */
  public WaTorModelRules(long randomSeed, int energyFish,
      int reproduceBoundary, int sharkEnergy, String emptyColor, String sharkColor,
      String fishColor) {
    random = new Random(randomSeed);
    possibleTypes = new ArrayList<>();
    possibleColors = new ArrayList<>();
    ENERGY_FROM_FISH = energyFish;
    REPRODUCE_BOUNDARY = reproduceBoundary;
    DEFAULT_ENERGY = sharkEnergy;
    this.EMPTY_COLOR = emptyColor;
    this.FISH_COLOR = fishColor;
    this.SHARK_COLOR = sharkColor;
    possibleTypes.add(EMPTY);
    possibleTypes.add(SHARK);
    possibleTypes.add(FISH);
    possibleColors.add(EMPTY_COLOR);
    possibleColors.add(SHARK_COLOR);
    possibleColors.add(FISH_COLOR);
  }

  private void setColor(State state) {
    if (state.getType().equals(FISH)) {
      state.setColor(FISH_COLOR);
    } else if (state.getType().equals(SHARK)) {
      state.setColor(SHARK_COLOR);
    } else {
      state.setColor(EMPTY_COLOR);
    }
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
    if (gridManager.getTypeAtCoordinate(x, y).equals(FISH)) {
      gridManager.setStateAtCoordinate(x, y,
          decideFishState(gridManager, x, y));
    } else if (gridManager.getTypeAtCoordinate(x, y).equals(SHARK)) {
      gridManager.setStateAtCoordinate(x, y,
          decideSharkState(gridManager, x, y, gridManager.getTypeAtCoordinate(x, y)));
    }
    setColor(gridManager.getStateAtCoordinate(x, y));

  }

  private State decideFishState(GridManager gridManager, int x, int y) {
    ArrayList<State> emptyCells = new ArrayList<>();
    checkNeighboringCellsForEmptyCells(gridManager, y, emptyCells, x - 1 >= 0, y >= 0, x - 1);
    checkNeighboringCellsForEmptyCells(gridManager, y - 1, emptyCells, x >= 0, y - 1 >= 0, x);
    checkNeighboringCellsForEmptyCells(gridManager, y + 1, emptyCells, x >= 0,
        y + 1 < gridManager.getColumn(), x);
    checkNeighboringCellsForEmptyCells(gridManager, y, emptyCells, x + 1 < gridManager.getRow(),
        y >= 0, x + 1);
    //if there are spaces to move to, MOVE
    if (!emptyCells.isEmpty()) {
      //cannot reproduce yet
      if (gridManager.getStateAtCoordinate(x, y).getNumberOfMoves() < REPRODUCE_BOUNDARY) {
        int index = random.nextInt(emptyCells.size());
        State dummy = emptyCells.get(index);
        gridManager.setStateAtCoordinate(dummy.getxCoord(), dummy.getyCoord(),
            new State(dummy.getxCoord(), dummy.getyCoord(), FISH, FISH_COLOR,
                gridManager.getStateAtCoordinate(x, y).getNumberOfMoves() + 1));
        return new State(x, y, EMPTY, EMPTY_COLOR, 0, 0);
      } else {
        //reproduce
        int index = random.nextInt(emptyCells.size());
        State dummy = emptyCells.get(index);
        gridManager.setStateAtCoordinate(dummy.getxCoord(), dummy.getyCoord(),
            new State(dummy.getxCoord(), dummy.getyCoord(), FISH, FISH_COLOR, 0, 0));
        return new State(x, y, FISH, FISH_COLOR, 0, 0);
      }
    }
    return gridManager.getStateAtCoordinate(x, y);
  }

  private void checkNeighboringCellsForEmptyCells(GridManager gridManager, int y,
      ArrayList<State> emptyCells, boolean isXInBound, boolean isYInBound, int x) {
    if (isXInBound && isYInBound && gridManager.getTypeAtCoordinate(x, y).equals(EMPTY)) {
      emptyCells.add(gridManager.getStateAtCoordinate(x, y));
    }
  }

  private State decideSharkState(GridManager gridManager, int xCoord, int yCoord, String type) {
    ArrayList<State> emptyCells = new ArrayList<>();
    ArrayList<State> fishCells = new ArrayList<>();
    //if no energy, die
    if (gridManager.getStateAtCoordinate(xCoord, yCoord).getEnergy() <= 0) {
      return new State(xCoord, yCoord, EMPTY, EMPTY_COLOR, 0, 0);
    }
    checkNeighborsForFish(gridManager, yCoord, emptyCells, fishCells, xCoord - 1 >= 0,
        xCoord - 1);
    checkNeighborsForFish(gridManager, yCoord - 1, emptyCells, fishCells,
        xCoord >= 0 && yCoord - 1 >= 0, xCoord);
    checkNeighborsForFish(gridManager, yCoord, emptyCells, fishCells,
        xCoord + 1 < gridManager.getRow(), xCoord + 1);

    if (yCoord + 1 < gridManager.getColumn()) {
      //lower cell
      checkNeighborsForFishOrEmptyCell(gridManager, yCoord + 1, emptyCells, fishCells, xCoord);
    }

    //check if there are any adjacent fish cells
    if (!fishCells.isEmpty()) {
      return moveSharkToNearbyFishCell(gridManager, xCoord, yCoord, fishCells);
    }
    //if the shark is not dead, but nowhere to move just return
    return moveSharkToEmptyCell(gridManager, xCoord, yCoord, emptyCells);
  }

  private void checkNeighborsForFish(GridManager gridManager, int yCoord,
      ArrayList<State> emptyCells, ArrayList<State> fishCells, boolean xInBound, int xCoord) {
    if (xInBound) {
      //left cell
      checkNeighborsForFishOrEmptyCell(gridManager, yCoord, emptyCells, fishCells, xCoord);
    }
  }

  private void checkNeighborsForFishOrEmptyCell(GridManager gridManager, int yCoord,
      ArrayList<State> emptyCells, ArrayList<State> fishCells, int i) {
    if (gridManager.getTypeAtCoordinate(i, yCoord).equals(FISH)) {
      fishCells.add(gridManager.getStateAtCoordinate(i, yCoord));
    } else if (gridManager.getTypeAtCoordinate(i, yCoord).equals(EMPTY)) {
      emptyCells.add(gridManager.getStateAtCoordinate(i, yCoord));
    }
  }

  private State moveSharkToNearbyFishCell(GridManager gridManager, int xCoord, int yCoord,
      ArrayList<State> fishCells) {
    if (gridManager.getStateAtCoordinate(xCoord, yCoord).getNumberOfMoves() > REPRODUCE_BOUNDARY) {
      //if ready to reproduce, then reproduce after eating fish
      int index = random.nextInt(fishCells.size());
      State dummy = fishCells.get(index);
      gridManager
          .setStateAtCoordinate(dummy.getxCoord(), dummy.getyCoord(), new State(dummy.getxCoord(),
              dummy.getyCoord(), SHARK, SHARK_COLOR, 0,
              gridManager.getStateAtCoordinate(xCoord, yCoord).getEnergy() + ENERGY_FROM_FISH - 1));

      setColor(gridManager.getStateAtCoordinate(dummy.getxCoord(), dummy.getyCoord()));

      return new State(xCoord, yCoord, SHARK, SHARK_COLOR, 0, DEFAULT_ENERGY);

    } else {
      //move to a fish but don't reproduce
      int index = random.nextInt(fishCells.size());
      State dummy = fishCells.get(index);
      gridManager
          .setStateAtCoordinate(dummy.getxCoord(), dummy.getyCoord(), new State(dummy.getxCoord(),
              dummy.getyCoord(), SHARK, SHARK_COLOR,
              gridManager.getStateAtCoordinate(xCoord, yCoord).getNumberOfMoves() + 1,
              gridManager.getStateAtCoordinate(xCoord, yCoord).getEnergy() + ENERGY_FROM_FISH - 1));

      setColor(gridManager.getStateAtCoordinate(dummy.getxCoord(), dummy.getyCoord()));
      return new State(xCoord, yCoord, EMPTY, EMPTY_COLOR, 0, 0);
    }
  }

  private State moveSharkToEmptyCell(GridManager gridManager, int xCoord, int yCoord,
      ArrayList<State> emptyCells) {
    if (emptyCells.isEmpty()) {
      return new State(xCoord, yCoord, EMPTY, EMPTY_COLOR,
          gridManager.getStateAtCoordinate(xCoord, yCoord).getNumberOfMoves(),
          gridManager.getStateAtCoordinate(xCoord, yCoord).getEnergy() - 1);

    } else {
      //if the shark is not dead and there is a room to move
      int index = random.nextInt(emptyCells.size());
      State dummy = emptyCells.get(index);
      gridManager
          .setStateAtCoordinate(dummy.getxCoord(), dummy.getyCoord(), new State(dummy.getxCoord(),
              dummy.getyCoord(), SHARK, SHARK_COLOR,
              gridManager.getStateAtCoordinate(xCoord, yCoord).getNumberOfMoves() + 1,
              gridManager.getStateAtCoordinate(xCoord, yCoord).getEnergy() - 1));
      setColor(gridManager.getStateAtCoordinate(dummy.getxCoord(), dummy.getyCoord()));

      return new State(xCoord, yCoord, EMPTY, EMPTY_COLOR, 0,
          0);

    }
  }
}

