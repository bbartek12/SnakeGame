import javafx.application.*;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.animation.*;
import java.util.*;

public class GameScene extends Application {

    ArrayList<Player> snake = new ArrayList<Player>(20); // store the body of the snake
    Group root = new Group();
    Fruit fruit = new Fruit(300, 300);
    Scene scene = new Scene(root, 600, 600);
    Button btn = new Button("Start");
    Button reset = new Button("Restart");

   // creates a rectangle object that randomly moves around the game 
   // this is the red rectangle the snake eats
   public class Fruit extends Rectangle{
	   
	   Fruit(double x, double y){
		   super(20, 20, Color.RED);
		   setTranslateX(x);
		   setTranslateY(y);
	   }
   }
   
   // creating a new fruit object is pointless so I simply move it when it is eaten
   void createFruit() {
	   fruit.setTranslateX(Math.random()*500);
	   fruit.setTranslateY(Math.random()*500);

	   
	   if(fruit.getBoundsInParent().intersects(snake.get(0).getBoundsInParent()))
		   createFruit();

   }
   
   // unused -- remove?
   void removeFruit() {
	   root.getChildren().remove(fruit);
   }
   
 //------------------------------------------------------------------------------------------------------------  
   
   public class Player extends Rectangle{
	
	// death flag
        boolean isDead = false;
	
	// movement directions
        boolean up = false;
        boolean down = false;
        boolean left = false;
        boolean right = false;
	
	// length of snake
        int length;
       
	// Constructor
        Player(int x, int y){

            super(20, 20); // overwrites rectangle classes to size 20 by 20 square
            length = 0; // length of snake
            
            // set body position
	    setTranslateX(x);
            setTranslateY(y);
           
	    // set original movement directions to none
	    up = false;
            down = false;
            left = false;
            right = false;
            
        }
        
	// Copy constructor? which allows to set directions
        Player(int x, int y, boolean l, boolean r, boolean u, boolean d){

            super(20, 20); // overwrites rectangle classes
            length = 0;
            setTranslateX(x);
            setTranslateY(y);
            this.up = u;
            this.down = d;
            this.left = l;
            this.right = r;
            
        }

        // set snake to move left
        void moveLeft(){
            if(!right){ // block from moving opposite direction
            	left = true;
            	right = false;
            	up = false;
            	down = false;
            }
        }
        
        // set snake to move right
        void moveRight(){
            if(!left){ // block from moving opposite direction
            	left = false;
            	right = true;
            	up = false;
            	down = false;
            }
        }
        
        // set snake to move up
        void moveUp(){
            if(!down){ // block from moving opposite direction
            	left = false;
            	right = false;
            	up = true;
            	down = false;
            }
        }
        void moveDown(){
            if(!up){ // block from moving opposite direction
            	left = false;
            	right = false;
            	up = false;
            	down = true;
            }
        }
        
        // Actually shift the snake in the direction it is set to move
        void goLeft() {
        	if(!right){
        		setTranslateX(getTranslateX() - 20);
        	}
        }
        void goRight() {
        	if(!left){
        		setTranslateX(getTranslateX() + 20);
        	}
        }
        void goDown() {
        	if(!up){  
        		setTranslateY(getTranslateY() + 20);
            }
        }
        void goUp() {
        	if(!down){
            		setTranslateY(getTranslateY() - 20);
        	}
        }
        //--------------------------------------------------------------
  
        
        // Adds a piece depending on direction of current rectangle
        // Should only be used on last piece in snake
        void grow(){
            Player bod = new Player(1000, 1000); // spawns box out of range before adding it to the snake
            if(length < snake.size()) {
	            
		    // these if statements do not seem to work since it would always spawn with down being true for each rectangle
		    if(up){ // if last piece is going up add a piece behind it
	            	bod.moveUp();
	                bod.setTranslateX(getTranslateX());
	                bod.setTranslateY(getTranslateY() + 20);
	            }
		    // if last piece is going down add a piece above it
	            else if(down){   
	            	bod.moveDown();
	                bod.setTranslateX(getTranslateX());
	                bod.setTranslateY(getTranslateY() - 20);
	            }
		    // if last piece is going left add a piece to right of it
	            else if(left){   
	            	bod.moveLeft();
	                bod.setTranslateX(getTranslateX() + 20);
	                bod.setTranslateY(getTranslateY());
	            }
		    // if last piece is going right a piece to the left of it
	            else if(right){    
	            	bod.moveRight();
	                bod.setTranslateX(getTranslateX() - 20);
	                bod.setTranslateY(getTranslateY());
	            }
		    
	            length++;
	            snake.add(bod); // add to list of snake pieces
	            root.getChildren().add(bod); // add to group to make it visible
            }
        }
    } 
    
 //------------------------------------------------------------------------------------------------------------  

   
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
				   snake.get(1).setTranslateX(posX);
				   snake.get(1).setTranslateY(posY);
			   }
			   else {
				   snake.get(i).setTranslateX(snake.get(i-1).getTranslateX());			   			  
				   snake.get(i).setTranslateY(snake.get(i-1).getTranslateY());
			   }
		   }
	   }
   }
   

   
   // Creates snake if there is none or adds a piece to end of the body
   void growSnake() {
	   	   if(snake.size() == 0) {
	   		  Player bod = new Player(280, 280);
	   		  snake.add(bod);
           		  root.getChildren().add(bod);
	   	   }
	   	   else {
	   		  // debug
	   		   System.out.println("before" + snake.get(snake.size()-1).left);
	   		   System.out.println(snake.get(snake.size()-1).right);
	   		   System.out.println(snake.get(snake.size()-1).up);
	   		   System.out.println(snake.get(snake.size()-1).down);
	   		   
	   		   snake.get(snake.size()-1).grow(); // grow from last index
	   		   
	   		   System.out.println("after" + snake.get(snake.size()-2).left);
	   		   System.out.println(snake.get(snake.size()-2).right);
	   		   System.out.println(snake.get(snake.size()-2).up);
	   		   System.out.println(snake.get(snake.size()-2).down);
	   		   
	   		 //  copyDirection(snake.get(snake.size()-1), snake.get(snake.size()-2));
	   	   }
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
	  
	  if(snake.get(0).isDead) { // flag is raised in collisionDetection to mark snake as dead
		  return true;
	  }
	  
	  return false;
  }
  
  //------------------------------------------------------------------------------------------------------------  
   
    @Override
    public void start(Stage primarystage) throws Exception{

        
        primarystage.setTitle("Snake");
        primarystage.setScene(startGameScene()); // set to menu screen
        
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
				
				if(counter %20 == 0) { // slow down fps of the game
					
					// set so when game is over none of the functions are accessed
					// a better idea may be to stop the animation timer and reset it.
					if(snake.size() > 0) { 
						double posX = snake.get(0).getTranslateX();
						double posY = snake.get(0).getTranslateY();

						follow(posX,posY);

						if(snake.get(0).left) {
							snake.get(0).goLeft();
						}
						else if(snake.get(0).right) {
							snake.get(0).goRight();
						}
						else if(snake.get(0).up) {
							snake.get(0).goUp();
						}
						else if(snake.get(0).down) {
							snake.get(0).goDown();
						}			

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

    // Create a menu which starts the game
    Scene startGameScene() {
    	
    	btn.setStyle("-fx-background-color: red; -fx-font-weight: bold; -fx-font-size: 30");
    	btn.setPrefSize(200, 100);
    	
    	Text title = new Text("Snake");
    	
    	title.setStyle("-fx-font-weight: bold; -fx-font-size: 50");

    	VBox vbox = new VBox(title, btn);
    	vbox.setAlignment(Pos.CENTER);
    	return new Scene(vbox,500,500);
    }
   
  // Create a reset game button which shows up after death
  // this also resets values to original
  Scene gameOverScene() {
    	
	// make button appealing
    	reset.setStyle("-fx-background-color: red; -fx-font-weight: bold; -fx-font-size: 30");
    	reset.setPrefSize(200, 100);
    	
    	Text gameOver = new Text("Game Over");
    	gameOver.setStyle("-fx-font-weight: bold; -fx-font-size: 50");
	
	// used to center button
    	VBox vbox = new VBox(gameOver, reset);
    	vbox.setAlignment(Pos.CENTER);
    	
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
    	
    	return new Scene(vbox,500,500);
    }

    // launch application
    public static void main(String[] args){
        launch(args);
    }

}
