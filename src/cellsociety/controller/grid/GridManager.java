package cellsociety.controller.grid;

import cellsociety.model.cell.Cell;
import cellsociety.model.gameoflife.GameOfLifeCell;
import java.util.*;

/**
 * 
 */
public class GridManager {

    private static final int ROWSIZE = 100;
    private static final int COLUMNSIZE = 100;
    /**
     *
     */
    private Map<Cell, List<Cell>> grid;
    /**
     * Default constructor
     */
    public GridManager() {
        grid = new HashMap<>();
        buildGrid();
    }

    private Map<Cell, List<Cell>> buildGrid(){
        for(int row = 0; row < ROWSIZE; row++){
            for(int col = 0; col < COLUMNSIZE; col++){
                ArrayList<Cell> neighbors = new ArrayList<>();
                grid.put(new GameOfLifeCell(), neighbors);
            }
        }
        return grid;
    }

    public Map<Cell, List<Cell>> getGrid(){
        return grid;
    }




}