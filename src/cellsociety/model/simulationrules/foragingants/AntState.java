package cellsociety.model.simulationrules.foragingants;

import cellsociety.model.State;

/**
 * Purpose of this class is to keep track of slightly more variables than what State class allows.
 * This class is built to keep track of many variables for each ant
 * @author Ji Yun Hyo
 */
public class AntState extends State {

  private boolean hasFood;
  private int amountOfFood;
  private String direction;

  /**
   * Constructor with more variations other than number of moves
   *
   * @param xCoord        xCoord associated with the state
   * @param yCoord        yCoord associated with the state
   * @param type          the type of the player
   * @param colorString   color of the state
   * @param numberOfMoves the current, cumulative number of moves
   */
  public AntState(int xCoord, int yCoord, String type, String colorString, int numberOfMoves,
      int amountOfFood) {
    super(xCoord, yCoord, type, colorString, numberOfMoves, amountOfFood);
    hasFood = false;
    this.amountOfFood = amountOfFood;
  }

  /**
   * Constructor for Ants in the simulations
   * @param xCoord
   * @param yCoord
   * @param type type of the player
   * @param colorString
   * @param numberOfMoves the current, cumulative number of moves
   * @param hasFood boolean indicating whether ant has discovered food or not
   * @param direction direction the ant is facing
   */
  public AntState(int xCoord, int yCoord, String type, String colorString, int numberOfMoves,
      boolean hasFood, String direction) {
    super(xCoord, yCoord, type, colorString, numberOfMoves, hasFood, direction);
    this.hasFood = hasFood;
    amountOfFood = 0;
    this.direction = direction;
  }

  /**
   * Constructor used by the GridManager class to make the initial template for Ant simulation
   * @param xCoord
   * @param yCoord
   * @param type
   * @param colorString
   * @param numberOfMoves
   */
  public AntState(int xCoord, int yCoord, String type, String colorString, int numberOfMoves) {
    super(xCoord, yCoord, type, colorString, numberOfMoves);
    hasFood = false;
    amountOfFood = 0;
  }

  /**
   * @return boolean indicating whether ant has discovered food or not
   */
  public boolean hasFood() {
    return hasFood;
  }
}
