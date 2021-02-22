package Snake;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


public class GameOverScene {

    // Create main buttons
    Button reset = new Button("Restart");
    Button homeScreen = new Button("Home");
    Button quit = new Button("Quit");

    Label playerScore = new Label("Score: ");

    // Textfield, a label, and hbox for positioning
    TextField userScoreInput = new TextField();
    Text userNameLabel = new Text("Enter Your Name For Leadboard: ");
    Button enterBtn = new Button("Enter");
    HBox hBoxInput = new HBox( 4, userNameLabel, userScoreInput, enterBtn);

    // Scoreboard button and hbox for positioning
    Button scoreBoardBtn = new Button("Scoreboard");
    HBox hBoxScore = new HBox(scoreBoardBtn);

    Text gameOver = new Text("Game Over");
    VBox vbox = new VBox(gameOver, playerScore, hBoxInput, reset, homeScreen, quit, hBoxScore);

    public static final String KEY = "gameOver"; // key to retrieve scene

    public GameOverScene() {

        // Some styling to reset button
        reset.setStyle("-fx-background-color: grey; -fx-font-weight: bold; -fx-font-size: 30");
        reset.setPrefSize(200, 100);

        // Some styling to homescreen button
        homeScreen.setStyle("-fx-background-color: green; -fx-font-weight: bold; -fx-font-size: 30");
        homeScreen.setPrefSize(200, 100);

        // Some styling to quit button
        quit.setStyle("-fx-background-color: red; -fx-font-weight: bold; -fx-font-size: 30");
        quit.setPrefSize(200, 100);

        // Some styling to quit button
        enterBtn.setStyle("-fx-background-color: grey;");
        enterBtn.setPrefSize(80, 10);

        gameOver.setStyle("-fx-font-weight: bold; -fx-font-size: 50");
        playerScore.setStyle("-fx-font-weight: bold; -fx-font-size: 20");

        // Input field style
        userScoreInput.setMaxWidth(80);
        hBoxInput.setAlignment(Pos.CENTER);

        // Set score button to bottom of the screen and its style
        scoreBoardBtn.setStyle("-fx-background-color: grey;  -fx-font-size: 20");
        hBoxScore.setMaxWidth(600); // prevent from button going to end of screen when using full screen
        hBoxScore.setAlignment(Pos.CENTER_RIGHT); // move button to the right within the hbox
        scoreBoardBtn.setPrefSize(150, 50);

        // used to center button
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(30);
    }

    public Scene createScene(){
        return new Scene(vbox,600,600);
    }
}