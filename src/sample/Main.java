package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.util.Scanner;

public class Main extends Application {

    public Scene sceneStart;

    @Override
    public void start(Stage primaryStage) throws Exception {

        File file = new File("src/map.txt");
        Scanner input = new Scanner(file);
        Tank tank;
        Player player = new MyPlayer();
        Game game = null;

        try {
            Map map = new Map(input);
            player.setMap(map);
            tank = new Tank(map);
            game = new Game(map, tank);
        } catch (InvalidMapException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }

        sceneStart = new Scene(game.startVBox(primaryStage), 650, 600);
        primaryStage.setTitle("Tanks");
        primaryStage.setScene(sceneStart);
        primaryStage.show();
    }




    public static void main(String[] args) {
        launch(args);
    }

}
