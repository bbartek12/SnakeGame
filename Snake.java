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

// create a linked list of rectangle or player objects. Set direction of previous node to be used using current node. Also set 
// also position previous node based on current node so if current node is going up previous node is added to back.

// walk arraylist and move head up by size of block and put previous node to current node's position

public class GameScene extends Application {

  //  Player head = new Player(300, 300);
    ArrayList<Player> snake = new ArrayList<Player>(20);
    Group root = new Group();
    Fruit fruit = new Fruit(300, 300);
    Scene scene = new Scene(root, 600, 600);
    Button btn = new Button("Start");
    Button reset = new Button("Restart");

    
   public class Fruit extends Rectangle{
	   
	   Fruit(double x, double y){
		   super(20, 20, Color.RED);
		   setTranslateX(x);
		   setTranslateY(y);
	   }
   }
   
   void createFruit() {
	   fruit.setTranslateX(Math.random()*500);
	   fruit.setTranslateY(Math.random()*500);

	   
	   if(fruit.getBoundsInParent().intersects(snake.get(0).getBoundsInParent()))
		   createFruit();

   }
   
   void removeFruit() {
	   root.getChildren().remove(fruit);
   }
   
 //------------------------------------------------------------------------------------------------------------  
   
   public class Player extends Rectangle{
        boolean isDead = false;
        boolean up = false;
        boolean down = false;
        boolean left = false;
        boolean right = false;
        int length;
       
        Player(int x, int y){

            super(20, 20); // overwrites rectangle classes
            length = 0;
            setTranslateX(x);
            setTranslateY(y);
            up = false;
            down = false;
            left = false;
           right = false;
            
        }
        
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
        
  
        
        // Adds a piece depending on direction of current rectangle
        // Should only be used on last piece in snake
        void grow(){
            Player bod = new Player(1000, 1000); // spawns box out of range before adding it to the snake
            if(length < snake.size()) {
	            
            	// these if statements do not seem to work since it would always spawn with down being true for each rectangle
            	if(up){
	            	bod.moveUp();
	                bod.setTranslateX(getTranslateX());
	                bod.setTranslateY(getTranslateY() + 20);
	            }
	            else if(down){   
	            	bod.moveDown();
	                bod.setTranslateX(getTranslateX());
	                bod.setTranslateY(getTranslateY() - 20);
	            }
	            else if(left){   
	            	bod.moveLeft();
	                bod.setTranslateX(getTranslateX() + 20);
	                bod.setTranslateY(getTranslateY());
	            }
	            else if(right){    
	            	bod.moveRight();
	                bod.setTranslateX(getTranslateX() - 20);
	                bod.setTranslateY(getTranslateY());
	            }
	            length++;
	            snake.add(bod);
	            root.getChildren().add(bod); // add to group to make it visible
            }
        }
    } 
    
 //------------------------------------------------------------------------------------------------------------  

   
    // Shifts entire snake
   // Since each piece must follow each other we scan array of pieces and shift each in reverse order
   // Move current piece into position of previous piece
   void follow(double posX, double posY) {
   
	   if(snake.size() > 1) {
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
   
   
   void copyDirection(Player s1, Player s2) {
	   s1.down = s2.down;
	   s1.left = s2.left;
	   s1.up = s2.up;
	   s1.right = s2.right;
   }
   
   // Creates snake if there is none or adds a piece to end of the body
   void growSnake() {
	   	   if(snake.size() == 0) {
	   		  Player bod = new Player(280, 280);
	   		  snake.add(bod);
              root.getChildren().add(bod);
	   	   }
	   	   else {
	   		  
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
   
   
   void snakeAteFruit() {
	   
	   if( snake.get(0).getBoundsInParent().intersects(fruit.getBoundsInParent())){
	//	   removeFruit();
		   growSnake();
		   createFruit();
	   }	   
   }
   

  void collisionDetection() {
	   
	   for(int i = 2; i < snake.size()-1; i++) {
		 //  if( snake.get(i).getBoundsInParent().intersects(snake.get(0).getBoundsInParent())){
			if(Math.abs(snake.get(0).getTranslateX()  - snake.get(i).getTranslateX()) < 0.0001 && Math.abs(snake.get(0).getTranslateY() - snake.get(i).getTranslateY()) < 0.0001) {  
			   System.out.println("yay"); //DEBUG
			  // Platform.exit();
			   snake.get(0).isDead = true; // game over
		   }
	   }
   }
  
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
        primarystage.setScene(startGameScene());
        
        btn.setOnAction(e->
    	{
            primarystage.setScene(scene);
            primarystage.show();
    	});
        
        reset.setOnAction(e->
        {
        	 primarystage.setScene(scene);
             primarystage.show();
        });
        
        // Start snake with length of 3
        growSnake();
    	growSnake();
    	growSnake();

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

    Scene startGameScene() {
    	
    	btn.setStyle("-fx-background-color: red; -fx-font-weight: bold; -fx-font-size: 30");
    	btn.setPrefSize(200, 100);
    	
    	Text title = new Text("Snake");
    	
    	title.setStyle("-fx-font-weight: bold; -fx-font-size: 50");

    	VBox vbox = new VBox(title, btn);
    	vbox.setAlignment(Pos.CENTER);
    	return new Scene(vbox,500,500);
    }
    
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
