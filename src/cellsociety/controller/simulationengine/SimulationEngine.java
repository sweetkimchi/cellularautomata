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



    /**
     * 
     */
    public Decoder decoder;

    /**
     * 
     */
    public Data data;

    /**
     * 
     */
    public SimulationScreen simulationScreen;

    /**
     * 
     */
    public Stage stage;

    /**
     * 
     */
    public XMLParser populator;

    /**
     * Default constructor
     */
    public SimulationEngine(Stage stage) {
        initialize(stage);
        System.out.println("START of the Simulation");
    }

    private void initialize(Stage stage){
        SimulationScreen simulationScreen = new SimulationScreen(stage);
       // GameOfLifeSimulator gameOfLifeSimulator = new GameOfLifeSimulator();
    }

}