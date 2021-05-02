package sample;

import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import javax.swing.*;
import java.io.File;
import java.util.Random;

public class BotBullet implements Runnable {
    private int x = 0;
    private int y = 0;
    private int direction;
    private final Map map;
    private int[][] barriers;
    private final File bullet = new File("src/sample/Images/bullet.png");
    private final Image image = new Image(bullet.toURI().toString());
    private final ImageView bulletView = new ImageView(image);
    private final Bot bot;
    private final GridPane fieldPane;
    private Tank tank;
    Thread t = null;
    private boolean condition = false;


    BotBullet(Map map, int direction, Bot bot, GridPane fieldPane, Tank tank) {
        this.map = map;
        this.direction = direction;
        this.bot = bot;
        this.fieldPane = fieldPane;
        this.tank = tank;
        barriers = new int[map.getSize()][map.getSize()];
        for (int i = 0; i < map.getSize(); i++) {
            for (int j = 0; j < map.getSize(); j++) {
                switch (map.getValueAt(i, j)) {
                    case 'S':
                        barriers[i][j] = 9;
                        break;
                    case 'B':
                        barriers[i][j] = 4;
                        break;
                    default:
                        barriers[i][j] = 0;
                        break;
                }
            }
        }
    }

    public void fireUp(Bot bot, GridPane fieldPane, Tank tank) {
        if (y > 0 && barriers[y - 1][x] != 9) {
            int counter = 1;
            BotBullet bullet = new BotBullet(map, bot.getBotDirection(), bot, fieldPane, tank);
            Platform.runLater(() -> fieldPane.add(bullet.getBulletView(bot), bot.getPosition().getX(), bot.getPosition().getY()));
            TranslateTransition transition = new TranslateTransition(Duration.millis(100), bullet.getBulletView(bot));
            PauseTransition pause = new PauseTransition(Duration.millis(100));
            int i = y - 1;
            while (i >= 0) {
                if (tank.getTankPosition().getX() == x && tank.getTankPosition().getY() == i) {
                    respawnTank();
                    break;
                }
                if (barriers[i][x] == 0) {
                    counter++;
                    i--;
                } else if (barriers[i][x] == 9) {
                    break;
                } else if (barriers[i][x] == 1) {
                    if (map.getValueAt(i, x) != '0') {
                        map.setElement('0', i, x);
                        ImageView v = new Barriers().getBlack(bot.getSize());
                        int finalI = i;
                        Platform.runLater(() -> {
                            fieldPane.add(v, x, finalI);
                            bot.getBotTank().toFront();
                        });
                    }
                    barriers[i][x]--;
                    break;
                } else {
                    barriers[i][x]--;
                    break;
                }
            }
            if (counter > 1) {
                counter--;
            }
            transition.setByY((-1) * counter * bot.getSize() - 25);
            transition.play();
            pause.setOnFinished(e -> bullet.getBulletView(bot).setVisible(false));
            pause.play();
        }
    }

    public void fireDown(Bot bot, GridPane fieldPane, Tank tank) {
        if (y < barriers.length - 1 && barriers[y + 1][x] != 9) {
            int counter = 1;
            BotBullet bullet = new BotBullet(map, bot.getBotDirection(), bot, fieldPane, tank);
            Platform.runLater(() -> fieldPane.add(bullet.getBulletView(bot), bot.getPosition().getX(), bot.getPosition().getY()));
            TranslateTransition transition = new TranslateTransition(Duration.millis(100), bullet.getBulletView(bot));
            PauseTransition pause = new PauseTransition(Duration.millis(100));
            int i = y + 1;
            while (i < barriers.length) {
                if (tank.getTankPosition().getX() == x && tank.getTankPosition().getY() == i) {
                    respawnTank();
                    break;
                }
                if (barriers[i][x] == 0) {
                    counter++;
                    i++;
                } else if (barriers[i][x] == 9) {
                    break;
                } else if (barriers[i][x] == 1) {
                    if (map.getValueAt(i, x) != '0') {
                        map.setElement('0', i, x);
                        ImageView v = new Barriers().getBlack(bot.getSize());
                        int finalI = i;
                        Platform.runLater(() -> {
                            fieldPane.add(v, x, finalI);
                            bot.getBotTank().toFront();
                        });
                    }
                    barriers[i][x]--;
                    break;
                } else {
                    barriers[i][x]--;
                    break;
                }
            }
            if (counter > 1) {
                counter--;
            }
            transition.setByY(counter * bot.getSize() + 25);
            transition.play();
            pause.setOnFinished(e -> bullet.getBulletView(bot).setVisible(false));
            pause.play();
        }
    }

    public void fireLeft(Bot bot, GridPane fieldPane, Tank tank) {
        if (x > 0 && barriers[y][x - 1] != 9) {
            int counter = 1;
            BotBullet bullet = new BotBullet(map, bot.getBotDirection(), bot, fieldPane, tank);
            Platform.runLater(() -> fieldPane.add(bullet.getBulletView(bot), bot.getPosition().getX(), bot.getPosition().getY()));
            TranslateTransition transition = new TranslateTransition(Duration.millis(100), bullet.getBulletView(bot));
            PauseTransition pause = new PauseTransition(Duration.millis(100));
            int i = x - 1;
            while (i >= 0) {
                if (tank.getTankPosition().getX() == i && tank.getTankPosition().getY() == y) {
                    respawnTank();
                    break;
                }
                if (barriers[y][i] == 0) {
                    counter++;
                    i--;
                } else if (barriers[y][i] == 9) {
                    break;
                } else if (barriers[y][i] == 1) {
                    if (map.getValueAt(y, i) != '0') {
                        map.setElement('0', y, i);
                        ImageView v = new Barriers().getBlack(bot.getSize());
                        int finalI = i;
                        Platform.runLater(() -> {
                            fieldPane.add(v, finalI, y);
                            bot.getBotTank().toFront();
                        });
                    }
                    barriers[y][i]--;
                    break;
                } else {
                    barriers[y][i]--;
                    break;
                }
            }
            if (counter > 1) {
                counter--;
            }
            transition.setByX((-1) * counter * bot.getSize() - 25);
            transition.play();
            pause.setOnFinished(e -> bullet.getBulletView(bot).setVisible(false));
            pause.play();
        }
    }

    public void fireRight(Bot bot, GridPane fieldPane, Tank tank) {
        if (x < barriers.length - 1 && barriers[y][x + 1] != 9) {
            int counter = 1;
            BotBullet bullet = new BotBullet(map, bot.getBotDirection(), bot, fieldPane, tank);
            Platform.runLater(() -> fieldPane.add(bullet.getBulletView(bot), bot.getPosition().getX(), bot.getPosition().getY()));
            TranslateTransition transition = new TranslateTransition(Duration.millis(100), bullet.getBulletView(bot));
            PauseTransition pause = new PauseTransition(Duration.millis(100));
            int i = x + 1;
            while (i < barriers.length) {
                if (tank.getTankPosition().getX() == i && tank.getTankPosition().getY() == y) {
                    respawnTank();
                    break;
                }
                if (barriers[y][i] == 0) {
                    counter++;
                    i++;
                } else if (barriers[y][i] == 9) {
                    break;
                } else if (barriers[y][i] == 1) {
                    if (map.getValueAt(y, i) != '0') {
                        map.setElement('0', y, i);
                        ImageView v = new Barriers().getBlack(bot.getSize());
                        int finalI = i;
                        Platform.runLater(() -> {
                            fieldPane.add(v, finalI, y);
                            bot.getBotTank().toFront();
                        });
                    }
                    barriers[y][i]--;
                    break;
                } else {
                    barriers[y][i]--;
                    break;
                }
            }
            if (counter > 1) {
                counter--;
            }
            transition.setByX(counter * bot.getSize() + 25);
            transition.play();
            pause.setOnFinished(e -> bullet.getBulletView(bot).setVisible(false));
            pause.play();
        }
    }

    public ImageView getBulletView(Bot tank) {
        bulletView.setFitHeight(tank.getSize() - Math.round((tank.getSize() * 10.0) / 100));
        bulletView.setFitWidth(tank.getSize() - Math.round((tank.getSize() * 10.0) / 100));
        return bulletView;
    }

    @Override
    public void run() {

        Random rand = new Random();
        boolean shoot = rand.nextBoolean();
        bulletView.setFitHeight(bot.getSize() - Math.round((bot.getSize() * 10.0) / 100));
        bulletView.setFitWidth(bot.getSize() - Math.round((bot.getSize() * 10.0) / 100));
        while (!condition) {
            if (shoot) {
                direction = bot.getBotDirection();
                x = bot.getPosition().getX();
                y = bot.getPosition().getY();
                if (direction == 1) {
                    if (!Thread.currentThread().isInterrupted()) {
                        fireUp(bot, fieldPane, tank);
                    } else {
                        break;
                    }
                } else if (direction == 2) {
                    if (!Thread.currentThread().isInterrupted()) {
                        fireRight(bot, fieldPane, tank);
                    } else {
                        break;
                    }
                } else if (direction == 3) {
                    if (!Thread.currentThread().isInterrupted()) {
                        fireDown(bot, fieldPane, tank);
                    } else {
                        break;
                    }
                } else if (direction == 4) {
                    if (!Thread.currentThread().isInterrupted()) {
                        fireLeft(bot, fieldPane, tank);
                    } else {
                        break;
                    }
                }
            }
            if (!Thread.currentThread().isInterrupted()) {
                shoot = rand.nextBoolean();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }

    }

    public void start() {
        if (t == null) {
            t = new Thread(this);
            t.start();
        }
    }

    public void stop() {
        if (t != null) {
            t.interrupt();
        }
    }

    public void stopBullet() {
        condition = true;
        t.interrupt();
    }

    public void respawnTank(){
        tank.tankMinusOneLive();
        Game.setTextLives(tank.getTankLives());
        if(tank.getTankLives() == 0){
            System.out.println("Your tank is destroyed");
            Platform.runLater(() -> {
                fieldPane.getChildren().remove(tank.getTank());
                fieldPane.add(new Barriers().getBlack(tank.getSize()), tank.getStartPosition().getX(), tank.getStartPosition().getY());
                tank.getMap().setElement('0', tank.getTankPosition().getX(), tank.getTankPosition().getY());
            });
            JOptionPane.showMessageDialog(new JFrame(), "GAME OVER!", "Dialog", JOptionPane.WARNING_MESSAGE);
        }
    }
}
