package sample.barriers;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class BrickBarrier {
    private final File file = new File("src/Images/brick.png");
    private final Image image = new Image(file.toURI().toString());
    private final ImageView brickView = new ImageView(image);

    public ImageView getBrick(){
        brickView.setFitHeight(64);
        brickView.setFitWidth(64);
        return brickView;
    }
}
