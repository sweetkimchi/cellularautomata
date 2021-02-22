package cellsociety.model.sugarscape;

import cellsociety.model.GridManager;
import cellsociety.model.State;
import cellsociety.model.Rules;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SugarScapeRules extends Rules {

  private String FULL_SUGAR_COLOR;
  private String LOW_SUGAR_COLOR;
  private String AGENT_COLOR;
  private String EMPTY_COLOR;
  private ArrayList<String> possibleTypes;
  private ArrayList<String> possibleColors;
  private String FULLPATCH = "fullpatch";
  private String LOWPATCH = "lowpatch";
  private String AGENT = "paper";
  private String EMPTY = "empty";
  private ArrayList<AgentState> agents;
  private ArrayList<PatchState> patches;
  private double THRESHHOLD;
  private Random random;

  public SugarScapeRules(int numberOfAgents, int maximumSugar, int growBackSugar,
      int sugarMetabolism, int vision,
      String fullColor, String lowColor, String agentColor, String emptyColor) {
    this.FULL_SUGAR_COLOR = fullColor;
    this.LOW_SUGAR_COLOR = lowColor;
    this.AGENT_COLOR = agentColor;
    this.EMPTY_COLOR = emptyColor;
    initializeColorsAndTypes();
    initializePatches();
  }

  private void initializePatches() {
  }

  private void initializeAgents(int numberOfAgents) {

  }

  private void initializeColorsAndTypes() {
    possibleTypes = new ArrayList<>();
    possibleColors = new ArrayList<>();
    possibleTypes.add(EMPTY);
    possibleTypes.add(FULLPATCH);
    possibleTypes.add(LOWPATCH);
    possibleTypes.add(AGENT);
    possibleColors.add(EMPTY_COLOR);
    possibleColors.add(FULL_SUGAR_COLOR);
    possibleColors.add(LOW_SUGAR_COLOR);
    possibleColors.add(AGENT_COLOR);
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

  @Override
  public void decideState(List<Integer> neighborsOfEachTypeAtCoordinate, List<int[][]> nextStates,
      List<State> updateStates, int x, int y,
      GridManager gridManager) {

  }
}
