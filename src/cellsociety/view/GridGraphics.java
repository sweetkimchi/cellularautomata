package cellsociety.view;

import cellsociety.model.cell.State;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

/**
 * Creates and updates the grid which displays the cellular automata simulation. It can be updated
 * for new states, grabbing colors from the CSS file, and it can be resized.
 *
 * @author Harrison Huang
 */
public class GridGraphics {

  private static final double GRID_BUFFER = 10;
  private static final double NUM_BUFFERS = 10;
  private static final double GRID_GAP_SIZE = .5;
  private double gridSize = SimulationScreen.WINDOW_HEIGHT - NUM_BUFFERS * GRID_BUFFER;

  private GridPane gridPane;

  /**
   * Initializes the GridPane for the class.
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

  }

  /**
   * Getter method for the Node for the GridPane.
   *
   * @return Node object for the GridPane
   */
  public Node getNode() {
    return gridPane;
  }

  /**
   * Updates the state of the grid with the input of a 2-D array of states and the name of the model
   * in order to determine color, as predetermined by the CSS file.
   *
   * @param states 2-D array of states
   * @param model  name of model
   */
  public void update(State[][] states, String model) {
    gridPane.getChildren().clear();
    for (int r = 0; r < states.length; r++) {
      for (int c = 0; c < states[0].length; c++) {
        Rectangle rect = new Rectangle();
        rect.setWidth(gridSize / states.length);
        rect.setHeight(gridSize / states[0].length);
        rect.getStyleClass().add(model + "-" + states[r][c].getType());
        gridPane.add(rect, r, c);
      }
    }
  }

  /**
   * Clears the whole grid.
   */
  public void reset() {
    gridPane.getChildren().clear();
  }

  /**
   * Resizes the grid to the specified size.
   *
   * @param size double for the new size of the grid
   */
  public void resizeGrid(double size) {
    gridSize = calculateGridSize(size);
    int totalNum = (int) Math.sqrt(gridPane.getChildren().size());
    for (Node node : gridPane.getChildren()) {
      Rectangle rectangle = (Rectangle) node;
      rectangle.setWidth(gridSize / totalNum);
      rectangle.setHeight(gridSize / totalNum);
    }
  }

  private double calculateGridSize(double windowHeight) {
    return windowHeight - NUM_BUFFERS * GRID_BUFFER;
  }

}
