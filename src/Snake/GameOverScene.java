package Snake;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


public class GameOverScene {

    Button reset = new Button("Restart");
    Text gameOver = new Text("Game Over");
    VBox vbox = new VBox(gameOver, reset);

    public static final String KEY = "gameOver"; // key to retrieve scene

    public GameOverScene() {

        // Some styling to button
        reset.setStyle("-fx-background-color: red; -fx-font-weight: bold; -fx-font-size: 30");
        reset.setPrefSize(200, 100);

        gameOver.setStyle("-fx-font-weight: bold; -fx-font-size: 50");

        // used to center button
        vbox.setAlignment(Pos.CENTER);
    }

    public Scene createScene(){
        return new Scene(vbox,500,500);
    }
}