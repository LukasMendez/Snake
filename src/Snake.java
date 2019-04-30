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
    Point lastPoint = new Point(340,300);
    int snakeSize;


    private final StringProperty directionProperty = new SimpleStringProperty();



    public Snake(){

        theSnake.add(new Point(300,300));
        theSnake.add(new Point(310,300));
        theSnake.add(new Point(320,300));
        theSnake.add(new Point(330,300));
        theSnake.add(new Point(340,300));
        snakeSize = 5;

    }


    public void setLocation(double xCord, double yCord){

        for (int i = 0; i < theSnake.size(); i++) {

            theSnake.get(i).setxCord(xCord);

            theSnake.get(i).setyCord(yCord);


        }

    }


    public ArrayList<Point> getPoint(){

        return theSnake;

    }


    public final String getName(){

        return directionProperty.getName();

    }


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


            // TODO MAKE IF ELSE STATEMENTS THAT DETERMINE THE DIRECTION OF THE HEAD BASED ON THE CURRENT DIRECTION SELECTED




    }

    public Point getHead(){

        return theSnake.get(theSnake.size()-1);

    }


    public Point getTail(){

        return theSnake.get(0);
    }


    public void removeTail(){
        theSnake.remove(0);
    }



}
