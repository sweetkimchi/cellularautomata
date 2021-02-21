package cellsociety.view;

import java.sql.BatchUpdateException;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class StartScreen {

  private VBox box;
  Label label;

  public StartScreen() {
    box = new VBox();
    box.setAlignment(Pos.CENTER);
  }

  public Pane getPane() {
    return box;
  }

  public void addTitle(String title) {
    label = new Label(title);
    label.setId("title");
    box.getChildren().add(label);
  }

  public void setTitle(String title) {
    if (label == null) addTitle(title);
    else label.setText(title);
  }

  public void setButtonText(String text) {
    for (Node node : box.getChildren()) {
      if (node instanceof Button) {
        ((Button) node).setText(text);
      }
    }
  }

  public void addNode(Node node) {
    box.getChildren().add(node);
  }
}
