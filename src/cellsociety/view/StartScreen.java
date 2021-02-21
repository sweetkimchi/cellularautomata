package cellsociety.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class StartScreen {

  private GridPane pane;

  public StartScreen() {
    pane = new GridPane();
    pane.setAlignment(Pos.CENTER);
  }

  public GridPane getPane() {
    return pane;
  }

  public void addTitle(String title) {
    Label label = new Label(title);
    label.setId("title");
    pane.add(label,0,0);
  }

  public void addButton(Button beginButton) {
    beginButton.setAlignment(Pos.CENTER);
    pane.add(beginButton,0,1);
  }
}
