package cellsociety.controller.simulationengine;

import cellsociety.controller.grid.GridManager;
import cellsociety.controller.grid.Simulator;
import cellsociety.model.cell.Cell;
import cellsociety.model.cell.State;
import cellsociety.model.gameoflife.GameOfLifeCell;
import cellsociety.model.gameoflife.GameOfLifeRule;
import cellsociety.view.SimulationScreen;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javafx.animation.AnimationTimer;
import javafx.stage.Stage;

/**
 *
 */
public class SimulationEngine extends Simulator {

  private final ArrayList<State> LIST_NAME_BLOCK = new ArrayList<>(
      Arrays.asList(new State(0, 0, true), new State(0, 1, true), new State(1, 0, true),
          new State(1, 1, true)));
  private final ArrayList<State> LIST_NAME_BLINKER = new ArrayList<>(
      Arrays.asList(new State(0, 0, true), new State(0, 1, true), new State(0, 2, true)));
  private final ArrayList<State> LIST_NAME_CORNELL = new ArrayList<>(Arrays
      .asList(new State(1, 0, true), new State(0, 1, true), new State(1, 1, true),
          new State(2, 1, true)));
  private final ArrayList<State> LIST_NAME_JIYUN = new ArrayList<>(Arrays
      .asList(new State(1, 0, true), new State(2, 0, true), new State(0, 1, true),
          new State(1, 2, true), new State(4, 2, true), new State(2, 3, true),
          new State(3, 3, true), new State(4, 3, true)));
  private final ArrayList<State> LIST_NAME_TOAD = new ArrayList<>(Arrays
      .asList(new State(0, 1, true), new State(0, 2, true), new State(1, 3, true),
          new State(2, 0, true), new State(3, 1, true), new State(3, 2, true)));


  private GridManager gridManager;
  private GameOfLifeRule gameOfLifeRule;
  private State[][] stateOfAllCells;
  private GameOfLifeCell[][] cellPositions;
  private final SimulationScreen simulationScreen;
  private Map<Cell, List<Cell>> mapOfNeighbors;
  private ArrayList<State> template;
  private final int row = 50;
  private final int col = 50;

  /**
   * Default constructor
   */
  public SimulationEngine(String templateName) {
    simulationScreen = new SimulationScreen(new Stage());

    assignTemplate(templateName);
    initializeData();


  }

  private ArrayList<State> assignTemplate(String templateName) {
    if (templateName.equals("block")) {
      template = LIST_NAME_BLOCK;
    } else if (templateName.equals("blinker")) {
      template = LIST_NAME_BLINKER;
    } else if (templateName.equals("cornell")) {
      template = LIST_NAME_CORNELL;
    } else if (templateName.equals("jiyun")) {
      template = LIST_NAME_JIYUN;
    }
    return template;
  }


  @Override
  protected void initializeData() {
    initializeConstructors();
    initializeGrid();
    initializeCells();
  //  gridManager.printGrid();
    updateCellState();

    runSimulation();
  }

  protected void initializeConstructors() {
    gameOfLifeRule = new GameOfLifeRule();
    cellPositions = new GameOfLifeCell[row][col];
    gridManager = new GridManager(row, col);
  }

  protected void initializeCells() {
    for (int row = 0; row < stateOfAllCells.length; row++) {
      for (int col = 0; col < stateOfAllCells[0].length; col++) {
        cellPositions[row][col] = new GameOfLifeCell(stateOfAllCells[row][col]);
      }
    }
    simulationScreen.update(stateOfAllCells);
  }

  protected void initializeGrid() {
    stateOfAllCells = gridManager.buildGrid(template);
    initializeCells();
  }

  @Override
  public void updateCellState() {
    stateOfAllCells = gameOfLifeRule.judgeStateOfEachCell(stateOfAllCells);
    gridManager.updateGrid(stateOfAllCells);
    //gridManager.printGrid();
    simulationScreen.update(stateOfAllCells);
  }

  public State[][] getStateOfAllCells() {
    return stateOfAllCells;
  }


  private void runSimulation() {
    AnimationTimer animation = new AnimationTimer() {
      @Override
      public void handle(long now) {
        updateCellState();
      }
    };
    animation.start();
  }

  private int getNumberOfNeighbors(State[][] stateOfAllCells, int numberOfSides) {
    return 0;
  }


}