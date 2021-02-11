package cellsociety.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class Controller {

  public static final String DATA_FILE_EXTENSION = "*.xml";
  public final static FileChooser FILE_CHOOSER = makeChooser(DATA_FILE_EXTENSION);
  public static final String NUM_ROWS = "numRows";
  public static final String NUM_COLS = "numCols";
  public static final String SHAPE = "shape";
  public static final String SHAPE_COORDS = "shapeCoords";
  public static final String AUTHOR = "author";
  public static final String TITLE = "title";
  private ArrayList<Integer> coords;

  private static FileChooser makeChooser(String extension) {
    FileChooser result = new FileChooser();
    result.setTitle("Open Data File");
    result.setInitialDirectory(new File(System.getProperty("user.dir")));
    result.getExtensionFilters().setAll(new ExtensionFilter("Text Files", extension));
    return result;
  }

  public void start() {
    File dataFile = FILE_CHOOSER.showOpenDialog(null);
    XMLParser parser = new XMLParser("simulation");
    Map<String, String> attributes = parser.getAttribute(dataFile);
    setSimulationParams(attributes);
  }

  private void setSimulationParams(Map<String, String> simAttributes) {
    int numRows = Integer.parseInt(simAttributes.get(NUM_ROWS));
    int numCols = Integer.parseInt(simAttributes.get(NUM_COLS));
    String shape = simAttributes.get(SHAPE);
    String coordsAsString = simAttributes.get(SHAPE_COORDS);
    coords = new ArrayList<>();
    for (int i = 0; i < coordsAsString.length(); i++) {
      coords.add(Integer.parseInt(coordsAsString));
    }
  }
}
