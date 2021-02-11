package cellsociety.model.gameoflife;

import cellsociety.controller.grid.GridManager;
import cellsociety.model.cell.Cell;
import cellsociety.model.cell.State;
import java.util.List;

/**
 * 
 */
public class GameOfLifeCell extends Cell {
    State state;
    /**
     * Default constructor
     */
    public GameOfLifeCell(int xCoord, int yCoord, boolean alive) {
        state = new State(xCoord, yCoord, alive);
    }

    @Override
    public int getNumberOfNeighbors() {
        return 0;
    }

    /**
     * 
     */
    public void checkNextState() {
        // TODO implement here
    }


}