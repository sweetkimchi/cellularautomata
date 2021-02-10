package cellsociety.model.cell;

import java.util.*;

/**
 * 
 */
public abstract class Cell {

    State state;
    /**
     * Default constructor
     */
    public Cell() {

    }

    /**
     * 
     */
    public State currentState;

    /**
     * 
     */
    public State nextState;

    /**
     * 
     */
    public ArrayList neighbors;



    /**
     * 
     */
    public void updateState() {
        // TODO implement here
    }

    /**
     * 
     */
    public void getCurrentState() {
        // TODO implement here
    }

    public abstract int getNumberOfNeighbors();

    /**
     * 
     */
    public void getNextState() {
        // TODO implement here
    }

    /**
     * 
     */
    public abstract void checkNextState();

}