package cellsociety.model.gameoflife;

import cellsociety.controller.grid.GridManager;
import cellsociety.controller.grid.Simulator;
import cellsociety.model.cell.Cell;
import cellsociety.model.cell.State;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javafx.animation.AnimationTimer;

/**
 *
 */
public class GameOfLifeSimulator extends Simulator {

  private ArrayList<State> LIST_NAME_BLOCK = new ArrayList<>(Arrays.asList(new State(0, 0, true), new State(0, 1, true), new State(1, 0, true),
      new State(1, 1, true)));

  private GridManager gridManager;
  private GameOfLifeRule gameOfLifeRule;
  private State[][] stateOfAllCells;
  private Map<Cell, List<Cell>> mapOfNeighbors;
  private ArrayList<State> template;
  private int row = 30;
  private int col = 30;

  /**
   * Default constructor
   */
  public GameOfLifeSimulator(String templateName) {
    assignTemplate(templateName);

    printGrid();
  }

  private ArrayList<State> assignTemplate(String templateName) {
    if (templateName.equals("block")) {
      template = LIST_NAME_BLOCK;
    }
    return template;
  }


  @Override
  protected void initializeData() {

  }

  protected void initializeRules() {
    gameOfLifeRule = new GameOfLifeRule();
  }

  protected void initializeCells() {
    for(int row = 0; row < stateOfAllCells.length; row++){
      for(int col = 0; col < stateOfAllCells[0].length; col++){

      }
    }
  }

  protected void initializeGrid() {
    gridManager = new GridManager();
    stateOfAllCells = gridManager.buildGrid(row, col, template);
    initializeCells();
  }

  private void printGrid() {
    for (int x = 0; x < row; x++) {
      for (int y = 0; y < col; y++) {
        if(stateOfAllCells[x][y].alive){

          System.out.print(" T ");
        }
        else{
          System.out.print(" F ");
        }
      }
      System.out.println();
    }
  }

  private void runSimulation(){
    AnimationTimer animation= new AnimationTimer() {
      @Override
      public void handle(long now) {
        System.out.print("GO");
      }
    };
  }


}