package cellsociety.view;

import javafx.scene.shape.Polygon;

public class TriangleCell extends Polygon {

  public TriangleCell(double x, double y, double sideLength, boolean pointsUp) {
    super();
    if (pointsUp) {
      getPoints().addAll(
        x+sideLength/2,y+0.0,
          x+0.0,y+sideLength*Math.sqrt(3)/2,
          x+sideLength,y+sideLength*Math.sqrt(3)/2
      );
    }
    else {
      getPoints().addAll(
          x+0.0,y+0.0,
          x+sideLength,y+0.0,
          x+sideLength/2,y+sideLength*Math.sqrt(3)/2
      );
    }
  }

}
