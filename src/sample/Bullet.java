package sample;

import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.io.File;

public class Bullet {
    private int x = 0;
    private int y = 0;
    private int direction;
    private Map map;
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

    public void fire(Tank tank, GridPane fieldPane) {
        bulletView.setFitHeight(tank.getSize() - Math.round((tank.getSize() * 10.0) / 100));
        bulletView.setFitWidth(tank.getSize() - Math.round((tank.getSize() * 10.0) / 100));
        direction = tank.getTankDirection();
        if (direction == 1) {
            x = tank.getTankPosition().getX();
            y = tank.getTankPosition().getY();
            if (!fireBotUp(tank, fieldPane)) {
                fireUp(tank, fieldPane);
            }
        } else if (direction == 2) {
            x = tank.getTankPosition().getX();
            y = tank.getTankPosition().getY();
            fireRight(tank, fieldPane);
        } else if (direction == 3) {
            x = tank.getTankPosition().getX();
            y = tank.getTankPosition().getY();
            if (!fireBotDown(tank, fieldPane)) {
                fireDown(tank, fieldPane);
            }
        } else if (direction == 4) {
            x = tank.getTankPosition().getX();
            y = tank.getTankPosition().getY();
            fireLeft(tank, fieldPane);
        }
    }

    public void fireUp(Tank tank, GridPane fieldPane) {
        if (y > 0 && barriers[y - 1][x] != 9) {
            int counter = 1;
            Bullet bullet = new Bullet(map, tank.getTankDirection());
            fieldPane.add(bullet.getBulletView(tank), tank.getTankPosition().getX(), tank.getTankPosition().getY());
            TranslateTransition transition = new TranslateTransition(Duration.millis(100), bullet.getBulletView(tank));
            PauseTransition pause = new PauseTransition(Duration.millis(100));
            ImageView v = new Barriers().getBlack(tank.getSize());

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
            transition.setByY((-1) * counter * tank.getSize() - 25);
            transition.play();
            pause.setOnFinished(e -> bullet.getBulletView(tank).setVisible(false));
            pause.play();
        }
    }

    public void fireDown(Tank tank, GridPane fieldPane) {
        if (y < barriers.length - 1 && barriers[y + 1][x] != 9) {
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

    public void fireLeft(Tank tank, GridPane fieldPane) {
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

    public void fireRight(Tank tank, GridPane fieldPane) {
        if (x < barriers.length - 1 && barriers[y][x + 1] != 9) {
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


    public boolean fireBotUp(Tank tank, GridPane fieldPane) {
        boolean cond = false;
        if (y > 0) {
            Map bots = tank.getMap();
            int counter = 1;
            Bullet bullet = new Bullet(map, tank.getTankDirection());
            TranslateTransition transition = new TranslateTransition(Duration.millis(100), bullet.getBulletView(tank));
            PauseTransition pause = new PauseTransition(Duration.millis(100));
            ImageView v = new Barriers().getBlack(tank.getSize());

            int i = y - 1;
            while (i >= 0) {
                if (bots.getValueAt(i, x) == '0') {
                    counter++;
                    i--;
                } else if (bots.getValueAt(i, x) == 'M') {
                    fieldPane.add(bullet.getBulletView(tank), tank.getTankPosition().getX(), tank.getTankPosition().getY());
                    if (bots.getValueAt(i, x) != '0') {
                        bots.setElement('0', i, x);
                        fieldPane.add(v, x, i);
                        tank.getTank().toFront();
                        if (counter > 1) {
                            counter--;
                        }
                        transition.setByY((-1) * counter * tank.getSize() - 25);
                        transition.play();
                        pause.setOnFinished(e -> bullet.getBulletView(tank).setVisible(false));
                        pause.play();
                        cond = true;
                    }
                    break;
                } else {
                    break;
                }
            }
        }
        return cond;
    }

    public boolean fireBotDown(Tank tank, GridPane fieldPane) {
        boolean cond = false;
        if (y < barriers.length - 1) {
            Map bots = tank.getMap();
            int counter = 1;
            Bullet bullet = new Bullet(map, tank.getTankDirection());
            TranslateTransition transition = new TranslateTransition(Duration.millis(100), bullet.getBulletView(tank));
            PauseTransition pause = new PauseTransition(Duration.millis(100));
            ImageView v = new Barriers().getBlack(tank.getSize());

            int i = y + 1;
            while (i < barriers.length) {
                if (bots.getValueAt(i, x) == '0') {
                    counter++;
                    i++;
                } else if (bots.getValueAt(i, x) == 'M') {
                    fieldPane.add(bullet.getBulletView(tank), tank.getTankPosition().getX(), tank.getTankPosition().getY());
                    if (bots.getValueAt(i, x) != '0') {
                        bots.setElement('0', i, x);
                        fieldPane.add(v, x, i);
                        tank.getTank().toFront();
                        if (counter > 1) {
                            counter--;
                        }
                        transition.setByY(counter * tank.getSize() - 25);
                        transition.play();
                        pause.setOnFinished(e -> bullet.getBulletView(tank).setVisible(false));
                        pause.play();
                        cond = true;
                    }
                    break;
                } else {
                    break;
                }
            }
        }
        return cond;
    }

    public Map getMap() {
        return map;
    }

    public void fireOnline(Tank tank, GridPane fieldPane) {
        bulletView.setFitHeight(tank.getSize() - Math.round((tank.getSize() * 10.0) / 100));
        bulletView.setFitWidth(tank.getSize() - Math.round((tank.getSize() * 10.0) / 100));
        direction = tank.getTankDirection();
        map = tank.getMap();
        if (direction == 1) {
            x = tank.getTankPosition().getX();
            y = tank.getTankPosition().getY();
            if (!fireBotUp(tank, fieldPane)) {
                fireUp(tank, fieldPane);
            }
        } else if (direction == 2) {
            x = tank.getTankPosition().getX();
            y = tank.getTankPosition().getY();
            fireRight(tank, fieldPane);
        } else if (direction == 3) {
            x = tank.getTankPosition().getX();
            y = tank.getTankPosition().getY();
            if (!fireBotDown(tank, fieldPane)) {
                fireDown(tank, fieldPane);
            }
        } else if (direction == 4) {
            x = tank.getTankPosition().getX();
            y = tank.getTankPosition().getY();
            fireLeft(tank, fieldPane);
        }
    }

}

