package cellsociety.controller.simulationengine;

import cellsociety.controller.Decoder;
import cellsociety.controller.XMLParser;
import cellsociety.model.gameoflife.GameOfLifeSimulator;
import cellsociety.model.rules.Data;
import cellsociety.view.SimulationScreen;
import javafx.stage.Stage;

/**
 *
 */
public class SimulationEngine {

  public Decoder decoder;
  public Data data;
  public SimulationScreen simulationScreen;
  public XMLParser populator;
  private final Stage stage;

  /**
   * Default constructor
   */
  public SimulationEngine(Stage stage) {
    this.stage = stage;
  }

  public void startSimulationEngine() {

    //SimulationScreen simulationScreen = new SimulationScreen(stage);
    String template = "jiyun";
    GameOfLifeSimulator gameOfLifeSimulator = new GameOfLifeSimulator(template);
  }

  public void start() {

  }

}