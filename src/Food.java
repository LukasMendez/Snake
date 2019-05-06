/**
 * Created by Lukas
 * 29-04-2019.
 */
public class Food {

    private String type;
    private Point food;


    public Point getFood() {
        return food;
    }


    public Food(){
        this.food = randomCoord();
    }

    //
    public Point randomCoord(){
        int xCord = (int)(Math.random() * 650);
        int yCord = (int)(Math.random() * 650);
        int temp = xCord % 10;
        xCord = xCord - temp;
        temp = yCord % 10;
        yCord = yCord - temp;
        Point point = new Point(xCord,yCord);
        return point;
    }




    public void refreshFood(){
        food = randomCoord();
        type = "TODO";
    }


}
