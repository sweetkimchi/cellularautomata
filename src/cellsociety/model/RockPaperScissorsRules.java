package cellsociety.model;

import cellsociety.controller.grid.GridManager;
import cellsociety.model.rules.Rules;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RockPaperScissorsRules extends Rules {

  private final String ROCK_COLOR = "red";
  private final String PAPER_COLOR = "blue";
  private final String SCISSORS_COLOR = "lightgrey";
  private final String EMPTY_COLOR = "black";
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

  public RockPaperScissorsRules(int threshold, long randomSeed) {
    THRESHHOLD = threshold;
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
    return null;
  }

  /**
   * Returns the possible colors for each type
   *
   * @return arraylist of colors
   */
  @Override
  public ArrayList<String> getPossibleColors() {
    return null;
  }

  @Override
  public void decideState(List<Integer> neighborsOfEachTypeAtCoordinate, List<int[][]> nextStates,
      int x, int y, GridManager gridManager) {

  }
}
