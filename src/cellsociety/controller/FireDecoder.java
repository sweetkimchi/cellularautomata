package cellsociety.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class FireDecoder extends Decoder{
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

    public FireDecoder(Map<String, String> attributes){
        myCoords = new ArrayList<>(Arrays.asList(attributes.get(COORDS).split("[,]", 0)));
        myTemplate = attributes.get(TEMP);
        myProb = Float.parseFloat(attributes.get(PROB));
        mySeed = Integer.parseInt(attributes.get(SEED));
        emptyRatio = Float.parseFloat(attributes.get(EMPTY_RATIO));
        treeRatio = Float.parseFloat(attributes.get(TREE_RATIO));
    }
    public ArrayList<String> getCoords(){
        return myCoords;
    }
    public String getTemplate(){
        return myTemplate;
    }
    public float getProb(){
        return myProb;
    }
    public int getSeed(){
        return mySeed;
    }
    public float getEmptyRatio(){return emptyRatio;}
    public float getTreeRatio(){return treeRatio;}
}
