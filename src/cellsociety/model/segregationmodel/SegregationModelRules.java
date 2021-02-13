package cellsociety.model.segregationmodel;

import cellsociety.model.cell.State;
import cellsociety.model.rules.Rules;
import java.util.ArrayList;

/**
 *
 */
public class SegregationModelRules extends Rules {

  private double emptyRatio;
  private double populationRatio;
  private ArrayList<String> possibleTypes;
  /**
   * Default constructor
   */
  public SegregationModelRules(double emptyRatio, double populationRatio) {
    this.emptyRatio = emptyRatio;
    this.populationRatio = populationRatio;
    possibleTypes = new ArrayList<>();
    possibleTypes.add("empty");
    possibleTypes.add("shark");
    possibleTypes.add("fish");
  }

  @Override
  protected String decideState(int i, boolean alive) {

    return "";
  }

  @Override
  public State[][] judgeStateOfEachCell(State[][] statesOfAllCells) {
    return new State[0][];
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