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
        for(int i = 0; i < 5; i++){
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


    public List<Character> pathToTarget(Algorithm bot, Algorithm tank) {
        List<Character> moves = new ArrayList<Character>();

        double currentDistance = Algorithm.distance(bot, tank);
        while (Algorithm.distance(bot, tank) != 0) {
            if (bot.getY() != 0 && !Algorithm.getMarker().contains(0)) { // Check up direction
                bot.setY(bot.getY() - 1);
                if (Algorithm.distance(bot, tank) < currentDistance) {
                    moves.add('U');
                    currentDistance = Algorithm.distance(bot, tank);
                    Algorithm.clearMarker();
                } else {
                    //bot.setY(bot.getY() + 1); // Revert
                    Algorithm.addToMarker(0);
                    currentDistance = Algorithm.distance(bot, tank);
                }
            } else if (!Algorithm.getMarker().contains(1)) { // Check down direction
                bot.setY(bot.getY() + 1);
                if (Algorithm.distance(bot, tank) < currentDistance) {
                    moves.add('D');
                    currentDistance = Algorithm.distance(bot, tank);
                    Algorithm.clearMarker();
                } else {
                    //bot.setY(bot.getY() - 1); // Revert
                    currentDistance = Algorithm.distance(bot, tank);
                    Algorithm.addToMarker(1);
                }

            } else if (bot.getX() != 0 && !Algorithm.getMarker().contains(2)) { // Check left direction
                bot.setX(bot.getX() - 1);
                if (Algorithm.distance(bot, tank) < currentDistance) {
                    moves.add('L');
                    currentDistance = Algorithm.distance(bot, tank);
                    Algorithm.clearMarker();
                } else {
                    //bot.setX(bot.getX() + 1); // Revert
                    currentDistance = Algorithm.distance(bot, tank);
                    Algorithm.addToMarker(2);
                }
            } else if (!Algorithm.getMarker().contains(3)) { // Check right direction
                bot.setX(bot.getX() + 1);
                if (Algorithm.distance(bot, tank) < currentDistance) {
                    moves.add('R');
                    currentDistance = Algorithm.distance(bot, tank);
                    Algorithm.clearMarker();
                } else {
                    //bot.setX(bot.getX() - 1);
                }
            }
        }
        return moves;
    }

}
