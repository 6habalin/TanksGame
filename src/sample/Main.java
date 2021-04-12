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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class Main extends Application {

    Scene sceneStart, sceneMain;
    Map map = null;
    Game game = null;

    @Override
    public void start(Stage primaryStage) throws Exception {

        File file = new File("src/map.txt");
        Scanner input = new Scanner(file);
        Player player = new MyPlayer();

        try {
            map = new Map(input);
            game = new Game(map);
        } catch (InvalidMapException e) {
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

    StackPane gameVBox() {
        StackPane pane = new StackPane();
        BarriersPane field = new BarriersPane(map);
        //field.getPane().setGridLinesVisible(true);
        Tank tank = new Tank(1, 1);
        pane.setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)));
        pane.setAlignment(Pos.CENTER);
        pane.getChildren().add(field.getPane());
        return pane;
    }


    public static void main(String[] args) {
        launch(args);
    }

}
