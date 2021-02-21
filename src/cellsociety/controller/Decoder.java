package cellsociety.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * This class handles XML file selection and initialization of parameters.
 *
 * @author Shaw Phillips
 */
public class Decoder {
  public static final String DATA_FILE_EXTENSION = "*.xml";
  public final static FileChooser FILE_CHOOSER = makeChooser(DATA_FILE_EXTENSION);
  public static final String HEAD = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n <data game=\"game\">";
  public static final String TITLE = "title";
  public static final String AUTHOR = "author";
  public static final String DESC = "description";
  public static final String NUM_ROWS = "numRows";
  public static final String NUM_COLS = "numCols";
  public static final String MODEL = "model";
  public static final String TEMPLATE = "template";
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
  public static final List<String> MODEL_TYPES = List.of("spreadingoffire", "segregationmodel", "percolation", "wator", "gameOfLife", "rockpaperscissors", "foragingants", "langton", "sugarscape");
  public static final String GOLDefaultShape = "25,25,25,26,25,27";
  public static final String FireDefaultShape = "21,20,20,21,21,21,22,21";
  public static final String SHAPE = "shape";
  public static final String SIDES = "numberofsides";
  public static final String COLOR = "color";
  //             Sample Text Names               //
  public static final String RATIO1 = "ratio1";
  public static final String RATIO2 = "ratio2";
  public static final String RATIO3 = "ratio3";
  public static final String RATIO4 = "ratio4";
  public static final String INTEGER1 = "integer1";
  public static final String INTEGER2 = "integer2";
  public static final String INTEGER3 = "integer3";
  public static final String INTEGER4 = "integer4";
  public static final String STRING1 = "string1";
  public static final String STRING2 = "string2";

  private int radius;
  private String shape;
  private String color;
  private int numberOfSides;
  private ArrayList<String> coordinates;
  private String template;
  private int seed;
  private int fishRate;
  private int sharkRate;
  private int sharkLives;
  private int energy;
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
  private int threshold;
  // Sample Ratios, Strings, and Integers //
  private float ratio1;
  private float ratio2;
  private float ratio3;
  private float ratio4;
  private int integer1;
  private int integer2;
  private int integer3;
  private int integer4;
  private String string1;
  private String string2;
  private String description;
  private String model;
  private String title;
  private String author;
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
    try{
      description = attributes.get(DESC);
      title = attributes.get(TITLE);
      author = attributes.get(AUTHOR);
      numRows = Integer.parseInt(attributes.get(NUM_ROWS));
      numColumns = Integer.parseInt(attributes.get(NUM_COLS));
      model = attributes.get(MODEL);
      //           Shape, numSides, and Color                //
      //shape = attributes.get(SHAPE);
      //numberOfSides = Integer.parseInt(attributes.get(SIDES));
      //color = attributes.get(COLOR);
    }
    catch(Exception e){
      Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid Parameter(s)");
      alert.showAndWait()
              .filter(response -> response == ButtonType.OK)
              .ifPresent(response -> alert.close());
    }
    if(model == null || !MODEL_TYPES.contains(model)){
      Alert modelAlert = new Alert(Alert.AlertType.ERROR, "Invalid Model");
      modelAlert.showAndWait()
              .filter(response -> response == ButtonType.OK)
              .ifPresent(response -> modelAlert.close());
    }
    switch (model) {
      case "gameOfLife" -> initializeGOL(attributes);
      case "wator" -> initializeWaTor(attributes);
      case "segregationmodel" -> initializeSegregation(attributes);
      case "spreadingoffire" -> initializeFire(attributes);
      case "percolation" -> initializePercolation(attributes);
      case "rockpaperscissors" -> initializeRPS(attributes);
      case "foragingants" -> initializeForagingAnts(attributes);
      case "langton" -> initializeLangton(attributes);
      case "sugarscape" -> initializeSugarScape(attributes);
    }
  }
  private void saveConfig(Map<String, String> config) throws FileNotFoundException {
    Map<String, String> savedConfig = new HashMap<>();
    FileChooser chooser = new FileChooser();
    chooser.setTitle("Choose Directory");
    chooser.getExtensionFilters().setAll(new FileChooser.ExtensionFilter("text file ", DATA_FILE_EXTENSION));
    chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
    File file = null;
    while(file == null) file = chooser.showSaveDialog(null);
    createConfigFile(file, savedConfig);
  }
  private void createConfigFile(File file, Map<String, String> attributes) throws FileNotFoundException {
    PrintWriter writer = new PrintWriter(file);
    writer.println(HEAD);
    for(String s : attributes.keySet()){
      if(attributes.get(s) == null) continue;
      writer.print("<" + s + ">");
      writer.print(attributes.get(s));
      writer.println("</" + s + ">");
    }
    writer.println("</data>");
    writer.close();
  }
  private void initializeGOL(Map<String, String> attributes){
    coordinates = new ArrayList<>(Arrays.asList(attributes.getOrDefault(COORDINATES, GOLDefaultShape).split("[,]", 0)));
    template = attributes.get(TEMPLATE);
  }
  private void initializeWaTor(Map<String, String> attributes){
    fishSharkRatio = Float.parseFloat(attributes.getOrDefault(FISH_SHARK_RATIO, "0.5"));
    emptyRatio = Float.parseFloat(attributes.getOrDefault(EMPTY_RATIO, "0.3"));
    seed = Integer.parseInt(attributes.getOrDefault(SEED, "0"));
    fishRate = Integer.parseInt(attributes.getOrDefault(FISH_RATE, "5"));
    sharkRate = Integer.parseInt(attributes.getOrDefault(SHARK_RATE, "5"));
    sharkLives = Integer.parseInt(attributes.getOrDefault(SHARK_LIVES, "3"));
    energy = Integer.parseInt(attributes.getOrDefault(ENERGY, "4"));
  }
  private void initializePercolation(Map<String, String> attributes){
    waterToEmptyRatio = Float.parseFloat(attributes.getOrDefault(WATER_EMPTY_RATIO, ".01"));
    blockRatio = Float.parseFloat(attributes.getOrDefault(BLOCK_RATIO, "0.5"));
    seed = Integer.parseInt(attributes.getOrDefault(SEED, "100"));
    template = attributes.getOrDefault(TEMPLATE, "random_one");
  }
  private void initializeSegregation(Map<String, String> attributes){
    populationRatio = Float.parseFloat(attributes.getOrDefault(POPULATION_RATIO, "0.5"));
    emptyRatio = Float.parseFloat(attributes.getOrDefault(EMPTY_RATIO, "0.1"));
    satisfactionThreshold = Float.parseFloat(attributes.getOrDefault(SATISFACTION_THRESHOLD, "0.6"));
    seed = Integer.parseInt(attributes.getOrDefault(SEED, "100"));
    template = attributes.getOrDefault(TEMPLATE, "random_one");
  }
  private void initializeFire(Map<String, String> attributes){
    coordinates = new ArrayList<>(Arrays.asList(attributes.getOrDefault(COORDINATES, FireDefaultShape).split("[,]", 0)));
    template = attributes.getOrDefault(TEMPLATE, "basicfire");
    probsOfCatch = Float.parseFloat(attributes.getOrDefault(PROB_OF_CATCH, "0.3"));
    seed = Integer.parseInt(attributes.getOrDefault(SEED, "100"));
    emptyRatio = Float.parseFloat(attributes.getOrDefault(EMPTY_RATIO, "0.1"));
    treeRatio = Float.parseFloat(attributes.getOrDefault(TREE_RATIO, "0.8"));
  }
  private void initializeRPS(Map<String, String> attributes){
    rockRatio = Float.parseFloat(attributes.getOrDefault(ROCK_RATIO, "0.33"));
    scissorsRatio = Float.parseFloat(attributes.getOrDefault(SCISSORS_RATIO, "0.5"));
    seed = Integer.parseInt(attributes.getOrDefault(SEED, "100"));
    emptyRatio = Float.parseFloat(attributes.getOrDefault(EMPTY_RATIO, "0"));
    threshold = Integer.parseInt(attributes.getOrDefault(THRESHOLD, "3"));
  }
  private void initializeForagingAnts(Map<String, String> attributes){
    coordinates = new ArrayList<>(Arrays.asList(attributes.getOrDefault(COORDINATES, GOLDefaultShape).split("[,]", 0)));
    //ratio2 = Float.parseFloat(attributes.getOrDefault("ratio2", "defaultratio2);
    radius = Integer.parseInt(attributes.getOrDefault("radius", "5"));
    numberOfAnts = Integer.parseInt(attributes.getOrDefault("numberofants", "50"));
    //string1 = attributes.get("string1");
    //string2 = attributes.get("string2");
  }
  private void initializeLangton(Map<String, String> attributes){
    //ratio1 = Float.parseFloat(attributes.getOrDefault("ratio1", "defaultratio1");
    //ratio2 = Float.parseFloat(attributes.getOrDefault("ratio2", "defaultratio2);
    //integer1 = Integer.parseInt(attributes.getOrDefault("integer1", "defaultinteger1");
    //integer2 = Integer.parseInt(attributes.getOrDefault("integer2", :defaultinteger2");
    //string1 = attributes.get("string1");
    //string2 = attributes.get("string2");
  }
  private void initializeSugarScape(Map<String, String> attributes){
    //ratio1 = Float.parseFloat(attributes.getOrDefault("ratio1", "defaultratio1");
    //ratio2 = Float.parseFloat(attributes.getOrDefault("ratio2", "defaultratio2);
    //integer1 = Integer.parseInt(attributes.getOrDefault("integer1", "defaultinteger1");
    //integer2 = Integer.parseInt(attributes.getOrDefault("integer2", :defaultinteger2");
    //string1 = attributes.get("string1");
    //string2 = attributes.get("string2");
  }
  public ArrayList<String> getCoordinates(){ return coordinates;}
  public int getSeed() {return seed;}
  public float getBlockRatio(){return blockRatio;}
  public float getWaterToEmptyRatio(){return waterToEmptyRatio;}
  public float getSatisfactionThreshold(){return satisfactionThreshold;}
  public float getEmptyRatio(){return emptyRatio;}
  public float getPopulationRatio(){return populationRatio;}
  public float getProbsOfCatch(){return probsOfCatch;}
  public float getTreeRatio(){return treeRatio;}
  public int getEnergy(){return energy;}
  public int getFishRate(){return fishRate;}
  public int getSharkLives(){return sharkLives;}
  public float getFishSharkRatio(){return fishSharkRatio;}
  public float getRockRatio(){return rockRatio;}
  public float getScissorsRatio(){return scissorsRatio;}
  public int getThreshold(){return threshold;}
  //            generic getters           //
  //public float getRatio1(){return ratio1;}
  //public float getRatio2(){return ratio2;}
  //public float getRatio3(){return ratio3;}
  //public float getRatio4(){return ratio4;}
  public int getRadius(){return radius;}
  public int getNumberOfAnts(){return numberOfAnts;}
  //public int getInteger3(){return integer3;}
  //public int getInteger4(){return integer4;}
  //public int getString1(){return string1;}
  //public int getString1(){return string1;}

  /**
   * @return model name
   */
  public String getModel() {
    return model;
  }
  /**
   * @return title
   */
  public String getTitle() {
    return title;
  }
  /**
   * @return author
   */
  public String getAuthor() {
    return author;
  }
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
  /**
   * @return description
   */
  public String getMyDesc() {
    return description;
  }
}
