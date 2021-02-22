package cellsociety.model.sugarscape;

import cellsociety.model.cell.State;

public class AgentState extends State {


  private int sugarPossession;
  private int sugarMetabolism;
  private int vision;
  private String type;

  /**
   * Constructor with more variations other than number of moves
   *
   * @param xCoord        xCoord associated with the state
   * @param yCoord        yCoord associated with the state
   * @param type          the type of the player (e.g. agent x, agent y)
   * @param colorString   color of the state
   * @param numberOfMoves the current, cumulative number of moves
   */
  public AgentState(int xCoord, int yCoord, String type, String colorString, int numberOfMoves, int sugarMetabolism, int vision, int sugarPossession) {
    super(xCoord, yCoord, type, colorString, numberOfMoves, sugarMetabolism, vision, sugarPossession);
    this.sugarMetabolism = sugarMetabolism;
    this.vision = vision;
  }

  /**
   * Basic constructor
   *
   * @param xCoord xCoord associated with the state
   * @param yCoord yCoord associated with the state
   * @param type   the type of the player
   */

}
