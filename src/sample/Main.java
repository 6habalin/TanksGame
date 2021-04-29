package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public Scene sceneStart;
    private static String arguments;
    Tank tank;
    Player player = new MyPlayer();
    Game game = null;

    @Override
    public void start(Stage primaryStage) throws IOException, InvalidMapException {
        Map map = new Map(arguments);
        player.setMap(map);
        tank = new Tank(map);
        game = new Game(map, tank);
        sceneStart = new Scene(game.startVBox(primaryStage), 650, 600);
        primaryStage.setTitle("Tanks");
        primaryStage.setScene(sceneStart);
        primaryStage.show();
    }


    public static void main(String[] args) {
        arguments = args[0];
        launch(args);
    }

}
