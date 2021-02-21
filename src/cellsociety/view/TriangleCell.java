package cellsociety.view;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;

/**
 * The triangle cell generates an equilateral triangle to be tiled using GridGraphics.
 *
 * @author Harrison Huang
 */
public class TriangleCell extends Polygon {

  private static final double TRIANGLE_RATIO = Math.sqrt(3)/2;
  private static final String STROKE_COLOR = "black";
  private static final double STROKE_WIDTH = .5;

  /**
   * Creates a new Triangle Cell object for the given parameters.
   *
   * @param x position in x direction
   * @param y position in y direction
   * @param sideLength length of side of triangle
   * @param pointsUp boolean of which direction the triangle points
   */
  public TriangleCell(double x, double y, double sideLength, boolean pointsUp) {
    super();
    double halfSide = sideLength/2;
    double height = sideLength*TRIANGLE_RATIO;
    if (pointsUp) {
      getPoints().addAll(
        x + halfSide, y,
          x, y + height,
          x+sideLength, y + height
      );
    }
    else {
      getPoints().addAll(
          x, y,
          x + sideLength, y,
          x + halfSide, y + height
      );

      setBorders();
    }
  }

  private void setBorders() {
    setStroke(Paint.valueOf(STROKE_COLOR));
    setStrokeWidth(STROKE_WIDTH);
  }

}
