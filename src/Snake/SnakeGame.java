package Snake;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

	GameLogic gameLogic = new GameLogic();
	// Create objects necessary for game
//	ArrayList<Snake> snake = new ArrayList<Snake>(20); // store the body of the snake
	Group root = new Group();
//	Fruit fruit = new Fruit(300, 300);
	int score = 0;
	int setSpeed = 10; // The higher this number is the slower the snake

	// Retrieve the two scenes, create a new one, and something to store the scenes
	StartScene startScene = new StartScene();
	GameOverScene gameOverScene = new GameOverScene();
	Scene scene = new Scene(root, 600, 600);
	Map<String, Scene> sceneMap = new HashMap<>();

	// Get the buttons from the scenes
	Button btn = startScene.startBtn;
	Button reset = gameOverScene.reset;

	Label scoreText = new Label("Score: " + Integer.toString(gameLogic.score));
	HashMap<String, Integer> playerScoreMap;

	void snakeAteUpdate(){
		if(gameLogic.snakeAteFruit(root)) {
			scoreText.setText("Score: " + Integer.toString(gameLogic.score));
		}
	}

	@Override
	public void start(Stage primarystage) {

		// Store scenes in hashmap to allow for swapping
		sceneMap.put(startScene.KEY, startScene.createScene());
		sceneMap.put(gameOverScene.KEY, gameOverScene.createScene());

		score = 0; // set score to 0 at start of game

		scoreText.setStyle("-fx-font-size:30;");
		scoreText.relocate(scene.getWidth()/2-70, 0); // Put score near middle of screen

		primarystage.setTitle("Snake");
		primarystage.setScene(sceneMap.get(StartScene.KEY)); // set to menu screen

		// Button to start game
		btn.setOnAction(e->
		{
			primarystage.setScene(scene);
			primarystage.show();
		});

		// resets game after a death
		reset.setOnAction(e->
		{
			primarystage.setScene(scene);
			primarystage.show();
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
							primarystage.setScene(gameOverScene());
							primarystage.show();
						}
					}
				}
				counter++;
			}
		}.start();

		primarystage.show();
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


			// Connect to database
		//	Connection connection = ScoresDatabase.connect();
//		try {
//			HashMap<String, Integer> playerScoreMap = ScoresDatabase.getSortedScores();
//			for(Map.Entry pair : playerScoreMap.entrySet()){
//				System.out.println(pair.getKey());
//				System.out.println( pair.getValue());
//			}
//		//	ScoresDatabase.insert("test2", 399);
//			System.out.println("234242");
//		} catch (SQLException throwables) {
//			throwables.printStackTrace();
//		}


		//	System.out.println(Integer.toString(playerScoreMap.get("test")));

		launch(args);
	}


}
