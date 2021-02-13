package cellsociety.model.gameoflife;

import cellsociety.model.rules.Builder;
import java.util.ArrayList;

public class GameOfLifeBuilder extends Builder {
  ArrayList<String> coordinates;
  public GameOfLifeBuilder(ArrayList<String> coordinaates){
    this.coordinates = coordinaates;
  }

}
