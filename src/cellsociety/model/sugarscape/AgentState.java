package cellsociety.model.sugarscape;

import cellsociety.model.cell.State;

public class AgentState extends State {


  private int sugarPossession;
  private int sugarMetabolism;
  private int vision;
  private String type;

  /**
   * Basic constructor
   *
   * @param xCoord xCoord associated with the state
   * @param yCoord yCoord associated with the state
   * @param type   the type of the player
   */
  public AgentState(int xCoord, int yCoord, String type, int sugarMetabolism, int vision) {
    super(xCoord, yCoord, type);
    this.type = type;
    this.vision = vision;
    this.sugarMetabolism = sugarMetabolism;
  }
}
