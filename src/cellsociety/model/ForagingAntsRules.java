package cellsociety.model;

import cellsociety.controller.grid.GridManager;
import cellsociety.model.rules.Rules;
import java.util.ArrayList;
import java.util.List;

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
  private String HORMONE = "hormone";
  private String EMPTY = "empty";
  private int numberOfAnts;
  
  public ForagingAntsRules(int numberOfAnts){
    this.numberOfAnts = numberOfAnts;
    initializeColorsAndTypes();
  }

  private void initializeColorsAndTypes() {
    possibleTypes = new ArrayList<>();
    possibleColors = new ArrayList<>();
    possibleTypes.add(NEST);
    possibleTypes.add(FOOD);
    possibleTypes.add(ANT);
    possibleTypes.add(HORMONE);
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

    if(gridManager.getTypeAtCoordinate(x,y).equals(EMPTY) && neighborsOfEachTypeAtCoordinate.get(2) > 0){
      nextStates.get(2)[x][y] = 1;
    }

      if(gridManager.getTypeAtCoordinate(x,y).equals(ANT) && neighborsOfEachTypeAtCoordinate.get(4) > 0){
        nextStates.get(4)[x][y] = 1;
      }
  }
}
