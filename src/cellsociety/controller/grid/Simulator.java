package cellsociety.controller.grid;

import cellsociety.model.cell.Cell;
import java.util.List;
import java.util.Map;

/**
 *
 */
public abstract class Simulator {

  private GridManager gridManager;
  private Map<Cell, List<Cell>> grid;

  /**
   * Default constructor
   */
  public Simulator() {
  }

  protected abstract void initializeData();

  protected abstract void initializeModelConstructors(String type);

  protected abstract void initializeGrid();


  public Map<Cell, List<Cell>> processGridForVisualization() {
    return grid;
  }

  /**
   *
   */
  public abstract void updateCellState();

  /**
   *
   */
  public void updateSimulatorView() {
    // TODO implement here
  }

}