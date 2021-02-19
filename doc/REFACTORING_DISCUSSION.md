## Refactoring Lab Discussion
### Team
cellsociety team 01
### Names
Ji Yun Hyo - jh160 <br>
Shaw Phillips - sp422 <br>
Harrison Huang - hlh38

### Issues in Current Code

#### Method or Class
- Non-private instance variables
    1. ./src/cellsociety/model/cell/State.java: public String type; 
    2. ./src/cellsociety/model/cell/State.java: public Paint color;
    3. ./src/cellsociety/model/cell/State.java: public int numberOfMoves;
    4. ./src/cellsociety/model/cell/State.java: public int energy;
    5. ./src/cellsociety/view/SimulationScreen.java: public SidePanel sidePanel;
    6. ./src/cellsociety/view/SimulationScreen.java: public GridGraphics gridGraphics;
    
- Encapsulation
    1. ./src/cellsociety/controller/grid/GridManager.java: public State[][] buildGridWithRandomSeed(double emptyRatio, double populationRatio, int seed, ArrayList<String> possibleTypes, ArrayList<String> possibleColors){
    2. ./src/cellsociety/controller/grid/GridManager.java: public State[][] buildGridWithTemplate(ArrayList<State> template, String type) {
    3. ./src/cellsociety/controller/grid/GridManager.java: public void updateGrid(State[][] stateOfCells) {
    4. ./src/cellsociety/model/gameoflife/GameOfLifeRule.java: public State[][] judgeStateOfEachCell(State[][] statesOfAllCells) {
    5. ./src/cellsociety/model/percolation/PercolationRules.java: public State[][] judgeStateOfEachCell(State[][] statesOfAllCells) {
    6. ./src/cellsociety/model/rules/Rules.java: public abstract State[][] judgeStateOfEachCell(State[][] statesOfAllCells);
    7. ./src/cellsociety/model/segregationmodel/SegregationModelRules.java: public State[][] judgeStateOfEachCell(State[][] statesOfAllCells) {
    8. ./src/cellsociety/model/spreadingoffire/SpreadingOfFireRules.java: public State[][] judgeStateOfEachCell(State[][] statesOfAllCells) {
    9. ./src/cellsociety/model/watormodel/WaTorModelRules.java: public State[][] judgeStateOfEachCell(State[][] statesOfAllCells) {
    10. ./src/cellsociety/view/GridGraphics.java: public void update(State[][] states, String model) { 
    11. ./src/cellsociety/view/SimulationScreen.java: public void update(State[][] states, String model) {

-Code duplication <br>
    1. public class SegDecoder extends Decoder <br>
    2. public SegDecoder(Map<String, String> attributes)<br>
    3. public float getPopRatio()<br>
    4. public float getEmptyRatio()<br>
    5. public float getSatThresh()<br>
    6. public int getRandSeed()<br>
    7. public String getTemp()<br><br>
    and 5 more methods will have to be added here for the new simulations added this week<br>
    8. public class Decoder {<br>
    9. public void readValuesFromXMLFile()<br>
    10. public GOLDecoder getGOLDecoder()<br>
    11. public WaTorDecoder getWaTorDecoder()<br>
    12. public SegDecoder getSegDecoder()<br>
    13. public FireDecoder getFireDecoder()<br>
    14. public PercDecoder getPercDecoder()<br>


### Refactoring Plan

* What are the code's biggest issues?
  Encapsulation and duplication of code, especially for the judgeStateOfEachCell method. We also have
  a few public instance variables.

* Which issues are easy to fix and which are hard?
  Easy to fix: public instance variables
  Hard to fix: encapsulation and duplication of code

* What are good ways to implement the changes "in place"?
  Change public to private for instance variables. Make set and get methods to set and get the public instance variable.
  Try to refactor the judgeStateOfEachCell into Rules abstract class.

### Refactoring Work

* Issue chosen: Fix and Alternatives
  Delete duplicate Decoder classes and condence methods into super-class.

* Issue chosen: Fix and Alternatives
  Instead of passing around the entire grid, pass around a grid of colors (for view component to display) and pass in a integer array
  of numberOfNeighbors to the rules class to determine/update the states.