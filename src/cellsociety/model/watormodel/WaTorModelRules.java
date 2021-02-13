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
  private final String FISH_COLOR = "red";
  private final String SHARK_COLOR = "green";
  private final String EMPTY_COLOR = "blue";
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

  private State decideState(State[][] statesOfAllCells, int xCoord, int yCoord, String type) {
    ArrayList<State> possibilities = new ArrayList<>();

    Random random = new Random(randomSeed);
    if(type.equals(SHARK)){
      if(xCoord - 1 >= 0){
        //left cell

      }else if(xCoord >= 0 && yCoord - 1 >= 0){
        //upper cell

      } else if (yCoord + 1 < statesOfAllCells[0].length) {
        //lower cell

      }else if(xCoord + 1 < statesOfAllCells.length){
        //right cell

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
    random.nextInt(possibilities.size());
    statesOfAllCells
    return ;
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