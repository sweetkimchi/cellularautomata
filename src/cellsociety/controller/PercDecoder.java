package cellsociety.controller;

import java.util.Map;

public class PercDecoder extends Decoder{
    public static final String WATER_TO_EMPTY = "watertoemptyratio";
    public static final String BLOCK_RATIO = "blockratio";
    public static final String SEED = "randomseed";
    public static final String TEMP = "template";

    private float myWaterToEmptyRatio;
    private float myBlockRatio;
    private int mySeed;
    private String myTemplate;

    public PercDecoder(Map<String, String> attributes){
        myWaterToEmptyRatio = Float.parseFloat(attributes.get(WATER_TO_EMPTY));
        myBlockRatio = Float.parseFloat(attributes.get(BLOCK_RATIO));
        mySeed = Integer.parseInt(attributes.get(SEED));
        myTemplate = attributes.get(TEMP);
    }
    public float getWaterToEmptyRatio(){
        return myWaterToEmptyRatio;
    }
    public float getBlockRatio(){
        return myBlockRatio;
    }
    public int getSeed(){
        return mySeed;
    }
    public String getTemplate(){
        return myTemplate;
    }
}
