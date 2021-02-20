package cellsociety.controller;

import java.util.Map;

/**
 * This class handles parsing for all Percolation-specific parameters
 * @author Shaw Phillips
 */
public class PercDecoder extends Decoder{
    public static final String WATER_TO_EMPTY = "watertoemptyratio";
    public static final String BLOCK_RATIO = "blockratio";
    public static final String SEED = "randomseed";
    public static final String TEMP = "template";

    private float myWaterToEmptyRatio;
    private float myBlockRatio;
    private int mySeed;
    private String myTemplate;

    /**
     * Parse all Percolation parameters from XML file and initialize variables
     * @param attributes of XML file
     */
    public PercDecoder(Map<String, String> attributes){
        myWaterToEmptyRatio = Float.parseFloat(attributes.get(WATER_TO_EMPTY));
        myBlockRatio = Float.parseFloat(attributes.get(BLOCK_RATIO));
        mySeed = Integer.parseInt(attributes.get(SEED));
        myTemplate = attributes.get(TEMP);
    }
    /**
     * @return water-to-empty ratio
     */
    public float getWaterToEmptyRatio(){
        return myWaterToEmptyRatio;
    }
    /**
     * @return block ratio
     */
    public float getBlockRatio(){
        return myBlockRatio;
    }
    /**
     * @return seed
     */
    public int getSeed(){
        return mySeed;
    }
    /**
     * @return template name
     */
    public String getTemplate(){
        return myTemplate;
    }
}
