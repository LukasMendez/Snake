import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import static java.awt.Color.RED;

/**
 * Created by Lukas
 * 29-04-2019.
 */
public class Main extends Application {

    BorderPane root = new BorderPane();
    Canvas canvas = new Canvas();
    Snake snake = new Snake();
    private GraphicsContext gc;

    @Override
    public void start(Stage primaryStage) throws Exception {

        Scene scene = new Scene(root, 700,700);

        root.setCenter(canvas);


        gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.RED);
        gc.fillRect(300,300,50,50);


        drawSnake();



        primaryStage.setScene(scene);
        primaryStage.show();


    }

    private void drawSnake(){

        gc = canvas.getGraphicsContext2D();

        for (Point element : snake.getPoint()){

            gc.setFill(Color.RED);

            canvas.getGraphicsContext2D().fillOval(element.getxCord(),element.getyCord(),3,3);



        }






    }







}
