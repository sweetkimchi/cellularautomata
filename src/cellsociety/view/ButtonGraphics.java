package cellsociety.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

/**
 *
 */
public class ButtonGraphics {

  private FlowPane pane;

  /**
   * Default constructor
   */
  public ButtonGraphics() {
    pane = new FlowPane();

    pane.setPadding(new Insets(5, 5, 5, 5));
    pane.setVgap(4);
    pane.setHgap(4);
    pane.setPrefWrapLength(170);

    pane.setAlignment(Pos.CENTER_LEFT);
  }
  public FlowPane getPane() {
    return pane;
  }

  public void addNodesToPane(Node... button) {
    pane.getChildren().addAll(button);
  }

}
