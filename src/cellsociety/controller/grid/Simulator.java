package cellsociety.controller.grid;

import cellsociety.model.cell.Cell;
import java.util.*;

/**
 * 
 */
public abstract class Simulator {

    private GridManager gridManager;
    private Map<Cell, List<Cell>> grid;
    /**
     * Default constructor
     */
    public Simulator() {
    }

    protected abstract void initializeData();

    protected abstract void initializeRules();

    protected abstract void initializeGrid();


    public Map<Cell, List<Cell>> processGridForVisualization(){
        return grid;
    }

    /**
     * 
     */
    public void updateCellState() {
        // TODO implement here
    }

    /**
     * 
     */
    public void updateSimulatorView() {
        // TODO implement here
    }

}