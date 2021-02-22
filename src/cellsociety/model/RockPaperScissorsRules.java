package cellsociety.model;

import cellsociety.controller.grid.GridManager;
import cellsociety.model.rules.Rules;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RockPaperScissorsRules extends Rules {

  private String ROCK_COLOR;
  private String PAPER_COLOR;
  private String SCISSORS_COLOR;
  private String EMPTY_COLOR;
  private ArrayList<String> possibleTypes;
  private ArrayList<String> possibleColors;
  private String ROCK = "rock";
  private String PAPER = "paper";
  private String SCISSORS = "scissors";
  private String EMPTY = "empty";
  private double THRESHHOLD;
  private Random random;

  public RockPaperScissorsRules() {
    initializeColorsAndTypes();
  }

  public RockPaperScissorsRules(int threshold, long randomSeed, Map<String, String> colors) {
    THRESHHOLD = threshold;
    ROCK_COLOR = colors.get("rock");
    PAPER_COLOR = colors.get("paper");
    SCISSORS_COLOR = colors.get("scissors");
    EMPTY_COLOR = colors.get("empty");
    System.out.println(THRESHHOLD);
    random = new Random(randomSeed);
    initializeColorsAndTypes();
  }

  private void initializeColorsAndTypes() {
    possibleTypes = new ArrayList<>();
    possibleColors = new ArrayList<>();
    possibleTypes.add(EMPTY);
    possibleTypes.add(ROCK);
    possibleTypes.add(PAPER);
    possibleTypes.add(SCISSORS);
    possibleColors.add(EMPTY_COLOR);
    possibleColors.add(ROCK_COLOR);
    possibleColors.add(PAPER_COLOR);
    possibleColors.add(SCISSORS_COLOR);
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

    System.out.println(THRESHHOLD);

    if(gridManager.getTypeAtCoordinate(x,y).equals(ROCK) && neighborsOfEachTypeAtCoordinate.get(2) > THRESHHOLD){
      nextStates.get(2)[x][y] = 1;
    }
    if(gridManager.getTypeAtCoordinate(x,y).equals(PAPER) && neighborsOfEachTypeAtCoordinate.get(3) > THRESHHOLD){
      nextStates.get(3)[x][y] = 1;
    }
    if(gridManager.getTypeAtCoordinate(x,y).equals(SCISSORS)&& neighborsOfEachTypeAtCoordinate.get(1) > THRESHHOLD){
      nextStates.get(1)[x][y] = 1;
    }
  }
}
