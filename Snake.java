package Snake;

import javafx.scene.shape.Rectangle;

public class Snake extends Rectangle {

    Main main = new Main();

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
    Snake(int x, int y){

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
}



