package cellsociety.model.cell;

import java.util.ArrayList;

/**
 *
 */
public abstract class Cell {

  /**
   *
   */
  public State currentState;
  /**
   *
   */
  public State nextState;
  /**
   *
   */
  public ArrayList neighbors;
  State state;

  /**
   * Default constructor
   */
  public Cell() {

  }

  /**
   *
   */
  public void updateState() {
    // TODO implement here
  }

  /**
   *
   */
  public void getCurrentState() {
    // TODO implement here
  }

  public abstract int getNumberOfNeighbors();

  /**
   *
   */
  public void getNextState() {
    // TODO implement here
  }

  /**
   *
   */
  public abstract void checkNextState();

}