package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Game {
    private final Map map;
    static Scene sceneMain;
    private final Tank tank;
    final GridPane fieldPane = new GridPane();
    final Barriers barriers = new Barriers();
    Bullet bullet;
    Bot bot1;
    Bot bot2;
    Bot bot3;
    Bot[] botArray;

    Game(Map map, Tank tank) {
        this.tank = tank;
        this.map = map;
        bullet = new Bullet(map, tank.getTankDirection());
        bot1 = new Bot(map);
        bot2 = new Bot(map);
        bot3 = new Bot(map);
        botArray = new Bot[]{bot1, bot2, bot3};
    }

    public VBox startVBox(Stage primaryStage) throws FileNotFoundException {
        VBox start = new VBox(20);
        start.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        start.setSpacing(50);
        Text text = new Text("Tanks");
        text.setFont(Font.font("Snell Roundhand", FontWeight.BOLD, 104));
        text.setStroke(Color.DARKRED);
        text.setFill(Color.WHITE);
        InputStream stream = new FileInputStream("src/sample/Images/start.png");
        Image image = new Image(stream);
        ImageView startButton = new ImageView(image);
        startButton.setFitWidth(200);
        startButton.setFitHeight(90);
        start.setAlignment(Pos.CENTER);
        start.getChildren().addAll(text, startButton);
        sceneMain = new Scene(gameVBox(), 650, 600);
        startButton.setOnMouseClicked(e -> {
            primaryStage.setScene(sceneMain);
            Runnable runnable1 = new BotMovement(bot1);
            Thread threadBot1 = new Thread(runnable1);
            threadBot1.start();

            Runnable runnable2 = new BotMovement(bot2);
            Thread threadBot2 = new Thread(runnable2);
            threadBot2.start();

            Runnable runnable3 = new BotMovement(bot3);
            Thread threadBot3 = new Thread(runnable3);
            threadBot3.start();
        });

        sceneMain.setOnKeyPressed(e -> {
            Runnable movement = null;
            switch (e.getCode()) {
                case UP:
                case W:
                    movement = new TankMovement(tank, "up");
                    break;
                case DOWN:
                case S:
                    movement = new TankMovement(tank, "down");
                    break;
                case LEFT:
                case A:
                    movement = new TankMovement(tank, "left");
                    break;
                case RIGHT:
                case D:
                    movement = new TankMovement(tank, "right");
                    break;
                case SPACE:
                    bullet.fire(tank, fieldPane, botArray);
                    System.out.println("Fire!");
                    break;
            }
            Thread thread1 = new Thread(movement);
            if (thread1.isAlive()) {
                thread1.interrupt();
            }
            thread1.start();
        });
        sceneMain.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                bullet.fire(tank, fieldPane, botArray);
                System.out.println("Fire!");
            }
        });


        return start;
    }

    public BorderPane gameVBox() {
        BorderPane pane = new BorderPane();
        pane.setMaxSize(650, 600);
        pane.setMinSize(650, 600);
        Text text = new Text("Lives:\n" + tank.getTankLives());
        text.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        text.setTextAlignment(TextAlignment.CENTER);
        fieldPane.setMaxSize(500, 500);
        fieldPane.setMinSize(500, 500);
        pane.setCenter(fieldPane);
        pane.setRight(text);
        fieldPane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        barriers.setSize(map.getSize());
        tank.setSize(barriers.getSize());
        bot1.setSize(barriers.getSize());
        bot2.setSize(barriers.getSize());
        bot3.setSize(barriers.getSize());
        for (int i = 0; i < map.getSize(); i++) {
            for (int j = 0; j < map.getSize(); j++) {
                switch (map.getValueAt(i, j)) {
                    case 'S':
                        fieldPane.add(barriers.getSteel(), j, i);
                        break;
                    case 'B':
                        fieldPane.add(barriers.getBrick(), j, i);
                        break;
                    case 'W':
                        fieldPane.add(barriers.getWater(), j, i);
                        break;
                    case 'T':
                        fieldPane.add(barriers.getTrees(), j, i);
                        break;
                    case '0':
                        fieldPane.add(barriers.getTransparent(), j, i);
                        break;
                    case 'P':
                        fieldPane.add(tank.getTank(), j, i);
                        break;
                }
            }
        }
        fieldPane.add(bot1.getBotTank(), bot1.getBotPosition().getX(), bot1.getBotPosition().getY());
        fieldPane.add(bot2.getBotTank(), bot2.getBotPosition().getX(), bot2.getBotPosition().getY());
        fieldPane.add(bot3.getBotTank(), bot3.getBotPosition().getX(), bot3.getBotPosition().getY());
        pane.setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)));
        return pane;
    }

}
