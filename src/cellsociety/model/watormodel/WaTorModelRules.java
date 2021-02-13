package cellsociety.model.watormodel;

    import cellsociety.model.cell.State;
    import cellsociety.model.rules.Rules;
    import java.awt.Paint;
    import java.lang.reflect.Array;
    import java.util.ArrayList;
    import java.util.Random;

/**
 *
 */
public class WaTorModelRules extends Rules {

  private double emptyRatio;
  private double populationRatio;
  private ArrayList<String> possibleTypes;
  private String FISH = "fish";
  private String SHARK = "shark";
  private String EMPTY = "empty";
  private final String FISH_COLOR = "grey";
  private final String SHARK_COLOR = "green";
  private final String EMPTY_COLOR = "blue";
  private int ENERGY_FROM_FISH = 4;
  private long randomSeed;
  /**
   * Default constructor
   */
  public WaTorModelRules(double emptyRatio, double populationRatio, long randomSeed) {
    this.emptyRatio = emptyRatio;
    this.populationRatio = populationRatio;
    this.randomSeed = randomSeed;
    possibleTypes = new ArrayList<>();
    possibleTypes.add(EMPTY);
    possibleTypes.add(SHARK);
    possibleTypes.add(FISH);
  }

  private State checkIfFishAndMove(State[][] statesOfAllCells, int xCoord, int yCoord, int newX, int newY){
      statesOfAllCells[newX][newY] = statesOfAllCells[xCoord][yCoord];
      statesOfAllCells[newX][newY].numberOfMoves += ENERGY_FROM_FISH;
      return new State(xCoord,yCoord, EMPTY);
  }

  private State decideState(State[][] statesOfAllCells, int xCoord, int yCoord, String type) {

    if(type.equals(SHARK)){
      if(xCoord - 1 >= 0){
        //left cell
        //if fish, move to the new coordinate, destry the fish
        if(statesOfAllCells[xCoord-1][yCoord].type.equals(FISH)){
          checkIfFishAndMove(statesOfAllCells, xCoord, yCoord, xCoord-1,yCoord);
        }

      }else if(xCoord >= 0 && yCoord - 1 >= 0){
        //upper cell
        if(statesOfAllCells[xCoord][yCoord-1].type.equals(FISH)){
          checkIfFishAndMove(statesOfAllCells, xCoord, yCoord, xCoord,yCoord-1);
        }

      } else if (yCoord + 1 < statesOfAllCells[0].length) {
        //lower cell
        if(statesOfAllCells[xCoord][yCoord + 1].type.equals(FISH)){
          checkIfFishAndMove(statesOfAllCells, xCoord, yCoord, xCoord,yCoord+1);
        }

      }else if(xCoord + 1 < statesOfAllCells.length){
        //right cell
        if(statesOfAllCells[xCoord+1][yCoord].type.equals(FISH)){
          checkIfFishAndMove(statesOfAllCells, xCoord, yCoord, xCoord+1,yCoord);
        }

      }
    } else if(type.equals(FISH)){
      if(xCoord - 1 >= 0){
        //left cell

      }else if(xCoord >= 0 && yCoord - 1 >= 0){
        //upper cell

      } else if (yCoord + 1 < statesOfAllCells[0].length) {
        //lower cell

      }else if(xCoord + 1 < statesOfAllCells.length){
        //right cell

      }
    }

    return statesOfAllCells[xCoord][yCoord];
  }

  @Override
  public State[][] judgeStateOfEachCell(State[][] statesOfAllCells) {
    int[][] numberOfFishNeighbors = numberOfAliveNeighbors(statesOfAllCells, FISH);
    int[][] numberOfSharkNeighbors = numberOfAliveNeighbors(statesOfAllCells, SHARK);
    int[][] numberOfEmptyNeighbors = numberOfAliveNeighbors(statesOfAllCells, EMPTY);
    for (int x = 0; x < statesOfAllCells.length; x++) {
      for (int y = 0; y < statesOfAllCells[0].length; y++) {
        if(!statesOfAllCells[x][y].type.equals(EMPTY)){
          statesOfAllCells[x][y] = decideState(statesOfAllCells, x, y, statesOfAllCells[x][y].type);
        }
        setColor(statesOfAllCells[x][y]);

      }
    }
    return statesOfAllCells;
  }

  private void setColor(State state){
    if (state.type.equals(FISH)) {
      state.setColor(FISH_COLOR);
    } else if(state.type.equals(SHARK)){
      state.setColor(SHARK_COLOR);
    } else{
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

}