package sample;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

import java.io.*;
import java.net.Socket;
import java.util.*;

public class Game {
    private final Map map;
    static Scene sceneMain;
    private final Tank tank;
    final GridPane fieldPane = new GridPane();
    final Barriers barriers = new Barriers();
    private final Bullet bullet;
    private Socket socket;
    private ObjectOutputStream toServer = null;
    private ObjectInputStream fromServer = null;
    private Tank newTank;
    private boolean multiplayer = false;
    private List<Bot> botList = new ArrayList<Bot>();
    private List<BotMovement> botMovementList = new ArrayList<BotMovement>();
    private List<BotBullet> botBulletList = new ArrayList<BotBullet>();
    private static Text text;
    private static Text text2;

    Game(Map map, Tank tank) {
        this.tank = tank;
        this.map = map;
        bullet = new Bullet(map, tank.getTankDirection());
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
        Button multiplayerButton = new Button("Multiplayer");
        multiplayerButton.setMaxHeight(90);
        multiplayerButton.setMaxWidth(200);
        multiplayerButton.setMinHeight(90);
        multiplayerButton.setMinWidth(200);
        start.setAlignment(Pos.CENTER);
        start.getChildren().addAll(text, startButton, multiplayerButton);

        startButton.setOnMouseClicked(e -> {
            try {
                sceneMain = new Scene(gameVBox(1), 650, 600);
            } catch (IOException | ClassNotFoundException ioException) {
                ioException.printStackTrace();
            }
            primaryStage.setScene(sceneMain);
            addPlayerActions();
            addBotsActions();
        });

        multiplayerButton.setOnMouseClicked(e -> {
            try {
                socket = new Socket("localhost", 8000);
                toServer = new ObjectOutputStream(socket.getOutputStream());
                fromServer = new ObjectInputStream(socket.getInputStream());
                multiplayer = true;
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            try {
                sceneMain = new Scene(gameVBox(2), 650, 600);
            } catch (IOException | ClassNotFoundException ioException) {
                ioException.printStackTrace();
            }

            primaryStage.setScene(sceneMain);

            addPlayerActions();
            try {
                multiplayer();
            } catch (ClassNotFoundException | IOException ioException) {
                ioException.printStackTrace();
            }
        });
        return start;
    }

    public BorderPane gameVBox(int n) throws IOException, ClassNotFoundException {
        BorderPane pane = new BorderPane();
        pane.setMaxSize(650, 600);
        pane.setMinSize(650, 600);
        text = new Text("Lives:\n" + tank.getTankLives());
        text.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        text.setTextAlignment(TextAlignment.CENTER);
        fieldPane.setMaxSize(500, 500);
        fieldPane.setMinSize(500, 500);
        pane.setCenter(fieldPane);
        pane.setRight(text);
        fieldPane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        pane.setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)));
        if (n == 1) {
            addElements();
        } else if (n == 2) {
            addElementsMultiplayer();
        }
        return pane;
    }

    public static void setTextLives(int number){
        text.setText("Lives:\n" + number);
    }

    public void addPlayerActions() {
        sceneMain.setOnKeyPressed(e -> {
            Runnable movement = null;
            String action = "";
            switch (e.getCode()) {
                case UP:
                case W:
                    movement = new TankMovement(tank, "up");
                    action = "up";
                    break;
                case DOWN:
                case S:
                    movement = new TankMovement(tank, "down");
                    action = "down";
                    break;
                case LEFT:
                case A:
                    movement = new TankMovement(tank, "left");
                    action = "left";
                    break;
                case RIGHT:
                case D:
                    movement = new TankMovement(tank, "right");
                    action = "right";
                    break;
                case SPACE:
                    if(multiplayer) {
                        bullet.fire(tank, fieldPane);
                    } else {
                        bullet.fire(tank, fieldPane, botList, botMovementList, botBulletList);
                    }
                    action = "fire";
                    System.out.println("Fire!");
                    break;
            }

           if(multiplayer){
               try {
                   toServer.writeObject(action);
               } catch (IOException ioException) {
                   ioException.printStackTrace();
               }
           }
            Thread thread1 = new Thread(movement);
            if (thread1.isAlive()) {
                thread1.interrupt();
            }
            thread1.start();
        });
        sceneMain.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                if(multiplayer) {
                    bullet.fire(tank, fieldPane);
                } else {
                    bullet.fire(tank, fieldPane, botList, botMovementList, botBulletList);
                }
                System.out.println("Fire!");
            }
        });
    }

    public void addBotsActions() {
        Bot bot = new Bot(map);
        bot.setSize(barriers.getSize());
        BotBullet botBullet = new BotBullet(map, bot.getBotDirection(), bot, fieldPane, tank);
        fieldPane.add(bot.getBotTank(), bot.getPosition().getX(), bot.getPosition().getY());
        BotMovement bMovement = new BotMovement(bot, tank);
        bMovement.start();
        botBullet.start();
        botList.add(bot);
        botMovementList.add(bMovement);
        botBulletList.add(botBullet);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Bot newBot = new Bot(map);
                newBot.setSize(barriers.getSize());
                BotBullet botBullet = new BotBullet(map, newBot.getBotDirection(), newBot, fieldPane, tank);
                Platform.runLater(() -> fieldPane.add(newBot.getBotTank(), newBot.getPosition().getX(), newBot.getPosition().getY()));
                BotMovement movement = new BotMovement(newBot, tank);
                movement.start();
                botBullet.start();
                botList.add(bot);
                botMovementList.add(movement);
                botBulletList.add(botBullet);
            }
        }, 20000, 20000);
    }

    public void addElements() {
        barriers.setSize(map.getSize());
        tank.setSize(barriers.getSize());
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
    }

    public void multiplayer() throws IOException, ClassNotFoundException {
        Runnable runnable = () -> {
            while (true) {
                String action = null;
                try {
                    action = (String) fromServer.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                switch (Objects.requireNonNull(action)) {
                    case "up":
                        newTank.moveUp(newTank);
                        break;
                    case "down":
                        newTank.moveDown(newTank);
                        break;
                    case "left":
                        newTank.moveLeft(newTank);
                        break;
                    case "right":
                        newTank.moveRight(newTank);
                        break;
                    case "fire":
                        Platform.runLater(() -> bullet.fireOnline(newTank, fieldPane, tank, toServer));
                        newTank.setTankMap(map);
                        break;
                }
            }
        };
        Thread t = new Thread(runnable);
        t.start();
    }

    public void addPlayer(Map chosenMap) throws IOException, ClassNotFoundException {
        newTank = new Tank(chosenMap);
        newTank.setPosition((Position) fromServer.readObject());
        newTank.setSize(barriers.getSize());
        fieldPane.add(newTank.getTank(), newTank.getTankPosition().getX(), newTank.getTankPosition().getY());
    }

    public void addElementsMultiplayer() throws IOException, ClassNotFoundException {

        toServer.writeObject(tank.getTankPosition());
        toServer.flush();
        Map chosenMap = (Map) fromServer.readObject();
        tank.setPosition((Position) fromServer.readObject());

        barriers.setSize(chosenMap.getSize());
        tank.setSize(barriers.getSize());
        for (int i = 0; i < chosenMap.getSize(); i++) {
            for (int j = 0; j < chosenMap.getSize(); j++) {
                switch (chosenMap.getValueAt(i, j)) {
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
                        fieldPane.add(tank.getTank(), tank.getTankPosition().getX(), tank.getTankPosition().getY());
                        break;
                }
            }
        }
        addPlayer(chosenMap);
    }
}
