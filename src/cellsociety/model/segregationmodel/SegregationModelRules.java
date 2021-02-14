package cellsociety.model.watormodel;

import cellsociety.model.cell.State;
import cellsociety.model.rules.Rules;
import java.util.ArrayList;

/**
 *
 */
public class SegregationModelRules extends Rules {

  /**
   * Default constructor
   */
  public SegregationModelRules() {
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
  public ArrayList<String> getPossibleTypes() {
    return null;
  }

  @Override
  public ArrayList<String> getPossibleColors() {
    return null;
  }

}