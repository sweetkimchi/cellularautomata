package cellsociety.model.foragingants;

import cellsociety.controller.grid.GridManager;
import cellsociety.model.cell.State;
import cellsociety.model.rules.Rules;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ForagingAntsRules extends Rules {

  private static final String NEST_COLOR = "green";
  private final String ANT_COLOR = "red";
  private final String HORMONE_COLOR = "blue";
  private final String FOOD_COLOR = "lightgrey";
  private final String EMPTY_COLOR = "black";
  private ArrayList<String> possibleTypes;
  private ArrayList<String> possibleColors;
  private String ANT = "rock";
  private String FOOD = "paper";
  private String NEST = "nest";
  private String PHERMONE = "hormone";
  private String EMPTY = "empty";
  private int numberOfAnts;
  private final Random random;
  private int numberOfSides;
  
  public ForagingAntsRules(int numberOfAnts, int randomSeed, int numberOfSides){
    this.numberOfAnts = numberOfAnts;
    random = new Random(randomSeed);
    this.numberOfSides = numberOfSides;
    initializeColorsAndTypes();
  }

  private void initializeColorsAndTypes() {
    possibleTypes = new ArrayList<>();
    possibleColors = new ArrayList<>();
    possibleTypes.add(NEST);
    possibleTypes.add(FOOD);
    possibleTypes.add(ANT);
    possibleTypes.add(PHERMONE);
    possibleTypes.add(EMPTY);


    possibleColors.add(NEST_COLOR);
    possibleColors.add(FOOD_COLOR);
    possibleColors.add(ANT_COLOR);
    possibleColors.add(HORMONE_COLOR);
    possibleColors.add(EMPTY_COLOR);
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

  @Override
  public void decideState(List<Integer> neighborsOfEachTypeAtCoordinate, List<int[][]> nextStates,
      int x, int y, GridManager gridManager) {
      if(gridManager.getTypeAtCoordinate(x,y).equals(EMPTY) && neighborsOfEachTypeAtCoordinate.get(0) > 0 && numberOfAnts > 0){
        nextStates.get(2)[x][y] = 1;
        numberOfAnts--;
      }

    if(gridManager.getTypeAtCoordinate(x,y).equals(ANT)){
      gridManager.setStateAtCoordinate(x,y, decideAntState(gridManager, x, y, nextStates, getPossibleTypes(), getPossibleColors()));
    }


  }

  private State decideAntState(GridManager gridManager, int x, int y, List<int[][]> nextStates, ArrayList<String> possibleTypes, ArrayList<String> possibleColors) {
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
        int index = random.nextInt(emptyCells.size());
        State dummy = emptyCells.get(index);
        //    System.out.println(index);
        gridManager
            .setStateAtCoordinate(dummy.getxCoord(), dummy.getyCoord(), new AntState(dummy.getxCoord(),
                dummy.getyCoord(), possibleTypes.get(2), possibleColors.get(2),
                gridManager.getStateAtCoordinate(x, y).getNumberOfMoves() + 1, false, determineDirection(dummy, x, y)));
        return new AntState(x, y, possibleTypes.get(3), possibleColors.get(3), 0);

    }
    return gridManager.getStateAtCoordinate(x, y);
  }

  //0 - up
  //
  private String determineDirection(State dummy, int x, int y) {
    if(dummy.getxCoord() > x){
      return "right";
    }else if(dummy.getxCoord() < x){
      return "left";
    }else if(dummy.getyCoord() > y){
      return "up";
    }else{
      return "down";
    }
  }
}
