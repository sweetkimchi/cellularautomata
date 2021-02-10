package cellsociety.model.gameoflife;

import cellsociety.controller.grid.GridManager;
import cellsociety.controller.grid.Simulator;
import cellsociety.model.cell.Cell;
import java.util.*;

/**
 * 
 */
public class GameOfLifeSimulator extends Simulator {

    private GridManager gridManager;
    private GameOfLifeRule gameOfLifeRule;
    private Map<Cell, List<Cell>> grid;
    /**
     * Default constructor
     */
    public GameOfLifeSimulator() {
        initializeData();
    }



    protected void initializeRules() {
        gameOfLifeRule = new GameOfLifeRule();
    }

    protected void initializeCells() {

    }

    protected void initializeGrid(){
       gridManager = new GridManager();
       grid = gridManager.getGrid();
    }


}