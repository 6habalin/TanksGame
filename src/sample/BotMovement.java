package sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BotMovement implements Runnable {
    private final Bot bot;
    private int direction = 1;
    private final Random rand = new Random();
    private Thread t;
    private final Map map;
    private Tank tank;


    BotMovement(Bot bot, Tank tank) {
        this.bot = bot;
        direction = bot.getBotDirection();
        map = bot.getMap();
        this.tank = tank;
    }

    @Override
    public void run() {
        BotMovementAlgorithm algorithm = new BotMovementAlgorithm(bot, tank);
        while(true){
            algorithm.path();
        }
    }

    public void start() {
        if (t == null) {
            t = new Thread(this);
            t.start();
        }
    }

}
