import javafx.animation.*;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
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

    private BorderPane root = new BorderPane();
    private Canvas canvas = new Canvas(700, 700);
    private HBox hBox = new HBox();

    private IntegerProperty scoreProperty = new SimpleIntegerProperty();


    private Label score = new Label();

    private Snake snake = new Snake();
    private Food food = new Food();
    private GraphicsContext gc = canvas.getGraphicsContext2D();
    private SimpleStringProperty currentDirection = new SimpleStringProperty();
    private boolean gameOver = false;


    @Override
    public void start(Stage primaryStage) throws Exception {

        Scene scene = new Scene(root, 700, 700);
        root.setStyle("-fx-background-color: BLACK");

        root.setCenter(canvas);
        root.setTop(hBox);

        scoreProperty.setValue(0);

        // Label
        score.setTextFill(Color.WHITE);
        score.setText("Score: " + scoreProperty.get());
        score.textProperty().bind(snake.getSnakeSizeProperty().asString());


        // HBox settings
        hBox.setPrefHeight(30);
        hBox.setStyle("-fx-background-color: rgba(47,47,47,0.86)");
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(score);


        // Default direction when you start the game
        currentDirection.set("RIGHT");


        new AnimationTimer() {
            long lastUpdate = 0;

            public void handle(long now) {
                if (now - lastUpdate >= 50000000) {


                    if (!gameOver) {

                        clearCanvas();
                        drawFood();
                        drawSnake();

                        if (snake.getHead().getxCord() == food.getFood().getxCord() && snake.getHead().getyCord() == food.getFood().getyCord()) {
                            snake.addHead(currentDirection);
                            food.refreshFood();
                        } else {
                            snake.addHead(currentDirection);
                            snake.removeTail();
                        }

                        checkIfEatingItself();
                        lastUpdate = now;

                        // RIGHT BORDER
                        if (snake.getTail().getxCord() > canvas.getWidth() && currentDirection.get().equals("RIGHT")) {

                            System.out.println("Crossed the RIGHT border");
                            snake.setLocation(0, snake.getHead().getyCord());

                            // LEFT BORDER
                        } else if (snake.getTail().getxCord() < 1 && currentDirection.get().equals("LEFT")) {

                            System.out.println("Crossed the LEFT border");
                            snake.setLocation(canvas.getWidth(), snake.getHead().getyCord());
                        }
                        // TOP BORDER
                        else if (snake.getTail().getyCord() == 0 && currentDirection.get().equals("UP")) {

                            System.out.println("Crossed the UPPER border");
                            snake.setLocation(snake.getHead().getxCord(), canvas.getHeight());

                            // BOTTOM BORDER
                        } else if (snake.getTail().getyCord() == canvas.getHeight() && currentDirection.get().equals("DOWN")) {
                            System.out.println("Crossed the BOTTOM border");
                            snake.setLocation(snake.getHead().getxCord(), 0);
                        }


                    } else {


                        drawGameOver();
                    }


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
                    case R:

                        restartGame();

                }
            }
        });


        //     drawGameOver();

        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private void drawSnake() {


        for (int i = 0; i < snake.getPoint().size() - 1; i++) {

            gc.setFill(Color.DARKGREEN);

            canvas.getGraphicsContext2D().fillRect(snake.getPoint().get(i).getxCord(), snake.getPoint().get(i).getyCord(), 10, 10);

        }


        gc.setFill(Color.LIMEGREEN);

        canvas.getGraphicsContext2D().fillRect(snake.getHead().getxCord(), snake.getHead().getyCord(), 10, 10);


    }


    private void checkIfEatingItself() {


        for (int i = 0; i < snake.getPoint().size() - 1; i++) {


            if (snake.getHead().getxCord() != 0 && snake.getHead().getxCord() != canvas.getWidth() && snake.getHead().getyCord() != 0 && snake.getHead().getyCord() != canvas.getHeight()) {

                if (snake.getHead().getxCord() == snake.getPoint().get(i).getxCord() && snake.getHead().getyCord() == snake.getPoint().get(i).getyCord()) {

                    System.out.println("Snake was biting itself");
                    gameOver = true;

                }


            }


        }

    }

    private void restartGame() {

        clearCanvas();
        snake = new Snake();
        currentDirection.set("RIGHT");
        gameOver = false;

    }

    private void clearCanvas() {
        gc.clearRect(0, 0, 700, 700);
    }

    private void drawFood() {
        gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.ORANGE);
        canvas.getGraphicsContext2D().fillOval(food.getFood().getxCord(), food.getFood().getyCord(), 10, 10);
    }


    private void drawGameOver() {

        gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.RED);

        Font gameOverText = new Font("Arial Black", 40);

        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(gameOverText);
        gc.fillText("GAME OVER", canvas.getWidth() / 2, 100);

        Font restartText = new Font("Arial Black", 16);
        gc.setFont(restartText);
        gc.fillText("Press 'R' to restart the game", canvas.getWidth() / 2, 120);

    }


}




