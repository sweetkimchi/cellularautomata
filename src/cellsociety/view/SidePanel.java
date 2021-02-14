package cellsociety.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 *
 */
public class SidePanel {

  private static final double MAX_WIDTH = 170;

  private VBox pane;
  private Label desc;

  /**
   * Default constructor
   */
  public SidePanel() {
    pane = new VBox();

    pane.setPadding(new Insets(5, 5, 5, 5));
    //pane.setVgap(4);
    //pane.setHgap(4);
    //pane.setPrefWrapLength(MAX_WIDTH);

    pane.setAlignment(Pos.CENTER_LEFT);
  }
  public Node getPane() {
    return pane;
  }

  public void addNodesToPane(Node... button) {
    pane.getChildren().addAll(button);
  }

  public void setDescription(String description) {
    desc = new Label(description);
    desc.setWrapText(true);
    desc.setMaxWidth(MAX_WIDTH);
    addNodesToPane(desc);
  }

  public void removeDescription() {
    if (desc != null) {
      pane.getChildren().remove(desc);
    }
  }

}
