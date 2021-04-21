package sample;

import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Bullet {
    private int x = 0;
    private int y = 0;
    private int direction;
    private final Map map;
    private int[][] barriers;
    private final File bullet = new File("src/sample/Images/bullet.png");
    private final Image image = new Image(bullet.toURI().toString());
    private final ImageView bulletView = new ImageView(image);


    Bullet(Map map, int direction) {
        this.map = map;
        this.direction = direction;
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

    public void fire(Tank tank, GridPane fieldPane, Bot[] botArray) {
        bulletView.setFitHeight(tank.getSize() - Math.round((tank.getSize() * 10.0) / 100));
        bulletView.setFitWidth(tank.getSize() - Math.round((tank.getSize() * 10.0) / 100));
        direction = tank.getTankDirection();
        if (direction == 1) {
            x = tank.getTankPosition().getX();
            y = tank.getTankPosition().getY();
            fireUp(tank, fieldPane, botArray);
        } else if (direction == 2) {
            x = tank.getTankPosition().getX();
            y = tank.getTankPosition().getY();
            fireRight(tank, fieldPane, botArray);
        } else if (direction == 3) {
            x = tank.getTankPosition().getX();
            y = tank.getTankPosition().getY();
            fireDown(tank, fieldPane, botArray);
        } else if (direction == 4) {
            x = tank.getTankPosition().getX();
            y = tank.getTankPosition().getY();
            fireLeft(tank, fieldPane, botArray);
        }
    }

    public void fireUp(Tank tank, GridPane fieldPane, Bot[] botArray) {
        if (y > 0 && barriers[y - 1][x] != 9) {
            int counter = 1;
            List<String> botPositions = new ArrayList<String>();
            Bullet bullet = new Bullet(map, tank.getTankDirection());
            fieldPane.add(bullet.getBulletView(tank), tank.getTankPosition().getX(), tank.getTankPosition().getY());
            TranslateTransition transition = new TranslateTransition(Duration.millis(100), bullet.getBulletView(tank));
            PauseTransition pause = new PauseTransition(Duration.millis(100));
            for (int i = 0; i < botArray.length; i++) {
                botPositions.add(botArray[i].getBotPosition().toString());
            }
            System.out.println(botPositions);
            int i = y - 1;
            while (i >= 0) {
                if (barriers[i][x] == 0) {
                    counter++;
                    i--;
                } else if (barriers[i][x] == 9) {
                    break;
                } else if (barriers[i][x] == 1) {
                    if (map.getValueAt(i, x) != '0') {
                        map.setElement('0', i, x);
                        ImageView v = new Barriers().getBlack(tank.getSize());
                        fieldPane.add(v, x, i);
                        tank.getTank().toFront();
                    }
                    barriers[i][x]--;
                    break;
                } else if (botPositions.contains("(" + i + "," + x + ")")) {
                    System.out.println("HIT HIT HIT");
                } else {
                    barriers[i][x]--;
                    break;
                }
                System.out.println(new Position(i, x));
            }
            if (counter > 1) {
                counter--;
            }
            transition.setByY((-1) * counter * tank.getSize() - 25);
            transition.play();
            pause.setOnFinished(e -> bullet.getBulletView(tank).setVisible(false));
            pause.play();
        }
    }

    public void fireDown(Tank tank, GridPane fieldPane, Bot[] botArray) {
        if (y < barriers.length && barriers[y + 1][x] != 9) {
            int counter = 1;
            Bullet bullet = new Bullet(map, tank.getTankDirection());
            fieldPane.add(bullet.getBulletView(tank), tank.getTankPosition().getX(), tank.getTankPosition().getY());
            TranslateTransition transition = new TranslateTransition(Duration.millis(100), bullet.getBulletView(tank));
            PauseTransition pause = new PauseTransition(Duration.millis(100));
            int i = y + 1;
            while (i < barriers.length) {
                if (barriers[i][x] == 0) {
                    counter++;
                    i++;
                } else if (barriers[i][x] == 9) {
                    break;
                } else if (barriers[i][x] == 1) {
                    if (map.getValueAt(i, x) != '0') {
                        map.setElement('0', i, x);
                        ImageView v = new Barriers().getBlack(tank.getSize());
                        fieldPane.add(v, x, i);
                        tank.getTank().toFront();
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
            transition.setByY(counter * tank.getSize() + 25);
            transition.play();
            pause.setOnFinished(e -> bullet.getBulletView(tank).setVisible(false));
            pause.play();
        }
    }

    public void fireLeft(Tank tank, GridPane fieldPane, Bot[] botArray) {
        if (x > 0 && barriers[y][x - 1] != 9) {
            int counter = 1;
            Bullet bullet = new Bullet(map, tank.getTankDirection());
            fieldPane.add(bullet.getBulletView(tank), tank.getTankPosition().getX(), tank.getTankPosition().getY());
            TranslateTransition transition = new TranslateTransition(Duration.millis(100), bullet.getBulletView(tank));
            PauseTransition pause = new PauseTransition(Duration.millis(100));
            int i = x - 1;
            while (i >= 0) {
                if (barriers[y][i] == 0) {
                    counter++;
                    i--;
                } else if (barriers[y][i] == 9) {
                    break;
                } else if (barriers[y][i] == 1) {
                    if (map.getValueAt(y, i) != '0') {
                        map.setElement('0', y, i);
                        ImageView v = new Barriers().getBlack(tank.getSize());
                        fieldPane.add(v, i, y);
                        tank.getTank().toFront();
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
            transition.setByX((-1) * counter * tank.getSize() - 25);
            transition.play();
            pause.setOnFinished(e -> bullet.getBulletView(tank).setVisible(false));
            pause.play();
        }
    }

    public void fireRight(Tank tank, GridPane fieldPane, Bot[] botArray) {
        if (x < barriers.length && barriers[y][x + 1] != 9) {
            int counter = 1;
            Bullet bullet = new Bullet(map, tank.getTankDirection());
            fieldPane.add(bullet.getBulletView(tank), tank.getTankPosition().getX(), tank.getTankPosition().getY());
            TranslateTransition transition = new TranslateTransition(Duration.millis(100), bullet.getBulletView(tank));
            PauseTransition pause = new PauseTransition(Duration.millis(100));
            int i = x + 1;
            while (i < barriers.length) {
                if (barriers[y][i] == 0) {
                    counter++;
                    i++;
                } else if (barriers[y][i] == 9) {
                    break;
                } else if (barriers[y][i] == 1) {
                    if (map.getValueAt(y, i) != '0') {
                        map.setElement('0', y, i);
                        ImageView v = new Barriers().getBlack(tank.getSize());
                        fieldPane.add(v, i, y);
                        tank.getTank().toFront();
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
            transition.setByX(counter * tank.getSize() + 25);
            transition.play();
            pause.setOnFinished(e -> bullet.getBulletView(tank).setVisible(false));
            pause.play();
        }
    }

    public ImageView getBulletView(Tank tank) {
        bulletView.setFitHeight(tank.getSize() - Math.round((tank.getSize() * 10.0) / 100));
        bulletView.setFitWidth(tank.getSize() - Math.round((tank.getSize() * 10.0) / 100));
        return bulletView;
    }


}

