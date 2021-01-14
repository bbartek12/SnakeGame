package Snake;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

// creates a rectangle object that randomly moves around the game
// this is the red rectangle the snake eats
public class Fruit extends Rectangle {

    Fruit(double x, double y){
        super(20, 20, Color.RED);
        setTranslateX(x);
        setTranslateY(y);
    }
}
