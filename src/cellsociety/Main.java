package cellsociety;

import cellsociety.controller.Controller;
import cellsociety.controller.simulationengine.SimulationEngine;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 */
public class Main extends Application {


  public static void main(String[] args) {
    // TODO implement here
    launch(args);
  }

  @Override
  public void start(Stage stage) {
    String template = "jiyun";
    SimulationEngine simulationEngine = new SimulationEngine(template);
  }
}