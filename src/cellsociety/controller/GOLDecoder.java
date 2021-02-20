package cellsociety.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
/**
 * This class handles parsing for all Game-of-Life specific parameters
 * @author Shaw Phillips
 */
public class GOLDecoder extends Decoder{
    public static final String COORDS = "shapeCoords";
    public static final String TEMPLATE = "template";

    private ArrayList<String> myCoords;
    private String myTemplateName;
    /**
     * Parse all Game of Life parameters from XML file and initialize variables
     * @param attributes of XML file
     */
    public GOLDecoder(Map<String, String> attributes){
        myCoords = new ArrayList<>(Arrays.asList(attributes.get(COORDS).split("[,]", 0)));
        myTemplateName = attributes.get(TEMPLATE);
    }
    /**
     * @return template name
     */
    public String getTemplate() {
        return myTemplateName;
    }
    /**
     * @return coordinates
     */
    public ArrayList<String> getCoords(){
        return myCoords;
    }
}
