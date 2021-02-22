package cellsociety.controller.simulationengine;

import cellsociety.controller.Decoder;
import cellsociety.controller.grid.GridManager;
import cellsociety.model.foragingants.ForagingAntGridManager;
import cellsociety.model.foragingants.ForagingAntsRules;
import cellsociety.model.RockPaperScissorsRules;
import cellsociety.model.cell.State;
import cellsociety.model.gameoflife.GameOfLifeRule;
import cellsociety.model.percolation.PercolationRules;
import cellsociety.model.rules.Rules;
import cellsociety.model.spreadingoffire.SpreadingOfFireRules;
import cellsociety.model.segregationmodel.SegregationModelRules;
import cellsociety.model.sugarscape.NavigatingSugarScapeRules;
import cellsociety.model.sugarscape.SugarScapeGridManager;
import cellsociety.model.watormodel.WaTorModelRules;
import cellsociety.view.SimulationScreen;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
  private List states;
  private Map<String, String> colors;
  private Map<String, String> descriptors;



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
    descriptors = decoder.getUniversalParameters();
    initializeModelConstructors(descriptors.get("model"));
    gridManager.setNumberOfSides(decoder.getNumberOfSides());
    simulationScreen.setGridShape(decoder.getShape());
    simulationScreen.update(gridManager);
    simulationScreen.setDescription(descriptors.get("description"));
    runSimulation();
  }

  protected void initializeModelConstructors(String game) {

    if (game.equals("gameOfLife")) {
      rules = new GameOfLifeRule(decoder.getGOLColors());
      template = constructStartingStateForSimulation(decoder.getCoordinates());

      gridManager
              .buildGridWithTemplate(template, rules.getPossibleTypes(), rules.getPossibleColors(), 0);
    }
    if (game.equals("percolation")) {

      rules = new PercolationRules(decoder.getSeed(), decoder.getPercolationColors());
      gridManager
              .buildGridWithRandomSeed(decoder.getPercolationRatios().get("block"), decoder.getPercolationRatios().get("waterempty"),
                      decoder.getSeed(), rules.getPossibleTypes(), rules.getPossibleColors());
    }
    if (game.equals("segregationmodel")) {
      rules = new SegregationModelRules(decoder.getSeed(), decoder.getSegregationRatios().get("satisfaction"), decoder.getSegregationColors());
      gridManager
              .buildGridWithRandomSeed(decoder.getSegregationRatios().get("empty"), decoder.getSegregationRatios().get("population"),
                      decoder.getSeed(), rules.getPossibleTypes(), rules.getPossibleColors());

    }
    if (game.equals("spreadingoffire")) {
      rules = new SpreadingOfFireRules(decoder.getSeed(), decoder.getFireRatios().get("probcatch"), decoder.getFireColors());
      gridManager
              .buildGridWithRandomSeed(decoder.getFireRatios().get("empty"), decoder.getFireRatios().get("tree"),
                      decoder.getSeed(), rules.getPossibleTypes(), rules.getPossibleColors());
    }
    if (game.equals("wator")) {
      //   rules = new WaTorModelRules(emptyRatio, populationRatio, randomSeed, energyFish, reproduceBoundary, sharkEnergy);
      rules = new WaTorModelRules(decoder.getSeed(), decoder.getWaTorIntegers(), decoder.getWaTorColors());
      gridManager
              .buildGridWithRandomSeed(decoder.getWaTorRatios().get("empty"), decoder.getWaTorRatios().get("fishshark"),
                      decoder.getSeed(), rules.getPossibleTypes(), rules.getPossibleColors());
    }
    if (game.equals("rockpaperscissors")) {
      //   rules = new WaTorModelRules(emptyRatio, populationRatio, randomSeed, energyFish, reproduceBoundary, sharkEnergy);
      colors = decoder.getRPSColors();
      rules = new RockPaperScissorsRules(decoder.getRPSIntegers().get("threshold"), decoder.getRPSIntegers().get("seed"), decoder.getRPSColors());

      gridManager
              .buildGridWithRandomSeed(decoder.getRPSRatios().get("empty"), decoder.getRPSRatios().get("scissors"),
                      decoder.getSeed(), rules.getPossibleTypes(), rules.getPossibleColors());
    }
    if (game.equals("foragingants")) {
      //   rules = new WaTorModelRules(emptyRatio, populationRatio, randomSeed, energyFish, reproduceBoundary, sharkEnergy);
      colors = decoder.getAntColors();

      rules = new ForagingAntsRules(decoder.getAntIntegers(), decoder.getAntColors(), decoder.getMoveBias(), decoder.getNumberOfSides());
      ForagingAntGridManager foragingAntGridManager = new ForagingAntGridManager(decoder.getRows(), decoder.getCols());
      gridManager
          .buildAntGridWithTemplate(decoder.getCoordinates(), rules.getPossibleTypes(), rules.getPossibleColors(), decoder.getAntIntegers().get("radius"), decoder.getNumberOfSides());
    }
    if (game.equals("navigatingsugarscape")) {
      //   rules = new WaTorModelRules(emptyRatio, populationRatio, randomSeed, energyFish, reproduceBoundary, sharkEnergy);
      SugarScapeGridManager sugarScapeGridManager = new SugarScapeGridManager(decoder.getRows(), decoder.getCols());
      rules = new NavigatingSugarScapeRules(decoder.getSugarScapeIntegers(), decoder.getSugarScapeColors());
      sugarScapeGridManager
          .makeSugarScapeGridWithRandomSeed(decoder.getSugarScapeRatios().get("empty"), decoder.getSugarScapeRatios().get("patch"), decoder.getSugarScapeIntegers().get("numagents"), decoder.getSugarScapeIntegers().get("metabolism"), decoder.getSugarScapeIntegers().get("vision"),
                  decoder.getSeed(), rules.getPossibleTypes(), rules.getPossibleColors());
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
    states = gridManager.saveSimulation();
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
