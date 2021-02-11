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
    private List<List<Cell>> grid;
    /**
     * Default constructor
     */
    public GridManager() {
        grid = new ArrayList<>();
    }

    public List<List<Cell>> buildGrid(int r, int c, ArrayList<ArrayList<Cell>> template){
        for(int row = 0; row < ROWSIZE; row++){
            for(int col = 0; col < COLUMNSIZE; col++){
                ArrayList<Cell> neighbors = new ArrayList<>();
            //    grid.add(new GameOfLifeCell(), neighbors);
            }
        }
        return grid;
    }

    public List<List<Cell>> getGrid(){
        return grid;
    }




}