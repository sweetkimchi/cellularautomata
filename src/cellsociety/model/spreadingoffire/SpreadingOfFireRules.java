package cellsociety.model.spreadingoffire;

import cellsociety.model.cell.State;
import cellsociety.model.rules.Rules;

/**
 *
 */
public class SpreadingOfFireRules extends Rules {

  /**
   * Default constructor
   */
  public SpreadingOfFireRules() {
  }

  @Override
  protected boolean decideState(int i, boolean alive) {
    return false;
  }

  /**
   * judges the state of each cell using the rule of the specific model class
   *
   * @param statesOfAllCells starting states of all cells
   * @return updated states of all cells
   */
  @Override
  public State[][] judgeStateOfEachCell(State[][] statesOfAllCells) {
    return new State[0][];
  }


}