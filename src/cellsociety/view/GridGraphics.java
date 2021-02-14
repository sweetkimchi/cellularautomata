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

  private static final double GRID_BUFFER = 10;
  private static final double GRID_SIZE = SimulationScreen.WINDOW_HEIGHT - 10 * GRID_BUFFER;
  private static final double GRID_GAP_SIZE = .5;

  private GridPane gridPane;

  /**
   * Default constructor
   */
  public GridGraphics() {
    initialize();
  }

  private void initialize() {
    gridPane = new GridPane();

    gridPane.setMaxSize(GRID_SIZE,GRID_SIZE);

    gridPane.setPadding(new Insets(GRID_BUFFER, GRID_BUFFER, GRID_BUFFER, GRID_BUFFER));

    gridPane.setVgap(GRID_GAP_SIZE);
    gridPane.setHgap(GRID_GAP_SIZE);

    gridPane.setAlignment(Pos.CENTER_RIGHT);

  }

  public GridPane getGridPane() {
    return gridPane;
  }

  public void update(State[][] states, String model) {
    gridPane.getChildren().clear();
    for (int r = 0; r < states.length; r++) {
      for (int c = 0; c < states[0].length; c++) {
        Rectangle rect = new Rectangle();
        rect.setWidth(GRID_SIZE/states.length);
        rect.setHeight(GRID_SIZE/states[0].length);
        rect.getStyleClass().add(model + "-" + states[r][c].type);
        gridPane.add(rect, r, c);
      }
    }
  }

  public void reset() {
    gridPane.getChildren().clear();
  }

}
