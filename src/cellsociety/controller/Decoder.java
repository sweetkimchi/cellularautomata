package cellsociety.controller;

import java.io.File;
import java.util.Map;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class Decoder {

  public static final String DATA_FILE_EXTENSION = "*.xml";
  public final static FileChooser FILE_CHOOSER = makeChooser(DATA_FILE_EXTENSION);
  public static final String MODEL = "model";
  public static final String TITLE = "title";
  public static final String AUTHOR = "author";
  public static final String DESC = "description";
  public static final String NUM_ROWS = "numRows";
  public static final String NUM_COLS = "numCols";

  private GOLDecoder golDecoder;
  private WaTorDecoder waTorDecoder;
  private String myDesc;
  private String myModel;
  private String myType;
  private String myTitle;
  private String myAuthor;
  private int myRows;
  private int myCols;

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
    myDesc = attributes.get(DESC);
    myTitle = attributes.get(TITLE);
    myAuthor = attributes.get(AUTHOR);
    myRows = Integer.parseInt(attributes.get(NUM_ROWS));
    myCols = Integer.parseInt(attributes.get(NUM_COLS));
    myModel = attributes.get(MODEL);
    if(myModel.equals("gameOfLife")){golDecoder = new GOLDecoder(attributes);}
    else if(myModel.equals("wator")) {waTorDecoder = new WaTorDecoder(attributes);}
  }
  public GOLDecoder getGOLDecoder(){
    return golDecoder;
  }
  public WaTorDecoder getWaTorDecoder(){
    return waTorDecoder;
  }
  public String getModel() {
    return myModel;
  }
  public String getTitle() {
    return myTitle;
  }
  public String getAuthor() {
    return myAuthor;
  }
  public String getDesc(){
    return myDesc;
  }
  public int getRows() {
    return myRows;
  }
  public int getCols() {
    return myCols;
  }
}