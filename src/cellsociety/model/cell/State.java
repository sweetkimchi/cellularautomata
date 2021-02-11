package cellsociety.model.cell;


import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * 
 */
public class State {

    public boolean alive;
    private int xCoord;
    private int yCoord;
    public Paint color;
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
            color = Paint.valueOf("orange");
        }else{
            color = Paint.valueOf("black");
        }
    }

    public int getxCoord(){
        return xCoord;
    }

    public int getyCoord(){
        return yCoord;
    }

}