package sample;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class Tank extends MyPlayer{
    private final File file = new File("src/Images/tankDown.png");
    private final Image image = new Image(file.toURI().toString());
    private final ImageView tankView = new ImageView(image);
    Position position = new Position(0,0);

    public ImageView getTank(){
        tankView.setFitHeight(64);
        tankView.setFitWidth(64);
        return tankView;
    }

}
