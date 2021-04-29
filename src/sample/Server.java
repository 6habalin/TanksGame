package sample;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
import java.util.Random;

public class Server {

    private static Position position1;
    private static Position position2;
    private static Map map;


    public static void main(String[] args) throws Exception {

        ServerSocket server = new ServerSocket(8000);
        Socket socket1 = server.accept();
        Socket socket2 = server.accept();


        ObjectInputStream fromClient = new ObjectInputStream(socket1.getInputStream());
        ObjectInputStream fromClient2 = new ObjectInputStream(socket2.getInputStream());
        ObjectOutputStream toClient = new ObjectOutputStream(socket1.getOutputStream());
        ObjectOutputStream toClient2 = new ObjectOutputStream(socket2.getOutputStream());

        map = (Map) fromClient.readObject();
        Map map2 = (Map) fromClient2.readObject();
        position1 = (Position) fromClient.readObject();
        position2 = (Position) fromClient2.readObject();
        position2 = getRandomPosition(map);

        toClient.writeObject(map);
        toClient2.writeObject(map);

        toClient.writeObject(position1);
        toClient2.writeObject(position2);

        toClient.writeObject(position2);
        toClient2.writeObject(position1);
        toClient.flush();
        toClient2.flush();


        Runnable runnable1 = () -> {
            while (true) {
                String action1 = null;
                try {
                    action1 = (String) fromClient.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                switch (Objects.requireNonNull(action1)) {
                    case "up":
                        position1.setY(position1.getY() - 1);
                        try {
                            toClient2.writeObject("up");
                            toClient2.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "down":
                        position1.setY(position1.getY() + 1);
                        try {
                            toClient2.writeObject("down");
                            toClient2.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "left":
                        position1.setX(position1.getX() - 1);
                        try {
                            toClient2.writeObject("left");
                            toClient2.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "right":
                        position1.setX(position1.getX() + 1);
                        try {
                            toClient2.writeObject("right");
                            toClient2.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "fire":
                        try{
                            toClient2.writeObject("fire");
                            toClient2.flush();
                        } catch (IOException e){
                            e.printStackTrace();
                        }
                        break;
                }
                System.out.println(position1 + " " + position2);
            }
        };

        Runnable runnable2 = () -> {
            while (true) {
                String action2 = null;
                try {
                    action2 = (String) fromClient2.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }

                switch (Objects.requireNonNull(action2)) {
                    case "up":
                        position2.setY(position2.getY() - 1);
                        try {
                            toClient.writeObject("up");
                            toClient.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "down":
                        position2.setY(position2.getY() + 1);
                        try {
                            toClient.writeObject("down");
                            toClient.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "left":
                        position2.setX(position2.getX() - 1);
                        try {
                            toClient.writeObject("left");
                            toClient.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "right":
                        position2.setX(position2.getX() + 1);
                        try {
                            toClient.writeObject("right");
                            toClient.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "fire":
                        try{
                            toClient.writeObject("fire");
                            toClient.flush();
                        } catch (IOException e){
                            e.printStackTrace();
                        }
                        break;
                }
                System.out.println(position1 + " " + position2);
            }
        };

        Thread t1 = new Thread(runnable1);
        Thread t2 = new Thread(runnable2);
        t1.start();
        t2.start();
    }

    public static Position getRandomPosition(Map map) {
        Random rand = new Random();
        Position pos;
        int randI = rand.nextInt(map.getSize());
        int randJ = rand.nextInt(map.getSize());
        while (true) {
            if (map.getValueAt(randJ, randI) == '0') {
                pos = new Position(randI, randJ);
//                map.setElement('P', randJ, randI);
                break;
            } else {
                randI = rand.nextInt(map.getSize());
                randJ = rand.nextInt(map.getSize());
            }
        }
        return pos;
    }


}