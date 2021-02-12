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
  protected boolean decideState(int i, boolean alive) {
    return false;
  }

  @Override
  public State[][] judgeStateOfEachCell(State[][] statesOfAllCells) {
    return new State[0][];
  }

}