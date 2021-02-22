package cellsociety;

import cellsociety.model.SimulationEngine;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * launches the model by calling the simulation engine
 * @author Ji Yun Hyo
 */
public class Main extends Application {


  /**
   * main method
   *
   * @param args
   */
  public static void main(String[] args) {
    // TODO implement here
    launch(args);
  }

  /**
   * Starts the simulation
   *
   * @param stage Stage object required for animation
   */
  @Override
  public void start(Stage stage) {
    SimulationEngine simulationEngine = new SimulationEngine();
  }
}