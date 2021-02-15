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
 * Manages and displays the main user interface. It contains a grid and a side panel of controls.
 * These controls (buttons and a slider) affect how the simulation looks and runs.
 *
 * @author Harrison Huang
 */
public class SimulationScreen {

  public static final String DEFAULT_RESOURCE_PACKAGE = "cellsociety.view.resources.";
  public static final String DEFAULT_RESOURCE_FOLDER =
      "/" + DEFAULT_RESOURCE_PACKAGE.replace(".", "/");
  public static final String STYLESHEET = "default.css";
  public static final int WINDOW_WIDTH = 800;
  public static final int WINDOW_HEIGHT = 600;
  private static final String language = "English";
  private static final double MIN_SPEED = 0;
  private static final double MAX_SPEED = 60;
  private static final double DEFAULT_SPEED = 30;
  private final Group sceneNodes;
  private final Stage stage;
  public CellGraphics cellGraphics;
  public SidePanel sidePanel;
  public GraphGraphics graphGraphics;
  public GridGraphics gridGraphics;
  /**
   *
   */
  public SliderGraphics sliderGraphics;
  private final ResourceBundle resources;
  private Slider slider;
  private Scene scene;

  private final SimulationEngine simulationEngine;

  /**
   * Constructor for the SimulationScreen. To be called by the SimulationEngine.
   * It displays the window and creates the side panel of controls.
   * @param stage Stage on which the window is started
   * @param engine the SimulationEngine running this screen
   */
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
    addSidePanelControls();
    root.setRight(gridGraphics.getGridPane());
    root.setLeft(sidePanel.getPane());

    stage.setTitle(resources.getString("SimulationTitle"));
    scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
    scene.getStylesheets()
        .add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET).toExternalForm());

    stage.setScene(scene);
    stage.show();

  }

  private void addSidePanelControls() {
    // Add buttons
    Button startButton = makeButton("StartCommand", event -> simulationEngine.startSimulation());
    Button stopButton = makeButton("StopCommand", event -> simulationEngine.stopSimulation());
    Button stepButton = makeButton("StepCommand", event -> {
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
      } else {
        sidePanel.setDescription(desc);
      }
    });

    sidePanel.addNodesToPane(startButton, stopButton, stepButton, resetButton, loadNewButton);

    // Add slider
    Label label = new Label();
    label.setText(resources.getString("SpeedLabel"));
    slider = new Slider(MIN_SPEED, MAX_SPEED, DEFAULT_SPEED);

    sidePanel.addNodesToPane(label, slider);

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
    simulationEngine.setSimulationSpeed((int) slider.getValue());
  }
}
