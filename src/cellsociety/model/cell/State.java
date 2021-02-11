package cellsociety.model.cell;


import javafx.scene.paint.Paint;

/**
 *
 */
public class State {

  public boolean alive;
  public Paint color;
  private final int xCoord;
  private final int yCoord;

  /**
   * Default constructor Initializes the state of each cell according to the data fed
   */
  public State(int xCoord, int yCoord, boolean alive) {
    this.xCoord = xCoord;
    this.yCoord = yCoord;
    this.alive = alive;
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