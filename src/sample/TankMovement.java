package sample;

public class TankMovement implements Runnable{
    private Tank tank;
    private int direction = 2;
    private Thread t;
    String action;

    TankMovement(Tank tank, String action){
        this.tank = tank;
        this.action = action;
    }


    @Override
    public void run() {
        if(action.equals("up")){
            tank.moveUp(tank);
        } else if(action.equals("down")){
            tank.moveDown(tank);
        } else if(action.equals("right")){
            tank.moveRight(tank);
        } else if(action.equals("left")){
            tank.moveLeft(tank);
        }
    }

}
