package cellsociety.controller;

import java.util.Map;

/**
 * This class handles parsing for all Wa-Tor specific parameters
 *
 * @author Shaw Phillips
 */
public class WaTorDecoder extends Decoder {

  public static final String FS_RATIO = "fishsharkratio";
  public static final String EMPTY_RATIO = "emptyratio";
  public static final String SEED = "randomseed";
  public static final String FISH_RATE = "fishreproduce";
  public static final String SHARK_RATE = "sharkreproduce";
  public static final String SHARK_LIVES = "sharklives";
  public static final String ENERGY = "energyfromeating";

  private int mySeed;
  private int fishRate;
  private int sharkRate;
  private int sharkLives;
  private int energy;
  private float myFSRatio;
  private float myEmptyRatio;

  /**
   * Parse all Wa-Tor parameters from XML file and initialize variables
   *
   * @param attributes from parsed XML file
   */
  public WaTorDecoder(Map<String, String> attributes) {
    myFSRatio = Float.parseFloat(attributes.get(FS_RATIO));
    myEmptyRatio = Float.parseFloat(attributes.get(EMPTY_RATIO));
    mySeed = Integer.parseInt(attributes.get(SEED));
    fishRate = Integer.parseInt(attributes.get(FISH_RATE));
    sharkRate = Integer.parseInt(attributes.get(SHARK_RATE));
    sharkLives = Integer.parseInt(attributes.get(SHARK_LIVES));
    energy = Integer.parseInt(attributes.get(ENERGY));
  }

  /**
   * @return fish-to-shark ratio
   */
  public float getFSRatio() {
    return myFSRatio;
  }

  /**
   * @return empty ratio
   */
  public float getEmptyRatio() {
    return myEmptyRatio;
  }

  /**
   * @return seed
   */
  public int getSeed() {
    return mySeed;
  }

  /**
   * @return fish reproduction rate
   */
  public int getFishRate() {
    return fishRate;
  }

  /**
   * @return shark reproduction rate
   */
  public int getSharkRate() {
    return sharkRate;
  }

  /**
   * @return shark lives
   */
  public int getSharkLives() {
    return sharkLives;
  }

  /**
   * @return energy from eating
   */
  public int getEnergy() {
    return energy;
  }
}
