package Snake;

import javafx.util.Pair;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class ScoresDatabase {

    private static String url = "jdbc:mysql://localhost:3306/Scores";
    private static String userName = "root";
    private static String password = "root";
    private static Connection connection;
    public static boolean isConnected = true;

    // Connect to database
    private static Connection connect(){
        try {
            // Connect to database
            connection = DriverManager.getConnection(url, userName, password);
        }
        catch (SQLException throwables) {
            isConnected = false;
            throwables.printStackTrace();
        }

        return connection;
    }

    // Get all scores sorted
    public static List<Pair<String, Integer>> getSortedScores() throws SQLException {
        Connection connection = connect();

        // Checks if server is on and if not prevents from rest of the function being executed
        if(connection == null){
            return new ArrayList<>();
        }

        // Create a statement
        Statement statement = connection.createStatement();

        // Query to get all scores from highest to lowest
        ResultSet resultSet = statement.executeQuery("SELECT UserName,Score FROM UserScores ORDER BY Score DESC;");

        // List of pairs to insert the score in order
        List<Pair<String, Integer>> playerScoreList = new ArrayList<>();


        // Insert into Hashmap
        while(resultSet.next()){
            playerScoreList.add(new Pair<>(resultSet.getString("UserName"), resultSet.getInt("Score")));
        }

        // Close statement and connection once operation is finished
        statement.close();
        connection.close();

       return playerScoreList;
    }

    public static void insert(String key, int value) throws SQLException{

        Connection connection = connect();

        // Checks if server is on and if not prevents from rest of the function being executed
        if(connection == null){
            return;
        }
        // Query to insert 2 values
        PreparedStatement statement = connection.prepareStatement("INSERT INTO UserScores (Score, Username) values  (?,?)");

        // Insert data into database
        statement.setInt(1, value);
        statement.setString(2, key);
        statement.executeUpdate();

        // Close statement and connection once operation is finished
        statement.close();
        connection.close();

    }

}
