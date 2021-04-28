package sample;


import java.io.Serializable;

public class Position implements Serializable {
    private int x;
    private int y;

    Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean equals(Position pos) {
        return x == pos.getX() && y == pos.getY();
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
