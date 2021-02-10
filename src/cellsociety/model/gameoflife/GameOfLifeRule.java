package cellsociety.model.gameoflife;

import cellsociety.model.cell.Cell;
import cellsociety.model.rules.Rules;

/**
 * 
 */
public class GameOfLifeRule extends Rules {

    /*
    1. Case 1: State.alive = false when numberOfNeighbors <= lowerSurvivalBoundary
    2. Case 2: State.alive = True when lowerSurvivalBoundary < numberOfNeighbors <= numberOfNeighbors
    3. Case 3: State.alive = false when upperSurvivalBoundary < numberOfNeighbors
     */
    private final int lowerSurvivalBoundary;
    private final int upperSurvivalBoundary;
    /**
     * Default constructor
     */
    public GameOfLifeRule() {
        /*
        Each cell with one or no neighbors dies, as if by solitude.
        Each cell with four or more neighbors dies, as if by overpopulation.
        Each cell with two or three neighbors survives.
         */
        lowerSurvivalBoundary = 2;
        upperSurvivalBoundary = 3;
    }

    private void judgeStateOfCell(GameOfLifeCell cell){
        if(cell.getNumberOfNeighbors() <= lowerSurvivalBoundary){
            cell.state.alive = false;
        }
    }

}