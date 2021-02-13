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




  private Map<String, String> myAttributes;
  private String myModel;
  private String myTitle;
  private String myAuthor;
  private String myTemplate;
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
    myAttributes = parser.getAttribute(dataFile);
    myTitle = myAttributes.get(TITLE);
    myAuthor = myAttributes.get(AUTHOR);
    myTemplate = myAttributes.get(TEMPLATE);
    myRows = Integer.parseInt(myAttributes.get(NUM_ROWS));
    myCols = Integer.parseInt(myAttributes.get(NUM_COLS));
    myModel = myAttributes.get(MODEL);
    //gameoflife initializers

    //waTor initializers

//    System.out.println(myCoords);
//        for(int i=0; i<attributes.get(COORDS).length(); i++) myCoords.add(Integer.parseInt(attributes.get(COORDS).substring(i,i+1)));
  }
  public Map<String, String> getAttributes(){
    return myAttributes;
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


// gameOfLife getters


// waTor getters





}



