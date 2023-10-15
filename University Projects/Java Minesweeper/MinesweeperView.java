import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;



/**
 * Here is minesweeper view. The goal is to get a board that fully implements the game.
 * @author 903595809
 * @version 1
 */
public class MinesweeperView extends Application {
    /**
     * We'll be storing our data here. We need to have the game scene and the intro scene in memory to swap back
     * and forth, and we will also need to store the username and difficulty to pass between them.
     */
    private Scene introScene;
    private Scene gameScene;
    private Scene endScene;

    private String username;
    private Difficulty userDiff;
    private Button[][] buttons = new Button[15][15];
    private MinesweeperGame game;
    //private String[][] board = new String[15][15];


    /**
     * Then we have to override the start method. This will actually run our application, and there will be
     * very little going on here. We run our "constructors" of both scences, and set the intro scene. At this
     * point, all of the other functionality and logic is handled within the respective scenes.
     *
     * @param primaryStage start method needs a stage to show.
     */
    public void start(Stage primaryStage) {
        //We'll have to make both scenes in memory - we just swap between them.
        createIntroScene(primaryStage);
        //createGameScene(primaryStage); we don't need to create this immediately. we will wait.

        primaryStage.setScene(introScene);
        primaryStage.setTitle("Minesweeper");
        primaryStage.show();

    }

    /**
     * This generates our intro scene. We need a parameter of "stage" to have something for the lambda expresion
     * to set the stage on. Basically in order to set a scene, we need access to a stage.
     *
     * @param stage this is the stage we will use for setting scenes and generating game scenes.
     */
    private void createIntroScene(Stage stage) {
        //lets only run if we don't already have a stage - see the method will have to take in a stage in order
        // to have something to assign the mouse to.
        if (introScene == null) {

            /**
             * So the way this scene is set up, is we have a Vbox with all of our content stacked up along top
             * center.
             */
            VBox pane = new VBox();
            pane.setAlignment(Pos.TOP_CENTER);
            pane.setPadding(new Insets(5, 12.5, 13.5, 14.5));
            pane.setSpacing(5.5);

            /**
             * For the actual content of the vbox, we first add a background image. This requires a new
             * background class, which takes in a new background image, which takes in a new image, and all of
             * these args that need to be imported.
             *
             * We also have the top text, with extra newlines as a improvised spacer, as well as white labels
             * x2, combo box, and text fields, and button.
             *
             * After each element we'll add it to the pain (adds top to bottom)
             */
            pane.setBackground(new Background(new BackgroundImage(new Image("daMinesweeper.png"),
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT)));

            Text text = new Text(25, 20, "DaMinesweeper \n \n");
            text.setFill(Color.WHITE);
            text.setFont(
                    Font.font("Impact", FontWeight.BOLD, FontPosture.ITALIC, 65));
            pane.getChildren().add(text);

            Label label1 = new Label("Difficulty:");
            label1.setTextFill(Color.WHITE);
            pane.getChildren().add(label1);

            //Now we need a new drop down menu
            ComboBox newDropDown = new ComboBox();
            //We aren't going to bother with java collections, just add the old fashioned way.
            newDropDown.getItems().add("JoJo Siwa (Easy)");
            newDropDown.getItems().add("My Steak (Medium)");
            newDropDown.getItems().add("Me (Hard)");
            pane.getChildren().add(newDropDown);

            Label label2 = new Label("Name:");
            label2.setTextFill(Color.WHITE);
            pane.getChildren().add(label2);

            TextField myTextName = new TextField();
            myTextName.setMaxWidth(150);
            pane.getChildren().add(myTextName);

            Button btAdd = new Button("Lesss Gooo! (Start Game)");
            pane.getChildren().add(btAdd);

            /**
             * This is our button Action. When we hit it, we need to save our name and difficulty, and make a new
             * board, and switch scenes.
             *
             * if we don't have name and difficulty, we need to set an alert.
             */
            btAdd.setOnAction(e -> {

                if (myTextName.getText().isEmpty() || newDropDown.getValue() == null) {
                    //if empty, we alert
                    Alert error = new Alert(Alert.AlertType.ERROR, "Uh, uh, uh-huh\n"
                            + "Yeah, Please put in difficulty AND name\nyuh yuh");
                    error.setTitle("daError");
                    error.showAndWait();

                } else {
                    //we are good. start the game.
                    this.username = myTextName.getText();
                    this.userDiff = getDifficulty(newDropDown);
                    //DEBUG                System.out.println(username);
                    //                System.out.println(userDiff.name());

                    createGameScene(stage);
                    stage.setScene(gameScene);
                }

            });

            /**
             * And this brings it all together. It sets our scene with our pane and at this size.
             */
            introScene = new Scene(pane, 600, 600);


        }
    }

    /**
     * This is the constructor to make a game scene. It can only be called in the intro scene if the name and
     * difficulty have been passed in.
     *
     * @param stage once again we pass in our stage so we can call methods on it.
     */
    private void createGameScene(Stage stage) {

        /**
         * First thing we do is call our constructor and make a new minesweeper game.
         */
        game = new MinesweeperGame(userDiff);

        /**
         * Now visually, we need to make a grid of buttons. They don't do anything, but thats fine. This will be
         * done with gridpane, becuase you an index what buttons go where.
         * add(Node child, int columnIndex, int rowIndex)
         *
         * We will label the buttons via an array. We will be using a naming convention y, x. We will start by
         * printing X in each bin and initializing.
         *
         * Could I use a character array instead? Yes. But, strings will make debugging so much easier.
         * We will also make board a variable accessible to the ENTIRE class. This will hopefully clear up
         * issues with anonymous classes.
         */
        /*
        for (int y = 0; y < 15; y++) {
            for (int x = 0; x < 15; x++) {
                //labelArr[y][x] = y + ", " + x; x,y coords debugging
                this.board[y][x] = "X";
            }
        }
         */


        GridPane gPane = new GridPane();
        for (int y = 0; y < 15; y++) {
            for (int x = 0; x < 15; x++) {

                Button but = new Button("X");
                //but.setUserData(new int[] {y, x});
                buttons[y][x] = but;
                but.setTextFill(Color.hsb((x * y) * 270 / 225, 1, 0.75));

                /**
                 * Button actions:
                 * 1) it feeds the correct coords into check
                 * 2) check spits out tiles, we update board visuals w/method. Since the buttons are refering to
                 * our board, this should dynamically update.
                 * 3) we check if its lost
                 * 4) we check if we win
                 */
                int passY = y;
                int passX = x;
                but.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        Tile[] curTiles = game.check(passY, passX);
                        //game.printBoard();
                        //game.printBoardVisible();
                        if (curTiles != null) {
                            updateButtons(curTiles);
                        }

                        if (game.isLost() || game.isWon()) {
                            createEndScene(stage, game.isWon());
                            stage.setScene(endScene);
                        }

                        //System.out.println(String.format("Button %d, %d has been pressed", passY, passX));
                        //board[(int) (Math.random()*15)][(int) (Math.random()*15)] ="|||";
                        //but.setText(board[X]);
                        //debugBoard();


                    }
                });

                but.setMinHeight(600 / 15);
                but.setMinWidth(600 / 15);
                gPane.add(but, y, x);
            }
        }


        gameScene = new Scene(gPane, 600, 600);
    }

    /**
     * Similar to the previous two stages, we want to travel back  and forth.
     * @param stage what we need to take in to have access.
     */
    private void createEndScene(Stage stage, Boolean isWon) {
        VBox pane = new VBox();
        pane.setAlignment(Pos.CENTER);

        GridPane boards = new GridPane();
        Label playBoard = new Label(printBoardPlayer());
        //Label fullBoard = new Label("test2") ;
        Label playBoardLabel = new Label("How far you got, king");
        //Label playBoardFull = new Label( "The full board");
        boards.add(playBoard, 0, 0);
        //boards.add(fullBoard, 1, 0);
        boards.add(playBoardLabel, 0, 1);
        //boards.add(playBoardFull, 1, 1);
        boards.setVisible(false);
        boards.setGridLinesVisible(true);
        boards.setAlignment(Pos.CENTER);
        pane.getChildren().add(boards);

        Label lEnd;
        if (isWon) {
            lEnd = new Label(String.format("Congratulations, %s\nwon, yeah yeah!", username));
        } else {
            lEnd = new Label(String.format("You Lose, %s,\n yeah yeah!", username));
        }
        lEnd.setFont(
                Font.font("Impact", FontWeight.BOLD, FontPosture.ITALIC, 40));
        pane.getChildren().add(lEnd);

        HBox btns = new HBox();
        Button btn = new Button("Play Again");
        //named inner class going back to start.
        class EndHandler implements EventHandler<ActionEvent> {
            public void handle(ActionEvent ae) {
                stage.setScene(introScene);
            }
        }
        btn.setOnAction(new EndHandler());
        Button btn2 = new Button("Show DaBoard");
        btn2.setOnAction(e -> {
            boards.setVisible(true);
        });
        btns.getChildren().add(btn);
        btns.getChildren().add(btn2);
        btns.setAlignment(Pos.CENTER);
        pane.getChildren().add(btns);

        endScene = new Scene(pane, 600, 600);
    }

    /**
     * Helper method that returns the difficulty that was selected in the box.
     *
     * @param comboBox like many other methods here, we need to access combobox, and the only way to do it is by
     *                 feeding it in as a parameter.
     */
    private Difficulty getDifficulty(ComboBox comboBox) {
        String userStr = (String) comboBox.getValue();
        Difficulty retDif;

        switch (userStr) {
        case "Medium":
            retDif = Difficulty.MEDIUM;
            break;
        case "Me (Hard)":
            retDif = Difficulty.HARD;
            break;
        default:
            retDif = Difficulty.EASY;
        }
        return retDif;
    }

    /**
     * We are having a method that will update our board. First of all, we are having a button array as a
     * private var so we can acess it no issues.
     *
     * @param tiles take in the tiles form the the check method
     *              So if we have a mine, we print out
     */
    private void updateButtons(Tile[] tiles) {
        for (Tile ti : tiles) {
            if (ti.isMine()) {
                buttons[ti.getY()][ti.getX()].setText("_");
            } else {
                buttons[ti.getY()][ti.getX()].setText(String.valueOf(ti.getBorderingMines()));
            }
            buttons[ti.getY()][ti.getX()].setTextFill(Color.BLACK);
        }
    }

    /**
     * this is a method to show how far the player got, essnetially printing the status of all the buttons onto
     * a grid.
     */
    private String printBoardPlayer() {
        String str = "\n   ";
        for (int y = 0; y < 15; y++) {
            for (int x = 0; x < 15; x++) {
                str = str + buttons[x][y].getText() + "   ";
            }
            str = str + "\n" + "   ";
        }
        //str = str + "\n";
        return str;
    }

}


    /*
     /**
     * debug board by printing it out.. uses illegal imports.

    private void debugBoard() {
        for(String[] x: board)
            System.out.println(Arrays.toString(x));
            }
    }
    */

/* OLD PLACEHOLDER CODE FOR SCENE SWAPS
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Line;
import javafx.scene.shape.Circle;

 Rectangle bgd = new Rectangle(0, 0, 300, 300);
        bgd.setFill(Color.CYAN);

        // ground
        Rectangle ground = new Rectangle(0, 175, 300, 50);
        ground.setFill(Color.BLUE);

        // sun
        Circle sun = new Circle(0, 0, 60);
        sun.setFill(Color.YELLOW);

        // head
        Ellipse head = new Ellipse(MID, TOP + 20, 20, 20);
        head.setFill(Color.WHITE);

        // upper torso
        Ellipse upper_torso = new Ellipse(MID, TOP + 60, 35, 25);
        upper_torso.setFill(Color.WHITE);

        // lower torso
        Ellipse lower_torso = new Ellipse(MID, TOP + 110, 50, 30);
        lower_torso.setFill(Color.WHITE);

        // left eye
        Ellipse lefteye = new Ellipse(MID - 8, TOP + 12, 3, 3);
        // right eye
        Ellipse righteye = new Ellipse(MID + 8, TOP + 12, 3, 3);

        // smile
        Arc smile = new Arc(MID, TOP + 25, 10, 5, 190, 160);
        smile.setType(ArcType.OPEN);

        // left arm
        Line leftarm = new Line(MID - 25, TOP + 60, MID - 50, TOP + 40);
        // right arm
        Line rightarm = new Line(MID + 25, TOP + 60, MID + 55, TOP + 60);

        // brim of hat
        Line hat_brim = new Line(MID - 20, TOP + 5, MID + 20, TOP + 5);
        // top of hat
        Rectangle hat_top = new Rectangle(MID - 15, TOP - 20, 30, 25);

        // Create A scene and place it in the stage
        Pane pane = new Pane();
        pane.getChildren().add(bgd);

        try {
            Text text = new Text(MID + 95, 20, String.format("%s and %s", username, userDiff.name()));
            text.setFont(
                    Font.font("Courier", FontWeight.BOLD, FontPosture.ITALIC, 15));
            pane.getChildren().add(text);


        } catch (NullPointerException ne) {
            Text text = new Text(MID + 95, 20, "no Name");
            text.setFont(
                    Font.font("Courier", FontWeight.BOLD, FontPosture.ITALIC, 15));
            pane.getChildren().add(text);

        }


        pane.getChildren().add(ground);
        pane.getChildren().add(sun);
        pane.getChildren().add(head);
        pane.getChildren().add(upper_torso);
        pane.getChildren().add(lower_torso);
        pane.getChildren().add(lefteye);
        pane.getChildren().add(righteye);
        pane.getChildren().add(leftarm);
        pane.getChildren().add(rightarm);
        pane.getChildren().add(smile);
        pane.getChildren().add(hat_brim);
        pane.getChildren().add(hat_top);

        sun.setOnMouseClicked(e -> {
            stage.setScene(introScene);
            stage.setTitle("SceneSwap - INTRO Scene");
        });
 */
