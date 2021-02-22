package cellsociety.view;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Displays a graph for all cell types in a separate window, runs parallel to the simulation grid
 * screen.
 *
 * @author Harrison Huang
 */
public class GraphGraphics {

  BorderPane pane;
  Stage stage;
  private int currentTime;
  private LineChart<String, Number> lineChart;
  private static final int LINE_WINDOW_SIZE = 150;
  private static final double WINDOW_WIDTH = 500;
  private static final double WINDOW_HEIGHT = 500;
  private final Map<String,XYChart.Series<String,Number>> lines;
  private static final String EMPTY_CELL = "empty";

  /**
   * Creates a new window for displaying the graph.
   *
   * @param title title for the graph displayed for the window and at the top
   * @param xLabel label for the x-axis
   * @param yLabel label for the y-axis
   */
  public GraphGraphics(String title, String xLabel, String yLabel) {
    currentTime = 0;
    lines = new HashMap<>();

    pane = new BorderPane();
    stage = new Stage();
    stage.setTitle(title);
    lineChart = createChart(title, xLabel, yLabel);
    pane.setCenter(lineChart);
    Scene scene = new Scene(pane, WINDOW_WIDTH, WINDOW_HEIGHT);
    stage.setScene(scene);
    stage.show();
  }

  private LineChart<String, Number> createChart(String title, String xLabel, String yLabel) {
    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis();
    xAxis.setLabel(xLabel);
    xAxis.setAnimated(false);
    yAxis.setLabel(yLabel);
    yAxis.setAnimated(false);

    lineChart = new LineChart<>(xAxis, yAxis);
    lineChart.setTitle(title);
    lineChart.setAnimated(false);

    return lineChart;
  }

  /**
   * Updates the graph given a map of new values. Adds new lines if they do not exist.
   * Skips all empty cells. Maintains a window size to show a limited time frame.
   *
   * @param values a Map of values to update the grid.
   */
  public void update(Map<String,Integer> values) {
    if (values.keySet().size()!= 0) {
      currentTime++;
      for (String type : values.keySet()) {
        if (type.equals(EMPTY_CELL)) continue;
        putIfAbsent(type);
        updateLine(type,values.get(type));
      }
    }
  }

  private void putIfAbsent(String type) {
    if (!lines.containsKey(type)) {
      lines.put(type,new XYChart.Series<>());
      lines.get(type).setName(type);
      lineChart.getData().add(lines.get(type));
    }
  }

  private void updateLine(String type, Integer value) {
    lines.get(type).getData().add(new XYChart.Data<>(""+currentTime, value));
    if (lines.get(type).getData().size() > LINE_WINDOW_SIZE) {
      lines.get(type).getData().remove(0);
    }
  }

  /**
   * Resets the window and previous data.
   */
  public void reset() {
    for (XYChart.Series<String,Number> line : lines.values()) {
      line.getData().clear();
    }
    lines.clear();
    lineChart.getData().clear();
    currentTime = 0;
  }

  /**
   * Returns a boolean of whether or not the graph window is open.
   * 
   * @return boolean if graph window is active
   */
  public boolean isActive() {
    return stage.isShowing();
  }
}
