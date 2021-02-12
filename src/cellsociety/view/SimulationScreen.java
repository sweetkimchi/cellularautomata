package cellsociety.view;

import cellsociety.model.cell.State;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 */
public class SimulationScreen {

  public static final int WINDOW_WIDTH = 800;
  public static final int WINDOW_HEIGHT = 600;
  private static final String WINDOW_TITLE = "Cell Society Simulation";
  public CellGraphics cellGraphics;
  public ButtonGraphics buttonGraphics;
  public GraphGraphics graphGraphics;
  public GridGraphics gridGraphics;
  /**
   *
   */
  public SliderGraphics sliderGraphics;
  private final Group sceneNodes;
  private Scene scene;
  private final Stage stage;

  public SimulationScreen(Stage stage) {
    this.stage = stage;
    sceneNodes = new Group();
    initialize();
  }

  public void initialize() {
    BorderPane root = new BorderPane();
    gridGraphics = new GridGraphics();

    stage.setTitle(WINDOW_TITLE);
    scene = new Scene(gridGraphics.getGridPane(), WINDOW_WIDTH, WINDOW_HEIGHT);
    stage.setScene(scene);
    stage.show();



  }

  public void update(State[][] states) {
    gridGraphics.update(states);
  }
}
