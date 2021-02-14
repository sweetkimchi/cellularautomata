package cellsociety.view;

import cellsociety.controller.simulationengine.SimulationEngine;
import cellsociety.model.cell.State;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 */
public class SimulationScreen {

  public static final int WINDOW_WIDTH = 800;
  public static final int WINDOW_HEIGHT = 600;
  private static final String WINDOW_TITLE = "Cell Society Simulation";
  private final Group sceneNodes;
  private final Stage stage;
  public CellGraphics cellGraphics;
  public ButtonGraphics buttonGraphics;
  public GraphGraphics graphGraphics;
  public GridGraphics gridGraphics;

  private static final double MIN_SPEED = 0.5;
  private static final double MAX_SPEED = 3;
  private static final double DEFAULT_SPEED = 1;
  /**
   *
   */
  public SliderGraphics sliderGraphics;
  private Scene scene;

  private SimulationEngine simulationEngine;

  public SimulationScreen(Stage stage, SimulationEngine engine) {
    this.stage = stage;
    simulationEngine = engine;
    sceneNodes = new Group();
    initialize();
  }

  public void initialize() {
    BorderPane root = new BorderPane();
    gridGraphics = new GridGraphics();
    buttonGraphics = new ButtonGraphics();
    createButtons();
    root.setRight(gridGraphics.getGridPane());
    root.setLeft(buttonGraphics.getPane());

    stage.setTitle(WINDOW_TITLE);
    scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
    stage.setScene(scene);
    stage.show();

  }
  private void createButtons() {
    Button startButton = new Button("Start");
    startButton.setOnAction(event -> simulationEngine.startSimulation());
    Button stopButton = new Button("Stop");
    stopButton.setOnAction(event -> simulationEngine.stopSimulation());
    Button stepButton = new Button("Step");
    stepButton.setOnAction(event -> {
      if (simulationEngine.decoderInitialized()) {
        simulationEngine.updateCellState();
      }
    });
    Button resetButton = new Button("Reset");
    resetButton.setOnAction(event -> {
      if (simulationEngine.decoderInitialized()) {
        gridGraphics.reset();
        simulationEngine.stopSimulation();
        simulationEngine.initializeData();
      }
    });
    Button loadNewButton = new Button("Load New");
    loadNewButton.setOnAction(event -> {
      simulationEngine.stopSimulation();
      simulationEngine.initializeDecoder();
      simulationEngine.initializeData();
    });

    buttonGraphics.addNodesToPane(startButton,stopButton,stepButton,resetButton,loadNewButton);

    Slider slider = new Slider(MIN_SPEED,MAX_SPEED,DEFAULT_SPEED);
    Label label = new Label();
    label.setText("Speed");

    buttonGraphics.addNodesToPane(slider,label);


  }


  public void update(State[][] states) {
    gridGraphics.update(states);
  }
}
