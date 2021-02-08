package cellsociety.cell;

import java.util.*;

/**
 * 
 */
public abstract class Cell {

    /**
     * Default constructor
     */
    public Cell() {
    }

    /**
     * 
     */
    public void currentState;

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
    public void gridPosition;

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