package sample;

import java.io.Serializable;

public class MyPlayer implements Player, Serializable {
    private Map map;
    private int mapSize = 0;
    private final Position position = new Position(0, 0);
    int x = 0;
    int y = 0;


    @Override
    public void setMap(Map map) {
        this.map = map;
        mapSize = map.getSize();
        for (int i = 0; i < map.getSize(); i++) {
            for (int j = 0; j < map.getSize(); j++) {
                if (map.getValueAt(i, j) == 'P') {
                    position.setX(j);
                    position.setY(i);
                }
            }
        }
    }


    @Override
    public void moveRight() {
        x = position.getY();
        y = position.getX();
        if (y + 1 <= mapSize - 1) {
            if (map.getValueAt(x, y + 1) != '1') {
                position.setX(y + 1);
            }
        }
    }

    @Override
    public void moveLeft() {
        x = position.getY();
        y = position.getX();
        if (y - 1 >= 0) {
            if (map.getValueAt(x, y - 1) != '1') {
                position.setX(y - 1);
            }
        }
    }

    @Override
    public void moveUp() {
        x = position.getY();
        y = position.getX();
        if (x - 1 >= 0) {
            if (map.getValueAt(x - 1, y) != '1') {
                position.setY(x - 1);
            }
        }
    }

    @Override
    public void moveDown() {
        x = position.getY();
        y = position.getX();
        if (x + 1 <= map.getSize() - 1) {
            if (map.getValueAt(x + 1, y) != '1') {
                position.setY(x + 1);
            }
        }
    }

    @Override
    public Position getPosition() {
        return position;
    }

}
