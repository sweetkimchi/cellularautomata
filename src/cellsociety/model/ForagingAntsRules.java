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
  
  public ForagingAntsRules(){
    initializeColorsAndTypes();
  }

  private void initializeColorsAndTypes() {
    possibleTypes = new ArrayList<>();
    possibleColors = new ArrayList<>();
    possibleTypes.add(EMPTY);
    possibleTypes.add(ANT);
    possibleTypes.add(FOOD);
    possibleTypes.add(NEST);
    possibleTypes.add(HORMONE);

    possibleColors.add(EMPTY_COLOR);
    possibleColors.add(ANT_COLOR);
    possibleColors.add(FOOD_COLOR);
    possibleColors.add(NEST_COLOR);
    possibleColors.add(HORMONE_COLOR);
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

  }
}
