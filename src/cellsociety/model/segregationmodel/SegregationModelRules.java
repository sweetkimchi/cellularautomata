package cellsociety.model.segregationmodel;

import cellsociety.model.cell.State;
import cellsociety.model.rules.Rules;

/**
 *
 */
public class SegregationModelRules extends Rules {

  /**
   * Default constructor
   */
  public SegregationModelRules() {
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

}