package cellsociety.model.simulationrules;

import cellsociety.model.GridManager;
import cellsociety.model.State;
import java.util.ArrayList;
import java.util.List;
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

  public RockPaperScissorsRules(int threshold, long randomSeed, String rockColor, String paperColor,
      String scissorsColor, String emptyColor) {
    THRESHHOLD = threshold;
    ROCK_COLOR = rockColor;
    PAPER_COLOR = paperColor;
    SCISSORS_COLOR = scissorsColor;
    EMPTY_COLOR = emptyColor;
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
   * returns the possible types (e.g. agent x, agent y, empty)
   *
   * @return arraylist of possible types
   */
  @Override
  public List<String> getPossibleTypes() {
    return possibleTypes;
  }

  /**
   * Returns the possible colors for each type
   *
   * @return arraylist of colors
   */
  @Override
  public List<String> getPossibleColors() {
    return possibleColors;
  }

  /**
   * Decide state updates each cell at each specific coordinate location according to the rules.
   * Assumption is that all the parameters are non-null values that are properly defined.
   * @param neighborsOfEachTypeAtTheCurrentLocation counts the number of neighbors that are specific "type" of
   *                                                neighbors at the specific x, y coordinate location for all
   *                                                possible types of the simulation.
   * @param markStateForFurtherAnalysis this list of integer array acts as the means for each rules class
   *                                    to keep track of each possible type of state. List contains one integer
   *                                    array per possible type of state.
   * @param updateStates List of states contains all the states that are to be updated at the end of each iteration.
   *                     UpdateStates is sent to each Rules class where rules are applied. If a state has to be
   *                     updated, the rules class adds the state to updateStates.
   * @param x x coordinate
   * @param y y coordinate
   * @param gridManager gridManager object needed to control and oversee state checking
   */
  @Override
  public void decideState(List<Integer> neighborsOfEachTypeAtTheCurrentLocation, List<int[][]> markStateForFurtherAnalysis,
      List<State> updateStates, int x, int y,
      GridManager gridManager) {

    System.out.println(THRESHHOLD);

    if (gridManager.getTypeAtCoordinate(x, y).equals(ROCK)
        && neighborsOfEachTypeAtTheCurrentLocation.get(2) > THRESHHOLD) {
      updateStates.add(new State(x, y, PAPER, PAPER_COLOR, 0));
    }
    if (gridManager.getTypeAtCoordinate(x, y).equals(PAPER)
        && neighborsOfEachTypeAtTheCurrentLocation.get(3) > THRESHHOLD) {
      updateStates.add(new State(x, y, SCISSORS, SCISSORS_COLOR, 0));
    }
    if (gridManager.getTypeAtCoordinate(x, y).equals(SCISSORS)
        && neighborsOfEachTypeAtTheCurrentLocation.get(1) > THRESHHOLD) {
      updateStates.add(new State(x, y, ROCK, ROCK_COLOR, 0));
    }
  }
}
