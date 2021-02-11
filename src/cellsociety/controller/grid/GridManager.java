package cellsociety.controller.grid;

import cellsociety.model.cell.Cell;
import cellsociety.model.cell.State;
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
    private List<List<State>> grid;
    /**
     * Default constructor
     */
    public GridManager() {
        grid = new ArrayList<>();
    }

    public State[][] buildGrid(int r, int c, ArrayList<State> template){
        State [][] stateOfCells = new State[r][c];
        for(int row = 0; row < r; row++){
            for(int col = 0; col < c; col++){
                State state = new State(row, col, false);
                stateOfCells[row][col] = state;
            }
        }
        for(State s : template){
            stateOfCells[r /2 + s.getxCoord()][c /2 + s.getyCoord()].alive = true;
        }
        return stateOfCells;
    }


    public List<List<State>> getGrid(){
        return grid;
    }




}