package cellsociety;

import cellsociety.controller.main.SimulationEngine;
import java.util.*;
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
    public void start(Stage primaryStage) throws Exception {
        var simulationEngine = new SimulationEngine();

        simulationEngine.initialize(primaryStage);

        primaryStage.show();
    }
}