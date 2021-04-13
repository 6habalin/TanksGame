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

    Game(Map map, Tank tank) {
        this.tank = tank;
        this.map = map;
        bullet = new Bullet(map, tank.getTankDirection());
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
        sceneMain.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case UP:
                case W:
                    tank.moveUp(tank);
                    break;
                case DOWN:
                case S:
                    tank.moveDown(tank);
                    break;
                case LEFT:
                case A:
                    tank.moveLeft(tank);
                    break;
                case RIGHT:
                case D:
                    tank.moveRight(tank);
                    break;
                case SPACE:
                    bullet.fire(tank, fieldPane);
                    break;
            }
        });
        sceneMain.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                bullet.fire(tank, fieldPane);
            }
        });
        startButton.setOnMouseClicked(e -> primaryStage.setScene(sceneMain));
        return start;
    }

    StackPane gameVBox() {
        StackPane pane = new StackPane();

        fieldPane.setMaxSize(500, 500);
        fieldPane.setMinSize(500, 500);
        fieldPane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
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
        fieldPane.setGridLinesVisible(true);
        pane.setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)));
        pane.setAlignment(Pos.CENTER);
        pane.getChildren().add(fieldPane);
        return pane;
    }

}
