import javafx.application.*;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.layout.Pane;
import javafx.animation.*;
import java.util.*;

// create a linked list of rectangle or player objects. Set direction of previous node to be used using current node. Also set 
// also position previous node based on current node so if current node is going up previous node is added to back.

// walk arraylist and move head up by size of block and put previous node to current node's position

public class GameScene extends Application {

    Player head = new Player(250, 250);
    ArrayList<Player> snake = new ArrayList<Player>(5);
    Group root = new Group();
    double posX;
    double posY;

  

   public  class Player extends Rectangle{
        boolean isDead = false;
        boolean up = false;
        boolean down = true;
        boolean left = false;
        boolean right = false;
        int length;
       
        Player(int x, int y){

            super(20, 20); // overwrites rectangle classes
   //         dead = false;
            length = 0;
  //          this.posX = x;
    //        this.posY = y;
            setTranslateX(x);
            setTranslateY(y);
        }

        void moveLeft(){
            if(!right){ // block from moving opposite direction
            	left = true;
            	right = false;
            	up = false;
            	down = false;
                setTranslateX(getTranslateX() - 20);
            }
        }

        void moveRight(){
            if(!left){
            	left = false;
            	right = true;
            	up = false;
            	down = false;
                setTranslateX(getTranslateX() + 20);
            }
        }
        void moveUp(){
            if(!down){
            	left = false;
            	right = false;
            	up = true;
            	down = false;
            	setTranslateY(getTranslateY() - 20);
            }
        }
        void moveDown(){
            if(!up){
            	left = false;
            	right = false;
            	up = false;
            	down = true;
                setTranslateY(getTranslateY() + 20);
            }
        }
        
        // Adds a piece depending on direction of current rectangle
        // Should only be used on last piece in snake
        void grow(){
            Player bod = new Player(20, 20);
         if(length < snake.size()) {
	            if(up){      
	                bod.setTranslateX(getTranslateX());
	                bod.setTranslateY(getTranslateY() + 20);
	            }
	             if(down){      
	                bod.setTranslateX(getTranslateX());
	                bod.setTranslateY(getTranslateY() - 20);
	            }
	            if(left){      
	                bod.setTranslateX(getTranslateX() + 20);
	                bod.setTranslateY(getTranslateY());
	            }
	            if(right){      
	                bod.setTranslateX(getTranslateX() - 20);
	                bod.setTranslateY(getTranslateY());
	            }
	            length++;
	            snake.add(bod);
	            root.getChildren().add(bod); // add to group to make it visible
         }
           // root.getChildren().add(bod);   
        }
    } 
    
    // Shifts entire snake
   // Since each piece must follow each other we scan array of pieces and shift each in reverse order
   // Move current piece into position of previous piece
   void follow() {
	   
	   if(snake.size() > 1) {
		   
		   for(int i = snake.size(); i > 1; i--) {
			   snake.get(i-1).setTranslateX(snake.get(i-2).getTranslateX());
			   snake.get(i-1).setTranslateY(snake.get(i-2).getTranslateY());
		   }
		   
		   /*
		   ListIterator<Player> li = snake.listIterator(snake.size());
		   
		   while(li.hasPrevious()) {
			 
			 Player prev =   snake.get(li.previousIndex()); // previous snake piece
			 snake.get(li.previousIndex() +1).setTranslateX(prev.getTranslateX()); // current snake piece get previous snake x position
			 snake.get(li.previousIndex() +1).setTranslateY(prev.getTranslateY()); // current snake piece get previous snake y position
	
			 li.previous();
			 
		   } 
	 */  }
   }
   
   void growSnake() {
	   snake.get(snake.size()-1).grow(); // grow from last index
   }
   
   void snakeAteFruit() {
	   
   }
   
    @Override
    public void start(Stage primarystage) throws Exception{
    	

    	
        snake.add(head); // this will be the part which is controlled
        
        
        
    //	Canvas canvas = new Canvas(500,500);
  //  	GraphicsContext gc = canvas.getGraphicsContext2D();
    	
    	root.getChildren().add(snake.get(0));
    	
        Scene scene = new Scene(root, 500, 500);
        primarystage.setTitle("Snake");
        
        
        primarystage.setScene(scene);

        
        scene.setOnKeyPressed(e-> {
            if(e.getCode() == KeyCode.W){
                snake.get(0).moveUp();
            }
            if(e.getCode() == KeyCode.A){
                snake.get(0).moveLeft();
                growSnake();
            }
            if(e.getCode() == KeyCode.S){
                snake.get(0).moveDown();
            }
            if(e.getCode() == KeyCode.D){
                snake.get(0).moveRight();
            }
        });
        

        new AnimationTimer() {
        	int counter = 0;
			@Override
			public void handle(long now) {
				
				if(counter %60 == 0) {
					posX = snake.get(0).getTranslateX();
					posY = snake.get(0).getTranslateY();
					
				if(snake.get(0).left) {
					snake.get(0).moveLeft();
				}
				else if(snake.get(0).right) {
					snake.get(0).moveRight();
				}
				else if(snake.get(0).up) {
					snake.get(0).moveUp();
				}
				else if(snake.get(0).down) {
					snake.get(0).moveDown();
				}
				follow();
			}
			counter++;
			}
        	
        }.start();
        
        
        primarystage.show();
    }



    public static void main(String[] args){
        launch(args);
    }

}
