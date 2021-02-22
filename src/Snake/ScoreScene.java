package Snake;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Pair;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScoreScene {
    ListView<String> scoreList;
    public static final String KEY = "ScoreScene"; // Key to retrieve scene
    Button returnButton = new Button("Return");

    // Fill up listview from scores stored in database
    void fillUpListView() throws SQLException {

        // Set to new listview to clear out list each in case it gets updated
        scoreList = null;
        scoreList = new ListView<>();

        // Get list of pair values from database
        List<Pair<String, Integer>> pairList = ScoresDatabase.getSortedScores();

        // Starting rank of each player
        int rank = 1;

        // Insert strings into listview ranking players from high to low
        for(Pair<String, Integer> pair : pairList) {
            scoreList.getItems().add(rank++ + ". " + pair.getKey() + " " + pair.getValue());
        }
    }


    // Return listview scene of player rankings
    // Also a button to return to previous scene
    Scene createScoresScene() throws SQLException {

        // If disconnected from server game will still work
       if(!ScoresDatabase.isConnectedToServer()){
           return new Scene(new VBox(),600, 600);
       }
        fillUpListView();

        VBox vBox = new VBox(4);

        Label title = new Label("Player Rankings");
        title.setAlignment(Pos.CENTER);

        // Make return button bigger and easier to read
        returnButton.setStyle("-fx-font-weight:bold; -fx-font-size: 30");
        returnButton.setPrefSize(200, 100);

        // Hbox to shift the button to the center
        HBox hbox = new HBox(returnButton);
        hbox.setAlignment(Pos.CENTER);

        // Insert all objects into vbox
        vBox.getChildren().addAll(title, scoreList, hbox);

        return new Scene(vBox, 600, 600);
    }


}
