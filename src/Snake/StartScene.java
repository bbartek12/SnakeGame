package Snake;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class StartScene {

    // Buttons for 3 different difficulties
    Button easyBtn = new Button("Easy");
    Button mediumBtn = new Button("Medium");
    Button hardBtn = new Button("Hard");

    Button scoreBoardBtn = new Button("Scoreboard");
    HBox hBox = new HBox(scoreBoardBtn);

    // Integers to determine difficulty
    public static final int EASY = 15;
    public static final int MEDIUM = 10;
    public static final int HARD = 5;



    Text title = new Text("Snake");
    VBox vbox = new VBox(title, easyBtn, mediumBtn, hardBtn, hBox);

    public static final String KEY = "startGame"; // Key to retrieve scene


    // Create a menu which starts the game
    StartScene(){

        // Set the style of the buttons
        easyBtn.setStyle("-fx-background-color: green; -fx-font-weight: bold; -fx-font-size: 30");
        easyBtn.setPrefSize(200, 100);

        mediumBtn.setStyle("-fx-background-color: yellow; -fx-font-weight: bold; -fx-font-size: 30");
        mediumBtn.setPrefSize(200, 100);

        hardBtn.setStyle("-fx-background-color: red; -fx-font-weight: bold; -fx-font-size: 30");
        hardBtn.setPrefSize(200, 100);

        scoreBoardBtn.setStyle("-fx-background-color: grey;  -fx-font-size: 20");
        hBox.setMaxWidth(600); // prevent from button going to end of screen when using full screen
        hBox.setAlignment(Pos.CENTER_RIGHT); // move button to the right within the hbox
        scoreBoardBtn.setPrefSize(150, 50);

        title.setStyle("-fx-font-weight: bold; -fx-font-size: 50");

        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(50);
    }

    Scene createScene(){

        return new Scene(vbox, 500, 600);
    }
}
