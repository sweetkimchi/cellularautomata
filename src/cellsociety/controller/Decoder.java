package cellsociety.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class Decoder {

  public static final String DATA_FILE_EXTENSION = "*.xml";
  public final static FileChooser FILE_CHOOSER = makeChooser(DATA_FILE_EXTENSION);
  public static final String TYPE = "type";
  public static final String TITLE = "title";
  public static final String AUTHOR = "author";
  public static final String DESC = "description";
  public static final String NUM_ROWS = "numRows";
  public static final String NUM_COLS = "numCols";
  public static final String COORDS = "shapeCoords";
  public static final String TEMPLATE = "template";
  public static final String MODEL = "model";
  public static final String FS_RATIO = "fishsharkratio";
  public static final String EMPTY_RATIO = "emptyratio";
  public static final String SEED = "randomseed";
  public static final String FISH_RATE = "fishreproduce";
  public static final String SHARK_RATE = "sharkreproduce";
  public static final String SHARK_LIVES = "sharklives";
  public static final String ENERGY = "energyfromeating";

  private ArrayList<Integer> myType;
  private String myModel;
  private String myTitle;
  private String myAuthor;
  private String myTemplate;
  private int myRows;
  private int myCols;
  private int mySeed;
  private int fishRate;
  private int sharkRate;
  private int sharkLives;
  private int energy;
  private float myFSRatio;
  private float myEmptyRatio;
  private ArrayList<String> myCoords;

  private static FileChooser makeChooser(String extension) {
    FileChooser result = new FileChooser();
    result.setTitle("Open Data File");
    result.setInitialDirectory(new File(System.getProperty("user.dir")));
    result.getExtensionFilters().setAll(new ExtensionFilter("Text Files", extension));
    return result;
  }

  public void readValuesFromXMLFile() {
    File dataFile = FILE_CHOOSER.showOpenDialog(null);
    XMLParser parser = new XMLParser("game");
    Map<String, String> attributes = parser.getAttribute(dataFile);
    myTitle = attributes.get(TITLE);
    myAuthor = attributes.get(AUTHOR);
    myTemplate = attributes.get(TEMPLATE);
    myRows = Integer.parseInt(attributes.get(NUM_ROWS));
    myCols = Integer.parseInt(attributes.get(NUM_COLS));
    myModel = attributes.get(MODEL);
    myType = new ArrayList<>(Integer.parseInt(attributes.get(TYPE)));
    myCoords = new ArrayList<>(Arrays.asList(attributes.get(COORDS).split("[,]", 0)));
    //waTor initializers
    myFSRatio = Float.parseFloat(FS_RATIO);
    myEmptyRatio = Float.parseFloat(EMPTY_RATIO);
    mySeed = Integer.parseInt(SEED);
    fishRate = Integer.parseInt(FISH_RATE);
    sharkRate = Integer.parseInt(SHARK_RATE);
    sharkLives = Integer.parseInt(SHARK_LIVES);
    energy = Integer.parseInt(ENERGY);
//    System.out.println(myCoords);
//        for(int i=0; i<attributes.get(COORDS).length(); i++) myCoords.add(Integer.parseInt(attributes.get(COORDS).substring(i,i+1)));
  }
// general parameters
  public String getModel() {
    return myModel;
  }

  public String getTitle() {
    return myTitle;
  }

  public String getAuthor() {
    return myAuthor;
  }

  public String getTemplate() {
    return myTemplate;
  }

  public int getRows() {
    return myRows;
  }

  public int getCols() {
    return myCols;
  }

  public ArrayList<String> getCoords() {
    return myCoords;
  }
// gameOfLife getters
  public ArrayList<Integer> getType(){
    return myType;
  }

// waTor getters
  public float getFSRatio(){
    return myFSRatio;
  }
  public float getEmptyRatio(){
    return myEmptyRatio;
  }
  public int getSeed(){
    return mySeed;
  }
  public int getFishRate(){
    return fishRate;
  }
  public int getSharkRate(){
    return sharkRate;
  }
  public int getEnergy(){
    return energy;
  }




}



