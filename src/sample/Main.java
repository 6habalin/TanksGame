package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.barriers.BrickBarrier;
import sample.barriers.SteelBarier;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class Main extends Application {

    Scene sceneStart, sceneMain;

    @Override
    public void start(Stage primaryStage) throws Exception {
        sceneStart = new Scene(startVBox(primaryStage), 650, 600);
        primaryStage.setTitle("Tanks");
        primaryStage.setScene(sceneStart);
        primaryStage.show();
    }

    VBox startVBox(Stage primaryStage) throws FileNotFoundException {
        VBox start = new VBox(20);
        start.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        start.setSpacing(50);
        Text text = new Text("Tanks");
        text.setFont(Font.font("Verdana", FontWeight.BOLD, 24));
        text.setFill(Color.WHITE);
        InputStream stream = new FileInputStream("src/Images/start.png");
        Image image = new Image(stream);
        ImageView startButton = new ImageView(image);
        startButton.setFitWidth(200);
        startButton.setFitHeight(90);
        start.setAlignment(Pos.CENTER);
        start.getChildren().addAll(text, startButton);
        sceneMain = new Scene(gameVBox(), 650, 600);
        startButton.setOnMouseClicked(e -> primaryStage.setScene(sceneMain));
        return start;
    }

    StackPane gameVBox(){
        StackPane pane = new StackPane();
        GridPane field = new GridPane();
        //field.setGridLinesVisible(true);
        Tank tank = new Tank();
        pane.setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)));
        pane.setAlignment(Pos.CENTER);
        field.setMaxSize(500,500);
        field.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        field.add(tank.getTank(), tank.position.getX(), tank.position.getY());
        field.add(new BrickBarrier().getBrick(), 1, 1);
        pane.getChildren().add(field);
        return pane;
    }


    public static void main(String[] args) {
        launch(args);
        Scanner input = new Scanner(System.in);
        String className = input.nextLine();

        // Checking the implementation of the Position class
        if (className.equals("Position")) {
            Position p1 = new Position(input.nextInt(), input.nextInt());
            System.out.println(p1);
            p1.setX(5);
            System.out.println(p1.getX());

            Position p2 = new Position(5, 10);
            System.out.println(p1.equals(p2));
        }

        // Checking the implementation of the Map class
        else if (className.equals("Map")) {
            try {
                Map map = new Map(input);
                map.print();
                int size = map.getSize();
                System.out.println(size);
                System.out.println(map.getValueAt(size / 2, size / 2));
            } catch (Exception e) {
            }
        }

        // Checking the Player interface and the MyPlayer class
        else if (className.equals("Player")) {
            Player player = new MyPlayer();
            System.out.println(Player.class.isInterface());
            System.out.println(player instanceof Player);
            System.out.println(player instanceof MyPlayer);
        }

        // Checking the InvalidMapException class
        else if (className.equals("Exception")) {
            try {
                throw new InvalidMapException("Some message");
            } catch (InvalidMapException e) {
                System.out.println(e.getMessage());
            }
        }

        // Checking the Game class and all of its components
        else if (className.equals("Game")) {

            // Initialize player, map, and the game
            Player player = new MyPlayer();
            Game game = null;

            try {
                game = new Game(new Map(input));
            } catch (InvalidMapException e) { // custom exception
                System.out.println(e.getMessage());
                System.exit(0);
            }

            game.addPlayer(player);

            // Make the player move based on the commands given in the input
            String moves = input.next();
            char move;
            for (int i = 0; i < moves.length(); i++) {
                move = moves.charAt(i);
                switch (move) {
                    case 'R':
                        player.moveRight();
                        break;
                    case 'L':
                        player.moveLeft();
                        break;
                    case 'U':
                        player.moveUp();
                        break;
                    case 'D':
                        player.moveDown();
                        break;
                }
            }

            // Determine the final position of the player after completing all the moves above
            Position playerPosition = player.getPosition();
            System.out.println("Player final position");
            System.out.println("Row: " + playerPosition.getY());
            System.out.println("Col: " + playerPosition.getX());
        }
    }
}
