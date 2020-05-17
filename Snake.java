import javafx.application.*;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.layout.Pane;
import javafx.animation.*;
import java.util.*;

// create a linked list of rectangle or player objects. Set direction of previous node to be used using current node. Also set 
// also position previous node based on current node so if current node is going up previous node is added to back.

// walk arraylist and move head up by size of block and put previous node to current node's position

public class Snake extends Application {

    Player head = new Player(250, 250);
    ArrayList<Player> snake = new ArrayList<Player>(5);
  

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

        void grow(){
            Player bod = new Player(20, 20);
            if(up == true){      
                bod.setTranslateX(getTranslateX() - 20);
                bod.setTranslateY(getTranslateY() - 20);
            }
             if(down == true){      
                bod.setTranslateX(getTranslateX() - 20);
                bod.setTranslateY(getTranslateY() - 20);
            }
            if(left == true){      
                bod.setTranslateX(getTranslateX() - 20);
                bod.setTranslateY(getTranslateY() - 20);
            }
            if(right == true){      
                bod.setTranslateX(getTranslateX() - 20);
                bod.setTranslateY(getTranslateY() - 20);
            }
            
            snake.add(bod);
           // root.getChildren().add(bod);   
        }
    } 
    
    
    @Override
    public void start(Stage primarystage) throws Exception{
   //    Rectangle snake = new Rectangle(50,50,50,50);
//       Group root = new Group(snake);
        snake.add(head);
        Group root = new Group(snake.get(0));
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

        primarystage.show();
    }



    public static void main(String[] args){
        launch(args);
    }

}
