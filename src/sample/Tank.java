package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class Tank extends MyPlayer {
    private final File file = new File("src/Images/tankUp.png");
    private final Image image = new Image(file.toURI().toString());
    private final ImageView tankView = new ImageView(image);
    Position position;
    private int size = 0;

    Tank(int x, int y) {
        position = new Position(x, y);
    }

    Tank() {}

    public ImageView getTank() {
        tankView.setFitHeight(size);
        tankView.setFitWidth(size);
        return tankView;
    }

    public void setPosition(int x, int y) {
        position.setX(x);
        position.setY(y);
    }

    public void setSize(int x){
        size = x;
    }

}
