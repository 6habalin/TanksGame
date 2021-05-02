package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class Barriers {         //barriers class for displaying them on pane
    private File file = null;
    private Image image = null;
    private ImageView imageView = null;
    private int size = 0;

    public ImageView getBrick() {
        file = new File("src/sample/resources/brick.png");
        image = new Image(file.toURI().toString());
        imageView = new ImageView(image);
        imageView.setFitHeight(size);
        imageView.setFitWidth(size);
        return imageView;
    }

    public ImageView getSteel() {
        file = new File("src/sample/resources/steel.png");
        image = new Image(file.toURI().toString());
        imageView = new ImageView(image);
        imageView.setFitHeight(size);
        imageView.setFitWidth(size);
        return imageView;
    }

    public ImageView getTrees() {
        file = new File("src/sample/resources/trees.png");
        image = new Image(file.toURI().toString());
        imageView = new ImageView(image);
        imageView.setFitHeight(size);
        imageView.setFitWidth(size);
        imageView.toFront();
        return imageView;
    }

    public ImageView getWater() {
        file = new File("src/sample/resources/water.png");
        image = new Image(file.toURI().toString());
        imageView = new ImageView(image);
        imageView.setFitHeight(size);
        imageView.setFitWidth(size);
        return imageView;
    }

    public ImageView getTransparent() {
        file = new File("src/sample/resources/water.png");
        image = new Image(file.toURI().toString());
        imageView = new ImageView(image);
        imageView.setOpacity(0);
        imageView.setFitHeight(size);
        imageView.setFitWidth(size);
        return imageView;
    }

    public ImageView getBlack(int size) {
        file = new File("src/sample/resources/black.png");
        image = new Image(file.toURI().toString());
        imageView = new ImageView(image);
        imageView.setFitHeight(size);
        imageView.setFitWidth(size);
        imageView.toBack();
        return imageView;
    }

    public void setSize(int sizeOfMap) {
        size = 500 / sizeOfMap;
    }

    public int getSize() {
        return size;
    }


}

