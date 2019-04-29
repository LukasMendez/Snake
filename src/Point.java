import javafx.scene.shape.Circle;

import static javafx.scene.paint.Color.RED;

/**
 * Created by Lukas
 * 29-04-2019.
 */
public class Point {

    private double xCord;
    private double yCord;

    private Circle circle;


    public Point(int xCord, int yCord){

        this.xCord=xCord;
        this.yCord=yCord;

        circle = new Circle(3,RED);

    }


    public double getxCord(){
        return xCord;
    }

    public double getyCord(){
        return yCord;
    }






    public Circle getCircle(){

        return circle;
    }






}
