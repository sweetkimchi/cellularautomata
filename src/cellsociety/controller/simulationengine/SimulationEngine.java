package cellsociety.controller.simulationengine;

import cellsociety.controller.Decoder;
import cellsociety.controller.grid.GridManager;
import cellsociety.controller.grid.Simulator;
import cellsociety.model.cell.Cell;
import cellsociety.model.cell.State;
import cellsociety.model.gameoflife.GameOfLifeCell;
import cellsociety.model.gameoflife.GameOfLifeRule;
import cellsociety.view.SimulationScreen;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.animation.AnimationTimer;
import javafx.stage.Stage;

/**
 *
 */
public class SimulationEngine extends Simulator {

//  private final ArrayList<State> LIST_NAME_BLOCK = new ArrayList<>(
//      Arrays.asList(new State(0, 0, true), new State(0, 1, true), new State(1, 0, true),
//          new State(1, 1, true)));
//  private final ArrayList<State> LIST_NAME_BLINKER = new ArrayList<>(
//      Arrays.asList(new State(0, 0, true), new State(0, 1, true), new State(0, 2, true)));
//  private final ArrayList<State> LIST_NAME_CORNELL = new ArrayList<>(Arrays
//      .asList(new State(1, 0, true), new State(0, 1, true), new State(1, 1, true),
//          new State(2, 1, true)));
//  private final ArrayList<State> LIST_NAME_JIYUN = new ArrayList<>(Arrays
//      .asList(new State(1, 0, true), new State(2, 0, true), new State(0, 1, true),
//          new State(1, 2, true), new State(4, 2, true), new State(2, 3, true),
//          new State(3, 3, true), new State(4, 3, true)));
//  private final ArrayList<State> LIST_NAME_TOAD = new ArrayList<>(Arrays
//      .asList(new State(0, 1, true), new State(0, 2, true), new State(1, 3, true),
//          new State(2, 0, true), new State(3, 1, true), new State(3, 2, true)));


  private final SimulationScreen simulationScreen;
  private int row;
  private int col;
  private GridManager gridManager;
  private GameOfLifeRule gameOfLifeRule;
  private State[][] stateOfAllCells;
  private GameOfLifeCell[][] cellPositions;
  private Map<Cell, List<Cell>> mapOfNeighbors;
  private ArrayList<State> template;
  private Decoder decoder;

  /**
   * Default constructor
   */
  public SimulationEngine() {
    simulationScreen = new SimulationScreen(new Stage());
    initializeDecoder();
    initializeData();


  }

  private ArrayList<State> constructStartingStateForSimulation(ArrayList<String> coordinates) {
    template = new ArrayList<>();
    for (int i = 0; i + 1 < coordinates.size(); i += 2) {
      State state = new State(Integer.parseInt(coordinates.get(i)),
          Integer.parseInt(coordinates.get(i + 1)), true);
      template.add(state);
    }
    return template;
  }

  private void initializeDecoder(){
    decoder = new Decoder();
    decoder.readValuesFromXMLFile();
  }


  @Override
  protected void initializeData() {
    row = decoder.getRows();
    col = decoder.getCols();
    initializeModelConstructors();
    constructStartingStateForSimulation(decoder.getCoords());
    initializeGrid();
    initializeCells();
    //  gridManager.printGrid();
    updateCellState();
    runSimulation();
  }

  protected void initializeModelConstructors() {
    gameOfLifeRule = new GameOfLifeRule();
    //need to be fixed for a better design
  }

  protected void initializeCells() {
    cellPositions = new GameOfLifeCell[row][col];
    for (int row = 0; row < stateOfAllCells.length; row++) {
      for (int col = 0; col < stateOfAllCells[0].length; col++) {
        cellPositions[row][col] = new GameOfLifeCell(stateOfAllCells[row][col]);
      }
    }
    simulationScreen.update(stateOfAllCells);
  }

  protected void initializeGrid() {
    gridManager = new GridManager(row, col);
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