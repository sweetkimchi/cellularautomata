package cellsociety.controller;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import xml.XMLParser;
import java.io.File;
import java.util.Map;

public class Controller {
    public static final String DATA_FILE_EXTENSION = "*.xml";
    public final static FileChooser FILE_CHOOSER = makeChooser(DATA_FILE_EXTENSION);
    public static final String NUM_ROWS = "numRows";
    public static final String NUM_COLS = "numCols";
    public static final String AUTHOR = "author";
    public static final String TITLE = "title";

    public void start(){
        File dataFile = FILE_CHOOSER.showOpenDialog(null);
        XMLParser parser = new XMLParser("simulation");
        Map<String, String> attributes = parser.getAttribute(dataFile);

    }
    private void setSimulationParams(Map<String, String> attributes){
        int numRows = Integer.parseInt(attributes.get(NUM_ROWS));
        int numCols = Integer.parseInt(attributes.get(NUM_COLS));
    }
















    private static FileChooser makeChooser(String extension){
        FileChooser result = new FileChooser();
        result.setTitle("Open Data File");
        result.setInitialDirectory(new File(System.getProperty("user.dir")));
        result.getExtensionFilters().setAll(new ExtensionFilter("Text Files", extension));
        return result;
    }
}
