package cellsociety.model.cell;


/**
 * Purpose: stores all information about the state of a cell Assumptions:
 *
 * @author Ji Yun Hyo
 */
public class State {

  private final int xCoord;
  private final int yCoord;
  private final int ENERGY = 10;
  private String type;
  private String color;
  private int numberOfMoves;
  private int energy;
  private boolean hasFood;

  /**
   * Basic constructor
   *
   * @param xCoord xCoord associated with the state
   * @param yCoord yCoord associated with the state
   * @param type   the type of the player
   */
  public State(int xCoord, int yCoord, String type) {
    this.xCoord = xCoord;
    this.yCoord = yCoord;
    this.type = type;
    numberOfMoves = 0;
    this.energy = ENERGY;
  }

  /**
   * Constructor with more variations other than number of moves
   *
   * @param xCoord        xCoord associated with the state
   * @param yCoord        yCoord associated with the state
   * @param type          the type of the player (e.g. agent x, agent y)
   * @param colorString   color of the state
   * @param numberOfMoves the current, cumulative number of moves
   */
  public State(int xCoord, int yCoord, String type, String colorString, int numberOfMoves) {
    this.xCoord = xCoord;
    this.yCoord = yCoord;
    this.type = type;
    this.numberOfMoves = numberOfMoves;
    this.energy = ENERGY;
    color = colorString;
  }

  /**
   * Constructor with more variations other than number of moves
   *
   * @param xCoord        xCoord associated with the state
   * @param yCoord        yCoord associated with the state
   * @param type          the type of the player (e.g. agent x, agent y)
   * @param colorString   color of the state
   * @param numberOfMoves the current, cumulative number of moves
   * @param energy        the current energy
   */
  public State(int xCoord, int yCoord, String type, String colorString, int numberOfMoves,
      int energy) {
    this.xCoord = xCoord;
    this.yCoord = yCoord;
    this.type = type;
    this.energy = energy;
    this.numberOfMoves = numberOfMoves;
    color = colorString;
  }

  public State(int xCoord, int yCoord, String type, String colorString, int numberOfMoves,
      boolean hasFood) {
    this.xCoord = xCoord;
    this.yCoord = yCoord;
    this.type = type;
    this.hasFood = hasFood;
    this.numberOfMoves = numberOfMoves;
    color = colorString;
  }



  /**
   * @return xCoord associated with the state
   */
  public int getxCoord() {
    return xCoord;
  }

  /**
   * @return yCoord associated with the sates
   */
  public int getyCoord() {
    return yCoord;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public int getEnergy() {
    return energy;
  }

  public int getNumberOfMoves() {
    return numberOfMoves;
  }

  public String getColor() {
    return color;
  }

  /**
   * Sets the color of the state. Used whenever the type is updated.
   *
   * @param colorName
   */
  public void setColor(String colorName) {
    color = colorName;
  }

  public String getDirection() {
  }
}