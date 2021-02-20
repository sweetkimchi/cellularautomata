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

/**
 * Controls the simulation. Acts as the middle-ground between GridManager, Decoder, Models, and
 * the View component. Depends on other parts of MVC working correctly. SimulationEngine makes sure
 * that different components do not have to interact with each other directly. Each component
 * handles its own thing without having to know too much about what the other components are doing.
 * The SimulationEngine acts to run the simulation, interacting with the SimulationScreen to
 * perform functions.
 * @author Ji Yun Hyo
 * @author Harrison Huang
 */
public class SimulationEngine{

  private final SimulationScreen simulationScreen;
  private GridManager gridManager;
  private Rules rules;
  private State[][] stateOfAllCells;
  private ArrayList<State> template;
  private Decoder decoder;
  private AnimationTimer animation;
  private int frameDelay;
  private int sleepTimer = 0;


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

  /**
   * Initializes the decoder so that it can go fetch data from the correct XML file for whichever component needs it
   */
  public void initializeDecoder() {
    decoder = new Decoder();
    decoder.readValuesFromXMLFile();
  }

  /**
   * @return whether decoder is initialized or not
   */
  public boolean decoderInitialized() {
    return (decoder != null);
  }

  /**
   * initializes all relevant data of the model
   */
  public void initializeData() {
    initializeGrid();
    initializeModelConstructors(decoder.getModel());
    simulationScreen.update(gridManager.getGrid(), decoder.getModel());
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
      rules = new SegregationModelRules(
          segDecoder.getRandSeed(), segDecoder.getSatThresh());
      stateOfAllCells = gridManager
              .buildGridWithRandomSeed(segDecoder.getEmptyRatio(), segDecoder.getPopRatio(),
                      segDecoder.getRandSeed(), rules.getPossibleTypes(), rules.getPossibleColors());

    }
    if (game.equals("spreadingoffire")) {
      FireDecoder fireDecoder = decoder.getFireDecoder();
      rules = new SpreadingOfFireRules(fireDecoder.getSeed(), fireDecoder.getProb());
      stateOfAllCells = gridManager
              .buildGridWithRandomSeed(fireDecoder.getEmptyRatio() ,fireDecoder.getTreeRatio(),
                      fireDecoder.getSeed(), rules.getPossibleTypes(), rules.getPossibleColors());
    }
    if (game.equals("wator")) {
      //   rules = new WaTorModelRules(emptyRatio, populationRatio, randomSeed, energyFish, reproduceBoundary, sharkEnergy);
      WaTorDecoder waTorDecoder = decoder.getWaTorDecoder();
      rules = new WaTorModelRules(
          waTorDecoder.getSeed(), waTorDecoder.getEnergy(), waTorDecoder.getFishRate(),
              waTorDecoder.getSharkLives());
      stateOfAllCells = gridManager
              .buildGridWithRandomSeed(waTorDecoder.getEmptyRatio(), waTorDecoder.getFSRatio(),
                      waTorDecoder.getSeed(), rules.getPossibleTypes(), rules.getPossibleColors());
    }
    //need to be fixed for a better design
  }


  protected void initializeGrid() {
    gridManager = new GridManager(decoder.getRows(), decoder.getCols());
  }

  /**
   * Updates the state of each cell according to logic of the model
   */
  public void updateCellState() {
   // badExampleOfEncapsulation();
    goodExampleOfEncapsulation();
  }

  private void goodExampleOfEncapsulation() {
    gridManager.judgeStateOfEachCell(rules);
    //THIS IS A BAD EXAMPLE THAT NEEDS TO BE FIXED
    // I WILL PASS IN THE GRID OF COLORS OR MAYBE GRID OF TYPES
    simulationScreen.update(gridManager.getGrid(),decoder.getModel());
  }

  private void badExampleOfEncapsulation() {
    rules.judgeStateOfEachCell(stateOfAllCells ,gridManager.getNumberOfNeighborsForEachType(rules.getPossibleTypes()));
    gridManager.updateGrid(stateOfAllCells);
    simulationScreen.update(stateOfAllCells, decoder.getModel());
  }


  private void runSimulation() {
    animation = new AnimationTimer() {
      @Override
      public void handle(long now) {
        simulationScreen.checkWindowSizeChanged();
        if (sleepTimer < frameDelay) {
          sleepTimer++;
          return;
        }
        updateCellState();
        sleepTimer = 0;
      }
    };
    simulationScreen.update(gridManager.getGrid(),decoder.getModel());
  }

  /**
   * Allows interactive button to start the simulation in View Component
   */
  public void startSimulation() {
    if (animation != null) {
      animation.start();
    }
  }

  /**
   * Allows interactive button to stop the simulation in View component
   */
  public void stopSimulation() {
    if (animation != null) {
      animation.stop();
    }
  }

  /**
   * Allows interactive slider to adjust the speed of simulation
   * @param s speed of the simulation
   */
  public void setSimulationSpeed(int s) {
    if (frameDelay >= 0) {
      frameDelay = 60 - s;
    }
  }

}
