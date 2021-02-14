package cellsociety.model.percolation;

import cellsociety.model.cell.State;
import cellsociety.model.rules.Rules;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 */
public class PercolationRules extends Rules {

  private final String BLOCK_COLOR = "black";
  private final String WATER_COLOR = "blue";
  private final String EMPTY_COLOR = "lightgrey";
  private ArrayList<String> possibleTypes;
  private ArrayList<String> possibleColors;
  private String BLOCK = "block";
  private String WATER = "water";
  private String EMPTY = "empty";
  private double probsOfFire;
  private Random random;

  /**
   * Default constructor
   */
  public PercolationRules(long randomSeed) {
    random = new Random(randomSeed);
    possibleTypes = new ArrayList<>();
    possibleColors = new ArrayList<>();
    possibleTypes.add(EMPTY);
    possibleTypes.add(WATER);
    possibleTypes.add(BLOCK);
    possibleColors.add(EMPTY_COLOR);
    possibleColors.add(WATER_COLOR);
    possibleColors.add(BLOCK_COLOR);
  }

  private void decideState(State[][] statesOfAllCells, String type, int[][] emptyNeighbors) {

    System.out.println(type);
    for (int x = 0; x < statesOfAllCells.length; x++) {
      for (int y = 0; y < statesOfAllCells[0].length; y++) {
        if(statesOfAllCells[x][y].type.equals(EMPTY) && emptyNeighbors[x][y] == 1){
          statesOfAllCells[x][y] = new State(x,y,type);
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



  public State[][] judgeStateOfEachCell(State[][] statesOfAllCells) {
    int[][] numberOfFireNeighbors = numberOfAliveNeighbors(statesOfAllCells, BLOCK);
    int[][] numberOfTreeNeighbors = numberOfAliveNeighbors(statesOfAllCells, WATER);
    int[][] dissatisfiedNeighbors = numberOfAliveNeighbors(statesOfAllCells, "");
    int[][] fireNextRound = numberOfAliveNeighbors(statesOfAllCells, "");
    int[][] emptyNextRound = numberOfAliveNeighbors(statesOfAllCells, "");
    for (int x = 0; x < statesOfAllCells.length; x++) {
      for (int y = 0; y < statesOfAllCells[0].length; y++) {
        if(statesOfAllCells[x][y].type.equals(BLOCK)){
          if(x - 1 >= 0 && y >= 0 && statesOfAllCells[x-1][y].type.equals(WATER)){
            double randomNumber = random.nextDouble();
            if(randomNumber < probsOfFire){
              fireNextRound[x - 1][y] = 1;
            }
          }
          if(x >= 0 && y - 1 >= 0 && statesOfAllCells[x][y-1].type.equals(WATER)){
            double randomNumber = random.nextDouble();
            if(randomNumber < probsOfFire){
              fireNextRound[x][y - 1] = 1;
            }
          }
          if(x + 1 < statesOfAllCells.length && y>= 0 && statesOfAllCells[x+1][y].type.equals(WATER)){
            double randomNumber = random.nextDouble();
            if(randomNumber < probsOfFire){
              fireNextRound[x + 1][y] = 1;
            }
          }
          if(x >= 0 && y +1 < statesOfAllCells[0].length && statesOfAllCells[x][y+1].type.equals(
              WATER)){
            double randomNumber = random.nextDouble();
            if(randomNumber < probsOfFire){
              fireNextRound[x][y + 1] = 1;
            }
          }
          emptyNextRound[x][y] = 1;
        }
      }
    }
 //   statesOfAllCells = setToFire(fireNextRound,statesOfAllCells, emptyNextRound);
    printGrid(fireNextRound);
    return statesOfAllCells;
  }


  private State[][] setToFire(int[][] fireNextRound, State[][] statesOfAllCells,  int[][] emptyNextRound){
    for(int i = 0; i < fireNextRound.length; i++){
      for(int j = 0; j < fireNextRound[0].length; j++){
        if(fireNextRound[i][j] == 1){
          statesOfAllCells[i][j] = new State(i,j, BLOCK);
          setColor(statesOfAllCells[i][j]);
        }
        if(emptyNextRound[i][j] == 1){
          statesOfAllCells[i][j] = new State(i,j,EMPTY);
          setColor(statesOfAllCells[i][j]);
        }
      }
    }
    return statesOfAllCells;
  }

  private void setColor(State state) {
    if (state.type.equals(BLOCK)) {
      state.setColor(BLOCK_COLOR);
    } else if (state.type.equals(WATER)) {
      state.setColor(WATER_COLOR);
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