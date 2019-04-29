import javafx.scene.shape.Circle;

import java.util.ArrayList;

/**
 * Created by Lukas
 * 29-04-2019.
 */
public class Snake {

    ArrayList<Point> theSnake = new ArrayList<>();
    Point lastPoint = new Point(340,300);


    public Snake(){

        theSnake.add(new Point(300,300));
        theSnake.add(new Point(310,300));
        theSnake.add(new Point(320,300));
        theSnake.add(new Point(330,300));
        theSnake.add(new Point(340,300));


    }


    public ArrayList<Point> getPoint(){

        return theSnake;

    }

    public void addPoint(){
        Point newPoint = new Point((int)(lastPoint.getxCord()+10),300);
        theSnake.add(newPoint);
        lastPoint = newPoint;
    }



}
