import javafx.animation.*;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.sound.midi.Sequence;

import java.sql.Time;

import static java.awt.Color.RED;
import static javafx.scene.input.KeyCode.SHIFT;

/**
 * Created by Lukas
 * 29-04-2019.
 */
public class Main extends Application {

    BorderPane root = new BorderPane();
    Canvas canvas = new Canvas(700, 700);
    Snake snake = new Snake();
    Food food = new Food();
    private GraphicsContext gc;
    private SimpleStringProperty currentDirection = new SimpleStringProperty();

    @Override
    public void start(Stage primaryStage) throws Exception {

        Scene scene = new Scene(root, 700, 700);

        root.setCenter(canvas);

        // Default direction when you start the game
        currentDirection.set("RIGHT");


        drawSnake();

        new AnimationTimer() {
            long lastUpdate = 0;

            public void handle(long now) {
                if (now - lastUpdate >= 50000000) {
                    clearCanvas();
                    drawFood();
                    drawSnake();
                    if(snake.getHead().getxCord() == food.getFood().getxCord() && snake.getHead().getyCord() == food.getFood().getyCord()){
                        snake.addHead(currentDirection);
                        food.refreshFood();
                    }
                    else{
                        snake.addHead(currentDirection);
                        snake.removeTail();
                    }

                    lastUpdate = now;

                    // RIGHT BORDER
                    if (snake.getTail().getxCord()>canvas.getWidth() && currentDirection.get().equals("RIGHT")){

                        System.out.println("Crossed the right border");
                        snake.setLocation(0,snake.getHead().getyCord());

                    // LEFT BORDER
                    } else if (snake.getTail().getxCord()<1 && currentDirection.get().equals("LEFT")){

                        System.out.println("I WAS EXECUTED");
                        snake.setLocation(canvas.getWidth(),snake.getHead().getyCord());

                    }
                    // TOP BORDER




                    // BOTTOM BORDER



                }
            }
        }.start();


        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {

                    case UP:

                        if (currentDirection.get().equals("DOWN")) {
                            break;
                        }

                        currentDirection.set("UP");
                        System.out.println("Pressed up");

                        break;
                    case DOWN:

                        if (currentDirection.get().equals("UP")) {
                            break;
                        }

                        currentDirection.set("DOWN");
                        System.out.println("Pressed down");
                        break;
                    case LEFT:

                        if (currentDirection.get().equals("RIGHT")) {
                            break;
                        }

                        currentDirection.set("LEFT");
                        System.out.println("Pressed left");
                        break;
                    case RIGHT:

                        if (currentDirection.get().equals("LEFT")) {
                            break;
                        }

                        currentDirection.set("RIGHT");
                        System.out.println("Pressed right");
                        break;


                }
            }
        });


        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private void drawSnake() {

        gc = canvas.getGraphicsContext2D();

        for (int i = 0; i < snake.getPoint().size()-1 ; i++) {

            gc.setFill(Color.RED);

            canvas.getGraphicsContext2D().fillOval(snake.getPoint().get(i).getxCord(), snake.getPoint().get(i).getyCord(), 10, 10);

        }


        gc.setFill(Color.ORANGE);

        canvas.getGraphicsContext2D().fillOval(snake.getHead().getxCord(), snake.getHead().getyCord(), 10, 10);



    }

    private void drawFood(){
        gc = canvas.getGraphicsContext2D();
        canvas.getGraphicsContext2D().fillOval(food.getFood().getxCord(), food.getFood().getyCord(), 10, 10);
    }

    private void clearCanvas() {
        gc.clearRect(0, 0, 700, 700);
    }


}

