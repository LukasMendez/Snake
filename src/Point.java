import javafx.scene.shape.Circle;

import static javafx.scene.paint.Color.RED;

/**
 * Created by Lukas
 * 29-04-2019.
 */
public class Point {



    private double xCord;
    private double yCord;


    public Point(int xCord, int yCord){

        this.xCord=xCord;
        this.yCord=yCord;

    }


    public void setxCord(double xCord) {
        this.xCord = xCord;
    }

    public void setyCord(double yCord) {
        this.yCord = yCord;
    }




    public double getxCord(){
        return xCord;
    }

    public double getyCord(){
        return yCord;
    }






}
