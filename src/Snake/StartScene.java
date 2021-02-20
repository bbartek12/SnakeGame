package Snake;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class StartScene {

    Button startBtn = new Button("Start");
    Text title = new Text("Snake");
    VBox vbox = new VBox(title, startBtn);

    public static final String KEY = "startGame"; // Key to retrieve scene

    // Create a menu which starts the game
    StartScene(){


        startBtn.setStyle("-fx-background-color: green; -fx-font-weight: bold; -fx-font-size: 30");
        startBtn.setPrefSize(200, 100);

        title.setStyle("-fx-font-weight: bold; -fx-font-size: 50");

        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(100);
    }

    Scene createScene(){

        return new Scene(vbox, 500, 500);
    }
}
