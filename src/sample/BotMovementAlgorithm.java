package sample;

import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.List;

public class BotMovementAlgorithm {
    private int direction;
    private final Map map;
    private final Bot bot;
    private final Tank tank;
    private final Position position;
    private final List<Character> barriers = new ArrayList<Character>();
    private final List<String> hasVisited = new ArrayList<String>();

    BotMovementAlgorithm(Bot bot, Tank tank) {
        this.bot = bot;
        this.tank = tank;
        direction = bot.getBotDirection();
        this.map = bot.getMap();
        position = bot.getPosition();
        barriers.add('B');
        barriers.add('S');
        barriers.add('W');
    }

    public void path() {
        int i = 0;
        while (i == 0) {
            if (direction == 1 && !Thread.currentThread().isInterrupted()) {
                i = checkUp();
            } else if (direction == 2 && !Thread.currentThread().isInterrupted()) {
                i = checkRight();
            } else if (direction == 3 && !Thread.currentThread().isInterrupted()) {
                i = checkDown();
            } else if (direction == 4 && !Thread.currentThread().isInterrupted()) {
                i = checkLeft();
            }
        }
    }

    public int checkUp() {
        int x = position.getY();
        int y = position.getX();
        if (!(position.equals(tank.getTankPosition()))) {
            if (y - 1 > 0) {
                if (barriers.contains(map.getValueAt(x, y - 1))) {
                    if (x - 1 >= 0) {
                        if (!(barriers.contains(map.getValueAt(x - 1, y)))) {
                            bot.moveUp(bot);
                            hasVisited.add(bot.getPosition().toString());
                        } else {
                            bot.moveDown(bot);
                        }
                    } else {
                        bot.moveRight(bot);
                    }

                } else {
                    bot.moveLeft(bot);
                }
            } else {
                bot.moveDown(bot);
            }
            hasVisited.add(bot.getPosition().toString());
            direction = bot.getBotDirection();
            return 0;
        } else {
            return 1;
        }
    }

    public int checkRight() {
        int x = position.getY();
        int y = position.getX();
        if (!(position.equals(tank.getTankPosition()))) {
            if (x - 1 >= 0) {
                if (barriers.contains(map.getValueAt(x - 1, y))) {
                    if (y + 1 < map.getSize()) {
                        if (!(barriers.contains(map.getValueAt(x, y + 1)))) {
                            bot.moveRight(bot);
                            hasVisited.add(bot.getPosition().toString());
                        } else {
                            bot.moveLeft(bot);
                        }
                    } else {
                        bot.moveUp(bot);
                    }

                } else {
                    //direction = rotateCounterClockWise(direction);
                    bot.moveUp(bot);
                }
            } else {
                bot.moveLeft(bot);
            }
            hasVisited.add(bot.getPosition().toString());
            direction = bot.getBotDirection();
            return 0;
        } else {
            return 1;
        }
    }

    public int checkDown() {
        int x = position.getY();
        int y = position.getX();
        if (!(position.equals(tank.getTankPosition()))) {
            if (y + 1 < map.getSize()) {
                if (barriers.contains(map.getValueAt(x, y + 1))) {
                    if (x + 1 < map.getSize()) {
                        if (!(barriers.contains(map.getValueAt(x + 1, y)))) {
                            bot.moveDown(bot);
                            hasVisited.add(bot.getPosition().toString());
                        } else {
                            bot.moveUp(bot);
                        }
                    } else {
                        bot.moveLeft(bot);
                    }

                } else {
                    //direction = rotateCounterClockWise(direction);
                    bot.moveRight(bot);
                }
            } else {
                bot.moveUp(bot);
            }
            hasVisited.add(bot.getPosition().toString());
            direction = bot.getBotDirection();
            return 0;
        } else {
            return 1;
        }
    }

    public int checkLeft() {
        int x = position.getY();
        int y = position.getX();
        if (!(position.equals(tank.getTankPosition()))) {

            if (x + 1 < map.getSize()) {
                if (barriers.contains(map.getValueAt(x + 1, y))) {

                    if (y - 1 >= 0) {
                        if (!(barriers.contains(map.getValueAt(x, y - 1)))) {

                            bot.moveLeft(bot);

                            hasVisited.add(bot.getPosition().toString());
                        } else {
                            bot.moveRight(bot);
                        }
                    } else {
                        bot.moveDown(bot);
                    }

                } else {
                    //direction = rotateCounterClockWise(direction);
                    bot.moveDown(bot);
                }
            } else {
                bot.moveRight(bot);
            }
            hasVisited.add(bot.getPosition().toString());
            direction = bot.getBotDirection();
            return 0;
        } else {
            return 1;
        }
    }


    public int rotateClockWise(int dir) {
        if (dir > 0 && dir <= 4) {
            if (dir == 4) {
                dir = 1;
            } else {
                dir += 1;
            }
        }
        return dir;
    }

    public int rotateCounterClockWise(int dir) {
        if (dir > 0 && dir <= 4) {
            if (dir == 1) {
                dir = 4;
            } else {
                dir -= 1;
            }
        }
        return dir;
    }
}