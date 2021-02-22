package Snake;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SnakeGame extends Application {


	// Create objects necessary for game
	GameLogic gameLogic = new GameLogic();
	ScoresDatabase database = new ScoresDatabase();
	Group root = new Group();
	int setSpeed = 10; // The higher this number is the slower the snake

	// Retrieve the two scenes, create a new one, and something to store the scenes
	StartScene startScene = new StartScene();
	GameOverScene gameOverScene = new GameOverScene();
	ScoreScene scoreScene = new ScoreScene();
	Scene scene = new Scene(root, 600, 600);
	Scene previousScene; // used to get back from scoreboard
	Map<String, Scene> sceneMap = new HashMap<>();

	// Get the buttons from the scenes
	//---------------------------------------------------------------------

	// Start scene
	Button easyBtn = startScene.easyBtn;
	Button mediumBtn = startScene.mediumBtn;
	Button hardBtn= startScene.hardBtn;
	Button scoreBoard = startScene.scoreBoardBtn;

	// Score scene
	Button previousSceneBtn = scoreScene.returnButton;

	// Gameover scene
	Button reset = gameOverScene.reset;
	Button home = gameOverScene.homeScreen;
	Button quit = gameOverScene.quit;
	Button scoreBoardBtn2 = gameOverScene.scoreBoardBtn;
	Button enterBtn = gameOverScene.enterBtn;

	//---------------------------------------------------------------------

	Label scoreText = new Label("Score: " + Integer.toString(gameLogic.score));
	Label scoreGameOver = gameOverScene.playerScore;
	TextField scoreInput = gameOverScene.userScoreInput;

	// Update the current score when snake ate the fruit
	void snakeAteUpdate(){
		if(gameLogic.snakeAteFruit(root)) {
			scoreText.setText("Score: " + Integer.toString(gameLogic.score));
		}
	}

	// Sets scene to main game scene
	void startGame(Stage primaryStage){
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@Override
	public void start(Stage primaryStage) throws SQLException {

		// Store scenes in hashmap to allow for swapping
		sceneMap.put(startScene.KEY, startScene.createScene());
		sceneMap.put(gameOverScene.KEY, gameOverScene.createScene());
		sceneMap.put(scoreScene.KEY, scoreScene.createScoresScene());

		gameLogic.score = 0; // set score to 0 at start of game

		scoreText.setStyle("-fx-font-size:30;");
		scoreText.relocate(scene.getWidth()/2-70, 0); // Put score near middle of screen

		primaryStage.setTitle("Snake");
		primaryStage.setScene(sceneMap.get(StartScene.KEY)); // set to menu screen

		// If server is disconnected then block database related functionalities
        if(!database.isConnected){
        	scoreBoard.setDisable(true);
        	scoreBoardBtn2.setDisable(true);
        	scoreInput.setDisable(true);
        	enterBtn.setDisable(true);
		}

		// Button to start game
		easyBtn.setOnAction(e->
		{
			setSpeed = StartScene.EASY;
			startGame(primaryStage);
		});

		// Button to start game
		mediumBtn.setOnAction(e->
		{
			setSpeed = StartScene.MEDIUM;
			startGame(primaryStage);
		});

		// Button to start game
		hardBtn.setOnAction(e->
		{
			setSpeed = StartScene.HARD;
			startGame(primaryStage);
		});

		// Button to see ranking of all players
		scoreBoard.setOnAction(e->
		{
			previousScene = primaryStage.getScene();
			primaryStage.setScene(sceneMap.get(ScoreScene.KEY));
			primaryStage.show();
		});


		// Go back from scoreboard scene
		previousSceneBtn.setOnAction(e ->
		{
			primaryStage.setScene(previousScene);
			primaryStage.show();
		});

		// resets game after a death
		reset.setOnAction(e->
		{
			startGame(primaryStage);
		});

		// closes game
		quit.setOnAction(e->
		{
			Platform.exit();
		});

		// Enter scoreboard from gameover screen
		scoreBoardBtn2.setOnAction(e->
		{
			previousScene = primaryStage.getScene();
			primaryStage.setScene(sceneMap.get(ScoreScene.KEY));
			primaryStage.show();
		});

		home.setOnAction(e->
		{
			resetGame();
			primaryStage.setScene(sceneMap.get(startScene.KEY));
			primaryStage.show();
		});

		enterBtn.setOnAction(e->
		{

			try {
			    // Insert players score
				database.insert(scoreInput.getText(), gameLogic.score);
				sceneMap.put(ScoreScene.KEY, scoreScene.createScoresScene());
			}
			catch (SQLException throwables) {
				throwables.printStackTrace();
			}
			enterBtn.setDisable(true);

		});

		// Set default values for snake and position of fruit
		resetGame();

		// button input uses player object functions
		// sets the values to true or false to avoid spamming of buttons
		// if the snake moved with each button press the game would be broken since the body would shift 1 block
		scene.setOnKeyPressed(e-> {

			if(e.getCode() == KeyCode.W){
				gameLogic.snake.get(0).moveUp();
			}
			else if(e.getCode() == KeyCode.A){
				gameLogic.snake.get(0).moveLeft();
				//   growSnake(); // DEBUG - growth + follow
			}
			else if(e.getCode() == KeyCode.S){
				gameLogic.snake.get(0).moveDown();
			}
			else if(e.getCode() == KeyCode.D){
				gameLogic.snake.get(0).moveRight();
			}
		});


		// Animates constant movement
		// Check for collision and out of bounds death
		// also move fruit when it is eaten
		new AnimationTimer() {
			int counter = 0;

			@Override
			public void handle(long now) {

				if(counter % setSpeed == 0) { // slow down fps of the game

					// set so when game is over none of the functions are accessed
					// a better idea may be to stop the animation timer and reset it.
					if(gameLogic.snake.size() > 0) {

						double posX = gameLogic.snake.get(0).getTranslateX();
						double posY = gameLogic.snake.get(0).getTranslateY();

						gameLogic.follow(posX,posY);

						// after picking direction with button input go in that direction
						gameLogic.playerDirection(gameLogic.snake.get(0));

						gameLogic.collisionDetection();
						//gameLogic.snakeAteFruit(root);
						snakeAteUpdate();
						if(gameLogic.isPlayerDead(scene)) {
							scoreGameOver.setText(String.valueOf(gameLogic.score));
							primaryStage.setScene(gameOverScene());
							primaryStage.show();
						}
					}
				}
				counter++;
			}
		}.start();

		primaryStage.show();
	}


	// Resets game values to default
	public void resetGame(){

		// clear snake
		root.getChildren().clear();
		gameLogic.snake.clear();

		gameLogic.score = 0;
		scoreText.setText("Score: " + gameLogic.score);
		root.getChildren().add(scoreText);

		// add fruit back to root and move it
		root.getChildren().add(gameLogic.fruit);
		gameLogic.createFruit();

		enterBtn.setDisable(false);

		// Starts snake to original size
		gameLogic.growSnake(root);
		gameLogic.growSnake(root);
		gameLogic.growSnake(root);
	}

	// Create a reset game button which shows up after death
	// this also resets values to original
	Scene gameOverScene() {
		resetGame();
		return sceneMap.get(gameOverScene.KEY);
	}

	// launch application
	public static void main(String[] args) {
		launch(args);
	}


}
