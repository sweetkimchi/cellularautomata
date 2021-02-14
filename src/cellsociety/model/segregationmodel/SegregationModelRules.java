package cellsociety.model.watormodel;

import cellsociety.model.cell.State;
import cellsociety.model.rules.Rules;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 */
public class SegregationModelRules extends Rules {

  private final String AGENTX_COLOR = "red";
  private final String AGENTY_COLOR = "blue";
  private final String EMPTY_COLOR = "lightgrey";
  private ArrayList<String> possibleTypes;
  private ArrayList<String> possibleColors;
  private String AGENTX = "agentx";
  private String AGENTY = "agenty";
  private String EMPTY = "empty";
  private double THRESHHOLD;

  /**
   * Default constructor
   */
  public SegregationModelRules(double populationRatio, long randomSeed, double THRESHHOLD) {
    Random random = new Random(randomSeed);
    possibleTypes = new ArrayList<>();
    possibleColors = new ArrayList<>();
    possibleTypes.add(EMPTY);
    possibleTypes.add(AGENTY);
    possibleTypes.add(AGENTX);
    possibleColors.add(EMPTY_COLOR);
    possibleColors.add(AGENTY_COLOR);
    possibleColors.add(AGENTX_COLOR);
    this.THRESHHOLD = THRESHHOLD;
  }

  private void decideState(State[][] statesOfAllCells, String type, int[][] emptyNeighbors) {

    System.out.println(type);
    for (int x = 0; x < statesOfAllCells.length; x++) {
      for (int y = 0; y < statesOfAllCells[0].length; y++) {
        if(statesOfAllCells[x][y].type.equals(EMPTY)){
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
    int[][] numberOfAGENTXNeighbors = numberOfAliveNeighbors(statesOfAllCells, AGENTX);
    int[][] numberOfAGENTYNeighbors = numberOfAliveNeighbors(statesOfAllCells, AGENTY);

    int[][] dissatisfiedNeighbors = numberOfAliveNeighbors(statesOfAllCells, "");
    int[][] emptyNeighbors = numberOfAliveNeighbors(statesOfAllCells, "");
    for (int x = 0; x < statesOfAllCells.length; x++) {
      for (int y = 0; y < statesOfAllCells[0].length; y++) {
        int agentXNeighbor = numberOfAGENTXNeighbors[x][y];
        int agentYNeighbor = numberOfAGENTYNeighbors[x][y];
        double total = agentYNeighbor + agentXNeighbor;
        double ratio = 0;

        if (statesOfAllCells[x][y].type.equals(AGENTY) && total != 0) {
          ratio = ((double) agentYNeighbor)/total;
          if(ratio < THRESHHOLD ){
            dissatisfiedNeighbors[x][y] = 1;
          }
        }
        if (statesOfAllCells[x][y].type.equals(AGENTX) && total != 0) {
      //    System.out.println(ratio);
          ratio = ((double) agentXNeighbor)/total;
          if(ratio < THRESHHOLD){

            dissatisfiedNeighbors[x][y] = 2;
          }
        }
        if (statesOfAllCells[x][y].type.equals(EMPTY)){
          emptyNeighbors[x][y] = 1;
        }

      }
    }
    statesOfAllCells = relocateDissatisfiedNeighbors(dissatisfiedNeighbors,statesOfAllCells,emptyNeighbors);
    printGrid(dissatisfiedNeighbors);
    return statesOfAllCells;
  }


  private State[][] relocateDissatisfiedNeighbors(int[][] dissatisfiedNeighbors, State[][] statesOfAllCells, int[][] emptyNeighbors){
    for(int i = 0; i < dissatisfiedNeighbors.length; i++){
      for(int j = 0; j < dissatisfiedNeighbors[0].length; j++){
        if(dissatisfiedNeighbors[i][j] == 1){
          decideState(statesOfAllCells, AGENTY, emptyNeighbors);
          statesOfAllCells[i][j] = new State(i,j,EMPTY);
          setColor(statesOfAllCells[i][j]);
        }
        if(dissatisfiedNeighbors[i][j] == 2){
          decideState(statesOfAllCells,AGENTX, emptyNeighbors);
          statesOfAllCells[i][j] = new State(i,j,EMPTY);
          setColor(statesOfAllCells[i][j]);
        }
      }
    }
    return statesOfAllCells;
  }

  private void setColor(State state) {
    if (state.type.equals(AGENTX)) {
      state.setColor(AGENTX_COLOR);
    } else if (state.type.equals(AGENTY)) {
      state.setColor(AGENTY_COLOR);
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