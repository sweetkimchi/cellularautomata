package cellsociety.view;

import cellsociety.model.GridManager;
import cellsociety.model.SimulationEngine;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
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
  public static final String DEFAULT_STYLESHEET = "default.css";
  public static final String LIGHT_STYLESHEET = "light.css";
  public static final String DARK_STYLESHEET = "dark.css";
  public static final double WINDOW_WIDTH = 800;
  public static final double WINDOW_HEIGHT = 600;
  private static final String language = "English";
  private static final double MIN_SPEED = 0;
  private static final double MAX_SPEED = 60;
  private static final double DEFAULT_SPEED = 30;
  private final Stage stage;
  private final SimulationEngine simulationEngine;
  private BorderPane root;
  private ResourceBundle resources;
  private SidePanel sidePanel;
  private GridGraphics gridGraphics;
  private TopPanel topPanel;
  private GraphGraphics graphGraphics;
  private double prevWindowWidth = 0;
  private double prevWindowHeight = 0;
  private Slider slider;
  private Scene scene;

  /**
   * Constructor for the SimulationScreen. To be called by the SimulationEngine. It displays the
   * window and creates the side panel of controls.
   *
   * @param stage  Stage on which the window is started
   * @param engine the SimulationEngine running this screen
   */
  public SimulationScreen(Stage stage, SimulationEngine engine) {
    this.stage = stage;
    simulationEngine = engine;
    resources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
    initializeStartScreen();
  }

  private void initializeStartScreen() {
    StartScreen startScreen = new StartScreen();
    Button beginButton = makeButton("BeginCommand", event -> initialize());
    startScreen.addTitle(resources.getString("SimulationTitle"));
    startScreen.addNode(beginButton);
    startScreen.addNode(createLanguageBox(startScreen));
    displayScreen(startScreen.getPane());
  }

  private Node createLanguageBox(StartScreen screen) {
    ComboBox<String> box = new ComboBox<>();
    box.getItems().addAll("English", "Chinese", "Korean");
    box.setOnAction(event -> {
      setResourcePackage(box.getValue());
      screen.setButtonText(resources.getString("BeginCommand"));
      screen.setTitle(resources.getString("SimulationTitle"));
      stage.setTitle(resources.getString("SimulationTitle"));
    });
    box.setValue(box.getItems().get(0));
    return box;
  }

  private void setResourcePackage(String language) {
    resources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);

  }

  private void initialize() {
    root = new BorderPane();
    gridGraphics = new GridGraphics();
    sidePanel = new SidePanel();
    topPanel = new TopPanel();
    addSidePanelControls();
    addTopPanelControls();
    root.setCenter(gridGraphics.getNode());
    root.setLeft(sidePanel.getNode());
    root.setTop(topPanel.getNode());

    stage.close();
    displayScreen(root);
  }

  private void addTopPanelControls() {
    ComboBox<String> comboBox = createColorBox();
    Button gridButton = makeButton("GridCommand", event -> toggleGrid());
    Button graphButton = makeButton("GraphCommand", event -> initializeGraph());
    Button newSimulationButton = makeButton("NewSimulationCommand",
        event -> new SimulationEngine());
    Button exitButton = makeButton("ExitCommand", event -> exitSimulation());
    topPanel.add(comboBox, gridButton, graphButton, newSimulationButton, exitButton);
  }

  private void exitSimulation() {
    stage.close();
    if (graphGraphics != null) {
      graphGraphics.close();
    }
  }

  private void toggleGrid() {
    if (root.getCenter() == null) {
      root.setCenter(gridGraphics.getNode());
    } else {
      root.setCenter(null);
    }
  }

  private void initializeGraph() {
    if (graphGraphics != null && graphGraphics.isActive()) {
      return;
    }
    graphGraphics = new GraphGraphics(resources.getString("GraphTitle"),
        resources.getString("GraphXLabel"), resources.getString("GraphYLabel"));
  }

  private ComboBox<String> createColorBox() {
    Map<String, String> colorTypes = new HashMap<>();
    colorTypes.put(resources.getString("DefaultColor"), DEFAULT_STYLESHEET);
    colorTypes.put(resources.getString("LightColor"), LIGHT_STYLESHEET);
    colorTypes.put(resources.getString("DarkColor"), DARK_STYLESHEET);
    ComboBox<String> comboBox = new ComboBox<>();
    for (String s : colorTypes.keySet()) {
      comboBox.getItems().add(s);
    }
    comboBox.setValue(resources.getString("DefaultColor"));
    comboBox.setOnAction(event -> setStylesheet(colorTypes.get(comboBox.getValue())));
    return comboBox;
  }

  private void setStylesheet(String stylesheet) {
    scene.getStylesheets().clear();
    scene.getStylesheets()
        .add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + stylesheet).toExternalForm());
  }

  private void displayScreen(Pane root) {
    stage.setTitle(resources.getString("SimulationTitle"));
    scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
    setStylesheet(DEFAULT_STYLESHEET);

    stage.setScene(scene);
    stage.show();
  }

  private void addSidePanelControls() {
    Button startButton = makeButton("StartCommand", event -> simulationEngine.startSimulation());
    Button stopButton = makeButton("StopCommand", event -> simulationEngine.stopSimulation());
    Button stepButton = makeButton("StepCommand", event -> stepSimulation());
    Button resetButton = makeButton("ResetCommand", event -> resetSimulation());
    Button loadNewButton = makeButton("LoadNewCommand", event -> loadNewFile());
    Button saveButton = makeButton("SaveCommand", event -> saveSimulation());

    sidePanel.addNodesToPane(startButton, stopButton, stepButton, resetButton, loadNewButton);
    sidePanel.addNodesToPane(saveButton);
    addSpeedSlider();

  }

  private void stepSimulation() {
    if (simulationEngine.decoderInitialized()) {
      simulationEngine.updateCellState();
    }
  }

  private void resetSimulation() {
    if (simulationEngine.decoderInitialized()) {
      gridGraphics.reset();
      resetGraph();
      sidePanel.removeDescription();
      simulationEngine.stopSimulation();
      simulationEngine.initializeData();
    }
  }

  private void resetGraph() {
    if (graphGraphics != null) {
      graphGraphics.reset();
    }
  }

  private void loadNewFile() {
    simulationEngine.stopSimulation();
    String desc = sidePanel.removeDescription();
    simulationEngine.initializeDecoder();
    if (simulationEngine.decoderInitialized()) {
      simulationEngine.initializeData();
      checkWindowSizeChanged();
      resetGraph();
    } else {
      sidePanel.setDescription(desc);
    }
  }

  private void saveSimulation() {
    try {
      simulationEngine.saveSimulation();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  private void addSpeedSlider() {
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

  /**
   * Sets the description of the current model to the screen. Calls the side panel to do this.
   *
   * @param description text to be displayed
   */
  public void setDescription(String description) {
    sidePanel.setDescription(description);
  }

  /**
   * Updates the screen by calling on the grid to update. Also updates the simulation speed with the
   * current value on the slider.
   *
   * @param gridManager to get states for updating the grid
   */
  public void update(GridManager gridManager) {
    gridGraphics.update(gridManager);
    updateGraph(gridManager.getTallyOfEachTypeToPresentAsSummary());
    simulationEngine.setSimulationSpeed((int) slider.getValue());
    checkWindowSizeChanged();
  }

  private void updateGraph(Map<String, Integer> map) {
    if (graphGraphics != null) {
      graphGraphics.update(map);
    }
  }

  /**
   * Checks if the window size has changed, and updates the grid if it has.
   */
  public void checkWindowSizeChanged() {
    if (scene.getHeight() != prevWindowHeight || scene.getWidth() != prevWindowWidth) {
      gridGraphics.resizeGrid(scene.getWidth() - SidePanel.MAX_WIDTH,
          scene.getHeight() - topPanel.getHeight());
      prevWindowHeight = scene.getHeight();
      prevWindowWidth = scene.getWidth();
    }
  }

  /**
   * Sets shape of tile for the grid.
   *
   * @param shape String for tile shape
   */
  public void setGridShape(String shape) {
    gridGraphics.setGridShape(shape);
  }
}
