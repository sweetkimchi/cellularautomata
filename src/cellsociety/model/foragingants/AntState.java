package cellsociety.model.foragingants;

import cellsociety.model.cell.State;

public class AntState extends State {

  private boolean hasFood;
  private int amountOfFood;
  private String direction;
  /**
   * Constructor with more variations other than number of moves
   *
   * @param xCoord        xCoord associated with the state
   * @param yCoord        yCoord associated with the state
   * @param type          the type of the player (e.g. agent x, agent y)
   * @param colorString   color of the state
   * @param numberOfMoves the current, cumulative number of moves
   */
  public AntState(int xCoord, int yCoord, String type, String colorString, int numberOfMoves, int amountOfFood) {
    super(xCoord, yCoord, type, colorString, numberOfMoves, amountOfFood);
    hasFood = false;
    this.amountOfFood = amountOfFood;
  }

  public AntState(int xCoord, int yCoord, String type, String colorString,int numberOfMoves, boolean hasFood, String direction) {
    super(xCoord, yCoord, type, colorString, numberOfMoves, hasFood, direction);
    this.hasFood = hasFood;
    amountOfFood = 0;
    this.direction = direction;
  }

  public AntState(int xCoord, int yCoord, String type, String colorString, int numberOfMoves) {
    super(xCoord, yCoord, type, colorString, numberOfMoves);
    hasFood = false;
    amountOfFood = 0;
  }

  public boolean hasFood(){
    return hasFood;
  }

  public int getFood(){
    return amountOfFood;
  }

}
