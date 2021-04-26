package sample;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.File;
import java.util.Random;

public class Bot implements Player {
    private final File tankUp = new File("src/sample/Images/botTankUp.png");
    private final File tankDown = new File("src/sample/Images/botTankDown.png");
    private final File tankLeft = new File("src/sample/Images/botTankLeft.png");
    private final File tankRight = new File("src/sample/Images/botTankRight.png");
    private Image image = new Image(tankUp.toURI().toString());
    private final ImageView tankView = new ImageView(image);
    private Position position;
    private int size = 0;
    private Map map;
    private int direction = 1;
    private final Random rand = new Random();
    private TranslateTransition transition;

    Bot(Map map) {
        this.map = map;
        setBotRandPosition();
    }


    public ImageView getBotTank() {
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

    public void setBotRandPosition() {
        int randI = rand.nextInt(map.getSize());
        int randJ = rand.nextInt(map.getSize());
        while (true) {
            if (map.getValueAt(randJ, randI) == '0') {
                position = new Position(randI, randJ);
                break;
            } else {
                randI = rand.nextInt(map.getSize());
                randJ = rand.nextInt(map.getSize());
            }
        }
    }

    public void moveUp(Bot bot) {
        int x = position.getY();
        int y = position.getX();
        bot.setTankImage(tankUp);
        direction = 1;
        if (x - 1 >= 0) {
            switch (map.getValueAt(x - 1, y)) {
                case 'T':
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            bot.getBotTank().toBack();
                        }
                    });
                case '0':
                case 'P':
                    if (map.getValueAt(x - 1, y) != 'T') {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                bot.getBotTank().toFront();
                            }
                        });
                    }
                    transition = new TranslateTransition(Duration.millis(800), bot.getBotTank());
                    transition.setByY((-1) * getSize());
                    transition.play();
                    try {
                        Thread.sleep(790);
                        position.setY(x - 1);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                    break;
            }
        }
    }

    public void moveRight(Bot bot) {
        int x = position.getY();
        int y = position.getX();
        bot.setTankImage(tankRight);
        direction = 2;
        if (y + 1 <= map.getSize() - 1) {
            switch (map.getValueAt(x, y + 1)) {
                case 'T':
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            bot.getBotTank().toBack();
                        }
                    });
                case '0':
                case 'P':
                    if (map.getValueAt(x, y + 1) != 'T') {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                bot.getBotTank().toFront();
                            }
                        });
                    }
                    transition = new TranslateTransition(Duration.millis(800), bot.getBotTank());
                    transition.setByX(bot.getSize());
                    transition.play();
                    try {
                        Thread.sleep(790);
                        position.setX(y + 1);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                    break;
            }
        }
    }

    public void moveDown(Bot bot) {
        int x = position.getY();
        int y = position.getX();
        bot.setTankImage(tankDown);
        direction = 3;
        if (x + 1 <= map.getSize() - 1) {
            switch (map.getValueAt(x + 1, y)) {
                case 'T':
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            bot.getBotTank().toBack();
                        }
                    });
                case '0':
                case 'P':
                    if (map.getValueAt(x + 1, y) != 'T') {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                bot.getBotTank().toFront();
                            }
                        });
                    }
                    transition = new TranslateTransition(Duration.millis(800), bot.getBotTank());
                    transition.setByY(bot.getSize());
                    transition.play();
                    try {
                        Thread.sleep(790);
                        position.setY(x + 1);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                    break;

            }
        }
    }

    public void moveLeft(Bot bot) {
        int x = position.getY();
        int y = position.getX();
        bot.setTankImage(tankLeft);
        direction = 4;
        if (y - 1 >= 0) {
            switch (map.getValueAt(x, y - 1)) {
                case 'T':
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            bot.getBotTank().toBack();
                        }
                    });
                case '0':
                case 'P':
                    if (map.getValueAt(x, y - 1) != 'T') {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                bot.getBotTank().toFront();
                            }
                        });
                    }
                    transition = new TranslateTransition(Duration.millis(800), bot.getBotTank());
                    transition.setByX((-1) * bot.getSize());
                    transition.play();
                    try {
                        Thread.sleep(790);
                        position.setX(y - 1);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                    break;
            }
        }
    }

    @Override
    public void moveRight() {

    }

    @Override
    public void moveLeft() {

    }

    @Override
    public void moveUp() {

    }

    @Override
    public void moveDown() {

    }

    @Override
    public void setMap(Map map) {
        this.map = map;
    }

    private void setTankImage(File file) {
        image = new Image(file.toURI().toString());
        tankView.setImage(image);
    }

    public int getBotDirection() {
        return direction;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    public Map getMap() {
        return map;
    }


}
