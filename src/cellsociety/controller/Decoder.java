package cellsociety.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.*;

import cellsociety.controller.simulationengine.SimulationEngine;
import cellsociety.model.cell.State;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * This class handles XML file selection, initialization of parameters and saving of the simulation.
 *
 * @author Shaw Phillips
 */
public class Decoder {
  public static final String DATA_FILE_EXTENSION = "*.xml";
  public final static FileChooser FILE_CHOOSER = makeChooser(DATA_FILE_EXTENSION);
  public static final String HEAD = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n<data game=\"game\">";
  public static final String TITLE = "title";
  public static final String AUTHOR = "author";
  public static final String DESC = "description";
  public static final String NUM_ROWS = "numRows";
  public static final String NUM_COLS = "numCols";
  public static final String MODEL = "model";
  public static final String COORDINATES = "shapeCoords";
  public static final String FISH_SHARK_RATIO = "fishsharkratio";
  public static final String FISH_RATE = "fishreproduce";
  public static final String SHARK_RATE = "sharkreproduce";
  public static final String SHARK_LIVES = "sharklives";
  public static final String ENERGY = "energyfromeating";
  public static final String WATER_EMPTY_RATIO = "watertoemptyratio";
  public static final String BLOCK_RATIO = "blockratio";
  public static final String EMPTY_RATIO = "emptyratio";
  public static final String POPULATION_RATIO = "populationratio";
  public static final String SATISFACTION_THRESHOLD = "satisfactionthreshold";
  public static final String PROB_OF_CATCH = "probsOfCatch";
  public static final String TREE_RATIO = "treeratio";
  public static final String SEED = "randomseed";
  public static final String ROCK_RATIO = "rockratio";
  public static final String SCISSORS_RATIO = "scissorsratio";
  public static final String THRESHOLD = "threshhold";
  public static final String GOLDefaultShape = "25,25,25,26,25,27";
  public static final String FireDefaultShape = "21,20,20,21,21,21,22,21";
  public static final String BLOCK_COLOR = "blockcolor";
  public static final String EMPTY_COLOR = "emptycolor";
  public static final String WATER_COLOR = "watercolor";
  public static final String SHAPE = "shape";
  public static final String SIDES = "numberofsides";
  public static final List<String> MODEL_TYPES = List.of("spreadingoffire", "navigatingsugarscape", "segregationmodel", "percolation", "wator", "gameOfLife", "rockpaperscissors", "foragingants", "langton", "sugarscape");
  public static final List<String> VALID_SHAPES = List.of("square", "triangle", "hexagon");
  public static final List<Integer> squareSides = List.of(4, 8);
  public static final List<Integer> triangleSides = List.of(3, 12);
  public static final int hexagonSides = 6;
  private Map<String, String> universal;
  private Map<String, String> colors;
  private Map<String, Integer> integerMap;
  private Map<String, Float> ratios;
  private List<String> stringStates;
  private ArrayList<String> coordinates;
  private String shape;
  private String blockColor;
  private String emptyColor;
  private String waterColor;
  private String colorX;
  private String colorY;
  private String fireColor;
  private String treeColor;
  private String fishColor;
  private String sharkColor;
  private String rockColor;
  private String paperColor;
  private String scissorsColor;
  private String aliveColor;
  private String deadColor;
  private String nestColor;
  private String antColor;
  private String phermoneColor;
  private String foodColor;
  private String weakPhermoneColor;
  private String fullSugarColor;
  private String lowSugarColor;
  private String agentColor;
  private String description;
  private String model;
  private String title;
  private String author;
  private float fishSharkRatio;
  private float emptyRatio;
  private float waterToEmptyRatio;
  private float blockRatio;
  private float populationRatio;
  private float satisfactionThreshold;
  private float treeRatio;
  private float probsOfCatch;
  private float rockRatio;
  private float scissorsRatio;
  private float patchRatio;
  private float moveBias;
  private int numberOfSides;
  private int seed;
  private int fishRate;
  private int sharkRate;
  private int sharkLives;
  private int energy;
  private int threshold;
  private int phermoneAmount;
  private int numAgents;
  private int maxSugar;
  private int growBackSugar;
  private int metabolism;
  private int vision;
  private int radius;
  private int numRows;
  private int numColumns;
  private int numberOfAnts;

  private static FileChooser makeChooser(String extension) {
    FileChooser result = new FileChooser();
    result.setTitle("Open Data File");
    result.setInitialDirectory(new File(System.getProperty("user.dir")));
    result.getExtensionFilters().setAll(new ExtensionFilter("Text Files", extension));
    return result;
  }
  /**
   *  Open window to choose XML file, initialize parser, parse universal values, and determine the model
   */
  public void readValuesFromXMLFile() {
    File dataFile = FILE_CHOOSER.showOpenDialog(null);
    XMLParser parser = new XMLParser("game");
    Map<String, String> attributes = parser.getAttribute(dataFile);
    try {
      description = attributes.get(DESC);
      title = attributes.get(TITLE);
      author = attributes.get(AUTHOR);
      numRows = Integer.parseInt(attributes.get(NUM_ROWS));
      numColumns = Integer.parseInt(attributes.get(NUM_COLS));
      model = attributes.get(MODEL);
      shape = attributes.get(SHAPE);
      numberOfSides = Integer.parseInt(attributes.get(SIDES));
    } catch (Exception e) {
      Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid Universal Parameter(s)");
      alert.showAndWait()
              .filter(response -> response == ButtonType.OK)
              .ifPresent(response -> alert.close());
    }
    checkValidParameters();
    switch (model) {
      case "gameOfLife" -> initializeGOL(attributes);
      case "wator" -> initializeWaTor(attributes);
      case "segregationmodel" -> initializeSegregation(attributes);
      case "spreadingoffire" -> initializeFire(attributes);
      case "percolation" -> initializePercolation(attributes);
      case "rockpaperscissors" -> initializeRPS(attributes);
      case "foragingants" -> initializeForagingAnts(attributes);
      case "sugarscape" -> initializeSugarScape(attributes);
    }
  }
  private void checkValidParameters() {
    if (!MODEL_TYPES.contains(model)) {
      Alert modelAlert = new Alert(Alert.AlertType.ERROR, "Invalid Model");
      modelAlert.showAndWait()
              .filter(response -> response == ButtonType.OK)
              .ifPresent(response -> modelAlert.close());
    }
    if(!VALID_SHAPES.contains(shape)){
      Alert shapeAlert = new Alert(Alert.AlertType.ERROR, "Invalid Shape - must be square, triangle, or hexagon");
      shapeAlert.showAndWait()
              .filter(response -> response == ButtonType.OK)
              .ifPresent(response -> shapeAlert.close());
    }
    else{
      if(shape.equals("square") && !squareSides.contains(numberOfSides) ||
          shape.equals("triangle") && !triangleSides.contains(numberOfSides) ||
          shape.equals("hexagon") && numberOfSides != 6){
        Alert numSidesAlert = new Alert(Alert.AlertType.ERROR, "Invalid Number of Sides - Square (4, 8), Triangle (3, 12), Hexagon (6)");
        numSidesAlert.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> numSidesAlert.close());
      }
    }
  }

  /**
   * Opens save directory for saving current state of simulation as XML file
   * @param stateOfSimulation
   * @throws FileNotFoundException
   */
  public void saveConfig(List<String> stateOfSimulation) throws FileNotFoundException {
    FileChooser chooser = new FileChooser();
    chooser.setTitle("Choose Directory");
    chooser.getExtensionFilters().setAll(new FileChooser.ExtensionFilter("text file ", DATA_FILE_EXTENSION));
    chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
    File file = chooser.showSaveDialog(null);
    createConfigFile(file, stateOfSimulation);
  }
  private void createConfigFile(File file, List<String> states) throws FileNotFoundException {
    PrintWriter writer = new PrintWriter(file);
    writer.println(HEAD);
    writer.println("<buildfromtemplate>1</buildfromtemplate>");
    writer.print("<states>");
    for(int i=0; i<states.size(); i+=6){
      writer.print("[");
      for(int j=0; j<6; j++){
        if(states.get(i + j) == null) continue;
        writer.print(states.get(i + j));
      }
      writer.print("],");
    }
    writer.println("</states>");
    writer.println("</data>");
    writer.close();
  }
  private void loadTemplate(Map<String, String> attributes){
    String[] str = attributes.get("states").split(",");
    stringStates = new ArrayList<String>();
    stringStates = Arrays.asList(str);
  }
  /**
   * @return parsed String array of simulation state
   */
  public List<String> returnTemplate(){return stringStates;}
  private void initializeGOL(Map<String, String> attributes){
    coordinates = new ArrayList<>(Arrays.asList(attributes.getOrDefault(COORDINATES, GOLDefaultShape).split("[,]", 0)));
    aliveColor = attributes.get("alivecolor").equals("") ? "black" : attributes.get("alivecolor");
    deadColor = attributes.get("deadcolor").equals("") ? "lightgrey" : attributes.get("deadcolor");
  }
  private void initializeWaTor(Map<String, String> attributes){
    fishSharkRatio = Float.parseFloat(attributes.getOrDefault(FISH_SHARK_RATIO, "0.5"));
    emptyRatio = Float.parseFloat(attributes.getOrDefault(EMPTY_RATIO, "0.3"));
    seed = Integer.parseInt(attributes.getOrDefault(SEED, "0"));
    fishRate = Integer.parseInt(attributes.getOrDefault(FISH_RATE, "5"));
    sharkRate = Integer.parseInt(attributes.getOrDefault(SHARK_RATE, "5"));
    sharkLives = Integer.parseInt(attributes.getOrDefault(SHARK_LIVES, "3"));
    energy = Integer.parseInt(attributes.getOrDefault(ENERGY, "4"));
    emptyColor = attributes.get(EMPTY_COLOR).equals("") ? "lightgrey" : attributes.get(EMPTY_COLOR);
    fishColor = attributes.get("fishcolor").equals("") ? "green" : attributes.get("fishcolor");
    sharkColor = attributes.get("sharkcolor").equals("") ? "blue" : attributes.get("sharkcolor");
  }
  private void initializePercolation(Map<String, String> attributes){
    waterToEmptyRatio = Float.parseFloat(attributes.getOrDefault(WATER_EMPTY_RATIO, ".01"));
    blockRatio = Float.parseFloat(attributes.getOrDefault(BLOCK_RATIO, "0.5"));
    seed = Integer.parseInt(attributes.getOrDefault(SEED, "100"));
    blockColor = attributes.get(BLOCK_COLOR).equals("") ? "black" : attributes.get(BLOCK_COLOR);
    emptyColor = attributes.get(EMPTY_COLOR).equals("") ? "lightgrey" : attributes.get(EMPTY_COLOR);
    waterColor = attributes.get(WATER_COLOR).equals("") ? "blue" : attributes.get(WATER_COLOR);
  }
  private void initializeSegregation(Map<String, String> attributes){
    populationRatio = Float.parseFloat(attributes.getOrDefault(POPULATION_RATIO, "0.5"));
    emptyRatio = Float.parseFloat(attributes.getOrDefault(EMPTY_RATIO, "0.1"));
    satisfactionThreshold = Float.parseFloat(attributes.getOrDefault(SATISFACTION_THRESHOLD, "0.6"));
    seed = Integer.parseInt(attributes.getOrDefault(SEED, "100"));
    emptyColor = attributes.get(EMPTY_COLOR).equals("") ? "lightgrey" : attributes.get(EMPTY_COLOR);
    colorX = attributes.get("agentxcolor").equals("") ? "red" : attributes.get("agentxcolor");
    colorY = attributes.get("agentycolor").equals("") ? "blue" : attributes.get("agentycolor");
  }
  private void initializeFire(Map<String, String> attributes){
    coordinates = new ArrayList<>(Arrays.asList(attributes.getOrDefault(COORDINATES, FireDefaultShape).split("[,]", 0)));
    probsOfCatch = Float.parseFloat(attributes.getOrDefault(PROB_OF_CATCH, "0.3"));
    seed = Integer.parseInt(attributes.getOrDefault(SEED, "100"));
    emptyRatio = Float.parseFloat(attributes.getOrDefault(EMPTY_RATIO, "0.1"));
    treeRatio = Float.parseFloat(attributes.getOrDefault(TREE_RATIO, "0.8"));
    emptyColor = attributes.get(EMPTY_COLOR).equals("") ? "yellow" : attributes.get(EMPTY_COLOR);
    treeColor = attributes.get("treecolor").equals("") ? "green" : attributes.get("treecolor");
    fireColor = attributes.get("firecolor").equals("") ? "red" : attributes.get("firecolor");
  }
  private void initializeRPS(Map<String, String> attributes){
    rockRatio = Float.parseFloat(attributes.getOrDefault(ROCK_RATIO, "0.33"));
    scissorsRatio = Float.parseFloat(attributes.getOrDefault(SCISSORS_RATIO, "0.5"));
    seed = Integer.parseInt(attributes.getOrDefault(SEED, "100"));
    emptyRatio = Float.parseFloat(attributes.getOrDefault(EMPTY_RATIO, "0"));
    threshold = Integer.parseInt(attributes.getOrDefault(THRESHOLD, "3"));
    emptyColor = attributes.get(EMPTY_COLOR).equals("") ? "black" : attributes.get(EMPTY_COLOR);
    paperColor = attributes.get("papercolor").equals("") ? "blue" : attributes.get("papercolor");
    rockColor = attributes.get("rockcolor").equals("") ? "red" : attributes.get("rockcolor");
    scissorsColor = attributes.get("scissorscolor").equals("") ? "lightgrey" : attributes.get("scissorscolor");
  }
  private void initializeForagingAnts(Map<String, String> attributes){
    coordinates = new ArrayList<>(Arrays.asList(attributes.getOrDefault(COORDINATES, GOLDefaultShape).split("[,]", 0)));
    moveBias = Float.parseFloat(attributes.getOrDefault("movebias", "0.96"));
    phermoneAmount = Integer.parseInt(attributes.getOrDefault("phermoneamount", "30"));
    radius = Integer.parseInt(attributes.getOrDefault("radius", "5"));
    numberOfAnts = Integer.parseInt(attributes.getOrDefault("numberofants", "50"));
    nestColor = attributes.get("nestcolor").equals("") ? "green" : attributes.get("nestcolor");
    antColor = attributes.get("antcolor").equals("") ? "red" : attributes.get("antcolor");
    phermoneColor = attributes.get("phermonecolor").equals("") ? "blue" : attributes.get("phermonecolor");
    foodColor = attributes.get("foodcolor").equals("") ? "lightgrey" : attributes.get("foodcolor");
    emptyColor = attributes.get(EMPTY_COLOR).equals("") ? "black" : attributes.get(EMPTY_COLOR);
    weakPhermoneColor = attributes.get("weakphermonecolor").equals("") ? "skyblue" : attributes.get("weakphermonecolor");
  }
  private void initializeSugarScape(Map<String, String> attributes){
    seed = Integer.parseInt(attributes.getOrDefault(SEED, "10"));
    emptyRatio = Float.parseFloat(attributes.getOrDefault(EMPTY_RATIO, "0"));
    patchRatio = Float.parseFloat(attributes.getOrDefault("patchratio", "0.7"));
    numAgents = Integer.parseInt(attributes.getOrDefault("numberofagents", "30"));
    maxSugar = Integer.parseInt(attributes.getOrDefault("maxsugar", "5"));
    growBackSugar = Integer.parseInt(attributes.getOrDefault("growbacksugar", "1"));
    metabolism = Integer.parseInt(attributes.getOrDefault("metabolism", "2"));
    vision = Integer.parseInt(attributes.getOrDefault("vision", "2"));
    fullSugarColor = attributes.get("fullsugarcolor").equals("") ? "orange" : attributes.get("fullsugarcolor");
    lowSugarColor = attributes.get("lowsugarcolor").equals("") ? "white" : attributes.get("lowsugarcolor");
    agentColor = attributes.get("agentcolor").equals("") ? "red" : attributes.get("agentcolor");
    emptyColor = attributes.get(EMPTY_COLOR).equals("") ? "black" : attributes.get(EMPTY_COLOR);
  }
  /**
   * @return map of Game of Life colors
   */
  public Map<String, String> getGOLColors(){
    colors = new HashMap<>();
    colors.put("alive", aliveColor);
    colors.put("dead", deadColor);
    return colors;
  }
  /**
   * @return map of Wa-Tor colors
   */
  public Map<String, String> getWaTorColors(){
    colors = new HashMap<>();
    colors.put("empty", emptyColor);
    colors.put("fish", fishColor);
    colors.put("shark", sharkColor);
    return colors;
  }
  /**
   * @return map of Wa-Tor integer parameters
   */
  public Map<String, Integer> getWaTorIntegers(){
    integerMap = new HashMap<>();
    integerMap.put("seed", seed);
    integerMap.put("fishrate", fishRate);
    integerMap.put("sharkrate", sharkRate);
    integerMap.put("sharklives", sharkLives);
    integerMap.put("energy", energy);
    return integerMap;
  }
  /**
   * @return map of Wa-Tor ratios
   */
  public Map<String, Float> getWaTorRatios(){
    ratios = new HashMap<>();
    ratios.put("fishshark", fishSharkRatio);
    ratios.put("empty", emptyRatio);
    return ratios;
  }
  /**
   * @return map of Percolation colors
   */
  public Map<String, String> getPercolationColors(){
    colors = new HashMap<>();
    colors.put("block", blockColor);
    colors.put("empty", emptyColor);
    colors.put("water", waterColor);
    return colors;
  }
  /**
   * @return map of Percolation ratios
   */
  public Map<String, Float> getPercolationRatios(){
    ratios = new HashMap<>();
    ratios.put("waterempty", waterToEmptyRatio);
    ratios.put("block", blockRatio);
    return ratios;
  }
  /**
   * @return map of Segregation colors
   */
  public Map<String, String> getSegregationColors(){
    colors = new HashMap<>();
    colors.put("empty", emptyColor);
    colors.put("agentx", colorX);
    colors.put("agenty", colorY);
    return colors;
  }
  /**
   * @return map of Segregation ratios
   */
  public Map<String, Float> getSegregationRatios(){
    ratios = new HashMap<>();
    ratios.put("population", populationRatio);
    ratios.put("empty", emptyRatio);
    ratios.put("satisfaction", satisfactionThreshold);
    return ratios;
  }
  /**
   * @return map of Spreading of Fire colors
   */
  public Map<String, String> getFireColors(){
    colors = new HashMap<>();
    colors.put("empty", emptyColor);
    colors.put("tree", treeColor);
    colors.put("fire", fireColor);
    return colors;
  }
  /**
   * @return map of Spreading of Fire ratios
   */
  public Map<String, Float> getFireRatios(){
    ratios = new HashMap<>();
    ratios.put("probcatch", probsOfCatch);
    ratios.put("empty", emptyRatio);
    ratios.put("tree", treeRatio);
    return ratios;
  }
  /**
   * @return map of Rock-Paper-Scissors colors
   */
  public Map<String, String> getRPSColors(){
    colors = new HashMap<>();
    colors.put("empty", emptyColor);
    colors.put("paper", paperColor);
    colors.put("rock", rockColor);
    colors.put("scissors", scissorsColor);
    return colors;
  }
  /**
   * @return map of Rock-Paper-Scissors ratios
   */
  public Map<String, Float> getRPSRatios(){
    ratios = new HashMap<>();
    ratios.put("rock", rockRatio);
    ratios.put("scissors", scissorsRatio);
    ratios.put("empty", emptyRatio);
    return ratios;
  }
  /**
   * @return map of Rock-Paper-Scissors integer parameters
   */
  public Map<String, Integer> getRPSIntegers(){
    integerMap = new HashMap<>();
    integerMap.put("seed", seed);
    integerMap.put("threshold", threshold);
    return integerMap;
  }
  /**
   * @return map of Foraging Ants colors
   */
  public Map<String, String> getAntColors(){
    colors = new HashMap<>();
    colors.put("nest", nestColor);
    colors.put("ant", antColor);
    colors.put("phermone", phermoneColor);
    colors.put("weakphermone", weakPhermoneColor);
    colors.put("food", foodColor);
    colors.put("empty", emptyColor);
    return colors;
  }
  /**
   * @return map of Foraging Ants integer parameters
   */
  public Map<String, Integer> getAntIntegers(){
    integerMap = new HashMap<>();
    integerMap.put("phermone", phermoneAmount);
    integerMap.put("radius", radius);
    integerMap.put("numants", numberOfAnts);
    integerMap.put("seed", seed);
    return integerMap;
  }
  /**
   * @return map of SugarScape colors
   */
  public Map<String, String> getSugarScapeColors(){
    colors = new HashMap<>();
    colors.put("full", fullSugarColor);
    colors.put("low", lowSugarColor);
    colors.put("agent", agentColor);
    colors.put("empty", emptyColor);
    return colors;
  }
  /**
   * @return map of SugarScape integer parameters
   */
  public Map<String, Integer> getSugarScapeIntegers(){
    integerMap = new HashMap<>();
    integerMap.put("seed", seed);
    integerMap.put("numagents", numAgents);
    integerMap.put("maxsugar", maxSugar);
    integerMap.put("growbacksugar", growBackSugar);
    integerMap.put("metabolism", metabolism);
    integerMap.put("vision", vision);
    return integerMap;
  }
  /**
   * @return map of SugarScape ratios
   */
  public Map<String, Float> getSugarScapeRatios(){
    ratios = new HashMap<>();
    ratios.put("empty", emptyRatio);
    ratios.put("patch", patchRatio);
    return ratios;
  }
  /**
   * @return map of universal parameters for every simulation
   */
  public Map<String, String> getUniversalParameters(){
    universal = new HashMap<>();
    universal.put("model", model);
    universal.put("title", title);
    universal.put("author", author);
    universal.put("description", description);
    return universal;
  }
  /**
   * @return model seed
   */
  public int getSeed() {return seed;}
  /**
   * @return coordinates for starting shape
   */
  public ArrayList<String> getCoordinates(){ return coordinates;}
  /**
   * @return move bias
   */
  public float getMoveBias(){return moveBias;}
  /**
   * @return number of sides for simulation shape
   */
  public int getNumberOfSides(){return numberOfSides;}
  /**
   * @return cell shape
   */
  public String getShape(){return shape;}
  /**
   * @return number of rows
   */
  public int getRows() {
    return numRows;
  }
  /**
   * @return number of columns
   */
  public int getCols() {
    return numColumns;
  }
}
