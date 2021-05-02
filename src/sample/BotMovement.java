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

        algorithm.path();
        //System.out.println(moves);

//        for (int i = 0; i < moves.size(); i++) {
//            switch (moves.get(i)) {
//                case 'u':
//                    //if (bot.getPosition().getY() > 0) {
//                        bot.moveUp(bot);
//                    //}
//                    break;
//                case 'r':
//                   // if (bot.getPosition().getX() < map.getSize() - 1) {
//                        bot.moveRight(bot);
//                    //}
//                    break;
//                case 'd':
//                    //if (bot.getPosition().getY() < map.getSize() - 1) {
//                        bot.moveDown(bot);
//                    //}
//                    break;
//                case 'l':
//                    //if (bot.getPosition().getX() > 0) {
//                        bot.moveLeft(bot);
//                    //}
//                    break;
//            }
//        }
    }

    public void start() {
        if (t == null) {
            t = new Thread(this);
            t.start();
        }
    }

}
