import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

/**
 * Created by Lukas
 * 29-04-2019.
 */
public class Snake {

    ArrayList<Point> theSnake = new ArrayList<>();
    Point lastPoint = new Point(390,300);
    int snakeSize;
    SimpleIntegerProperty snakeSizeProperty = new SimpleIntegerProperty();

    /**
     * Initial state for the snake. Currently 11 points in total.
     */

    public Snake(){

        theSnake.add(new Point(300,300));
        theSnake.add(new Point(310,300));
        theSnake.add(new Point(320,300));
        theSnake.add(new Point(330,300));
        theSnake.add(new Point(340,300));
        theSnake.add(new Point(350,300));
        theSnake.add(new Point(360,300));
        theSnake.add(new Point(370,300));
        theSnake.add(new Point(380,300));
        theSnake.add(new Point(390,300));


        snakeSize = 10;

    }

    /**
     * Used for replacing the snake on the canvas, whenever it hits the border
     * @param xCord x coordinate
     * @param yCord y coordinate
     */

    public void setLocation(double xCord, double yCord){

        for (int i = 0; i < theSnake.size(); i++) {

            theSnake.get(i).setxCord(xCord);

            theSnake.get(i).setyCord(yCord);


        }

    }

    /**
     * Gets the points from the snake
     * @return the ArrayList of the snake
     */
    public ArrayList<Point> getPoint(){

        return theSnake;

    }


    /**
     * Will add a head to the direction given
     * @param direction UP, DOWN, LEFT or RIGHT
     */
    public void addHead(SimpleStringProperty direction){


        if (direction.get().equals("UP")){

            Point newPoint = new Point((int)(lastPoint.getxCord()),(int)lastPoint.getyCord()-10);
            theSnake.add(newPoint);
            lastPoint = newPoint;

        } else if (direction.get().equals("DOWN")){

            Point newPoint = new Point((int)(lastPoint.getxCord()),(int)lastPoint.getyCord()+10);
            theSnake.add(newPoint);
            lastPoint = newPoint;

        } else if (direction.get().equals("LEFT")){

            Point newPoint = new Point((int)(lastPoint.getxCord()-10),(int)lastPoint.getyCord());
            theSnake.add(newPoint);
            lastPoint = newPoint;

        } else if (direction.get().equals("RIGHT")){

            Point newPoint = new Point((int)(lastPoint.getxCord()+10),(int)lastPoint.getyCord());
            theSnake.add(newPoint);
            lastPoint = newPoint;


        }

        snakeSizeProperty.setValue(theSnake.size());
    }


    /**
     * Gets the size of the snake. Used for binding
     * @return size of snake in IntegerProperty
     */
    public IntegerProperty getSnakeSizeProperty(){

        return snakeSizeProperty;

    }

    /**
     * Neck is the point behind the head
     * @return a Point
     */

    public Point getNeck(){

        return theSnake.get(theSnake.size()-2);

    }

    /**
     * The front element in the ArrayList
     * @return a Point
     */

    public Point getHead(){

        return theSnake.get(theSnake.size()-1);

    }


    /**
     * The first element of the ArrayList
     * @return a Point
     */

    public Point getTail(){

        return theSnake.get(0);
    }

    /**
     * Remove the first element on the snake ArrayList
     */

    public void removeTail(){
        theSnake.remove(0);
    }



}
