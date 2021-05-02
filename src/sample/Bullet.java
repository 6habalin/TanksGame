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
    private Map map;
    private int[][] barriers;
    private final File bullet = new File("src/sample/Images/bullet.png");
    private final Image image = new Image(bullet.toURI().toString());
    private final ImageView bulletView = new ImageView(image);
    private List<Bot> botList = new ArrayList<Bot>();
    private List<BotMovement> botMovementList = new ArrayList<BotMovement>();
    private List<BotBullet> botBulletList = new ArrayList<BotBullet>();


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

    public void fire(Tank tank, GridPane fieldPane, List<Bot> botList, List<BotMovement> botMovementList, List<BotBullet> botBulletList) {
        this.botList = botList;
        this.botMovementList = botMovementList;
        this.botBulletList = botBulletList;
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
            if (!fireBotRight(tank, fieldPane)) {
                fireRight(tank, fieldPane);
            }
        } else if (direction == 3) {
            x = tank.getTankPosition().getX();
            y = tank.getTankPosition().getY();
            if (!fireBotDown(tank, fieldPane)) {
                fireDown(tank, fieldPane);
            }
        } else if (direction == 4) {
            x = tank.getTankPosition().getX();
            y = tank.getTankPosition().getY();
            if (!fireBotLeft(tank, fieldPane)) {
                fireLeft(tank, fieldPane);
            }
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
            boolean cicleBreak = false;
            while (i >= 0) {
                switch (bots.getValueAt(i, x)) {
                    case 'B':
                    case 'S':
                    case 'W':
                        cicleBreak = true;
                        break;
                    default:
                        for (int j = 0; j < botList.size(); j++) {
                            if (botList.get(j).getPosition().getY() == i && botList.get(j).getPosition().getX() == x) {
                                cond = true;
                                cicleBreak = true;
                                System.out.println("Hit");
                                fieldPane.add(bullet.getBulletView(tank), tank.getTankPosition().getX(), tank.getTankPosition().getY());
                                tank.getTank().toFront();
                                bullet.getBulletView(tank).toFront();
                                botMovementList.get(j).stopMovement();
                                botBulletList.get(j).stopBullet();
                                transition.setByY((-1) * counter * tank.getSize() - 25);
                                transition.play();
                                pause.setOnFinished(e -> bullet.getBulletView(tank).setVisible(false));
                                pause.play();
                                fieldPane.getChildren().remove(botList.get(j).getBotTank());
                                bots.setElement('0', i, x);
                                botList.remove(botList.get(j));
                                botMovementList.remove(botMovementList.get(j));
                                botBulletList.remove(botBulletList.get(j));
                            } else {
                                counter++;
                            }
                        }
                        break;
                }
                if (cicleBreak) {
                    break;
                }
                i--;
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
            bullet.getBulletView(tank).toFront();
            TranslateTransition transition = new TranslateTransition(Duration.millis(100), bullet.getBulletView(tank));
            PauseTransition pause = new PauseTransition(Duration.millis(100));
            ImageView v = new Barriers().getBlack(tank.getSize());

            int i = y + 1;
            boolean cicleBreak = false;
            while (i < barriers.length) {
                switch (bots.getValueAt(i, x)) {
                    case 'B':
                    case 'S':
                    case 'W':
                        cicleBreak = true;
                        break;
                    default:
                        for (int j = 0; j < botList.size(); j++) {
                            if (botList.get(j).getPosition().getY() == i && botList.get(j).getPosition().getX() == x) {
                                cond = true;
                                cicleBreak = true;
                                System.out.println("Hit");
                                fieldPane.add(bullet.getBulletView(tank), tank.getTankPosition().getX(), tank.getTankPosition().getY());
                                tank.getTank().toFront();
                                bullet.getBulletView(tank).toFront();
                                botMovementList.get(j).stopMovement();
                                botBulletList.get(j).stopBullet();
                                transition.setByY(counter * tank.getSize() - 25);
                                transition.play();
                                pause.setOnFinished(e -> bullet.getBulletView(tank).setVisible(false));
                                pause.play();
                                fieldPane.getChildren().remove(botList.get(j).getBotTank());
                                botList.remove(botList.get(j));
                                botMovementList.remove(botMovementList.get(j));
                                botBulletList.remove(botBulletList.get(j));
                                bots.setElement('0', i, x);
                            } else {
                                counter++;
                            }
                        }
                        break;
                }
                if (cicleBreak) {
                    break;
                }
                i++;
            }
        }
        return cond;
    }

    public boolean fireBotRight(Tank tank, GridPane fieldPane) {
        boolean cond = false;
        if (x < barriers.length - 1) {
            Map bots = tank.getMap();
            int counter = 1;
            Bullet bullet = new Bullet(map, tank.getTankDirection());
            bullet.getBulletView(tank).toFront();
            TranslateTransition transition = new TranslateTransition(Duration.millis(100), bullet.getBulletView(tank));
            PauseTransition pause = new PauseTransition(Duration.millis(100));
            ImageView v = new Barriers().getBlack(tank.getSize());

            int i = x + 1;
            boolean cicleBreak = false;
            while (i < barriers.length) {
                switch (bots.getValueAt(y, i)) {
                    case 'B':
                    case 'S':
                    case 'W':
                        cicleBreak = true;
                        break;
                    default:
                        for (int j = 0; j < botList.size(); j++) {
                            if (botList.get(j).getPosition().getX() == i && botList.get(j).getPosition().getY() == y) {
                                cond = true;
                                cicleBreak = true;
                                System.out.println("Hit");
                                fieldPane.add(bullet.getBulletView(tank), tank.getTankPosition().getX(), tank.getTankPosition().getY());
                                tank.getTank().toFront();
                                bullet.getBulletView(tank).toFront();
                                botMovementList.get(j).stopMovement();
                                botBulletList.get(j).stopBullet();
                                transition.setByX(counter * tank.getSize() - 25);
                                transition.play();
                                pause.setOnFinished(e -> bullet.getBulletView(tank).setVisible(false));
                                pause.play();
                                fieldPane.getChildren().remove(botList.get(j).getBotTank());
                                botList.remove(botList.get(j));
                                botMovementList.remove(botMovementList.get(j));
                                botBulletList.remove(botBulletList.get(j));
                                bots.setElement('0', y, i);
                            } else {
                                counter++;
                            }
                        }
                        break;
                }
                if (cicleBreak) {
                    break;
                }
                i++;
            }
        }
        return cond;
    }

    public boolean fireBotLeft(Tank tank, GridPane fieldPane) {
        boolean cond = false;
        if (x > 0) {
            Map bots = tank.getMap();
            int counter = 1;
            Bullet bullet = new Bullet(map, tank.getTankDirection());
            bullet.getBulletView(tank).toFront();
            TranslateTransition transition = new TranslateTransition(Duration.millis(100), bullet.getBulletView(tank));
            PauseTransition pause = new PauseTransition(Duration.millis(100));
            ImageView v = new Barriers().getBlack(tank.getSize());

            int i = x - 1;
            boolean cicleBreak = false;
            while (i >= 0) {
                switch (bots.getValueAt(y, i)) {
                    case 'B':
                    case 'S':
                    case 'W':
                        cicleBreak = true;
                        break;
                    default:
                        for (int j = 0; j < botList.size(); j++) {
                            if (botList.get(j).getPosition().getX() == i && botList.get(j).getPosition().getY() == y) {
                                cond = true;
                                cicleBreak = true;
                                System.out.println("Hit");
                                fieldPane.add(bullet.getBulletView(tank), tank.getTankPosition().getX(), tank.getTankPosition().getY());
                                tank.getTank().toFront();
                                bullet.getBulletView(tank).toFront();
                                botMovementList.get(j).stopMovement();
                                botBulletList.get(j).stopBullet();
                                transition.setByX((-1) * counter * tank.getSize() - 25);
                                transition.play();
                                pause.setOnFinished(e -> bullet.getBulletView(tank).setVisible(false));
                                pause.play();
                                fieldPane.getChildren().remove(botList.get(j).getBotTank());
                                botList.remove(botList.get(j));
                                botMovementList.remove(botMovementList.get(j));
                                botBulletList.remove(botBulletList.get(j));
                                bots.setElement('0', y, i);
                            } else {
                                counter++;
                            }
                        }
                        break;
                }
                if (cicleBreak) {
                    break;
                }
                i--;
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

