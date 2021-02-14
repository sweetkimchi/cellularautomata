package cellsociety.model.watormodel;

import cellsociety.model.cell.State;
import cellsociety.model.rules.Rules;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 */
public class WaTorModelRules extends Rules {

  private final String FISH_COLOR = "green";
  private final String SHARK_COLOR = "blue";
  private final String EMPTY_COLOR = "lightgrey";
  private double emptyRatio;
  private double populationRatio;
  private ArrayList<String> possibleTypes;
  private ArrayList<String> possibleColors;
  private String FISH = "fish";
  private String SHARK = "shark";
  private String EMPTY = "empty";
  private int ENERGY_FROM_FISH = 3;
  private Random random;
  private int REPRODUCE_BOUNDARY = 4;
  private int DEFAULT_ENERGY = 3;

  /**
   * Default constructor
   */
  public WaTorModelRules(double emptyRatio, double populationRatio, long randomSeed, int energyFish, int reproduceBoundary, int sharkEnergy) {
    this.emptyRatio = emptyRatio;
    this.populationRatio = populationRatio;
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


  private State decideFishState(State[][] statesOfAllCells, int xCoord, int yCoord, String type) {
    ArrayList<State> emptyCells = new ArrayList<>();
    if (xCoord - 1 >= 0 && statesOfAllCells[xCoord - 1][yCoord].type.equals(EMPTY)) {
      //left cell
      emptyCells.add(statesOfAllCells[xCoord - 1][yCoord]);

    }
    if (xCoord >= 0 && yCoord - 1 >= 0 && statesOfAllCells[xCoord][yCoord - 1].type.equals(EMPTY)) {
      //upper cell
      emptyCells.add(statesOfAllCells[xCoord][yCoord - 1]);
    }
    if (yCoord + 1 < statesOfAllCells[0].length && statesOfAllCells[xCoord][yCoord + 1].type
        .equals(EMPTY)) {
      //lower cell
      emptyCells.add(statesOfAllCells[xCoord][yCoord + 1]);
    }
    if (xCoord + 1 < statesOfAllCells.length && statesOfAllCells[xCoord + 1][yCoord].type
        .equals(EMPTY)) {
      //right cell
      emptyCells.add(statesOfAllCells[xCoord + 1][yCoord]);
    }

    //if there are spaces to move to, MOVE
    if (!emptyCells.isEmpty()) {
      //cannot reproduce yet
      if (statesOfAllCells[xCoord][yCoord].numberOfMoves < REPRODUCE_BOUNDARY) {
        int index = random.nextInt(emptyCells.size());
        State dummy = emptyCells.get(index);
        //    System.out.println(index);
        statesOfAllCells[dummy.getxCoord()][dummy.getyCoord()] = new State(dummy.getxCoord(),
            dummy.getyCoord(), FISH, FISH_COLOR,
            statesOfAllCells[xCoord][yCoord].numberOfMoves + 1);
        return new State(xCoord, yCoord, EMPTY, EMPTY_COLOR, 0,0);
      } else {
        //reproduce
        int index = random.nextInt(emptyCells.size());
        State dummy = emptyCells.get(index);
        // System.out.println("REPRODUCE");
        statesOfAllCells[dummy.getxCoord()][dummy.getyCoord()] = new State(dummy.getxCoord(),
            dummy.getyCoord(), FISH, FISH_COLOR, 0,0);
        return new State(xCoord, yCoord, FISH, FISH_COLOR, 0,0);
      }
    }

    return statesOfAllCells[xCoord][yCoord];
  }

  private State decideSharkState(State[][] statesOfAllCells, int xCoord, int yCoord, String type) {
    ArrayList<State> emptyCells = new ArrayList<>();
    ArrayList<State> fishCells = new ArrayList<>();

    //if no energy, die
    if (statesOfAllCells[xCoord][yCoord].energy <= 0) {
      return new State(xCoord, yCoord, EMPTY, EMPTY_COLOR, 0,0);
    }

    //each time lose one energy
    //each time gain one move

   // System.out.println("ENERGY: " + statesOfAllCells[xCoord][yCoord].energy);

    if (xCoord - 1 >= 0) {
      //left cell
      if (statesOfAllCells[xCoord - 1][yCoord].type.equals(FISH)) {
        fishCells.add(statesOfAllCells[xCoord - 1][yCoord]);
      } else if (statesOfAllCells[xCoord - 1][yCoord].type.equals(EMPTY)) {
        emptyCells.add(statesOfAllCells[xCoord - 1][yCoord]);
      }

    }
    if (xCoord >= 0 && yCoord - 1 >= 0) {
      //upper cell
      if (statesOfAllCells[xCoord][yCoord - 1].type.equals(FISH)) {
        fishCells.add(statesOfAllCells[xCoord][yCoord - 1]);
      } else if (statesOfAllCells[xCoord][yCoord - 1].type.equals(EMPTY)) {
        emptyCells.add(statesOfAllCells[xCoord][yCoord - 1]);
      }

    }
    if (yCoord + 1 < statesOfAllCells[0].length) {
      //lower cell
      if (statesOfAllCells[xCoord][yCoord + 1].type.equals(FISH)) {
        fishCells.add(statesOfAllCells[xCoord][yCoord + 1]);
      } else if (statesOfAllCells[xCoord][yCoord + 1].type.equals(EMPTY)) {
        emptyCells.add(statesOfAllCells[xCoord][yCoord + 1]);
      }

    }
    if (xCoord + 1 < statesOfAllCells.length) {
      //right cell
      if (statesOfAllCells[xCoord + 1][yCoord].type.equals(FISH)) {
        fishCells.add(statesOfAllCells[xCoord + 1][yCoord]);
      } else if (statesOfAllCells[xCoord + 1][yCoord].type.equals(EMPTY)) {
        emptyCells.add(statesOfAllCells[xCoord + 1][yCoord]);
      }
    }

    //check if there are any adjacent fish cells
    if (!fishCells.isEmpty()) {
      if (statesOfAllCells[xCoord][yCoord].numberOfMoves > REPRODUCE_BOUNDARY) {
        //if ready to reproduce, then reproduce after eating fish
        int index = random.nextInt(fishCells.size());
        State dummy = fishCells.get(index);
        statesOfAllCells[dummy.getxCoord()][dummy.getyCoord()] = new State(dummy.getxCoord(),
            dummy.getyCoord(), SHARK, SHARK_COLOR, 0, statesOfAllCells[xCoord][yCoord].energy + ENERGY_FROM_FISH - 1);

        setColor(statesOfAllCells[dummy.getxCoord()][dummy.getyCoord()]);

        return new State(xCoord, yCoord, SHARK, SHARK_COLOR, 0, DEFAULT_ENERGY);
      } else {
        //move to a fish but don't reproduce
        int index = random.nextInt(fishCells.size());
        State dummy = fishCells.get(index);
        statesOfAllCells[dummy.getxCoord()][dummy.getyCoord()] = new State(dummy.getxCoord(),
            dummy.getyCoord(), SHARK, SHARK_COLOR, statesOfAllCells[xCoord][yCoord].numberOfMoves + 1,
            statesOfAllCells[xCoord][yCoord].energy + ENERGY_FROM_FISH - 1);

        setColor(statesOfAllCells[dummy.getxCoord()][dummy.getyCoord()]);

        return new State(xCoord, yCoord, EMPTY, EMPTY_COLOR, 0, 0);
      }
    }

    //if no adjacent fish cells, check if the shark is dead

    //if the shark is not dead, but nowhere to move just return
    if (emptyCells.isEmpty()) {
      return new State(xCoord, yCoord, EMPTY, EMPTY_COLOR, statesOfAllCells[xCoord][yCoord].numberOfMoves,
          statesOfAllCells[xCoord][yCoord].energy - 1);
    } else {
      //if the shark is not dead and there is a room to move
      int index = random.nextInt(emptyCells.size());
      State dummy = emptyCells.get(index);
      statesOfAllCells[dummy.getxCoord()][dummy.getyCoord()] = new State(dummy.getxCoord(),
          dummy.getyCoord(), SHARK, SHARK_COLOR, statesOfAllCells[xCoord][yCoord].numberOfMoves + 1, statesOfAllCells[xCoord][yCoord].energy - 1);
      setColor(statesOfAllCells[dummy.getxCoord()][dummy.getyCoord()]);

      return new State(xCoord, yCoord, EMPTY, EMPTY_COLOR, 0,
          0);
    }
  }

  public State[][] judgeStateOfEachCell(State[][] statesOfAllCells) {
    int[][] numberOfFishNeighbors = numberOfAliveNeighbors(statesOfAllCells, FISH);
    int[][] numberOfSharkNeighbors = numberOfAliveNeighbors(statesOfAllCells, SHARK);
    int[][] numberOfEmptyNeighbors = numberOfAliveNeighbors(statesOfAllCells, EMPTY);
    for (int x = 0; x < statesOfAllCells.length; x++) {
      for (int y = 0; y < statesOfAllCells[0].length; y++) {
        if (statesOfAllCells[x][y].type.equals(FISH)) {
          statesOfAllCells[x][y] = decideFishState(statesOfAllCells, x, y,statesOfAllCells[x][y].type);
        } else if (statesOfAllCells[x][y].type.equals(SHARK)) {
          statesOfAllCells[x][y] = decideSharkState(statesOfAllCells, x, y,
              statesOfAllCells[x][y].type);
        }
        setColor(statesOfAllCells[x][y]);

      }
    }
    return statesOfAllCells;
  }

  private void setColor(State state) {
    if (state.type.equals(FISH)) {
      state.setColor(FISH_COLOR);
    } else if (state.type.equals(SHARK)) {
      state.setColor(SHARK_COLOR);
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

