package cellsociety.model.gameoflife;

import cellsociety.model.cell.Cell;
import cellsociety.model.cell.State;
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

    public State[][] judgeStateOfEachCell(State[][] statesOfAllCells){
        int[][] numberOfAliveNeighbors = numberOfAliveNeighbors(statesOfAllCells);
        for(int x = 0; x < statesOfAllCells.length; x++) {
            for (int y = 0; y < statesOfAllCells[0].length; y++) {
                statesOfAllCells[x][y].alive = decideState(numberOfAliveNeighbors[x][y], statesOfAllCells[x][y].alive);
            }
        }
        return statesOfAllCells;
    }

    private int[][] numberOfAliveNeighbors(State[][] statesOfAllCells){
        int[][] numberOfNeighbors = new int[statesOfAllCells.length][statesOfAllCells[0].length];
        for(int x = 0; x < statesOfAllCells.length; x++){
            for(int y = 0; y < statesOfAllCells[0].length;y++){
                int numberOfNeighbor = 0;
                if(x - 1 >= 0 && y - 1 >= 0 && statesOfAllCells[x-1][y-1].alive){
                    numberOfNeighbor++;
                }
                if(x - 1 >= 0 && y >= 0 && statesOfAllCells[x-1][y].alive){
                    numberOfNeighbor++;
                }
                if(x - 1 >= 0 && y + 1 < statesOfAllCells[0].length && statesOfAllCells[x-1][y+1].alive){
                    numberOfNeighbor++;
                }
                if(y - 1 >= 0 && statesOfAllCells[x][y - 1].alive){
                    numberOfNeighbor++;
                }
                if(y + 1 < statesOfAllCells[0].length && statesOfAllCells[x][y + 1].alive){
                    numberOfNeighbor++;
                }
                if(x +1 < statesOfAllCells.length && y - 1 >= 0 && statesOfAllCells[x+1][y-1].alive){
                    numberOfNeighbor++;
                }
                if(x + 1 < statesOfAllCells.length && y >= 0 && statesOfAllCells[x+1][y].alive){
                    numberOfNeighbor++;
                }
                if(x + 1 < statesOfAllCells.length && y + 1 < statesOfAllCells[0].length && statesOfAllCells[x+1][y+1].alive){
                    numberOfNeighbor++;
                }
                System.out.print(" " + numberOfNeighbor + " ");
                numberOfNeighbors[x][y] = numberOfNeighbor;
                //    statesOfAllCells[x][y].alive = decideState(numberOfNeighbor);
            }
            System.out.println();
        }
        System.out.println();
        return numberOfNeighbors;
    }

    private boolean decideState(int numberOfNeighbor, boolean alive){
        if(alive){
            if(numberOfNeighbor < lowerSurvivalBoundary ){
                return false;
            }else if(numberOfNeighbor <= upperSurvivalBoundary){
                return true;
            }else{
                return false;
            }
        }else{
            if(numberOfNeighbor == 3){
                return true;
            }else{
                return false;
            }
        }

    }
}