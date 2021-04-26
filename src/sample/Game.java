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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

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
    List<Bot> botList;
    BotBullet bot1Bullet;
    BotBullet bot2Bullet;
    BotBullet bot3Bullet;


    Game(Map map, Tank tank) {
        this.tank = tank;
        this.map = map;
        bot1 = new Bot(map);
        bot2 = new Bot(map);
        bot3 = new Bot(map);
        bot1Bullet = new BotBullet(map, bot1.getBotDirection(), bot1, fieldPane, tank);
        bot2Bullet = new BotBullet(map, bot2.getBotDirection(), bot2, fieldPane, tank);
        bot3Bullet = new BotBullet(map, bot3.getBotDirection(), bot3, fieldPane, tank);
        botList = new ArrayList<Bot>();
        botList.add(bot1);
        botList.add(bot2);
        botList.add(bot3);
        bullet = new Bullet(map, tank.getTankDirection(), botList);
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
        addPlayerActions();
        addBotsActions(startButton, primaryStage);
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
        pane.setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)));
        addElements();
        return pane;
    }

    public void addPlayerActions(){
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
                    bullet.fire(tank, fieldPane);
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
                bullet.fire(tank, fieldPane);
                System.out.println("Fire!");
            }
        });
    }

    public void addBotsActions(ImageView startButton, Stage primaryStage){
        startButton.setOnMouseClicked(e -> {
            primaryStage.setScene(sceneMain);
            Runnable runnable1 = new BotMovement(bot1, tank);
            Thread threadBot1 = new Thread(runnable1);
            threadBot1.start();

            Runnable runnable2 = new BotMovement(bot2, tank);
            Thread threadBot2 = new Thread(runnable2);
            threadBot2.start();

            Runnable runnable3 = new BotMovement(bot3, tank);
            Thread threadBot3 = new Thread(runnable3);
            threadBot3.start();

            bot1Bullet.start();
            bot2Bullet.start();
            bot3Bullet.start();
        });
    }

    public void addElements(){
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
        fieldPane.add(bot1.getBotTank(), bot1.getPosition().getX(), bot1.getPosition().getY());
        fieldPane.add(bot2.getBotTank(), bot2.getPosition().getX(), bot2.getPosition().getY());
        fieldPane.add(bot3.getBotTank(), bot3.getPosition().getX(), bot3.getPosition().getY());
    }

}
