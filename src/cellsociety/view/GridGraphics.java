package cellsociety.view;

import cellsociety.model.cell.State;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

/**
 *
 */
public class GridGraphics {

  private static final double GRID_BUFFER = 10;
  private static final double NUM_BUFFERS = 10;
  private static final double GRID_GAP_SIZE = .5;
  private double gridSize = SimulationScreen.WINDOW_HEIGHT - NUM_BUFFERS * GRID_BUFFER;

  private GridPane gridPane;

  /**
   * Default constructor
   */
  public GridGraphics() {
    initialize();
  }

  private void initialize() {
    gridPane = new GridPane();

    gridPane.setPadding(new Insets(GRID_BUFFER, GRID_BUFFER, GRID_BUFFER, GRID_BUFFER));

    gridPane.setVgap(GRID_GAP_SIZE);
    gridPane.setHgap(GRID_GAP_SIZE);

    gridPane.setAlignment(Pos.CENTER_RIGHT);

    EventHandler<MouseEvent> eventHandler = new EventHandler() {
      @Override
      public void handle(Event event) {

      }
    };

  }

  public GridPane getGridPane() {
    return gridPane;
  }

  public void update(State[][] states, String model) {
    gridPane.getChildren().clear();
    for (int r = 0; r < states.length; r++) {
      for (int c = 0; c < states[0].length; c++) {
        Rectangle rect = new Rectangle();
        rect.setWidth(gridSize / states.length);
        rect.setHeight(gridSize / states[0].length);
        rect.getStyleClass().add(model + "-" + states[r][c].type);
        gridPane.add(rect, r, c);
      }
    }
  }

  public void reset() {
    gridPane.getChildren().clear();
  }

  public void resizeGrid(double size) {
    gridSize = calculateGridSize(size);
    int totalNum = (int)Math.sqrt(gridPane.getChildren().size());
    for (Node node : gridPane.getChildren()) {
      Rectangle rectangle = (Rectangle) node;
      rectangle.setWidth(gridSize/totalNum);
      rectangle.setHeight(gridSize/totalNum);
    }
  }

  private double calculateGridSize(double windowHeight) {
    return windowHeight - NUM_BUFFERS * GRID_BUFFER;
  }

}
