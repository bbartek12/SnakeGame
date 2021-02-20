package Snake;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

// creates a rectangle object that randomly moves around the game
// this is the red rectangle the snake eats
public class Fruit extends Rectangle {

    private final static int POINTS = 10;

    Fruit(double x, double y){
        super(20, 20, Color.RED);
        setTranslateX(x);
        setTranslateY(y);
    }

    // Get points when the fruit is eaten
    public int getPoints(){
        return POINTS;
    }
}
