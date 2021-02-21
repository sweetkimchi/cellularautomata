package cellsociety.view;

import cellsociety.model.cell.State;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

/**
 * Creates and updates the grid which displays the cellular automata simulation. It can be updated
 * for new states, grabbing colors from the CSS file, and it can be resized.
 *
 * @author Harrison Huang
 */
public class GridGraphics {

  private static final double GRID_BUFFER = 20;
  private static final double NUM_BUFFERS = 2;
  private static final double GRID_GAP_SIZE = 0.5;
  private double gridSize;

  // square = 1, triangle = 2, hexagon = 3
  private static final int GRID_SHAPE = 1;

  private static final double TRIANGLE_RATIO = Math.sqrt(3)/2;
  private static final double HEXAGON_OFFSET_CORRECTION = 0.5;

  private AnchorPane paneForGrid;
  private State[][] currentStates;
  private String currentModel;

  /**
   * Initializes the GridPane for the class.
   */
  public GridGraphics() {
    initialize();
  }

  private void initialize() {
    paneForGrid = new AnchorPane();
    gridSize = calculateGridSize(SimulationScreen.WINDOW_WIDTH);
  }

  /**
   * Getter method for the Node for the GridPane.
   *
   * @return Node object for the GridPane
   */
  public Node getNode() {
    return paneForGrid;
  }

  /**
   * Updates the state of the grid with the input of a 2-D array of states and the name of the model
   * in order to determine color, as predetermined by the CSS file.
   *
   * @param states 2-D array of states
   * @param model  name of model
   */
  public void update(State[][] states, String model) {
    if (states != null && model != null) {
      currentStates = states;
      currentModel = model;
    }
    updateStates();
  }

  private void updateStates() {
    if (currentStates==null || currentModel==null) return;
    reset();
    for (int x = 0; x < currentStates.length; x++) {
      for (int y = 0; y < currentStates[0].length; y++) {
        Node node;
        if (GRID_SHAPE == 1) {
          node = makeSquare(x, y);
        }
        else if (GRID_SHAPE == 2) {
          node = makeTriangle(x, y);
        }
        else if (GRID_SHAPE == 3) {
          node = makeHexagon(x, y);
        }
        else break;
        node.getStyleClass().add(currentModel + "-" + currentStates[y][x].getType());
        paneForGrid.getChildren().add(node);

      }
    }
  }

  private Node makeSquare(int r, int c) {
    Rectangle rect = new Rectangle();
    double sideLength = gridSize / (currentStates.length+GRID_GAP_SIZE);
    rect.setWidth(sideLength);
    rect.setHeight(sideLength);
    rect.setX(GRID_BUFFER + c *(sideLength+GRID_GAP_SIZE));
    rect.setY(GRID_BUFFER + r *(sideLength+GRID_GAP_SIZE));
    return rect;
  }

  private Node makeTriangle(int r, int c) {
    double sideLength = gridSize / (currentStates.length * TRIANGLE_RATIO);
    return new TriangleCell(GRID_BUFFER + c * sideLength/2,
        GRID_BUFFER + r * sideLength * TRIANGLE_RATIO,
        sideLength,(r + c) % 2 == 0);
  }

  private Node makeHexagon(int r, int c) {
    double hexagonWidth = gridSize / (currentStates.length + HEXAGON_OFFSET_CORRECTION);
    double sideLength = hexagonWidth / 2 / TRIANGLE_RATIO;
    double hexagonHeight = (3.0/2) * sideLength;
    if (r % 2 == 0) {
      return new HexagonCell(GRID_BUFFER + c * hexagonWidth,
          GRID_BUFFER + r * hexagonHeight, sideLength);
    }
    else {
      return new HexagonCell(GRID_BUFFER + (c +HEXAGON_OFFSET_CORRECTION) * hexagonWidth,
          GRID_BUFFER + r * hexagonHeight, sideLength);
    }
  }

  /**
   * Clears the whole grid.
   */
  public void reset() {
    paneForGrid.getChildren().clear();
  }

  /**
   * Resizes the grid to the specified size (width and height).
   *
   * @param width double for the new width of the grid
   * @param height double for the new height of the grid
   */
  public void resizeGrid(double width, double height) {
    if (width/height > paneForGrid.getMaxWidth()/ paneForGrid.getMaxHeight()) {
      gridSize = calculateGridSize(height);
    }
    else gridSize = calculateGridSize(width);
    updateStates();
  }

  private double calculateGridSize(double size) {
    return size - NUM_BUFFERS * GRID_BUFFER;
  }

}
