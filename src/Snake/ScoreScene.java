package Snake;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Pair;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScoreScene {
    ListView<String> scoreList;
    ScoresDatabase database = new ScoresDatabase();
    public static final String KEY = "ScoreScene"; // Key to retrieve scene
    Button returnButton = new Button("Return");

    // Fill up listview from scores stored in database
    void fillUpListView() throws SQLException {

        // Set to new listview to clear out list each in case it gets updated
        scoreList = null;
        scoreList = new ListView<>();
        List<Pair<String, Integer>> pairList = database.getSortedScores();

        int rank = 1;

        // Insert strings into listview ranking players from high to low
        for(Pair<String, Integer> pair : pairList) {
            scoreList.getItems().add(rank++ + ". " + pair.getKey() + " " + pair.getValue());
        }

    }


    // Return listview scene of player rankings
    Scene createScoresScene() throws SQLException {
        fillUpListView();
        VBox vBox = new VBox(4);
        Label title = new Label("Player Rankings");
        title.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(title, scoreList, returnButton);
        return new Scene(vBox, 600, 600);
    }


}
