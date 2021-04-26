package sample;

public class TankMovement implements Runnable {
    private final Tank tank;
    String action;

    TankMovement(Tank tank, String action) {
        this.tank = tank;
        this.action = action;
    }


    @Override
    public void run() {
        switch (action) {
            case "up":
                tank.moveUp(tank);
                break;
            case "down":
                tank.moveDown(tank);
                break;
            case "right":
                tank.moveRight(tank);
                break;
            case "left":
                tank.moveLeft(tank);
                break;
        }
    }

}
