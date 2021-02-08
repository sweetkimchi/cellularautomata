package cellsociety.main;

import cellsociety.configurationfile.Decoder;
import cellsociety.configurationfile.XMLPopulator;
import cellsociety.rules.Data;
import cellsociety.view.SimulationScreen;
import java.util.*;
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
    }

}