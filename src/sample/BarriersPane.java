package sample;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;


public class BarriersPane extends GridPane {
    Map map;
    GridPane pane = new GridPane();
    Barriers barriers = new Barriers();
    Tank tank = new Tank();

    BarriersPane(Map map) {
        this.map = map;
        pane.setMaxSize(500, 500);
        pane.setMinSize(500, 500);
        pane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        barriers.setSize(map.getSize());
        tank.setSize(barriers.getSize());
        for (int i = 0; i < map.getSize(); i++) {
            for (int j = 0; j < map.getSize(); j++) {
                switch (map.getValueAt(i, j)) {
                    case 'S':
                        pane.add(barriers.getSteel(), j, i);
                        break;
                    case 'B':
                        pane.add(barriers.getBrick(), j, i);
                        break;
                    case 'W':
                        pane.add(barriers.getWater(), j, i);
                        break;
                    case 'T':
                        pane.add(barriers.getTrees(), j, i);
                        break;
                    case '0':
                        pane.add(barriers.getTransparent(), j, i);
                        break;
                    case 'P':
                        pane.add(tank.getTank(), j, i);
                        break;
                }
            }
        }
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public GridPane getPane() {
        return pane;
    }

}
