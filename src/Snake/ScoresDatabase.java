package Snake;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class ScoresDatabase {

    private static String url = "jdbc:mysql://localhost:3306/Scores";
    private static String userName = "root";
    private static String password = "root";
    private static Connection connection;


    public static Connection connect(){
        try {
            // Connect to database
            connection = DriverManager.getConnection(url, userName, password);


            System.out.println( "test");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return connection;
    }

    // Get all scores sorted
    public static HashMap<String, Integer> getSortedScores() throws SQLException {
        Connection connection = connect();

        // Create a statement
        Statement statement = connection.createStatement();

        // Query to get all scores from highest to lowest
        ResultSet resultSet = statement.executeQuery("SELECT UserName,Score FROM UserScores ORDER BY Score DESC;");

        // LinkedHashmap to insert the score in order
        HashMap<String, Integer> playerScoreMap = new LinkedHashMap<>();

        // Insert into Hashmap
        while(resultSet.next()){
            playerScoreMap.put(resultSet.getString("UserName"), resultSet.getInt("Score"));
        }

        // Close statement and connection once operation is finished
        statement.close();
        connection.close();

       return playerScoreMap;
    }

    public static void insert(String key, int value) throws SQLException{

        Connection connection = connect();

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
