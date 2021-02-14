package cellsociety.controller.simulationengine;

import cellsociety.controller.Decoder;
import cellsociety.controller.FireDecoder;
import cellsociety.controller.PercDecoder;
import cellsociety.controller.SegDecoder;
import cellsociety.controller.WaTorDecoder;
import cellsociety.controller.grid.GridManager;
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

public class SimulationEngine{

  private final SimulationScreen simulationScreen;
  private int row;
  private int col;
  private GridManager gridManager;
  private Rules rules;
  private State[][] stateOfAllCells;
  private ArrayList<State> template;
  private Decoder decoder;
  private AnimationTimer animation;

  private int frameDelay;
  private int sleepTimer = 0;


  private double probsOfFire = 0.5;
  private double popRatio = 0.9;
  private int randseed = 1414;


  /**
   * Default constructor
   */
  public SimulationEngine() {
    simulationScreen = new SimulationScreen(new Stage(), this);
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

  public void initializeDecoder() {
    decoder = new Decoder();
    decoder.readValuesFromXMLFile();
  }

  public boolean decoderInitialized() {
    return (decoder != null);
  }

  public void initializeData() {
    row = decoder.getRows();
    col = decoder.getCols();
    initializeGrid();

    initializeModelConstructors(decoder.getModel());

  //  initializeModelConstructors("spreadingoffire");
    simulationScreen.update(stateOfAllCells, decoder.getModel());
    simulationScreen.setDescription(decoder.getMyDesc());
    runSimulation();
  }

  protected void initializeModelConstructors(String game) {

    if (game.equals("gameOfLife")) {
      rules = new GameOfLifeRule();
      template = constructStartingStateForSimulation(decoder.getGOLDecoder().getCoords());
      stateOfAllCells = gridManager
              .buildGridWithTemplate(template, rules.getStartingPositionCellType());;
      updateCellState();
    }
    if (game.equals("percolation")) {
      PercDecoder percDecoder = decoder.getPercDecoder();
      rules = new PercolationRules(percDecoder.getSeed());
      stateOfAllCells = gridManager
              .buildGridWithRandomSeed(percDecoder.getBlockRatio(), percDecoder.getWaterToEmptyRatio(),
                      percDecoder.getSeed(), rules.getPossibleTypes(), rules.getPossibleColors());
    }
    if (game.equals("segregationmodel")) {
      SegDecoder segDecoder = decoder.getSegDecoder();
      rules = new SegregationModelRules(segDecoder.getPopRatio(),
              segDecoder.getRandSeed(), segDecoder.getSatThresh());
      stateOfAllCells = gridManager
              .buildGridWithRandomSeed(segDecoder.getEmptyRatio(), segDecoder.getPopRatio(),
                      segDecoder.getRandSeed(), rules.getPossibleTypes(), rules.getPossibleColors());

    }
    if (game.equals("spreadingoffire")) {
      FireDecoder fireDecoder = decoder.getFireDecoder();
      rules = new SpreadingOfFireRules(fireDecoder.getEmptyRatio(), fireDecoder.getSeed(), fireDecoder.getProb());
      stateOfAllCells = gridManager
              .buildGridWithRandomSeed(fireDecoder.getEmptyRatio() ,fireDecoder.getTreeRatio(),
                      fireDecoder.getSeed(), rules.getPossibleTypes(), rules.getPossibleColors());
    }
    if (game.equals("wator")) {
      //   rules = new WaTorModelRules(emptyRatio, populationRatio, randomSeed, energyFish, reproduceBoundary, sharkEnergy);
      WaTorDecoder waTorDecoder = decoder.getWaTorDecoder();
      rules = new WaTorModelRules(waTorDecoder.getFSRatio(),
              waTorDecoder.getSeed(), waTorDecoder.getEnergy(), waTorDecoder.getFishRate(),
              waTorDecoder.getSharkLives());
      stateOfAllCells = gridManager
              .buildGridWithRandomSeed(waTorDecoder.getEmptyRatio(), waTorDecoder.getFSRatio(),
                      waTorDecoder.getSeed(), rules.getPossibleTypes(), rules.getPossibleColors());
    }
    //need to be fixed for a better design
  }


  protected void initializeGrid() {
    gridManager = new GridManager(row, col);
  }

  public void updateCellState() {
    stateOfAllCells = rules.judgeStateOfEachCell(stateOfAllCells);
    gridManager.updateGrid(stateOfAllCells);
    simulationScreen.update(stateOfAllCells, decoder.getModel());
  }

  public State[][] getStateOfAllCells() {
    return stateOfAllCells;
  }


  private void runSimulation() {
    animation = new AnimationTimer() {
      @Override
      public void handle(long now) {
        if (sleepTimer < frameDelay) {
          sleepTimer++;
          return;
        }
        updateCellState();
        sleepTimer = 0;
      }
    };
    simulationScreen.update(stateOfAllCells,decoder.getModel());
  }

  public void startSimulation() {
    if (animation != null) {
      animation.start();
    }
  }

  public void stopSimulation() {
    if (animation != null) {
      animation.stop();
    }
  }
  public void setSimulationSpeed(int s) {
    if (frameDelay >= 0) {
      frameDelay = 60 - s;
    }
  }


  private int getNumberOfNeighbors(State[][] stateOfAllCells, int numberOfSides) {
    return 0;
  }


}
