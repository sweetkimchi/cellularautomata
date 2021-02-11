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

<<<<<<< HEAD
        //SimulationScreen simulationScreen = new SimulationScreen(stage);
        String template = "blinker";
=======
        SimulationScreen simulationScreen = new SimulationScreen(stage);
        String template = "toad";
>>>>>>> 32c588640d349eaa42de51af7873a72858417ca0
        GameOfLifeSimulator gameOfLifeSimulator = new GameOfLifeSimulator(template);
    }

    public void start(){

    }

}