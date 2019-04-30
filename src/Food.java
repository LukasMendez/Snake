/**
 * Created by Lukas
 * 29-04-2019.
 */
public class Food {

    public Point getFood() {
        return food;
    }

    private Point food;

    public Food(){
        this.food = RandomCoord();
    }

    //
    public Point RandomCoord(){
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
        food = RandomCoord();
    }


}
