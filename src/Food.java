import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Lukas
 * 29-04-2019.
 */
public class Food {

    private String type = "normal";
    private Point food;
    Timer timer = new Timer();


    public Point getFood() {
        return food;
    }

    public String getType(){
        return type;
    }


    /**
     * Will construct the food object and assign a random coordinate to it
     */
    public Food(){
        this.food = randomCoord();
    }

    /**
     * Will return a random point that is within the range of the stage dimensions
     * @return a point object with x and y coordinates
     */
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

    /**
     * There are three food types in this game. This method randomizes the output food based on probability.
     * Normal food has 40% chance of spawning. Speed and Double Head has 30% chance of spawning.
     *
     */

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

        // Cancel active timer (Else their would be different timers and the food would jump)
        timer.cancel();
        // Creates a new timer after the timer got cancelled. Else their would be an exception error
        timer = new Timer();
        // Calls the food timer, to repeat the timer, after it has been cancelled.
        refreshFoodTimer();



        randomFood();

    }

    public void refreshFoodTimer(){

        //TODO Maybe implement a try catch ? It's not necessary with that what I have done, but if the timer failes to initialize one single time, it will throw an error
        //TODO and the timer wont get initialized afterwords.

        System.out.println("refreshFoodTimer Called");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Calls method to refresh food
                refreshFood();
                // purges the timer cache to clean up stuff (saves memory)
                timer.purge();
                System.out.println("New food has been served! Bon appetit :)");
            }
        }, 8*1000);
    }




}