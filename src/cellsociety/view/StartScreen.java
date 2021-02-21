package cellsociety.view;

import java.sql.BatchUpdateException;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * The Start Screen is the initial splash screen shown before starting the program. It is intended
 * to contain the title, a button to begin, and a dropdown box to select language.
 *
 * @author Harrison Huang
 */
public class StartScreen {

  private VBox box;
  Label label;

  /**
   * Creates a new splash screen.
   */
  public StartScreen() {
    box = new VBox();
    box.setAlignment(Pos.CENTER);
  }

  /**
   * Returns the start screen as a Pane.
   *
   * @return Pane for the screen
   */
  public Pane getPane() {
    return box;
  }

  /**
   * Adds title to the screen for the given text.
   *
   * @param title text
   */
  public void addTitle(String title) {
    label = new Label(title);
    label.setId("title");
    box.getChildren().add(label);
  }

  /**
   * Sets title for the given text, or adds a title if it does not exist yet.
   *
   * @param title text
   */
  public void setTitle(String title) {
    if (label == null) addTitle(title);
    else label.setText(title);
  }

  /**
   * Changes the text of the buttons in the pane.
   *
   * @param text to overwrite the button text
   */
  public void setButtonText(String text) {
    for (Node node : box.getChildren()) {
      if (node instanceof Button) {
        ((Button) node).setText(text);
      }
    }
  }

  /**
   * Adds a node to the screen.
   *
   * @param node to be added
   */
  public void addNode(Node node) {
    box.getChildren().add(node);
  }
}
