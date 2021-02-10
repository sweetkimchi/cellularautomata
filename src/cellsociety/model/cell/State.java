package cellsociety.model.cell;

/**
 * 
 */
public class State {

    public boolean alive;
    private int xCoord;
    private int yCoord;
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