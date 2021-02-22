package cellsociety.model;

import cellsociety.controller.Decoder;
import cellsociety.model.simulationrules.GameOfLifeRule;
import cellsociety.model.simulationrules.PercolationRules;
import cellsociety.model.simulationrules.RockPaperScissorsRules;
import cellsociety.model.simulationrules.Rules;
import cellsociety.model.simulationrules.SegregationModelRules;
import cellsociety.model.simulationrules.SpreadingOfFireRules;
import cellsociety.model.simulationrules.WaTorModelRules;
import cellsociety.model.simulationrules.foragingants.ForagingAntGridManager;
import cellsociety.model.simulationrules.foragingants.ForagingAntsRules;
import cellsociety.view.SimulationScreen;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
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

  private List<State> constructStartingStateForSimulation(List<String> coordinates) {
    template = new ArrayList<>();
    for (int i = 0; i + 1 < coordinates.size(); i += 2) {
      State state = new State(Integer.parseInt(coordinates.get(i)),
          Integer.parseInt(coordinates.get(i + 1)), rules.getPossibleTypes().get(0));
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
    simulationScreen.setGridShape(decoder.getShape());
    simulationScreen.update(gridManager);
    simulationScreen.setDescription(decoder.getMyDesc());
    runSimulation();
  }

  protected void initializeModelConstructors(String game) {

    if (game.equals("gameOfLife")) {
      rules = new GameOfLifeRule(decoder.getAliveColor(), decoder.getDeadColor());
      template = (ArrayList<State>) constructStartingStateForSimulation(decoder.getCoordinates());

      gridManager
          .buildGridWithTemplate(template, rules.getPossibleTypes(), rules.getPossibleColors());
    }
    if (game.equals("percolation")) {
      rules = new PercolationRules(decoder.getSeed(), decoder.getBlockColor(),
          decoder.getWaterColor(), decoder.getEmptyColor());
      gridManager
          .buildGridWithRandomSeed(decoder.getBlockRatio(), decoder.getWaterToEmptyRatio(),
              decoder.getSeed(), rules.getPossibleTypes(), rules.getPossibleColors());
    }
    if (game.equals("segregationmodel")) {
      rules = new SegregationModelRules(decoder.getSeed(), decoder.getSatisfactionThreshold(),
          decoder.getColorX(), decoder.getColorY(), decoder.getEmptyColor());
      gridManager
          .buildGridWithRandomSeed(decoder.getEmptyRatio(), decoder.getPopulationRatio(),
              decoder.getSeed(), rules.getPossibleTypes(), rules.getPossibleColors());

    }
    if (game.equals("spreadingoffire")) {
      rules = new SpreadingOfFireRules(decoder.getSeed(), decoder.getProbsOfCatch(),
          decoder.getEmptyColor(), decoder.getTreeColor(), decoder.getFireColor());
      gridManager
          .buildGridWithRandomSeed(decoder.getEmptyRatio(), decoder.getTreeRatio(),
              decoder.getSeed(), rules.getPossibleTypes(), rules.getPossibleColors());
    }
    if (game.equals("wator")) {
      //   rules = new WaTorModelRules(emptyRatio, populationRatio, randomSeed, energyFish, reproduceBoundary, sharkEnergy);
      rules = new WaTorModelRules(
          decoder.getSeed(), decoder.getEnergy(), decoder.getFishRate(), decoder.getSharkLives(),
          decoder.getEmptyColor(), decoder.getSharkColor(), decoder.getFishColor());
      gridManager
          .buildGridWithRandomSeed(decoder.getEmptyRatio(), decoder.getFishSharkRatio(),
              decoder.getSeed(), rules.getPossibleTypes(), rules.getPossibleColors());
    }
    if (game.equals("rockpaperscissors")) {
      //   rules = new WaTorModelRules(emptyRatio, populationRatio, randomSeed, energyFish, reproduceBoundary, sharkEnergy);
      rules = new RockPaperScissorsRules(decoder.getThreshold(), decoder.getSeed(),
          decoder.getRockColor(), decoder.getPaperColor(), decoder.getScissorsColor(),
          decoder.getEmptyColor());

      gridManager
          .buildGridWithRandomSeed(decoder.getEmptyRatio(), decoder.getScissorsRatio(),
              decoder.getSeed(), rules.getPossibleTypes(), rules.getPossibleColors());
    }
    if (game.equals("foragingants")) {
      //   rules = new WaTorModelRules(emptyRatio, populationRatio, randomSeed, energyFish, reproduceBoundary, sharkEnergy);
      int numberOfsides = 4;

      rules = new ForagingAntsRules(decoder.getNumberOfAnts(), decoder.getSeed(),
          decoder.getNumberOfSides(), decoder.getNestColor(), decoder.getAntColor(),
          decoder.getPhermoneColor(), decoder.getFoodColor(), decoder.getEmptyColor(),
          decoder.getWeakPhermoneColor(), decoder.getMoveBias(), decoder.getPhermoneAmount());
      ForagingAntGridManager foragingAntGridManager = new ForagingAntGridManager(decoder.getRows(),
          decoder.getCols(), decoder.getNumberOfSides());
      gridManager
          .buildAntGridWithTemplate(decoder.getCoordinates(), rules.getPossibleTypes(),
              rules.getPossibleColors(), decoder.getRadius(), decoder.getNumberOfSides());
    }
    //need to be fixed for a better design
  }


  protected void initializeGrid() {
    gridManager = new GridManager(decoder.getRows(), decoder.getCols(), decoder.getNumberOfSides());
  }

  /**
   * Updates the state of each cell according to logic of the model
   */
  public void updateCellState() {
    gridManager.judgeStateOfEachCell(rules);
    //THIS IS A BAD EXAMPLE THAT NEEDS TO BE FIXED
    // I WILL PASS IN THE GRID OF COLORS OR MAYBE GRID OF TYPES
    simulationScreen.update(gridManager);
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
    simulationScreen.update(gridManager);
  }

  /**
   * Allows interactive button to start the simulation in View Component
   */
  public void startSimulation() {
    if (animation != null) {
      animation.start();
    }
  }

  public void saveSimulation() throws FileNotFoundException {
    stopSimulation();
    List states = gridManager.saveSimulation();
    decoder.saveConfig(states);
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
