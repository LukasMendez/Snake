/**
 * Created by Lukas
 * 29-04-2019.
 */
public class Food {

    private String type = "normal";
    private Point food;


    public Point getFood() {
        return food;
    }

    public String getType(){
        return type;
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


    public void randomFood(){

        int randomNumber = ((int)(Math.random() * 100));

        if (randomNumber>=0 && randomNumber<=40){

            type = "normal";

        } else if (randomNumber>40 && randomNumber<=70){

            type = "speed";

        } else if (randomNumber>70 && randomNumber<=100){

            type = "double head";

        }


    }




    public void refreshFood(){
        food = randomCoord();
        randomFood();
    }


}
