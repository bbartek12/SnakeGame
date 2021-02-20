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


//	// Shifts entire snake
//	// Since each piece must follow each other we scan array of pieces and shift each in reverse order
//	// Move current piece into position of previous piece
//	void follow(double posX, double posY) {
//		// protect against out of bounds error when sneak head is destroyed
//		// unnecessary since head is created before animation loop
//		if(snake.size() > 1) {
//			// Going from the end of the snake shift each piece to the position of the piece ahead of it
//			for(int i = snake.size()-1; i > 0; i--) {
//				if(i == 1) { // prevents from first piece being overlapped
//					copyDirection(snake.get(1),snake.get(0));
//					snake.get(1).setTranslateX(posX);
//					snake.get(1).setTranslateY(posY);
//				}
//				else {
//					copyDirection(snake.get(i),snake.get(i-1));
//					snake.get(i).setTranslateX(snake.get(i-1).getTranslateX());
//					snake.get(i).setTranslateY(snake.get(i-1).getTranslateY());
//				}
//			}
//		}
//	}
//

//	void playerDirection(Snake head){
//		// after picking direction with button input go in that direction
//		if(head.left) {
//			head.goLeft();
//		}
//		else if(head.right) {
//			head.goRight();
//		}
//		else if(head.up) {
//			head.goUp();
//		}
//		else if(head.down) {
//			head.goDown();
//		}
//	}

//	// copy over the direction of object snake s2 to s1
//	void copyDirection(Snake s1, Snake s2) {
//		s1.down = s2.down;
//		s1.left = s2.left;
//		s1.up = s2.up;
//		s1.right = s2.right;
//	}

//	void setGrowthPosition(Snake s1, Snake s2){
//
//		// these if statements do not seem to work since it would always spawn with down being true for each rectangle
//		if (s1.up) { // if last piece is going up add a piece behind it
//			s2.moveUp();
//			s2.setTranslateX(s1.getTranslateX());
//			s2.setTranslateY(s1.getTranslateY() + 20);
//		}
//		// if last piece is going down add a piece above it
//		else if (s1.down) {
//			s2.moveDown();
//			s2.setTranslateX(s1.getTranslateX());
//			s2.setTranslateY(s1.getTranslateY() - 20);
//		}
//		// if last piece is going left add a piece to right of it
//		else if (s1.left) {
//			s2.moveLeft();
//			s2.setTranslateX(s1.getTranslateX() + 20);
//			s2.setTranslateY(s1.getTranslateY());
//		}
//		// if last piece is going right a piece to the left of it
//		else if (s1.right) {
//			s2.moveRight();
//			s2.setTranslateX(s1.getTranslateX() - 20);
//			s2.setTranslateY(s1.getTranslateY());
//		}
//	}
//
//	// Creates snake if there is none or adds a piece to end of the body
//	void growSnake() {
//
//		Snake bod = new Snake(280, 280);
//		if(snake.size() != 0) {
//			setGrowthPosition(snake.get(snake.size()-1), bod);
//		}
//		snake.add(bod);
//		root.getChildren().add(bod);
//	}

//	// creating a new fruit object is pointless so I simply move it when it is eaten
//	void createFruit() {
//		fruit.setTranslateX(Math.random()*500);
//		fruit.setTranslateY(Math.random()*500);
//
//		// Safety against illegal access
//		if(snake.isEmpty()){
//			return;
//		}
//
//		// If fruit is create on top of snake head move it
//		if(fruit.getBoundsInParent().intersects(snake.get(0).getBoundsInParent()))
//			createFruit();
//
//	}

//	void updateScore(){
////		score += gameLogic.fruit.getPoints();
//	}

	void snakeAteUpdate(){
		if(gameLogic.snakeAteFruit(root)) {
			scoreText.setText("Score: " + Integer.toString(gameLogic.score));
		}
	}

//	// This checks if bounds of the snake head touched the fruit
//	// this may be too "sensitive" since even touching the border will count
//	// should be changed to collision detection like check after fruit pieces spawn on perfect grid
//	void snakeAteFruit() {
//		if( gameLogic.snake.get(0).getBoundsInParent().intersects(gameLogic.fruit.getBoundsInParent())){
//			updateScore();
//			gameLogic.growSnake(root);
//			gameLogic.createFruit();
//		}
//	}

//	// Checks if the head touched any other piece of the snake
//	// doesn't check 2nd piece since it is impossible to eat body without 4 body pieces (so can be changed further)
//	void collisionDetection() {
//
//		for(int i = 2; i < snake.size()-1; i++) {
//			if(Math.abs(snake.get(0).getTranslateX()  - snake.get(i).getTranslateX()) < 0.0001 && Math.abs(snake.get(0).getTranslateY() - snake.get(i).getTranslateY()) < 0.0001) {
//				System.out.println("yay"); //DEBUG
//				// Platform.exit();
//				snake.get(0).isDead = true; // game over
//			}
//		}
//	}


	//------------------------------------------------------------------------------------------------------------

	@Override
	public void start(Stage primarystage) {

		// Store scenes in hashmap to allow for swapping
		sceneMap.put(startScene.KEY, startScene.createScene());
		sceneMap.put(gameOverScene.KEY, gameOverScene.createScene());

		score = 0; // set score to 0 at start of game

		scoreText.setStyle("-fx-font-size:30;");

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
		resetValues();

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
	public void resetValues(){

		// clear snake
		root.getChildren().clear();
		gameLogic.snake.clear();

		gameLogic.score = 0;
		scoreText.setText("Score: " + gameLogic.score);
		root.getChildren().add(scoreText);

		scoreText.relocate(200, 0);
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
		resetValues();
		return sceneMap.get(gameOverScene.KEY);
	}

	// launch application
	public static void main(String[] args){
		launch(args);
	}


}
