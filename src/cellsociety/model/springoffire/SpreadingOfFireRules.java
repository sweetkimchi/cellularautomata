package cellsociety.model.springoffire;

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

  /**
   * Judges the state of each cell with the rules defined by the model
   *
   * @param statesOfAllCells the current states of each cell
   * @return the new states of each cell
   */
  @Override
  public State[][] judgeStateOfEachCell(State[][] statesOfAllCells) {
    return new State[0][];
  }

}