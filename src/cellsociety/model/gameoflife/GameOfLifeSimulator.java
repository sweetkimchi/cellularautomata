package cellsociety.model.gameoflife;

import cellsociety.controller.grid.GridManager;
import cellsociety.controller.grid.Simulator;

/**
 * 
 */
public class GameOfLifeSimulator extends Simulator {

    private GridManager gridManager;
    /**
     * Default constructor
     */
    public GameOfLifeSimulator() {
        initializeData();
    }

    private void initializeData(){
        initializeGrid();
    }

    private void initializeGrid(){
       gridManager = new GridManager();

    }

}