package cellsociety.controller.simulationengine;

import cellsociety.controller.Decoder;
import cellsociety.controller.grid.GridManager;
import cellsociety.model.ForagingAntsRules;
import cellsociety.model.RockPaperScissorsRules;
import cellsociety.model.cell.State;
import cellsociety.model.gameoflife.GameOfLifeRule;
import cellsociety.model.percolation.PercolationRules;
import cellsociety.model.rules.Rules;
import cellsociety.model.spreadingoffire.SpreadingOfFireRules;
import cellsociety.model.segregationmodel.SegregationModelRules;
import cellsociety.model.watormodel.WaTorModelRules;
import cellsociety.view.SimulationScreen;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.stage.Stage;

/**
 * Controls the simulation. Acts as the middle-ground between GridManager, Decoder, Models, and the
 * View component. Depends on other parts of MVC working correctly. SimulationEngine makes sure that
 * different components do not have to interact with each other directly. Each component handles its
 * own thing without having to know too much about what the other components are doing. The
 * SimulationEngine acts to run the simulation, interacting with the SimulationScreen to perform
 * functions.
 *
 * @author Ji Yun Hyo
 * @author Harrison Huang
 */
public class SimulationEngine {

  private final SimulationScreen simulationScreen;
  private GridManager gridManager;
  private Rules rules;
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
   * Initializes the decoder so that it can go fetch data from the correct XML file for whichever
   * component needs it
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
    simulationScreen.update(gridManager, decoder.getModel());
    simulationScreen.setDescription(decoder.getMyDesc());
    runSimulation();
  }

  protected void initializeModelConstructors(String game) {

    if (game.equals("gameOfLife")) {
      rules = new GameOfLifeRule();
      template = constructStartingStateForSimulation(decoder.getCoordinates());
      gridManager
              .buildGridWithTemplate(template, rules.getPossibleTypes(), rules.getPossibleColors(), decoder.getDiameter());
    }
    if (game.equals("percolation")) {
      rules = new PercolationRules(decoder.getSeed());
      gridManager
              .buildGridWithRandomSeed(decoder.getBlockRatio(), decoder.getWaterToEmptyRatio(),
                      decoder.getSeed(), rules.getPossibleTypes(), rules.getPossibleColors());
    }
    if (game.equals("segregationmodel")) {
      rules = new SegregationModelRules(
              decoder.getSeed(), decoder.getSatisfactionThreshold());
      gridManager
              .buildGridWithRandomSeed(decoder.getEmptyRatio(), decoder.getPopulationRatio(),
                      decoder.getSeed(), rules.getPossibleTypes(), rules.getPossibleColors());

    }
    if (game.equals("spreadingoffire")) {
      rules = new SpreadingOfFireRules(decoder.getSeed(), decoder.getProbsOfCatch());
      gridManager
              .buildGridWithRandomSeed(decoder.getEmptyRatio(), decoder.getTreeRatio(),
                      decoder.getSeed(), rules.getPossibleTypes(), rules.getPossibleColors());
    }
    if (game.equals("wator")) {
      //   rules = new WaTorModelRules(emptyRatio, populationRatio, randomSeed, energyFish, reproduceBoundary, sharkEnergy);
      rules = new WaTorModelRules(
              decoder.getSeed(), decoder.getEnergy(), decoder.getFishRate(),
              decoder.getSharkLives());
      gridManager
              .buildGridWithRandomSeed(decoder.getEmptyRatio(), decoder.getFishSharkRatio(),
                      decoder.getSeed(), rules.getPossibleTypes(), rules.getPossibleColors());
    }
    if (game.equals("rockpaperscissors")) {
      //   rules = new WaTorModelRules(emptyRatio, populationRatio, randomSeed, energyFish, reproduceBoundary, sharkEnergy);
      rules = new RockPaperScissorsRules(decoder.getThreshold(), decoder.getSeed());
      gridManager
              .buildGridWithRandomSeed(decoder.getEmptyRatio(), decoder.getScissorsRatio(),
                      decoder.getSeed(), rules.getPossibleTypes(), rules.getPossibleColors());
    }
    if (game.equals("foragingants")) {
      //   rules = new WaTorModelRules(emptyRatio, populationRatio, randomSeed, energyFish, reproduceBoundary, sharkEnergy);
      rules = new ForagingAntsRules();
      template = constructStartingStateForSimulation(decoder.getCoordinates());
      gridManager
          .buildGridWithTemplate(template, rules.getPossibleTypes(), rules.getPossibleColors(), decoder.getDiameter());
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
    gridManager.judgeStateOfEachCell(rules);
    //THIS IS A BAD EXAMPLE THAT NEEDS TO BE FIXED
    // I WILL PASS IN THE GRID OF COLORS OR MAYBE GRID OF TYPES
    simulationScreen.update(gridManager, decoder.getModel());
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
    simulationScreen.update(gridManager, decoder.getModel());
  }

  /**
   * Allows interactive button to start the simulation in View Component
   */
  public void startSimulation() {
    if (animation != null) {
      animation.start();
    }
  }
  public void saveSimulation(){
    stopSimulation();

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
   *
   * @param s speed of the simulation
   */
  public void setSimulationSpeed(int s) {
    if (frameDelay >= 0) {
      frameDelay = 60 - s;
    }
  }

}
