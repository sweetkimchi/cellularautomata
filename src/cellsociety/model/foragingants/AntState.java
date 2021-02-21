package cellsociety.model.foragingants;

import cellsociety.model.cell.State;

public class AntState extends State {


  /**
   * Constructor with more variations other than number of moves
   *
   * @param xCoord        xCoord associated with the state
   * @param yCoord        yCoord associated with the state
   * @param type          the type of the player (e.g. agent x, agent y)
   * @param colorString   color of the state
   * @param numberOfMoves the current, cumulative number of moves
   */
  public AntState(int xCoord, int yCoord, String type, String colorString, int numberOfMoves) {
    super(xCoord, yCoord, type, colorString, numberOfMoves);
  }
}
