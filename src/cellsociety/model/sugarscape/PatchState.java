package cellsociety.model.sugarscape;

import cellsociety.model.State;

public class PatchState extends State {

  private int amountOfSuguar;
  private int maximumSugar;
  private int growBackSugar;
  private String type;

  /**
   * Basic constructor
   *
   * @param xCoord xCoord associated with the state
   * @param yCoord yCoord associated with the state
   * @param type   the type of the player
   */
  public PatchState(int xCoord, int yCoord, String type, int maximumSugar, int growBackSugar) {
    super(xCoord, yCoord, type);
    amountOfSuguar = 0;
    this.maximumSugar = maximumSugar;
    this.growBackSugar = growBackSugar;
    this.type = type;
  }

}
