import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class introTESTING extends Application {

    private Scene introScene;

    //Then we have to override the start method
    public void start(Stage primaryStage) {
        //We'll have to make both scenes in memory - we just swap between them.
        createIntroScene(primaryStage);
        primaryStage.setTitle("INTRO SCENE");
        primaryStage.setScene(introScene);
        primaryStage.show();

    }

    //This is the constructor to make an intro scene
    private void createIntroScene(Stage stage) {
        //lets only run if we don't already have a stage - see the method will have to take in a stage in order
        // to have something to assign the mouse to.
        if (introScene == null) {


            GridPane pane = new GridPane();
            pane.setAlignment(Pos.CENTER);
            pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
            pane.setHgap(5.5);
            pane.setVgap(5.5);

            // Place nodes in the pane
            //add(Node child, int columnIndex, int rowIndex)
            pane.add(new Label("First Name:"), 0, 0);
            pane.add(new TextField(), 1, 0);
            pane.add(new Label("MI:"), 0, 1);
            pane.add(new TextField(), 1, 1);
            pane.add(new Label("Last Name:"), 0, 2);
            pane.add(new TextField(), 1, 2);
            Button btAdd = new Button("Add Name");
            pane.add(btAdd, 1, 3);
            GridPane.setHalignment(btAdd, HPos.RIGHT);
            btAdd.setOnAction(e -> {
                //stage.setScene(gameScene);
                stage.setTitle("SceneSwap - GAME Scene");
            });

            introScene = new Scene(pane);


        }
    }
}
