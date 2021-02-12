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

  /**
   * Default constructor Initializes the state of each cell according to the data fed
   */
  public State(int xCoord, int yCoord, String type) {
    this.xCoord = xCoord;
    this.yCoord = yCoord;
    this.type = type;
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