package cellsociety.view;

import java.util.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 */
public class SimulationScreen {

    private static final String WINDOW_TITLE = "Cell Society";
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;
    private Group sceneNodes;
    private Scene scene;
    private Stage stage;

    public CellGraphics cellGraphics;
    public ButtonGraphics buttonGraphics;
    public GraphGraphics graphGraphics;
    public GridGraphics gridGraphics;

    /**
     *
     */
    public SliderGraphics sliderGraphics;

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
    }
}
