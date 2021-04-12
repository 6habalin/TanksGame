package sample;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class Game {
    private Map map;

    Game(Map map) throws InvalidMapException {
        setMap(map);
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public void addPlayer(Player newPlayer) {
        newPlayer.setMap(this.map);
    }

}
