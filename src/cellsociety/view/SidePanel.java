package cellsociety.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * The SidePanel is used as a place to put nodes (buttons and sliders) and a description at the
 * bottom.
 *
 * @aythor Harrison Huang
 */
public class SidePanel {

  public static final double MAX_WIDTH = 220;

  private final VBox panel;
  private Label desc;

  /**
   * Constructs the panel on which to put the controls and description.
   */
  public SidePanel() {
    panel = new VBox();

    panel.setPadding(new Insets(5, 5, 5, 5));

    panel.setAlignment(Pos.CENTER_LEFT);
  }

  /**
   * Returns the node for the panel.
   * @return Node object
   */
  public Node getNode() {
    return panel;
  }

  /**
   * Adds one or multiple nodes to the pane.
   * @param nodes singular or multiple nodes
   */
  public void addNodesToPane(Node... nodes) {
    panel.getChildren().addAll(nodes);
  }

  /**
   * Adds a description of text to the bottom of the pane.
   * @param description String for the description
   */
  public void setDescription(String description) {
    desc = new Label(description);
    desc.setWrapText(true);
    desc.setMaxWidth(MAX_WIDTH);
    addNodesToPane(desc);
  }

  /**
   * Removes the description if it exists. Returns the removed description.
   * @return String of removed description
   */
  public String removeDescription() {
    if (desc == null) {
      return "";
    }
    panel.getChildren().remove(desc);
    String ret = desc.getText();
    desc = null;
    return ret;
  }

}
