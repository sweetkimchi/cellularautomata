package cellsociety.model.watormodel;

import cellsociety.controller.grid.GridManager;
import cellsociety.model.cell.State;
import cellsociety.model.rules.Rules;
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

  private final String FISH_COLOR = "green";
  private final String SHARK_COLOR = "blue";
  private final String EMPTY_COLOR = "lightgrey";
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
      int reproduceBoundary, int sharkEnergy) {
    random = new Random(randomSeed);
    possibleTypes = new ArrayList<>();
    possibleColors = new ArrayList<>();
    ENERGY_FROM_FISH = energyFish;
    REPRODUCE_BOUNDARY = reproduceBoundary;
    DEFAULT_ENERGY = sharkEnergy;
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


  /**
   * specifies the starting states of the cells according to the simulation rule
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

  @Override
  public void decideState(List<Integer> neighborsOfEachTypeAtCoordinate, List<int[][]> nextStates,
      int x, int y, GridManager gridManager) {
    if (gridManager.getTypeAtCoordinate(x, y).equals(FISH)) {
      gridManager.setStateAtCoordinate(x, y,
          decideFishState(gridManager, x, y, gridManager.getTypeAtCoordinate(x, y), nextStates));
    } else if (gridManager.getTypeAtCoordinate(x, y).equals(SHARK)) {
      gridManager.setStateAtCoordinate(x, y,
          decideSharkState(gridManager, x, y, gridManager.getTypeAtCoordinate(x, y)));
    }
    setColor(gridManager.getStateAtCoordinate(x, y));

  }

  private State decideFishState(GridManager gridManager, int x, int y, String typeAtCoordinate,
      List<int[][]> nextStates) {
    ArrayList<State> emptyCells = new ArrayList<>();
    if (x - 1 >= 0 && gridManager.getTypeAtCoordinate(x - 1, y).equals(EMPTY)) {
      //left cell
      emptyCells.add(gridManager.getStateAtCoordinate(x - 1, y));
    }
    if (x >= 0 && y - 1 >= 0 && gridManager.getTypeAtCoordinate(x, y - 1).equals(EMPTY)) {
      //upper cell
      emptyCells.add(gridManager.getStateAtCoordinate(x, y - 1));
    }
    if (y + 1 < gridManager.getColumn() && gridManager.getTypeAtCoordinate(x, y + 1)
        .equals(EMPTY)) {
      //lower cell
      emptyCells.add(gridManager.getStateAtCoordinate(x, y + 1));
    }
    if (x + 1 < gridManager.getRow() && gridManager.getTypeAtCoordinate(x + 1, y)
        .equals(EMPTY)) {
      //right cell
      emptyCells.add(gridManager.getStateAtCoordinate(x + 1, y));
    }

    //if there are spaces to move to, MOVE
    if (!emptyCells.isEmpty()) {
      //cannot reproduce yet
      if (gridManager.getStateAtCoordinate(x, y).getNumberOfMoves() < REPRODUCE_BOUNDARY) {
        int index = random.nextInt(emptyCells.size());
        State dummy = emptyCells.get(index);
        //    System.out.println(index);
        gridManager
            .setStateAtCoordinate(dummy.getxCoord(), dummy.getyCoord(), new State(dummy.getxCoord(),
                dummy.getyCoord(), FISH, FISH_COLOR,
                gridManager.getStateAtCoordinate(x, y).getNumberOfMoves() + 1));
        return new State(x, y, EMPTY, EMPTY_COLOR, 0, 0);
      } else {
        //reproduce
        int index = random.nextInt(emptyCells.size());
        State dummy = emptyCells.get(index);
        // System.out.println("REPRODUCE");
        gridManager
            .setStateAtCoordinate(dummy.getxCoord(), dummy.getyCoord(), new State(dummy.getxCoord(),
                dummy.getyCoord(), FISH, FISH_COLOR, 0, 0));
        return new State(x, y, FISH, FISH_COLOR, 0, 0);
      }
    }
    return gridManager.getStateAtCoordinate(x, y);
  }

  private State decideSharkState(GridManager gridManager, int xCoord, int yCoord, String type) {
    ArrayList<State> emptyCells = new ArrayList<>();
    ArrayList<State> fishCells = new ArrayList<>();

    //if no energy, die
    if (gridManager.getStateAtCoordinate(xCoord, yCoord).getEnergy() <= 0) {
      return new State(xCoord, yCoord, EMPTY, EMPTY_COLOR, 0, 0);
    }

    checkOneNeighborForFish(gridManager, yCoord, emptyCells, fishCells, xCoord - 1 >= 0,
        xCoord - 1);
    checkOneNeighborForFish(gridManager, yCoord - 1, emptyCells, fishCells,
        xCoord >= 0 && yCoord - 1 >= 0, xCoord);

    if (yCoord + 1 < gridManager.getColumn()) {
      //lower cell
      checkNeighborsForFishOrEmptyCell(gridManager, yCoord + 1, emptyCells, fishCells, xCoord);
    }
    checkOneNeighborForFish(gridManager, yCoord, emptyCells, fishCells,
        xCoord + 1 < gridManager.getRow(), xCoord + 1);

    //check if there are any adjacent fish cells
    if (!fishCells.isEmpty()) {
      return moveSharkToNearbyFishCell(gridManager, xCoord, yCoord, fishCells);
    }

    //if the shark is not dead, but nowhere to move just return
    return tryToMoveSharkToEmptyCell(gridManager, xCoord, yCoord, emptyCells);
  }

  private void checkOneNeighborForFish(GridManager gridManager, int yCoord,
      ArrayList<State> emptyCells, ArrayList<State> fishCells, boolean b, int i) {
    if (b) {
      //left cell
      checkNeighborsForFishOrEmptyCell(gridManager, yCoord, emptyCells, fishCells, i);
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

  private State tryToMoveSharkToEmptyCell(GridManager gridManager, int xCoord, int yCoord,
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

