package cellsociety.controller.simulationengine;

import cellsociety.controller.Decoder;
import cellsociety.controller.grid.GridManager;
import cellsociety.controller.grid.Simulator;
import cellsociety.model.cell.State;
import cellsociety.model.gameoflife.GameOfLifeRule;
import cellsociety.model.percolation.PercolationRules;
import cellsociety.model.rules.Rules;
import cellsociety.model.spreadingoffire.SpreadingOfFireRules;
import cellsociety.model.watormodel.SegregationModelRules;
import cellsociety.model.watormodel.WaTorModelRules;
import cellsociety.view.SimulationScreen;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.stage.Stage;

public class SimulationEngine extends Simulator {

  private final SimulationScreen simulationScreen;
  private int row;
  private int col;
  private GridManager gridManager;
  private Rules rules;
  private State[][] stateOfAllCells;
  private ArrayList<State> template;
  private Decoder decoder;
  private double populationRatio = 0.4;
  private double emptyRatio = 0.1;
  private int randomSeed = 0;

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
          Integer.parseInt(coordinates.get(i + 1)), rules.getStartingPositionCellType());
      template.add(state);
    }
    return template;
  }

  private void initializeDecoder() {
    decoder = new Decoder();
    decoder.readValuesFromXMLFile();
  }


  @Override
  protected void initializeData() {
    row = decoder.getRows();
    col = decoder.getCols();
    initializeGrid();
    initializeModelConstructors(decoder.getModel());
    updateCellState();
    runSimulation();
  }

  protected void initializeModelConstructors(String game) {

    if (game.equals("gameoflife")) {
      rules = new GameOfLifeRule();
      template = constructStartingStateForSimulation(decoder.getCoords());
      stateOfAllCells = gridManager.buildGridWithTemplate(template, rules.getStartingPositionCellType());
    }
    if (game.equals("percolation")) {
      rules = new PercolationRules();
    }
    if (game.equals("segregationmodel")) {
      rules = new SegregationModelRules();
    }
    if (game.equals("spreadingoffire")) {
      rules = new SpreadingOfFireRules();
    }
    if (game.equals("watormodel")) {
      rules = new WaTorModelRules(emptyRatio, populationRatio, randomSeed);
      stateOfAllCells = gridManager.buildGridWithRandomSeed(emptyRatio, populationRatio, randomSeed, rules.getPossibleTypes());
      gridManager.printGrid(stateOfAllCells);
    }
    //need to be fixed for a better design
  }


  protected void initializeGrid() {
    gridManager = new GridManager(row, col);
  }

  @Override
  public void updateCellState() {
    stateOfAllCells = rules.judgeStateOfEachCell(stateOfAllCells);
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