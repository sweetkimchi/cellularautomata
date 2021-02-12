package cellsociety.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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

    private String myType;
    private String myTitle;
    private String myAuthor;
    private String myTemplate;
    private int myRows;
    private int myCols;
    private ArrayList<String> myCoords;

    public void readValuesFromXMLFile(){
        File dataFile = FILE_CHOOSER.showOpenDialog(null);
        XMLParser parser = new XMLParser("game");
        Map<String, String> attributes = parser.getAttribute(dataFile);
        myType = attributes.get(TYPE);
        myTitle = attributes.get(TITLE);
        myAuthor = attributes.get(AUTHOR);
        myTemplate = attributes.get(TEMPLATE);
        myRows = Integer.parseInt(attributes.get(NUM_ROWS));
        myCols = Integer.parseInt(attributes.get(NUM_COLS));
        myCoords = new ArrayList<>(Arrays.asList(attributes.get(COORDS).split("[,]", 0)));
        System.out.println(myCoords);
//        for(int i=0; i<attributes.get(COORDS).length(); i++) myCoords.add(Integer.parseInt(attributes.get(COORDS).substring(i,i+1)));
    }
    private static FileChooser makeChooser(String extension){
        FileChooser result = new FileChooser();
        result.setTitle("Open Data File");
        result.setInitialDirectory(new File(System.getProperty("user.dir")));
        result.getExtensionFilters().setAll(new ExtensionFilter("Text Files", extension));
        return result;
    }
    public String getType(){
        return myType;
    }
    public String getTitle(){
        return myTitle;
    }
    public String getAuthor(){
        return myAuthor;
    }
    public String getTemplate(){
        return myTemplate;
    }
    public int getRows(){
        return myRows;
    }
    public int getCols(){
        return myCols;
    }
    public ArrayList<String> getCoords(){
        return myCoords;
    }
}
