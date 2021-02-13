package cellsociety.model.cell;


import javafx.scene.paint.Paint;

/**
 *
 */
public class State {

  private final int xCoord;
  private final int yCoord;
  public String type;
  public Paint color;
  public int numberOfMoves;
  public int energy;

  /**
   * Default constructor Initializes the state of each cell according to the data fed
   */
  public State(int xCoord, int yCoord, String type) {
    this.xCoord = xCoord;
    this.yCoord = yCoord;
    this.type = type;
    numberOfMoves = 0;
    this.energy = 5;
  }

  public State(int xCoord, int yCoord, String type, String colorString, int numberOfMoves) {
    this.xCoord = xCoord;
    this.yCoord = yCoord;
    this.type = type;
    this.numberOfMoves = numberOfMoves;
    color = Paint.valueOf(colorString);
  }

  public State(int xCoord, int yCoord, String type, String colorString, int numberOfMoves, int energy) {
    this.xCoord = xCoord;
    this.yCoord = yCoord;
    this.type = type;
    this.energy = energy;
    this.numberOfMoves = numberOfMoves;
    color = Paint.valueOf(colorString);
  }

  public void setColor(String colorName) {
    color = Paint.valueOf(colorName);
  }

  public void checkAliveAndColor() {

  }

  public int getxCoord() {
    return xCoord;
  }

  public int getyCoord() {
    return yCoord;
  }

}