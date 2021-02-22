package cellsociety.view;

import javafx.scene.Node;
import javafx.scene.layout.HBox;

/**
 * The SidePanel is used as a place to put nodes for a menu bar at the top.
 *
 * @author Harrison Huang
 */
public class TopPanel {

  private final HBox panel;
  private static final String NODE_ID = "top-panel";

  /**
   * Creates new TopPanel.
   */
  public TopPanel() {
    panel = new HBox();
    panel.setId(NODE_ID);

  }

  /**
   * Adds nodes to the TopPanel.
   *
   * @param nodes to be added
   */
  public void add(Node... nodes) {
    panel.getChildren().addAll(nodes);
  }

  /**
   * Returns the TopPanel as a node.
   *
   * @return node representing TopPanel
   */
  public Node getNode() {
    return panel;
  }

  /**
   * Returns the height of the panel.
   *
   * @return double for height
   */
  public double getHeight() {
    return panel.getHeight();
  }
}
