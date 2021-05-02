package sample;

public class BotMovement implements Runnable {
    private final Bot bot;
    private Thread t;
    private final Tank tank;
    private boolean movement = false;


    BotMovement(Bot bot, Tank tank) {
        this.bot = bot;
        this.tank = tank;
    }

    @Override
    public void run() {
        BotMovementAlgorithm algorithm = new BotMovementAlgorithm(bot, tank);
        while(!movement){
            algorithm.path();
        }
    }

    public void start() {
        if (t == null) {
            t = new Thread(this);
            t.start();
        }
    }

    public void stop() throws InterruptedException {
        if(t != null){
            t.interrupt();
        }
    }

    public void stopMovement(){
        movement = true;
        t.interrupt();
    }

}
