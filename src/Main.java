import com.sun.jdi.LongValue;
import javafx.animation.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.sound.midi.Sequence;

import java.sql.Time;

import static java.awt.Color.RED;

/**
 * Created by Lukas
 * 29-04-2019.
 */
public class Main extends Application {

    BorderPane root = new BorderPane();
    Canvas canvas = new Canvas(700,700);
    Snake snake = new Snake();
    private GraphicsContext gc;

    @Override
    public void start(Stage primaryStage) throws Exception {

        Scene scene = new Scene(root, 700,700);

        root.setCenter(canvas);

/*
        gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.RED);
        gc.fillRect(300,300,50,50);
*/

        drawSnake();

        new AnimationTimer() {
            long lastUpdate = 0 ;

            public void handle(long now) {
                if (now - lastUpdate >= 50000000) {
                    drawSnake();
                    snake.addPoint();
                    lastUpdate = now ;
                }
            }
        }.start();

        primaryStage.setScene(scene);
        primaryStage.show();


    }

    private void drawSnake(){

        gc = canvas.getGraphicsContext2D();

        for (Point element : snake.getPoint()){

            gc.setFill(Color.RED);

            canvas.getGraphicsContext2D().fillOval(element.getxCord(),element.getyCord(),10,10);



        }






    }







}

