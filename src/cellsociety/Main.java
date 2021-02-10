package cellsociety;

import cellsociety.controller.main.SimulationEngine;
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
        SimulationEngine simulationEngine = new SimulationEngine();
    }
}