package cellsociety.view;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GraphGraphics {

  BorderPane pane;
  Stage stage;
  private int currentTime;
  private XYChart.Series<String, Number> series;
  private final LineChart<String, Number> lineChart;
  private static final int WINDOW_SIZE = 150;
  private Map<String,XYChart.Series<String,Number>> lines;

  public GraphGraphics(String title, String xLabel, String yLabel) {

    pane = new BorderPane();
    stage = new Stage();
    stage.setTitle(title);
    currentTime = 0;

    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis();
    xAxis.setLabel(xLabel);
    xAxis.setAnimated(false);
    yAxis.setLabel(yLabel);
    yAxis.setAnimated(false);

    lineChart = new LineChart<>(xAxis, yAxis);
    lineChart.setTitle(title);
    lineChart.setAnimated(false);

    lines = new HashMap<>();

    pane.setCenter(lineChart);
    Scene scene = new Scene(pane, 800, 600);
    stage.setScene(scene);
    stage.show();
  }

  public void update(Map<String,Integer> values) {
    if (values.keySet().size()!= 0) {
      currentTime++;
      for (String type : values.keySet()) {
        if (!lines.keySet().contains(type)) {
          lines.put(type,new XYChart.Series<>());
          lines.get(type).setName(type);
          lineChart.getData().add(lines.get(type));
        }
        lines.get(type).getData().add(new XYChart.Data<>(""+currentTime,values.get(type)));
        if (lines.get(type).getData().size() > WINDOW_SIZE) {
          lines.get(type).getData().remove(0);
        }
      }
    }
  }
  public void reset() {
    series.getData().clear();
    lines.clear();
    currentTime = 0;
  }

}
