package Snake;

import javafx.scene.Group;
import javafx.scene.Scene;

import java.util.ArrayList;

public class GameLogic {


    // Create objects necessary for game
    ArrayList<Snake> snake = new ArrayList<Snake>(20); // store the body of the snake
    Fruit fruit = new Fruit(300, 300);
    int score = 0;


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
    void growSnake(Group snakeHead) {

        Snake bod = new Snake(280, 280);
        if(snake.size() != 0) {
            setGrowthPosition(snake.get(snake.size()-1), bod);
        }
        snake.add(bod);
        snakeHead.getChildren().add(bod);
    }

    // creating a new fruit object is pointless so I simply move it when it is eaten
    void createFruit() {
        fruit.setTranslateX(Math.random()*500);
        fruit.setTranslateY(Math.random()*500);

        // Safety against illegal access
        if(snake.isEmpty()){
            return;
        }

        // If fruit is create on top of snake head move it
        if(fruit.getBoundsInParent().intersects(snake.get(0).getBoundsInParent()))
            createFruit();

    }

    void increaseScore(){

        score += fruit.getPoints();
    }


    // This checks if bounds of the snake head touched the fruit
    // this may be too "sensitive" since even touching the border will count
    // should be changed to collision detection like check after fruit pieces spawn on perfect grid
    boolean snakeAteFruit(Group root) {
        if( snake.get(0).getBoundsInParent().intersects(fruit.getBoundsInParent())){
            increaseScore();
            growSnake(root);
            createFruit();
            return true;
        }
        return false;
    }


    // Checks if the head touched any other piece of the snake
    // doesn't check 2nd piece since it is impossible to eat body without 4 body pieces (so can be changed further)
    void collisionDetection() {

        for(int i = 2; i < snake.size()-1; i++) {
            if(Math.abs(snake.get(0).getTranslateX()  - snake.get(i).getTranslateX()) < 0.0001 && Math.abs(snake.get(0).getTranslateY() - snake.get(i).getTranslateY()) < 0.0001) {
                snake.get(0).isDead = true; // game over
            }
        }
    }

    // Checks if player is out of bounds
    // or if the snake eat is body-- this is checked in collisionDetection()
    boolean isPlayerDead(Scene scene) {

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
}
