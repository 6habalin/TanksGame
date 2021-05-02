package sample;

public class BotMovement implements Runnable {          //class for moving bots with multithreading
    private final Bot bot;
    private Thread t;
    private final Tank tank;
    private boolean movement = false;
    private final BotMovementAlgorithm algorithm;


    BotMovement(Bot bot, Tank tank) {
        this.bot = bot;
        this.tank = tank;
        algorithm = new BotMovementAlgorithm(bot, tank);
    }

    @Override
    public void run() {
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
