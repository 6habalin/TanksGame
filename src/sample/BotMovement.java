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
        List<Character> moves = new ArrayList<Character>();
        Map botMap = tank.getMap();
        for(int i = 0; i < 10; i++){
            int temp = rand.nextInt(5);
            if(temp == 1){
                moves.add('U');
            } else if(temp == 2){
                moves.add('D');
            } else if(temp == 3){
                moves.add('L');
            } else if(temp == 4){
                moves.add('R');
            }
        }
        for (int i = 0; i < moves.size(); i++) {
            switch (moves.get(i)) {
                case 'U':
                    if (bot.getPosition().getY() < map.getSize() - 1) {
                        bot.moveUp(bot);
                    }
                    break;
                case 'R':
                    if (bot.getPosition().getX() < map.getSize() - 1) {
                        bot.moveRight(bot);
                    }
                    break;
                case 'D':
                    if (bot.getPosition().getY() > 0) {
                        bot.moveDown(bot);
                    }
                    break;
                case 'L':
                    if (bot.getPosition().getX() > 0) {
                        bot.moveLeft(bot);
                    }
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
