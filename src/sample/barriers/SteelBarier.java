package sample.barriers;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class SteelBarier {
    private final File file = new File("src/Images/steel.png");
    private final Image image = new Image(file.toURI().toString());
    private final ImageView steelView = new ImageView(image);

    public ImageView getSteel(){
        steelView.setFitHeight(64);
        steelView.setFitWidth(64);
        return steelView;
    }
}
