package cellsociety.controller;

import java.util.Map;

public class WaTorDecoder extends Decoder{
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

    public WaTorDecoder(Map<String, String> attributes){
        myFSRatio = Float.parseFloat(attributes.get(FS_RATIO));
        myEmptyRatio = Float.parseFloat(attributes.get(EMPTY_RATIO));
        mySeed = Integer.parseInt(attributes.get(SEED));
        fishRate = Integer.parseInt(attributes.get(FISH_RATE));
        sharkRate = Integer.parseInt(attributes.get(SHARK_RATE));
        sharkLives = Integer.parseInt(attributes.get(SHARK_LIVES));
        energy = Integer.parseInt(attributes.get(ENERGY));
    }
    public float getFSRatio(){
        return myFSRatio;
    }
    public float getEmptyRatio(){
        return myEmptyRatio;
    }
    public int getSeed(){
        return mySeed;
    }
    public int getFishRate(){
        return fishRate;
    }
    public int getSharkRate(){
        return sharkRate;
    }
    public int getSharkLives(){
        return sharkLives;
    }
    public int getEnergy(){
        return energy;
    }


}
