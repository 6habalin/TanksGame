package sample;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Server {

    private static Position position;
    private static Map map;
    private static List<Position> positions;


    public static void main(String[] args) throws Exception{
        positions = new ArrayList<Position>();

        ServerSocket server = new ServerSocket(8000);
        Socket socket = server.accept();

        ObjectInputStream fromClient = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream toClient = new ObjectOutputStream(socket.getOutputStream());

        map = (Map) fromClient.readObject();
        position = (Position) fromClient.readObject();
        positions.add(position);
        toClient.writeInt(positions.size());
        toClient.flush();
        int index = positions.indexOf(position);
        String action;

        while (true){
            action = (String) fromClient.readObject();
            switch (action){
                case "up":
                    positions.get(index).setY(position.getY() - 1);
                    break;
                case "down":
                    positions.get(index).setY(position.getY() + 1);
                    break;
                case "left":
                    positions.get(index).setX(position.getX() - 1);
                    break;
                case "right":
                    positions.get(index).setX(position.getX() + 1);
                    break;
            }
        }


//        fromClient.close();
//        toClient.close();
//        socket.close();

    }



    public static Position getRandomPosition(Map map) {
        Random rand = new Random();
        Position pos;
        int randI = rand.nextInt(map.getSize());
        int randJ = rand.nextInt(map.getSize());
        while (true) {
            if (map.getValueAt(randJ, randI) == '0') {
                pos = new Position(randI, randJ);
                map.setElement('P', randJ, randI);
                break;
            } else {
                randI = rand.nextInt(map.getSize());
                randJ = rand.nextInt(map.getSize());
            }
        }
        return pos;
    }
}