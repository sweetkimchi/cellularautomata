package cellsociety.controller;
import cellsociety.controller.Decoder;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class GOLDecoder extends Decoder{
    public static final String COORDS = "shapeCoords";
    public static final String TEMPLATE = "template";

    private ArrayList<String> myCoords;
    private String myTemplateName;

    public GOLDecoder(Map<String, String> attributes){
        myCoords = new ArrayList<>(Arrays.asList(attributes.get(COORDS).split("[,]", 0)));
        myTemplateName = attributes.get(TEMPLATE);
    }
    public String getTemplate() {
        return myTemplateName;
    }
    public ArrayList<String> getCoords(){
        return myCoords;
    }
}
