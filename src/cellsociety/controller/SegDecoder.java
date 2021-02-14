package cellsociety.controller;

import java.util.Map;

public class SegDecoder extends Decoder{
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
    public SegDecoder(Map<String, String> attributes){
        myPopRatio = Float.parseFloat(attributes.get(POP_RATIO));
        myEmptyRatio = Float.parseFloat(attributes.get(EMPTY_RATIO));
        mySatThresh = Float.parseFloat(attributes.get(SAT_THRESH));
        myRandSeed = Integer.parseInt(attributes.get(RAND_SEED));
        myTemp = attributes.get(TEMP);
    }
    public float getPopRatio(){
        return myPopRatio;
    }
    public float getEmptyRatio(){
        return myEmptyRatio;
    }
    public float getSatThresh(){
        return mySatThresh;
    }
    public int getRandSeed(){
        return myRandSeed;
    }
    public String getTemp(){
        return myTemp;
    }
}
