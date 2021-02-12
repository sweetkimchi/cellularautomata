package cellsociety.view;

import cellsociety.model.cell.State;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 */
public class SimulationScreen {

  public static final int WINDOW_WIDTH = 800;
  public static final int WINDOW_HEIGHT = 600;
  private static final String WINDOW_TITLE = "Cell Society";
  private final Group sceneNodes;
  private final Stage stage;
  public CellGraphics cellGraphics;
  public ButtonGraphics buttonGraphics;
  public GraphGraphics graphGraphics;
  public GridGraphics gridGraphics;
  /**
   *
   */
  public SliderGraphics sliderGraphics;
  private Scene scene;

  public SimulationScreen(Stage stage) {
    this.stage = stage;
    sceneNodes = new Group();
    initialize();
  }

  public void initialize() {
    stage.setTitle(WINDOW_TITLE);
    scene = new Scene(sceneNodes, WINDOW_WIDTH, WINDOW_HEIGHT);
    stage.setScene(scene);
    stage.show();

    gridGraphics = new GridGraphics(stage);
  }

  public void update(State[][] states) {
    gridGraphics.update(states);
  }
}
