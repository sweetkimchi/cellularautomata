package cellsociety.controller;

import java.util.Map;

/**
 * This class handles parsing for all Segregation-specific parameters
 *
 * @author Shaw Phillips
 */
public class SegDecoder extends Decoder {

  public static final String POP_RATIO = "populationratio";
  public static final String EMPTY_RATIO = "emptyratio";
  public static final String SAT_THRESH = "satisfactionthreshhold";
  public static final String RAND_SEED = "randomseed";
  public static final String TEMP = "template";

  private float myPopRatio;
  private float myEmptyRatio;
  private float mySatThresh;
  private int myRandSeed;
  private String myTemp;

  /**
   * Parse all segregation parameters from XML file and initialize variables
   *
   * @param attributes of XML file
   */
  public SegDecoder(Map<String, String> attributes) {
    myPopRatio = Float.parseFloat(attributes.get(POP_RATIO));
    myEmptyRatio = Float.parseFloat(attributes.get(EMPTY_RATIO));
    mySatThresh = Float.parseFloat(attributes.get(SAT_THRESH));
    myRandSeed = Integer.parseInt(attributes.get(RAND_SEED));
    myTemp = attributes.get(TEMP);
  }

  /**
   * @return population ratio
   */
  public float getPopRatio() {
    return myPopRatio;
  }

  /**
   * @return empty ratio
   */
  public float getEmptyRatio() {
    return myEmptyRatio;
  }

  /**
   * @return saturation threshold
   */
  public float getSatThresh() {
    return mySatThresh;
  }

  /**
   * @return random seed
   */
  public int getRandSeed() {
    return myRandSeed;
  }

  /**
   * @return template name
   */
  public String getTemp() {
    return myTemp;
  }
}
