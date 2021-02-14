package cellsociety.view;

import cellsociety.controller.simulationengine.SimulationEngine;
import cellsociety.model.cell.State;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

  private static final String language = "English";
  public static final String DEFAULT_RESOURCE_PACKAGE = "cellsociety.view.resources.";
  public static final String DEFAULT_RESOURCE_FOLDER = "/" + DEFAULT_RESOURCE_PACKAGE.replace(".", "/");
  private ResourceBundle resources;
  public static final String STYLESHEET = "default.css";

  public static final int WINDOW_WIDTH = 800;
  public static final int WINDOW_HEIGHT = 600;
  private final Group sceneNodes;
  private final Stage stage;
  public CellGraphics cellGraphics;
  public SidePanel sidePanel;
  public GraphGraphics graphGraphics;
  public GridGraphics gridGraphics;
  private Slider slider;

  private static final double MIN_SPEED = 0;
  private static final double MAX_SPEED = 60;
  private static final double DEFAULT_SPEED = 30;
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
    resources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
    initialize();
  }

  private void initialize() {
    BorderPane root = new BorderPane();
    gridGraphics = new GridGraphics();
    sidePanel = new SidePanel();
    createButtons();
    root.setRight(gridGraphics.getGridPane());
    root.setLeft(sidePanel.getPane());

    stage.setTitle(resources.getString("SimulationTitle"));
    scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
    scene.getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET).toExternalForm());

    stage.setScene(scene);
    stage.show();

  }
  private void createButtons() {
    // Add buttons
    Button startButton = makeButton("StartCommand", event -> simulationEngine.startSimulation());
    Button stopButton = makeButton("StopCommand",event -> simulationEngine.stopSimulation());
    Button stepButton = makeButton("StepCommand",event -> {
      if (simulationEngine.decoderInitialized()) {
        simulationEngine.updateCellState();
      }
    });
    Button resetButton = makeButton("ResetCommand", event -> {
      if (simulationEngine.decoderInitialized()) {
        gridGraphics.reset();
        sidePanel.removeDescription();
        simulationEngine.stopSimulation();
        simulationEngine.initializeData();
      }
    });
    Button loadNewButton = makeButton("LoadNewCommand", event -> {
      simulationEngine.stopSimulation();
      String desc = sidePanel.removeDescription();
      simulationEngine.initializeDecoder();
      if (simulationEngine.decoderInitialized()) {
        simulationEngine.initializeData();
      }
      else sidePanel.setDescription(desc);
    });

    sidePanel.addNodesToPane(startButton,stopButton,stepButton,resetButton,loadNewButton);

    // Add slider
    Label label = new Label();
    label.setText(resources.getString("SpeedLabel"));
    slider = new Slider(MIN_SPEED,MAX_SPEED,DEFAULT_SPEED);

    sidePanel.addNodesToPane(label,slider);

  }

  private Button makeButton(String property, EventHandler<ActionEvent> handler) {
    Button button = new Button();
    button.setText(resources.getString(property));
    button.setOnAction(handler);
    return button;
  }

  public void setDescription(String description) {
    sidePanel.setDescription(description);
  }



  public void update(State[][] states, String model) {
    gridGraphics.update(states, model);
    simulationEngine.setSimulationSpeed((int)slider.getValue());
  }
}
