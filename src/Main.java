
import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Created by Lukas
 * 29-04-2019.
 */
public class Main extends Application {

    private BorderPane root = new BorderPane();
    private Canvas canvas = new Canvas(700, 700);
    private HBox hBox = new HBox();

    private IntegerProperty scoreProperty = new SimpleIntegerProperty();


    private Label lengthOfSnakeLabel = new Label("Length of snake: ");
    private Label lengthOfSnake = new Label();

    private Label scoreLabel = new Label("Score: ");
    private Label score = new Label();


    private Snake snake = new Snake();
    private Food food = new Food();
    private GraphicsContext gc = canvas.getGraphicsContext2D();
    private SimpleStringProperty currentDirection = new SimpleStringProperty();

    private int speedInMiliSecs = 50000000;
    int foodTolerance = 0;


    // USED IN CASE THE CURRENT DIRECTION ISN'T ALLOWED
    private SimpleStringProperty previousDirection = new SimpleStringProperty();

    private boolean gameOver = false;
    private int insaneCounter = 0;

    // Background music
    MediaPlayer musicplayer;


    @Override
    public void start(Stage primaryStage) throws Exception {

        Scene scene = new Scene(root, 700, 700);
        root.setStyle("-fx-background-color: BLACK");

        root.setCenter(canvas);
        root.setTop(hBox);



        // Label
        lengthOfSnake.setTextFill(Color.WHITE);
        lengthOfSnakeLabel.setTextFill(Color.WHITE);

        lengthOfSnake.textProperty().bind(snake.getSnakeSizeProperty().asString());

        scoreLabel.setTextFill(Color.WHITE);
        score.setTextFill(Color.WHITE);


        scoreProperty.setValue(0);
        scoreLabel.setText("   Score: ");
        score.textProperty().bind(scoreProperty.asString());


        // This calls the timer method found in the food class
        // This will be called a single time to start the timer process/thread
        // If you dont want the timer to run by default / startup, you can out comment this one here.
        // Then the timer will first get initialized after the player ate his first fruit...
        System.out.println("Timer initialized...");
        food.refreshFoodTimer();


        // HBox settings
        hBox.setPrefHeight(30);
        hBox.setStyle("-fx-background-color: rgba(47,47,47,0.86)");
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(lengthOfSnakeLabel, lengthOfSnake, scoreLabel, score);


        // Default direction when you start the game
        previousDirection.set("RIGHT");
        currentDirection.set("RIGHT");


        // Background music
        playBackgroundMelody("Media/backgroundmusic.mp3");

        // Create the event handler
        scene.setOnKeyPressed(Main.this::handleKeys);


        new AnimationTimer() {
            long lastUpdate = 0;

            public void handle(long now) {
                if (now - lastUpdate >= speedInMiliSecs) {


                    if (!gameOver) {

                        clearCanvas();
                        drawFood();
                        drawSnake();

                        if (snake.getHead().getxCord() <= (food.getFood().getxCord() + foodTolerance) &&
                                snake.getHead().getxCord() >= (food.getFood().getxCord() - foodTolerance) &&
                                snake.getHead().getyCord() <= (food.getFood().getyCord() + foodTolerance) &&
                                snake.getHead().getyCord() >= (food.getFood().getyCord() - foodTolerance)) {
                            scoreProperty.setValue(scoreProperty.get() + 200);
                            snake.addHead(currentDirection);

                            eatSound();

                            speedInMiliSecs = 50000000;
                            foodTolerance = 0;


                            if (food.getType().equals("speed")) {

                                eatSound();

                                // Will increase the speed
                                speedInMiliSecs = 300000;
                            } else if (food.getType().equals("double head")) {

                                eatSound();

                                foodTolerance = 10;
                            }

                            food.refreshFood();

                            handleInsaneMode();

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


        // WILL MAKE SURE TO KILL ALL THREADS WHEN CLOSING THE WINDOW
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                System.out.println("Stage is closing");
                System.exit(0);
            }
        });


        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private void drawSnake() {


        for (int i = 0; i < snake.getPoint().size() - 1; i++) {

            gc.setFill(Color.DARKGREEN);

            canvas.getGraphicsContext2D().fillRect(snake.getPoint().get(i).getxCord(), snake.getPoint().get(i).getyCord(), 10, 10);

        }

        gc.setFill(Color.LIMEGREEN);

        canvas.getGraphicsContext2D().fillRect(
                snake.getHead().getxCord() - foodTolerance,
                snake.getHead().getyCord() - foodTolerance,
                10 + (foodTolerance * 2),
                10 + (foodTolerance * 2));
    }

    /**
     * Will check if the head touches any of the points from its body
     */

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
        speedInMiliSecs = 50000000;
        foodTolerance = 0;
        insaneCounter=0;
        gameOver = false;
        lengthOfSnake.textProperty().bind(snake.getSnakeSizeProperty().asString());
        scoreProperty.set(0);
        score.textProperty().bind(scoreProperty.asString());
        playBackgroundMelody("Media/backgroundmusic.mp3");
        soundPlayAble=1;


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

        playGameOverSound();


    }


    private void clearCanvas() {
        gc.clearRect(0, 0, 700, 700);
    }


    private void drawFood() {

        String type = food.getType();

        if (type.equals("normal")) {

            gc = canvas.getGraphicsContext2D();

            gc.setFill(Color.ORANGE);
            canvas.getGraphicsContext2D().fillOval(food.getFood().getxCord(), food.getFood().getyCord(), 10, 10);


        } else if (type.equals("speed")) {

            gc = canvas.getGraphicsContext2D();

            gc.setFill(Color.BLUE);
            canvas.getGraphicsContext2D().fillOval(food.getFood().getxCord(), food.getFood().getyCord(), 10, 10);

        } else if (type.equals("double head")) {

            gc = canvas.getGraphicsContext2D();

            gc.setFill(Color.PURPLE);
            canvas.getGraphicsContext2D().fillOval(food.getFood().getxCord(), food.getFood().getyCord(), 10, 10);

        }


    }



    public void handleKeys(KeyEvent event) {

        previousDirection.set(currentDirection.get());

        switch (event.getCode()) {

            case UP:

                if (currentDirection.get().equals("DOWN")) {
                    break;
                }

                if (snake.getHead().getxCord()!=snake.getNeck().getxCord()){
                    currentDirection.set("UP");
                    System.out.println("Pressed up");

                }

                break;
            case DOWN:

                if (currentDirection.get().equals("UP")) {
                    break;
                }


                if (snake.getHead().getxCord()!=snake.getNeck().getxCord()){
                    currentDirection.set("DOWN");
                    System.out.println("Pressed down");

                }


                break;
            case LEFT:

                if (currentDirection.get().equals("RIGHT")) {
                    break;
                }

                if (snake.getHead().getyCord()!=snake.getNeck().getyCord()){

                    currentDirection.set("LEFT");
                    System.out.println("Pressed left");
                }


                break;
            case RIGHT:

                if (currentDirection.get().equals("LEFT")) {
                    break;
                }


                if (snake.getHead().getyCord()!=snake.getNeck().getyCord()){

                    currentDirection.set("RIGHT");
                    System.out.println("Pressed right");

                }

                break;

            case R:

                restartGame();

        }


    }


    public void handleInsaneMode() {
        Canvas overlay = new Canvas(canvas.getWidth(), canvas.getHeight());
        GraphicsContext overlayGc = overlay.getGraphicsContext2D();
        root.getChildren().add(overlay);
        Font insaneFont = new Font("Arial Black", 50);
        Font insaneBigFont = new Font("Arial Black", 60);
        Color[] colorArray = {Color.RED, Color.LIMEGREEN, Color.ALICEBLUE, Color.ANTIQUEWHITE};
        if (insaneCounter < 5) {
            insaneCounter += 1;
        } else if (insaneCounter == 5) {

            musicplayer.stop();
            playBackgroundMelody("Media/mario.mp3");

            root.setStyle("-fx-background-color: #2e0044");
            insaneRotation(90);
            new AnimationTimer() {
                int lastUpdate = 0;

                @Override
                public void handle(long now) {
                    if (now - lastUpdate >= 50000000 && insaneCounter == 6 && !gameOver) {
                        int random = (int) (Math.random() * 4);
                        overlayGc.setFill(colorArray[random]);
                        overlayGc.setTextAlign(TextAlignment.CENTER);
                        random = (int) (Math.random() * 10);
                        if (random > 8) {
                            overlayGc.setFont(insaneBigFont);
                        } else {
                            overlayGc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                            overlayGc.setFont(insaneFont);
                        }
                        overlayGc.fillText("INSANE MODE!", canvas.getWidth() / 2, canvas.getHeight() / 2);
                    } else {
                        overlayGc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                        insaneRotation(-90);

                        musicplayer.stop();
                        playBackgroundMelody("Media/backgroundmusic.mp3");
                        root.setStyle("-fx-background-color: BLACK");
                        stop();
                    }
                }
            }.start();
            insaneCounter += 1;
        } else if (insaneCounter == 6) {
            insaneCounter = 0;
        }
        System.out.println("insane: " + insaneCounter);
    }

    public void insaneRotation(int angle) {
        Rotate rotate = new Rotate(angle);
        rotate.setPivotX(350);
        rotate.setPivotY(350);
        canvas.getTransforms().add(rotate);
    }




    // SOUND EFFECTS


    public void playBackgroundMelody(String path){

        if (musicplayer!=null){

            musicplayer.stop();
        }

        Media mp3MusicFile = new Media(getClass().getResource(path).toExternalForm());

        musicplayer = new MediaPlayer(mp3MusicFile);
        musicplayer.setAutoPlay(true);
        musicplayer.setCycleCount(MediaPlayer.INDEFINITE);


    }

    // USED TO PREVENT THE ANIMATION TIMER FROM RESTARTING THE SONG OVER AND OVER
    private int soundPlayAble = 1;

    private void playGameOverSound(){



        if (soundPlayAble==1){

            playBackgroundMelody("Media/gameover.mp3");
            musicplayer.setCycleCount(1);
            soundPlayAble=0;
        }


    }



    private void eatSound(){

        Media soundEffect = new Media(getClass().getResource("Media/eatingSound.mp3").toExternalForm());

        MediaPlayer mediaPlayerShort = new MediaPlayer(soundEffect);
        mediaPlayerShort.setAutoPlay(true);
        mediaPlayerShort.setCycleCount(1);



    }












}


