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

    //gridPane.setMinSize(800, 600);
    gridPane.setMaxSize(GRID_SIZE,GRID_SIZE);

    //Setting the padding
    gridPane.setPadding(new Insets(GRID_BUFFER, GRID_BUFFER, GRID_BUFFER, GRID_BUFFER));

    //Setting the vertical and horizontal gaps between the columns
    gridPane.setVgap(GRID_GAP_SIZE);
    gridPane.setHgap(GRID_GAP_SIZE);

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
        gridPane.add(new Rectangle(GRID_SIZE/states.length, GRID_SIZE/states.length,
                                    states[r][c].color), r, c);
      }
    }
  }

  public void reset() {
    gridPane.getChildren().clear();
  }

}
