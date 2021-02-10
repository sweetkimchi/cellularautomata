package cellsociety.model.cell;

/**
 * 
 */
public class State {

    private int xCoord;
    private int yCoord;
    private boolean alive;
    /**
     * Default constructor
     * Initializes the state of each cell according to the data fed
     */
    public State(int xCoord, int yCoord, boolean alive) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.alive = alive;
    }

}