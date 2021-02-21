package cellsociety.view;

import javafx.scene.Node;
import javafx.scene.layout.HBox;

public class TopPanel {

  private HBox panel;
  private static final String NODE_ID = "top-panel";

  public TopPanel() {
    panel = new HBox();
    panel.setId(NODE_ID);

  }

  public void add(Node node) {
    panel.getChildren().add(node);
  }

  public Node getNode() {
    return panel;
  }
}
