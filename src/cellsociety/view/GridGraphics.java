package cellsociety.view;

import cellsociety.model.cell.State;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

/**
 *
 */
public class GridGraphics {

  private GridPane gridPane;

  /**
   * Default constructor
   */
  public GridGraphics() {
    initialize();
  }

  private void initialize() {
    gridPane = new GridPane();

    //gridPane.setMinSize(800, 600);

    //Setting the padding
    gridPane.setPadding(new Insets(10, 10, 10, 10));

    //Setting the vertical and horizontal gaps between the columns
    gridPane.setVgap(.5);
    gridPane.setHgap(.5);

    //Setting the Grid alignment
    gridPane.setAlignment(Pos.CENTER_RIGHT);

  }

  public GridPane getGridPane() {
    return gridPane;
  }

  public void update(State[][] states) {
    gridPane.getChildren().clear();
    for (int r = 0; r < states.length; r++) {
      for (int c = 0; c < states[0].length; c++) {
        gridPane.add(new Rectangle(10, 10, states[r][c].color), r, c);
      }
    }
  }

  public void reset() {
    gridPane.getChildren().clear();
  }

}
