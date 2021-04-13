package sample;

import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.File;

public class Tank extends MyPlayer {
    private final File tankUp = new File("src/Images/tankUp.png");
    private final File tankDown = new File("src/Images/tankDown.png");
    private final File tankLeft = new File("src/Images/tankLeft.png");
    private final File tankRight = new File("src/Images/tankRight.png");
    private Image image = new Image(tankUp.toURI().toString());
    private final ImageView tankView = new ImageView(image);
    private Position position;
    private int size = 0;
    final Map map;
    private int direction = 1;

    Tank(Map map) {
        this.map = map;
        for (int i = 0; i < map.getSize(); i++) {
            for (int j = 0; j < map.getSize(); j++) {
                if (map.getValueAt(i, j) == 'P') {
                    position = new Position(j, i);
                }
            }
        }
    }

    public ImageView getTank() {    // return graphical tank
        tankView.setFitHeight(size);
        tankView.setFitWidth(size);
        return tankView;
    }

    public void setSize(int x) {
        size = x;
    }

    public int getSize() {
        return size;
    }

    public void moveRight(Tank tank) {
        int x = position.getY();
        int y = position.getX();
        tank.setTankImage(tankRight);
        direction = 2;
        if (y + 1 <= map.getSize() - 1) {
            switch (map.getValueAt(x, y + 1)) {
                case 'T':
                case '0':
                case 'P':
                    TranslateTransition transition = new TranslateTransition(Duration.millis(100), tank.getTank());
                    transition.setByX(tank.getSize());
                    transition.play();
                    try {
                        Thread.sleep(90);
                        position.setX(y + 1);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                    break;
            }
        }
    }

    public void moveUp(Tank tank) {
        int x = position.getY();
        int y = position.getX();
        tank.setTankImage(tankUp);
        direction = 1;
        if (x - 1 >= 0) {
            switch (map.getValueAt(x - 1, y)) {
                case 'T':
                case '0':
                case 'P':
                    TranslateTransition transition = new TranslateTransition(Duration.millis(100), tank.getTank());
                    transition.setByY((-1) * tank.getSize());
                    transition.play();
                    try {
                        Thread.sleep(90);
                        position.setY(x - 1);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                    break;
            }
        }
    }

    public void moveDown(Tank tank) {
        int x = position.getY();
        int y = position.getX();
        tank.setTankImage(tankDown);
        direction = 3;
        if (x + 1 <= map.getSize() - 1) {
            switch (map.getValueAt(x + 1, y)) {
                case 'T':
                case '0':
                case 'P':
                    TranslateTransition transition = new TranslateTransition(Duration.millis(100), tank.getTank());
                    transition.setByY(tank.getSize());
                    transition.play();
                    try {
                        Thread.sleep(90);
                        position.setY(x + 1);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                    break;
            }
        }
    }

    public void moveLeft(Tank tank) {
        int x = position.getY();
        int y = position.getX();
        tank.setTankImage(tankLeft);
        direction = 4;
        if (y - 1 >= 0) {
            switch (map.getValueAt(x, y - 1)) {
                case 'T':
                case '0':
                case 'P':
                    TranslateTransition transition = new TranslateTransition(Duration.millis(100), tank.getTank());
                    transition.setByX((-1) * tank.getSize());
                    transition.play();
                    try {
                        Thread.sleep(90);
                        position.setX(y - 1);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                    break;
            }
        }
    }

    private void setTankImage(File file) {
        image = new Image(file.toURI().toString());
        tankView.setImage(image);
    }

    public int getTankDirection(){
        return direction;
    }

    public Position getTankPosition(){
        return position;
    }
}
