package sample;

public interface Player {
    void moveRight();

    void moveLeft();

    void moveUp();

    void moveDown();

    void setMap(Map map);

    Position getPosition();
}
