package cellsociety.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * This class handles parsing for all Fire-specific parameters
 *
 * @author Shaw Phillips
 */
public class FireDecoder extends Decoder {

  public static final String COORDS = "shapeCoords";
  public static final String TEMP = "template";
  public static final String PROB = "probsOfCatch";
  public static final String SEED = "randomseed";
  public static final String EMPTY_RATIO = "emptyratio";
  public static final String TREE_RATIO = "treeratio";

  private ArrayList<String> myCoords;
  private String myTemplate;
  private float myProb;
  private int mySeed;
  private float emptyRatio;
  private float treeRatio;

  /**
   * Parse all Fire parameters from XML file and initialize variables
   *
   * @param attributes of XML file
   */
  public FireDecoder(Map<String, String> attributes) {
    myCoords = new ArrayList<>(Arrays.asList(attributes.get(COORDS).split("[,]", 0)));
    myTemplate = attributes.get(TEMP);
    myProb = Float.parseFloat(attributes.get(PROB));
    mySeed = Integer.parseInt(attributes.get(SEED));
    emptyRatio = Float.parseFloat(attributes.get(EMPTY_RATIO));
    treeRatio = Float.parseFloat(attributes.get(TREE_RATIO));
  }

  /**
   * @return coordinates
   */
  public ArrayList<String> getCoords() {
    return myCoords;
  }

  /**
   * @return template name
   */
  public String getTemplate() {
    return myTemplate;
  }

  /**
   * @return probability of catching
   */
  public float getProb() {
    return myProb;
  }

  /**
   * @return seed
   */
  public int getSeed() {
    return mySeed;
  }

  /**
   * @return empty ratio
   */
  public float getEmptyRatio() {
    return emptyRatio;
  }

  /**
   * @return tree ratio
   */
  public float getTreeRatio() {
    return treeRatio;
  }
}
