package cellsociety.model.watormodel;

import cellsociety.model.cell.State;
import cellsociety.model.rules.Rules;

/**
 *
 */
public class WaTorModelRules extends Rules {

  /**
   * Default constructor
   */
  public WaTorModelRules() {
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
  protected String decideState(int i, boolean alive) {
    return "";
  }

}