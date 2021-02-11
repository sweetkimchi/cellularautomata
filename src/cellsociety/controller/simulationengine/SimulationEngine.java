package cellsociety.controller.simulationengine;

import cellsociety.controller.Decoder;
import cellsociety.controller.XMLParser;
import cellsociety.model.gameoflife.GameOfLifeSimulator;
import cellsociety.model.rules.Data;
import cellsociety.view.SimulationScreen;
import javafx.stage.Stage;
import xml.XMLParser;

/**
 * 
 */
public class SimulationEngine {
    private Stage stage;
    public Decoder decoder;
    public Data data;
    public SimulationScreen simulationScreen;
    public XMLParser populator;

    /**
     * Default constructor
     */
    public SimulationEngine(Stage stage) {
        this.stage = stage;
    }

    public void startSimulationEngine(){

        SimulationScreen simulationScreen = new SimulationScreen(stage);
        GameOfLifeSimulator gameOfLifeSimulator = new GameOfLifeSimulator();
    }

    public void start(){

    }

}