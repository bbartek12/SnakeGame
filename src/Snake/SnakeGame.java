package Snake;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SnakeGame extends Application {

	ArrayList<Snake> snake = new ArrayList<Snake>(20); // store the body of the snake
	Group root = new Group();
	Fruit fruit = new Fruit(300, 300);
	Scene scene = new Scene(root, 600, 600);

	// Retrieve the two scenes
	StartScene startScene = new StartScene();
	GameOverScene gameOverScene = new GameOverScene();

	// Get the buttons from the scenes
	Button btn = startScene.startBtn;
	Button reset = gameOverScene.reset;

	Map<String, Scene> sceneMap = new HashMap<>();


	// Shifts entire snake
	// Since each piece must follow each other we scan array of pieces and shift each in reverse order
	// Move current piece into position of previous piece
	void follow(double posX, double posY) {
		// protect against out of bounds error when sneak head is destroyed
		// unnecessary since head is created before animation loop
		if(snake.size() > 1) {
			// Going from the end of the snake shift each piece to the position of the piece ahead of it
			for(int i = snake.size()-1; i > 0; i--) {
				if(i == 1) { // prevents from first piece being overlapped
					copyDirection(snake.get(1),snake.get(0));
					snake.get(1).setTranslateX(posX);
					snake.get(1).setTranslateY(posY);
				}
				else {
					copyDirection(snake.get(i),snake.get(i-1));
					snake.get(i).setTranslateX(snake.get(i-1).getTranslateX());
					snake.get(i).setTranslateY(snake.get(i-1).getTranslateY());
				}
			}
		}
	}


	void playerDirection(Snake head){
		// after picking direction with button input go in that direction
		if(head.left) {
			head.goLeft();
		}
		else if(head.right) {
			head.goRight();
		}
		else if(head.up) {
			head.goUp();
		}
		else if(head.down) {
			head.goDown();
		}
	}

	// copy over the direction of object snake s2 to s1
	void copyDirection(Snake s1, Snake s2) {
		s1.down = s2.down;
		s1.left = s2.left;
		s1.up = s2.up;
		s1.right = s2.right;
	}

	void setGrowthPosition(Snake s1, Snake s2){

		// these if statements do not seem to work since it would always spawn with down being true for each rectangle
		if (s1.up) { // if last piece is going up add a piece behind it
			s2.moveUp();
			s2.setTranslateX(s1.getTranslateX());
			s2.setTranslateY(s1.getTranslateY() + 20);
		}
		// if last piece is going down add a piece above it
		else if (s1.down) {
			s2.moveDown();
			s2.setTranslateX(s1.getTranslateX());
			s2.setTranslateY(s1.getTranslateY() - 20);
		}
		// if last piece is going left add a piece to right of it
		else if (s1.left) {
			s2.moveLeft();
			s2.setTranslateX(s1.getTranslateX() + 20);
			s2.setTranslateY(s1.getTranslateY());
		}
		// if last piece is going right a piece to the left of it
		else if (s1.right) {
			s2.moveRight();
			s2.setTranslateX(s1.getTranslateX() - 20);
			s2.setTranslateY(s1.getTranslateY());
		}
	}

	// Creates snake if there is none or adds a piece to end of the body
	void growSnake() {
		if(snake.size() == 0) {
			Snake bod = new Snake(280, 280);

			snake.add(bod);
			root.getChildren().add(bod);
		}
		else {
			Snake bod =  new Snake(280, 280);

			setGrowthPosition(snake.get(snake.size()-1), bod);
			snake.add(bod);
			root.getChildren().add(bod);
		}
	}

	// creating a new fruit object is pointless so I simply move it when it is eaten
	void createFruit() {
		fruit.setTranslateX(Math.random()*500);
		fruit.setTranslateY(Math.random()*500);

		// If fruit is create on top of snake head move it
		if(fruit.getBoundsInParent().intersects(snake.get(0).getBoundsInParent()))
			createFruit();

	}


	// This checks if bounds of the snake head touched the fruit
	// this may be too "sensitive" since even touching the border will count
	// should be changed to collision detection like check after fruit pieces spawn on perfect grid
	void snakeAteFruit() {
		if( snake.get(0).getBoundsInParent().intersects(fruit.getBoundsInParent())){
			growSnake();
			createFruit();
		}
	}

	// Checks if the head touched any other piece of the snake
	// doesn't check 2nd piece since it is impossible to eat body without 4 body pieces (so can be changed further)
	void collisionDetection() {

		for(int i = 2; i < snake.size()-1; i++) {
			if(Math.abs(snake.get(0).getTranslateX()  - snake.get(i).getTranslateX()) < 0.0001 && Math.abs(snake.get(0).getTranslateY() - snake.get(i).getTranslateY()) < 0.0001) {
				System.out.println("yay"); //DEBUG
				// Platform.exit();
				snake.get(0).isDead = true; // game over
			}
		}
	}

	// Checks if player is out of bounds
	// or if the snake eat is body-- this is checked in collisionDetection()
	boolean isPlayerDead() {

		// When the snake head goes outside the bounds
		if(snake.get(0).getTranslateX() > scene.getWidth() || snake.get(0).getTranslateX() < 0
				|| snake.get(0).getTranslateY() > scene.getHeight() || snake.get(0).getTranslateY() < 0 ) {
			return true;
		}

		if(snake.get(0).isDead) { // flag is raised in collisionDetection to mark snake eat its own body
			return true;
		}

		return false;
	}
	//------------------------------------------------------------------------------------------------------------

	@Override
	public void start(Stage primarystage) {

		sceneMap.put(startScene.KEY, startScene.createScene());
		sceneMap.put(gameOverScene.KEY, gameOverScene.createScene());

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

		// Start snake with length of 3
		growSnake();
		growSnake();
		growSnake();

		// move fruit to a random place and diplay it
		createFruit();
		root.getChildren().add(fruit);

		// button input uses player object functions
		// sets the values to true or false to avoid spamming of buttons
		// if the snake moved with each button press the game would be broken since the body would shift 1 block
		scene.setOnKeyPressed(e-> {

			if(e.getCode() == KeyCode.W){
				snake.get(0).moveUp();
			}
			else if(e.getCode() == KeyCode.A){
				snake.get(0).moveLeft();
				//   growSnake(); // DEBUG - growth + follow
			}
			else if(e.getCode() == KeyCode.S){
				snake.get(0).moveDown();
			}
			else if(e.getCode() == KeyCode.D){
				snake.get(0).moveRight();
			}
		});


		// Animates constant movement
		// Check for collision and out of bounds death
		// also move fruit when it is eaten
		new AnimationTimer() {
			int counter = 0;

			@Override
			public void handle(long now) {

				if(counter %10 == 0) { // slow down fps of the game

					// set so when game is over none of the functions are accessed
					// a better idea may be to stop the animation timer and reset it.
					if(snake.size() > 0) {

						double posX = snake.get(0).getTranslateX();
						double posY = snake.get(0).getTranslateY();

						follow(posX,posY);

						// after picking direction with button input go in that direction
						playerDirection(snake.get(0));

						collisionDetection();
						snakeAteFruit();
						if(isPlayerDead()) {
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
		snake.clear();

		// add fruit back to root and move it
		root.getChildren().add(fruit);
		fruit.setTranslateX(Math.random()*500);
		fruit.setTranslateY(Math.random()*500);

		// reset snake to original size
		growSnake();
		growSnake();
		growSnake();
	}

	// Create a reset game button which shows up after death
	// this also resets values to original
	Scene gameOverScene() {
		resetValues();
		return sceneMap.get("gameOver");
	}

	// launch application
	public static void main(String[] args){
		launch(args);
	}


}
