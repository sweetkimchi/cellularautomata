package cellsociety.model.cell;

import java.awt.Color;

/**
 * 
 */
public class State {

    public boolean alive;
    private int xCoord;
    private int yCoord;
    public Color color;
    /**
     * Default constructor
     * Initializes the state of each cell according to the data fed
     */
    public State(int xCoord, int yCoord, boolean alive) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.alive = alive;
        setColor();
    }

    private void setColor(){
        if(alive){
            color = Color.ORANGE;
        }else{
            color = Color.BLACK;
        }
    }

    public int getxCoord(){
        return xCoord;
    }

    public int getyCoord(){
        return yCoord;
    }

}