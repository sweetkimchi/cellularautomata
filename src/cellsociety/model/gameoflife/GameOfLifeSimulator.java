package cellsociety.model.gameoflife;

import cellsociety.controller.grid.GridManager;
import cellsociety.controller.grid.Simulator;
import cellsociety.model.cell.Cell;
import java.lang.reflect.Array;
import java.util.*;

/**
 * 
 */
public class GameOfLifeSimulator extends Simulator {

    private GridManager gridManager;
    private GameOfLifeRule gameOfLifeRule;
    private List<List<Cell>> grid;
    private Map<Cell, List<Cell>> mapOfNeighbors;
    private String templateName;
    private int row = 50;
    private int col = 50;
    private ArrayList<ArrayList<Cell>> template;
    /**
     * Default constructor
     */
    public GameOfLifeSimulator(String templateName) {
        this.templateName = templateName;
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
       grid = gridManager.buildGrid(row, col, template);
    }


}