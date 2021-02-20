package cellsociety.view;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;

/**
 *
 */
public class HexagonCell extends Polygon {

  private static final String STROKE_COLOR = "black";
  private static final double STROKE_WIDTH = .5;

  /**
   *
   * @param x
   * @param y
   * @param sideLength
   */
  public HexagonCell(double x, double y, double sideLength) {
    super();
    double halfSide = sideLength/2;
    double height = sideLength * Math.sqrt(3)/2;
    getPoints().addAll(
        x, y,
        x + height, y + halfSide,
        x + height, y + halfSide + sideLength,
        x, y + 2 * sideLength,
        x - height, y + halfSide + sideLength,
        x - height, y + halfSide
    );
    setBorders();
  }
  private void setBorders() {
    setStroke(Paint.valueOf(STROKE_COLOR));
    setStrokeWidth(STROKE_WIDTH);
  }

}
