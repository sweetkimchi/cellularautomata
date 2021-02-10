package cellsociety.controller.main;

import cellsociety.controller.Decoder;
import cellsociety.controller.XMLPopulator;
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
    public XMLPopulator populator;

    /**
     * Default constructor
     */
    public SimulationEngine() {
        System.out.println("START of the Simulation");
    }

}