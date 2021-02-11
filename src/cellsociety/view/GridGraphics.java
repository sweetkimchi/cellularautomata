package cellsociety.view;

import cellsociety.model.cell.State;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 *
 */
public class GridGraphics {

  private GridPane gridPane;

  /**
   * Default constructor
   */
  public GridGraphics(Stage stage) {
    initialize(stage);
  }

  private void initialize(Stage stage) {
    gridPane = new GridPane();

//        Button button1 = new Button("Button 1");
//        Button button2 = new Button("Button 2");
//
//        gridPane.add(button1, 0, 2);
//        gridPane.add(button2, 0, 4);

    gridPane.setMinSize(800, 600);

    //Setting the padding
    gridPane.setPadding(new Insets(10, 10, 10, 10));

    //Setting the vertical and horizontal gaps between the columns
    gridPane.setVgap(.5);
    gridPane.setHgap(.5);

    //Setting the Grid alignment
    gridPane.setAlignment(Pos.CENTER);

//        Random random = new Random();
//        String color;
//        for (int r=0; r<20; r++) {
//            for (int c=0; c<20; c++) {
//                if (random.nextDouble()<.2) {
//                    color = "yellow";
//                }
//                else color = "black";
//                gridPane.add(new Rectangle(20,20, Paint.valueOf(color)),
//                                r+30,c);
//            }
//        }

    //Creating a scene object
    Scene scene = new Scene(gridPane);

    //Setting title to the Stage
    stage.setTitle("Grid Template");

    //Adding scene to the stage
    stage.setScene(scene);
  }

  public void update(State[][] states) {
    gridPane.getChildren().clear();
    for (int r = 0; r < states.length; r++) {
      for (int c = 0; c < states[0].length; c++) {
        gridPane.add(new Rectangle(13, 13, states[r][c].color), r, c);
      }
    }
  }

}