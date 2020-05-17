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
  

   public  class Player extends Rectangle{
        boolean isDead = false;
        boolean up = false;
        boolean down = true;
        boolean left = false;
        boolean right = false;
        int length;
        int posX;
        int posY;

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
            if(right == false){ // block from moving opposite direction
            	left = true;
            	right = false;
            	up = false;
            	down = false;
                setTranslateX(getTranslateX() - 1);
            }
        }

        void moveRight(){
            if(left == false){
            	left = false;
            	right = true;
            	up = false;
            	down = false;
                setTranslateX(getTranslateX() +1);
            }
        }
        void moveUp(){
            if(down == false){
            	left = false;
            	right = false;
            	up = true;
            	down = false;
            	setTranslateY(getTranslateY() - 1);
            }
        }
        void moveDown(){
            if(up == false){
            	left = false;
            	right = false;
            	up = false;
            	down = true;
                setTranslateY(getTranslateY() + 1);
            }
        }
        
        // Adds a piece depending on direction of current rectangle
        // Should only be used on last piece in snake
        void grow(){
            Player bod = new Player(20, 20);
         if(length < snake.size()) {
	            if(up){      
	                bod.setTranslateX(getTranslateX() - 20);
	                bod.setTranslateY(getTranslateY() - 20);
	            }
	             if(down){      
	                bod.setTranslateX(getTranslateX() - 20);
	                bod.setTranslateY(getTranslateY() - 20);
	            }
	            if(left){      
	                bod.setTranslateX(getTranslateX() - 20);
	                bod.setTranslateY(getTranslateY() - 20);
	            }
	            if(right){      
	                bod.setTranslateX(getTranslateX() - 20);
	                bod.setTranslateY(getTranslateY() - 20);
	            }
	            length++;
	            snake.add(bod);
         }
           // root.getChildren().add(bod);   
        }
    } 
    
    // Shifts entire snake
   // Since each piece must follow each other we scan array of pieces and shift each in reverse order
   // Move current piece into position of previous piece
   void follow(ArrayList<Player> snake) {
	   
	   ListIterator<Player> li = snake.listIterator(snake.size());
	   
	   while(li.hasPrevious()) {
		 
		 Player prev =   snake.get(li.previousIndex()); // previous snake piece
		 snake.get(li.previousIndex() +1).setTranslateX(prev.getTranslateX()); // current snake piece get previous snake x position
		 snake.get(li.previousIndex() +1).setTranslateY(prev.getTranslateY()); // current snake piece get previous snake y position

		 li.previous();
	   }
	   
   }
   
    @Override
    public void start(Stage primarystage) throws Exception{
    	

    	
        snake.add(head); // this will be the part which is controlled
        
        Group root = new Group();
        
    	Canvas canvas = new Canvas(500,500);
    	GraphicsContext gc = canvas.getGraphicsContext2D();
    	
    	root.getChildren().add(snake.get(0));
    	
        Scene scene = new Scene(root, 500, 500);
        primarystage.setTitle("Snake");
        
        
        primarystage.setScene(scene);

        
        scene.setOnKeyPressed(e-> {
            if(e.getCode() == KeyCode.W){
                snake.get(0).moveUp();
              //  snake.grow();
            }
            if(e.getCode() == KeyCode.A){
                snake.get(0).moveLeft();
            }
            if(e.getCode() == KeyCode.S){
                snake.get(0).moveDown();
            }
            if(e.getCode() == KeyCode.D){
                snake.get(0).moveRight();
            }
        });
        

        new AnimationTimer() {

			@Override
			public void handle(long now) {

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
				
			}
        	
        }.start();
        
        
        primarystage.show();
    }



    public static void main(String[] args){
        launch(args);
    }

}
