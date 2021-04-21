package sample;

import java.util.Random;

public class BotMovement implements Runnable {
    private Bot bot;
    private int direction = 1;
    private Random rand = new Random();
    Thread t;
    Map map;

    BotMovement(Bot bot) {
        this.bot = bot;
        direction = bot.getBotDirection();
        map = bot.getMap();
    }

    @Override
    public void run() {
        while (true) {
            direction = rand.nextInt(100);
            if (direction >= 70) {
                direction = 2;
            } else if (direction >= 40) {
                direction = 3;
            } else if (direction >= 20) {
                direction = 1;
            } else {
                direction = 4;
            }
            switch (direction) {
                case 1:
                    bot.moveUp(bot);
                    break;
                case 2:
                    bot.moveRight(bot);
                    break;
                case 3:
                    bot.moveDown(bot);
                    break;
                case 4:
                    bot.moveLeft(bot);
                    break;
            }

        }
    }

    public void start() {
        if (t == null) {
            t = new Thread(this);
            t.start();
        }
    }
}
